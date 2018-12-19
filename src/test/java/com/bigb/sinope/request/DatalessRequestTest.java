package com.bigb.sinope.request;

import org.junit.Test;
import com.bigb.sinope.CommandTestUtils;

public class DatalessRequestTest extends CommandTestUtils {
    @Test
    public void testPing() {
        byte[] content = runRequest(DatalessRequest.PING_CMD);
        assertEquals(7, content.length);
        int pos = 0;
        
        // Start
        validateByte(content, pos++, 0x55);
        validateByte(content, pos++, 0x00);
        
        // Size
        validateShort(content, pos++, DatalessRequest.PING_CMD.getPayloadSize());
        ++pos;

        // Command
        validateByte(content, pos++, 0x12);
        validateByte(content, pos++, 0x00);
        
        // CRC-8
        validateByte(content, pos++, 0x34);
    }
}
