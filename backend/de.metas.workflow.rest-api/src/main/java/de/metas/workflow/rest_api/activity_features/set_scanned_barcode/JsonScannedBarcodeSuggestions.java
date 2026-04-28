package de.metas.workflow.rest_api.activity_features.set_scanned_barcode;

import com.google.common.collect.ImmutableList;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collector;

@Value
@Builder
@Jacksonized
public class JsonScannedBarcodeSuggestions
{
	public static final JsonScannedBarcodeSuggestions EMPTY = builder().list(ImmutableList.of()).build();

	@NonNull List<JsonScannedBarcodeSuggestion> list;

	public static JsonScannedBarcodeSuggestions ofList(@Nullable final List<JsonScannedBarcodeSuggestion> list)
	{
		return list == null || list.isEmpty()
				? EMPTY
				: builder().list(ImmutableList.copyOf(list)).build();
	}

	public static Collector<JsonScannedBarcodeSuggestion, ?, JsonScannedBarcodeSuggestions> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(JsonScannedBarcodeSuggestions::ofList);
	}
}
