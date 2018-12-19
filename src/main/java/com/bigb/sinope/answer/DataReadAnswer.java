package com.bigb.sinope.answer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import com.bigb.sinope.JsonFields;
import com.bigb.sinope.JsonObjectWriter;
import com.bigb.sinope.SinopeBadFormatException;
import com.bigb.sinope.SinopeDataInputStream;
import com.bigb.sinope.request.DataReadCommand;

/**
 * Base implementation of a request reading data from a device.
 * 
 * @author Francis Beaule
 *
 */
public final class DataReadAnswer extends AbstractAnswer {
    /**
     * The unsigned short data size.
     */
    private final short resultSize;

    /**
     * The unsigned short data size.
     */
    private final short appDataSize;

    /**
     * The name of the value field in the JSON object.
     */
    private final JsonFields field;

    /**
     * The value handler.
     */
    private final ValueHandler valueHandler;

    /**
     * @param name The command name for logging and answer purpose.
     */
    private DataReadAnswer(String name, JsonFields field, int resultSize, ValueHandler valueHandler) {
        super(name);
        this.field = field;
        this.resultSize = (short) resultSize;
        this.valueHandler = valueHandler;
        this.appDataSize = (short) (this.resultSize + 5);
    }

    @Override
    public final int getCommandId() {
        return 0x0241;
    }

    @Override
    protected int getDataSize() {
        return 12 + this.appDataSize;
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
        if (this.appDataSize != aSize) {
            throw new SinopeBadFormatException(
                    "Expected application data size of " + this.appDataSize + " instead got " + aSize);
        }

        long dataId = stream.readUnsignedInt();
        short dataSize = stream.readUnsignedByte();
        if (this.resultSize != dataSize) {
            throw new SinopeBadFormatException("Expected data size of " + this.resultSize + " for data ID "
                    + Long.toHexString(dataId) + " instead got " + dataSize);
        }

        json.add(this.field, this.valueHandler.handleValue(stream.readBytes(this.resultSize)));
    }

    /**
     * Creates a new {@link DataReadAnswer} to read a room temperature.
     * 
     * @return The {@link DataReadCommand}.
     */
    public static DataReadAnswer newReadRoomTemperature() {
        return new DataReadAnswer("Read Room Temperature", JsonFields.TEMPERATURE, 2,
                v -> new BigDecimal(v.doubleValue() / 100.0));
    }

    /**
     * Creates a new {@link DataReadAnswer} to read a room set point (I.E. desired temperature).
     * 
     * @return The {@link DataReadCommand}.
     */
    public static DataReadAnswer newReadRoomSetPoint() {
        return new DataReadAnswer("Read Room Set Point", JsonFields.SET_POINT, 2,
                v -> new BigDecimal(v.doubleValue() / 100.0));
    }

    /**
     * Creates a new {@link DataReadAnswer} to read a room heat level.
     * 
     * @return The {@link DataReadCommand}.
     */
    public static DataReadAnswer newRoomHeatLevel() {
        return new DataReadAnswer("Read Room Heat Level", JsonFields.HEAT_LVL, 1, v -> new BigDecimal(v));
    }

    /**
     * Creates a new {@link DataReadAnswer} to read a room load value.
     * 
     * @return The {@link DataReadCommand}.
     */
    public static DataReadAnswer newRoomLoadValue() {
        return new DataReadAnswer("Read Room Load Value", JsonFields.LOAD, 2, v -> new BigDecimal(v));
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
}
