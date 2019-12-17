package com.bim.service;

import com.bim.dto.CaseBaseDto;

import java.util.List;

public interface CaseBaseService {

    List<CaseBaseDto> getCasesSortByName();
    List<CaseBaseDto> getCasesSortByName(int size);

    List<CaseBaseDto> getCasesSortByDate();
    List<CaseBaseDto> getCasesSortByDate(int size);

    List<CaseBaseDto> getCasesSortByKeyword(String keyword);
}
