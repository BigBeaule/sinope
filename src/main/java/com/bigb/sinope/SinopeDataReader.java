package com.bigb.sinope;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.json.JsonObject;
import com.bigb.sinope.answer.CommandAnswer;
import com.bigb.sinope.answer.DataReadAnswer;
import com.bigb.sinope.answer.DatalessAnswer;
import com.bigb.sinope.answer.KeyAnswer;
import com.bigb.sinope.answer.LoginAnswer;

/**
 * Reader for Sinope answers.
 * 
 * @author Francis Beaule
 *
 */
public class SinopeDataReader {
    /**
     * The stream to read from.
     */
    private final SinopeDataInputStream stream;

    /**
     * @param stream The stream to read from.
     */
    SinopeDataReader(InputStream stream) {
        this.stream = new SinopeDataInputStream(new DataInputStream(stream));
    }

    /**
     * Reads a stream which should contains a well formatted message.
     * 
     * @return A JSON object if the stream contains a well formatted message.
     * @throws IOException Error when reading.
     * @throws SinopeBadFormatException Format error.
     */
    JsonObject read() throws IOException, SinopeBadFormatException {
        // TODO Needs to be able to reuse the stream when a SinopeBadFormatException occurs...

        /*-
         Command message content is:
         Preamble: 		0x55
         Frame control: 0x00
         Payload size: 	2 bytes
         Command: 		2 bytes
         Data: 			X bytes
         CRC: 			1 byte
        */
        if (this.stream.readByte() != SinopeConstants.PREAMBLE) {
            throw SinopeBadFormatException.MISSING_PREAMBLE;
        }
        if (this.stream.readByte() != SinopeConstants.FRAME_CTRL) {
            throw SinopeBadFormatException.MISSING_FRAME_CTRL;
        }

        int payloadSize = this.stream.readUnsignedShort();
        int command = this.stream.readUnsignedShort();

        CommandAnswer cmd = COMMANDS.get(command);
        if (cmd == null) {
            throw new SinopeBadFormatException("Unknown command %s!", command);
        }

        if (!cmd.isPayloadSizeValid(payloadSize)) {
            throw new SinopeBadFormatException(
                    "Invalid payload size of " + payloadSize + " for command " + cmd.getName());
        }

        JsonObjectWriter json = new JsonObjectWriter();
        json.add(JsonFields.CMD, cmd.getName());
        cmd.readAnswer(this.stream, json);

        // Skip CRC-8
        this.stream.skip(1);

        return json.complete();
    }

    /**
     * @param cmds The commands map to fill
     * @param cmd The command to add
     */
    private static void supportCommand(Map<Integer, CommandAnswer> cmds, CommandAnswer cmd) {
        cmds.put(cmd.getCommandId(), cmd);
    }

    /**
     * The supported commands
     */
    private static final Map<Integer, CommandAnswer> COMMANDS;
    static {
        Map<Integer, CommandAnswer> cmd = new HashMap<>();
        supportCommand(cmd, DatalessAnswer.PING_CMD);
        supportCommand(cmd, new KeyAnswer());
        supportCommand(cmd, new LoginAnswer());
        supportCommand(cmd, new DataReadAnswer());

        COMMANDS = Collections.unmodifiableMap(cmd);
    }
}
