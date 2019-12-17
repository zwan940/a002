package com.bim.util;

import com.bim.entry.CaseEntry;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

@NoArgsConstructor @Data
public class DBUtil {
    private static String hostAddress = "localhost";
    private static int port = 27017;
    private static String dbName = "bim";
    private static MongoDatabase mongoDatabase = null;

    static {
        mongoDatabase = new MongoClient(hostAddress, port).getDatabase(dbName);
    }

    public static MongoDatabase getMongoDB(){
        if (mongoDatabase == null) {
            mongoDatabase = new MongoClient(hostAddress, port).getDatabase(dbName);
        }
        return mongoDatabase;
    }
}
