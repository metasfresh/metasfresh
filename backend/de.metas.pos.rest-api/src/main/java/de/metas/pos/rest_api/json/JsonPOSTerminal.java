package de.metas.pos.rest_api.json;

import de.metas.pos.POSTerminal;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPOSTerminal
{
	@NonNull String currencySymbol;
	int pricePrecision;
	int currencyPrecision;
	boolean isCashJournalOpen;
	@NonNull BigDecimal cashLastBalance;

	@NonNull
	public static JsonPOSTerminal from(@NonNull final POSTerminal posTerminal, @NonNull final String adLanguage)
	{
		return JsonPOSTerminal.builder()
				.currencySymbol(posTerminal.getCurrencySymbol(adLanguage))
				.pricePrecision(posTerminal.getPricePrecision().toInt())
				.currencyPrecision(posTerminal.getCurrencyPrecision().toInt())
				.isCashJournalOpen(posTerminal.isCashJournalOpen())
				.cashLastBalance(posTerminal.getCashLastBalance().toBigDecimal())
				.build();
	}
}
