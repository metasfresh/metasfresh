package de.metas.pos.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSOrderStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonPOSOrder
{
	@NonNull POSOrderExternalId uuid;
	@Nullable POSOrderStatus status;
	@Nullable String currencySymbol;
	@Nullable BigDecimal totalAmt;
	@NonNull List<JsonPOSOrderLine> lines;

	public static JsonPOSOrder of(@NonNull final POSOrder order, @NonNull final JsonContext jsonContext)
	{
		final String currencySymbol = jsonContext.getCurrencySymbol(order.getCurrencyId());
		return builder()
				.uuid(order.getExternalId())
				.status(order.getStatus())
				.currencySymbol(currencySymbol)
				.totalAmt(order.getTotalAmt())
				.lines(order.getLines().stream()
						.map(line -> JsonPOSOrderLine.of(line, currencySymbol))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}
