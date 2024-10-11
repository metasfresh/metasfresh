package de.metas.pos.rest_api.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;
import de.metas.i18n.BooleanWithReason;
import de.metas.money.CurrencyId;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSOrderStatus;
import de.metas.pos.POSTerminalId;
import de.metas.pos.remote.RemotePOSOrder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Value
@Builder
@Jacksonized
public class JsonPOSOrder
{
	@NonNull POSOrderExternalId uuid;
	@NonNull POSTerminalId posTerminalId;
	@Nullable POSOrderStatus status;
	@Nullable String currencySymbol;
	@Nullable BigDecimal totalAmt;
	@Nullable BigDecimal taxAmt;
	@Nullable BigDecimal paidAmt;
	@Nullable BigDecimal openAmt;
	@NonNull List<JsonPOSOrderLine> lines;
	@Nullable List<JsonPOSPayment> payments;

	boolean allowDraft;
	@JsonInclude(JsonInclude.Include.NON_EMPTY) String allowDraftReason;
	boolean allowWaitPayment;
	@JsonInclude(JsonInclude.Include.NON_EMPTY) String allowWaitPaymentReason;
	boolean allowTryComplete;
	@JsonInclude(JsonInclude.Include.NON_EMPTY) String allowTryCompleteReason;
	boolean allowVoid;
	@JsonInclude(JsonInclude.Include.NON_EMPTY) String allowVoidReason;
	boolean allowAddPayment;
	@JsonInclude(JsonInclude.Include.NON_EMPTY) String allowAddPaymentReason;

	int hashCode;

	public static JsonPOSOrder from(@NonNull final POSOrder order, @NonNull final Function<CurrencyId, String> getCurrencySymbolById)
	{
		final String currencySymbol = getCurrencySymbolById.apply(order.getCurrencyId());

		final BooleanWithReason allowDraft = order.checkCanTransitionToDrafted();
		final BooleanWithReason allowWaitingPayment = order.checkCanTransitionToWaitingPayment();
		final BooleanWithReason allowTryComplete = order.checkCanTryTransitionToCompleted();
		final BooleanWithReason allowVoid = order.checkCanTransitionToVoided();
		final BooleanWithReason allowAddPayment = order.checkUserCanAddPayment();

		return builder()
				.uuid(order.getExternalId())
				.posTerminalId(order.getPosTerminalId())
				.status(order.getStatus())
				.currencySymbol(currencySymbol)
				.totalAmt(order.getTotalAmt().toBigDecimal())
				.taxAmt(order.getTaxAmt().toBigDecimal())
				.paidAmt(order.getPaidAmt().toBigDecimal())
				.openAmt(order.getOpenAmt().toBigDecimal())
				.lines(order.getLines().stream()
						.map(line -> JsonPOSOrderLine.of(line, currencySymbol))
						.collect(ImmutableList.toImmutableList()))
				.payments(order.streamPaymentsNotDeleted()
						.map(JsonPOSPayment::of)
						.collect(ImmutableList.toImmutableList()))
				//
				.allowDraft(allowDraft.isTrue())
				.allowDraftReason(allowDraft.getReasonAsString())
				.allowWaitPayment(allowWaitingPayment.isTrue())
				.allowWaitPaymentReason(allowWaitingPayment.getReasonAsString())
				.allowTryComplete(allowTryComplete.isTrue())
				.allowTryCompleteReason(allowTryComplete.getReasonAsString())
				.allowVoid(allowVoid.isTrue())
				.allowVoidReason(allowVoid.getReasonAsString())
				.allowAddPayment(allowAddPayment.isTrue())
				.allowAddPaymentReason(allowAddPayment.getReasonAsString())
				//
				.hashCode(order.hashCode())
				//
				.build();
	}

	public static ImmutableList<JsonPOSOrder> fromList(@NonNull final List<POSOrder> orders, @NonNull final Function<CurrencyId, String> getCurrencySymbolById)
	{
		return orders.stream()
				.sorted(Comparator.comparing(posOrder -> posOrder.getLocalId() != null ? posOrder.getLocalId().getRepoId() : Integer.MAX_VALUE))
				.map(order -> from(order, getCurrencySymbolById))
				.collect(ImmutableList.toImmutableList());
	}

	public RemotePOSOrder toRemotePOSOrder()
	{
		return RemotePOSOrder.builder()
				.uuid(uuid)
				.posTerminalId(posTerminalId)
				.lines(lines.stream().map(JsonPOSOrderLine::toRemotePOSOrderLine).collect(ImmutableList.toImmutableList()))
				.payments(payments != null && !payments.isEmpty()
						? payments.stream().map(JsonPOSPayment::toRemotePOSPayment).collect(ImmutableList.toImmutableList())
						: ImmutableList.of())
				.build();
	}
}
