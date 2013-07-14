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
import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

import persistence.PersonBean;
import persistence.PersonPersistence;

import com.mongodb.BasicDBObject;

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
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response getPerson(@PathParam("id") String personId) {
		
		LOGGER.info("Request :: Resource person :: GET :: " + personId);

		Person person = null;
		try {
			
			person = getPersonFromDatabase(personId);
			
			if (person != null) {
				System.out.println("Person found!");
			} else {
				System.out.println("Person not found!");
			}
		} catch (UnknownHostException e) {
//			LOGGER.error("Exeption :: );
			return Response.serverError().build();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GenericEntity<Person> entity = new GenericEntity<Person>(
				generatedPerson) {
		};
		return Response.ok(entity).build();
	}

	private void sendPersonToDatabase(Person personToPersist)
			throws UnknownHostException {
		final PersonPersistence persistence = new PersonPersistence();
		PersonBean personBeanToPersist = convertToPersonBean(personToPersist);
		BasicDBObject dbPerson = persistence
				.createNewPerson(personBeanToPersist);
		if (dbPerson != null) {
			System.out.println("Successfully persisted person: "
					+ personToPersist.getId());
		}
	}

	private Person getPersonFromDatabase(String id) throws UnknownHostException {
		final PersonPersistence persistence = new PersonPersistence();
		PersonBean personFromDb = persistence.getPersonById(id);
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
