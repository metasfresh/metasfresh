package de.metas.inoutcandidate.qty_reservation;

import com.google.common.collect.ImmutableList;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_QtyReservation;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Repository for loading {@link QtyReservation} domain objects from the {@code M_QtyReservation} table.
 */
@Repository
public class QtyReservationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Load all active reservations (Qty > 0) for the given product IDs.
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
				.addCompareFilter(I_M_QtyReservation.COLUMNNAME_Qty, CompareQueryFilter.Operator.GREATER, BigDecimal.ZERO)
				.addInArrayFilter(I_M_QtyReservation.COLUMNNAME_M_Product_ID, productIds)
				.create()
				.stream()
				.map(QtyReservationRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private static QtyReservation fromRecord(@NonNull final I_M_QtyReservation record)
	{
		return QtyReservation.builder()
				.orderLineId(OrderLineId.ofRepoId(record.getC_OrderLine_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.attributesKey(AttributesKey.ofString(record.getAttributesKey()))
				.qty(Quantitys.of(record, I_M_QtyReservation::getQty, I_M_QtyReservation::getC_UOM_ID))
				.build();
	}
}
 