package graph.social;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;

public class MyDB
{

    private static MongoClient client = null;
//    private static 
    public static MongoClient getClient(){

        if (MyDB.client == null){
            try
            {
                MyDB.client = new MongoClient();
            } catch (UnknownHostException e)
            {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return MyDB.client;

    }
}
