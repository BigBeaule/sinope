package com.bigb.sinope.request;

import java.io.IOException;
import java.io.OutputStream;
import com.bigb.sinope.Command;

/**
 * Extension of a {@link Command} for Sinope requests.
 * 
 * @author Francis Beaule
 *
 */
public interface CommandRequest extends Command {
    /**
     * Sends a request on a given stream.
     * 
     * @param stream The stream to write to.
     * @throws IOException Error during writing.
     */
    void sendRequest(OutputStream stream) throws IOException;
}
