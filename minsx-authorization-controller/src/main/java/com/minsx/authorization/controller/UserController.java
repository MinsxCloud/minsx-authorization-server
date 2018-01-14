package com.minsx.authorization.controller;

import com.alibaba.fastjson.JSON;
import com.minsx.authorization.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * IndexController
 * Created by Joker on 2017/8/29.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    //----------------------------------测试--------------------------------------

    @ResponseBody
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/getUserName",method = RequestMethod.POST)
    public String getUserName(){
        return "userName = goodsave";
    }
    
    @ResponseBody
    @RequestMapping(value = "/userInfo",method = RequestMethod.GET)
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
