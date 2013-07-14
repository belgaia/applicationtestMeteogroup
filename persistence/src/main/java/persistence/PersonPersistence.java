package persistence;

import java.net.UnknownHostException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import exceptions.PersonNotFoundException;

public class PersonPersistence {

	private static Logger LOGGER = Logger.getLogger(PersonPersistence.class);
	
	private static final String COLLECTION_NAME = "persons";
	
	private static MongoDBConnector mongoDBConnector = MongoDBConnector.getInstance();

	public PersonBean getPersonById(String personId) throws UnknownHostException, PersonNotFoundException {
		DBObject foundDBPerson = mongoDBConnector.find(COLLECTION_NAME, "id", personId);
		if (foundDBPerson == null) {
			throw new PersonNotFoundException("Person does not exist.", 404);
		}
		return convertToPersonBean(foundDBPerson);
	}

	/**
	 * Inserts a new person (defined by the parameter object) to the collection "persons" of the
	 * meteogroup database.
	 * @param personToPersist	Person information to store in a new document.
	 * @return					New created MongoDB document with person information.
	 * @throws UnknownHostException
	 */
	public BasicDBObject createNewPerson(PersonBean personToPersist) throws UnknownHostException {

		BasicDBObject document = convertToDbDocument(personToPersist);
		mongoDBConnector.createDocument(COLLECTION_NAME, document);

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
