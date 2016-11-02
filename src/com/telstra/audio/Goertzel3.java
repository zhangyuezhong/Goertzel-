package com.telstra.audio;

public class Goertzel3 {

	double w = 0.0;
	double cosine = 0.0;
	double sine = 0.0;
	double coeff = 0.0;

	double Q0 = 0.0;
	double Q1 = 0.0;
	double Q2 = 0.0;

	public Goertzel3(double sample_rate, double target_freq) {
		double N = 205;
		int k = (int) (0.5 + ((N * target_freq) / sample_rate));
		
		System.out.println("k=" + k);
		w = ((2.0 * Math.PI) / N) * k;
		System.out.println("W = " + w);
		cosine = Math.cos(w);
		sine = Math.sin(w);
		coeff = 2.0 * cosine;
	}

	public double getPower(byte[] samples, double sample_rate, double target_freq) {
		Q0 = 0.0;
		Q1 = 0.0;
		Q2 = 0.0;
		for (byte b : samples) {
			Q0 = coeff * Q1 - Q2 + (double) b;
			Q2 = Q1;
			Q1 = Q0;
		}
		// double real = (Q1 - Q2 * cosine);
		// double imag = (Q2 * sine);
		// double magnitude = (real * real) + (imag * imag);
		// return magnitude;
		return Q1 * Q1 + Q2 * Q2 - Q1 * Q2 * coeff;
	}
}
