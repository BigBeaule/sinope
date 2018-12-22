package com.bigb.sinope;

/**
 * Interface describing all the Sinope commands sent and received.  
 * 
 * @author Francis Beaule
 *
 */
public interface Command {
    /**
     * @return The {@link SinopeCommand} name.
     */
    String getName();

    /**
     * @return The command identification unsigned short.
     */
    int getCommandId();
}
