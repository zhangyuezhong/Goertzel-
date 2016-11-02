package com.telstra.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioFileTest6 {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		File file = new File("C:\\DCIM\\Dtmf-8_pcm.wav");
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

		int stepSize = 38590;
		final AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);

		byte[] buffers = new byte[stepSize];
		int byteReads = inputStream.read(buffers, 0, stepSize);
		while (byteReads > 0) {
			byte[] buf = new byte[byteReads];
			System.arraycopy(buffers, 0, buf, 0, byteReads);

			for (int i = 0; i < DTMF.DTMF_FREQUENCIES.length; i++) {
				double power =   Goertzel6.getPowewr(buf, DTMF.DTMF_FREQUENCIES[i], 8000);
				System.out.println("[" + DTMF.DTMF_FREQUENCIES[i] + "]=" + power);
			}
			System.out.println("=========================================");
			byteReads = inputStream.read(buffers, 0, stepSize);
		}

	}
}
