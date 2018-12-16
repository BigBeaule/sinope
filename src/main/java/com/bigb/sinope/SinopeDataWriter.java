package com.bigb.sinope;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

import org.apache.commons.lang3.ArrayUtils;

public class SinopeDataWriter {
	private final DataOutputStream content;

	private final CRC8 crc = new CRC8();

	public SinopeDataWriter(OutputStream stream) {
		this.content = new DataOutputStream(stream);
	}

	public void writeByte(short value) throws IOException {
		this.content.write(value);
		this.crc.updateCrc(value);
	}

	public void writeShort(int value) throws IOException {
		int reversedValue = Integer.reverseBytes(value) >> 16;
		this.content.writeShort(reversedValue);
		this.crc.updateCrc(reversedValue);
	}

	public void writeInt(long value) throws IOException {
		long reversedValue = Long.reverseBytes(value) >> 32;
		this.content.write((int) reversedValue);
		this.crc.updateCrc(reversedValue);
	}

	public void writeLong(BigInteger value) throws IOException {
		byte[] bytes = value.toByteArray();
		ArrayUtils.reverse(bytes);

		for (byte b : bytes) {
			this.content.write(b);
			this.crc.updateCrc(b);
		}
	}

	public void complete() throws IOException {
		this.content.write(this.crc.getCrc());
		this.content.flush();
	}
}
