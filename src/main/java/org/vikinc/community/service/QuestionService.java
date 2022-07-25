package org.vikinc.community.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vikinc.community.dto.*;
import org.vikinc.community.exception.CustomizeErrorCode;
import org.vikinc.community.exception.CustomizeException;
import org.vikinc.community.mapper.NotificationMapper;
import org.vikinc.community.mapper.QuestionMapper;
import org.vikinc.community.mapper.UserMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    //获取所有问题列表
    public DTOPagination getALLList(String serch,Integer page, Integer size) {
        serch = getSerch(serch);

        List<DTOQuestion> dtoQuestionList = new ArrayList<>();
        DTOPagination dtoPagination = new DTOPagination();

        DTOQuestionQuery dtoQuestionQuery = new DTOQuestionQuery();
        dtoQuestionQuery.setSerch(serch);
        Integer total = questionMapper.countBySerch(dtoQuestionQuery);//问题总数

        Integer offset = dtoPagination.setPagination(total, page, size);
        dtoQuestionQuery.setPage(offset);
        dtoQuestionQuery.setSize(size);
        List<Question> questionList = questionMapper.getALLListBySerch(dtoQuestionQuery);

        for (Question question : questionList) {
            User user = userMapper.getByaccountId(question.getCreator());
            DTOQuestion dtoQuestion = new DTOQuestion();
            BeanUtils.copyProperties(question,dtoQuestion);
            if(user!= null){
                dtoQuestion.setUser(user);
            }
            dtoQuestionList.add(dtoQuestion);
        }

        dtoPagination.setTList(dtoQuestionList);

        return dtoPagination;
    }

    //获取当前用户发布的所有问题列表
    public DTOPagination getUserQuestionList(String serch,String accountId, Integer page, Integer size) {
        serch = getSerch(serch);

        List<DTOQuestion> dtoQuestionList = new ArrayList<>();
        DTOPagination dtoPagination = new DTOPagination();

        DTOQuestionQuery dtoQuestionQuery = new DTOQuestionQuery();
        dtoQuestionQuery.setSerch(serch);
        dtoQuestionQuery.setAccountId(accountId);
        Integer proTotal = questionMapper.countProBySerch(dtoQuestionQuery);//个人问题总数
        Integer offset = dtoPagination.setPagination(proTotal, page, size);

        dtoQuestionQuery.setPage(offset);
        dtoQuestionQuery.setSize(size);
        List<Question> questionList = questionMapper.getUserQuestionListsBySerch(dtoQuestionQuery);

        for (Question question : questionList) {
            User user = userMapper.getByaccountId(question.getCreator());
            DTOQuestion dtoQuestion = new DTOQuestion();
            BeanUtils.copyProperties(question,dtoQuestion);
            dtoQuestion.setQuestionCount(proTotal);
            if(user!= null)
                dtoQuestion.setUser(user);
            dtoQuestionList.add(dtoQuestion);
        }

        dtoPagination.setTList(dtoQuestionList);

        return dtoPagination;
    }

    private String getSerch(String serch) {
        if(StringUtils.isNotBlank(serch)){
            String[] tags = StringUtils.split(serch, " ");
            return Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        return "";
    }

    //获取question详情页
    public DTOQuestion getQuestionByID(Integer id) {
        DTOQuestion dtoQuestion = new DTOQuestion();
        Question question = questionMapper.getQuestionByID(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        String creator = question.getCreator();
        User user = userMapper.getByaccountId(creator);
        BeanUtils.copyProperties(question,dtoQuestion);
        dtoQuestion.setUser(user);

        return dtoQuestion;
    }
    //创建或更新question
    public void createOrUpdateQuestion(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.CreateQuestion(question);
        }else{
            //更新
            question.setGmtModified(question.getGmtCreate());
            int updated = questionMapper.update(question);
            if(updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    //自增长浏览量
    public void incView(Integer id) {
        Question updateQuestion = new Question();
        updateQuestion.setId(id);
        updateQuestion.setViewCount(1);
        questionMapper.incView(updateQuestion);
    }

    public List<DTOQuestion> getTagRelated(DTOQuestion dtoTag) {
        if(StringUtils.isBlank(dtoTag.getTag()))
            return new ArrayList<>();

        String[] tag = StringUtils.split(dtoTag.getTag(), ",");
        String regexpTag = Arrays.stream(tag).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(dtoTag.getId());
        question.setTag(regexpTag);
        List<Question> questionList = questionMapper.getTagRelated(question);
        List<DTOQuestion> dtoQuestionList = questionList.stream().map(q -> {
            DTOQuestion dtoQuestion = new DTOQuestion();
            BeanUtils.copyProperties(q,dtoQuestion);
            return dtoQuestion;
        }).collect(Collectors.toList());
        return dtoQuestionList;
    }

    public Integer getquestionCount(String accountId) {
        Integer count = questionMapper.getQuestionCountByUser(accountId);
        return count;
    }
}
