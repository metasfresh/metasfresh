package de.metas.pos.rest_api.json;

import de.metas.pos.POSProduct;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonProduct
{
	@NonNull ProductId id;
	@NonNull String name;

	public static JsonProduct from(@NonNull final POSProduct product, @NonNull final String adLanguage)
	{
		return JsonProduct.builder()
				.id(product.getId())
				.name(product.getName(adLanguage))
				.build();
	}

}
