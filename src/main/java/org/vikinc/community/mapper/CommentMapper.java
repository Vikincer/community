package org.vikinc.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.vikinc.community.dto.Comment;
import org.vikinc.community.dto.User;

import java.util.List;

@Mapper
public interface CommentMapper {

    void insert(Comment comment);

    Comment getCommentByID(@Param("parentId") int parentId, @Param("commentator") String commentator);

    List<Comment> getCommentListByID(Integer id);

    //按时间倒序排列
    List<Comment> getCommentListByIDDESC(Integer id);

    //二级评论
    List<Comment> getSecCommentListByTargetId(int id);

    int getCommentCount(int id);

    Comment getCommentByTargetId(Integer targetId);

}
