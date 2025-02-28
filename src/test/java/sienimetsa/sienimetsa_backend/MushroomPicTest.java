package sienimetsa.sienimetsa_backend;

import org.junit.jupiter.api.Test;

import sienimetsa.sienimetsa_backend.domain.Mushroompic;

import static org.junit.jupiter.api.Assertions.*;

class MushroomPicTest {

    @Test
    void testEnumValues() {
        // Verify the number of enum values
        Mushroompic[] values = Mushroompic.values();
        assertEquals(5, values.length);

        // Verify specific enum values
        assertEquals(Mushroompic.mp1, Mushroompic.valueOf("mp1"));
        assertEquals(Mushroompic.mp2, Mushroompic.valueOf("mp2"));
        assertEquals(Mushroompic.mp3, Mushroompic.valueOf("mp3"));
        assertEquals(Mushroompic.mp4, Mushroompic.valueOf("mp4"));
        assertEquals(Mushroompic.mp5, Mushroompic.valueOf("mp5"));
    }

    @Test
    void testEnumToString() {
        assertEquals("mp1", Mushroompic.mp1.toString());
        assertEquals("mp2", Mushroompic.mp2.toString());
    }
}
