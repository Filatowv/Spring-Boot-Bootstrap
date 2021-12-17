package com.filatovilia.spring.springboot.springbootapp.controller;

import com.filatovilia.spring.springboot.springbootapp.model.Role;
import com.filatovilia.spring.springboot.springbootapp.model.User;
import com.filatovilia.spring.springboot.springbootapp.service.RoleService;
import com.filatovilia.spring.springboot.springbootapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class UserController {


	private final UserService userService;
	private final RoleService roleService;


	@Autowired
	public UserController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}


	//список пользователя
	@GetMapping(value = "/user")
	public String getUserInfo(@AuthenticationPrincipal User user, Model model){
		model.addAttribute("user", user);
		model.addAttribute("roles",user.getRoles());
		return "user_Info";
	}


	//список всех пользователей
	@GetMapping(value = "/admin")
	public String adminInfoPage(Model model) {
		model.addAttribute("userList",userService.getAllUsers());
		model.addAttribute("user",new User());
		List<Role> listRoles = roleService.getAllRole();
		model.addAttribute("listRoles",listRoles);
		return "admin_Info";
	}


	//форма нового пользователя
	@GetMapping(value = "/admin/new")
	public String newUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("roles", roleService.getAllRole());
		return "new_user";
	}


	//список с новым пользователем
	@PostMapping(value = "/admin/add")
	private String addUser(@ModelAttribute User user, @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {
		Set<Role> roles = new HashSet<>();
		for (String role : checkBoxRoles) {
			roles.add(roleService.getRoleByName(role));
		}
		user.setRoles(roles);
		userService.addUser(user);
		return "redirect:/admin";
	}


    //форма редактирования
	@GetMapping("/admin/edit/{id}")
	public String edit(@PathVariable("id")long id,Model model) {
		model.addAttribute("userEdit",userService.getUserById(id));
		model.addAttribute("role",roleService.getAllRole());
		return "edit_user";
	}

	//запрос редактирования
	@PatchMapping(value = "/admin/{id}")
	public String updateUser(@ModelAttribute User user, @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {
		Set<Role> roles = new HashSet<>();
		for (String role : checkBoxRoles) {
			roles.add(roleService.getRoleByName(role));
		}
		user.setRoles(roles);
		userService.updateUser(user);
		return "redirect:/admin";
	}

	// удаление
	@RequestMapping("/admin/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		userService.deleteUser(id);
		return "redirect:/admin";
	}
}