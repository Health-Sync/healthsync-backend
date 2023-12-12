package AI.Powered.Virtual.Medical.Doctor.repository;

import AI.Powered.Virtual.Medical.Doctor.entries.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
