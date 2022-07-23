package org.vikinc.community.dto;

import lombok.Data;

@Data
public class DTONotification {
    private int id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}
