package org.vikinc.community.dto;

import lombok.Data;

/**
 * 网站用户实体类
 */

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;   //github的id
    private String token;       //UUID 随机
    private Long gmtCreate;     //创建时间
    private Long gmtModified;   //修改时间
    private String avatarUrl;   //头像avatar_url

}
