package org.vikinc.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.vikinc.community.dto.User;

@Mapper
public interface UserMapper {
    void insert(User user);
}
