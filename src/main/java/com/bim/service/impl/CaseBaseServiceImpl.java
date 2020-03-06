package com.bim.service.impl;

import com.bim.dao.CaseDao;
import com.bim.dto.CaseBaseDto;
import com.bim.entry.CaseEntry;
import com.bim.service.CaseBaseService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("caseBaseService")
public class CaseBaseServiceImpl implements CaseBaseService {

    @Autowired
    private CaseDao caseDao;

    @Value("#{fileSaveProperties['img.caseImgSaveDir']}")
    private String caseImgSaveDir;

    @Value("#{siteProperties['index.contentSize']}")
    private int contentSizeInIndex;

    @Override
    public List<CaseBaseDto> getCasesSortByName() {
        int defaultSize = (contentSizeInIndex > 0)? contentSizeInIndex: 7;
        return getCasesSortByNameAndSizeIs(defaultSize);
    }

    @Override
    public List<CaseBaseDto> getCasesSortByName(int size) {
        return getCasesSortByNameAndSizeIs(size);
    }

    @Override
    public List<CaseBaseDto> getCasesSortByDate() {
        int defaultSize = (contentSizeInIndex > 0)? contentSizeInIndex: 7;
        return getCasesSortByDateAndSizeIs(defaultSize);
    }

    @Override
    public List<CaseBaseDto> getCasesSortByDate(int size) {
        return getCasesSortByDateAndSizeIs(size);
    }

    @Override
    public List<CaseBaseDto> getCasesSortByKeyword(String keyword) {
        List<Document> caseDocumentList = caseDao.selectCaseLikeKeyword(keyword);
        return convertCaseDocumentToDto(caseDocumentList);
    }

    private List<CaseBaseDto> getCasesSortByNameAndSizeIs(int size){
        List<CaseBaseDto> results =  getCasesAndSizeIs(size);
        results.sort(Comparator.comparing(CaseBaseDto::getTitle));
        return results;
    }

    private List<CaseBaseDto> getCasesSortByDateAndSizeIs(int size){
        List<CaseBaseDto> results =  getCasesAndSizeIs(size);
        results.sort(Comparator.comparing(CaseBaseDto::getCreateDate).reversed());
        return results;
    }

    //get by id
    @Override
    public CaseEntry getCaseById(String id){
        CaseBaseDto dto = new CaseBaseDto();
        CaseEntry entry = caseDao.selectCaseById(id);
        return entry;
    }
    //getDTO by id
    //TODO: implement
    @Override
    public CaseBaseDto getCaseBaseById(String id){
        CaseBaseDto dto = new CaseBaseDto();
        dto = caseDao.selectCaseDtoById(id);
        return dto;
    }
    private List<CaseBaseDto> getCasesAndSizeIs(int size){
        List<Document> caseDocumentList = caseDao.selectAllCase();
        return convertCaseDocumentToDto(caseDocumentList, size);
    }

    private List<CaseBaseDto> convertCaseDocumentToDto(List<Document> caseDocumentList){
        return convertCaseDocumentToDto(caseDocumentList, caseDocumentList.size());
    }

    private List<CaseBaseDto> convertCaseDocumentToDto(List<Document> caseDocumentList, int size){
        return caseDocumentList.stream().map(e -> {
            CaseBaseDto caseBase = new CaseBaseDto();
            caseBase.setId(e.getObjectId("_id").toHexString());
            caseBase.setTitle(e.getString("title"));
            Document workflow = (Document) e.get("workflow");
            caseBase.setIntroduction(workflow.getString("introduction"));
            caseBase.setCreateDate(e.getDate("createDate"));
            caseBase.setImgUrl(caseImgSaveDir + "/" + e.getString("introImgName"));
            return caseBase;
        }).limit(size).collect(Collectors.toList());
    }
}
