package de.metas.ui.web.order.sales.hu.reservation;

import java.util.List;

import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.order.OrderLineId;
import de.metas.printing.esb.base.util.Check;
import de.metas.purchasecandidate.SalesOrderLine;
import de.metas.purchasecandidate.SalesOrderLineRepository;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class HUReservationDocumentFilterService
{
	private final SalesOrderLineRepository salesOrderLineRepository;

	public HUReservationDocumentFilterService(@NonNull final SalesOrderLineRepository salesOrderLineRepository)
	{
		this.salesOrderLineRepository = salesOrderLineRepository;
	}

	public DocumentFilter createOrderLineDocumentFilter(@NonNull final OrderLineId orderLineId)
	{
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
		final IWarehouseAdvisor warehouseAdvisor = Services.get(IWarehouseAdvisor.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final SalesOrderLine salesOrderLine = salesOrderLineRepository.getById(orderLineId);

		final I_M_Warehouse //
		orderLineWarehouse = warehouseAdvisor.evaluateWarehouse(salesOrderLine.getId().getOrderLineId());
		Check.assumeNotNull(orderLineWarehouse,
				"For the current sales order line, there needs to be a warehouse; salesOrderLine={}", salesOrderLine);

		final List<WarehouseId> //
		warehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(WarehouseId.ofRepoId(orderLineWarehouse.getM_Warehouse_ID()));

		final ImmutableAttributeSet //
		attributeSet = ImmutableAttributeSet.ofAttributesetInstanceId(salesOrderLine.getAsiId());

		final IHUQueryBuilder huQuery = handlingUnitsDAO
				.createHUQueryBuilder()
				.addOnlyWithProductId(salesOrderLine.getProductId())
				.addOnlyInWarehouseIds(warehouseIds)
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.addOnlyWithAttributes(attributeSet)
				.setExcludeReservedToOtherThan(salesOrderLine.getId().getOrderLineId());

		return HUIdsFilterHelper.createFilter(huQuery);
	}

	public DocumentFilter createNoReservationDocumentFilter()
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final IHUQueryBuilder huQuery = handlingUnitsDAO
				.createHUQueryBuilder()
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.setExcludeReserved();

		return HUIdsFilterHelper.createFilter(huQuery);
	}
}
