package uz.uzbekistan.hrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uzbekistan.hrmanagement.entity.Role;
import uz.uzbekistan.hrmanagement.entity.enums.RoleName;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

Optional<Role> findRoleByRoleName(RoleName roleName);
}
