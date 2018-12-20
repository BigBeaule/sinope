package com.bigb.sinope;

import java.io.ByteArrayInputStream;
import javax.json.JsonObject;
import org.junit.Test;

public class SinopeDataReaderTest extends CommandTestUtils {
    private static final long SEQ = 0x12345678;

    @Test
    public void testMultipleRead() throws Exception {
        ByteArrayInputStream stream = new ByteArrayInputStream(new byte[] {//
                //////////////////////////// Acknowledge message //////////////////////////
                0x55, // Preamble
                0x00, // Frame control

                0x0E, // Size
                0x00, //

                0x41, // Command ID
                0x02, //

                0x78, // Sequence
                0x56, //
                0x34, //
                0x12, //

                0x00, // Status

                0x00, // Attempt

                0x01, // Has more

                0x44, // Device ID
                0x04, //
                0x00, //
                0x00, //

                0x00, // Application data size

                0x0D, // CRC-8

                //////////////////////////// Get room temperature message //////////////////////////
                0x55, // Preamble
                0x00, // Frame control

                0x0E, // Size
                0x00, //

                0x41, // Command ID
                0x02, //

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

        SinopeDataReader reader = new SinopeDataReader(stream);
        JsonObject ackMsg = reader.read();
        assertEquals(SEQ, ackMsg.getJsonNumber(JsonFields.SEQUENCE.getField()).longValue());
        assertTrue(ackMsg.getBoolean(JsonFields.MORE.getField()));
        assertEquals(DataReadStatus.WAIT_FOR_ANSWER, DataReadStatus.getStatus(ackMsg.getInt(JsonFields.STATUS.getField())));
        assertEquals(0x0444, ackMsg.getInt(JsonFields.DEV_ID.getField()));
        
        JsonObject tempMsg = reader.read();
        assertEquals(SEQ, tempMsg.getJsonNumber(JsonFields.SEQUENCE.getField()).longValue());
        assertFalse(tempMsg.getBoolean(JsonFields.MORE.getField()));
        assertEquals(DataReadStatus.DATA_ANSWER, DataReadStatus.getStatus(tempMsg.getInt(JsonFields.STATUS.getField())));
        assertEquals(0x0444, tempMsg.getInt(JsonFields.DEV_ID.getField()));
        assertEquals(19.22, tempMsg.getJsonNumber(JsonFields.TEMPERATURE.getField()).doubleValue(), 0);
        assertEquals("Read Room Temperature", tempMsg.getString(JsonFields.TYPE.getField()));
    }
}
