package com.bigb.sinope;

/**
 * Base implementation of a {@link Command}.
 * 
 * @author Francis Beaule
 *
 */
public abstract class AbstractCommand implements Command {
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
     * @return The size of the payload data.
     */
    protected abstract short getDataSize();

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
