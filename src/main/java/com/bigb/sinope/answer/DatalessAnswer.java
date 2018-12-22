package com.bigb.sinope.answer;

import java.io.IOException;

import com.bigb.sinope.JsonObjectWriter;
import com.bigb.sinope.SinopeCommand;
import com.bigb.sinope.SinopeDataInputStream;

/**
 * Answers that doesn't contains any application data.
 * 
 * @author Francis Beaule
 *
 */
public final class DatalessAnswer extends AbstractAnswer {
	/**
	 * The Ping answer.
	 */
	public static final CommandAnswer PING_CMD = new DatalessAnswer(SinopeCommand.PING, 0x0013);

	/**
	 * The unsigned short command ID.
	 */
	private final int cmdId;

	/**
	 * @param name The {@link SinopeCommand}.
	 * @param cmdId The unsigned short command ID.
	 */
	private DatalessAnswer(SinopeCommand name, int cmdId) {
		super(name);
		this.cmdId = cmdId;
	}

	@Override
	public int getCommandId() {
		return this.cmdId;
	}

	@Override
	public boolean isAnswerPayloadSizeValid(int payloadSize) {
		return payloadSize == 0;
	}

	@Override
	public void readAnswer(SinopeDataInputStream stream, JsonObjectWriter json) throws IOException {
		// Nothing to read
	}
}
