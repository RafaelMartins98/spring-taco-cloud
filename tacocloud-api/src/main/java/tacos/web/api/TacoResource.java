package tacos.web.api;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;
import tacos.Taco;

@Relation(value = "taco", collectionRelation = "tacos")
public class TacoResource extends ResourceSupport{
	
	
	public static final TacoResourceAssembler tacoAssembler = new TacoResourceAssembler();
	
	private static final IngredientResourceAssembler ingredientAssembler = new IngredientResourceAssembler();
	
	private @Getter String name;
	
	private @Getter Date createdAt;
	
	private @Getter List<IngredientResource> ingredients;
	
	public TacoResource(Taco taco) {
		this.name = taco.getName();
		this.createdAt = taco.getCreatedAt();
		this.ingredients = ingredientAssembler.toResources(taco.getIngredients());
	}
	
	

}
