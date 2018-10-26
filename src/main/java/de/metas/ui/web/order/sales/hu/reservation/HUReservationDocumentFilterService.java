package de.metas.ui.web.order.sales.hu.reservation;

import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.product.ProductId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper;
import de.metas.util.Services;
import lombok.Builder;
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

	private static final String SYSCONFIG_AllowSqlWhenFilteringHUAttributes = "de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.AllowSqlWhenFilteringHUAttributes";

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

	private IHUQueryBuilder createHUQuery(@NonNull final OrderLine salesOrderLine)
	{
		return prepareHUQuery()
				.warehouseId(salesOrderLine.getWarehouseId())
				.productId(salesOrderLine.getProductId())
				.asiId(salesOrderLine.getAsiId())
				.reservedToSalesOrderLineIdOrNotReservedAtAll(salesOrderLine.getId())
				.build();
	}

	public DocumentFilter createDocumentFilterIgnoreAttributes(@NonNull final Packageable packageable)
	{
		final IHUQueryBuilder huQuery = createHUQueryIgnoreAttributes(packageable);

		return HUIdsFilterHelper.createFilter(huQuery);
	}

	private IHUQueryBuilder createHUQueryIgnoreAttributes(final Packageable packageable)
	{
		return prepareHUQuery()
				.warehouseId(packageable.getWarehouseId())
				.productId(packageable.getProductId())
				.asiId(null) // ignore attributes
				.reservedToSalesOrderLineIdOrNotReservedAtAll(packageable.getSalesOrderLineIdOrNull())
				.build();
	}

	@Builder(builderMethodName = "prepareHUQuery", builderClassName = "ReservationHUQueryBuilder")
	private IHUQueryBuilder createHUQuery(
			@NonNull final WarehouseId warehouseId,
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId asiId,
			@Nullable final OrderLineId reservedToSalesOrderLineIdOrNotReservedAtAll)
	{
		final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);
		final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);

		final Set<WarehouseId> pickingWarehouseIds = warehousesRepo.getWarehouseIdsOfSamePickingGroup(warehouseId);

		final IHUQueryBuilder huQuery = handlingUnitsRepo
				.createHUQueryBuilder()
				.addOnlyInWarehouseIds(pickingWarehouseIds)
				.addOnlyWithProductId(productId)
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active);

		// ASI
		if (asiId != null)
		{
			final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
			final ImmutableAttributeSet attributeSet = attributesRepo.getImmutableAttributeSetById(asiId);
			huQuery.addOnlyWithAttributes(attributeSet);
			huQuery.allowSqlWhenFilteringAttributes(isAllowSqlWhenFilteringHUAttributes());
		}

		// Reservation
		if (reservedToSalesOrderLineIdOrNotReservedAtAll == null)
		{
			huQuery.setExcludeReserved();
		}
		else
		{
			huQuery.setExcludeReservedToOtherThan(reservedToSalesOrderLineIdOrNotReservedAtAll);
		}

		return huQuery;
	}

	private boolean isAllowSqlWhenFilteringHUAttributes()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_AllowSqlWhenFilteringHUAttributes, true);
	}
}
