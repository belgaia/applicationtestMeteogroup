package persistence;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class PersonPersistence {

	// private static final String PERSON_DB_NAME = "persons";

	private MongoDBConnector mongoDBConnector;

	private void setUp() {
		mongoDBConnector = new MongoDBConnector();
	}

	public PersonBean getPersonById(String personId)
			throws UnknownHostException {
		setUp();
		DBObject foundDBPerson = mongoDBConnector.findById(personId);
		return convertToPersonBean(foundDBPerson);
	}

	public BasicDBObject createNewPerson(PersonBean personToPersist)
			throws UnknownHostException {

		setUp();

		DB database = mongoDBConnector
				.getOrCreateDatabase(MongoDBConnector.DB_NAME);
		System.out.println("database: " + database);

		DBCollection collection = database
				.getCollection(MongoDBConnector.COLLECTION_NAME);
		BasicDBObject document = convertToDbDocument(personToPersist);

		collection.insert(document);
		return document;
	}

	private BasicDBObject convertToDbDocument(PersonBean person) {

		BasicDBObject document = new BasicDBObject();
		document.put("id", person.getId());
		document.put("givenName", person.getGivenName());
		document.put("familyName", person.getFamilyName());
		document.put("middleName", person.getMiddleNames());
		document.put("dateOfBirth", person.getDateOfBirth());
		document.put("dateOfDeath", person.getDateOfDeath());
		document.put("placeOfBirth", person.getPlaceOfBirth());
		document.put("height", person.getHeight());
		document.put("twitterId", person.getTwitterId());

		return document;
	}

	private PersonBean convertToPersonBean(DBObject personFromDatabase) {

		// TODO: set constants for the database object identifier
		PersonBean person = new PersonBean();
		person.setId((String) personFromDatabase.get("id"));
		person.setGivenName((String) personFromDatabase.get("givenName"));
		person.setFamilyName((String) personFromDatabase.get("familyName"));
		person.setMiddleNames((String) personFromDatabase.get("middleNames"));
		person.setDateOfBirth((Date) personFromDatabase.get("dateOfBirth"));
		person.setDateOfDeath((Date) personFromDatabase.get("dateOfDeath"));
		person.setPlaceOfBirth((String) personFromDatabase.get("placeOfBirth"));
		person.setHeight((Float) personFromDatabase.get("heigth"));
		person.setTwitterId((String) personFromDatabase.get("twitterId"));

		return person;
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
