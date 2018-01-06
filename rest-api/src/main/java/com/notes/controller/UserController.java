package com.notes.controller;

import com.notes.entity.User;
import com.notes.exception.UserNotFoundException;
import com.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
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

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public User getUser(@PathVariable String username) {
		try {
			return userService.getByUsername(username);
		} catch (NoResultException ignored) {
			throw new UserNotFoundException();
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> getAllUsers() {
		return userService.getAll();
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String username) {
		User user = userService.getByUsername(username);
		userService.delete(user);
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.PUT)
	public void updateUser(@PathVariable String username,
						   @RequestBody User user) {
		User existUser = userService.getByUsername(username);
		user.setId(existUser.getId());
		userService.update(user);
	}

//	private User findUser(String userId) {
//		return findUser(userId, null);
//	}
//
//	private User findUser(String userId, Principal principal) {
//
//		User user = null;
//
//		if (userId.matches("^\\d+$")) {
//			int id = Integer.parseInt(userId);
//			user = userService.get(id);
//		} else {
//			try {
//				user = userService.getByUsername(userId);
//			} catch (NoResultException ignored) {
//				throw new UserNotFoundException();
//			}
//		}
//
//		if (user == null) {
//			throw new UserNotFoundException();
//		}
//
//		if (principal != null && !principal.getName().equals(user.getUsername()))
//			throw new NoAccessException();
//
//		return user;
//	}
}
