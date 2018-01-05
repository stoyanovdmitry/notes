package com.notes.security;

import com.notes.entity.User;
import com.notes.exception.UserNotFoundException;
import com.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			User user = userService.getByUsername(username);
			return new UserDetailsImpl(user);
		} catch (NoResultException ignored) {
			throw new UserNotFoundException();
		}
	}
}
