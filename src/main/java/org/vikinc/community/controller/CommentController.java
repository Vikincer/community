package org.vikinc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.*;
import org.vikinc.community.dto.*;
import org.vikinc.community.exception.CustomizeErrorCode;
import org.vikinc.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object comment(@RequestBody DTOCreateComment dtoCreateComment,
                          HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null)
            return DTOResult.errorOf(CustomizeErrorCode.NO_LOGIN);
        if(dtoCreateComment == null || StringUtils.isBlank(dtoCreateComment.getContent()))
            return DTOResult.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);


        Comment comment = new Comment();
        if(dtoCreateComment.getTargetId() != null)
            comment.setTargetId(dtoCreateComment.getTargetId());
        comment.setParentId(dtoCreateComment.getParentId());
        comment.setContent(dtoCreateComment.getContent());
        comment.setType(dtoCreateComment.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getAccountId());
        commentService.insert(comment);
        return DTOResult.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public DTOResult<List<DTOComment>> comment(@PathVariable(name = "id") int id){
        List<DTOComment> dtoCommentList = commentService.getSecCommentListByTargetId(id);
        return DTOResult.okOf(dtoCommentList);
    }
}
