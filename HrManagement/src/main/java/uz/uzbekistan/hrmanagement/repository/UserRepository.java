package uz.uzbekistan.hrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uzbekistan.hrmanagement.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findUserByEmailAndId(String email, UUID id);

    User getUserByEmail(String email);
}
