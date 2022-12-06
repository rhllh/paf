package vttp2022.day27;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@SpringBootApplication
public class Day27Application {

	@Autowired
	private MongoTemplate template;

	// @Override
    // public void run(String... args){
    //     //insertNewDocument();
	// 	//insertFromJsonObject();
	// 	//deleteDocument();
	// 	updateDocument();

    //     System.exit(0);
    // }

	// public void updateDocument() {
	// 	Query query = Query.query(Criteria.where("title").is("Fourth blog"));

	// 	Update updateOps = new Update()
	// 						.set("title", "My fourth blog ever")
	// 						.set("comments", "this is an updated comment");

	// 	UpdateResult ur = template.updateMulti(query, updateOps, Document.class, "posts");

	// 	System.out.println("updated documents > " + ur.getModifiedCount());
	// }

	// public void deleteDocument() {
	// 	Criteria c = Criteria.where("title").regex(".*json","i");

	// 	DeleteResult dr = template.remove(Query.query(c), "posts");

	// 	System.out.println("deleted documents > " + dr.getDeletedCount());
	// }

	// public void insertFromJsonObject() {
	// 	JsonObject json = Json.createObjectBuilder()
	// 						.add("title", "Concerning JSON-P")
	// 						.add("date", new Date().toString())
	// 						.add("summary", "Convert JSON-P to String")
	// 						.build();

	// 	Document blog = Document.parse(json.toString());

	// 	Document inserted = template.insert(blog, "posts");
	// 	System.out.println("inserted : " + inserted.toString());
	// }

    // public void insertNewDocument(){
	// 	// create a document to be inserted
    //     Document blog = new Document();
    //     blog.put("title", "Fourth blog");
    // 	blog.put("date", new Date());
    //     blog.put("Summary", "The meaning of computer");

    //     Document comment = new Document();
    //     comment.put("user","derf");
    //     comment.put("comment", "I love your computer");

	// 	List<Document> comments = new LinkedList<>();
	// 	comments.add(comment);
	// 	blog.put("comments", comments);
	// 	blog.put("tags", List.of("one", "two", "three"));

	// 	Document inserted = template.insert(blog, "posts");

	// 	System.out.println(inserted.toJson());
    // }

	public static void main(String[] args) {
		SpringApplication.run(Day27Application.class, args);
	}

}
