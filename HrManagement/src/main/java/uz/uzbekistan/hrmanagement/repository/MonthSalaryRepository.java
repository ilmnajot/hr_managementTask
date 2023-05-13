package uz.uzbekistan.hrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uzbekistan.hrmanagement.entity.MonthSalary;

import java.util.List;
import java.util.UUID;
@Repository
public interface MonthSalaryRepository extends JpaRepository<MonthSalary, UUID> {

    List<MonthSalary> findAllByOwner_Id(UUID owner_id);
}
