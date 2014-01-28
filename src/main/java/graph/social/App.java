package graph.social;

/**
 */

import graph.social.routes.Friends;
import graph.social.routes.Stepfriends;
import graph.social.routes.Suggest;
import spark.Spark;

public class App 
{
    public static void main( String[] args )
    {
        Spark.get(new Friends("/friends/:id/"));
        Spark.get(new Stepfriends("/stepfriends/:id/"));
        Spark.get(new Suggest("/suggest/:id/"));
    }
}
