package org.vikinc.community.dto;

import lombok.Data;

/**
 * Github OAuth 实体类
 */

@Data
public class DTOAccessToken {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;



}
