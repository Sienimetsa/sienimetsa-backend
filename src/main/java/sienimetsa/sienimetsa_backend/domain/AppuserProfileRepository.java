package sienimetsa.sienimetsa_backend.domain;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface AppuserProfileRepository extends CrudRepository<AppuserProfile, Long> {
    // Find profile by user
    Optional<AppuserProfile> findByUser(Appuser user);
}