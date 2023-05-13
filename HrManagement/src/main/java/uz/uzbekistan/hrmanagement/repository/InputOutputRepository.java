package uz.uzbekistan.hrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uzbekistan.hrmanagement.entity.InputOutput;

import java.util.List;
import java.util.UUID;

@Repository
public interface InputOutputRepository extends JpaRepository<InputOutput, UUID> {

    List<InputOutput> findAllByUser_Id(UUID user_id);
}
