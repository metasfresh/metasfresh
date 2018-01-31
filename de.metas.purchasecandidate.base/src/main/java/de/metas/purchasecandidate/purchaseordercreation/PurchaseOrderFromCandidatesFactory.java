package de.metas.purchasecandidate.purchaseordercreation;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.IdentityHashMap;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_C_Order;
import de.metas.order.OrderFactory;
import de.metas.order.OrderLineBuilder;
import de.metas.order.event.OrderUserNotifications;
import de.metas.purchasecandidate.PurchaseCandidate;
import lombok.Builder;
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
	private final OrderFactory orderFactory;
	private final IdentityHashMap<PurchaseCandidate, OrderLineBuilder> purchaseCandidate2OrderLineBuilder = new IdentityHashMap<>();
	private final OrderUserNotifications userNotifications;

	private final VendorRequestFromCandidatesFactory vendorRequestFromCandidatesFactory;

	@Builder
	private PurchaseOrderFromCandidatesFactory(
			@NonNull final PurchaseOrderAggregationKey orderAggregationKey,
			@NonNull OrderUserNotifications userNotifications)
	{
		final int vendorBPartnerId = orderAggregationKey.getVendorBPartnerId();

		this.orderFactory = OrderFactory.newPurchaseOrder()
				.orgId(orderAggregationKey.getOrgId())
				.warehouseId(orderAggregationKey.getWarehouseId())
				.shipBPartner(vendorBPartnerId)
				.datePromised(orderAggregationKey.getDatePromised());

		this.vendorRequestFromCandidatesFactory = VendorRequestFromCandidatesFactory.createForVendorId(vendorBPartnerId);

		this.userNotifications = userNotifications;
	}

	public void addCandidate(final PurchaseCandidate candidate)
	{
		vendorRequestFromCandidatesFactory.addCandidate(candidate);

		final OrderLineBuilder orderLineBuilder = orderFactory.orderLineByProductAndUom(candidate.getProductId(), candidate.getUomId())
				.orElseGet(() -> orderFactory.newOrderLine()
						.productId(candidate.getProductId()));

		orderLineBuilder.addQty(candidate.getQtyToPurchase(), candidate.getUomId());

		purchaseCandidate2OrderLineBuilder.put(candidate, orderLineBuilder);
	}

	public void createAndComplete()
	{
		final I_C_Order order = orderFactory.createAndComplete();

		vendorRequestFromCandidatesFactory.createAndComplete(order.getC_Order_ID());

		purchaseCandidate2OrderLineBuilder.forEach(this::updatePurchaseCandidateFromOrderLineBuilder);

		final Set<Integer> userIdsToNotify = getUserIdsToNotify();
		if (!userIdsToNotify.isEmpty())
		{
			userNotifications.queueEventsUntilCurrentTrxCommit();
			userNotifications.notifyOrderCompleted(order, userIdsToNotify);
		}
	}

	private void updatePurchaseCandidateFromOrderLineBuilder(
			@NonNull final PurchaseCandidate candidate,
			@NonNull final OrderLineBuilder orderLineBuilder)
	{
		candidate.setPurchaseOrderLineIdAndMarkProcessed(orderLineBuilder.getCreatedOrderLineId());
	}

	private Set<Integer> getUserIdsToNotify()
	{
		return purchaseCandidate2OrderLineBuilder.keySet()
				.stream()
				.map(PurchaseCandidate::getSalesOrderId)
				.distinct()
				.map(salesOrderId -> load(salesOrderId, I_C_Order.class))
				.map(I_C_Order::getCreatedBy)
				.collect(ImmutableSet.toImmutableSet());
	}

}
