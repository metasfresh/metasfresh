package de.metas.ui.web.order.sales.hu.reservation;

import java.util.Set;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.order.OrderLineId;
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

		final Set<WarehouseId> //
		warehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(WarehouseId.ofRepoId(orderLineWarehouse.getM_Warehouse_ID()));

		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		final ImmutableAttributeSet attributeSet = attributesRepo.getImmutableAttributeSetById(salesOrderLine.getAsiId());

		final IHUQueryBuilder huQuery = handlingUnitsDAO
				.createHUQueryBuilder()
				.addOnlyWithProductId(salesOrderLine.getProductId())
				.addOnlyInWarehouseIds(warehouseIds)
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.setExcludeReservedToOtherThan(salesOrderLine.getId().getOrderLineId())
				.addOnlyWithAttributes(attributeSet);

		return HUIdsFilterHelper.createFilter(huQuery);
	}

	public DocumentFilter createDocumentFilterIgnoreAttributes(@NonNull final Packageable packageable)
	{
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final Set<WarehouseId> //
		warehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(packageable.getWarehouseId());

		final IHUQueryBuilder huQuery = handlingUnitsDAO
				.createHUQueryBuilder()
				.addOnlyWithProductId(packageable.getProductId())
				.addOnlyInWarehouseIds(warehouseIds)
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active);

		if (packageable.getOrderLineIdOrNull() == null)
		{
			huQuery.setExcludeReserved();
		}
		else
		{
			huQuery.setExcludeReservedToOtherThan(packageable.getOrderLineIdOrNull());
		}

		return HUIdsFilterHelper.createFilter(huQuery);
	}
}
