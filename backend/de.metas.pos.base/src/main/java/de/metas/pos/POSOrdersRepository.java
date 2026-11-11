package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.pos.repository.model.I_C_POS_Order;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Issue;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Repository Tables: C_POS_Order, C_POS_OrderLine, C_POS_Payment, AD_Issue (read only: the issues recorded on a card payment), SUMUP_Transaction (read only, in the sweep candidate subqueries)
 * Repository Cluster: POSOrdersRepository, POSOrdersLoaderAndSaver
 */
@Repository
@RequiredArgsConstructor
public class POSOrdersRepository
{
	static final String NO_ERROR_CODE = "<no ErrorCode>";

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

	/**
	 * The card payments of all orders of the terminal, oldest first, as raw {@code C_POS_Payment} id and status code
	 * (inactive ones included). The status is NOT mapped to {@link POSPaymentProcessingStatus}, so it also reports
	 * statuses the till domain does not model.
	 */
	public List<POSCardPaymentStatus> listCardPaymentStatusesByTerminalId(@NonNull final POSTerminalId posTerminalId)
	{
		final IQueryBuilder<I_C_POS_Order> orderIdsQuery = queryBL.createQueryBuilder(I_C_POS_Order.class)
				.addEqualsFilter(I_C_POS_Order.COLUMNNAME_C_POS_ID, posTerminalId);

		return queryBL.createQueryBuilder(I_C_POS_Payment.class)
				.addEqualsFilter(I_C_POS_Payment.COLUMNNAME_POSPaymentMethod, POSPaymentMethod.CARD.getCode())
				.addInSubQueryFilter(I_C_POS_Payment.COLUMNNAME_C_POS_Order_ID, I_C_POS_Order.COLUMNNAME_C_POS_Order_ID, orderIdsQuery.create())
				.orderBy(I_C_POS_Payment.COLUMNNAME_C_POS_Payment_ID)
				.create()
				.stream()
				.map(record -> new POSCardPaymentStatus(POSPaymentId.ofRepoId(record.getC_POS_Payment_ID()), record.getPOSPaymentProcessingStatus()))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * The card payments whose result is unknown and that name SumUp's client transaction id, but for which metasfresh has
	 * no SumUp transaction with that id yet (SumUp accepted the checkout, but saving its transaction failed): their
	 * transaction is to be imported from SumUp. Oldest first. The caller must close the stream.
	 */
	public Stream<POSOrderAndPaymentId> streamCardPaymentsToImport()
	{
		return queryBL.createQueryBuilder(I_C_POS_Payment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_POS_Payment.COLUMNNAME_POSPaymentMethod, POSPaymentMethod.CARD.getCode())
				.addEqualsFilter(I_C_POS_Payment.COLUMNNAME_POSPaymentProcessingStatus, POSPaymentProcessingStatus.UNKNOWN.getCode())
				.addNotNull(I_C_POS_Payment.COLUMNNAME_SUMUP_ClientTransactionID)
				.filter(TypedSqlQueryFilter.of("NOT EXISTS (SELECT 1 FROM SUMUP_Transaction t"
						+ " WHERE t.SUMUP_ClientTransactionId = " + I_C_POS_Payment.Table_Name + "." + I_C_POS_Payment.COLUMNNAME_SUMUP_ClientTransactionID + ")"))
				.orderBy(I_C_POS_Payment.COLUMNNAME_C_POS_Payment_ID)
				.create()
				.stream()
				.map(POSOrdersRepository::toPOSOrderAndPaymentId);
	}

	/**
	 * The orphan card payments of the current attempt: PENDING card payments that name no SumUp client transaction id, whose
	 * checkout started before the given time, and for which no SumUp transaction carrying their POS reference was created at
	 * or after the start of that checkout (a transaction of an earlier attempt of the same payment does not count). So their
	 * checkout stopped before SumUp's answer was stored. Oldest first. The caller must close the stream.
	 */
	public Stream<POSOrderAndPaymentId> streamOrphanCardPaymentIds(@NonNull final Instant startedBefore)
	{
		final String paymentTable = I_C_POS_Payment.Table_Name;
		return queryBL.createQueryBuilder(I_C_POS_Payment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_POS_Payment.COLUMNNAME_POSPaymentMethod, POSPaymentMethod.CARD.getCode())
				.addEqualsFilter(I_C_POS_Payment.COLUMNNAME_POSPaymentProcessingStatus, POSPaymentProcessingStatus.PENDING.getCode())
				.addEqualsFilter(I_C_POS_Payment.COLUMNNAME_SUMUP_ClientTransactionID, null)
				.addCompareFilter(I_C_POS_Payment.COLUMNNAME_CardCheckoutStartedAt, CompareQueryFilter.Operator.LESS, Timestamp.from(startedBefore))
				.filter(TypedSqlQueryFilter.of("NOT EXISTS (SELECT 1 FROM SUMUP_Transaction t"
						+ " WHERE t.C_POS_Order_ID = " + paymentTable + "." + I_C_POS_Payment.COLUMNNAME_C_POS_Order_ID
						+ " AND t.C_POS_Payment_ID = " + paymentTable + "." + I_C_POS_Payment.COLUMNNAME_C_POS_Payment_ID
						+ " AND t.Created >= " + paymentTable + "." + I_C_POS_Payment.COLUMNNAME_CardCheckoutStartedAt + ")"))
				.orderBy(I_C_POS_Payment.COLUMNNAME_C_POS_Payment_ID)
				.create()
				.stream()
				.map(POSOrdersRepository::toPOSOrderAndPaymentId);
	}

	/**
	 * The PENDING card payments that name a SumUp client transaction id for which metasfresh stores a SumUp transaction that is
	 * no longer PENDING: its status event did not resolve the payment, so the stored result is to be applied. Oldest first. The
	 * caller must close the stream.
	 */
	public Stream<POSOrderAndPaymentId> streamPendingCardPaymentsNamingAFinalSumUpTransaction()
	{
		return queryBL.createQueryBuilder(I_C_POS_Payment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_POS_Payment.COLUMNNAME_POSPaymentMethod, POSPaymentMethod.CARD.getCode())
				.addEqualsFilter(I_C_POS_Payment.COLUMNNAME_POSPaymentProcessingStatus, POSPaymentProcessingStatus.PENDING.getCode())
				.addNotNull(I_C_POS_Payment.COLUMNNAME_SUMUP_ClientTransactionID)
				.filter(TypedSqlQueryFilter.of("EXISTS (SELECT 1 FROM SUMUP_Transaction t"
						+ " WHERE t.SUMUP_ClientTransactionId = " + I_C_POS_Payment.Table_Name + "." + I_C_POS_Payment.COLUMNNAME_SUMUP_ClientTransactionID
						+ " AND t.Status <> 'PENDING')"))
				.orderBy(I_C_POS_Payment.COLUMNNAME_C_POS_Payment_ID)
				.create()
				.stream()
				.map(POSOrdersRepository::toPOSOrderAndPaymentId);
	}

	private static POSOrderAndPaymentId toPOSOrderAndPaymentId(@NonNull final I_C_POS_Payment record)
	{
		return POSOrderAndPaymentId.of(POSOrderId.ofRepoId(record.getC_POS_Order_ID()), POSPaymentId.ofRepoId(record.getC_POS_Payment_ID()));
	}

	/**
	 * The {@code ErrorCode}s of the AD_Issues recorded for the given payment, oldest first. An issue without an
	 * ErrorCode is reported as {@value #NO_ERROR_CODE} rather than dropped, so that it still counts as an issue.
	 */
	public List<String> listIssueErrorCodesByPaymentId(@NonNull final POSPaymentId posPaymentId)
	{
		final TableRecordReference paymentRef = TableRecordReference.of(I_C_POS_Payment.Table_Name, posPaymentId);
		return queryBL.createQueryBuilder(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, paymentRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, paymentRef.getRecord_ID())
				.orderBy(I_AD_Issue.COLUMNNAME_AD_Issue_ID)
				.create()
				.stream()
				.map(issue -> issue.getErrorCode() != null ? issue.getErrorCode() : NO_ERROR_CODE)
				.collect(ImmutableList.toImmutableList());
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

	/**
	 * Updates the existing order under a row lock of its {@code C_POS_Order}, taken before the order is loaded and held
	 * until the transaction ends (the thread's transaction, or a new one if there is none). Use it where concurrent
	 * updaters of the same order (the till's card checkout, SumUp status events, completion) must not overwrite each other.
	 */
	public POSOrder updateByExternalIdForUpdate(final @NonNull POSOrderExternalId externalId, final @NonNull Consumer<POSOrder> updater)
	{
		return trxManager.callInThreadInheritedTrx(() -> newLoaderAndSaver().updateByExternalIdForUpdate(externalId, updater));
	}

	/**
	 * Like {@link #updateById}, under the order's row lock, see {@link #updateByExternalIdForUpdate}.
	 */
	public void updateByIdForUpdate(@NonNull final POSOrderId id, @NonNull final Consumer<POSOrder> updater)
	{
		trxManager.callInThreadInheritedTrx(() -> newLoaderAndSaver().updateByIdForUpdate(id, updater));
	}

	/**
	 * Updates the given payment of the order under the order's row lock, see {@link #updateByExternalIdForUpdate}.
	 */
	public void updatePaymentByIdForUpdate(@NonNull final POSOrderAndPaymentId orderAndPaymentId, @NonNull final BiFunction<POSOrder, POSPayment, POSPayment> updater)
	{
		updateByIdForUpdate(orderAndPaymentId.getOrderId(), order -> order.updatePaymentById(orderAndPaymentId.getPaymentId(), payment -> updater.apply(order, payment)));
	}

	public POSOrderId getIdByExternalId(final @NonNull POSOrderExternalId externalId)
	{
		return newLoaderAndSaver().getIdByExternalId(externalId);
	}
}
