package stas.paliutin.jm_311.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import stas.paliutin.jm_311.model.Role;
import stas.paliutin.jm_311.service.RoleService;

@Controller
@RequestMapping("/admin/roles")
public class RolesController {

    private RoleService roleService;

    @Autowired
    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Model model) {
        System.out.println("==========get role index============");
        model.addAttribute("roles", roleService.findAll());
        return "roles/index";
    }

    @GetMapping("/new")
    public String add() {
        return "roles/new";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") long id,
                           Model model) {
        model.addAttribute("role", roleService.getById(id) );
        return "roles/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id,
                       Model model) {
        Role role = roleService.getById(id);
        if (role == null) {
            return "redirect:/admin/roles";
        }
        model.addAttribute("role", roleService.getById(id) );
        return "roles/edit";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id,
                         Model model) {
        roleService.delete(id);
        return "redirect:/admin/roles";
    }

    @PostMapping()
    public String create(@RequestParam(value = "name") String name) {
        roleService.create(new Role(name));
        System.out.println("-----post-role-create-------: " + name);
        return "redirect:/admin/roles";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("role") Role role) {
        roleService.update(role);
        return "redirect:/admin/roles";
    }
}
