package sienimetsa.sienimetsa_backend.domain;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
    User findByAUsername(String aUsername);
}