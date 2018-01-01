package com.minsx.authorization.controller;

import com.minsx.authorization.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SystemController {

	private final UserService userService;

	@Autowired
	public SystemController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@PostMapping(value = "/register")
    public ResponseEntity<?> register(HttpServletRequest request,@RequestParam String username, @RequestParam String password) {
        return userService.register(request,username,password);
    }

	@GetMapping(value = "/getEmailCode")
	public ResponseEntity<?> getEmailCode(@RequestParam String username) {
		return userService.getEmailCode(username);
	}


	
}
