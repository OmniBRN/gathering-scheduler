package ro.tudorboureanu.gatheringschedule;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class GatheringScheduleApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnAGatheringWhenDataIsSaved() {

		ResponseEntity<String> response = restTemplate.getForEntity("/api/gathering/e86294a0-ef5d-4afc-81e8-74ca97c94789", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String id = documentContext.read("$.id");
		assertThat(id).isEqualTo("e86294a0-ef5d-4afc-81e8-74ca97c94789");

		String name = documentContext.read("$.gatheringName");
		assertThat(name).isEqualTo("Temporary");
	}

	@Test
	void shouldNotReturnAGatheringWithUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/api/gathering/e86294a0-ef5d-4afc-81e8-74ca97c94780", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}

}
