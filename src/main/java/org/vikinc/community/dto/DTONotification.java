package org.vikinc.community.dto;

import lombok.Data;

@Data
public class DTONotification {
    private int id;
    private Long gmtCreate;
    private Integer status;
    private String notifier;
    private String notifierName;
    private String outerTitle;
    private Integer outerId;
    private String typeName;
    private String type;
}
