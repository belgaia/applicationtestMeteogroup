package applicationtest;

import java.net.UnknownHostException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

import persistence.PersonBean;
import persistence.PersonPersistence;

import com.mongodb.BasicDBObject;

import exceptions.PersonNotFoundException;

/**
 * RESTful service class for finding and creating person data.
 * 
 * @author Isabel Schaefer Batista
 * 
 */
@Path("person")
@Stateless
public class MeteoGroupService extends javax.ws.rs.core.Application {

	@EJB
	private Person person;

	private static final Logger LOGGER = Logger.getLogger(MeteoGroupService.class);

	/**
	 * Get person data by given ID via RESTful service call Usage:
	 * servername:port/meteogroupService/meteogroup/person/{id}
	 * 
	 * @param personId	The unique ID of the person to get the data.
	 * @return XML or JSON response with detailed information about the person.
	 */
	// TODO: sort the result set so each field is printed at the same position
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response getPerson(@PathParam("id") String personId) {
		
		LOGGER.info("Request :: Resource person :: GET :: " + personId);

		Person person = null;
		try {
			
			person = getPersonFromDatabase(personId);
			
			if (person == null) {
				
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (UnknownHostException e) {
			LOGGER.error("DBConnection :: Unknown Host Exception :: Connection cannot be established");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (PersonNotFoundException e) {
			LOGGER.error("GET person by Id :: 404 Person not found :: " + personId);
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(person).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createPerson(JAXBElement<Person> personXml) {

		Person generatedPerson = personXml.getValue();
		try {
			sendPersonToDatabase(generatedPerson);
		} catch (UnknownHostException e) {
			LOGGER.error("DBConnection :: Create person failed :: " + e.getMessage());
			e.printStackTrace();
		}

		GenericEntity<Person> entity = new GenericEntity<Person>(generatedPerson) {};
		return Response.ok(entity).build();
	}

	private void sendPersonToDatabase(Person personToPersist) throws UnknownHostException {
		
		final PersonPersistence persistence = new PersonPersistence();
		final PersonBean personBeanToPersist = convertToPersonBean(personToPersist);
		final BasicDBObject dbPerson = persistence.createNewPerson(personBeanToPersist);
		
		if (dbPerson != null) {
			LOGGER.info("DB Create :: Successfully persisted person :: " + personToPersist.getId());
		}
		
	}

	private Person getPersonFromDatabase(String id) throws UnknownHostException, PersonNotFoundException {
		final PersonPersistence persistence = new PersonPersistence();
		final PersonBean personFromDb = persistence.getPersonById(id);
		return convertToPersonForService(personFromDb);
	}

	private PersonBean convertToPersonBean(Person personToPersist) {

		// TODO: make this with Reflection to avoid errors and maintenance
		PersonBean personBean = new PersonBean();
		personBean.setId(personToPersist.getId());
		personBean.setGivenName(personToPersist.getGivenName());
		personBean.setFamilyName(personToPersist.getFamilyName());
		personBean.setMiddleNames(personToPersist.getMiddleNames());
		personBean.setDateOfBirth(personToPersist.getDateOfBirth());
		personBean.setDateOfDeath(personToPersist.getDateOfDeath());
		personBean.setHeight(personToPersist.getHeight());
		personBean.setPlaceOfBirth(personToPersist.getPlaceOfBirth());
		personBean.setTwitterId(personToPersist.getTwitterId());
		return personBean;
	}

	private Person convertToPersonForService(PersonBean personFromDb) {

		// TODO: make this with Reflection to avoid errors and maintenance
		Person personForService = new Person();
		personForService.setId(personFromDb.getId());
		personForService.setGivenName(personFromDb.getGivenName());
		personForService.setFamilyName(personFromDb.getFamilyName());
		personForService.setMiddleNames(personFromDb.getMiddleNames());
		personForService.setDateOfBirth(personFromDb.getDateOfBirth());
		personForService.setDateOfDeath(personFromDb.getDateOfDeath());
		personForService.setHeight(personFromDb.getHeight());
		personForService.setPlaceOfBirth(personFromDb.getPlaceOfBirth());
		personForService.setTwitterId(personFromDb.getTwitterId());
		return personForService;
	}
}
