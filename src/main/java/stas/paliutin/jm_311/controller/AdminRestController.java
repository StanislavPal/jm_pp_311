package stas.paliutin.jm_311.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stas.paliutin.jm_311.exception_handling.NoSuchUserException;
import stas.paliutin.jm_311.exception_handling.UserIncorrectData;
import stas.paliutin.jm_311.model.User;
import stas.paliutin.jm_311.service.UserService;

import java.util.List;

@RestController()
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> showAllUsers() {
        List<User> users = userService.findAll();

        if ( users == null) {
            throw new NoSuchUserException("There are no Users found");
        }
        return users;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        User user = userService.findOne(id);

        if(user == null) {
            throw new NoSuchUserException("There is no User found with ID = "
                    + id + " in Database");
        }
        return user;
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user) {
        userService.create(user);
        return user;
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
