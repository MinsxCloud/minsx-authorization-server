package com.minsx.authorization.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * IndexController
 * Created by Joker on 2017/8/29.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @ResponseBody
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/getUserName",method = RequestMethod.POST)
    public String getUserName(){
        return "userName = goodsave";
    }
    
    @ResponseBody
    @RequestMapping(value = "/userInfo",method = RequestMethod.POST)
    public String userInfo(){
    	Object object =SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	System.out.println(JSON.toJSONString(object));
        return "userInfo = goodsave";
    }
    
    @ResponseBody
    @PreAuthorize("hasAuthority('admins')")
    @RequestMapping(value = "/getUserAge",method = RequestMethod.POST)
    public String getUserAge(){
        return "userAge = 12";
    }

}
