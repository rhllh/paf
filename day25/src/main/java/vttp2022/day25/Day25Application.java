package vttp2022.day25;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Day25Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Day25Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String name = args[2];
		String numTix = args[3];

		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("name", name);
		form.add("numTickets", numTix);

		RequestEntity<MultiValueMap<String, String>> request = RequestEntity
										.post("http://localhost:8080/purchase")
										.accept(MediaType.TEXT_HTML)
										.contentType(MediaType.APPLICATION_FORM_URLENCODED)
										.body(form);

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> response = template.exchange(request, String.class);

		String payload = response.getBody();

		System.out.println("response >> " + payload);

		System.exit(0);
		
	}

}
