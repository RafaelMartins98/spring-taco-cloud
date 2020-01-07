package tacos.restclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@SpringBootConfiguration
@Slf4j
@ComponentScan
public class RestExamples {

	public static void main(String[] args) {
		SpringApplication.run(RestExamples.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public CommandLineRunner fetchIngredients(TacoCloudClient client) {

		return args -> {

			log.info("----------------------- GET -------------------------");
			log.info("GETTING INGREDIENT BY ID");
			log.info("Ingredient with id CHED: " + client.getIngredientByIdAlternative2("CHED"));
			
			log.info("Fetching all ingredients, in total there is " + client.getAllIngredients().size());

		};

	}

	@Bean
	public CommandLineRunner putIngredient(TacoCloudClient client) {

		return args -> {

			log.info("----------------------- PUT -------------------------");
			log.info("Before: " + client.getIngredientById("CHED"));

			log.info("Updating....");
			client.updateIngredient(new Ingredient("CHED", "Second Name", Type.WRAP));

			log.info("After: " + client.getIngredientById("CHED"));

		};
	}

	@Bean
	public CommandLineRunner deletingIngredient(TacoCloudClient client) {

		return args -> {

			log.info("----------------------- DELETE -------------------------");
			log.info("Deleting: " + client.getIngredientById("CHED"));

			client.deleteIngredient("CHED");

		};
	}

	@Bean
	public CommandLineRunner postIngredient(TacoCloudClient client) {
		return args -> {
			
			log.info("----------------------- POST -------------------------");
			log.info("Adding ingredient example");
			
			client.postIngredient(new Ingredient("EXEM", "Example", Type.WRAP));
			
			log.info("fetching added ingredient: " + client.getIngredientById("EXEM"));
		};	
	}

	
	@Bean
	public CommandLineRunner getRecenttacos(TacoCloudClient client) {
		return args -> {
			
			log.info("----------------------- GET RECENTS -------------------------");
		
			log.info("The first recent taco is" + client.getRecentTacos().iterator().next());
			
		};
	}
	
}
