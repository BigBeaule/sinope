package com.bigb.sinope.request;

import java.io.IOException;
import java.io.OutputStream;

import com.bigb.sinope.Command;

public interface CommandRequest extends Command {
	void sendRequest(OutputStream stream) throws IOException;
}
