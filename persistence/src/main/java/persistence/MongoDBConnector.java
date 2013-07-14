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

/**
 * Class for the connection to a MongoDB nosql database.
 * @author WeatherGirl
 *
 */
// TODO: move database name to PersonPersistence class (not only a MongoDB information)
public class MongoDBConnector {

	public static final String DB_NAME = "meteogroup";
	
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
				LOGGER.error("DBConnection :: UnknownHostException :: " + e.getMessage() + " :: " + SERVER_URL + ":" + PORT_NUMBER + " :: Change server name or port!");
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


	/**
	 * Returns the existing or newly created database with the given database name for further usage.
	 * @param databaseName		The name of the database you wish to use (or create).
	 * @return					The database object for further usage.
	 * @throws UnknownHostException		If there is no possible connection to any MongoDB database server. Change server name or port!
	 */
	public DB getOrCreateDatabase(String databaseName) throws UnknownHostException {
		
		List<String> databases = getAllDatabases();
		
		for(String database : databases) {
			if (database.equals(databaseName)) {
				LOGGER.info("DBConnection :: Database already exists :: " + databaseName + " :: Use this database");
				break;
			} else {
				LOGGER.info("DBConnection :: Database does not exist :: " + databaseName + " :: Create database and use this");
			}
		}
		
		DB database = mongo.getDB(databaseName);
		return database;
		
	}

	/**
	 * Returns a MongoDB document to a given field identifier and its value.
	 * @param collectionName	The collection where to search for the right document.
	 * @param fieldIdentifier	The identifier of the document field to search for.
	 * @param fieldValue		The value of the field in the document to search for.
	 * @return					The MongoDB document that was found to the given field identifier with the given value.
	 * @throws UnknownHostException
	 */
	// TODO: extract database and collection for general usage
	// TODO: implement better exception handling
	public DBObject find(String collectionName, String fieldIdentifier, String fieldValue) {
		
		DB database = mongo.getDB(DB_NAME);
		DBObject foundDocument = null;
		
		if (database.collectionExists(collectionName)) {

			DBCollection collection = database.getCollection(collectionName);
			BasicDBObject query = new BasicDBObject(fieldIdentifier, fieldValue);
			DBCursor cursor = collection.find(query);
			
			try {
				while (cursor.hasNext()) {
					foundDocument = cursor.next();
					LOGGER.info("DB search :: Found document :: Collection=" + collectionName + " :: fieldIdentifier= " + fieldIdentifier + " :: fieldValue= " + fieldValue);
				}
			} finally {
				cursor.close();
			}
		} else {
			LOGGER.error("DBConnection :: Collection does not exist :: " + collectionName);
		}
		
		return foundDocument;

	}
	
	/**
	 * Creates a new document in the given collection and with the data of the given object to persist.
	 * @param collectioName		The collection where the document has to be created.
	 * @param objectToPersist	The object that has to be persisted in a new document.
	 */
	public void createDocument(String collectionName, BasicDBObject objectToPersist) {
		
		DB database = mongo.getDB(DB_NAME);
		DBCollection collection = database.getCollection(collectionName);
		collection.insert(objectToPersist);
		
	}
	
	private List<String> getAllDatabases() {

		return mongo.getDatabaseNames();

	}
}
