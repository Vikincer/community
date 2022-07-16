package org.vikinc.community.mapper;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.vikinc.community.dto.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {
    //获取所有列表
    List<Question> getALLList(@Param("offset") Integer offset, @Param("size") Integer size);
    //创建 发布问题
    void CreateQuestion(Question question);

    Integer count();
}
