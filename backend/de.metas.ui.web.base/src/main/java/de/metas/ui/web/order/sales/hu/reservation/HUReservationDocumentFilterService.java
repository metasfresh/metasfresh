package de.metas.ui.web.order.sales.hu.reservation;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.picking.api.Packageable;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.handlingunits.filter.HUIdsFilterHelper;
import lombok.NonNull;
import org.springframework.stereotype.Service;

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
		final OrderLineId salesOrderLineId = salesOrderLine.getId();
		final HUReservationDocRef reservationDocRef = salesOrderLineId != null ? HUReservationDocRef.ofSalesOrderLineId(salesOrderLineId) : null;

		return huReservationService.prepareHUQuery()
				.warehouseId(salesOrderLine.getWarehouseId())
				.productId(salesOrderLine.getProductId())
				.bpartnerId(salesOrderLine.getBPartnerId())
				.asiId(salesOrderLine.getAsiId())
				.reservedToDocumentOrNotReservedAtAll(reservationDocRef)
				.build();
	}

	public DocumentFilter createDocumentFilterIgnoreAttributes(@NonNull final Packageable packageable)
	{
		final IHUQueryBuilder huQuery = createHUQueryIgnoreAttributes(packageable);

		return HUIdsFilterHelper.createFilter(huQuery);
	}

	private IHUQueryBuilder createHUQueryIgnoreAttributes(final Packageable packageable)
	{
		final OrderLineId salesOrderLineId = packageable.getSalesOrderLineIdOrNull();
		final HUReservationDocRef reservationDocRef = salesOrderLineId != null ? HUReservationDocRef.ofSalesOrderLineId(salesOrderLineId) : null;

		return huReservationService.prepareHUQuery()
				.warehouseId(packageable.getWarehouseId())
				.productId(packageable.getProductId())
				.bpartnerId(packageable.getCustomerId())
				.asiId(null) // ignore attributes
				.reservedToDocumentOrNotReservedAtAll(reservationDocRef)
				.build();
	}
}
