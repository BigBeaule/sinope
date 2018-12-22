package com.bigb.sinope.answer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bigb.sinope.JsonFields;
import com.bigb.sinope.JsonObjectWriter;
import com.bigb.sinope.SinopeBadFormatException;
import com.bigb.sinope.SinopeCommand;
import com.bigb.sinope.SinopeDataInputStream;

/**
 * Implementation of a request reading data from a device.
 * 
 * @author Francis Beaule
 *
 */
public final class DataReadAnswer extends AbstractAnswer {
	/**
	 * The supported data types.
	 */
	private static final Map<Long, DataInfo> DATA_TYPES;

	/**
	 * Default constructor.
	 */
	public DataReadAnswer() {
		super(SinopeCommand.DATA_READ);
	}

	@Override
	public final int getCommandId() {
		return 0x0241;
	}

	@Override
	public boolean isAnswerPayloadSizeValid(int payloadSize) {
		return payloadSize >= 12 ? true : false;
	}

	@Override
	public void readAnswer(SinopeDataInputStream stream, JsonObjectWriter json)
			throws IOException, SinopeBadFormatException {

		json.add(JsonFields.SEQUENCE, stream.readUnsignedInt());
		json.add(JsonFields.STATUS, stream.readByte());

		// Skip the attempt number as per documentation.
		stream.skip(1);

		json.add(JsonFields.MORE, stream.readByte() == 1);
		json.add(JsonFields.DEV_ID, stream.readUnsignedInt());

		short aSize = stream.readUnsignedByte();
		if (aSize > 0) {
			long dataId = stream.readUnsignedInt();
			DataInfo dataInfo = DATA_TYPES.get(dataId);

			if (dataInfo == null) {
				throw new SinopeBadFormatException("Unexpected data type %s", dataId);
			}

			short dataSize = stream.readUnsignedByte();
			if (dataInfo.resultSize != dataSize) {
				throw new SinopeBadFormatException("Expected data size of " + dataInfo.resultSize + " for data ID "
						+ Long.toHexString(dataId) + " instead got " + dataSize);
			}

			json.add(JsonFields.TYPE, dataInfo.dataType.getCommandName());
			json.add(dataInfo.field, dataInfo.valueHandler.handleValue(stream.readBytes(dataInfo.resultSize)));
		}
	}

	/**
	 * The supported data information.
	 * 
	 * @author Francis Beaule
	 *
	 */
	private static class DataInfo {
		/**
		 * The data type.
		 */
		private final SinopeCommand dataType;

		/**
		 * The unsigned short data size.
		 */
		private final short resultSize;

		/**
		 * The name of the value field in the JSON object.
		 */
		private final JsonFields field;

		/**
		 * The value handler.
		 */
		private final ValueHandler valueHandler;

		/**
		 * @param dataType The data type
		 * @param field The field in the JSON
		 * @param resultSize The result size
		 * @param valueHandler The value handler
		 */
		private DataInfo(SinopeCommand dataType, JsonFields field, int resultSize, ValueHandler valueHandler) {
			this.field = field;
			this.dataType = dataType;
			this.resultSize = (short) resultSize;
			this.valueHandler = valueHandler;
		}
	}

	/**
	 * Interface describing a handler for the value.
	 * 
	 * @author Francis Beaule
	 *
	 */
	private static interface ValueHandler {
		/**
		 * @param value The answer value
		 * @return The value to return
		 */
		BigDecimal handleValue(BigInteger value);
	}

	/**
	 * {@link ValueHandler} to convert hundreds of Celsius to Celsius.
	 */
	private static final ValueHandler CELSIUS = v -> new BigDecimal(v.doubleValue() / 100.0);

	/**
	 * {@link ValueHandler} to keep the value as is.
	 */
	private static final ValueHandler NO_OP = v -> new BigDecimal(v);

	static {
		Map<Long, DataInfo> map = new HashMap<>();
		map.put(0x0203L, new DataInfo(SinopeCommand.ROOM_TEMPERATURE, JsonFields.TEMPERATURE, 2, CELSIUS));
		map.put(0x0208L, new DataInfo(SinopeCommand.ROOM_SET_POINT, JsonFields.SET_POINT, 2, CELSIUS));
		map.put(0x0220L, new DataInfo(SinopeCommand.ROOM_HEAT_LVL, JsonFields.HEAT_LVL, 1, NO_OP));
		map.put(0x0D00L, new DataInfo(SinopeCommand.ROOM_LOAD, JsonFields.LOAD, 2, NO_OP));
		map.put(0x0204L, new DataInfo(SinopeCommand.OUTDOOR_TEMPERATURE, JsonFields.TEMPERATURE, 2, CELSIUS));
		map.put(0x0700L, new DataInfo(SinopeCommand.AWAY_STATUS, JsonFields.AWAY, 1,
				v -> new BigDecimal(v.intValue() == 2 ? 1 : 0)));
		
		DATA_TYPES = Collections.unmodifiableMap(map);
	}
}
