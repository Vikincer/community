package org.vikinc.community.dto;

import lombok.Data;

@Data
public class DTOComment {
    private int id;
    private int parentId;
    private Integer type;
    private String commentator;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer targetId;
    private User user;
}
