package de.metas.ui.web.order.sales.hu.reservation;

import org.springframework.stereotype.Service;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.reservation.HUReservationService;
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
	private final HUReservationService huReservationService;

	public HUReservationDocumentFilterService(
			@NonNull final OrderLineRepository orderLineRepository,
			@NonNull final HUReservationService huReservationService)
	{
		this.orderLineRepository = orderLineRepository;
		this.huReservationService = huReservationService;
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
		return huReservationService.prepareHUQuery()
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
		return huReservationService.prepareHUQuery()
				.warehouseId(packageable.getWarehouseId())
				.productId(packageable.getProductId())
				.asiId(null) // ignore attributes
				.reservedToSalesOrderLineIdOrNotReservedAtAll(packageable.getSalesOrderLineIdOrNull())
				.build();
	}
}
