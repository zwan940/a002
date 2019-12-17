package com.bim.entry;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @Data
public class DocumentEntry {
    private String id;
    private Integer type;
    private String name;
}
