package org.vikinc.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.vikinc.community.dto.Question;

@Mapper
public interface QuestionMapper {
    //创建 发布问题
    void CreateQuestion(Question question);
}
