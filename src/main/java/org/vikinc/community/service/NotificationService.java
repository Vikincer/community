package org.vikinc.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vikinc.community.dto.*;
import org.vikinc.community.enums.NotificationTypeEnum;
import org.vikinc.community.mapper.NotificationMapper;
import org.vikinc.community.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    public DTOPagination getUserRepiesList(String accountId, Integer page, Integer size) {
        List<DTONotification> dtoNotificationList = new ArrayList<>();
        DTOPagination dtoPagination = new DTOPagination();
        Integer total = notificationMapper.count();  //问题总数
        Integer offset = dtoPagination.setPagination(total, page, size);

        List<Notification> notifications = notificationMapper.getUserNotificationLists(accountId,offset,size);

        for (Notification notification : notifications) {
            DTONotification dtoNotification = new DTONotification();
            BeanUtils.copyProperties(notification,dtoNotification);
            dtoNotification.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

            dtoNotificationList.add(dtoNotification);
        }

        dtoPagination.setTList(dtoNotificationList);

        return dtoPagination;
    }

    public Integer getRepiesCount(String accountId) {
        Integer count = notificationMapper.getRepiesCount(accountId);
        return count;
    }
}
