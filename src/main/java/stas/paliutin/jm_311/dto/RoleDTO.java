package stas.paliutin.jm_311.dto;

import org.springframework.security.core.GrantedAuthority;
import stas.paliutin.jm_311.model.Role;

import javax.persistence.*;
import java.util.Objects;

public class RoleDTO {

    private Long id;

    private String name;

    public RoleDTO() {
    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    public RoleDTO(String name) {
        this.name = name.toUpperCase();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == RoleDTO.class) {
            return id.equals( ( (RoleDTO) obj).id ) && name.equals( ( (RoleDTO) obj).name);
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String role) {
        this.name = role.toUpperCase();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
