package com.bim.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@Data
public class TaskDto {
    private String id;
    private String name;
    private Integer order;
    private String description;
    private List<DocumentDto> documentList;
}
