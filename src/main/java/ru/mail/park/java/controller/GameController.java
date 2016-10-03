package ru.mail.park.java.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.mail.park.java.domain.User;
import ru.mail.park.java.service.UsersService;

@RestController
public class GameController {
	private final UsersService usersService;

	@Autowired
	public GameController(UsersService usersService) {
		this.usersService = usersService;
	}

	@RequestMapping("/")
	public String index(HttpSession session) {
		return "AGRage Team - Backend Server";
	}

	@RequestMapping("/login/{username}")
	public User login(@PathVariable String username, HttpSession session) {
		session.setAttribute("username", username);
		return usersService.ensureUserExists(username);
	}

	@RequestMapping("/me")
	public User me(HttpSession session) {
		String username = (String) session.getAttribute("username");
		if (username == null) {
			throw new NotLoggedInException();
		}
		return usersService.getUser(username);
	}

	@RequestMapping("/gain")
	public User gain(HttpSession session) {
		User me = me(session);
		return me.increasePower();
	}

	@RequestMapping("/fight/{victim}")
	public User fight(@PathVariable String victim, HttpSession session) {
		User me = me(session);
		User other = usersService.getUser(victim);
		if (other == null) {
			throw new UserNotFoundException();
		}
		if (me.getName().equals(other.getName())) {
			throw new IllegalStateException();
		}

		return me.fight(other);
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	private static class NotLoggedInException extends RuntimeException {
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	private static class UserNotFoundException extends RuntimeException {
	}

}
