package com.bim.entry;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor @Data
public class CaseEntry {
    private String id;
    private String title;
    private String introImgName;
    private String bimId;
    private Date createDate;
    private WorkflowEntry workflow;
    private String introduction;
    private String tableContent;
}
