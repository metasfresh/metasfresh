package de.metas.pos.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSOrderStatus;
import de.metas.pos.remote.RemotePOSOrder;
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
	@Nullable BigDecimal taxAmt;
	@Nullable BigDecimal paidAmt;
	@Nullable BigDecimal openAmt;
	@NonNull List<JsonPOSOrderLine> lines;
	@Nullable List<JsonPOSPayment> payments;

	public static JsonPOSOrder of(@NonNull final POSOrder order, @NonNull final JsonContext jsonContext)
	{
		final String currencySymbol = jsonContext.getCurrencySymbol(order.getCurrencyId());
		return builder()
				.uuid(order.getExternalId())
				.status(order.getStatus())
				.currencySymbol(currencySymbol)
				.totalAmt(order.getTotalAmt())
				.taxAmt(order.getTaxAmt())
				.paidAmt(order.getPaidAmt())
				.openAmt(order.getOpenAmt())
				.lines(order.getLines().stream()
						.map(line -> JsonPOSOrderLine.of(line, currencySymbol))
						.collect(ImmutableList.toImmutableList()))
				.payments(order.getPayments().stream()
						.map(JsonPOSPayment::of)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public RemotePOSOrder toRemotePOSOrder()
	{
		return RemotePOSOrder.builder()
				.uuid(uuid)
				.lines(lines.stream().map(JsonPOSOrderLine::toRemotePOSOrderLine).collect(ImmutableList.toImmutableList()))
				.payments(payments != null && !payments.isEmpty()
						? payments.stream().map(JsonPOSPayment::toRemotePOSPayment).collect(ImmutableList.toImmutableList())
						: ImmutableList.of())
				.build();
	}
}
