package com.bigb.sinope.request;

import java.io.IOException;
import com.bigb.sinope.Command;
import com.bigb.sinope.SinopeDataWriter;

/**
 * Extension of a {@link Command} for Sinope requests.
 * 
 * @author Francis Beaule
 *
 */
public interface CommandRequest extends Command {
    /**
     * @return The command payload size unsigned short.
     */
    int getPayloadSize();

    /**
     * Sends a request on a given stream.
     * 
     * @param writer The writer to write to.
     * @throws IOException Error during writing.
     */
    void sendRequest(SinopeDataWriter writer) throws IOException;
}
