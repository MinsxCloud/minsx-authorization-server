package com.minsx.authorization.common.util;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestUtil {

    public static HttpServletRequest getCurrentHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getCurrentHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static void responseJson(HttpStatus status, Object data) throws IOException {
        HttpServletResponse response = getCurrentHttpServletResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setStatus(status.value());
        response.getWriter().write(JSON.toJSONString(data));
        response.getWriter().flush();
        response.getWriter().close();
    }

    public static void responseJson(ServletResponse response, Object data) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(data));
        response.getWriter().flush();
        response.getWriter().close();
    }

}
