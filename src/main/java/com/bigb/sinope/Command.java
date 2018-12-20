package com.bigb.sinope;

/**
 * Interface describing all the Sinope commands sent and received.  
 * 
 * @author Francis Beaule
 *
 */
public interface Command {
    /**
     * @return The command name for logging and answer purpose.
     */
    String getName();

    /**
     * @return The command identification unsigned short.
     */
    int getCommandId();
}
