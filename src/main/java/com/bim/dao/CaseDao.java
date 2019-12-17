package com.bim.dao;

import com.bim.entry.CaseEntry;
import com.bim.entry.WorkflowEntry;
import com.bim.util.EntryConverter;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Repository
public class CaseDao {
    @Autowired @Qualifier("mongoDatabase")
    private MongoDatabase mongoDatabase;

    public List<Document> selectAllCase(){
        List<Document> results = new ArrayList<>();
        MongoCollection caseCal = mongoDatabase.getCollection("case");
        caseCal.find().forEach((Consumer) e -> results.add((Document) e));
        return results;
    }

    public List<Document> selectCaseLikeKeyword(String keyword) {
        List<Document> results = new ArrayList<>();
        MongoCollection caseCal = mongoDatabase.getCollection("case");
        caseCal.find(regex("title", keyword))
                .forEach((Consumer) e -> results.add((Document) e));
        return results;
    }

    public CaseEntry selectCaseById(String id){
        MongoCollection caseCal = mongoDatabase.getCollection("case");
        Document caseDocument =
                (Document) caseCal.find(eq("_id", new ObjectId(id))).first();
        if (caseDocument == null){ // 没有相关记录
            return null;
        }
        return EntryConverter.convertCaseDocToEntry(caseDocument);
    }


}









