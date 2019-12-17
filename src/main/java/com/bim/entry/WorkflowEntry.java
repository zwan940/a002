package com.bim.entry;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor @Data
public class WorkflowEntry {
    private String id;
    private Integer taskNum;
    private String introduction;
    private List<TaskEntry> taskList;
    private String flowChartName;
}
