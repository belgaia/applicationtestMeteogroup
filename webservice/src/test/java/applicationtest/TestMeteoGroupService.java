package applicationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class TestMeteoGroupService {

	private static final String BASE_URL = "http://localhost:8080/meteogroup/persons/person";
	private static final Logger LOGGER = Logger
			.getLogger(TestMeteoGroupService.class);

	@Autowired
	private RestTemplate restTemplate;

	// FIXME: test is not green
	@Test
	public void getOnePersonById() throws Exception {
		// get id
		LOGGER.info("Test response of search by person id");
		PersonBean givenPerson = createPersonOne();
		PersonBean person = restTemplate.getForObject(
				BASE_URL + "/person/{id}", PersonBean.class,
				givenPerson.getId());
		assertNotNull("no person", person);
		assertNotNull(person.getId());
		assertEquals(givenPerson.getFamilyName(), person.getFamilyName());
	}

	private PersonBean createPersonOne() {
		PersonBean person = new PersonBean();
		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.set(1985, 10, 24);
		person.setFamilyName("Rooney");
		person.setGivenName("Wayne");
		person.setMiddleNames("Mark");
		person.setDateOfDeath(null);
		person.setPlaceOfBirth("Liverpool");
		person.setHeight(1.76f);
		person.setTwitterId("@WayneRooney");
		return person;
	}
}
