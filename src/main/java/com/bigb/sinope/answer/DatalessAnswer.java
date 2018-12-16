package com.bigb.sinope.answer;

import java.io.DataInputStream;
import java.io.IOException;

import javax.json.JsonObjectBuilder;

public final class DatalessAnswer extends AbstractAnswer {
	public static final CommandAnswer PING_CMD = new DatalessAnswer("Ping", 0x0013);

	private final int cmdId;

	private DatalessAnswer(String name, int cmdId) {
		super(name);
		this.cmdId = cmdId;
	}

	@Override
	public int getCommandId() {
		return this.cmdId;
	}

	@Override
	protected short getDataSize() {
		return 0;
	}

	@Override
	public void readAnswer(DataInputStream stream, JsonObjectBuilder json) throws IOException {
		// Nothing to read	
	}
}
