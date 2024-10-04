package de.metas.pos;

import de.metas.pos.repository.model.I_C_POS_Order;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class POSOrdersRepository
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull final IQueryBL queryBL = Services.get(IQueryBL.class);

	private POSOrdersLoaderAndSaver newLoaderAndSaver()
	{
		return POSOrdersLoaderAndSaver.builder()
				.queryBL(queryBL)
				.build();
	}

	public List<POSOrder> getOpenOrders(@NonNull final UserId cashierId)
	{
		final List<I_C_POS_Order> orderRecords = queryBL.createQueryBuilder(I_C_POS_Order.class)
				.addEqualsFilter(I_C_POS_Order.COLUMNNAME_Cashier_ID, cashierId)
				.addInArrayFilter(I_C_POS_Order.COLUMNNAME_Status, POSOrderStatus.Drafted, POSOrderStatus.WaitingPayment)
				.create()
				.list();

		return newLoaderAndSaver().loadFromRecords(orderRecords);
	}

	public POSOrder getById(@NonNull final POSOrderId posOrderId)
	{
		return newLoaderAndSaver().getById(posOrderId);
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
		return trxManager.callInThreadInheritedTrx(() -> newLoaderAndSaver().createOrUpdateByExternalId(externalId, factory, updater));
	}

	public POSOrder updateById(@NonNull final POSOrderId id, @NonNull final Consumer<POSOrder> updater)
	{
		return trxManager.callInThreadInheritedTrx(() -> newLoaderAndSaver().updateById(id, updater));
	}

	public void save(@NonNull final POSOrder order)
	{
		newLoaderAndSaver().save(order);
	}

}
