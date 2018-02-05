package de.metas.purchasecandidate.purchaseordercreation;

import org.adempiere.util.collections.MapReduceAggregator;
import org.compiere.model.I_C_Order;

import de.metas.order.event.OrderUserNotifications;
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
 * Aggregates {@link PurchaseCandidate}s and creates completed purchase orders ({@link I_C_Order}).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PurchaseOrderFromCandidatesAggregator
		extends MapReduceAggregator<PurchaseOrderFromCandidatesFactory, PurchaseCandidate>
{
	public static final PurchaseOrderFromCandidatesAggregator newInstance()
	{
		return new PurchaseOrderFromCandidatesAggregator();
	}

	private final OrderUserNotifications userNotifications = OrderUserNotifications.newInstance();

	private PurchaseOrderFromCandidatesAggregator()
	{
		setItemAggregationKeyBuilder(PurchaseOrderAggregationKey::formPurchaseCandidate);
	}

	@Override
	protected PurchaseOrderFromCandidatesFactory createGroup(
			@NonNull final Object itemHashKey,
			final PurchaseCandidate item_NOTUSED)
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
	protected void addItemToGroup(
			@NonNull final PurchaseOrderFromCandidatesFactory orderFactory,
			@NonNull final PurchaseCandidate candidate)
	{
		orderFactory.addCandidate(candidate);
	}
}
