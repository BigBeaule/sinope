package com.bigb.sinope;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.math.BigInteger;
import javax.json.JsonObject;
import com.bigb.sinope.answer.CommandAnswer;
import com.bigb.sinope.request.CommandRequest;
import junit.framework.TestCase;

public class CommandTestUtils extends TestCase {
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

            JsonObjectWriter json = new JsonObjectWriter();
            cmd.readAnswer(new SinopeDataInputStream(stream), json);
            return json.complete();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void validateByte(byte[] content, int pos, int value) {
        assertEquals(value, Byte.toUnsignedInt(content[pos]));
    }
    
    protected void validateShort(byte[] content, int pos, int value) {
        byte[] bytes = new byte[2];
        bytes[0] = content[pos + 1];
        bytes[1] = content[pos];
        
        assertEquals(value, new BigInteger(bytes).intValue());
    }

    public static void printContent(byte[] content) {
        for (byte b : content) {
            String hex = Integer.toHexString(Byte.toUnsignedInt(b)).toUpperCase();
            System.out.println(hex.length() == 1 ? "0" + hex : hex);
        }
    }
}
