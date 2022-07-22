package org.vikinc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.vikinc.community.dto.DTOComment;
import org.vikinc.community.dto.DTOQuestion;
import org.vikinc.community.mapper.QuestionMapper;
import org.vikinc.community.service.CommentService;
import org.vikinc.community.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    /**
     *
     * @param id question.id
     * @param model
     * @return
     */
    @GetMapping("/question/{id}")
    public String questionList(@PathVariable("id") Integer id,
                               Model model){
        DTOQuestion dtoQuestion = questionService.getQuestionByID(id);

        List<DTOComment> dtoCommentList = commentService.getCommentListByQuestionId(id);

        List<DTOQuestion> dtoTagList = questionService.getTagRelated(dtoQuestion);

        questionService.incView(id);
        model.addAttribute("dtoTagList",dtoTagList);
        model.addAttribute("question",dtoQuestion);
        model.addAttribute("dtoCommentList",dtoCommentList);


        return "question";
    }
}
