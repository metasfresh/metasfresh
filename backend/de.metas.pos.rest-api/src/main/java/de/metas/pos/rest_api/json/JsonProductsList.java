package de.metas.pos.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.pos.POSProductsList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Comparator;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonProductsList
{
	@NonNull List<JsonProduct> list;

	public static JsonProductsList from(@NonNull final POSProductsList list, @NonNull final String adLanguage)
	{
		return builder()
				.list(list.stream()
						.map(product -> JsonProduct.from(product, adLanguage))
						.sorted(Comparator.comparing(JsonProduct::getName))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
