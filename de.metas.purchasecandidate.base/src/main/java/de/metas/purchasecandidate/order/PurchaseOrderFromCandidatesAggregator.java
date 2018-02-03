package de.metas.purchasecandidate.order;

import org.adempiere.util.collections.MapReduceAggregator;
import org.compiere.model.I_C_Order;

import de.metas.order.event.OrderUserNotifications;
import de.metas.purchasecandidate.PurchaseCandidate;

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
 * Aggregates {@link PurchaseCandidate}s and creates completed purchase orders ({@link I_C_Order}).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PurchaseOrderFromCandidatesAggregator extends MapReduceAggregator<PurchaseOrderFromCandidatesFactory, PurchaseCandidate>
{
	public static final PurchaseOrderFromCandidatesAggregator newInstance()
	{
		return new PurchaseOrderFromCandidatesAggregator();
	}

	private final OrderUserNotifications userNotifications = OrderUserNotifications.newInstance();

	private PurchaseOrderFromCandidatesAggregator()
	{
		setItemAggregationKeyBuilder(this::createHeaderAggregationKey);
	}

	private final PurchaseOrderAggregationKey createHeaderAggregationKey(final PurchaseCandidate candidate)
	{
		return PurchaseOrderAggregationKey.builder()
				.orgId(candidate.getOrgId())
				.warehouseId(candidate.getWarehouseId())
				.vendorBPartnerId(candidate.getVendorProductInfo().getVendorBPartnerId())
				.datePromisedMillis(candidate.getDatePromised().getTime())
				.build();
	}

	@Override
	protected PurchaseOrderFromCandidatesFactory createGroup(final Object itemHashKey, final PurchaseCandidate item_NOTUSED)
	{
		final PurchaseOrderAggregationKey orderAggregationKey = PurchaseOrderAggregationKey.cast(itemHashKey);
		return PurchaseOrderFromCandidatesFactory.builder()
				.orderAggregationKey(orderAggregationKey)
				.userNotifications(userNotifications)
				.build();
	}

	@Override
	protected void closeGroup(final PurchaseOrderFromCandidatesFactory orderFactory)
	{
		orderFactory.createAndComplete();
	}

	@Override
	protected void addItemToGroup(final PurchaseOrderFromCandidatesFactory orderFactory, final PurchaseCandidate candidate)
	{
		orderFactory.addCandidate(candidate);
	}
}
