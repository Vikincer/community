package org.vikinc.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vikinc.community.dto.DTOPagination;
import org.vikinc.community.dto.DTOQuestion;
import org.vikinc.community.dto.Question;
import org.vikinc.community.dto.User;
import org.vikinc.community.mapper.QuestionMapper;
import org.vikinc.community.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public DTOPagination getALLList(Integer page, Integer size) {
        List<DTOQuestion> dtoQuestionList = new ArrayList<>();
        DTOPagination dtoPagination = new DTOPagination();
        Integer total = questionMapper.count();  //问题总数
        Integer offset = dtoPagination.setPagination(total, page, size);

        List<Question> questionList = questionMapper.getALLList(offset,size);

        for (Question question : questionList) {
            User user = userMapper.getByaccountId(question.getCreator());
            if(user!= null){
                DTOQuestion dtoQuestion = new DTOQuestion();
                BeanUtils.copyProperties(question,dtoQuestion);
                dtoQuestion.setUser(user);
                dtoQuestionList.add(dtoQuestion);
            }
        }

        dtoPagination.setQuestionList(dtoQuestionList);

        return dtoPagination;
    }


    public DTOPagination getUserQuestionList(String accountId, Integer page, Integer size) {
        List<DTOQuestion> dtoQuestionList = new ArrayList<>();
        DTOPagination dtoPagination = new DTOPagination();
        Integer total = questionMapper.count();  //问题总数
        Integer offset = dtoPagination.setPagination(total, page, size);

        List<Question> questionList = questionMapper.getUserQuestionLists(accountId,offset,size);

        for (Question question : questionList) {
            User user = userMapper.getByaccountId(question.getCreator());
            if(user!= null){
                DTOQuestion dtoQuestion = new DTOQuestion();
                BeanUtils.copyProperties(question,dtoQuestion);
                dtoQuestion.setUser(user);
                dtoQuestionList.add(dtoQuestion);
            }
        }

        dtoPagination.setQuestionList(dtoQuestionList);

        return dtoPagination;
    }
}
