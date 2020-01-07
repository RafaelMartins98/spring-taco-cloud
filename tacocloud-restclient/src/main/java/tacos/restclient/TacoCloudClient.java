package tacos.restclient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Taco;

@Service
@Slf4j
public class TacoCloudClient {

	private RestTemplate restTemplate;

	private Traverson traverson;

	private static final String API_URI = "http://localhost:8080/api";

	private static final String INGREDIENT_BY_ID = "http://localhost:8080/api/ingredients/{id}";

	@Autowired
	public TacoCloudClient(RestTemplate restTemplate) {

		try {
			this.restTemplate = restTemplate;
			traverson = new Traverson(new URI(API_URI), MediaTypes.HAL_JSON);
		} catch (URISyntaxException e) {
			log.error("Error while initializing traverson");
		}
	}

	public Ingredient getIngredientById(String ingredientId) {

		log.info("fetiching ingredient with id " + ingredientId);

		Map<String, String> urlVariables = new HashMap<>();

		urlVariables.put("id", ingredientId);

		return restTemplate.getForObject(INGREDIENT_BY_ID, Ingredient.class, urlVariables);
	}

	public Ingredient getIngredientByIdAlternative2(String ingredientId) {

		log.info("fetiching ingredient with id " + ingredientId);

		ResponseEntity<Ingredient> response = restTemplate.getForEntity(INGREDIENT_BY_ID, Ingredient.class,
				ingredientId);

		log.info("Fetched time: " + response.getHeaders().getDate());

		return response.getBody();

	}

	public void updateIngredient(Ingredient ingredient) {

		log.info("updating ingredient " + ingredient.getName());

		restTemplate.put(INGREDIENT_BY_ID, ingredient, ingredient.getId());

	}

	public void deleteIngredient(String ingredientId) {

		log.info("Deleting ingredient with id " + ingredientId);

		try {

			restTemplate.delete(INGREDIENT_BY_ID, ingredientId);
		} catch (RestClientException e) {
			log.error("The existance of tacos disables the possibility of this ingredient deletion");
		}
	}

	public void postIngredient(Ingredient ingredient) {

		// postForLocation return URI contained on header, no response payload
		restTemplate.postForObject("http://localhost:8080/api/ingredients", ingredient, Ingredient.class);
	}

	public Collection<Ingredient> getAllIngredients() {

		ParameterizedTypeReference<Resources<Ingredient>> ingredientType = new ParameterizedTypeReference<Resources<Ingredient>>() {
		};

		return traverson.follow("ingredients").toObject(ingredientType).getContent();

	}

	public Collection<Taco> getRecentTacos() {

		ParameterizedTypeReference<Resources<Taco>> tacoType = new ParameterizedTypeReference<Resources<Taco>>() {
		};

		return traverson.follow("tacos", "recents").toObject(tacoType).getContent();

	}

	public Ingredient addIngredient(Ingredient ingredient) {
		String ingredientsUrl = traverson.follow("ingredients").asLink().getHref();
		return restTemplate.postForObject(ingredientsUrl, ingredient, Ingredient.class);
	}

}
