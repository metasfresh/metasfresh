package de.metas.ui.web.order.sales.hu.reservation;

import java.util.Set;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
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
	private final OrderLineRepository orderLineRepository;

	public HUReservationDocumentFilterService(@NonNull final OrderLineRepository orderLineRepository)
	{
		this.orderLineRepository = orderLineRepository;
	}

	public DocumentFilter createOrderLineDocumentFilter(@NonNull final OrderLineId orderLineId)
	{
		final OrderLine salesOrderLine = orderLineRepository.getById(orderLineId);
		return createOrderLineDocumentFilter(salesOrderLine);
	}

	private DocumentFilter createOrderLineDocumentFilter(@NonNull final OrderLine salesOrderLine)
	{
		final IHUQueryBuilder huQuery = createHUQuery(salesOrderLine);
		return HUIdsFilterHelper.createFilter(huQuery);
	}

	private IHUQueryBuilder createHUQuery(final OrderLine salesOrderLine)
	{
		final IWarehouseDAO warehouseRepo = Services.get(IWarehouseDAO.class);
		final IWarehouseAdvisor warehouseAdvisor = Services.get(IWarehouseAdvisor.class);
		final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

		final OrderLineId salesOrderLineId = salesOrderLine.getId();
		final WarehouseId orderLineWarehouseId = warehouseAdvisor.evaluateWarehouse(salesOrderLineId);
		Check.assumeNotNull(orderLineWarehouseId, "For the current sales order line, there needs to be a warehouse; salesOrderLine={}", salesOrderLine);

		final Set<WarehouseId> warehouseIds = warehouseRepo.getWarehouseIdsOfSamePickingGroup(orderLineWarehouseId);

		final ImmutableAttributeSet attributeSet = attributesRepo.getImmutableAttributeSetById(salesOrderLine.getAsiId());

		return handlingUnitsRepo
				.createHUQueryBuilder()
				.addOnlyWithProductId(salesOrderLine.getProductId())
				.addOnlyInWarehouseIds(warehouseIds)
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.setExcludeReservedToOtherThan(salesOrderLineId)
				.addOnlyWithAttributes(attributeSet);
	}

	public DocumentFilter createDocumentFilterIgnoreAttributes(@NonNull final Packageable packageable)
	{
		final IHUQueryBuilder huQuery = createHUQueryIgnoreAttributes(packageable);

		return HUIdsFilterHelper.createFilter(huQuery);
	}

	private IHUQueryBuilder createHUQueryIgnoreAttributes(final Packageable packageable)
	{
		final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
		final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);

		final Set<WarehouseId> warehouseIds = warehousesRepo.getWarehouseIdsOfSamePickingGroup(packageable.getWarehouseId());

		final IHUQueryBuilder huQuery = handlingUnitsRepo
				.createHUQueryBuilder()
				.addOnlyWithProductId(packageable.getProductId())
				.addOnlyInWarehouseIds(warehouseIds)
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active);

		if (packageable.getSalesOrderLineIdOrNull() == null)
		{
			huQuery.setExcludeReserved();
		}
		else
		{
			huQuery.setExcludeReservedToOtherThan(packageable.getSalesOrderLineIdOrNull());
		}
		return huQuery;
	}
}
