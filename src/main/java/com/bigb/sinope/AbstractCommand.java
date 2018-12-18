package com.bigb.sinope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base implementation of a {@link Command}.
 * 
 * @author Francis Beaule
 *
 */
public abstract class AbstractCommand implements Command {
    /**
     * The logger.
     */
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractCommand.class);
    
    /**
     * The command name for logging and answer purpose.
     */
    private final String name;

    /**
     * @param name The command name for logging and answer purpose.
     */
    public AbstractCommand(String name) {
        this.name = name;
    }

    /**
     * @return The unsigned short size of the payload data.
     */
    protected abstract int getDataSize();

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final int getPayloadSize() {
        // Payload size include the command size
        return 2 + this.getDataSize();
    }
}
