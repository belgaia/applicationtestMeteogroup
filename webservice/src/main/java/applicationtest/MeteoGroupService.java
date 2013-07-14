package applicationtest;

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

import com.sun.jersey.api.client.ClientResponse;

/**
 * RESTful service class for getting and posting person data.
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
	 * Get person data by given ID via RESTful service call
	 * Usage: servername:port/meteogroup/persons/person/{id}
	 * @param id The unique ID of the person to get the data.
	 * @return XML or JSON request with detailed information about the person.
	 */
	// TODO: response has to be XML/JSON object not just the familyName
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("/{id}")
	public Person getPerson(@PathParam("id") String id) {
		LOGGER.info("Get person with id: " + id);
		
		Person person = new Person();
		
		if (id.equals("1")) {
			person.setFamilyName("Mustermann");
		} else {
			person.setFamilyName("Unknown");
			ClientResponse.Status status = ClientResponse.Status.NOT_FOUND;
		}
		
		return person;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response createPerson(JAXBElement<Person> personXml) {
		
		Person generatedPerson = personXml.getValue();
		GenericEntity<Person> entity = new GenericEntity<Person>(generatedPerson) {};
		return Response.ok(entity).build();
		
	}	
}
