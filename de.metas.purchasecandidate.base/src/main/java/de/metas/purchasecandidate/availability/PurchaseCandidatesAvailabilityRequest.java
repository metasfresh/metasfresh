package de.metas.purchasecandidate.availability;

import java.util.Collection;
import java.util.Map;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.google.common.collect.ImmutableMap;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.vendor.gateway.api.availability.TrackingId;
import lombok.Value;

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

@Value
public class PurchaseCandidatesAvailabilityRequest
{
	public static final PurchaseCandidatesAvailabilityRequest of(final Map<TrackingId, PurchaseCandidate> purchaseCandidates)
	{
		return new PurchaseCandidatesAvailabilityRequest(purchaseCandidates);
	}

	public static final PurchaseCandidatesAvailabilityRequest of(final Collection<PurchaseCandidate> purchaseCandidates)
	{
		return new PurchaseCandidatesAvailabilityRequest(purchaseCandidates.stream()
				.collect(GuavaCollectors.toImmutableMapByKey(purchaseCandidate -> TrackingId.random())));
	}

	ImmutableMap<TrackingId, PurchaseCandidate> purchaseCandidates;

	private PurchaseCandidatesAvailabilityRequest(final Map<TrackingId, PurchaseCandidate> purchaseCandidates)
	{
		Check.assumeNotEmpty(purchaseCandidates, "purchaseCandidates is not empty");

		this.purchaseCandidates = ImmutableMap.copyOf(purchaseCandidates);
	}

}
