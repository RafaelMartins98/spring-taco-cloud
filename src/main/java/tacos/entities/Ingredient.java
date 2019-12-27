package tacos.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import tacos.enums.Type;

@Data
@RequiredArgsConstructor
public class Ingredient {

	
	private final String id;
	
	private final String name;

	private final Type type;
	

}
