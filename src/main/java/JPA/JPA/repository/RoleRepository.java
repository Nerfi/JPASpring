package JPA.JPA.repository;

import JPA.JPA.models.ERole;
import JPA.JPA.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// @Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}
