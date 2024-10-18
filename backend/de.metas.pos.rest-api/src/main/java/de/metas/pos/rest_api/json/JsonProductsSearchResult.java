package de.metas.pos.rest_api.json;

import de.metas.pos.product.POSProductsSearchResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonProductsSearchResult
{
	@NonNull List<JsonProduct> list;
	@Getter boolean barcodeMatched;

	public static JsonProductsSearchResult from(@NonNull final POSProductsSearchResult result, @NonNull final String adLanguage)
	{
		return builder()
				.list(JsonProduct.fromList(result.toList(), adLanguage))
				.barcodeMatched(result.isBarcodeMatched())
				.build();
	}
}
