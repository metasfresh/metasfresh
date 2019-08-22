package de.metas.ui.web.shipment_candidates_editor;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Set;

import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
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
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL;
	private final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
	private final IOrderDAO salesOrdersRepo = Services.get(IOrderDAO.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final IProductBL productsService = Services.get(IProductBL.class);

	@Builder
	private ShipmentCandidateRowsRepository(
			@NonNull final IShipmentScheduleBL shipmentScheduleBL)
	{
		this.shipmentScheduleBL = shipmentScheduleBL;
	}

	public ShipmentCandidateRows getByShipmentScheduleIds(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		Check.assumeNotEmpty(shipmentScheduleIds, "shipmentScheduleIds is not empty");

		final Collection<I_M_ShipmentSchedule> records = shipmentSchedulesRepo.getByIdsOutOfTrx(shipmentScheduleIds).values();
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
		return ShipmentCandidateRow.builder()
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.salesOrderDocumentNo(extractSalesOrderDocumentNo(record))
				.customer(extractCustomer(record))
				.warehouse(extractWarehouse(record))
				.product(extractProduct(record))
				.preparationDate(extractPreparationTime(record))
				.qtyToDeliver(extractQtyToDeliver(record))
				.build();
	}

	private String extractSalesOrderDocumentNo(@NonNull final I_M_ShipmentSchedule record)
	{
		final OrderId salesOrderId = OrderId.ofRepoIdOrNull(record.getC_Order_ID());
		if (salesOrderId == null)
		{
			return null;
		}

		final I_C_Order salesOrder = salesOrdersRepo.getById(salesOrderId);
		return salesOrder.getDocumentNo();
	}

	private LookupValue extractCustomer(@NonNull final I_M_ShipmentSchedule record)
	{
		final BPartnerId customerId = shipmentScheduleEffectiveBL.getBPartnerId(record);

		final ITranslatableString customerName = TranslatableStrings.anyLanguage(bpartnersRepo.getBPartnerNameById(customerId));
		return IntegerLookupValue.of(customerId, customerName);
	}

	private LookupValue extractWarehouse(@NonNull final I_M_ShipmentSchedule record)
	{
		final WarehouseId warehouseId = shipmentScheduleEffectiveBL.getWarehouseId(record);

		final ITranslatableString warehouseName = TranslatableStrings.anyLanguage(warehousesRepo.getWarehouseName(warehouseId));
		return IntegerLookupValue.of(warehouseId, warehouseName);
	}

	private LookupValue extractProduct(@NonNull final I_M_ShipmentSchedule record)
	{
		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());

		final ITranslatableString productName = TranslatableStrings.anyLanguage(productsService.getProductValueAndName(productId));
		return IntegerLookupValue.of(productId, productName);
	}

	private ZonedDateTime extractPreparationTime(@NonNull final I_M_ShipmentSchedule record)
	{
		return TimeUtil.asZonedDateTime(shipmentScheduleEffectiveBL.getPreparationDate(record));
	}

	private Quantity extractQtyToDeliver(@NonNull final I_M_ShipmentSchedule record)
	{
		return shipmentScheduleBL.getQtyToDeliver(record);
	}
}
