package AI.Powered.Virtual.Medical.Doctor.repository;

import AI.Powered.Virtual.Medical.Doctor.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String name);
}
