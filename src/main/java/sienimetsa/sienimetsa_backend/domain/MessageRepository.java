package sienimetsa.sienimetsa_backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByOrderByTimestampAsc();
      // Method to find the oldest message
      Message findFirstByOrderByTimestampAsc();
}
