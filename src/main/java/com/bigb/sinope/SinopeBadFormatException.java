package com.bigb.sinope;

import java.util.Locale;

public class SinopeBadFormatException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 402542487690171899L;

	public static final SinopeBadFormatException MISSING_PREAMBLE = new SinopeBadFormatException(
			"Missing preambe (0x55) as first byte!");

	public static final SinopeBadFormatException MISSING_FRAME_CTRL = new SinopeBadFormatException(
			"Missing frame controle (0x00) as second byte!");

	public SinopeBadFormatException(String msg) {
		super(msg);
	}

	public SinopeBadFormatException(String msg, int hexValue) {
		this(msg + " "
				+ (Integer.toHexString(hexValue).length() == 1
						? "0" + Integer.toHexString(hexValue).toUpperCase(Locale.ENGLISH)
						: Integer.toHexString(hexValue).toUpperCase(Locale.ENGLISH)));
	}
}
