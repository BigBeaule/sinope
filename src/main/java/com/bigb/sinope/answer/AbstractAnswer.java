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
}
