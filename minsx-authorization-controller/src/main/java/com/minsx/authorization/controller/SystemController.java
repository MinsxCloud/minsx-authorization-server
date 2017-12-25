package com.minsx.authorization.controller;

import com.minsx.authorization.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SystemController {


	@Autowired
	UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password) {
        return userService.register(username,password);
    }

	@GetMapping(value = "/getEmailCode")
	public ResponseEntity<?> getEmailCode(@RequestParam String username) {
		return userService.getEmailCode(username);
	}


	@RequestMapping(value = "/geetTest", method = RequestMethod.GET)
	public String test() {
		return "geetTest";
	}


	
}
