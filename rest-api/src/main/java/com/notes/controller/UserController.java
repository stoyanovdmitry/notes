package com.notes.controller;

import com.notes.entity.User;
import com.notes.exception.NoAccessException;
import com.notes.exception.UserNotFoundException;
import com.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.security.Principal;
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

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public User getUser(@PathVariable String userId,
						@AuthenticationPrincipal Principal principal) {
		return findUser(userId, principal);
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> getAllUsers() {
		return userService.getAll();
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String userId,
						   @AuthenticationPrincipal Principal principal) {
		User user = findUser(userId, principal);
		userService.delete(user);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	public void updateUser(@PathVariable String userId,
						   @RequestBody User user,
						   @AuthenticationPrincipal Principal principal) {
		User existUser = findUser(userId, principal);
		user.setId(existUser.getId());
		userService.update(user);
	}

	private User findUser(String userId) {
		return findUser(userId, null);
	}

	private User findUser(String userId, Principal principal) {

		User user = null;

		if (userId.matches("^\\d+$")) {
			int id = Integer.parseInt(userId);
			user = userService.get(id);
		} else {
			try {
				user = userService.getByUsername(userId);
			} catch (NoResultException ignored) {
			}
		}

		if (user == null) {
			throw new UserNotFoundException();
		}

		if (principal != null && !principal.getName().equals(user.getUsername()))
			throw new NoAccessException();

		return user;
	}
}
