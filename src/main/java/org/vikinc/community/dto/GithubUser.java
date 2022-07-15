package org.vikinc.community.dto;

import lombok.Data;

/**
 * Github 用户实体类
 */

@Data
public class GithubUser {
    private String login;   //用户名
    private Long id;    //id
    private String bio;     //详情
    private String avatarUrl;


}
