package com.bigb.sinope;

public abstract class AbstractCommand implements Command {
	private final String name;

	public AbstractCommand(String name) {
		this.name = name;
	}

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
