package org.ikovia;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;

public class InputFileTest {

    @Test
    public void sampleTest(){

        assertEquals(1,1);
    }

    @Test
    public void checkFile() throws IOException {

        assertNotNull(getClass().getResource("/samples.txt"));
    }
}
