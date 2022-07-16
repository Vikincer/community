package org.vikinc.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class logOutController {
    @GetMapping("/logout")
    String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma","No-cache");

        response.setHeader("Cache-Control","no-cache");

        response.setDateHeader("Expires", 0);

        response.flushBuffer();

        Cookie killMyCookie = new Cookie("mycookie", null);

        killMyCookie.setMaxAge(0);

        killMyCookie.setPath("/");

        response.addCookie(killMyCookie);

        request.getSession().invalidate();

        return "/";
    }
}
