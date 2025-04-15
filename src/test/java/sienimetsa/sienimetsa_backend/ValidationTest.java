package sienimetsa.sienimetsa_backend;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.User;

@SpringBootTest
class ValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // testataa appuser entity
    @Test
    void testInvalidAppuserEntity() {
        Appuser appuser = new Appuser();
        appuser.setUsername(""); // Assuming @NotBlank
        appuser.setPasswordHash(""); // Assuming @NotBlank
        appuser.setPhone(""); // Assuming @NotBlank
        appuser.setProfilePicture("1"); // Assuming @NotBlank
        appuser.setLevel(0); // Assuming @Min(1)
        appuser.setProgress(0); // Assuming @Min(0)
        appuser.setEmail(""); // Assuming @NotBlank
        appuser.setCountry(""); // Assuming @NotBlank

        Set<ConstraintViolation<Appuser>> violations = validator.validate(appuser);

        // Make sure we have violations
        assertFalse(violations.isEmpty(), "There should be validation errors");

        // Check the number of violations (you need to adjust this number based on
        // actual constraints)
        assertEquals(7, violations.size(), "The number of violations should match the invalid fields");
    }

    // testaa finding entity
    @Test
    void testInvalidFindingEntity() {
        Finding finding = new Finding();
        finding.setCity(""); // Assuming @NotBlank

        Set<ConstraintViolation<Finding>> violations = validator.validate(finding);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size()); // Checking for the city field validation
    }

    // testaa mushroom entity
    @Test
    void testInvalidMushroomEntity() {
        Mushroom mushroom = new Mushroom();
        mushroom.setMname(""); // Assuming @NotBlank
        mushroom.setToxicity_level(""); // Assuming @NotBlank
        mushroom.setColor(""); // Assuming @NotBlank
        mushroom.setGills(""); // Assuming @NotBlank
        mushroom.setCap(""); // Assuming @NotBlank
        mushroom.setTaste(""); // Assuming @NotBlank
        mushroom.setDescription(""); // Assuming @NotBlank

        Set<ConstraintViolation<Mushroom>> violations = validator.validate(mushroom);

        assertFalse(violations.isEmpty());
        assertEquals(7, violations.size()); // Checking for all violations
    }

    // testaa user entityn
    @Test
    void testInvalidUserEntity() {
        User user = new User();
        user.setUsername(""); // Assuming @NotBlank
        user.setPasswordHash(""); // Assuming @NotBlank

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size()); // Checking for both username and password validation
    }
}
