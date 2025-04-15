package sienimetsa.sienimetsa_backend.domain;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FindingRepository extends CrudRepository<Finding, Long> {
    List<Finding> findByAppuser(Appuser appuser);
    void deleteByAppuser(Appuser appuser);
}