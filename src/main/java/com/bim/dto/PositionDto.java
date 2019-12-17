package com.bim.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @Data
public class PositionDto {
    private String id;
    private Integer order; // 这个点所属任务步骤的序号
    private Double posX;
    private Double posY;
}
