package org.vikinc.community.dto;

import lombok.Data;

@Data
public class Comment {
    private int id;
    private Long parentId;
    private Integer type;
    private String commentator;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
}
