package Market;//package Router;

import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


import org.bson.Document;
import java.util.Arrays;


public class MongoDBAtlasClient {

    public static void connection() {

        MongoClientURI uri = new MongoClientURI("mongodb+srv://Bilko:<password>@cluster0.0xpyn.gcp.mongodb.net/<dbname>?retryWrites=true&w=majority");


        try (MongoClient con = new MongoClient(uri))
        {
            // Create Connection
            System.out.println("MongoDB connected successfully");

            //Accessing the database
            MongoDatabase database = con.getDatabase("test");
            System.out.println("MongoDB database connection successful");

            //Creating a collection
            database.createCollection("first");
            System.out.println("Collection created successfully");

            //Accessing collection
            MongoCollection<Document> collection = database.getCollection("first");

            Document document = new Document("name", "Test doc")
                    .append("message", new Document("Hello World", "Ohio gosaimasu"));

            collection.insertOne(document);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
