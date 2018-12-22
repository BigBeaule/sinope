package com.bigb.sinope.request;

import java.io.IOException;
import java.math.BigInteger;

import com.bigb.sinope.SinopeCommand;
import com.bigb.sinope.SinopeDataWriter;

/**
 * A request to login on the gateway.
 * 
 * @author Francis Beaule
 *
 */
public class LoginRequest extends KeyRequest {
	/**
	 * The unsigned long authentication key.
	 */
	private final BigInteger authKey;

	/**
	 * @param gatewayId The unsigned long gateway ID.
	 * @param authKey The unsigned long authentication key.
	 */
	public LoginRequest(String gatewayId, String authKey) {
		super(SinopeCommand.LOGIN, gatewayId);
		this.authKey = new BigInteger(authKey, 16);
	}

	@Override
	public int getCommandId() {
		return 0x0110;
	}

	@Override
	protected int getDataSize() {
		return super.getDataSize() + 8;
	}

	@Override
	protected void writeData(SinopeDataWriter writer) throws IOException {
		super.writeData(writer);
		writer.writeLong(this.authKey);
	}
}
