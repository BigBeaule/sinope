package com.bigb.sinope;

import com.bigb.sinope.answer.DataReadAnswerTest;
import com.bigb.sinope.answer.KeyAnswerTest;
import com.bigb.sinope.answer.LoginAnswerTest;
import com.bigb.sinope.request.DataReadCommandTest;
import com.bigb.sinope.request.DatalessRequestTest;
import com.bigb.sinope.request.KeyRequestTest;
import com.bigb.sinope.request.LoginRequestTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SinopeTests extends TestCase {
    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(//
                CRC8Test.class, //
                SinopeDataReaderTest.class, //

                DataReadAnswerTest.class, //
                KeyAnswerTest.class, //
                LoginAnswerTest.class, //

                DatalessRequestTest.class, //
                DataReadCommandTest.class, //
                KeyRequestTest.class, //
                LoginRequestTest.class //
        );
    }
}
