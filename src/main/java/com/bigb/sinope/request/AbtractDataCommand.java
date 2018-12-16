package com.bigb.sinope.request;

import java.io.IOException;

import com.bigb.sinope.SinopeDataWriter;

public abstract class AbtractDataCommand extends AbstractRequest {
	private static final byte REQUEST_CMD = 0x0;
	private static final int RESERVED_FIELDS = 6;
	private static final byte RESERVED_FIELD = 0x0;

	private static final int READ_CMD = 0x0240;

	private final long sequenceId;

	private final long deviceId;

	public AbtractDataCommand(String name, long sequenceId, long deviceId) {
		super(name);
		this.deviceId = deviceId;
		this.sequenceId = sequenceId;
	}
	
	protected abstract short getAppDataSize();
	
	protected abstract void writeAppData(SinopeDataWriter writer) throws IOException;

	@Override
	protected void writeData(SinopeDataWriter writer) throws IOException {
		writer.writeShort(READ_CMD);
		writer.writeInt(this.sequenceId);
		writer.writeByte(REQUEST_CMD);

		for (int i = 0; i < RESERVED_FIELDS; i++) {
			writer.writeByte(RESERVED_FIELD);
		}
		
		writer.writeInt(this.deviceId);
		writer.writeByte(this.getAppDataSize());
		this.writeAppData(writer);
	}
}
