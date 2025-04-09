package sienimetsa.sienimetsa_backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.Mushroom;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class LevelingServiceTest {

    private static final long UNIQUE_MUSHROOM_ID = 1L;
    private static final int INITIAL_LEVEL = 1;
    private static final int INITIAL_PROGRESS = 0;
    private static final int LEVEL_UP_PROGRESS = 100;
    private static final double PROGRESS_MULTIPLIER = 0.1;

    private LevelingService levelingService;
    private Appuser appuser;

    @BeforeEach
    void setUp() {
        levelingService = new LevelingService();
        appuser = createTestUser();
    }

    private Appuser createTestUser() {
        Appuser user = new Appuser();
        user.setLevel(INITIAL_LEVEL);
        user.setProgress(INITIAL_PROGRESS);
        user.setUniqueMushrooms(new HashSet<>());
        return user;
    }

    private Mushroom createMushroom(long id) {
        Mushroom mushroom = new Mushroom();
        mushroom.setM_id(id);
        return mushroom;
    }

    @Test
    void testProcessFinding_UniqueMushroom_LevelUp() {
        // Arrange
        Mushroom uniqueMushroom = createMushroom(UNIQUE_MUSHROOM_ID);

        // Act
        levelingService.processFinding(appuser, uniqueMushroom);

        // Assert
        assertLevelAndProgress(INITIAL_LEVEL + 1, INITIAL_PROGRESS, uniqueMushroom);
    }

    @Test
    void testProcessFinding_KnownMushroom_NoLevelUp() {
        // Arrange
        Mushroom knownMushroom = createMushroom(UNIQUE_MUSHROOM_ID);
        appuser.addUniqueMushroom(UNIQUE_MUSHROOM_ID);

        // Act
        levelingService.processFinding(appuser, knownMushroom);

        // Assert
        assertLevelAndProgress(INITIAL_LEVEL, 25, knownMushroom);
    }

    @Test
    void testProcessFinding_UniqueMushroom_MultipleLevelUps() {
        // Arrange
        Mushroom uniqueMushroom = createMushroom(UNIQUE_MUSHROOM_ID);
        appuser.setProgress(150);

        // Act
        levelingService.processFinding(appuser, uniqueMushroom);

        // Assert
        assertEquals(3, appuser.getLevel(), "User should level up to level 3");
        assertTrue(appuser.getProgress() < LEVEL_UP_PROGRESS, "Progress should reset after multiple level-ups");
    }

    @Test
    void testProcessFinding_ProgressMultiplier() {
        // Arrange
        Mushroom uniqueMushroom = createMushroom(UNIQUE_MUSHROOM_ID);
        appuser.setLevel(5);

        // Act
        levelingService.processFinding(appuser, uniqueMushroom);

        // Assert
        double expectedProgress = LEVEL_UP_PROGRESS * Math.max(PROGRESS_MULTIPLIER, 1.0 - PROGRESS_MULTIPLIER * (5 - 1));
        assertEquals(expectedProgress, appuser.getProgress(), "Progress should be adjusted by the multiplier");
    }

    private void assertLevelAndProgress(int expectedLevel, int expectedProgress, Mushroom mushroom) {
        assertEquals(expectedLevel, appuser.getLevel(), "User level mismatch after finding mushroom " + mushroom.getM_id());
        assertEquals(expectedProgress, appuser.getProgress(), "User progress mismatch after finding mushroom " + mushroom.getM_id());
        assertTrue(appuser.getUniqueMushrooms().contains(mushroom.getM_id()), "Mushroom " + mushroom.getM_id() + " should be added to the user's collection");
    }
}
