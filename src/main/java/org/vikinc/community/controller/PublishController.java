package org.vikinc.community.controller;

import jdk.nashorn.internal.runtime.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.vikinc.community.dto.DTOQuestion;
import org.vikinc.community.dto.Question;
import org.vikinc.community.dto.User;
import org.vikinc.community.mapper.QuestionMapper;
import org.vikinc.community.mapper.UserMapper;
import org.vikinc.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 发布页面 publish
 */

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    //发布
    @GetMapping("/publish")
    public String publish (){
        return "publish";
    }

    //提交发布
    @PostMapping("/publish")
    public String createQuestion(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Integer id,
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

        User user = (User) request.getSession().getAttribute("user");

        if(user == null ){
           model.addAttribute("error","用户未登录");
        }else{
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user.getAccountId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setId(id);

            questionService.createOrUpdateQuestion(question);
        }

        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model){
        DTOQuestion question = questionService.getQuestionByID(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",id);


        return "publish";
    }
}
