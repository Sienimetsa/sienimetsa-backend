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
        boolean isUnique = !appuser.getUniqueMushrooms().contains(mushroom.getM_id());
        int currentLevel = appuser.getLevel();
        double currentProgress = appuser.getProgress();

        // Apply the progress multiplier based on the level
        double progressMultiplier = Math.max(0.1, 1.0 - 0.1 * (currentLevel - 1)); // Apply 10% reduction per level

        // If the mushroom is unique, add progress, otherwise add for a known mushroom
        if (isUnique) {
            // Add the mushroom to the user's unique findings
            appuser.addUniqueMushroom(mushroom.getM_id());

            // Add progress based on the level and unique mushroom
            currentProgress += 100 * progressMultiplier; // Unique mushroom gives 100%, but adjusted by level
        } else {
            // Add 25% progress for a known mushroom, adjusted by level
            currentProgress += 25 * progressMultiplier; // Known mushroom gives 25%, but adjusted by level
        }

        // Check if the user has enough progress to level up (100% or more)
        // Level up and reset progress when progress exceeds or equals 100%
        while (currentProgress >= 100) {
            currentProgress -= 100; // Reset progress after level up
            currentLevel++; // Level up
        }

        // Calculate the progress required for the next level
        double progressToNextLevel = calculateProgressToNextLevel(currentLevel);

        // Check if user should level up further based on the new progress
        while (currentProgress >= progressToNextLevel) {
            currentProgress -= progressToNextLevel; // Subtract the progress required to level up
            currentLevel++; // Level up
            progressToNextLevel = calculateProgressToNextLevel(currentLevel); // Recalculate progress for the next level
        }

        // Update the user's level and progress
        appuser.setLevel(currentLevel);
        appuser.setProgress(currentProgress);
    }

    /**
     * Calculates the progress required to reach the next level.
     * The progress requirement increases as the user's level increases.
     * 
     * @param currentLevel The user's current level
     * @return The progress required for the next level
     */
    public double calculateProgressToNextLevel(int currentLevel) {
        return 100 * Math.pow(1.1, currentLevel - 1); // Progress increases by 10% per level
    }
}
