package com.bigb.sinope.answer;

import javax.json.JsonObject;
import org.junit.Test;
import com.bigb.sinope.CommandTestUtils;
import com.bigb.sinope.JsonFields;

public class KeyAnswerTest extends CommandTestUtils {
	@Test
	public void testCommand() {
		byte[] data = new byte[] { //
				0x01, // Status
				
				0x00, // Backoff
				0x00, //
				
				(byte) 0xEF, // Auth key
				(byte) 0xCD, //
				(byte) 0xAB, //
				(byte) 0x89, //
				0x67, //
				0x45, //
				0x23, //
				0x01//
		};

		JsonObject json = readAnswer(new KeyAnswer(), data);
		assertEquals("success", json.getString(JsonFields.STATUS.getField()));
		assertEquals("123456789ABCDEF", json.getString(JsonFields.KEY.getField()));
		
		data = new byte[] { //
				(byte) 0xFF, // Status
				
				0x10, // Backoff
				0x0E, //
				
				0x00, // Missing auth key
				0x00, //
				0x00, //
				0x00, //
				0x00, //
				0x00, //
				0x00, //
				0x00//
		};

		json = readAnswer(new KeyAnswer(), data);
		assertEquals("failed", json.getString(JsonFields.STATUS.getField()));
		assertEquals(3600, json.getInt(JsonFields.TIMEOUT.getField()));
	}
}
