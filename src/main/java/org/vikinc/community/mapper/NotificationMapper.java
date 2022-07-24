package org.vikinc.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.vikinc.community.dto.Notification;
import org.vikinc.community.dto.User;

import java.util.List;

@Mapper
public interface NotificationMapper {

    void insert(Notification notification);

    Integer getRepiesCount(String accountId);

    Integer count();

    List<Notification> getUserNotificationLists(@Param("accountId") String accountId, @Param("offset") Integer offset, @Param("size") Integer size);

    Notification getNotificationByID(int id);

    void updateByID(Notification notification);
}
