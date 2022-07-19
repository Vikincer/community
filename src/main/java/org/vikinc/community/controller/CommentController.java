package org.vikinc.community.controller;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.vikinc.community.dto.Comment;
import org.vikinc.community.dto.DTOComment;
import org.vikinc.community.dto.DTOResult;
import org.vikinc.community.dto.User;
import org.vikinc.community.exception.CustomizeErrorCode;
import org.vikinc.community.mapper.CommentMapper;
import org.vikinc.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object comment(@RequestBody DTOComment dtoComment,
                          HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null)
            return DTOResult.errorOf(CustomizeErrorCode.NO_LOGIN);

        Comment comment = new Comment();
        comment.setParentId(dtoComment.getParentId());
        comment.setContent(dtoComment.getContent());
        comment.setType(dtoComment.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator("1");
        commentService.insert(comment);
        return DTOResult.okOf();
    }
}
