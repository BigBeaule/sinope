package com.bigb.sinope.request;

import java.io.IOException;
import java.io.OutputStream;
import com.bigb.sinope.AbstractCommand;
import com.bigb.sinope.SinopeConstants;
import com.bigb.sinope.SinopeDataWriter;

/**
 * Base implementation of a {@link CommandRequest}
 * 
 * @author Francis Beaule
 *
 */
public abstract class AbstractRequest extends AbstractCommand implements CommandRequest {
    /**
     * @param name The command name for logging purpose.
     */
    public AbstractRequest(String name) {
        super(name);
    }

    /**
     * Writes the data of the request.
     * 
     * @param writer The writer to write to.
     * @throws IOException Error when writing.
     */
    protected abstract void writeData(SinopeDataWriter writer) throws IOException;

    @Override
    public final void sendRequest(OutputStream stream) throws IOException {
        /*-
         Command message content is:
         Preamble: 		0x55
         Frame control: 0x00
         Payload size: 	2 bytes
         Command: 		2 bytes
         Data: 			X bytes
         CRC: 			1 byte
        */

        SinopeDataWriter writer = new SinopeDataWriter(stream);
        writer.writeByte(SinopeConstants.PREAMBLE);
        writer.writeByte(SinopeConstants.FRAME_CTRL);
        writer.writeShort(this.getPayloadSize());
        writer.writeShort(this.getCommandId());
        this.writeData(writer);
        writer.complete();
    }
}
