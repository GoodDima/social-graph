package graph.social.routes;

import java.util.ArrayList;
import java.util.HashSet;

import graph.social.Model;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import spark.Request;
import spark.Response;
import spark.Route;

public class Friends extends Route
{

    public Friends(String path)
    {
        super(path);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Object handle(Request arg0, Response arg1)
    {

        String raw_id = arg0.params("id");
        long id = 0;
        if (raw_id.matches("\\d+")){
            id = Long.valueOf(raw_id);
        }

        DBObject user = Model.getById(id);
        if (user != null){
            BasicDBList friends = (BasicDBList) user.get("friends");
            if (friends.isEmpty()){
                return "No friends";
            }

            ArrayList<DBObject> direct_friends = Model.getUsers(friends);
            return direct_friends;

        }
        return "No data";

    }

}
