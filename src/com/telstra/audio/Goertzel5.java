package com.telstra.audio;

public class Goertzel5 {

	public static double getPowewr(int[] samples, double frequency, int samplerate) {

		double Skn, Skn1, Skn2;
		Skn = Skn1 = Skn2 = 0;

		for (int i = 0; i < samples.length; i++) {
			Skn2 = Skn1;
			Skn1 = Skn;
			Skn = 2 * Math.cos(2 * Math.PI * frequency / samplerate) * Skn1 - Skn2 + samples[i];
		}

		double WNk = Math.exp(-2 * Math.PI * frequency / samplerate); // this
																		// one
																		// ignores
																		// complex
																		// stuff
		// float WNk = exp(-2*j*PI*k/N);
		double value = (Skn - WNk * Skn1);
		return 20 * Math.log10(Math.abs(value));
	}
}
