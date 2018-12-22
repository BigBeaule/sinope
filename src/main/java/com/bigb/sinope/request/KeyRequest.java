package com.bigb.sinope.request;

import java.io.IOException;
import java.math.BigInteger;

import com.bigb.sinope.SinopeCommand;
import com.bigb.sinope.SinopeDataWriter;

/**
 * Request to generate an authentication key.
 * 
 * @author Francis Beaule
 *
 */
public class KeyRequest extends AbstractRequest {
	/**
	 * The unsigned long gateway ID.
	 */
	private final BigInteger gatewayId;

	/**
	 * @param gatewayId The unsigned long gateway ID.
	 */
	public KeyRequest(String gatewayId) {
		this(SinopeCommand.AUTH_KEY, gatewayId);
	}

	/**
	 * @param name The {@link SinopeCommand}.
	 * @param gatewayId The unsigned long gateway ID.
	 */
	protected KeyRequest(SinopeCommand name, String gatewayId) {
		super(name);
		this.gatewayId = new BigInteger(gatewayId, 16);
	}

	@Override
	public int getCommandId() {
		return 0x010A;
	}

	@Override
	protected int getDataSize() {
		return 8;
	}

	@Override
	protected void writeData(SinopeDataWriter writer) throws IOException {
		writer.writeLong(this.gatewayId);
	}
}
