package com.notes.controller;

import com.notes.entity.User;
import com.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public boolean addUser(@RequestBody User user) {
		return userService.save(user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable int id) {
		return userService.getById(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userService.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean deleteUser(@PathVariable int id) {
		User user = userService.getById(id);
		return userService.delete(user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public boolean updateUser(@PathVariable int id,
	                          @RequestBody User user) {
		user.setId(id);
		return userService.update(user);
	}
}
