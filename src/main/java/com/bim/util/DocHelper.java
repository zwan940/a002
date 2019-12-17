package com.bim.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DocHelper {
    @Value("#{fileSaveProperties['doc.ifc']}")
    private String ifcSaveDir;

    @Value("#{fileSaveProperties['doc.pdf']}")
    private String pdfSaveDir;

    private boolean notInit = true;

    private Map<Integer, String> TYPETABLE = new HashMap<>();
    private Map<Integer, String> PATHTABLE = new HashMap<>();

    private void init(){
        if (notInit) {
            TYPETABLE.put(1, "pdf"); PATHTABLE.put(1, pdfSaveDir);
            TYPETABLE.put(2, "ifc"); PATHTABLE.put(2, ifcSaveDir);
            notInit = false;
        }
    }

    public String getTypeByMark(Integer mark){
        init();
        return TYPETABLE.get(mark);
    }

    public String getPathByMark(Integer mark){
        init();
        return PATHTABLE.get(mark);
    }
}
