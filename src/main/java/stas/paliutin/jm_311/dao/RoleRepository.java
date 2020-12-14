package stas.paliutin.jm_311.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import stas.paliutin.jm_311.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByName(String name);
}
