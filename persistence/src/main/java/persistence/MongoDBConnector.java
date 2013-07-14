package persistence;

import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

// TODO: make Singleton
public class MongoDBConnector {

	public static final String DB_NAME = "meteogroup";
	public static final String COLLECTION_NAME = "persons";
	
	private static final Logger LOGGER = Logger.getLogger(MongoDBConnector.class);
	private static final String SERVER_URL = "localhost";
	private static final int PORT_NUMBER = 27017;

	private Mongo mongo;
	private static MongoDBConnector mongoDbConnectorInstance;
	
	private MongoDBConnector() {
		
		if(mongo == null) {
			try {
				mongo = new Mongo(SERVER_URL, PORT_NUMBER);
			} catch (UnknownHostException e) {
				LOGGER.error("DBConnection :: UnknownHostException :: " + e.getMessage() + " :: " + SERVER_URL + ":" + PORT_NUMBER);
				e.printStackTrace();
			}
		}
	}
	
	public static MongoDBConnector getInstance() {
		if (mongoDbConnectorInstance == null) {
			mongoDbConnectorInstance = new MongoDBConnector();
		}
		return mongoDbConnectorInstance;
	}

	public void setUp() throws UnknownHostException {

		mongo = new Mongo(SERVER_URL, PORT_NUMBER);
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
