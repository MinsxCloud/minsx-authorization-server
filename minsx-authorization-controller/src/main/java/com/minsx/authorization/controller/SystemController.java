package com.minsx.authorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SystemController {

	@RequestMapping(value = "/geetTest", method = RequestMethod.GET)
	public String test() {
		return "geetTest";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/code", method = RequestMethod.GET)
	public String code() {
		return "code";
	}
	
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index page";
	}
	
}
