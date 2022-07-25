package org.vikinc.community.dto;

import lombok.Data;

@Data
public class DTOQuestionQuery {
    private String serch;
    private Integer page;
    private Integer size;
    private String accountId;
}
