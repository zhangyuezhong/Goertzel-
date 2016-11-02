package com.telstra.audio;

public class Goertzel6 {

	public static double getPowewr(byte[] samples, double frequency, int samplerate) {

		int N = samples.length;

		int k = (int) (0.5 + ((N * frequency) / samplerate));

		double w = ((2.0 * Math.PI) / N) * k;

		double cosine = Math.cos(w);
		double sine = Math.sin(w);
		double coeff = 2.0 * cosine;

		double Q0 = 0.0;
		double Q1 = 0.0;
		double Q2 = 0.0;
		for (byte b : samples) {
			Q0 = coeff * Q1 - Q2 + (double) b;
			Q2 = Q1;
			Q1 = Q0;
		}

		// return Q1 * Q1 + Q2 * Q2 - Q1 * Q2 * coeff;

		double WNk = Math.exp(-2 * Math.PI * frequency / samplerate);

		double value = (Q0 - WNk * Q1);
		return 20 * Math.log10(Math.abs(value));
	}
}
