package stas.paliutin.jm_311.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stas.paliutin.jm_311.dto.UserDTO;
import stas.paliutin.jm_311.exception_handling.NoSuchUserException;
import stas.paliutin.jm_311.exception_handling.UserIncorrectData;
import stas.paliutin.jm_311.model.User;
import stas.paliutin.jm_311.service.RoleService;
import stas.paliutin.jm_311.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api")
public class ApiRestController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public ApiRestController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> showAllUsers() {

        List<User> users = userService.findAll();
        if ( users == null || users.isEmpty() ) {
            throw new NoSuchUserException("There are no Users found");
        }

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : users){
            usersDTO.add( new UserDTO(user) );
        }

        return usersDTO;
    }

    @GetMapping("/users/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        UserDTO userDTO = new UserDTO( userService.findOne(id) );

        if(userDTO == null) {
            throw new NoSuchUserException("There is no User found with ID = "
                    + id + " in Database");
        }
        return userDTO;
    }

    @PostMapping("/users")
    public UserDTO addNewUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        user.setRoles( roleService.findByIds( userDTO.getRoleIds() ) );
        user = userService.create(user);
        return new UserDTO( user );
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        userService.update(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userService.findOne(id);

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