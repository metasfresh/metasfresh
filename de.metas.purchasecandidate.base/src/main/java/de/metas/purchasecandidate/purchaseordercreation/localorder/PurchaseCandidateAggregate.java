package de.metas.purchasecandidate.purchaseordercreation.localorder;

import java.util.ArrayList;

import org.adempiere.exceptions.AdempiereException;

import de.metas.purchasecandidate.PurchaseCandidate;
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

public class PurchaseCandidateAggregate
{
	public static PurchaseCandidateAggregate of(final PurchaseOrderAggregationKey aggregationKey)
	{
		return new PurchaseCandidateAggregate(aggregationKey);
	}

	private final PurchaseOrderAggregationKey aggregationKey;
	private final ArrayList<PurchaseCandidate> purchaseCandidates = new ArrayList<>();

	private PurchaseCandidateAggregate(@NonNull final PurchaseOrderAggregationKey aggregationKey)
	{
		this.aggregationKey = aggregationKey;
	}

	public void add(@NonNull final PurchaseCandidate purchaseCandidate)
	{
		final PurchaseOrderAggregationKey purchaseCandidateAggKey = PurchaseOrderAggregationKey.fromPurchaseCandidate(purchaseCandidate);
		if (!aggregationKey.equals(purchaseCandidateAggKey))
		{
			throw new AdempiereException("" + purchaseCandidate + " does not have the expected aggregation key: " + aggregationKey);
		}

		purchaseCandidates.add(purchaseCandidate);
	}
}
