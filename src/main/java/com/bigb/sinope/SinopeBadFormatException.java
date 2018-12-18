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
     * @param msg The message exception
     */
    public SinopeBadFormatException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param hexValue
     */
    public SinopeBadFormatException(String msg, int hexValue) {
        this(String.format(msg,
                Integer.toHexString(hexValue).length() == 1
                        ? "0" + Integer.toHexString(hexValue).toUpperCase(Locale.ENGLISH)
                        : Integer.toHexString(hexValue).toUpperCase(Locale.ENGLISH)));
    }
}
