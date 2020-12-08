package stas.paliutin.jm_311.dto;

import stas.paliutin.jm_311.model.Role;
import stas.paliutin.jm_311.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private long id;

    private String name;

    private String lastName;

    private int age;

    private String username;

    private String password;

    private Set<Long> roleIds = new HashSet<>();

    public UserDTO() {
    }

     public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
        this.username = user.getUsername();
//        this.password = user.getPassword();
        this.password = "";
        for (Role role : user.getRoles() ) {
            this.roleIds.add( role.getId() );
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", roles=" + roleIds +
                '}';
    }
}
