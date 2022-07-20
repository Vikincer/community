package org.vikinc.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.vikinc.community.dto.User;

import java.util.List;

@Mapper
public interface UserMapper {
    //添加用户
    void insert(User user);
    //根据token查找用户
    User getByToken(String token);

    User getByaccountId(String creator);

    //查询用户
    List<User> getUserList();

    List<User> getUserListById(List<String> userIds);
}
