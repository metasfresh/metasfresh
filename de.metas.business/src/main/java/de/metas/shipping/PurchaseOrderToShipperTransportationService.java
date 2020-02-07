package de.metas.shipping;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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
public class PurchaseOrderToShipperTransportationService
{
	private final PurchaseOrderToShipperTransportationRepository repo;

	public PurchaseOrderToShipperTransportationService(@NonNull final PurchaseOrderToShipperTransportationRepository repo)
	{
		this.repo = repo;
	}

	public void addPurchaseOrdersToShipperTransportation(@NonNull final ShipperTransportationId shipperTransportationId, @NonNull final IQueryFilter<I_C_Order> queryFilter)
	{
		final ImmutableList<OrderId> validPurchaseOrdersIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order.class)
				.addInArrayFilter(I_C_Order.COLUMNNAME_DocStatus, DocStatus.Completed, DocStatus.Closed)
				.filter(queryFilter)
				.create()
				.listIds(OrderId::ofRepoId)
				.stream()
				.filter(orderId -> repo.purchaseOrderNotInShipperTransportation(orderId))
				.collect(ImmutableList.toImmutableList());

		for (final OrderId purchaseOrderId : validPurchaseOrdersIds)
		{
			repo.addPurchaseOrderToShipperTransportation(purchaseOrderId, shipperTransportationId);
		}

	}

}
