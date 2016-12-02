import com.google.gson.*;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Teste2 on 02/12/2016.
 */
public class TweetFeed {
    private static MongoDatabase database;

    public static void main(String[] args) throws IOException {
        final String screenName = args.length >0 ? args[0]: "MongoDb";
        Document tweets = new Document(getLastesTweets(screenName))    ;

        System.out.println(tweets);



        MongoCredential credential = MongoCredential.createCredential(
                "packfly", "packfly",
                "packfly".toCharArray());
        MongoClient c = new MongoClient(new ServerAddress("ds139267.mlab.com:39267"), Arrays.asList(credential));
        database = c.getDatabase("packfly");
        MongoCollection<Document> collection = database.getCollection("tweets");

        //collection.insertOne(tweets);
        FindIterable<Document> docs = collection.find();
            for (Document doc: docs){
                System.out.println(doc);
            }


    }

    private static BasicDBObject getLastesTweets(String screenName) throws IOException {
        URL url = new URL("https://api.vagalume.com.br/search.art?q=Skank&limit=5");
        InputStream is = url.openStream();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int retVal;
        while ((retVal = is.read()) != -1) {
            os.write(retVal);

        }
        final String tweetString = os.toString();

        List<Document> docs = new ArrayList<Document>();

        JsonElement json = new JsonParser().parse(tweetString).getAsJsonObject();
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(tweetString, JsonElement.class);
        JsonObject jsonObject = element.getAsJsonObject();

            BasicDBObject b = (BasicDBObject) JSON.parse(tweetString);

        return (BasicDBObject) JSON.parse(tweetString);

    }

}
