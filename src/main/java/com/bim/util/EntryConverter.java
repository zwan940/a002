package com.bim.util;

import com.bim.dto.CaseBaseDto;
import com.bim.entry.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

public class EntryConverter {

    public static List<TaskEntry> getTaskEntryListFrom(List<Document> taskDocuments){
        if (taskDocuments == null){
            return null;
        }
        return taskDocuments.stream()
                .map(EntryConverter::convertTaskDocToEntry)
                .collect(Collectors.toList());
    }

    public static List<DocumentEntry> getDocumentEntryListFrom(List<Document> documentDocs){
        if (documentDocs == null){
            return null;
        }

        return documentDocs.stream()
                .map(EntryConverter::convertDocumentDocToEntry)
                .collect(Collectors.toList());
    }

    public static CaseEntry convertCaseDocToEntry(Document caseDocument){
        if (caseDocument == null){
            return null;
        }

        CaseEntry result = new CaseEntry();
        Document workflowDoc = (Document) caseDocument.get("workflow");
        WorkflowEntry aWorkflowEntry = convertWorkflowDocToEntry(workflowDoc);
        String bimId = caseDocument.getObjectId("bimId")!=null?
                caseDocument.getObjectId("bimId").toHexString(): null;

        result.setId(caseDocument.getObjectId("_id").toHexString());
        result.setBimId(bimId);
        result.setCreateDate(caseDocument.getDate("createDate"));
        result.setIntroImgName(caseDocument.getString("introImgName"));
        result.setTitle(caseDocument.getString("title"));
        result.setWorkflow(aWorkflowEntry);
        result.setBimId(caseDocument.getObjectId("bimId").toHexString());

        return result;
    }

    public static WorkflowEntry convertWorkflowDocToEntry(Document workflowDocument){
        if (workflowDocument == null){
            return null;
        }

        WorkflowEntry result = new WorkflowEntry();
        List<Document> taskDocuments = (List<Document>) workflowDocument.get("task");
        List<TaskEntry> aTaskEntryList = getTaskEntryListFrom(taskDocuments);

        result.setId(workflowDocument.getObjectId("_id").toHexString());
        result.setFlowChartName(workflowDocument.getString("flowChartName"));
        result.setIntroduction(workflowDocument.getString("introduction"));
        result.setTaskNum(workflowDocument.getInteger("taskNum"));
        result.setTaskList(aTaskEntryList);
        return result;
    }

    public static TaskEntry convertTaskDocToEntry(Document taskDoc){
        if (taskDoc == null){
            return null;
        }

        TaskEntry result = new TaskEntry();
        Document posDoc = (Document) taskDoc.get("position");
        List<Document> documentDocs = (List<Document>) taskDoc.get("doc");
        PositionEntry aPosEntry = convertPositionDocToEntry(posDoc);
        List<DocumentEntry> documentEntries = getDocumentEntryListFrom(documentDocs);

        result.setId(taskDoc.getObjectId("_id").toHexString());
        result.setName(taskDoc.getString("name"));
        result.setOrder(taskDoc.getInteger("order"));
        result.setDescription(taskDoc.getString("description"));
        result.setPosition(aPosEntry);
        result.setDocumentList(documentEntries);

        return result;
    }

    public static PositionEntry convertPositionDocToEntry(Document posDoc){
        if (posDoc == null){
            return null;
        }

        PositionEntry result = new PositionEntry();
        result.setId(posDoc.getObjectId("_id").toHexString());
        result.setPosX(posDoc.getDouble("x"));
        result.setPosY(posDoc.getDouble("y"));
        return result;
    }

    public static DocumentEntry convertDocumentDocToEntry(Document documentDoc){
        if (documentDoc == null){
            return null;
        }

        DocumentEntry result = new DocumentEntry();
        result.setId(documentDoc.getObjectId("_id").toHexString());
        result.setName(documentDoc.getString("name"));
        result.setType(documentDoc.getInteger("type"));
        return result;
    }

    public static CaseBaseDto convertCaseDocToDto(Document documentDoc) {
        if (documentDoc == null) {
            return null;
        }
        CaseBaseDto caseBase = new CaseBaseDto();
        caseBase.setId(documentDoc.getObjectId("_id").toHexString());
        caseBase.setTitle(documentDoc.getString("title"));
        Document workflow = (Document) documentDoc.get("workflow");
        caseBase.setIntroduction(workflow.getString("introduction"));
        caseBase.setCreateDate(documentDoc.getDate("createDate"));
        caseBase.setImgUrl("/static/assets/caseimgs" + "/" + documentDoc.getString("introImgName"));
        return caseBase;
    }
}
