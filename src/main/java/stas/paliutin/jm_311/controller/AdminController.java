package stas.paliutin.jm_311.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import stas.paliutin.jm_311.model.User;
import stas.paliutin.jm_311.service.RoleService;
import stas.paliutin.jm_311.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping()
    public String index(Principal principal, Model model) {
        User user = userService.findOne( principal.getName() );
        //Информация о залогиневшемся юзере
        model.addAttribute("user", user);
        //Чистый объект с заполненной ролью по умолчанию, для формы нового пользователя
        model.addAttribute("new_user", new User().setRole( roleService.findOne("ROLE_USER") ) );
        //Список всех доступных ролей
        model.addAttribute("roles", roleService.findAll() );
        //Список всех юзеров
        model.addAttribute("users", userService.findAll() );
        return "admin";
    }

//    @GetMapping("/users/new")
//    public String add(Model model) {
//        model.addAttribute("roles", roleService.findAll() );
//        model.addAttribute("user", new User().setRole( roleService.findOne("ROLE_USER") ) );
//        return "users/new";
//    }

//    @GetMapping("/users/{id}")
//    public String showById(@PathVariable("id") long id,
//                           Model model) {
//        model.addAttribute("user", userService.getById(id));
//        return "users/show";
//    }

//    @GetMapping("/users/{id}/edit")
//    public String edit(@PathVariable("id") long id,
//                       Model model) {
//        User user = userService.getById(id);
//        if (user == null) {
//            return "redirect:/admin";
//        }
//
//        user.setPassword("");
//        model.addAttribute("roles", roleService.findAll() );
//        model.addAttribute("user", user );
//        return "users/edit";
//    }

    @PostMapping("/users/{id}/delete")
    public String delete(@PathVariable("id") long id,
                         Model model) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "roles_checkbox", required = false) String[] roles) {
        user.setRoles( roleService.findByNames(roles) );
        userService.create(user);
        return "redirect:/admin";
    }

    @PostMapping("/users/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "roles_checkbox", required = false) String[] roles) {
        user.setRoles( roleService.findByNames(roles) );
        userService.update(user);
        return "redirect:/admin";
    }
}
