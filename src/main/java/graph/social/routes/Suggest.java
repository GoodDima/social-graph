package graph.social.routes;

import java.util.ArrayList;

import graph.social.Model;

import com.mongodb.DBObject;

import spark.Request;
import spark.Response;
import spark.Route;

public class Suggest extends Route
{

    public Suggest(String path)
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

            ArrayList<DBObject> direct_friends = Model.getSuggestedFriends(user);
            return direct_friends;

        }


        return null;
    }

}
