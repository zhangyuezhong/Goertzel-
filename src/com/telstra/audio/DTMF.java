package com.telstra.audio;

/**
 * This interface defines several useful constants used throughout the mobile
 * client code for ultrasound detection.
 *
 * @author Tony Offer
 * @author Chris Palistrant
 * @version 1.0
 */
public interface DTMF {
	public static final double[] DTMF_FREQUENCIES = { 697, 770, 852, 941, 1209, 1336, 1477, 1633 };
	public static final double[] DTMF_ROW = { 1209, 1336, 1477, 1633 };
	public static final double[] DTMF_COL = { 697, 770, 852, 941 };
}