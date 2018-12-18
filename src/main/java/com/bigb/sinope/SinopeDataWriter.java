package com.bigb.sinope;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Writer for Sinope requests.
 * 
 * @author Francis Beaule
 *
 */
public class SinopeDataWriter {
    /**
     * The content in which to write.
     */
    private final DataOutputStream content;

    /**
     * The ongoing CRC-8.
     */
    private final CRC8 crc = new CRC8();

    /**
     * @param stream The stream in which to write
     */
    public SinopeDataWriter(OutputStream stream) {
        this.content = new DataOutputStream(stream);
    }

    /**
     * Writes an unsigned byte into the stream.
     * 
     * @param value The value to write.
     * @throws IOException Error when writing.
     */
    public void writeByte(short value) throws IOException {
        this.content.write(value);
        this.crc.updateCrc(value);
    }

    /**
     * Writes an unsigned big endian short into the stream as a unsigned little endian short.
     * 
     * @param value The value to write.
     * @throws IOException Error when writing.
     */
    public void writeShort(int value) throws IOException {
        int reversedValue = Integer.reverseBytes(value) >> 16;
        this.content.writeShort(reversedValue);
        this.crc.updateCrc(reversedValue);
    }

    /**
     * Writes an unsigned big endian integer into the stream as a unsigned little endian integer.
     * 
     * @param value The value to write.
     * @throws IOException Error when writing.
     */
    public void writeInt(long value) throws IOException {
        long reversedValue = Long.reverseBytes(value) >> 32;
        this.content.write((int) reversedValue);
        this.crc.updateCrc(reversedValue);
    }

    /**
     * Writes an unsigned big endian long into the stream as a unsigned little endian long.
     * 
     * @param value The value to write.
     * @throws IOException Error when writing.
     */
    public void writeLong(BigInteger value) throws IOException {
        byte[] bytes = value.toByteArray();
        ArrayUtils.reverse(bytes);

        for (byte b : bytes) {
            this.content.write(b);
            this.crc.updateCrc((short) b);
        }
    }

    /**
     * Complete the request and writes the CRC-8 in the stream.
     * 
     * @throws IOException Error when writing.
     */
    public void complete() throws IOException {
        this.content.write(this.crc.getCrc());
        this.content.flush();
    }
}
