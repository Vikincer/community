package org.vikinc.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class Comment {
    private int id;
    private int parentId;
    private Integer type;
    private String commentator;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer targetId;
}
