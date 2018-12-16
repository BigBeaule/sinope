package com.bigb.sinope;

public interface Command {
	String getName();
	
	int getCommandId();
	
	int getPayloadSize();
}
