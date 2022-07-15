package org.vikinc.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.vikinc.community.dto.User;

@Mapper
public interface UserMapper {
    //添加用户
    void insert(User user);
    //根据token查找用户
    User selectByToken(String token);
}
