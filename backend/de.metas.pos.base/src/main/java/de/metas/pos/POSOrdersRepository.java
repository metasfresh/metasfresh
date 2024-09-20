package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class POSOrdersRepository
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	// TODO persist to database
	private final LinkedHashMap<POSOrderExternalId, POSOrder> ordersByExternalId = new LinkedHashMap<>();

	public List<POSOrder> getOpenOrders(@NonNull final UserId cashierId)
	{
		return ordersByExternalId.values()
				.stream()
				.filter(order -> UserId.equals(order.getCashierId(), cashierId))
				.filter(order -> order.getStatus().isDrafted() || order.getStatus().isWaitingPayment())
				.collect(ImmutableList.toImmutableList());
	}

	public POSOrder getByExternalId(@NonNull final POSOrderExternalId externalId)
	{
		final POSOrder order = ordersByExternalId.get(externalId);
		if (order == null)
		{
			throw new AdempiereException("No POSOrder found for " + externalId);
		}
		return order;
	}

	public POSOrder updateByExternalId(final @NonNull POSOrderExternalId externalId, final @NonNull Consumer<POSOrder> updater)
	{
		return createOrUpdateByExternalId(
				externalId,
				externalId0 -> {
					throw new AdempiereException("No order found for external id " + externalId);
				},
				updater);
	}

	public POSOrder createOrUpdateByExternalId(
			@NonNull final POSOrderExternalId externalId,
			@NonNull final Function<POSOrderExternalId, POSOrder> factory,
			@NonNull final Consumer<POSOrder> updater)
	{
		return trxManager.callInThreadInheritedTrx(() -> {
			POSOrder order = ordersByExternalId.get(externalId);
			if (order == null)
			{
				order = factory.apply(externalId);
			}

			updater.accept(order);

			ordersByExternalId.put(order.getExternalId(), order);

			return order;
		});
	}

}
