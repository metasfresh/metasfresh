package de.metas.ui.web.shipment_candidates_editor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Set;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
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

final class ShipmentCandidateRowsRepository
{
	private final IShipmentScheduleBL shipmentScheduleBL;

	private final LookupDataSource salesOrdersLookup;
	private final LookupDataSource customersLookup;
	private final LookupDataSource warehousesLookup;
	private final LookupDataSource productsLookup;
	private final LookupDataSource asiLookup;
	private final LookupDataSource uomsLookup;
	private final LookupDataSource catchUOMsLookup;

	@Builder
	private ShipmentCandidateRowsRepository(
			@NonNull final IShipmentScheduleBL shipmentScheduleBL)
	{
		this.shipmentScheduleBL = shipmentScheduleBL;

		salesOrdersLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_Order.Table_Name);
		customersLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name);
		warehousesLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_M_Warehouse.Table_Name);
		productsLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_M_Product.Table_Name);
		asiLookup = LookupDataSourceFactory.instance.productAttributes();
		uomsLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_UOM.Table_Name);
		catchUOMsLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_UOM.Table_Name);
	}

	public ShipmentCandidateRows getByShipmentScheduleIds(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		Check.assumeNotEmpty(shipmentScheduleIds, "shipmentScheduleIds is not empty");

		final Collection<I_M_ShipmentSchedule> records = shipmentScheduleBL.getByIdsOutOfTrx(shipmentScheduleIds).values();
		final ImmutableList<ShipmentCandidateRow> rows = records
				.stream()
				.map(this::toShipmentCandidateRow)
				.collect(ImmutableList.toImmutableList());

		return ShipmentCandidateRows.builder()
				.rows(rows)
				.build();
	}

	private ShipmentCandidateRow toShipmentCandidateRow(@NonNull final I_M_ShipmentSchedule record)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final Quantity qtyToDeliverStockOverride = extractQtyToDeliver(record);
		final BigDecimal qtyToDeliverCatchOverride = extractQtyToDeliverCatchOverride(record);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID());
		final BigDecimal qtyOrdered = shipmentScheduleEffectiveBL.computeQtyOrdered(record);

		final boolean catchWeight = shipmentScheduleBL.isCatchWeight(record);

		return ShipmentCandidateRow.builder()
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.salesOrder(extractSalesOrder(record))
				.customer(extractCustomer(record))
				.warehouse(extractWarehouse(record))
				.product(extractProduct(record))
				.preparationDate(extractPreparationTime(record))
				//
				.qtyOrdered(qtyOrdered)
				.uom(extractStockUOM(record))
				//
				.qtyToDeliverStockInitial(qtyToDeliverStockOverride)
				.qtyToDeliverStockOverride(qtyToDeliverStockOverride.toBigDecimal())
				//
				.qtyToDeliverCatchOverrideInitial(qtyToDeliverCatchOverride)
				.qtyToDeliverCatchOverride(qtyToDeliverCatchOverride)
				.catchUOM(extractCatchUOM(record))
				//
				.asiIdInitial(asiId)
				.asi(toLookupValue(asiId))
				.catchWeight(catchWeight)
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

	private LookupValue extractStockUOM(@NonNull final I_M_ShipmentSchedule record)
	{
		final IProductBL productBL = Services.get(IProductBL.class);

		final int productId = record.getM_Product_ID();

		final UomId stockUOMId = productBL.getStockUOMId(productId);
		return stockUOMId != null
				? uomsLookup.findById(stockUOMId)
				: null;
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

	private Quantity extractQtyToDeliver(@NonNull final I_M_ShipmentSchedule record)
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

}
