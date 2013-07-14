package persistence;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoDBConnector {

	public static final String DB_NAME = "meteogroup";
	public static final String COLLECTION_NAME = "persons";

	private Mongo mongo;

	public void setUp() throws UnknownHostException {

		mongo = new Mongo("localhost", 27017);
	}

	public List<String> getAllDatabases() {

		List<String> databases = mongo.getDatabaseNames();
		for (String db : databases) {
			System.out.println(db);
		}
		return databases;
	}

	public DB getOrCreateDatabase(String databaseName)
			throws UnknownHostException {
		setUp();
		DB database = mongo.getDB(databaseName);
		return database;
	}

	public DBObject findById(String id) throws UnknownHostException {
		setUp();
		DB database = mongo.getDB(DB_NAME);
		DBObject foundDocument = null;
		if (database.collectionExists(COLLECTION_NAME)) {

			DBCollection collection = database.getCollection(COLLECTION_NAME);
			BasicDBObject query = new BasicDBObject("id", id);
			DBCursor cursor = collection.find(query);
			
			try {
				while (cursor.hasNext()) {
					foundDocument = cursor.next();
					System.out.println(foundDocument);
				}
			} finally {
				cursor.close();
			}
		}
		return foundDocument;

	}

	public static void main(String[] args) {
		MongoDBConnector databaseConnector = new MongoDBConnector();
		try {
			databaseConnector.setUp();
			System.out.println(databaseConnector.getAllDatabases());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			databaseConnector.findById("1");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
