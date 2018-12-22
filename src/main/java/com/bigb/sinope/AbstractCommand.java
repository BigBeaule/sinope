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
	 * The {@link SinopeCommand}.
	 */
	private final SinopeCommand name;

	/**
	 * @param name The {@link SinopeCommand}.
	 */
	public AbstractCommand(SinopeCommand name) {
		this.name = name;
	}

	@Override
	public final String getName() {
		return this.name.getCommandName();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.getName();
	}
}
