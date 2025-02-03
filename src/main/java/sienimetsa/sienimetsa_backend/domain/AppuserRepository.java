package sienimetsa.sienimetsa_backend.domain;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AppuserRepository extends CrudRepository<Appuser, Long> {
    Optional<Appuser> findByEmail(String email);
    Optional<Appuser> findByUsername(String username);

}
