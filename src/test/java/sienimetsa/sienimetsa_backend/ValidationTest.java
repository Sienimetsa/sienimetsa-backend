package sienimetsa.sienimetsa_backend;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.Mushroom;
import sienimetsa.sienimetsa_backend.domain.User;

@SpringBootTest
@ActiveProfiles("test")
class ValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    //testataa appuser entity
    @Test
    void testInvalidAppuserEntity() {
        Appuser appuser = new Appuser();
        appuser.setUsername(""); // Assuming @NotBlank
        appuser.setPasswordHash(""); // Assuming @NotBlank
        appuser.setPhone(""); // Assuming @NotBlank
        appuser.setEmail(""); // Assuming @NotBlank
        appuser.setCountry(""); // Assuming @NotBlank

        Set<ConstraintViolation<Appuser>> violations = validator.validate(appuser);

        assertFalse(violations.isEmpty());
        assertEquals(8, violations.size()); // Checking for all violations
    }

    //testaa finding entity
    @Test
    void testInvalidFindingEntity() {
        Finding finding = new Finding();
        finding.setCity(""); // Assuming @NotBlank

        Set<ConstraintViolation<Finding>> violations = validator.validate(finding);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size()); // Checking for the city field validation
    }

    //testaa mushroom entity
    @Test
    void testInvalidMushroomEntity() {
        Mushroom mushroom = new Mushroom();
        mushroom.setMname(""); // Assuming @NotBlank
        mushroom.setToxicity_level(""); // Assuming @NotBlank
        mushroom.setColor(""); // Assuming @NotBlank
        mushroom.setGills(""); // Assuming @NotBlank
        mushroom.setCap(""); // Assuming @NotBlank
        mushroom.setTaste(""); // Assuming @NotBlank

        Set<ConstraintViolation<Mushroom>> violations = validator.validate(mushroom);

        assertFalse(violations.isEmpty());
        assertEquals(6, violations.size()); // Checking for all violations
    }

    //testaa user entityn
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
