package applicationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TestMeteoGroupService {

	private static final String BASE_URI = "http://localhost:8080/meteogroupService";
	private static final Logger LOGGER = Logger.getLogger(TestMeteoGroupService.class);

	
	private WebResource service;
	
//	@Autowired
//	private RestTemplate restTemplate;
	
	@Before
	public void setUp() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(UriBuilder.fromUri(BASE_URI).build());
	}
	
	@Test
	public void getPersonByIdWithSuccess() {
		
		final String personId = "1";
		ClientResponse response = service.path("meteogroup").path("person/" + personId).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		assertEquals(200, response.getClientResponseStatus().getStatusCode());
		
	}
	
	@Test
	public void getPersonByIdWithSuccessAndCorrectResult() {
		final String personId = "1";
		Person foundPerson = service.path("meteogroup").path("person/" + personId).accept(MediaType.APPLICATION_XML).get(Person.class);
		
		assertEquals("Mustermann", foundPerson.getFamilyName());
	}
	
	// TODO: implement
	@Test
	@Ignore
	public void getPersonByIdThatDoesNotExist() {
		
	}
	
	@Test
	public void createNewPerson() {
		ClientResponse response = service.path("meteogroup").path("person/").accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		
		System.out.println(service.path("meteogroup").path("person/").accept(MediaType.APPLICATION_XML).get(ClientResponse.class).toString());
		assertEquals(200, response.getClientResponseStatus().getStatusCode());
	}
	
	
	
	
	

//	// FIXME: test is not green
//	@Test
//	public void getOnePersonById() throws Exception {
//		// get id
//		LOGGER.info("Test response of search by person id");
//		Person givenPerson = createPersonOne();
//		Person person = restTemplate.getForObject(
//				BASE_URL + "/person/{id}", Person.class,
//				givenPerson.getId());
//		assertNotNull("no person", person);
//		assertNotNull(person.getId());
//		assertEquals(givenPerson.getFamilyName(), person.getFamilyName());
//	}
//
//	private Person createPersonOne() {
//		Person person = new Person();
//		Calendar dateOfBirth = Calendar.getInstance();
//		dateOfBirth.set(1985, 10, 24);
//		person.setFamilyName("Rooney");
//		person.setGivenName("Wayne");
//		person.setMiddleNames("Mark");
//		person.setDateOfDeath(null);
//		person.setPlaceOfBirth("Liverpool");
//		person.setHeight(1.76f);
//		person.setTwitterId("@WayneRooney");
//		return person;
//	}
}
