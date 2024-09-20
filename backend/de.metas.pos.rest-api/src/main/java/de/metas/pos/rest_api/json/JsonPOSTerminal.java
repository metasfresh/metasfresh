package de.metas.pos.rest_api.json;

import de.metas.pos.POSTerminal;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPOSTerminal
{
	@NonNull String currencySymbol;
	int pricePrecision;
	int currencyPrecision;

	@NonNull
	public static JsonPOSTerminal from(@NonNull final POSTerminal posTerminal, @NonNull final String adLanguage)
	{
		return JsonPOSTerminal.builder()
				.currencySymbol(posTerminal.getCurrencySymbol(adLanguage))
				.pricePrecision(posTerminal.getPricePrecision().toInt())
				.currencyPrecision(posTerminal.getCurrencyPrecision().toInt())
				.build();
	}
}
