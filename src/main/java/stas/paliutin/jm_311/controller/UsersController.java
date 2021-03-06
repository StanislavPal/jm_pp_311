package stas.paliutin.jm_311.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import stas.paliutin.jm_311.model.Role;
import stas.paliutin.jm_311.model.User;
import stas.paliutin.jm_311.service.RoleService;
import stas.paliutin.jm_311.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UsersController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/index";
    }

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("roles", roleService.findAllWithUse( roleService.findOne("ROLE_USER") ) );
        return "users/new";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") long id,
                           Model model) {
        model.addAttribute("user", userService.getById(id));
        return "users/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id,
                       Model model) {
        User user = userService.getById(id);
        if (user == null) {
            return "redirect:/admin/users";
        }

        model.addAttribute("roles", roleService.findAllWithUse( user.getRoles().toArray(new Role[0]) ) );
        model.addAttribute("user", user );
        return "users/edit";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id,
                         Model model) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "roles_checkbox", required = false) String[] roles) {
        user.setRoles( roleService.findByRoles(roles) );
        userService.create(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "roles_checkbox", required = false) String[] roles) {
        user.setRoles( roleService.findByRoles(roles) );
        userService.update(user);
        return "redirect:/admin/users";
    }
}
