package sienimetsa.sienimetsa_backend.domain;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface AppuserRepository extends CrudRepository<Appuser, Long> {

    // Check if a user exists by email
    boolean existsByEmail(String email);

    // Check if a user exists by username
    boolean existsByUsername(String username);

    // Find a user by email
    Optional<Appuser> findByEmail(String email);

    // Find a user by username
    Optional<Appuser> findByUsername(String username);
}
