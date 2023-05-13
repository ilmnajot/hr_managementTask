package uz.uzbekistan.hrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uzbekistan.hrmanagement.entity.InOutStatus;
@Repository
public interface InOutStatusRepository extends JpaRepository<InOutStatus, Long> {
}
