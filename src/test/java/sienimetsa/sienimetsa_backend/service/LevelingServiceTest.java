package sienimetsa.sienimetsa_backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.Mushroom;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class LevelingServiceTest {

    private LevelingService levelingService;
    private Appuser appuser;

    @BeforeEach
    void setUp() {
        levelingService = new LevelingService();

        // Initialize a test user
        appuser = new Appuser();
        appuser.setLevel(1);
        appuser.setProgress(0);
        appuser.setUniqueMushrooms(new HashSet<>());
    }

    @Test
    void testProcessFinding_UniqueMushroom_LevelUp() {
        // Arrange
        Mushroom uniqueMushroom = new Mushroom();
        uniqueMushroom.setM_id(1L);

        // Act
        levelingService.processFinding(appuser, uniqueMushroom);

        // Assert
        assertEquals(2, appuser.getLevel(), "User should level up to level 2");
        assertEquals(0, appuser.getProgress(), "Progress should reset after leveling up");
        assertTrue(appuser.getUniqueMushrooms().contains(1L), "Unique mushroom should be added to the user's collection");
    }

    @Test
    void testProcessFinding_KnownMushroom_NoLevelUp() {
        // Arrange
        Mushroom knownMushroom = new Mushroom();
        knownMushroom.setM_id(1L);
        appuser.addUniqueMushroom(1L); // Add the mushroom to the user's collection

        // Act
        levelingService.processFinding(appuser, knownMushroom);

        // Assert
        assertEquals(1, appuser.getLevel(), "User should remain at level 1");
        assertTrue(appuser.getProgress() > 0, "Progress should increase for a known mushroom");
        assertEquals(25, appuser.getProgress(), "Progress should increase by 25 for a known mushroom");
    }

    @Test
    void testProcessFinding_UniqueMushroom_MultipleLevelUps() {
        // Arrange
        Mushroom uniqueMushroom = new Mushroom();
        uniqueMushroom.setM_id(1L);
        appuser.setProgress(150); // Set progress to exceed multiple levels

        // Act
        levelingService.processFinding(appuser, uniqueMushroom);

        // Assert
        assertEquals(3, appuser.getLevel(), "User should level up to level 3");
        assertTrue(appuser.getProgress() < 100, "Progress should reset after multiple level-ups");
    }


    @Test
    void testProcessFinding_ProgressMultiplier() {
        // Arrange
        Mushroom uniqueMushroom = new Mushroom();
        uniqueMushroom.setM_id(1L);
        appuser.setLevel(5); // Set user level to 5

        // Act
        levelingService.processFinding(appuser, uniqueMushroom);

        // Assert
        double expectedProgress = 100 * Math.max(0.1, 1.0 - 0.1 * (5 - 1)); // Progress multiplier for level 5
        assertEquals(expectedProgress, appuser.getProgress(), "Progress should be adjusted by the multiplier");
    }
}