package org.vikinc.community.controller;

import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.vikinc.community.dto.Question;
import org.vikinc.community.dto.User;
import org.vikinc.community.mapper.QuestionMapper;
import org.vikinc.community.mapper.UserMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 发布页面 publish
 */

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    //发布
    @GetMapping("/publish")
    public String publish (){
        return "publish";
    }

    //提交发布
    @PostMapping("/publish")
    public String createQuestion(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == "" || title == null){
            model.addAttribute("error","标题不能为空");
            return "/publish";
        }
        if(description == "" || title == description){
            model.addAttribute("error","问题补充不能为空");
            return "/publish";
        }
        Cookie[] cookies = request.getCookies();
        User user = null;
        if(cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.selectByToken(token);
                    if(user != null )
                        request.getSession().setAttribute("user",user);
                    break;
                }
            }

        if(user == null ){
           model.addAttribute("error","用户未登录");
        }else{
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setId(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.CreateQuestion(question);
        }

        return "redirect:/";
    }
}
