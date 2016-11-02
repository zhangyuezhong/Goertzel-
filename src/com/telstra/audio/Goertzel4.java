package com.telstra.audio;

public class Goertzel4 {

	double w = 0.0;
	double cosine = 0.0;
	double sine = 0.0;
	double coeff = 0.0;

	double Q0 = 0.0;
	double Q1 = 0.0;
	double Q2 = 0.0;

	public Goertzel4(double sample_rate, double target_freq, int numberOfSamples) {
		int k = (int) (0.5 +  (target_freq/sample_rate * numberOfSamples));
		w = (2.0 * Math.PI * k / numberOfSamples);
		System.out.println("W = " + w);
		cosine = Math.cos(w);
		sine = Math.sin(w);
		coeff = 2.0 * cosine;
	}
	public double getPower(int[] samples) {
		Q0 = 0.0;
		Q1 = 0.0;
		Q2 = 0.0;
		for (int b : samples) {
			Q0 = coeff * Q1 - Q2 + b;
			Q2 = Q1;
			Q1 = Q0;
		}
		return Q1 * Q1 + Q2 * Q2 - Q1 * Q2 * coeff;
	}
}
