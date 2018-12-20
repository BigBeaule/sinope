package com.bigb.sinope;

import java.util.Locale;

/**
 * Sinope exception for bad format message.
 * 
 * @author Francis Beaule
 *
 */
public class SinopeBadFormatException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 402542487690171899L;

    /**
     * Preamble is missing from the message.
     */
    public static final SinopeBadFormatException MISSING_PREAMBLE =
            new SinopeBadFormatException("Missing preambe (0x55) as first byte!");

    /**
     * Frame control is missing from the message.
     */
    public static final SinopeBadFormatException MISSING_FRAME_CTRL =
            new SinopeBadFormatException("Missing frame controle (0x00) as second byte!");

    /**
     * @param msg The exception message
     */
    public SinopeBadFormatException(String msg) {
        super(msg);
    }

    /**
     * @param msg The exception message
     * @param hexValue The hex value to display in the message in a pretty format
     */
    public SinopeBadFormatException(String msg, int hexValue) {
        this(String.format(msg,
                "0x" + (Integer.toHexString(hexValue).length() == 1
                        ? "0" + Integer.toHexString(hexValue).toUpperCase(Locale.ENGLISH)
                        : Integer.toHexString(hexValue).toUpperCase(Locale.ENGLISH))));
    }

    /**
     * @param msg The exception message
     * @param hexValue The hex value to display in the message in a pretty format
     */
    public SinopeBadFormatException(String msg, long hexValue) {
        this(String.format(msg,
                "0x" + (Long.toHexString(hexValue).length() == 1
                        ? "0" + Long.toHexString(hexValue).toUpperCase(Locale.ENGLISH)
                        : Long.toHexString(hexValue).toUpperCase(Locale.ENGLISH))));
    }
}
