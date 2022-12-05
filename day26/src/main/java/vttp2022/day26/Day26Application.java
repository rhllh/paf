package vttp2022.day26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day26Application {

	// @Autowired
	// private TVShowRepo tvShowRepo;

	public static void main(String[] args) {
		SpringApplication.run(Day26Application.class, args);
	}

	// @Override
	// public void run(String... args) throws Exception {

	// 	//List<Document> results = tvShowRepo.findTVShowByLang("English");
	// 	List<Document> results = tvShowRepo.findTVShowsByRating(6.5f);

	// 	// ? result is empty!
	// 	for (Document d : results) {
	// 		System.out.printf("Name: %s\nSummary: %s\n",
	// 		 					d.getString("name"), d.getString("summary"));
	// 	}

	// 	System.exit(0);
		
	// }

}
