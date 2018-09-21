package de.metas.purchasecandidate.purchaseordercreation.localorder;

import java.util.Collection;
import java.util.List;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.util.collections.MapReduceAggregator;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

public final class PurchaseCandidateAggregator extends MapReduceAggregator<PurchaseCandidateAggregate, PurchaseCandidate>
{
	public static List<PurchaseCandidateAggregate> aggregate(@NonNull final Collection<PurchaseCandidate> candidates)
	{
		final PurchaseCandidateAggregator aggregator = new PurchaseCandidateAggregator();
		aggregator.collectClosedGroups();
		aggregator.addAll(candidates.iterator());
		aggregator.closeAllGroups();
		return aggregator.getClosedGroups();
	}

	private PurchaseCandidateAggregator()
	{
		setItemAggregationKeyBuilder(PurchaseCandidateAggregateKey::fromPurchaseCandidate);
		collectClosedGroups();
	}

	@Override
	protected PurchaseCandidateAggregate createGroup(final Object itemHashKey, final PurchaseCandidate item)
	{
		return PurchaseCandidateAggregate.of(PurchaseCandidateAggregateKey.cast(itemHashKey));
	}

	@Override
	protected void closeGroup(@NonNull final PurchaseCandidateAggregate group)
	{
		group.calculateAndSetQtyToDeliver();
	}

	@Override
	protected void addItemToGroup(final PurchaseCandidateAggregate group, final PurchaseCandidate item)
	{
		group.add(item);
	}
}
