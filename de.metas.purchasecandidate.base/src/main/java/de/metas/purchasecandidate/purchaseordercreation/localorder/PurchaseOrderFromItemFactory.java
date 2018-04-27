package de.metas.purchasecandidate.purchaseordercreation.localorder;

import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

// import de.metas.adempiere.model.I_C_Order;
import de.metas.order.OrderFactory;
import de.metas.order.OrderLineBuilder;
import de.metas.order.event.OrderUserNotifications;
import de.metas.order.event.OrderUserNotifications.ADMessageAndParams;
import de.metas.order.event.OrderUserNotifications.NotificationRequest;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
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
/* package */ final class PurchaseOrderFromItemFactory
{
	@VisibleForTesting
	final static String MSG_Different_DatePromised = //
			"de.metas.purchasecandidate.Event_PurchaseOrderCreated_Different_DatePromised";

	@VisibleForTesting
	final static String MSG_Different_Quantity = //
			"de.metas.purchasecandidate.Event_PurchaseOrderCreated_Different_Quantity";

	@VisibleForTesting
	final static String MSG_Different_Quantity_AND_DatePromised = //
			"de.metas.purchasecandidate.Event_PurchaseOrderCreated_Different_Quantity_And_DatePromised";

	private final OrderFactory orderFactory;
	private final IdentityHashMap<PurchaseOrderItem, OrderLineBuilder> purchaseItem2OrderLine = new IdentityHashMap<>();
	private final OrderUserNotifications userNotifications;

	@Builder
	private PurchaseOrderFromItemFactory(
			@NonNull final PurchaseOrderAggregationKey orderAggregationKey,
			@NonNull OrderUserNotifications userNotifications)
	{
		final int vendorBPartnerId = orderAggregationKey.getVendorBPartnerId();

		this.orderFactory = OrderFactory.newPurchaseOrder()
				.orgId(orderAggregationKey.getOrgId())
				.warehouseId(orderAggregationKey.getWarehouseId())
				.shipBPartner(vendorBPartnerId)
				.datePromised(orderAggregationKey.getDatePromised());

		this.userNotifications = userNotifications;
	}

	public void addCandidate(final PurchaseOrderItem pruchaseOrderItem)
	{
		final OrderLineBuilder orderLineBuilder = orderFactory
				.orderLineByProductAndUom(
						pruchaseOrderItem.getProductId(),
						pruchaseOrderItem.getUomId())
				.orElseGet(() -> orderFactory
						.newOrderLine()
						.productId(pruchaseOrderItem.getProductId()));

		orderLineBuilder.addQty(pruchaseOrderItem.getPurchasedQty(), pruchaseOrderItem.getUomId());

		purchaseItem2OrderLine.put(pruchaseOrderItem, orderLineBuilder);
	}

	public I_C_Order createAndComplete()
	{
		final I_C_Order order = orderFactory.createAndComplete();

		purchaseItem2OrderLine
				.forEach(this::updatePurchaseCandidateFromOrderLineBuilder);

		final Set<Integer> userIdsToNotify = getUserIdsToNotify();
		if (userIdsToNotify.isEmpty())
		{
			return order;
		}

		final ADMessageAndParams adMessageAndParams = createMessageAndParamsOrNull(order);

		final NotificationRequest request = NotificationRequest.builder()
				.order(order)
				.recipientUserIds(userIdsToNotify)
				.adMessageAndParams(adMessageAndParams)
				.build();

		userNotifications.notifyOrderCompleted(request);

		return order;
	}

	private void updatePurchaseCandidateFromOrderLineBuilder(
			@NonNull final PurchaseOrderItem pruchaseOrderItem,
			@NonNull final OrderLineBuilder orderLineBuilder)
	{
		pruchaseOrderItem
				.setPurchaseOrderLineIdAndMarkProcessed(orderLineBuilder.getCreatedOrderLineId());
	}

	private ADMessageAndParams createMessageAndParamsOrNull(@NonNull final I_C_Order order)
	{
		boolean deviatingDatePromised = false;
		boolean deviatingQuantity = false;
		for (final PurchaseOrderItem purchaseOrderItem : purchaseItem2OrderLine.keySet())
		{
			final Date dateRequired = purchaseOrderItem.getPurchaseCandidate().getDateRequired();

			if (!Objects.equals(dateRequired, order.getDatePromised()))
			{
				deviatingDatePromised = true;
			}
			if (!purchaseOrderItem.pruchaseMatchesRequiredQty())
			{
				deviatingQuantity = true;
			}
		}

		if (deviatingDatePromised && deviatingQuantity)
		{
			return ADMessageAndParams.builder()
					.adMessage(MSG_Different_Quantity_AND_DatePromised)
					.params(createCommonMessageParams(order))
					.build();
		}
		else if (deviatingQuantity)
		{
			return ADMessageAndParams.builder()
					.adMessage(MSG_Different_Quantity)
					.params(createCommonMessageParams(order))
					.build();
		}
		else if (deviatingDatePromised)
		{
			return ADMessageAndParams.builder()
					.adMessage(MSG_Different_DatePromised)
					.params(createCommonMessageParams(order))
					.build();
		}
		else
		{
			return null;
		}
	}

	private List<Object> createCommonMessageParams(@NonNull final I_C_Order order)
	{
		final I_C_BPartner bpartner = order.getC_BPartner();
		final String bpValue = bpartner.getValue();
		final String bpName = bpartner.getName();
		return ImmutableList.of(TableRecordReference.of(order), bpValue, bpName);
	}

	private Set<Integer> getUserIdsToNotify()
	{
		return purchaseItem2OrderLine.keySet().stream()
				.map(PurchaseOrderItem::getPurchaseCandidate)
				.map(PurchaseCandidate::getSalesOrderId)
				.distinct()
				.map(salesOrderId -> InterfaceWrapperHelper.load(salesOrderId, I_C_Order.class))
				.map(I_C_Order::getCreatedBy)
				.collect(ImmutableSet.toImmutableSet());
	}
}
