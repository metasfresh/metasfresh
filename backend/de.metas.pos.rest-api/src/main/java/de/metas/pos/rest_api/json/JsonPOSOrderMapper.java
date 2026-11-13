package de.metas.pos.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.money.CurrencyId;
import de.metas.pos.POSCardReleaseWait;
import de.metas.pos.POSOrder;
import de.metas.pos.POSPayment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * The one place that turns POS orders into {@link JsonPOSOrder} for every channel (REST responses, order lists, websocket
 * events), so that each of them tells the till the same {@link JsonPOSPayment#getReleasableAt()}.
 */
@Component
@RequiredArgsConstructor
public class JsonPOSOrderMapper
{
	@NonNull private final POSCardReleaseWait cardReleaseWait;

	public JsonPOSOrder toJson(@NonNull final POSOrder order, @NonNull final Function<CurrencyId, String> getCurrencySymbolById)
	{
		return JsonPOSOrder.from(order, getCurrencySymbolById, payment -> getReleasableAt(order, payment));
	}

	public ImmutableList<JsonPOSOrder> toJsonList(@NonNull final List<POSOrder> orders, @NonNull final Function<CurrencyId, String> getCurrencySymbolById)
	{
		return orders.stream()
				.sorted(Comparator.comparing(order -> order.getLocalId() != null ? order.getLocalId().getRepoId() : Integer.MAX_VALUE))
				.map(order -> toJson(order, getCurrencySymbolById))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * @return for a card payment whose result is unknown the instant from which it may be released (see {@link POSCardReleaseWait});
	 * null for any other payment
	 */
	@Nullable
	private Instant getReleasableAt(@NonNull final POSOrder order, @NonNull final POSPayment payment)
	{
		if (!payment.isUnknownCardPayment() || payment.getCardCheckoutStartedAt() == null)
		{
			return null;
		}
		return cardReleaseWait.getReleasableAt(order.getClientAndOrgId(), payment.getCardCheckoutStartedAt());
	}
}
