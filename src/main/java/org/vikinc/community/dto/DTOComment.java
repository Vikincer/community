package org.vikinc.community.dto;

import lombok.Data;

@Data
public class DTOComment {
    private int parentId;
    private String content;
    private Integer type;
}
