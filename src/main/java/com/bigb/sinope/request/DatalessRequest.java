package com.bigb.sinope.request;

import com.bigb.sinope.SinopeDataWriter;

/**
 * Requests that doesn't requires any application data.
 * 
 * @author Francis Beaule
 *
 */
public final class DatalessRequest extends AbstractRequest {
    /**
     * The Ping request.
     */
    public static final CommandRequest PING_CMD = new DatalessRequest("Ping", 0x0012);

    /**
     * The unsigned short command ID.
     */
    private final int cmdId;

    /**
     * @param name The command name for logging purpose.
     * @param cmdId The unsigned short command ID.
     */
    private DatalessRequest(String name, int cmdId) {
        super(name);
        this.cmdId = cmdId;
    }

    @Override
    public int getCommandId() {
        return this.cmdId;
    }

    @Override
    protected int getDataSize() {
        return 0;
    }

    @Override
    protected void writeData(SinopeDataWriter writer) {
        // Nothing to write
    }
}
