package com.bigb.sinope.request;

import java.io.IOException;
import java.math.BigInteger;

import com.bigb.sinope.SinopeDataWriter;

public class KeyRequest extends AbstractRequest {
	private final BigInteger gatewayId;

	public KeyRequest(String gatewayId) {
		super("Authentication Key");
		this.gatewayId = new BigInteger(gatewayId, 16);
	}

	@Override
	public int getCommandId() {
		return 0x010A;
	}

	@Override
	protected short getDataSize() {
		return 8;
	}

	@Override
	protected void writeData(SinopeDataWriter writer) throws IOException {
		writer.writeLong(this.gatewayId);
	}
}
