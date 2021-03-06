package org.vikinc.community.dto;

import lombok.Data;

@Data
public class Notification {
    private int id;
    private String notifier;
    private String receiver;
    private Integer outerId;
    private String type;
    private Long gmtCreate;
    private int status;
    private String notifierName;
    private String outerTitle;
}
