package org.vikinc.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<DTOQuestion> getALLList() {
        List<Question> questionList = questionMapper.getALLList();
        List<DTOQuestion> dtoQuestionList = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.getByaccountId(question.getCreator());
            DTOQuestion dtoQuestion = new DTOQuestion();
            BeanUtils.copyProperties(question,dtoQuestion);
            dtoQuestion.setUser(user);
            dtoQuestionList.add(dtoQuestion);
        }
        return dtoQuestionList;
    }
}
