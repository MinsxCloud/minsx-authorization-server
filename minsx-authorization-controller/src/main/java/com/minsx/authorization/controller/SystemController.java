package com.minsx.authorization.controller;

import com.minsx.authorization.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/system")
public class SystemController {

	private final UserService userService;

	@Autowired
	public SystemController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/register")
    public ResponseEntity<?> register(HttpServletRequest request,@RequestParam String username,@RequestParam String email, @RequestParam String password) {
        return userService.register(request,username,email,password);
    }

	@GetMapping(value = "/getEmailCode")
	public ResponseEntity<?> getEmailCode(HttpServletRequest request,@RequestParam String username) {
		return userService.getEmailCode(request,username);
	}

	@PutMapping(value = "/changePass")
	public ResponseEntity<?> changePass(HttpServletRequest request, @RequestParam String username,@RequestParam String password,String emailCode) {
		return userService.changePass(request,username,password,emailCode);
	}

	
}
