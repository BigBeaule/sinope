package com.bigb.sinope;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.json.JsonObject;
import com.bigb.sinope.answer.CommandAnswer;
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
     * Reads a stream which should contains a well formatted message.
     * 
     * @param stream The stream to read from.
     * @return A JSON object if the stream contains a well formatted message.
     * @throws IOException Error when reading.
     * @throws SinopeBadFormatException Format error.
     */
    public static JsonObject read(DataInputStream stream) throws IOException, SinopeBadFormatException {
        SinopeDataInputStream sinopeStream = new SinopeDataInputStream(stream);
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
        if (sinopeStream.readByte() != SinopeConstants.PREAMBLE) {
            throw SinopeBadFormatException.MISSING_PREAMBLE;
        }
        if (sinopeStream.readByte() != SinopeConstants.FRAME_CTRL) {
            throw SinopeBadFormatException.MISSING_FRAME_CTRL;
        }
        
        int payloadSize = sinopeStream.readUnsignedShort();
        int command = sinopeStream.readUnsignedShort();

        CommandAnswer cmd = COMMANDS.get(command);
        if (cmd == null) {
            throw new SinopeBadFormatException("Unknown command %s!", command);
        }

        if (cmd.getPayloadSize() != payloadSize) {
            throw new SinopeBadFormatException(
                    "Expected payload size of " + cmd.getPayloadSize() + " instead got " + payloadSize);
        }

        JsonObjectWriter json = new JsonObjectWriter();
        json.add(JsonFields.CMD, cmd.getName());
        cmd.readAnswer(sinopeStream, json);
        
        // Skip CRC-8
        sinopeStream.skip(1);
        
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

        COMMANDS = Collections.unmodifiableMap(cmd);
    }
}
