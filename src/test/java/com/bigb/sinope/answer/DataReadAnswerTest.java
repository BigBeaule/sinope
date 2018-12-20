package com.bigb.sinope.answer;

import javax.json.JsonObject;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import com.bigb.sinope.CommandTestUtils;
import com.bigb.sinope.DataReadStatus;
import com.bigb.sinope.JsonFields;

public class DataReadAnswerTest extends CommandTestUtils {
    private static final long SEQ = 0x12345678;

    @Test
    public void testRoomTemp() {
        JsonObject json = getAnswer(new byte[] { //
                0x07, // Application data size

                0x03, // Command ID
                0x02, //
                0x00, //
                0x00, //

                0x02, // Data size

                (byte) 0x82, // Temperature
                0x07, //

                0x43 // CRC-8
        });

        assertEquals(19.22, json.getJsonNumber(JsonFields.TEMPERATURE.getField()).doubleValue(), 0);
        assertEquals("Read Room Temperature", json.getString(JsonFields.TYPE.getField()));
    }
    
    @Test
    public void testRoomSetPoint() {
        JsonObject json = getAnswer(new byte[] { //
                0x07, // Application data size

                0x08, // Command ID
                0x02, //
                0x00, //
                0x00, //

                0x02, // Data size

                (byte) 0x82, // Set Point
                0x07, //

                0x43 // CRC-8
        });

        assertEquals(19.22, json.getJsonNumber(JsonFields.SET_POINT.getField()).doubleValue(), 0);
        assertEquals("Read Room Set Point", json.getString(JsonFields.TYPE.getField()));
    }

    @Test
    public void testRoomHeatLevel() {
        JsonObject json = getAnswer(new byte[] { //
                0x06, // Application data size

                0x20, // Command ID
                0x02, //
                0x00, //
                0x00, //

                0x01, // Data size

                (byte) 0x45, // Heat Level

                0x43 // CRC-8
        });

        assertEquals(69.00, json.getJsonNumber(JsonFields.HEAT_LVL.getField()).doubleValue(), 0);
        assertEquals("Read Room Heat Level", json.getString(JsonFields.TYPE.getField()));
    }

    @Test
    public void testRoomLoadValue() {
        JsonObject json = getAnswer(new byte[] { //
                0x07, // Application data size

                0x00, // Command ID
                0x0D, //
                0x00, //
                0x00, //

                0x02, // Data size

                (byte) 0xE3, // Load
                0x0D, //

                0x43 // CRC-8
        });

        assertEquals(3555, json.getInt(JsonFields.LOAD.getField()));
        assertEquals("Read Room Load Value", json.getString(JsonFields.TYPE.getField()));
    }

    private static JsonObject getAnswer(byte[] specificData) {
        byte[] data = ArrayUtils.addAll(new byte[] { //
                0x78, // Sequence
                0x56, //
                0x34, //
                0x12, //

                0x0A, // Status

                0x01, // Attempt

                0x00, // Has more

                0x44, // Device ID
                0x04, //
                0x00, //
                0x00, //
        }, specificData);

        JsonObject json = readAnswer(new DataReadAnswer(), data);
        assertEquals(SEQ, json.getJsonNumber(JsonFields.SEQUENCE.getField()).longValue());
        assertFalse(json.getBoolean(JsonFields.MORE.getField()));
        assertEquals(DataReadStatus.DATA_ANSWER, DataReadStatus.getStatus(json.getInt(JsonFields.STATUS.getField())));
        assertEquals(0x0444, json.getInt(JsonFields.DEV_ID.getField()));

        return json;
    }
}
