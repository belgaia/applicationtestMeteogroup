package applicationtest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

/**
 * RESTful service class for getting and posting person data.
 * @author Isabel Schaefer Batista
 *
 */
@Path("person")
@Stateless
public class MeteoGroupService {

	@EJB
	private Person person;
	
	private static final Logger LOGGER = Logger.getLogger(MeteoGroupService.class);
	
	/**
	 * Get person data by given ID via RESTful service call
	 * Usage: servername:port/meteogroup/persons/person/{id}
	 * @param id The unique ID of the person to get the data.
	 * @return XML or JSON request with detailed information about the person.
	 */
	// TODO: response has to be XML/JSON object not just the familyName
	@GET
	@Produces({"application/xml", "application/json"})
	@Path("/{id}")
	public Person getPerson(@PathParam("id") String id) {
		LOGGER.info("Has to be implemented.");
		Person person = new Person();
		person.setFamilyName("Mustermann");
		return person;
	}
}
