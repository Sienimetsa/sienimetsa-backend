package sienimetsa.sienimetsa_backend.service;

import org.springframework.stereotype.Service;

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.Mushroom;

@Service
public class LevelingService {

    /**
     * Processes a mushroom finding for a user, updating their unique mushrooms
     * and potentially increasing their level.
     * 
     * @param appuser  The user who found the mushroom
     * @param mushroom The mushroom that was found
     */

    public void processFinding(Appuser appuser, Mushroom mushroom) {
        if (!appuser.getUniqueMushrooms().contains(mushroom.getM_id())) {
            appuser.addUniqueMushroom(mushroom.getM_id());

            int currentLevel = appuser.getLevel();
            int levelIncrement = calculateLevelIncrement(currentLevel);

            appuser.setLevel(currentLevel + levelIncrement);
        }
    }

    /**
     * Calculates the level increment based on the user's current level.
     * The increment slows down as the level increases.
     * 
     * @param currentLevel The user's current level
     * @return The calculated level increment
     */

    public int calculateLevelIncrement(int currentLevel) {
        return (int) Math.max(1, Math.round(10 / (currentLevel * 0.1 + 1)));
    }
}