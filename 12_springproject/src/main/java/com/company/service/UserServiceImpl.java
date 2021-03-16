package com.company.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.constants.Roles;
import com.company.entities.Role;
import com.company.entities.Todo;
import com.company.entities.User;
import com.company.repository.RoleRepository;
import com.company.repository.TodoRepository;
import com.company.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private TodoService todoService;

	@Override
	public void save(User user, Roles role) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		Set roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(role.toString()));

		user.setRoles(roles);

		userRepository.save(user);
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void deleteUser(long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			userRepository.delete(user.get());
			todoService.deleteByUserName(user.get().getUsername());
		}
	}
}
