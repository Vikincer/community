package org.vikinc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.vikinc.community.dto.DTONotification;
import org.vikinc.community.dto.DTOPagination;
import org.vikinc.community.dto.User;
import org.vikinc.community.mapper.QuestionMapper;
import org.vikinc.community.mapper.UserMapper;
import org.vikinc.community.service.NotificationService;
import org.vikinc.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          @RequestParam(name = "serch",required = false) String serch,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null)
            return "redirect:/";

        Integer repiesCount = notificationService.getRepiesCount(user.getAccountId());
        Integer questionCount = questionService.getquestionCount(user.getAccountId());
        model.addAttribute("repiesCount",repiesCount);
        model.addAttribute("questionCount",questionCount);
        model.addAttribute("pro","pro");

        if(action.equals("question")){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");

            DTOPagination dtoPagination = questionService.getUserQuestionList(serch,user.getAccountId(), page, size);
            model.addAttribute("dtoPagination",dtoPagination);
            model.addAttribute("serch",serch);
            return "profile";
        }else if (action.equals("repies")){
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","最新回复");

            DTOPagination dtoPagination = notificationService.getUserRepiesList(user.getAccountId(), page, size);
            model.addAttribute("dtoPagination",dtoPagination);
        }

        return "profile";
    }

    @GetMapping("/profile")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "serch",required = false) String serch){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null)
            return "redirect:/";
        DTOPagination dtoPagination = questionService.getUserQuestionList(serch,user.getAccountId(), page, size);

        Integer repiesCount = notificationService.getRepiesCount(user.getAccountId());
        Integer questionCount = questionService.getquestionCount(user.getAccountId());
        model.addAttribute("repiesCount",repiesCount);
        model.addAttribute("questionCount",questionCount);

        model.addAttribute("dtoPagination",dtoPagination);
        model.addAttribute("section","question");
        model.addAttribute("pro","pro");
        model.addAttribute("serch",serch);

        return "profile";
    }

}
