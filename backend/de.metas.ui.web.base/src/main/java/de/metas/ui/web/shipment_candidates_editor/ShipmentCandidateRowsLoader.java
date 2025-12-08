package de.metas.ui.web.shipment_candidates_editor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class ShipmentCandidateRowsLoader
{
	private final IShipmentScheduleBL shipmentScheduleBL;
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL;
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final LookupDataSource salesOrdersLookup;
	private final LookupDataSource customersLookup;
	private final LookupDataSource warehousesLookup;
	private final LookupDataSource productsLookup;
	private final LookupDataSource asiLookup;
	private final LookupDataSource catchUOMsLookup;

	private final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;

	private Map<OrderAndLineId, I_C_OrderLine> salesOrderLines;

	@Builder
	private ShipmentCandidateRowsLoader(
			@NonNull final IShipmentScheduleBL shipmentScheduleBL,
			@NonNull final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL,
			@NonNull final LookupDataSource salesOrdersLookup,
			@NonNull final LookupDataSource customersLookup,
			@NonNull final LookupDataSource warehousesLookup,
			@NonNull final LookupDataSource productsLookup,
			@NonNull final LookupDataSource asiLookup,
			@NonNull final LookupDataSource catchUOMsLookup,
			//
			final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		Check.assumeNotEmpty(shipmentScheduleIds, "shipmentScheduleIds is not empty");

		this.shipmentScheduleBL = shipmentScheduleBL;
		this.shipmentScheduleEffectiveBL = shipmentScheduleEffectiveBL;
		this.salesOrdersLookup = salesOrdersLookup;
		this.customersLookup = customersLookup;
		this.warehousesLookup = warehousesLookup;
		this.productsLookup = productsLookup;
		this.asiLookup = asiLookup;
		this.catchUOMsLookup = catchUOMsLookup;
		this.shipmentScheduleIds = ImmutableSet.copyOf(shipmentScheduleIds);
	}

	public static class ShipmentCandidateRowsLoaderBuilder
	{
		public ShipmentCandidateRows load()
		{
			return build().load();
		}
	}

	public ShipmentCandidateRows load()
	{
		final Collection<I_M_ShipmentSchedule> records = shipmentScheduleBL
				.getByIdsOutOfTrx(shipmentScheduleIds, I_M_ShipmentSchedule.class)
				.values();

		final ImmutableSet<OrderAndLineId> salesOrderAndLineIds = extractSalesOrderAndLineId(records);
		this.salesOrderLines = ordersRepo.getOrderLinesByIds(salesOrderAndLineIds);

		final ImmutableList<ShipmentCandidateRow> rows = records
				.stream()
				.map(this::toShipmentCandidateRow)
				.sorted(Comparator.comparing(ShipmentCandidateRow::getSalesOrderDisplayNameOrEmpty)
						.thenComparing(ShipmentCandidateRow::getSalesOrderLineNo))
				.collect(ImmutableList.toImmutableList());

		return ShipmentCandidateRows.builder()
				.rows(rows)
				.build();
	}

	private static ImmutableSet<OrderAndLineId> extractSalesOrderAndLineId(@NonNull final Collection<I_M_ShipmentSchedule> records)
	{
		return records.stream()
				.map(record -> extractSalesOrderAndLineId(record))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable
	private static OrderAndLineId extractSalesOrderAndLineId(@NonNull final I_M_ShipmentSchedule record)
	{
		return OrderAndLineId.ofRepoIdsOrNull(record.getC_Order_ID(), record.getC_OrderLine_ID());
	}

	/**
	 * Please keep the calculation of
	 * - QtyEnteredTU,
	 * - QtyEntered <-> qtyToDeliverUserEntered
	 * in sync with the customer report `Docs_Sales_Order_BOM_Details`.
	 */
	private ShipmentCandidateRow toShipmentCandidateRow(@NonNull final I_M_ShipmentSchedule record)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID());
		final BigDecimal qtyOrdered = shipmentScheduleEffectiveBL.computeQtyOrdered(record);

		final boolean catchWeight = record.isCatchWeight();
		final BigDecimal qtyToDeliverCatchOverride = extractQtyToDeliverCatchOverride(record);

		final PackingInfo packingInfo = extractPackingInfo(record);
		final Quantity qtyCUsToDeliver = extractQtyCUToDeliver(record);
		final BigDecimal qtyToDeliverUserEntered = packingInfo.computeQtyUserEnteredByQtyCUs(qtyCUsToDeliver);

		return ShipmentCandidateRow.builder()
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.salesOrder(extractSalesOrder(record))
				.salesOrderLineNo(extractSalesOrderLineNo(record))
				.customer(extractCustomer(record))
				.warehouse(extractWarehouse(record))
				.product(extractProduct(record))
				.packingInfo(packingInfo)
				.preparationDate(extractPreparationTime(record))
				//
				.qtyOrdered(packingInfo.computeQtyUserEnteredByQtyCUs(qtyOrdered))
				//
				.qtyToDeliverUserEnteredInitial(qtyToDeliverUserEntered)
				.qtyToDeliverUserEntered(qtyToDeliverUserEntered)
				//
				.catchWeight(catchWeight)
				.qtyToDeliverCatchOverrideInitial(qtyToDeliverCatchOverride)
				.qtyToDeliverCatchOverride(qtyToDeliverCatchOverride)
				.catchUOM(extractCatchUOM(record))
				//
				.asiIdInitial(asiId)
				.asi(toLookupValue(asiId))
				//
				.processed(record.isProcessed())
				//
				.build();
	}

	private LookupValue extractSalesOrder(@NonNull final I_M_ShipmentSchedule record)
	{
		final OrderId salesOrderId = OrderId.ofRepoIdOrNull(record.getC_Order_ID());
		return salesOrderId != null
				? salesOrdersLookup.findById(salesOrderId)
				: null;
	}

	private LookupValue extractCustomer(@NonNull final I_M_ShipmentSchedule record)
	{
		final BPartnerId customerId = shipmentScheduleBL.getBPartnerId(record);
		return customersLookup.findById(customerId);
	}

	private LookupValue extractWarehouse(@NonNull final I_M_ShipmentSchedule record)
	{
		final WarehouseId warehouseId = shipmentScheduleBL.getWarehouseId(record);
		return warehousesLookup.findById(warehouseId);
	}

	private LookupValue extractProduct(@NonNull final I_M_ShipmentSchedule record)
	{
		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		return productsLookup.findById(productId);
	}

	@VisibleForTesting
	static PackingInfo extractPackingInfo(@NonNull final I_M_ShipmentSchedule record)
	{
		final BigDecimal qtyCUsPerTU = record.getQtyItemCapacity();
		if (qtyCUsPerTU == null || qtyCUsPerTU.signum() <= 0)
		{
			return PackingInfo.NONE;
		}
		else
		{
			return PackingInfo.builder()
					.qtyCUsPerTU(qtyCUsPerTU)
					.description(record.getPackDescription())
					.build();
		}
	}

	private LookupValue extractCatchUOM(@NonNull final I_M_ShipmentSchedule record)
	{
		final UomId catchUomId = UomId.ofRepoIdOrNull(record.getCatch_UOM_ID());
		return catchUomId != null
				? catchUOMsLookup.findById(catchUomId)
				: null;
	}

	private LookupValue toLookupValue(@NonNull final AttributeSetInstanceId asiId)
	{
		return asiId.isRegular()
				? asiLookup.findById(asiId)
				: IntegerLookupValue.of(asiId.getRepoId(), "");
	}

	private ZonedDateTime extractPreparationTime(@NonNull final I_M_ShipmentSchedule record)
	{
		return shipmentScheduleBL.getPreparationDate(record);
	}

	private Quantity extractQtyCUToDeliver(@NonNull final I_M_ShipmentSchedule record)
	{
		return shipmentScheduleBL.getQtyToDeliver(record);
	}

	private BigDecimal extractQtyToDeliverCatchOverride(@NonNull final I_M_ShipmentSchedule record)
	{
		return shipmentScheduleBL
				.getCatchQtyOverride(record)
				.map(qty -> qty.toBigDecimal())
				.orElse(null);
	}

	private int extractSalesOrderLineNo(final I_M_ShipmentSchedule record)
	{
		final OrderAndLineId salesOrderAndLineId = extractSalesOrderAndLineId(record);
		if (salesOrderAndLineId == null)
		{
			return 0;
		}

		final I_C_OrderLine salesOrderLine = salesOrderLines.get(salesOrderAndLineId);
		if (salesOrderLine == null)
		{
			return 0;
		}

		return salesOrderLine.getLine();
	}

}
