package com.bigb.sinope.answer;

import java.io.IOException;
import javax.json.JsonObjectBuilder;
import com.bigb.sinope.Command;
import com.bigb.sinope.SinopeBadFormatException;
import com.bigb.sinope.SinopeDataInputStream;

/**
 * Extension of a {@link Command} for Sinope answers.
 * 
 * @author Francis Beaule
 *
 */
public interface CommandAnswer extends Command {
    /**
     * Reads a data stream a fills a JSON object from the content.
     * 
     * @param stream The stream to read from.
     * @param json The JSON object builder to fill.
     * @throws IOException Error during reading.
     * @throws SinopeBadFormatException Content is not in the expected format.
     */
    void readAnswer(SinopeDataInputStream stream, JsonObjectBuilder json) throws IOException, SinopeBadFormatException;
}
