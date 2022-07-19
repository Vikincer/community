package org.vikinc.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.vikinc.community.dto.Comment;

@Mapper
public interface CommentMapper {

    void insert(Comment comment);

    Comment getCommentByID(int parentId);
}
