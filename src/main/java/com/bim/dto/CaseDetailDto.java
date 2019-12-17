package com.bim.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor @Data
public class CaseDetailDto extends CaseBaseDto{
    private List<PositionDto> positionList;
    private String flowChartURL;
    private List<TaskDto> taskList;
    private String bimAppIntro;
}
