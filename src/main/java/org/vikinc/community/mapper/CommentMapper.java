package org.vikinc.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.vikinc.community.dto.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {

    void insert(Comment comment);

    Comment getCommentByID(@Param("parentId") int parentId, @Param("commentator") String commentator);

    List<Comment> getCommentListByID(Integer id);
}
