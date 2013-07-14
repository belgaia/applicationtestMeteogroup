package persistence;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class PersonPersistence {
	
	private static final String PERSON_DB_NAME = "persons";
	private MongoDBConnector mongoDBConnector;
	
	private void setUp() {
		mongoDBConnector = new MongoDBConnector();
	}
		
	public PersonBean getPersonById() {
		mongoDBConnector.getAllDatabases();
		
		return null;
	}

	public BasicDBObject createNewPerson(PersonBean personToPersist) throws UnknownHostException {
		
		setUp();
		
		DB database = mongoDBConnector.getOrCreateDatabase(PERSON_DB_NAME);
		System.out.println("database: " + database);
		
		DBCollection table = database.getCollection(PERSON_DB_NAME);
		BasicDBObject document = new BasicDBObject();
		document.put("id", personToPersist.getId());
		document.put("givenName", personToPersist.getGivenName());
		document.put("familyName", personToPersist.getFamilyName());
		document.put("middleName", personToPersist.getMiddleNames());
		document.put("dateOfBirth", personToPersist.getDateOfBirth());
		document.put("dateOfDeath", personToPersist.getDateOfDeath());
		document.put("placeOfBirth", personToPersist.getPlaceOfBirth());
		document.put("height", personToPersist.getHeight());
		document.put("twitterId", personToPersist.getTwitterId());
		
		table.insert(document);
		return document;
	}
	
	public static void main(String[] args) {
		
		PersonPersistence persistence = new PersonPersistence();
		
		PersonBean personToPersist = new PersonBean();
		personToPersist.setFamilyName("Batista");
		
		BasicDBObject storedPerson;
		try {
			storedPerson = persistence.createNewPerson(personToPersist);
			if (storedPerson != null) {
				System.out.println("Person stored.");
			} else {
				System.out.println("Error occurred during processing.");
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
