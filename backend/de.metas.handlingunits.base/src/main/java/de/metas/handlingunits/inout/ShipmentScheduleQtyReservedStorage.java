/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.handlingunits.inout;

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.inout.util.IShipmentScheduleQtyOnHandStorage;
import org.adempiere.inout.util.ShipmentScheduleAvailableStockDetail;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class shall hold details of reserved stock that's ready to be shipped (= VHUs that are reserved for one or more OrderLine).
 */
public class ShipmentScheduleQtyReservedStorage implements IShipmentScheduleQtyOnHandStorage
{
	private final Map<Util.ArrayKey, StockDataQuery> cachedMaterialQueries = new HashMap<>();
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull final ImmutableList<ShipmentScheduleAvailableStockDetail> stockDetails;

	public ShipmentScheduleQtyReservedStorage(final @NonNull ImmutableList<ShipmentScheduleAvailableStockDetail> stockDetails)
	{
		this.stockDetails = stockDetails;
	}

	@Override
	public List<ShipmentScheduleAvailableStockDetail> getStockDetailsMatching(final @NonNull OlAndSched olAndSched)
	{
		return null;
	}

	@Override
	public StockDataQuery toQuery(final @NonNull I_M_ShipmentSchedule sched)
	{
		final TableRecordReference scheduleReference = TableRecordReference.ofReferenced(sched);
		final Util.ArrayKey materialQueryCacheKey = Util.ArrayKey.of(
				scheduleReference.getTableName(),
				scheduleReference.getRecord_ID(),
				I_M_ShipmentSchedule.Table_Name,
				sched.getM_ShipmentSchedule_ID());

		return cachedMaterialQueries.computeIfAbsent(materialQueryCacheKey, k -> toQuery0(sched));
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
