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
public class AdminSoapController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminSoapController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping()
    public String restAdmin(Principal principal, Model model) {
        User user = userService.findByUsername( principal.getName() );
        //Информация о залогиневшемся юзере
        model.addAttribute("user", user);
        //Чистый объект с заполненной ролью по умолчанию, для формы нового пользователя
        model.addAttribute("new_user", new User()
                .setRole( roleService.findOne("ROLE_USER") )
        );
        //Список всех доступных ролей
        model.addAttribute("roles", roleService.findAll() );
        //Список всех юзеров
        model.addAttribute("users", userService.findAll() );
        return "rest/admin";
    }

    @GetMapping("/soap")
    public String soapAdmin(Principal principal, Model model) {
        User user = userService.findByUsername( principal.getName() );
        //Информация о залогиневшемся юзере
        model.addAttribute("user", user);
        //Чистый объект с заполненной ролью по умолчанию, для формы нового пользователя
        model.addAttribute("new_user", new User()
                .setRole( roleService.findOne("ROLE_USER") )
        );
        //Список всех доступных ролей
        model.addAttribute("roles", roleService.findAll() );
        //Список всех юзеров
        model.addAttribute("users", userService.findAll() );
        return "admin";
    }


    @PostMapping("/users/{id}/delete")
    public String delete(@PathVariable("id") long id,
                         Model model) {
        userService.delete(id);
        return "redirect:/admin/soap";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "roles_checkbox", required = false) String[] roles) {
        user.setRoles( roleService.findByNames(roles) );
        userService.create(user);
        return "redirect:/admin/soap";
    }

    @PostMapping("/users/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "roles_checkbox", required = false) String[] roles) {
        user.setRoles( roleService.findByNames(roles) );
        userService.update(user);
        return "redirect:/admin/soap";
    }
}
