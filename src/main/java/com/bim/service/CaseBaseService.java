package com.bim.service;

import com.bim.dto.CaseBaseDto;
import com.bim.entry.CaseEntry;

import java.util.List;

public interface CaseBaseService {

    List<CaseBaseDto> getCasesSortByName();
    List<CaseBaseDto> getCasesSortByName(int size);

    List<CaseBaseDto> getCasesSortByDate();
    List<CaseBaseDto> getCasesSortByDate(int size);

    List<CaseBaseDto> getCasesSortByKeyword(String keyword);

    CaseEntry getCaseById(String id);

    CaseBaseDto getCaseBaseById(String id);
}
