package com.bigb.sinope.answer;

import java.io.IOException;
import com.bigb.sinope.Command;
import com.bigb.sinope.JsonObjectWriter;
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
     * Checks if the payload size is valid for this type of answer.
     * 
     * @param payloadSize The answer payload size unsigned short.
     * @return True if valid and false otherwise.
     */
    boolean isPayloadSizeValid(int payloadSize);

    /**
     * Reads a data stream a fills a JSON object from the content.
     * 
     * @param stream The stream to read from.
     * @param json The JSON object writer to fill.
     * @throws IOException Error during reading.
     * @throws SinopeBadFormatException Content is not in the expected format.
     */
    void readAnswer(SinopeDataInputStream stream, JsonObjectWriter json) throws IOException, SinopeBadFormatException;
}
