package org.vikinc.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vikinc.community.dto.Comment;
import org.vikinc.community.dto.DTOComment;
import org.vikinc.community.dto.Question;
import org.vikinc.community.dto.User;
import org.vikinc.community.enums.CommentTypeEnum;
import org.vikinc.community.exception.CustomizeErrorCode;
import org.vikinc.community.exception.CustomizeException;
import org.vikinc.community.mapper.CommentMapper;
import org.vikinc.community.mapper.QuestionMapper;
import org.vikinc.community.mapper.UserMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TAGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.Question.getType()){
            //回复问题
            Question question = questionMapper.getQuestionByID(comment.getParentId());
            if(question == null ){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            question.setCommentCount(1);
            commentMapper.insert(comment);
            questionMapper.incCommentCount(question);
        }else{
            Question question = questionMapper.getQuestionByID(comment.getParentId());
            question.setCommentCount(1);
            questionMapper.incCommentCount(question);
            commentMapper.insert(comment);
        }
    }

    public List<DTOComment> getCommentListByQuestionId(Integer id) {
        List<Comment> commentList = commentMapper.getCommentListByIDDESC(id);
        if(commentList.size() == 0){
            return new ArrayList<>();
        }

        Set<String> commentators = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<String> userIds = new ArrayList();
        userIds.addAll(commentators);
//        List<User> userList = userMapper.getUserListById(userIds);

        List<DTOComment> dtoComment = new ArrayList<>(commentList.size());

        for (int i = 0; i<commentList.size(); i++){
            DTOComment temp = new DTOComment();
            BeanUtils.copyProperties(commentList.get(i),temp);
            User user = userMapper.getByaccountId(commentList.get(i).getCommentator());
            if(user != null){
                temp.setUser(user);
            }
            dtoComment.add(temp);
        }
        return dtoComment;
    }

    public List<DTOComment> getSecCommentListByTargetId(int id) {
        List<Comment> commentList = commentMapper.getSecCommentListByTargetId(id);
        if(commentList.size() == 0){
            return new ArrayList<>();
        }

        List<DTOComment> dtoComment = new ArrayList<>(commentList.size());
        for (int i = 0; i<commentList.size(); i++){
            DTOComment temp = new DTOComment();
            BeanUtils.copyProperties(commentList.get(i),temp);
            User user = userMapper.getByaccountId(commentList.get(i).getCommentator());
            if(user != null){
                temp.setUser(user);
            }
            dtoComment.add(temp);
        }
        return dtoComment;
    }
}
