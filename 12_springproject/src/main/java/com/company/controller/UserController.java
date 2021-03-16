package com.company.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.Validator.UserValidator;
import com.company.constants.Roles;
import com.company.entities.User;
import com.company.service.SecurityService;
import com.company.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@PostMapping("/registration")
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userForm, Roles.ROLE_USER);

		securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

		return "redirect:/welcome";
	}

	@GetMapping("/adminregistration")
	public String adminregistration(Model model) {
		model.addAttribute("userForm", new User());

		return "adminregistration";
	}

	@PostMapping("/adminregistration")
	public String adminRegistration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userForm, Roles.ROLE_ADMIN);

		securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

		return "redirect:/list-users";
	}

	@RequestMapping(value = "/delete-user", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam long id) {
		userService.deleteUser(id);
		return "redirect:/list-users";
	}

	@RequestMapping(value = "/list-users", method = RequestMethod.GET)
	public String showUsers(ModelMap model) {

		model.put("users", userService.getUsers());
		return "list-users";
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password are invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@GetMapping({ "/", "/welcome" })
	public String welcome(Model model) {

		String roles = getCurrentUserRoles();

		if (roles.contains(Roles.ROLE_ADMIN.toString())) {
			return "redirect:/list-users";
		} else {
			return "welcometodo";
		}
	}

	private String getCurrentUserRoles() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
	}

}
