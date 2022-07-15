package org.vikinc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.vikinc.community.dto.User;
import org.vikinc.community.mapper.UserMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 首页
 */

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        //验证登录是否成功
        Cookie[] cookies = request.getCookies();
        if(cookies != null)
        for (Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.selectByToken(token);
                if(user!= null )
                    request.getSession().setAttribute("user",user);
                break;
            }
        }

        return "index";
    }
}
