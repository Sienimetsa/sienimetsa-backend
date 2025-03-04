package sienimetsa.sienimetsa_backend;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sienimetsa.sienimetsa_backend.domain.Appuser;

@SpringBootTest
class MushroomPicTest {

    @Test
    void testProfilePictureSetterAndGetter() {
        Appuser user = new Appuser();
        String profilePicUrl = "https://example.com/profile.jpg";
        user.setProfilePicture(profilePicUrl);
        assertEquals(profilePicUrl, user.getProfilePicture());
    }

    @Test
    void testProfilePictureNotBlank() {
        Appuser user = new Appuser();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> user.setProfilePicture(""));
        assertTrue(exception.getMessage().contains("Profile picture is mandatory"));
    }
}
