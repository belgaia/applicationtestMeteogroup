package persistence;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class MongoDBConnector {
	
	Mongo mongo;
	
	public void setUp() throws UnknownHostException {
		
		mongo = new Mongo("localhost", 27017);
	}
	
	public List<String> getAllDatabases() {
		
		List<String> databases = mongo.getDatabaseNames();
		for(String db : databases){
			System.out.println(db);
		}
		return databases;
	}
	
	public DB getOrCreateDatabase(String databaseName) throws UnknownHostException {
		setUp();
		DB database = mongo.getDB(databaseName);
		return database;
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
		
	}
	

}
