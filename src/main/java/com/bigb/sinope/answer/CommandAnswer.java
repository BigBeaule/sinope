package com.bigb.sinope.answer;

import java.io.DataInputStream;
import java.io.IOException;
import javax.json.JsonObjectBuilder;
import com.bigb.sinope.Command;
import com.bigb.sinope.SinopeBadFormatException;

public interface CommandAnswer extends Command {
    void readAnswer(DataInputStream stream, JsonObjectBuilder json) throws IOException, SinopeBadFormatException;
}
