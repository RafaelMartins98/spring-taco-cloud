package tacos.repositories;

import tacos.entities.Ingredient;

public interface IngredientRepository {

	Iterable<Ingredient> findAll();

	Ingredient findOne(String id);

	Ingredient save(Ingredient ingredient);

}
