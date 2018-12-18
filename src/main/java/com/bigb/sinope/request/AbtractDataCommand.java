package com.bigb.sinope.request;

import java.io.IOException;
import com.bigb.sinope.SinopeDataWriter;

/**
 * Base implementation of an {@link AbstractRequest} for request sending data.
 * 
 * @author Francis Beaule
 *
 */
public abstract class AbtractDataCommand extends AbstractRequest {
    /**
     * The unsigned short value for a read command.
     */
    private static final int READ_CMD = 0x0240;

    /**
     * The value of the byte when requesting the command.
     */
    private static final byte REQUEST_CMD = 0x0;

    /**
     * The number of reserved fields.
     */
    private static final int RESERVED_FIELDS = 6;

    /**
     * The value of a reserved field.
     */
    private static final byte RESERVED_FIELD = 0x0;

    /**
     * The unsigned integer sequence ID.
     */
    private final long sequenceId;

    /**
     * The unsigned integer device ID.
     */
    private final long deviceId;

    /**
     * @param name The command name for logging purpose.
     * @param sequenceId The unsigned integer sequence ID.
     * @param deviceId The unsigned integer device ID.
     */
    public AbtractDataCommand(String name, long sequenceId, long deviceId) {
        super(name);
        this.deviceId = deviceId;
        this.sequenceId = sequenceId;
    }

    /**
     * @return The unsigned byte size of the application data.
     */
    protected abstract short getAppDataSize();

    /**
     * Writes the application data of the request.
     * 
     * @param writer The writer to write to.
     * @throws IOException Error when writing.
     */
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
