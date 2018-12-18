package com.bigb.sinope.answer;

import java.io.IOException;
import java.util.Locale;
import javax.json.JsonObjectBuilder;
import com.bigb.sinope.SinopeBadFormatException;
import com.bigb.sinope.SinopeConstants;
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
    protected int getDataSize() {
        return 11;
    }

    @Override
    public void readAnswer(SinopeDataInputStream stream, JsonObjectBuilder json)
            throws IOException, SinopeBadFormatException {

        byte status = stream.readByte();
        switch (status) {
            case 1:
                stream.skip(2); // Skip the backoff
                json.add(SinopeConstants.STATUS, "success");
                json.add("key", stream.readUnsignedLong().toString(16).toUpperCase(Locale.ENGLISH));
                break;
            case 2:
                json.add(SinopeConstants.STATUS, "key deleted");
                break;
            default:
                if (status < 0) {
                    // Authentication failed
                    json.add(SinopeConstants.STATUS, "failed");
                    json.add("timeout", stream.readUnsignedShort());
                } else {
                    throw new SinopeBadFormatException("Invalid status value " + status);
                }

        }
    }
}
