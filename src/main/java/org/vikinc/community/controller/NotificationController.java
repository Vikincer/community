package org.vikinc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.vikinc.community.dto.DTONotification;
import org.vikinc.community.dto.User;
import org.vikinc.community.enums.NotificationTypeEnum;
import org.vikinc.community.service.NotificationService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") int id){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null)
            return "redirect:/";
        DTONotification dtoNotification = notificationService.read(id,user);

        if(NotificationTypeEnum.REPLY_COMMENT.getType().equals(dtoNotification.getType())
                || NotificationTypeEnum.REPLY_QUESTION.getType().equals(dtoNotification.getType())){
            return "redirect:/question/" + dtoNotification.getOuterId();
        }else{
            return "redirect:/";
        }
    }
}
