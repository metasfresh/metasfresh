package org.adempiere.inout.util;

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.Util.ArrayKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class StockDataQueriesLoadingCache
{
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	private final Map<ArrayKey, StockDataQuery> cache = new HashMap<>();

	public StockDataQuery toQuery(@NonNull final I_M_ShipmentSchedule sched)
	{
		// In case the DeliveryRule is Force, there is no point to load the storage, because it's not needed.
		// FIXME: make sure this works performance wise, then remove the commented code
		// final I_M_ShipmentSchedule shipmentSchedule = olAndSched.getSched();
		// final String deliveryRule = shipmentScheduleEffectiveValuesBL.getDeliveryRule(shipmentSchedule);
		// if (!X_M_ShipmentSchedule.DELIVERYRULE_Force.equals(deliveryRule))
		// return null;

		//
		// Get the storage query from cache if available
		final TableRecordReference scheduleReference = TableRecordReference.ofReferenced(sched);
		final ArrayKey materialQueryCacheKey = ArrayKey.of(
				scheduleReference.getTableName(),
				scheduleReference.getRecord_ID(),
				I_M_ShipmentSchedule.Table_Name,
				sched.getM_ShipmentSchedule_ID());

		return cache.computeIfAbsent(materialQueryCacheKey, k -> toQuery0(sched));
	}

	private StockDataQuery toQuery0(@NonNull final I_M_ShipmentSchedule sched)
	{
		final WarehouseId shipmentScheduleWarehouseId = shipmentScheduleEffectiveBL.getWarehouseId(sched);
		final Set<WarehouseId> warehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(shipmentScheduleWarehouseId);

		final ProductId productId = ProductId.ofRepoId(sched.getM_Product_ID());

		return StockDataQuery.builder()
				.warehouseIds(warehouseIds)
				.productId(productId)
				.build();
	}
}
