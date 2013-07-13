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
		Person person = new Person();
		person.setFamilyName("Batista");
		ClientResponse response = service.path("meteogroup").path("person").accept(MediaType.APPLICATION_XML).post(ClientResponse.class, person);
		System.out.println("Status: " + response.getStatus());
		
		if(response.hasEntity()) {
			System.out.println("Has entity: " + response.getEntity(String.class));
		} else {
			System.out.println("Not reachable");
		}
		
//		
//		Person person = new Person();
//		person.setFamilyName("Batista");
		Person createdPerson = service.path("meteogroup").path("person").type(MediaType.APPLICATION_XML).post(Person.class, person);
//		ClientResponse response = service.path("meteogroup").path("person").type(MediaType.APPLICATION_XML).post(ClientResponse.class);
		
//		service.path("meteogroup").path("person").path("/").type(MediaType.APPLICATION_XML).post();
		
//		System.out.println("Reason: " + response.getEntity(String.class));
		
		
		
//		System.out.println(service.path("meteogroup").path("person").type(MediaType.APPLICATION_XML).post(ClientResponse.class));
//		throw new UniformInterfaceException(response.getEntity(String.class));
		
//		System.out.println(service.path("meteogroup").path("person/").accept(MediaType.APPLICATION_XML).get(ClientResponse.class).toString());
//		assertEquals(200, response.getClientResponseStatus().getStatusCode());
		
	}
}
