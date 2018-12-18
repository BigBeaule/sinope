package com.bigb.sinope;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * A data input stream to read Sinope answers in order to convert the little endian format to big
 * endian.
 * 
 * @author Francis Beaule
 *
 */
public class SinopeDataInputStream {
    /**
     * The stream to read from.
     */
    private final DataInputStream stream;

    /**
     * @param stream The stream to read from.
     */
    public SinopeDataInputStream(DataInputStream stream) {
        this.stream = stream;
    }

    /**
     * @param bytes The number of bytes to skip.
     * @throws IOException Error when reading.
     */
    public void skip(long bytes) throws IOException {
        this.stream.skip(bytes);
    }

    /**
     * Reads a signed byte.
     * 
     * @return The signed byte.
     * @throws IOException Error when reading.
     */
    public byte readByte() throws IOException {
        return this.stream.readByte();
    }

    /**
     * Reads a little endian unsigned short and convert it to big endian.
     * 
     * @return The unsigned short.
     * @throws IOException Error when reading.
     */
    public short readUnsignedByte() throws IOException {
        return (short) this.stream.readUnsignedByte();
    }

    /**
     * Reads a little endian unsigned short and convert it to big endian.
     * 
     * @return The unsigned short.
     * @throws IOException Error when reading.
     */
    public int readUnsignedShort() throws IOException {
        return (Integer.reverseBytes(this.stream.readUnsignedShort()) >>> 16) & 0xFFFF;
    }

    /**
     * Reads a little endian unsigned long and convert it to big endian.
     * 
     * @return The unsigned long.
     * @throws IOException Error when reading.
     */
    public long readUnsignedInt() throws IOException {
        return readBytes(4).longValue();
    }

    /**
     * Reads a little endian unsigned long and convert it to big endian.
     * 
     * @return The unsigned long.
     * @throws IOException Error when reading.
     */
    public BigInteger readUnsignedLong() throws IOException {
        return readBytes(8);
    }

    /**
     * Reads a little endian unsigned value and convert it to big endian {@link BigInteger}.
     * 
     * @param size The size in bytes to read.
     * @return The unsigned {@link BigInteger}.
     * @throws IOException Error when reading.
     */
    public BigInteger readBytes(int size) throws IOException {
        byte[] bytes = new byte[size];

        for (int i = bytes.length - 1; i >= 0; i--) {
            bytes[i] = this.stream.readByte();
        }

        return new BigInteger(bytes);
    }
}
