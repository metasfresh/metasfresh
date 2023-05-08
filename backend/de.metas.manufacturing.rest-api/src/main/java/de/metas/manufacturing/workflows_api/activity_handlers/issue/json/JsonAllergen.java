package de.metas.manufacturing.workflows_api.activity_handlers.issue.json;

import de.metas.product.allergen.Allergen;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class JsonAllergen
{
	@NonNull String name;
	@Nullable String color;

	public static JsonAllergen of(@NonNull Allergen allergen, @NonNull String adLanguange)
	{
		return builder()
				.name(allergen.getName().translate(adLanguange))
				.color(allergen.getColor())
				.build();
	}
}
