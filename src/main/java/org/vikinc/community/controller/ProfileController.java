package org.vikinc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.vikinc.community.dto.DTOPagination;
import org.vikinc.community.dto.User;
import org.vikinc.community.mapper.UserMapper;
import org.vikinc.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null)
            return "redirect:/";

        DTOPagination dtoPagination = questionService.getUserQuestionList(user.getAccountId(), page, size);
        model.addAttribute("dtoPagination",dtoPagination);
        if(action.equals("question")){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
            return "profile";
        }else if (action.equals("repies")){
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","最新回复");
        }

        return "profile";
    }

}
