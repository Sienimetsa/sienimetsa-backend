package sienimetsa.sienimetsa_backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;




class LevelingServiceTest {

    private LevelingService levelingService;
    private Appuser appuser;
    private Mushroom mushroom;

    @BeforeEach
    void setUp() {
        levelingService = new LevelingService();
        appuser = Mockito.mock(Appuser.class);
        mushroom = Mockito.mock(Mushroom.class);
    }

    @Test
    void testProcessFinding_NewMushroom() {
        // Arrange
        HashSet<Long> uniqueMushrooms = new HashSet<>();
        when(appuser.getUniqueMushrooms()).thenReturn(uniqueMushrooms);
        when(mushroom.getM_id()).thenReturn(1L);
        when(appuser.getLevel()).thenReturn(1);

        // Act
        levelingService.processFinding(appuser, mushroom);

        // Assert
        verify(appuser).addUniqueMushroom(1L);
        verify(appuser).setLevel(anyInt());
    }

    @Test
    void testProcessFinding_ExistingMushroom() {
        // Arrange
        HashSet<Long> uniqueMushrooms = new HashSet<>();
        uniqueMushrooms.add(1L);
        when(appuser.getUniqueMushrooms()).thenReturn(uniqueMushrooms);
        when(mushroom.getM_id()).thenReturn(1L);

        // Act
        levelingService.processFinding(appuser, mushroom);

        // Assert
        verify(appuser, never()).addUniqueMushroom(anyLong());
        verify(appuser, never()).setLevel(anyInt());
    }

    @Test
    void testCalculateLevelIncrement() {
        // Arrange
        int currentLevel = 5;

        // Act
        int levelIncrement = levelingService.calculateLevelIncrement(currentLevel);

        // Assert
        assertEquals((int) Math.max(1, Math.round(10 / (currentLevel * 0.1 + 1))), levelIncrement);
    }
}