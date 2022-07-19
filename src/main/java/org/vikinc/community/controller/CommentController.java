package org.vikinc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.vikinc.community.dto.Comment;
import org.vikinc.community.dto.DTOComment;
import org.vikinc.community.mapper.CommentMapper;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object comment(@RequestBody DTOComment dtoComment){
        Comment comment = new Comment();
        comment.setParentId(dtoComment.getParentId());
        comment.setContent(dtoComment.getContent());
        comment.setType(dtoComment.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator("1");
        commentMapper.insert(comment);
        return null;
    }
}
