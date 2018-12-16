package com.bigb.sinope.answer;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Locale;

import javax.json.JsonObjectBuilder;

import com.bigb.sinope.SinopeBadFormatException;
import com.bigb.sinope.SinopeDataReader;

public class KeyAnswer extends AbstractAnswer {
	private static final String STATUS = "status";

	public KeyAnswer() {
		super("Authentication Key");
	}

	@Override
	public int getCommandId() {
		return 0x010B;
	}

	@Override
	protected short getDataSize() {
		return 11;
	}

	@Override
	public void readAnswer(DataInputStream stream, JsonObjectBuilder json)
			throws IOException, SinopeBadFormatException {
		
		byte status = stream.readByte();
		switch (status) {
		case 1:
			stream.skip(2); // Skip the backoff
			json.add(STATUS, "success");
			json.add("key", SinopeDataReader.readLong(stream).toString(16).toUpperCase(Locale.ENGLISH));
			break;
		case 2:
			json.add(STATUS, "key deleted");
			break;
		default:
			if (status < 0) {
				// Authentication failed
				json.add(STATUS, "failed");
				json.add("timeout", SinopeDataReader.readShort(stream));
			} else {
				throw new SinopeBadFormatException("Invalid status value " + status);
			}

		}
	}
}
