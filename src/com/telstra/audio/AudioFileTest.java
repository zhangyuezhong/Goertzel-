package com.telstra.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioFileTest {
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

		Goertzel g = new Goertzel();
		Goertzel2 g2 = new Goertzel2(8000.0f, DTMF.DTMF_FREQUENCIES);
		int stepSize = 2046;
		final AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);

		byte[] buffers = new byte[stepSize];
		int byteReads = inputStream.read(buffers, 0, stepSize);
		while (byteReads > 0) {
			
			byte [] buf = new byte[byteReads];
			System.arraycopy(buffers, 0, buf, 0, byteReads);
			for(byte b : buf)
			{
				System.out.println(b);
			}
			
			
			g2.process(buf);
			byteReads = inputStream.read(buffers, 0, stepSize);
		}

	}
	
	
//	
//	double maxRow = 0.0d;
//	double maxCol = 0.0d;
//	double dtmfRow = 0;
//	double dtmfCol = 0;
//	byte[] buf = new byte[byteReads];
//	
//
//	System.arraycopy(buffers, 0, buf, 0, byteReads);
//
//	for (double f : DTMF.DTMF_ROW) {
//		double power = g.getPower2(buf, 8000.0d, f);
//		if (power > maxRow) {
//			maxRow = power;
//			dtmfRow = f;
//		}
//	}
//	for (double f : DTMF.DTMF_COL) {
//		double power = g.getPower2(buf, 8000.0d, f);
//		if (power > maxCol) {
//			maxCol = power;
//			dtmfCol = f;
//		}
//	}
//	boolean valid = true;
//	if (maxCol < maxRow * 0.398) {
//		valid = false;
//	}
//	if (maxRow < maxCol * 0.5) {
//		valid = false;
//	}
//	if (valid) {
//		String msg = String.format("power- %f   %f   %f  %f", maxRow, dtmfRow, maxCol, dtmfCol);
//		System.out.println(msg);
//		System.out.println("===========================================================");
//	}
}
