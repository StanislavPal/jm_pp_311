package stas.paliutin.jm_311.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

}
