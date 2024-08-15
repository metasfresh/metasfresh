package de.metas.manufacturing.workflows_api.activity_handlers.issue.json;

import de.metas.image.AdImageId;
import de.metas.product.hazard_symbol.HazardSymbol;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class JsonHazardSymbol
{
	@NonNull String name;
	@Nullable AdImageId imageId;

	public static JsonHazardSymbol of(@NonNull HazardSymbol hazardSymbol, @NonNull String adLanguange)
	{
		return builder()
				.name(hazardSymbol.getName().translate(adLanguange))
				.imageId(hazardSymbol.getImageId())
				.build();
	}
}
