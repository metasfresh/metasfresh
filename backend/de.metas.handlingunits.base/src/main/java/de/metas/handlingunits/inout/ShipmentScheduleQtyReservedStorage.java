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
import de.metas.cache.CCache;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderLineId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.inout.util.IShipmentScheduleQtyOnHandStorage;
import org.adempiere.inout.util.ShipmentScheduleAvailableStockDetail;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.Util;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * This class shall hold details of reserved stock that's ready to be shipped (= VHUs that are reserved for one or more OrderLine).
 */
public class ShipmentScheduleQtyReservedStorage implements IShipmentScheduleQtyOnHandStorage
{
	private final CCache<Util.ArrayKey, StockDataQuery> cachedMaterialQueries = CCache.newLRUCache("QtyReservedCachedMaterialQueries", 200, CCache.EXPIREMINUTES_Never);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final HUReservationRepository huReservationRepository;

	public ShipmentScheduleQtyReservedStorage(final HUReservationRepository huReservationRepository)
	{
		this.huReservationRepository = huReservationRepository;
	}

	@Override
	@NonNull
	public List<ShipmentScheduleAvailableStockDetail> getStockDetailsMatching(final @NonNull I_M_ShipmentSchedule sched)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(sched.getC_OrderLine_ID());

		if (orderLineId == null)
		{
			return Collections.emptyList();
		}
		else
		{
			// Reserved qty shall always be in VHUs, no need for picking BOM.
			return huReservationRepository.getByDocumentRef(HUReservationDocRef.ofSalesOrderLineId(orderLineId))
					.map(huRes -> toShipmentScheduleAvailableStockDetail(huRes, sched))
					.orElse(ImmutableList.of());
		}
	}

	@Override
	@NonNull
	public StockDataQuery toQuery(final @NonNull I_M_ShipmentSchedule sched)
	{
		final TableRecordReference scheduleReference = TableRecordReference.ofReferenced(sched);
		final Util.ArrayKey materialQueryCacheKey = Util.ArrayKey.of(
				scheduleReference.getTableName(),
				scheduleReference.getRecord_ID(),
				I_M_ShipmentSchedule.Table_Name,
				sched.getM_ShipmentSchedule_ID());

		return cachedMaterialQueries.getOrLoad(materialQueryCacheKey, k -> toQuery0(sched));
	}

	@NonNull
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

	private ImmutableList<ShipmentScheduleAvailableStockDetail> toShipmentScheduleAvailableStockDetail(@NonNull final HUReservation huRes, @NonNull final I_M_ShipmentSchedule sched)
	{
		return huRes.getVhuIds()
				.stream()
				.map(vhuId -> toShipmentScheduleAvailableStockDetail(vhuId, huRes, sched))
				.collect(ImmutableList.toImmutableList());
	}

	private ShipmentScheduleAvailableStockDetail toShipmentScheduleAvailableStockDetail(@NonNull final HuId vhuId, final @NonNull HUReservation huRes, @NonNull final I_M_ShipmentSchedule sched)
	{
		final ProductId productId = ProductId.ofRepoId(sched.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(productDAO.getById(productId).getC_UOM_ID());
		final WarehouseId warehouseId = handlingUnitsDAO.getWarehouseIdForHuId(vhuId);


		final Quantity reservedQtyByVhuId = huRes.getReservedQtyByVhuId(vhuId);
		final Quantity quantityInProductUom = uomConversionBL.convertQuantityTo(reservedQtyByVhuId, productId, uomId);
		return ShipmentScheduleAvailableStockDetail.builder()
				.productId(productId)
				.qtyOnHand(quantityInProductUom.toBigDecimal())
				.warehouseId(warehouseId)
				.storageAttributesKey(AttributesKey.ALL) // it's reserved, don't care about attributes at this point
				.build();

	}
}

