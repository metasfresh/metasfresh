package org.adempiere.inout.util;

import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.Util.ArrayKey;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;

class StockDataQueriesLoadingCache
{
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	private final HashMap<ArrayKey, StockDataQuery> cache = new HashMap<>();

	public StockDataQuery toQuery(@NonNull final ShipmentScheduleQtyOnHandSegment segment)
	{
		// In case the DeliveryRule is Force, there is no point to load the storage, because it's not needed.
		// FIXME: make sure this works performance wise, then remove the commented code
		// final I_M_ShipmentSchedule shipmentSchedule = olAndSched.getSched();
		// final String deliveryRule = shipmentScheduleEffectiveValuesBL.getDeliveryRule(shipmentSchedule);
		// if (!X_M_ShipmentSchedule.DELIVERYRULE_Force.equals(deliveryRule))
		// return null;

		//
		// Get the storage query from cache if available

		return cache.computeIfAbsent(extractCacheKey(segment), k -> toQuery0(segment));
	}

	@NonNull
	private static ArrayKey extractCacheKey(final @NotNull ShipmentScheduleQtyOnHandSegment segment)
	{
		final TableRecordReference scheduleReference = segment.getSourceRef();
		return ArrayKey.of(
				scheduleReference.getTableName(),
				scheduleReference.getRecord_ID(),
				segment.getShipmentScheduleId().getRepoId());
	}

	@NonNull
	private StockDataQuery toQuery0(@NonNull final ShipmentScheduleQtyOnHandSegment segment)
	{
		final WarehouseId shipmentScheduleWarehouseId = segment.getWarehouseId();
		final Set<WarehouseId> warehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(shipmentScheduleWarehouseId);

		final ProductId productId = segment.getProductId();

		return StockDataQuery.builder()
				.warehouseIds(warehouseIds)
				.productId(productId)
				.build();
	}
}
