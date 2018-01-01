package com.minsx.authorization.plugin.geetest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Controller
@RequestMapping("/verify")
public class GeeTestController {

    @GetMapping(value = "/initial")
    protected ResponseEntity<?> doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());
        String resStr = "{}";
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("client_type", "web");
        param.put("ip_address", request.getRemoteAddr());
        int gtServerStatus = gtSdk.preProcess(param);
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        resStr = gtSdk.getResponseStr();
        PrintWriter out = response.getWriter();
        out.println(resStr);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
