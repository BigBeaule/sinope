package com.bigb.sinope.answer;

import java.io.IOException;
import com.bigb.sinope.JsonFields;
import com.bigb.sinope.JsonObjectWriter;
import com.bigb.sinope.SinopeBadFormatException;
import com.bigb.sinope.SinopeDataInputStream;

/**
 * A answer to login on the gateway.
 * 
 * @author Francis Beaule
 *
 */
public class LoginAnswer extends AbstractAnswer {
    /**
     * Default constructor.
     */
    public LoginAnswer() {
        super("Login");
    }

    @Override
    public int getCommandId() {
        return 0x0111;
    }
    
    @Override
    public boolean isAnswerPayloadSizeValid(int payloadSize) {
        return payloadSize == 11;
    }

    @Override
    public void readAnswer(SinopeDataInputStream stream, JsonObjectWriter json)
            throws IOException, SinopeBadFormatException {

        byte status = stream.readByte();
        switch (status) {
            case 0:
                stream.skip(2); // Skip the backoff
                json.add(JsonFields.STATUS, "success");
                json.add(JsonFields.VERSION_MAJOR, stream.readUnsignedByte());
                json.add(JsonFields.VERSION_MINOR, stream.readUnsignedByte());
                json.add(JsonFields.VERSION_UPDATE, stream.readUnsignedByte());
                break;
            case -1:
                readFailure(stream, json, "failed");
                break;
            case -2:
                readFailure(stream, json, "N/A : -2");
                break;
            case -3:
                readFailure(stream, json, "N/A : -3");
                break;
            case -4:
                readFailure(stream, json, "banned");
                break;
            default:
                throw new SinopeBadFormatException("Invalid status value " + status);
        }
        
        // Skipping the device ID
        stream.skip(4);
    }

    /**
     * Read a failure.
     * 
     * @param stream The stream to read from.
     * @param json The JSON object builder to fill.
     * @param reason The failure reason
     * @throws IOException Error during reading.
     */
    private static void readFailure(SinopeDataInputStream stream, JsonObjectWriter json, String reason)
            throws IOException {

        json.add(JsonFields.STATUS, reason);
        json.add(JsonFields.TIMEOUT, stream.readUnsignedShort());
    }
}
