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
	public void addUser(@RequestBody User user) {
		userService.save(user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable int id) {
		return userService.get(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userService.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable int id) {
		User user = userService.get(id);
		userService.delete(user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateUser(@PathVariable int id,
	                       @RequestBody User user) {
		user.setId(id);
		userService.update(user);
	}
}
