package com.bigb.sinope.answer;

import java.io.DataInputStream;
import java.io.IOException;
import javax.json.JsonObjectBuilder;

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
    public static final CommandAnswer PING_CMD = new DatalessAnswer("Ping", 0x0013);

    /**
     * The unsigned short command ID.
     */
    private final int cmdId;

    /**
     * @param name The command name for logging purpose.
     * @param cmdId The unsigned short command ID.
     */
    private DatalessAnswer(String name, int cmdId) {
        super(name);
        this.cmdId = cmdId;
    }

    @Override
    public int getCommandId() {
        return this.cmdId;
    }

    @Override
    protected short getDataSize() {
        return 0;
    }

    @Override
    public void readAnswer(DataInputStream stream, JsonObjectBuilder json) throws IOException {
        // Nothing to read
    }
}
