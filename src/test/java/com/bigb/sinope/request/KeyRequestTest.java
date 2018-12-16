package com.bigb.sinope.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bigb.sinope.CommandTestUtils;
import com.bigb.sinope.request.KeyRequest;

public class KeyRequestTest extends CommandTestUtils {
	@Test
	public void testCommand() {
		byte[] content = runRequest(new KeyRequest("0123456789ABCDEF"));

//		CommandTestUtils.printContent(content);
		assertEquals(15, content.length);
		validateByte(content, 0, 0x55);
		validateByte(content, 1, 0x00);
		validateByte(content, 2, 0x0A);
		validateByte(content, 3, 0x00);
		validateByte(content, 4, 0x0A);
		validateByte(content, 5, 0x01);
		validateByte(content, 6, 0xEF);
		validateByte(content, 7, 0xCD);
		validateByte(content, 8, 0xAB);
		validateByte(content, 9, 0x89);
		validateByte(content, 10, 0x67);
		validateByte(content, 11, 0x45);
		validateByte(content, 12, 0x23);
		validateByte(content, 13, 0x01);
		validateByte(content, 14, 0xDA);
	}
}
