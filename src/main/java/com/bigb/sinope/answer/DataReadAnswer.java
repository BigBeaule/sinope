package com.bigb.sinope.answer;

import java.io.IOException;
import java.math.BigInteger;
import javax.json.JsonObjectBuilder;
import com.bigb.sinope.SinopeBadFormatException;
import com.bigb.sinope.SinopeConstants;
import com.bigb.sinope.SinopeDataInputStream;

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
    private final String field;

    /**
     * The value handler.
     */
    private final ValueHandler valueHandler;

    /**
     * @param name The command name for logging and answer purpose.
     */
    private DataReadAnswer(String name, String field, short resultSize, ValueHandler valueHandler) {
        super(name);
        this.field = field;
        this.resultSize = resultSize;
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
    public void readAnswer(SinopeDataInputStream stream, JsonObjectBuilder json)
            throws IOException, SinopeBadFormatException {

        json.add("sequence", stream.readUnsignedInt());
        json.add(SinopeConstants.STATUS, stream.readByte());

        // Skip the attempt number as per documentation.
        stream.skip(1);

        json.add("hasMore", stream.readByte() == 1);
        json.add("deviceId", stream.readUnsignedInt());

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
        long handleValue(BigInteger value);
    }
}
