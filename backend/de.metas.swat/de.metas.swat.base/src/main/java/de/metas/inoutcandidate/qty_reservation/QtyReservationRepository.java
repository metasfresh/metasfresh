package de.metas.inoutcandidate.qty_reservation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_QtyReservation;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Repository for loading {@link QtyReservation} domain objects from the {@code M_QtyReservation} table.
 */
@Repository
public class QtyReservationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Load all active reservations with unfulfilled qty ({@code Qty - QtyDelivered > 0}) for the given product IDs.
	 * <p>
	 * Returns ALL active reservations for these products, not just those for specific order lines.
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
				.filter(TypedSqlQueryFilter.of("Qty - QtyDelivered > 0"))
				.create()
				.stream()
				.map(QtyReservationRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Load all active M_QtyReservation records for the given order line IDs, in one query.
	 * Records are grouped by OrderLineId and sorted by SupplyType (OH before PS).
	 */
	@NonNull
	public ImmutableListMultimap<OrderLineId, I_M_QtyReservation> getRecordsByOrderLineIds(@NonNull final Set<OrderLineId> orderLineIds)
	{
		if (orderLineIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		final List<I_M_QtyReservation> records = queryBL.createQueryBuilder(I_M_QtyReservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID, orderLineIds)
				.orderBy(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID)
				.orderBy(I_M_QtyReservation.COLUMNNAME_SupplyType) // OH < PS alphabetically
				.create()
				.list();

		return records.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> OrderLineId.ofRepoId(record.getC_OrderLine_ID()),
						record -> record
				));
	}

	/**
	 * Spread {@code totalQtyDelivered} across all active M_QtyReservation records for the given order line.
	 * Records are processed in priority order (On Hand first, then Planned Supply),
	 * each capped at its own {@code Qty}.
	 */
	public void spreadQtyDelivered(@NonNull final OrderLineId orderLineId, @NonNull final BigDecimal totalQtyDelivered)
	{
		final List<I_M_QtyReservation> records = queryBL.createQueryBuilder(I_M_QtyReservation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.orderBy(I_M_QtyReservation.COLUMNNAME_SupplyType) // OH < PS alphabetically
				.create()
				.list();

		BigDecimal remaining = totalQtyDelivered;
		for (final I_M_QtyReservation record : records)
		{
			final BigDecimal recordQty = record.getQty();
			final BigDecimal allocated = remaining.min(recordQty).max(BigDecimal.ZERO);
			record.setQtyDelivered(allocated);
			InterfaceWrapperHelper.save(record);
			remaining = remaining.subtract(allocated);
		}
	}

	@NonNull
	static QtyReservation fromRecord(@NonNull final I_M_QtyReservation record)
	{
		return QtyReservation.builder()
				.orderLineId(OrderLineId.ofRepoId(record.getC_OrderLine_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.attributesKey(AttributesKey.ofString(record.getAttributesKey()))
				.qty(Quantitys.of(record, I_M_QtyReservation::getQty, I_M_QtyReservation::getC_UOM_ID))
				.qtyDelivered(Quantitys.of(record, I_M_QtyReservation::getQtyDelivered, I_M_QtyReservation::getC_UOM_ID))
				.build();
	}
}
