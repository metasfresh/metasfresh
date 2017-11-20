package de.metas.purchasecandidate.order;

import java.util.IdentityHashMap;

import de.metas.order.OrderFactory;
import de.metas.order.OrderLineBuilder;
import de.metas.purchasecandidate.PurchaseCandidate;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Creates one purchase order from given candidates.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */ final class PurchaseOrderFromCandidatesFactory
{
	public static PurchaseOrderFromCandidatesFactory newInstance(final PurchaseOrderAggregationKey orderAggregationKey)
	{
		return new PurchaseOrderFromCandidatesFactory(orderAggregationKey);
	}

	private final OrderFactory orderFactory;
	private final IdentityHashMap<PurchaseCandidate, OrderLineBuilder> purchaseCandidate2OrderLineBuilder = new IdentityHashMap<>();

	private PurchaseOrderFromCandidatesFactory(@NonNull final PurchaseOrderAggregationKey orderAggregationKey)
	{
		orderFactory = OrderFactory.newPurchaseOrder()
				.orgId(orderAggregationKey.getOrgId())
				.warehouseId(orderAggregationKey.getWarehouseId())
				.shipBPartner(orderAggregationKey.getVendorBPartnerId())
				.datePromised(orderAggregationKey.getDatePromised());
	}

	public void addCandidate(final PurchaseCandidate candidate)
	{
		final OrderLineBuilder orderLineBuilder = orderFactory.orderLineByProductAndUom(candidate.getProductId(), candidate.getUomId())
				.orElseGet(() -> orderFactory.newOrderLine()
						.productId(candidate.getProductId()));

		orderLineBuilder.addQty(candidate.getQtyRequired(), candidate.getUomId());

		purchaseCandidate2OrderLineBuilder.put(candidate, orderLineBuilder);
	}

	public void createAndComplete()
	{
		orderFactory.createAndComplete();

		purchaseCandidate2OrderLineBuilder.forEach(this::updatePurchaseCandidateFromOrderLineBuilder);
	}

	private void updatePurchaseCandidateFromOrderLineBuilder(final PurchaseCandidate candidate, final OrderLineBuilder orderLineBuilder)
	{
		candidate.setPurchaseOrderLineIdAndMarkProcessed(orderLineBuilder.getCreatedOrderLineId());
	}
}
