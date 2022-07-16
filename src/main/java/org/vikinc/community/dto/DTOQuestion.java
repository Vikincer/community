package org.vikinc.community.dto;

import lombok.Data;

@Data
public class DTOQuestion {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;     //创建时间
    private Long gmtModified;   //修改时间
    private int creator;
    private int commentCount = 0;   //评论数
    private int likeCount = 0;
    private int viewCount = 0;
    private String tag;
    private User user;
}
