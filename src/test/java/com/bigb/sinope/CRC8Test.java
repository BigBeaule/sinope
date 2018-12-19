package com.bigb.sinope;

import org.junit.Test;
import junit.framework.TestCase;

public class CRC8Test extends TestCase {
	@Test
	public void testCRC() {
		CRC8 crc = new CRC8();
		crc.updateCrc((byte) 0x55);
		crc.updateCrc((byte) 0x00);
		crc.updateCrc((byte) 0x0A);
		crc.updateCrc((byte) 0x00);
		crc.updateCrc((byte) 0x0A);
		crc.updateCrc((byte) 0x01);
		crc.updateCrc((byte) 0xEF);
		crc.updateCrc((byte) 0xCD);
		crc.updateCrc((byte) 0xAB);
		crc.updateCrc((byte) 0x89);
		crc.updateCrc((byte) 0x67);
		crc.updateCrc((byte) 0x45);
		crc.updateCrc((byte) 0x23);
		crc.updateCrc((byte) 0x01);

		// Expect DA > 218
		assertEquals(0xDA, Byte.toUnsignedInt(crc.getCrc()));
		
		crc = new CRC8();
		crc.updateCrc((byte) 0x55);
		crc.updateCrc((byte) 0x00);
		crc.updateCrc((byte) 0x16);
		crc.updateCrc((byte) 0x00);
		crc.updateCrc((byte) 0x40);
		crc.updateCrc((byte) 0x02);
		crc.updateCrc((byte) 0x78);
		crc.updateCrc((byte) 0x56);
		crc.updateCrc((byte) 0x34);
		crc.updateCrc((byte) 0x12);
		crc.updateCrc((byte) 0x00);
		
		crc.updateCrc((byte) 0x00);
		crc.updateCrc((byte) 0x00);
		crc.updateCrc((byte) 0x00);
		crc.updateCrc((byte) 0x00);
		crc.updateCrc((byte) 0x00);
		crc.updateCrc((byte) 0x00);
		
		crc.updateCrc((byte) 0x44);
		crc.updateCrc((byte) 0x04);
		crc.updateCrc((byte) 0x00);
		crc.updateCrc((byte) 0x00);
		crc.updateCrc((byte) 0x04);
		crc.updateCrc((byte) 0x03);
		crc.updateCrc((byte) 0x02);
		crc.updateCrc((byte) 0x00);
		crc.updateCrc((byte) 0x00);
		
		// Expect 29 > 41
		assertEquals(0x29, Byte.toUnsignedInt(crc.getCrc()));
	}
}
