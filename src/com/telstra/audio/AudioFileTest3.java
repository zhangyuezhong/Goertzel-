package com.telstra.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioFileTest3 {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		File file = new File("C:\\DCIM\\Dtmf-5.wav");

		Goertzel3 g1 = new Goertzel3(8000, 1336);
		Goertzel3 g2 = new Goertzel3(8000, 770);

		int stepSize = 205;
		final AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);

		byte[] buffers = new byte[stepSize];
		int byteReads = inputStream.read(buffers, 0, stepSize);
		while (byteReads > 0) {
			byte[] buf = new byte[byteReads];
			System.arraycopy(buffers, 0, buf, 0, byteReads);

			double power1 = g1.getPower(buf, 8000, 500);
			double power2 = g2.getPower(buf, 8000, 500);

			boolean valid = true;
			if (power1 < power2 * 0.398) {
				valid = false;
			}
			if (power2 < power1 * 0.5) {
				valid = false;
			}
			if (valid) {
				System.out.println("power1=" + power1 + " power2=" + power2);
			}

			byteReads = inputStream.read(buffers, 0, stepSize);
		}

	}
}
