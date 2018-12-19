package com.bigb.sinope.request;

import java.io.IOException;
import com.bigb.sinope.SinopeDataWriter;

/**
 * Requests that read a device data.
 * 
 * @author Francis Beaule
 *
 */
public final class DataReadCommand extends AbstractRequest {
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
     * When sending a read data request, the only application data sent will be the data ID.
     */
    private static final short APP_DATA_SIZE = 4;

    /**
     * The unsigned integer sequence ID.
     */
    private final long sequenceId;

    /**
     * The unsigned integer device ID.
     */
    private final long deviceId;

    /**
     * The unsigned integer data ID.
     */
    private final long dataId;

    /**
     * @param name The command name for logging purpose.
     * @param sequenceId The unsigned integer sequence ID.
     * @param deviceId The unsigned integer device ID.
     * @param dataId The unsigned integer data ID.
     */
    private DataReadCommand(String name, long sequenceId, long deviceId, long dataId) {
        super(name);
        this.dataId = dataId;
        this.deviceId = deviceId;
        this.sequenceId = sequenceId;
    }

    @Override
    public int getCommandId() {
        return 0x0240;
    }

    @Override
    protected int getDataSize() {
        return 20;
    }

    @Override
    protected void writeData(SinopeDataWriter writer) throws IOException {
        writer.writeInt(this.sequenceId);
        writer.writeByte(REQUEST_CMD);

        for (int i = 0; i < RESERVED_FIELDS; i++) {
            writer.writeByte(RESERVED_FIELD);
        }

        writer.writeInt(this.deviceId);
        writer.writeByte(APP_DATA_SIZE);
        writer.writeInt(this.dataId);
    }
    
    /**
     * Creates a new {@link DataReadCommand} to read a room temperature.
     * 
     * @param sequenceId The unsigned integer sequence ID.
     * @param deviceId The unsigned integer device ID.
     * @return The {@link DataReadCommand}.
     */
    public static DataReadCommand newReadRoomTemperature(long sequenceId, long deviceId) {
        return new DataReadCommand("Read Room Temperature", sequenceId, deviceId, 0x0203);
    }
    
    /**
     * Creates a new {@link DataReadCommand} to read a room set point (I.E. desired temperature).
     * 
     * @param sequenceId The unsigned integer sequence ID.
     * @param deviceId The unsigned integer device ID.
     * @return The {@link DataReadCommand}.
     */
    public static DataReadCommand newReadRoomSetPoint(long sequenceId, long deviceId) {
        return new DataReadCommand("Read Room Set Point", sequenceId, deviceId, 0x0208);
    }
    
    /**
     * Creates a new {@link DataReadCommand} to read a room heat level.
     * 
     * @param sequenceId The unsigned integer sequence ID.
     * @param deviceId The unsigned integer device ID.
     * @return The {@link DataReadCommand}.
     */
    public static DataReadCommand newRoomHeatLevel(long sequenceId, long deviceId) {
        return new DataReadCommand("Read Room Heat Level", sequenceId, deviceId, 0x0220);
    }
    
    /**
     * Creates a new {@link DataReadCommand} to read a room load value.
     * 
     * @param sequenceId The unsigned integer sequence ID.
     * @param deviceId The unsigned integer device ID.
     * @return The {@link DataReadCommand}.
     */
    public static DataReadCommand newRoomLoadValue(long sequenceId, long deviceId) {
        return new DataReadCommand("Read Room Load Value", sequenceId, deviceId, 0x0D00);
    }
}
