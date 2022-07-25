package org.vikinc.community.mapper;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.vikinc.community.dto.DTOQuestion;
import org.vikinc.community.dto.DTOQuestionQuery;
import org.vikinc.community.dto.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {
    //获取所有列表
    List<Question> getALLList(@Param("offset") Integer offset, @Param("size") Integer size);
    //创建 发布问题
    void CreateQuestion(Question question);

    Integer count();

    List<Question> getUserQuestionLists(@Param("accountId") String accountId, @Param("offset") Integer offset, @Param("size") Integer size);

    Question getQuestionByID(@Param("id")  Integer id);

    int update(Question question);

    void incView(Question updateQuestion);

    int incCommentCount(Question updateQuestion);

    List<Question> getTagRelated(Question question);

    Integer getQuestionCountByUser(String accountId);

    Integer countBySerch(DTOQuestionQuery dtoQuestionQuery);

    List<Question> getALLListBySerch(DTOQuestionQuery dtoQuestionQuery);

    Integer countProBySerch(DTOQuestionQuery dtoQuestionQuery);

    List<Question> getUserQuestionListsBySerch(DTOQuestionQuery dtoQuestionQuery);
}
