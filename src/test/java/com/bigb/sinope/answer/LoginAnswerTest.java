package com.bigb.sinope.answer;

import javax.json.JsonObject;
import org.junit.Test;
import com.bigb.sinope.CommandTestUtils;
import com.bigb.sinope.JsonFields;

public class LoginAnswerTest extends CommandTestUtils {
    @Test
    public void testCommand() {
        byte[] data = new byte[] { //
                0x00, // Status
                
                0x00, // Backoff
                0x00, //
                
                (byte) 0x01, // Major
                (byte) 0x02, // Minor
                (byte) 0x03, // Update
                
                0x67, // Device ID
                0x45, //
                0x23, //
                0x01//
        };

        JsonObject json = readAnswer(new LoginAnswer(), data);
        assertEquals("success", json.getString(JsonFields.STATUS.getField()));
        assertEquals(1, json.getInt(JsonFields.VERSION_MAJOR.getField()));
        assertEquals(2, json.getInt(JsonFields.VERSION_MINOR.getField()));
        assertEquals(3, json.getInt(JsonFields.VERSION_UPDATE.getField()));

        data = new byte[] { //
                (byte) 0xFF, // Status
                
                0x10, // Backoff
                0x0E, //
                
                0x00, // Missing auth key
                0x00, //
                0x00, //
                0x00, //
                0x00, //
                0x00, //
                0x00, //
                0x00//
        };

        json = readAnswer(new LoginAnswer(), data);
        assertEquals("failed", json.getString(JsonFields.STATUS.getField()));
        assertEquals(3600, json.getInt(JsonFields.TIMEOUT.getField()));
        
          data = new byte[] { //
                    (byte) 0xFC, // Status
                    
                    0x10, // Backoff
                    0x0E, //
                    
                    0x00, // Missing auth key
                    0x00, //
                    0x00, //
                    0x00, //
                    0x00, //
                    0x00, //
                    0x00, //
                    0x00//
            };

            json = readAnswer(new LoginAnswer(), data);
            assertEquals("banned", json.getString(JsonFields.STATUS.getField()));
            assertEquals(3600, json.getInt(JsonFields.TIMEOUT.getField()));
    }
}
