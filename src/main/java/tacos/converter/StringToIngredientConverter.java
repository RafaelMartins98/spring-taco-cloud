package tacos.converter;

import org.springframework.core.convert.converter.Converter;

import tacos.entities.Ingredient;
import tacos.enums.Type;

public class StringToIngredientConverter implements Converter<String, Ingredient> {

	@Override
	public Ingredient convert(String source) {

		String[] data = getOnlyAttributes(source);

		return new Ingredient(data[0], data[1], Type.valueOf(data[2]));
	}

	private String[] getOnlyAttributes(String instance) {
		
		String[] data = instance.substring(instance.indexOf("(") + 1, instance.lastIndexOf("") -1).split(",");
		
		for (int i = 0; i < data.length; i++) {
			data[i] = data[i].substring(data[i].indexOf("=") + 1);
		}
		
		return data;
	}

}
