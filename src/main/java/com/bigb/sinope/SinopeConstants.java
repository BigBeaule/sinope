package com.bigb.sinope;

/**
 * Sinope constants shared across multiple classes.
 * 
 * @author Francis Beaule
 *
 */
public interface SinopeConstants {
    /**
     * The frame preamble which is always the first byte of a request and answer.
     */
    byte PREAMBLE = 0x55;
    
    /**
     * The frame control which is always the second byte of a request and answer.
     */
    byte FRAME_CTRL = 0x00;
}
