package org.vikinc.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vikinc.community.dto.*;
import org.vikinc.community.enums.CommentTypeEnum;
import org.vikinc.community.enums.NotificationStatusEnum;
import org.vikinc.community.enums.NotificationTypeEnum;
import org.vikinc.community.exception.CustomizeErrorCode;
import org.vikinc.community.exception.CustomizeException;
import org.vikinc.community.mapper.CommentMapper;
import org.vikinc.community.mapper.NotificationMapper;
import org.vikinc.community.mapper.QuestionMapper;
import org.vikinc.community.mapper.UserMapper;

import java.util.ArrayList;
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

    @Autowired
    private NotificationMapper notificationMapper;

    private Notification notification = new Notification();

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TAGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        User user = userMapper.getByaccountId(comment.getCommentator());
        if(comment.getType() == CommentTypeEnum.Question.getType()){
            //回复问题
            Question question = questionMapper.getQuestionByID(comment.getParentId());
            if(question == null ){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            question.setCommentCount(1);
            commentMapper.insert(comment);
            questionMapper.incCommentCount(question);

            //创建通知
            notification.setGmtCreate(System.currentTimeMillis());
            notification.setType(NotificationTypeEnum.REPLY_QUESTION.getType());
            notification.setOuterId(question.getId());
            notification.setOuterTitle(question.getTitle());
            notification.setNotifierName(user.getName());
            notification.setNotifier(comment.getCommentator()); //发送人
            notification.setReceiver(question.getCreator()); //接收人
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notificationMapper.insert(notification);
        }else{

            Question question = questionMapper.getQuestionByID(comment.getParentId());
            question.setCommentCount(1);
            questionMapper.incCommentCount(question);
            commentMapper.insert(comment);
            //创建通知
            notification.setGmtCreate(System.currentTimeMillis());
            notification.setType(NotificationTypeEnum.REPLY_COMMENT.getType());
            notification.setOuterId(question.getId());
            Comment targetComment = commentMapper.getCommentByTargetId(comment.getTargetId());      //根据targetId 找到 comment
            String receiver = targetComment.getCommentator();   // 再找commentator
            notification.setNotifier(comment.getCommentator()); //发送人
            notification.setReceiver(receiver); //接收人
            notification.setOuterTitle(question.getTitle());
            notification.setNotifierName(user.getName());
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notificationMapper.insert(notification);
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
            int commentCount = commentMapper.getCommentCount(commentList.get(i).getId());
            if(user != null){
                temp.setUser(user);
            }
            temp.setCommentCount(commentCount);
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
