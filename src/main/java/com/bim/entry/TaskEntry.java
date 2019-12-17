package com.bim.entry;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor @Data
public class TaskEntry {
    private String id;
    private String name;
    private Integer order;
    private String description;
    private List<DocumentEntry> documentList;
    private PositionEntry position;
}
