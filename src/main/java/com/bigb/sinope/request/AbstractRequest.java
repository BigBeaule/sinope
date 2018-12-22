package com.bigb.sinope.request;

import java.io.IOException;

import com.bigb.sinope.AbstractCommand;
import com.bigb.sinope.SinopeCommand;
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
     * @param name {@link SinopeCommand}.
     */
    public AbstractRequest(SinopeCommand name) {
        super(name);
    }

    /**
     * Writes the data of the request.
     * 
     * @param writer The writer to write to.
     * @throws IOException Error when writing.
     */
    protected abstract void writeData(SinopeDataWriter writer) throws IOException;

    /**
     * @return The unsigned short size of the payload data.
     */
    protected abstract int getDataSize();

    @Override
    public final int getPayloadSize() {
        // Payload size include the command size
        return 2 + this.getDataSize();
    }

    @Override
    public final void sendRequest(SinopeDataWriter writer) throws IOException {
        /*-
         Command message content is:
         Preamble: 		0x55
         Frame control: 0x00
         Payload size: 	2 bytes
         Command: 		2 bytes
         Data: 			X bytes
         CRC: 			1 byte
        */

        writer.writeByte(SinopeConstants.PREAMBLE);
        writer.writeByte(SinopeConstants.FRAME_CTRL);
        writer.writeShort(this.getPayloadSize());
        writer.writeShort(this.getCommandId());
        this.writeData(writer);
        writer.complete();
    }
}
