package sienimetsa.sienimetsa_backend.domain;
import org.springframework.data.repository.CrudRepository;

public interface MushroomRepository extends CrudRepository<Mushroom, Long> {
    Mushroom findByMname(String mname);
}
