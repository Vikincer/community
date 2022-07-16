package org.vikinc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.vikinc.community.dto.DTOQuestion;
import org.vikinc.community.dto.Question;
import org.vikinc.community.dto.User;
import org.vikinc.community.mapper.QuestionMapper;
import org.vikinc.community.mapper.UserMapper;
import org.vikinc.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页
 */

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        //验证登录是否成功
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0)
        for (Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.getByToken(token);
                if(user!= null )
                    request.getSession().setAttribute("user",user);
                break;
            }
        }

        List<DTOQuestion> dtoQuestionList = questionService.getALLList();
        model.addAttribute("dtoQuestionList",dtoQuestionList);

        return "index";
    }
}
