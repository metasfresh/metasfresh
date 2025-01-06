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
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.material.interceptor.transactionevent.HUDescriptorService;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.inout.util.IShipmentScheduleQtyOnHandStorage;
import org.adempiere.inout.util.ShipmentScheduleAvailableStockDetail;
import org.adempiere.warehouse.WarehouseId;

import java.util.Collections;
import java.util.List;

/**
 * This class shall hold details of external property VHUs that are reserved for an OrderLine and ready to be shipped.
 * These should never show up in {@code MD_Stock} and as such would never be picked up by the qtyonHand logic.
 */
public class ShipmentScheduleQtyExternalPropertyStorage implements IShipmentScheduleQtyOnHandStorage
{
	private final IUOMConversionBL uomConversionBL;
	private final IProductBL productBL;
	private final IHandlingUnitsBL handlingUnitsBL;

	private final HUReservationRepository huReservationRepository;
	private final HUDescriptorService huDescriptorService;

	public ShipmentScheduleQtyExternalPropertyStorage(@NonNull final HUReservationRepository huReservationRepository,
			@NonNull final HUDescriptorService huDescriptorService,
			@NonNull final IUOMConversionBL uomConversionBL,
			@NonNull final IProductBL productBL,
			@NonNull final IHandlingUnitsBL handlingUnitsBL)
	{
		this.huReservationRepository = huReservationRepository;
		this.huDescriptorService = huDescriptorService;
		this.uomConversionBL = uomConversionBL;
		this.productBL = productBL;
		this.handlingUnitsBL = handlingUnitsBL;
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
			return huReservationRepository.getByDocumentRef(HUReservationDocRef.ofSalesOrderLineId(orderLineId), ImmutableSet.of())
					.map(huRes -> toShipmentScheduleAvailableStockDetail(huRes, sched))
					.orElse(ImmutableList.of());
		}
	}

	private ImmutableList<ShipmentScheduleAvailableStockDetail> toShipmentScheduleAvailableStockDetail(@NonNull final HUReservation huRes, @NonNull final I_M_ShipmentSchedule sched)
	{
		return huRes.getVhuIds()
				.stream()
				.filter(this::isExternalPropertyVHU)
				.map(vhuId -> toShipmentScheduleAvailableStockDetail(vhuId, huRes, sched))
				.collect(ImmutableList.toImmutableList());
	}

	private boolean isExternalPropertyVHU(final HuId vhuId)
	{
		final ImmutableList<HUDescriptor> huDescriptors = huDescriptorService.createHuDescriptors(vhuId);
		Check.assumeEquals(huDescriptors.size(), 1, "VHU {} should be associated with exactly 1 M_HU_Storage", vhuId);
		final HUDescriptor huDescriptor = Iterables.getOnlyElement(huDescriptors);
		return huDescriptor.isExternalProperty();
	}

	private ShipmentScheduleAvailableStockDetail toShipmentScheduleAvailableStockDetail(@NonNull final HuId vhuId, final @NonNull HUReservation huRes, @NonNull final I_M_ShipmentSchedule sched)
	{
		final ProductId productId = ProductId.ofRepoId(sched.getM_Product_ID());
		final UomId uomId = productBL.getStockUOMId(productId);
		final WarehouseId warehouseId = handlingUnitsBL.getWarehouseIdForHuId(vhuId);

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

