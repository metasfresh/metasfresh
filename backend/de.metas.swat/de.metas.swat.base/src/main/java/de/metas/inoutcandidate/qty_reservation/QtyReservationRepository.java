package de.metas.inoutcandidate.qty_reservation;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.QtyTU;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_QtyReservation;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;
import java.util.function.UnaryOperator;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Repository for loading {@link QtyReservation} domain objects from the {@code M_QtyReservation} table.
 */
@Repository
public class QtyReservationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public QtyReservation getById(@NonNull QtyReservationId id)
	{
		final I_M_QtyReservation record = queryBL.createQueryBuilder(I_M_QtyReservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_QtyReservation.COLUMNNAME_M_QtyReservation_ID, id)
				.create()
				.firstOnlyNotNull();
		return QtyReservationLoaderAndSaver.fromRecord(record);
	}

	/**
	 * Load all active, unprocessed reservations (unfulfilled qty) for the given product IDs.
	 * <p>
	 * Returns ALL such reservations for these products, not just those for specific order lines.
	 * This ensures that reservations held by order lines outside a particular batch are also accounted for.
	 */
	@NonNull
	public ImmutableList<QtyReservation> getActiveByProductIds(@NonNull final Set<ProductId> productIds)
	{
		if (productIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_QtyReservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_QtyReservation.COLUMNNAME_M_Product_ID, productIds)
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_Processed, false)
				.create()
				.stream()
				.map(QtyReservationLoaderAndSaver::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Load all active reservation records for the given order line IDs,
	 * apply the {@code updater} to each domain object, and batch-save changed records.
	 * <p>
	 * Records are processed in priority order: OH (On Hand) before PS (Planned Supply).
	 */
	public void updateByOrderLineIds(
			@NonNull final Set<OrderLineId> orderLineIds,
			@NonNull final UnaryOperator<QtyReservation> updater)
	{
		final QtyReservationLoaderAndSaver loaderAndSaver = new QtyReservationLoaderAndSaver();
		loaderAndSaver.updateByOrderLineIds(orderLineIds, updater);
	}

	public QtyReservationId createReservation(@NonNull final CreateQtyReservationRequest request)
	{
		final I_M_QtyReservation record = newInstance(I_M_QtyReservation.class);
		record.setC_Order_ID(request.getOrderAndLineId().getOrderRepoId());
		record.setC_OrderLine_ID(request.getOrderAndLineId().getOrderLineRepoId());
		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setM_Warehouse_ID(request.getWarehouseId().getRepoId());
		if (request.getVendorBPartnerId() != null)
		{
			record.setC_BPartner_Vendor_ID(request.getVendorBPartnerId().getRepoId());
		}
		record.setSupplyType(request.getSupplyType().getCode());
		if (request.getDatePromised() != null)
		{
			record.setDatePromised(TimeUtil.asTimestamp(request.getDatePromised()));
		}
		record.setQtyTU(request.getQtyTU().toBigDecimal());
		record.setQty(request.getQty().toBigDecimal());
		record.setC_UOM_ID(request.getQty().getUomId().getRepoId());
		record.setAttributesKey(request.getAttributesKey() != null ? request.getAttributesKey().getAsString() : null);
		saveRecord(record);

		return QtyReservationId.ofRepoId(record.getM_QtyReservation_ID());
	}

	public boolean deleteReservation(@NonNull final DeleteQtyReservationRequest request)
	{
		final int deleteCount = toSqlQuery(request).create().delete();
		return deleteCount > 0;
	}

	/**
	 * Returns all active, unprocessed reservations for the given order line.
	 * Used by the HU picker to honor each reservation's attributes when picking on-the-fly.
	 */
	@NonNull
	public ImmutableList<QtyReservation> getActiveByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return queryBL.createQueryBuilder(I_M_QtyReservation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.orderBy(I_M_QtyReservation.COLUMNNAME_M_QtyReservation_ID)
				.create()
				.stream()
				.map(QtyReservationLoaderAndSaver::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public QtyTU getReservedQtyTU(final @NotNull DeleteQtyReservationRequest request)
	{
		return getReservedQtyTU(toSqlQuery(request));
	}

	private IQueryBuilder<I_M_QtyReservation> toSqlQuery(@NonNull final DeleteQtyReservationRequest request)
	{
		final IQueryBuilder<I_M_QtyReservation> sqlQueryBuilder = queryNotProcessedByOrderLine(request.getOrderAndLineId())
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_SupplyType, request.getSupplyType().getCode());

		if (request.getDatePromised() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_DatePromised, TimeUtil.asTimestamp(request.getDatePromised()));
		}

		return sqlQueryBuilder;
	}

	public QtyTU getReservedQtyTU(@NonNull final OrderAndLineId orderLineId)
	{
		return getReservedQtyTU(queryNotProcessedByOrderLine(orderLineId));
	}

	private IQueryBuilder<I_M_QtyReservation> queryNotProcessedByOrderLine(final @NotNull OrderAndLineId orderAndLineId)
	{
		return queryBL.createQueryBuilder(I_M_QtyReservation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_C_Order_ID, orderAndLineId.getOrderId())
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID, orderAndLineId.getOrderLineId());
	}

	private static QtyTU getReservedQtyTU(@NonNull final IQueryBuilder<I_M_QtyReservation> queryBuilder)
	{
		final BigDecimal result = queryBuilder.create().aggregate(I_M_QtyReservation.COLUMNNAME_QtyTU, IQuery.Aggregate.SUM, BigDecimal.class);
		return result != null ? QtyTU.ofBigDecimal(result) : QtyTU.ZERO;
	}
}
