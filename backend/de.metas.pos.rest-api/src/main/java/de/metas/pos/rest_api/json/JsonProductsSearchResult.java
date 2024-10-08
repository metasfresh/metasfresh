package de.metas.pos.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.pos.POSProductsSearchResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Comparator;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonProductsSearchResult
{
	@NonNull List<JsonProduct> list;
	@Getter boolean isBarcodeMatched;

	public static JsonProductsSearchResult from(@NonNull final POSProductsSearchResult result, @NonNull final String adLanguage)
	{
		return builder()
				.list(result.stream()
						.map(product -> JsonProduct.from(product, adLanguage))
						.sorted(Comparator.comparing(JsonProduct::getName))
						.collect(ImmutableList.toImmutableList()))
				.isBarcodeMatched(result.isBarcodeMatched())
				.build();
	}
}
