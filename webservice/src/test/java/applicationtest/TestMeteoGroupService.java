package applicationtest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Integration test for the methods of the meteoGroup person service.
 * @author WeatherGirl
 *
 */
public class TestMeteoGroupService {

	private static final String BASE_URI = "http://localhost:8080/meteogroupService";
	private static final Logger LOGGER = Logger.getLogger(TestMeteoGroupService.class);
			
	private WebResource service;
	
	@Before
	public void setUp() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(UriBuilder.fromUri(BASE_URI).build());
	}
	
	/**
	 * Tests the response status if a person with given id can be found.
	 */
	@Test
	public void getPersonByIdWithSuccess() {
		
		final String personId = "1";
		ClientResponse response = service.path("meteogroup").path("person/" + personId).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		assertEquals(200, response.getClientResponseStatus().getStatusCode());
		
	}
	
	/**
	 * Tests the result if a person with given id can be found.
	 */
	@Test
	public void getPersonByIdWithSuccessAndCorrectResult() {
		final String personId = "1";
		Person foundPerson = service.path("meteogroup").path("person/" + personId).accept(MediaType.APPLICATION_XML).get(Person.class);
		
		assertEquals("Rooney", foundPerson.getFamilyName());
	}
	
	/**
	 * Tests method to get a person with given id if the id does not exist.
	 */
	@Test
	public void getPersonByIdThatDoesNotExist() {
		final String personId = "100";
		ClientResponse response = service.path("meteogroup").path("person/" + personId).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		
		assertEquals(404, response.getClientResponseStatus().getStatusCode());
	}
	
	/**
	 * Tests creating a new person. Tests if response status and result are correct.
	 */
	@Test
	public void createNewPerson() {
		Person person = new Person();
		person.setId("9999");
		person.setFamilyName("Mustermann");
		ClientResponse response = service.path("meteogroup").path("person").accept(MediaType.APPLICATION_XML).post(ClientResponse.class, person);
			
		Person createdPerson = service.path("meteogroup").path("person").type(MediaType.APPLICATION_XML).post(Person.class, person);
		assertEquals(200, response.getClientResponseStatus().getStatusCode());
		assertEquals("Mustermann", createdPerson.getFamilyName());
		
	}	
}
