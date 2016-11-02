package com.telstra.audio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioFileTest4 {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		File file = new File("C:\\DCIM\\Dtmf-5.wav");
		AudioFileFormat aff = AudioSystem.getAudioFileFormat(file);
		AudioFileFormat.Type type = aff.getType();
		if (type == AudioFileFormat.Type.WAVE) {
			System.out.println("wav file detected");
		}

		AudioFormat af = aff.getFormat();
		System.out.println("getChannels = " + af.getChannels());
		System.out.println("getFrameRate = " + af.getFrameRate());
		System.out.println("getFrameSize = " + af.getFrameSize());
		System.out.println("getSampleRate = " + af.getSampleRate());
		System.out.println("getSampleSizeInBits = " + af.getSampleSizeInBits());
		System.out.println("getEncoding = " + af.getEncoding().toString());
		System.out.println("isBigEndian = " + af.isBigEndian());
		int SampleRate = 8000;

		// A DTMF Tone has to have a length of at least 40 ms: 8000 Hz * 0.04 s
		// = 320
		int SampleBlockSize = SampleRate * 40 / 1000;
		SampleBlockSize =320;
		int stepSize = SampleBlockSize;
		List<Goertzel4> rows = new ArrayList<Goertzel4>();
		for (int i = 0; i < DTMF.DTMF_ROW.length; i++) {
			Goertzel4 g1 = new Goertzel4(8000, DTMF.DTMF_ROW[i], SampleBlockSize);
			rows.add(g1);
		}
		List<Goertzel4> cols = new ArrayList<Goertzel4>();
		for (int i = 0; i < DTMF.DTMF_COL.length; i++) {
			Goertzel4 g1 = new Goertzel4(8000, DTMF.DTMF_COL[i], SampleBlockSize);
			cols.add(g1);
		}
		final AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);

		byte[] buffers = new byte[stepSize];
		int byteReads = inputStream.read(buffers, 0, stepSize);
		while (byteReads > 0) {
			int[] samples = new int[byteReads];
			for (int i = 0; i < byteReads; i++) {
				samples[i] = G711.ulaw2linear(buffers[i]);
			}
			//System.arraycopy(buffers, 0, samples, 0, byteReads);
			double rowMax = 0.0;
			double rowKey = 0.0;
			for (int i = 0; i < DTMF.DTMF_ROW.length; i++) {
				double power = rows.get(i).getPower(samples);
				if (power > rowMax) {
					rowMax = power;
					rowKey = DTMF.DTMF_ROW[i];
				}
			}
			double colMax = 0.0;
			double colKey = 0.0;
			for (int i = 0; i < DTMF.DTMF_COL.length; i++) {
				double power = cols.get(i).getPower(samples);
				if (power > colMax) {
					colMax = power;
					colKey = DTMF.DTMF_COL[i];
				}
			}
			
			
			
			double AmplitudeThreshold = 10.0;
			if (rowMax < AmplitudeThreshold || colMax < AmplitudeThreshold) {

			} else {
				System.out.println("rowMax=" + rowMax + " colMax=" + colMax);
				System.out.println("rowKey=" + rowKey + " colKey=" + colKey);
			}

			byteReads = inputStream.read(buffers, 0, stepSize);
		}

	}
}
