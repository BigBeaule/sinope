package com.bigb.sinope.answer;

import static org.junit.Assert.assertEquals;

import javax.json.JsonObject;

import org.junit.Test;

import com.bigb.sinope.CommandTestUtils;

public class KeyAnswerTest extends CommandTestUtils {
	@Test
	public void testCommand() {
		byte[] data = new byte[] { //
				0x01, //
				0x00, //
				0x00, //
				(byte) 0xEF, //
				(byte) 0xCD, //
				(byte) 0xAB, //
				(byte) 0x89, //
				0x67, //
				0x45, //
				0x23, //
				0x01//
		};

		JsonObject json = readAnswer(new KeyAnswer(), data);
		assertEquals("success", json.getString("status"));
		assertEquals("123456789ABCDEF", json.getString("key"));
		
		data = new byte[] { //
				(byte) 0xFF, //
				0x10, //
				0x0E, //
				0x00, //
				0x00, //
				0x00, //
				0x00, //
				0x00, //
				0x00, //
				0x00, //
				0x00//
		};

		json = readAnswer(new KeyAnswer(), data);
		assertEquals("failed", json.getString("status"));
		assertEquals(3600, json.getInt("timeout"));
	}
}
