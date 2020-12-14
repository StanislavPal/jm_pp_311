package stas.paliutin.jm_311.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import stas.paliutin.jm_311.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}
