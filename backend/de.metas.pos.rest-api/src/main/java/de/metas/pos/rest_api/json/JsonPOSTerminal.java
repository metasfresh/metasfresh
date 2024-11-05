package de.metas.pos.rest_api.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.pos.POSPaymentMethod;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonPOSTerminal
{
	@NonNull POSTerminalId id;
	@NonNull String caption;
	@Nullable WorkplaceId workplaceId;
	@NonNull String currencySymbol;
	int pricePrecision;
	int currencyPrecision;
	@NonNull Set<POSPaymentMethod> availablePaymentMethods;
	boolean cashJournalOpen;
	@NonNull BigDecimal cashLastBalance;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable List<JsonProduct> products;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable List<JsonPOSOrder> openOrders;

	@NonNull
	public static JsonPOSTerminal from(@NonNull final POSTerminal posTerminal, @NonNull final String adLanguage)
	{
		return builderFrom(posTerminal, adLanguage).build();
	}

	public static JsonPOSTerminalBuilder builderFrom(final @NonNull POSTerminal posTerminal, final @NonNull String adLanguage)
	{
		return JsonPOSTerminal.builder()
				.id(posTerminal.getId())
				.caption(posTerminal.getName())
				.workplaceId(posTerminal.getWorkplaceId())
				.currencySymbol(posTerminal.getCurrencySymbol(adLanguage))
				.pricePrecision(posTerminal.getPricePrecision().toInt())
				.currencyPrecision(posTerminal.getCurrencyPrecision().toInt())
				.availablePaymentMethods(posTerminal.getAvailablePaymentMethods())
				.cashJournalOpen(posTerminal.isCashJournalOpen())
				.cashLastBalance(posTerminal.getCashLastBalance().toBigDecimal());
	}
}
