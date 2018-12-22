package com.bigb.sinope.answer;

import java.io.IOException;

import com.bigb.sinope.JsonFields;
import com.bigb.sinope.JsonObjectWriter;
import com.bigb.sinope.SinopeBadFormatException;
import com.bigb.sinope.SinopeCommand;
import com.bigb.sinope.SinopeDataInputStream;

/**
 * Answer from a device link report notification.
 * 
 * @author Francis Beaule
 *
 */
public class DeviceLinkReport extends AbstractAnswer {

	public DeviceLinkReport() {
		super(SinopeCommand.DEVICE_LINK);
	}

	@Override
	public int getCommandId() {
		return 0x0116;
	}

	@Override
	protected boolean isAnswerPayloadSizeValid(int payloadSize) {
		return payloadSize == 5;
	}

	@Override
	public void readAnswer(SinopeDataInputStream stream, JsonObjectWriter json)
			throws IOException, SinopeBadFormatException {

		byte status = stream.readByte();
		json.add(JsonFields.DEV_ID, stream.readUnsignedInt());
		switch (status) {
		case 0:
			json.add(JsonFields.STATUS, "located");
			break;
		case 1:
			json.add(JsonFields.STATUS, "joined");
			break;
		case -1:
			json.add(JsonFields.STATUS, "left");
			break;
		default:
			throw new SinopeBadFormatException("Unknown status " + status);
		}
	}
}
