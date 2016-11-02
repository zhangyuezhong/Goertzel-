package com.telstra.audio;

/**
 * The Goertzel class can be used to perform the Goertzel algorithm. In order to
 * use this class, four primary steps should be executed: initialize the
 * Goertzel class and all its variables (initGoertzel), process one sample of
 * data at a time (processSample), get the relative magnitude returned by the
 * Goertzle algorithm after N samples have been processed (getMagnitudeSquared,
 * getRealImag), and reset the Goertzel class and all its variables
 * (resetGoertzel).
 * <p>
 * This class is based on a C program implemented by Kevin Banks of Embedded
 * Systems Programming.
 *
 * @author Chris Palistrant, Tony Offer
 * @version 0.0, May 2004
 */
public class Goertzel {

	public double getPower2(byte[] samples, double sampleRate, double targetFrequency) {

		double sPrev = 0.0;
		double sPrev2 = 0.0;
		int i;
		double normalizedfreq = targetFrequency / sampleRate;
		double coeff = 2 * Math.cos(2 * Math.PI * normalizedfreq);
		for (i = 0; i < samples.length; i++) {
			double s = samples[i] + coeff * sPrev - sPrev2;
			sPrev2 = sPrev;
			sPrev = s;
		}
		
		   double WNk = Math.exp(-2 * Math.PI * targetFrequency / sampleRate);
           return 20 * Math.log10(Math.abs((sPrev - WNk * sPrev2)));
		
		//double power = sPrev2 * sPrev2 + sPrev * sPrev - coeff * sPrev * sPrev2;
		//return power;
	}

	public double getPower(byte[] samples, double sampleRate, double targetFrequency) {
		int N = samples.length;
		double K = (N * targetFrequency) / sampleRate;
		double W = (2 * Math.PI * K) / N;

		double C = 2 * Math.cos(W);

		double Q0 = 0;
		double Q1 = 0;
		double Q2 = 0;

		for (int i = 0; i < samples.length; i++) {
			Q2 = Q1;
			Q1 = Q0;
			Q0 = C * Q1 - Q2 + samples[i];

		}

		double P = Math.pow(Q2, 2) + Math.pow(Q1, 2) - C * Q1 * Q2;
		return P;
	}
}