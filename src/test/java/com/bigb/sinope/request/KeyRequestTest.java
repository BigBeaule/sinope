package com.bigb.sinope.request;

import org.junit.Test;
import com.bigb.sinope.CommandTestUtils;

public class KeyRequestTest extends CommandTestUtils {
    @Test
    public void testCommand() {
        CommandRequest cmd = new KeyRequest("0123456789ABCDEF");
        byte[] content = runRequest(cmd);
        assertEquals(15, content.length);
        int pos = 0;
        
        // Start
        validateByte(content, pos++, 0x55);
        validateByte(content, pos++, 0x00);
        
        // Size
        validateShort(content, pos++, cmd.getPayloadSize());
        ++pos;

        // Command
        validateByte(content, pos++, 0x0A);
        validateByte(content, pos++, 0x01);

        // Gateway ID
        validateByte(content, pos++, 0xEF);
        validateByte(content, pos++, 0xCD);
        validateByte(content, pos++, 0xAB);
        validateByte(content, pos++, 0x89);
        validateByte(content, pos++, 0x67);
        validateByte(content, pos++, 0x45);
        validateByte(content, pos++, 0x23);
        validateByte(content, pos++, 0x01);
        
        // CRC-8
        validateByte(content, pos++, 0xDA);
    }
}
