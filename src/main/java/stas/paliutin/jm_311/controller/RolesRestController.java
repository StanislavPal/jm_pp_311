package stas.paliutin.jm_311.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stas.paliutin.jm_311.dto.RoleDTO;
import stas.paliutin.jm_311.dto.UserDTO;
import stas.paliutin.jm_311.exception_handling.NoSuchUserException;
import stas.paliutin.jm_311.exception_handling.UserIncorrectData;
import stas.paliutin.jm_311.model.Role;
import stas.paliutin.jm_311.model.User;
import stas.paliutin.jm_311.service.RoleService;
import stas.paliutin.jm_311.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api")
public class RolesRestController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public RolesRestController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/roles")
    public List<RoleDTO> showAll() {

        List<Role> roles = roleService.findAll();
        if ( roles == null || roles.isEmpty() ) {
            throw new NoSuchUserException("There are no Roles found");
        }

        List<RoleDTO> rolesDTO = new ArrayList<>();
        for (Role role : roles){
            rolesDTO.add( new RoleDTO(role) );
        }

        return rolesDTO;
    }

    @GetMapping("/roles/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        UserDTO userDTO = new UserDTO( userService.findById(id) );

        if(userDTO == null) {
            throw new NoSuchUserException("There is no User found with ID = "
                    + id + " in Database");
        }
        return userDTO;
    }

    @PostMapping("/roles")
    public UserDTO addNewUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        user.setRoles( roleService.findByIds( userDTO.getRoleIds() ) );
        user = userService.create(user);
        return new UserDTO( user );
    }

    @PutMapping("/roles")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        user.setRoles( roleService.findByIds( userDTO.getRoleIds() ) );
        user = userService.update(user);
        return new UserDTO( user );
    }

    @DeleteMapping("/roles/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);

        if (user == null) {
            throw new NoSuchUserException("There is no User found with ID = "
                    + id + " in Database");
        }

        userService.delete(id);
        return "The User with ID = " + id + " was deleted";
    }

    //По Трегулову
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> exceptionHandler(NoSuchUserException exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo( exception.getMessage() );

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    //По Трегулову
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> exceptionHandler(Exception exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo( exception.getMessage() );

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

}
