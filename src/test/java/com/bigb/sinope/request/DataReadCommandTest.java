package com.bigb.sinope.request;

import org.junit.Test;
import com.bigb.sinope.CommandTestUtils;

public class DataReadCommandTest extends CommandTestUtils {
    @Test
    public void testRoomTemp() {
        DataReadCommand cmd = DataReadCommand.newReadRoomTemperature(0x12345678L, 0x00000444L);
        validateCommand(runRequest(cmd), cmd.getPayloadSize(), 0x29, 0x03, 0x02, 0x00, 0x00);
    }
    
    @Test
    public void testRoomSetPoint() {
        DataReadCommand cmd = DataReadCommand.newReadRoomSetPoint(0x12345678L, 0x00000444L);
        validateCommand(runRequest(cmd), cmd.getPayloadSize(), 0xA3, 0x08, 0x02, 0x00, 0x00);
    }
    
    @Test
    public void testRoomHeatLevel() {
        DataReadCommand cmd = DataReadCommand.newReadRoomHeatLevel(0x12345678L, 0x00000444L);
        validateCommand(runRequest(cmd), cmd.getPayloadSize(), 0xDD, 0x20, 0x02, 0x00, 0x00);
    }
    
    @Test
    public void testRoomLoadValue() {
        DataReadCommand cmd = DataReadCommand.newReadRoomLoadValue(0x12345678L, 0x00000444L);
        validateCommand(runRequest(cmd), cmd.getPayloadSize(), 0x54, 0x00, 0x0D, 0x00, 0x00);
    }
    
    private void validateCommand(byte[] content, int payloadSize, int crc, int... cmdId) {
        assertEquals(27, content.length);
        int pos = 0;
        
        // Start
        validateByte(content, pos++, 0x55);
        validateByte(content, pos++, 0x00);
        
        // Size
        validateShort(content, pos++, payloadSize);
        ++pos;

        // Command
        validateByte(content, pos++, 0x40);
        validateByte(content, pos++, 0x02);
        
        // Sequence
        validateByte(content, pos++, 0x78);
        validateByte(content, pos++, 0x56);
        validateByte(content, pos++, 0x34);
        validateByte(content, pos++, 0x12);
        
        // Request type
        validateByte(content, pos++, 0x00);
        
        // Reserved fields
        for (int i = 0; i < 6; i++) {
            validateByte(content, pos++, 0x00);
        }
        
        // Device ID
        validateByte(content, pos++, 0x44);
        validateByte(content, pos++, 0x04);
        validateByte(content, pos++, 0x00);
        validateByte(content, pos++, 0x00);
        
        // Application data size
        validateByte(content, pos++, 0x04);
        
        // Application data
        for (int i : cmdId) {
            validateByte(content, pos++, i);
        }

        // CRC-8
        validateByte(content, pos++, crc);
    }
}
