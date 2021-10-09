package de.metas.purchasecandidate.purchaseordercreation.localorder;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.document.DocTypeId;
import de.metas.order.event.OrderUserNotifications;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.util.collections.MapReduceAggregator;
import lombok.NonNull;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
 * Aggregates {@link PurchaseOrderItem}s and creates completed purchase orders ({@link I_C_Order}).
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class PurchaseOrderFromItemsAggregator
		extends MapReduceAggregator<PurchaseOrderFromItemFactory, PurchaseOrderItem>
{
	public static PurchaseOrderFromItemsAggregator newInstance()
	{
		return new PurchaseOrderFromItemsAggregator();
	}

	public static PurchaseOrderFromItemsAggregator newInstance(@Nullable final DocTypeId docType)
	{
		return new PurchaseOrderFromItemsAggregator(docType);
	}

	private final OrderUserNotifications userNotifications = OrderUserNotifications.newInstance();
	private final DocTypeId docType;

	private final List<I_C_Order> createdPurchaseOrders = new ArrayList<>();

	@VisibleForTesting
	PurchaseOrderFromItemsAggregator()
	{
		this(null);
	}

	@VisibleForTesting
	PurchaseOrderFromItemsAggregator(@Nullable final DocTypeId docType)
	{
		setItemAggregationKeyBuilder(PurchaseOrderAggregationKey::fromPurchaseOrderItem);
		this.docType = docType;
	}

	@Override
	protected PurchaseOrderFromItemFactory createGroup(
			@NonNull final Object itemHashKey,
			final PurchaseOrderItem item_NOTUSED)
	{
		final PurchaseOrderAggregationKey orderAggregationKey = PurchaseOrderAggregationKey.cast(itemHashKey);

		return PurchaseOrderFromItemFactory.builder()
				.orderAggregationKey(orderAggregationKey)
				.userNotifications(userNotifications)
				.docType(docType)
				.build();
	}

	@Override
	protected void closeGroup(@NonNull final PurchaseOrderFromItemFactory orderFactory)
	{
		final I_C_Order newPurchaseOrder = orderFactory.createAndComplete();
		createdPurchaseOrders.add(newPurchaseOrder);
	}

	@Override
	protected void addItemToGroup(
			@NonNull final PurchaseOrderFromItemFactory orderFactory,
			@NonNull final PurchaseOrderItem candidate)
	{
		orderFactory.addCandidate(candidate);
	}

	@VisibleForTesting
	List<I_C_Order> getCreatedPurchaseOrders()
	{
		return ImmutableList.copyOf(createdPurchaseOrders);
	}
}
