package org.vikinc.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vikinc.community.dto.Comment;
import org.vikinc.community.dto.Question;
import org.vikinc.community.enums.CommentTypeEnum;
import org.vikinc.community.exception.CustomizeErrorCode;
import org.vikinc.community.exception.CustomizeException;
import org.vikinc.community.mapper.CommentMapper;
import org.vikinc.community.mapper.QuestionMapper;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TAGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.getCommentByID(comment.getParentId());
            if(dbComment == null)
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            commentMapper.insert(comment);
        }else{
            //回复问题
            Question question = questionMapper.getQuestionByID(comment.getParentId());
            if(question == null ){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            question.setCommentCount(1);
            commentMapper.insert(comment);
            questionMapper.incCommentCount(question);
        }
    }
}
