package org.vikinc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.vikinc.community.dto.DTOQuestion;
import org.vikinc.community.mapper.QuestionMapper;
import org.vikinc.community.service.QuestionService;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String questionList(@PathVariable("id") Integer id,
                               Model model){
        DTOQuestion dtoQuestion = questionService.getQuestionByID(id);
        questionService.incView(id);
        model.addAttribute("question",dtoQuestion);
        return "question";
    }
}
