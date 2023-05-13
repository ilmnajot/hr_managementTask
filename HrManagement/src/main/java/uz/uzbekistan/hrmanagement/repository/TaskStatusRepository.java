package uz.uzbekistan.hrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uzbekistan.hrmanagement.entity.TaskStatus;
@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {


}
