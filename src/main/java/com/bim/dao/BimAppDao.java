package com.bim.dao;

import com.bim.entry.BimAppEntry;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class BimAppDao {
    @Autowired @Qualifier("mongoDatabase")
    private MongoDatabase mongoDatabase;

    public BimAppEntry selectById(String id){
        BimAppEntry result = new BimAppEntry();
        MongoCollection caseCol = mongoDatabase.getCollection("bimApp");
        Document bimAppDoc = (Document) caseCol.find(eq("_id", new ObjectId(id))).first();
        result.setId(id);
        result.setDescription(bimAppDoc.getString("description"));
        result.setName(bimAppDoc.getString("name"));
        return result;
    }
}
