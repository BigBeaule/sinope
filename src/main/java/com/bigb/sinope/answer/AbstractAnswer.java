package com.bigb.sinope.answer;

import com.bigb.sinope.AbstractCommand;

/**
 * Base implementation of a {@link CommandAnswer}.
 * 
 * @author Francis Beaule
 *
 */
public abstract class AbstractAnswer extends AbstractCommand implements CommandAnswer {
    /**
     * @param name The command name for logging and answer purpose.
     */
    public AbstractAnswer(String name) {
        super(name);
    }

    /**
     * Checks if the payload size is valid for this type of answer.
     * 
     * @param payloadSize The answer payload size unsigned short which doesn't contains the size field length.
     * @return True if valid and false otherwise.
     */
    protected abstract boolean isAnswerPayloadSizeValid(int payloadSize);

    @Override
    public final boolean isPayloadSizeValid(int payloadSize) {
        return isAnswerPayloadSizeValid(payloadSize - 2);
    }
}
