package com.bigb.sinope.answer;

import java.io.IOException;
import java.util.Locale;
import com.bigb.sinope.JsonFields;
import com.bigb.sinope.JsonObjectWriter;
import com.bigb.sinope.SinopeBadFormatException;
import com.bigb.sinope.SinopeDataInputStream;

/**
 * Answer to retrieve an authentication key.
 * 
 * @author Francis Beaule
 *
 */
public class KeyAnswer extends AbstractAnswer {
    /**
     * Default constructor.
     */
    public KeyAnswer() {
        super("Authentication Key");
    }

    @Override
    public int getCommandId() {
        return 0x010B;
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
            case 1:
                stream.skip(2); // Skip the backoff
                json.add(JsonFields.STATUS, "success");
                json.add(JsonFields.KEY, stream.readUnsignedLong().toString(16).toUpperCase(Locale.ENGLISH));
                break;
            case 2:
                json.add(JsonFields.STATUS, "key deleted");
                break;
            default:
                if (status < 0) {
                    // Authentication failed
                    json.add(JsonFields.STATUS, "failed");
                    json.add(JsonFields.TIMEOUT, stream.readUnsignedShort());
                } else {
                    throw new SinopeBadFormatException("Invalid status value " + status);
                }

        }
    }
}
