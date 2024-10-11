package de.metas.pos;

import de.metas.pos.repository.model.I_C_POS_Order;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class POSOrdersRepository
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final POSOrderEventDispatcher eventDispatcher;

	private POSOrdersLoaderAndSaver newLoaderAndSaver()
	{
		return POSOrdersLoaderAndSaver.builder()
				.queryBL(queryBL)
				.eventDispatcher(eventDispatcher)
				.build();
	}

	public List<POSOrder> list(@NonNull final POSOrderQuery query)
	{
		final List<I_C_POS_Order> orderRecords = toSqlQuery(query)
				.create()
				.list();

		return newLoaderAndSaver().loadFromRecords(orderRecords);
	}

	public Optional<POSOrder> firstOnly(@NonNull final POSOrderQuery query)
	{
		return toSqlQuery(query)
				.create()
				.firstOnlyOptional(I_C_POS_Order.class)
				.map(record -> newLoaderAndSaver().loadFromRecord(record));
	}

	private IQueryBuilder<I_C_POS_Order> toSqlQuery(final POSOrderQuery query)
	{
		final IQueryBuilder<I_C_POS_Order> sqlQueryBuilder = queryBL.createQueryBuilder(I_C_POS_Order.class)
				.addEqualsFilter(I_C_POS_Order.COLUMNNAME_C_POS_ID, query.getPosTerminalId())
				.addEqualsFilter(I_C_POS_Order.COLUMNNAME_Cashier_ID, query.getCashierId());

		if (query.isOpen())
		{
			sqlQueryBuilder.addInArrayFilter(I_C_POS_Order.COLUMNNAME_Status, POSOrderStatus.OPEN_STATUSES);
		}

		final Set<POSOrderExternalId> onlyOrderExternalIds = query.getOnlyOrderExternalIds();
		if (onlyOrderExternalIds != null && !onlyOrderExternalIds.isEmpty())
		{
			sqlQueryBuilder.addInArrayFilter(I_C_POS_Order.COLUMNNAME_ExternalId, POSOrderExternalId.toStringSet(onlyOrderExternalIds));
		}

		return sqlQueryBuilder;
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

	public void updateById(@NonNull final POSOrderId id, @NonNull final Consumer<POSOrder> updater)
	{
		trxManager.callInThreadInheritedTrx(() -> newLoaderAndSaver().updateById(id, updater));
	}

	public void updatePaymentById(@NonNull final POSOrderAndPaymentId orderAndPaymentId, @NonNull final BiFunction<POSOrder, POSPayment, POSPayment> updater)
	{
		updateById(orderAndPaymentId.getOrderId(), order -> order.updatePaymentById(orderAndPaymentId.getPaymentId(), payment -> updater.apply(order, payment)));
	}

	public void save(@NonNull final POSOrder order)
	{
		newLoaderAndSaver().save(order);
	}

}
