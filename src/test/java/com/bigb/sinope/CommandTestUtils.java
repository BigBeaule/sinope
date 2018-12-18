package com.bigb.sinope;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import com.bigb.sinope.answer.CommandAnswer;
import com.bigb.sinope.request.CommandRequest;

public class CommandTestUtils {
    protected byte[] runRequest(CommandRequest cmd) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            cmd.sendRequest(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected JsonObject readAnswer(CommandAnswer cmd, byte[] data) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
                DataInputStream stream = new DataInputStream(bais);) {

            JsonObjectBuilder json = Json.createObjectBuilder();
            cmd.readAnswer(new SinopeDataInputStream(stream), json);
            return json.build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void validateByte(byte[] content, int pos, int value) {
        assertEquals(value, Byte.toUnsignedInt(content[pos]));
    }

    public static void printContent(byte[] content) {
        for (byte b : content) {
            String hex = Integer.toHexString(Byte.toUnsignedInt(b)).toUpperCase();
            System.out.println(hex.length() == 1 ? "0" + hex : hex);
        }
    }
}
