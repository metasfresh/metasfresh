package de.metas.inoutcandidate.qty_reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_QtyReservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;

/**
 * Package-private loader and saver for {@link QtyReservation} domain objects.
 * Instantiated per operation by {@link QtyReservationRepository}.
 * <p>
 * Provides an identity map (cache) over {@code I_M_QtyReservation} records,
 * bulk-loads via a single SQL query, applies a domain-level updater,
 * and batch-saves only changed records.
 */
class QtyReservationLoaderAndSaver
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Identity map: QtyReservationId → record, in load order
	 */
	private final HashMap<QtyReservationId, I_M_QtyReservation> recordsCache = new HashMap<>();
	/**
	 * Maintains insertion order for iteration
	 */
	private final List<QtyReservationId> recordIdsInOrder = new ArrayList<>();

	void updateByOrderLineIds(
			@NonNull final Set<OrderLineId> orderLineIds,
			@NonNull final UnaryOperator<QtyReservation> updater)
	{
		if (orderLineIds.isEmpty())
		{
			return;
		}

		warmUp(orderLineIds);

		final List<I_M_QtyReservation> toSave = new ArrayList<>();
		for (final QtyReservationId recordId : recordIdsInOrder)
		{
			final I_M_QtyReservation record = recordsCache.get(recordId);
			final QtyReservation before = fromRecord(record);
			final QtyReservation after = updater.apply(before);

			if (!Objects.equals(before, after))
			{
				updateRecord(record, after);
				toSave.add(record);
			}
		}

		if (!toSave.isEmpty())
		{
			InterfaceWrapperHelper.saveAll(toSave);
		}
	}

	private void warmUp(@NonNull final Set<OrderLineId> orderLineIds)
	{
		final List<I_M_QtyReservation> records = queryBL.createQueryBuilder(I_M_QtyReservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID, orderLineIds)
				.orderBy(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID)
				.orderBy(I_M_QtyReservation.COLUMNNAME_SupplyType) // OH < PS alphabetically
				.create()
				.list();

		for (final I_M_QtyReservation record : records)
		{
			final QtyReservationId id = QtyReservationId.ofRepoId(record.getM_QtyReservation_ID());
			recordsCache.put(id, record);
			recordIdsInOrder.add(id);
		}
	}

	@NonNull
	static QtyReservation fromRecord(@NonNull final I_M_QtyReservation record)
	{
		return QtyReservation.builder()
				.id(QtyReservationId.ofRepoId(record.getM_QtyReservation_ID()))
				.orderLineId(OrderLineId.ofRepoId(record.getC_OrderLine_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.attributesKey(AttributesKey.ofString(record.getAttributesKey()))
				.qtyTU(QtyTU.ofBigDecimal(record.getQtyTU()))
				.qty(Quantitys.of(record, I_M_QtyReservation::getQty, I_M_QtyReservation::getC_UOM_ID))
				.qtyDelivered(Quantitys.of(record, I_M_QtyReservation::getQtyDelivered, I_M_QtyReservation::getC_UOM_ID))
				.build();
	}

	private static void updateRecord(
			@NonNull final I_M_QtyReservation record,
			@NonNull final QtyReservation domain)
	{
		record.setQtyDelivered(domain.getQtyDelivered().toBigDecimal());
		record.setProcessed(domain.isFullyDelivered());
	}
}
