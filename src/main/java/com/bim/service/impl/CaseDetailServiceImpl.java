package com.bim.service.impl;

import com.bim.dao.BimAppDao;
import com.bim.dao.CaseDao;
import com.bim.dto.CaseDetailDto;
import com.bim.dto.DocumentDto;
import com.bim.dto.PositionDto;
import com.bim.dto.TaskDto;
import com.bim.entry.CaseEntry;
import com.bim.entry.DocumentEntry;
import com.bim.entry.WorkflowEntry;
import com.bim.service.CaseDetailService;
import com.bim.util.DocHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("caseDetailService")
public class CaseDetailServiceImpl implements CaseDetailService {
    private CaseDao caseDao;
    private DocHelper docHelper;
    private BimAppDao bimAppDao;

    @Value("#{fileSaveProperties['img.caseImgSaveDir']}")
    private String caseImgSaveDir;

    @Value("#{fileSaveProperties['img.flowChartSaveDir']}")
    private String flowChartSaveDir;

    private final String TASKLIST = "taskList";
    private final String POSLIST = "posList";

    @Autowired
    public CaseDetailServiceImpl(CaseDao caseDao,
                                 DocHelper docHelper, BimAppDao bimAppDao) {
        this.caseDao = caseDao;
        this.docHelper = docHelper;
        this.bimAppDao = bimAppDao;
    }

    @Override
    public CaseDetailDto getCaseDetailByCaseId(String caseId) {
        CaseEntry targetCase = caseDao.selectCaseById(caseId);
        return generateCaseDetailBy(targetCase);
    }

    private CaseDetailDto generateCaseDetailBy(CaseEntry caseEntry){
        if (caseEntry == null){
            return null;
        }

        CaseDetailDto result = new CaseDetailDto();
        Map<String, List> taskAndPosContainer =
                generateTaskAndPosDtosBy(caseEntry.getWorkflow());
        List<TaskDto> taskList = (taskAndPosContainer.get(TASKLIST));
        List<PositionDto> positionList = taskAndPosContainer.get(POSLIST);
        String bimAppIntro = bimAppDao.selectById(caseEntry.getBimId()).getDescription();

        result.setId(caseEntry.getId());
        result.setTitle(caseEntry.getTitle());
        result.setIntroduction(caseEntry.getWorkflow().getIntroduction());
        result.setCreateDate(caseEntry.getCreateDate());
        result.setImgUrl(caseImgSaveDir + "/" + caseEntry.getIntroImgName());
        result.setFlowChartURL(flowChartSaveDir + "/"
                + caseEntry.getWorkflow().getFlowChartName());
        result.setTaskList(taskList);
        result.setPositionList(positionList);
        result.setBimAppIntro(bimAppIntro);

        return result;
    }

    private Map<String, List> generateTaskAndPosDtosBy(WorkflowEntry workflowEntry){
        Map<String, List> result = new HashMap<>();
        List<PositionDto> positionList = new ArrayList<>();
        List<TaskDto> taskList = new ArrayList<>();

        workflowEntry.getTaskList().forEach(e -> {
            TaskDto aTaskDto = new TaskDto();
            List<DocumentDto> aDocDto = getDocEntryListFrom(e.getDocumentList());

            aTaskDto.setId(e.getId());
            aTaskDto.setName(e.getName());
            aTaskDto.setOrder(e.getOrder());
            aTaskDto.setDescription(e.getDescription());
            aTaskDto.setDocumentList(aDocDto);
            taskList.add(aTaskDto);

            PositionDto aPositionDto = new PositionDto();
            aPositionDto.setId(e.getPosition().getId());
            aPositionDto.setOrder(e.getOrder());
            aPositionDto.setPosX(e.getPosition().getPosX());
            aPositionDto.setPosY(e.getPosition().getPosY());
            positionList.add(aPositionDto);
        });

        result.put(TASKLIST, taskList);
        result.put(POSLIST, positionList);
        return result;
    }

    private DocumentDto convertDocEntryToDto(DocumentEntry docEntry){
        DocumentDto result = new DocumentDto();
        result.setId(docEntry.getId());
        result.setType(docHelper.getTypeByMark(docEntry.getType()));
        result.setUrl(docHelper.getPathByMark(docEntry.getType()) + "/" + docEntry.getName());
        return result;
    }

    private List<DocumentDto> getDocEntryListFrom(List<DocumentEntry> docEntryList){
        return docEntryList.stream()
                .map(this::convertDocEntryToDto)
                .collect(Collectors.toList());
    }
}
