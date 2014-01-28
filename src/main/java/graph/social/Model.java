package graph.social;

import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Model
{

    public static int add(){
        
        return 0;
        
    }

    public static DBObject getById(long id){

        MongoClient mc = MyDB.getClient();
        DB db = mc.getDB("social-graph");
        DBCollection dbc = db.getCollection("data");

        DBObject result = dbc.findOne(new BasicDBObject("id", id));
        return result;

    }
    
    public static ArrayList<DBObject> getUsers(BasicDBList ids){

        MongoClient mc = MyDB.getClient();
        DB db = mc.getDB("social-graph");
        DBCollection dbc = db.getCollection("data");

        BasicDBObject query = new BasicDBObject();
        query.append("id", new BasicDBObject("$in", ids));

        DBCursor cursor = dbc.find(query);
        ArrayList<DBObject> result = new ArrayList<DBObject>(); 
        while (cursor.hasNext()) {
            result.add(cursor.next());
        }
        return result;

    }
    
    public static ArrayList<DBObject> getStepFriends(DBObject user){
        
        int id = (Integer) user.get("id");
        BasicDBList friends = (BasicDBList) user.get("friends");

        BasicDBObject in_query = new BasicDBObject("friends", new BasicDBObject("$in", friends));
        BasicDBObject nin_query = new BasicDBObject("id", new BasicDBObject("$nin", friends));
        BasicDBObject id_ne_query = new BasicDBObject("id", new BasicDBObject("$ne", id));
        BasicDBList and_list = new BasicDBList();
        and_list.add(in_query);
        and_list.add(nin_query);
        and_list.add(id_ne_query);

        BasicDBObject query = new BasicDBObject();

        query.append("$and", and_list);

        MongoClient mc = MyDB.getClient();
        DB db = mc.getDB("social-graph");
        DBCollection dbc = db.getCollection("data");

        DBCursor cursor = dbc.find(query);
        ArrayList<DBObject> result = new ArrayList<DBObject>(); 
        while (cursor.hasNext()) {
            result.add(cursor.next());
        }
        return result;
    }
    
    public static ArrayList<DBObject> getSuggestedFriends(DBObject user){
        
        ArrayList<DBObject> step_friends = Model.getStepFriends(user);
        BasicDBList friends = (BasicDBList) user.get("friends");
        ArrayList<DBObject> result = new ArrayList<DBObject>();
        BasicDBList common_friends;
        for(int i = 0, n = step_friends.size(); i < n; i++){
            common_friends = (BasicDBList) step_friends.get(i).get("friends");
            friends.retainAll(common_friends); // get arrays intersection
            if (common_friends.size() > 1){
                result.add(step_friends.get(i));
            }
        }
        return result;

    }
    
}
