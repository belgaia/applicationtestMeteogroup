package persistence;

import java.net.UnknownHostException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import exceptions.PersonNotFoundException;

/**
 * Class for all operations to create and find persons in a storage.
 * @author Isabel Schaefer Batista
 *
 */
public class PersonPersistence {

	private static Logger LOGGER = Logger.getLogger(PersonPersistence.class);
	
	private static final String COLLECTION_NAME = "persons";
	
	private static MongoDBConnector mongoDBConnector = MongoDBConnector.getInstance();

	/**
	 * Find person by id in the used storage.
	 * @param personId	The person id to find person data in the storage.
	 * @return			The person object found in the storage.
	 * @throws UnknownHostException		If the used storage cannot be found at the server.
	 * @throws PersonNotFoundException	If the person can not be found in the storage.
	 */
	public PersonBean getPersonById(String personId) throws UnknownHostException, PersonNotFoundException {
		
		LOGGER.info("DB Search :: Find person :: " + personId);
		
		DBObject foundDBPerson = mongoDBConnector.find(COLLECTION_NAME, "id", personId);
		if (foundDBPerson == null) {
			LOGGER.error("DB Search :: Could not find person :: " + personId);
			throw new PersonNotFoundException("Person does not exist: " + personId);
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

		LOGGER.info("DB Create :: Create new person :: " + personToPersist.getId());

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
}
