package com.bigb.sinope;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.bigb.sinope.answer.CommandAnswer;
import com.bigb.sinope.answer.DatalessAnswer;

public class SinopeDataReader {
	public static JsonObject read(DataInputStream stream) throws IOException, SinopeBadFormatException {
		/*-
		 Command message content is:
		 Preamble: 		0x55
		 Frame control: 0x00
		 Payload size: 	2 bytes
		 Command: 		2 bytes
		 Data: 			X bytes
		 CRC: 			1 byte
		*/
		if (stream.readByte() != SinopeConstants.PREAMBLE) {
			throw SinopeBadFormatException.MISSING_PREAMBLE;
		}
		if (stream.readByte() != SinopeConstants.FRAME_CTRL) {
			throw SinopeBadFormatException.MISSING_FRAME_CTRL;
		}

		int payloadSize = readShort(stream);
		int command = readShort(stream);

		CommandAnswer cmd = COMMANDS.get(command);
		if (cmd == null) {
			throw new SinopeBadFormatException("Unknown command", command);
		}

		if (cmd.getPayloadSize() != payloadSize) {
			throw new SinopeBadFormatException(
					"Expected payload size of " + cmd.getPayloadSize() + " instead got " + payloadSize);
		}

		JsonObjectBuilder json = Json.createObjectBuilder();
		json.add("command", cmd.getName());
		cmd.readAnswer(stream, json);
		return json.build();
	}

	public static int readShort(DataInputStream stream) throws IOException {
		return (Integer.reverseBytes(stream.readUnsignedShort()) >>> 16) & 0xFFFF;
	}
	
	public static BigInteger readLong(DataInputStream stream) throws IOException {
		byte[] bytes = new byte[8];
		
		for (int i = bytes.length - 1; i >=0; i--) {
			bytes[i] = stream.readByte();
		}
		
		return new BigInteger(bytes);
	}

	private static final Map<Integer, CommandAnswer> COMMANDS;
	static {
		Map<Integer, CommandAnswer> cmd = new HashMap<>();
		cmd.put(DatalessAnswer.PING_CMD.getCommandId(), DatalessAnswer.PING_CMD);

		COMMANDS = Collections.unmodifiableMap(cmd);
	}
}
