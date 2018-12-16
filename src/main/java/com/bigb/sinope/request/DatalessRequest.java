package com.bigb.sinope.request;

import com.bigb.sinope.SinopeDataWriter;

public final class DatalessRequest extends AbstractRequest {
	public static final CommandRequest PING_CMD = new DatalessRequest("Ping", 0x0012);

	private final int cmdId;

	private DatalessRequest(String name, int cmdId) {
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
	protected void writeData(SinopeDataWriter writer) {
		// Nothing to write
	}
}
