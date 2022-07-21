package org.vikinc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.*;
import org.vikinc.community.dto.Comment;
import org.vikinc.community.dto.DTOCreateComment;
import org.vikinc.community.dto.DTOResult;
import org.vikinc.community.dto.User;
import org.vikinc.community.exception.CustomizeErrorCode;
import org.vikinc.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;

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
        comment.setParentId(dtoCreateComment.getParentId());
        comment.setContent(dtoCreateComment.getContent());
        comment.setType(dtoCreateComment.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getAccountId());
        commentService.insert(comment);
        return DTOResult.okOf();
    }
}
