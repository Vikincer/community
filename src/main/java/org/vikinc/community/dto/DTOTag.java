package org.vikinc.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class DTOTag {
    private String categoryName;
    private List<String> tags;
}
