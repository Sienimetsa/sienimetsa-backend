package sienimetsa.sienimetsa_backend.domain;
import org.springframework.data.repository.CrudRepository;

public interface FindingRepository extends CrudRepository<Finding, Long> {
    Finding findByAppuser(Appuser appuser);

}
