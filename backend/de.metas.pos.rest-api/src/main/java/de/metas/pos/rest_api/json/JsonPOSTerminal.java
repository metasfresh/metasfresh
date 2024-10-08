package de.metas.pos.rest_api.json;

import de.metas.pos.POSPaymentMethod;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonPOSTerminal
{
	@NonNull POSTerminalId id;
	@NonNull String caption;
	@NonNull String currencySymbol;
	int pricePrecision;
	int currencyPrecision;
	@NonNull Set<POSPaymentMethod> availablePaymentMethods;
	boolean isCashJournalOpen;
	@NonNull BigDecimal cashLastBalance;

	@NonNull
	public static JsonPOSTerminal from(@NonNull final POSTerminal posTerminal, @NonNull final String adLanguage)
	{
		return JsonPOSTerminal.builder()
				.id(posTerminal.getId())
				.caption(posTerminal.getName())
				.currencySymbol(posTerminal.getCurrencySymbol(adLanguage))
				.pricePrecision(posTerminal.getPricePrecision().toInt())
				.currencyPrecision(posTerminal.getCurrencyPrecision().toInt())
				.availablePaymentMethods(posTerminal.getAvailablePaymentMethods())
				.isCashJournalOpen(posTerminal.isCashJournalOpen())
				.cashLastBalance(posTerminal.getCashLastBalance().toBigDecimal())
				.build();
	}
}
