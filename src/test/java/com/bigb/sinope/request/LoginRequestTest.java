package com.bigb.sinope.request;

import org.junit.Test;
import com.bigb.sinope.CommandTestUtils;

public class LoginRequestTest extends CommandTestUtils {
    @Test
    public void testCommand() {
        CommandRequest cmd = new LoginRequest("0123456789ABCDEF", "FEDCBA9876543210");
        byte[] content = runRequest(cmd);
        assertEquals(23, content.length);
        int pos = 0;

        // Start
        validateByte(content, pos++, 0x55);
        validateByte(content, pos++, 0x00);

        // Size
        validateShort(content, pos++, cmd.getPayloadSize());
        ++pos;

        // Command
        validateByte(content, pos++, 0x10);
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

        // Auth Key
        validateByte(content, pos++, 0x10);
        validateByte(content, pos++, 0x32);
        validateByte(content, pos++, 0x54);
        validateByte(content, pos++, 0x76);
        validateByte(content, pos++, 0x98);
        validateByte(content, pos++, 0xBA);
        validateByte(content, pos++, 0xDC);
        validateByte(content, pos++, 0xFE);

        // CRC-8
        validateByte(content, pos++, 0xB2);
    }
}
