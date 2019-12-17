package com.bim.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor @Data
public class CaseBaseDto {
    private String id;
    private String title;
    private String introduction;
    private Date createDate;
    private String imgUrl;
}
