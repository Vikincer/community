package org.vikinc.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vikinc.community.dto.*;
import org.vikinc.community.enums.NotificationStatusEnum;
import org.vikinc.community.enums.NotificationTypeEnum;
import org.vikinc.community.exception.CustomizeErrorCode;
import org.vikinc.community.exception.CustomizeException;
import org.vikinc.community.mapper.NotificationMapper;
import org.vikinc.community.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            String typeName = NotificationTypeEnum.nameOfType(notification.getType());
            dtoNotification.setTypeName(typeName);
            dtoNotificationList.add(dtoNotification);
        }

        dtoPagination.setTList(dtoNotificationList);

        return dtoPagination;
    }

    public Integer getRepiesCount(String accountId) {
        Integer count = notificationMapper.getRepiesCount(accountId);
        return count;
    }

    public DTONotification read(int id, User user) {
        Notification notification = notificationMapper.getNotificationByID(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getAccountId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByID(notification);

        DTONotification notificationDTO = new DTONotification();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;

    }
}
