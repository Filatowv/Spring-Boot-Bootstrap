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


	@RequestMapping(value = "/login")
	public String getLoginPage() {
		return "login";
	}

	//список пользователя
	@GetMapping(value = "/user")
	public String getUserInfo(@AuthenticationPrincipal User user, Model model){
		model.addAttribute("user", user);
		return "user_panel";
	}



	@GetMapping(value = "/admin")
	public String adminInfoPage(@AuthenticationPrincipal User user,Model model) {
		model.addAttribute("userList",userService.getAllUsers());
		model.addAttribute("user",user);
		return "admin_panel";
	}



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


	@PatchMapping(value = "/admin/edit")
	public String updateUser(@ModelAttribute User user, @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {
		Set<Role> roles = new HashSet<>();
		for (String role : checkBoxRoles) {
			roles.add(roleService.getRoleByName(role));
		}
		user.setRoles(roles);
		userService.updateUser(user);
		return "redirect:/admin";
	}


	@RequestMapping("/admin/delete/{id}")
	public String deleteProduct(@PathVariable("id") long id) {
		userService.deleteUser(id);
		return "redirect:/admin";
	}

}