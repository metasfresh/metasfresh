package de.metas.purchasecandidate.availability;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.vendor.gateway.api.availability.TrackingId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
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
public class AvailabilityMultiResult
{
	public static AvailabilityMultiResult of(final List<AvailabilityResult> results)
	{
		if (results.isEmpty())
		{
			return EMPTY;
		}

		return new AvailabilityMultiResult(Multimaps.index(results, AvailabilityResult::getTrackingId));
	}

	public static AvailabilityMultiResult of(@NonNull final AvailabilityResult result)
	{
		return of(ImmutableList.of(result));
	}

	public static final AvailabilityMultiResult EMPTY = new AvailabilityMultiResult();

	@Getter(AccessLevel.NONE)
	private final ImmutableListMultimap<TrackingId, AvailabilityResult> results;

	private AvailabilityMultiResult(final ListMultimap<TrackingId, AvailabilityResult> results)
	{
		this.results = ImmutableListMultimap.copyOf(results);
	}

	private AvailabilityMultiResult()
	{
		results = ImmutableListMultimap.of();
	}

	public boolean isEmpty()
	{
		return results.isEmpty();
	}

	public AvailabilityMultiResult merge(@NonNull final AvailabilityMultiResult other)
	{
		if (isEmpty())
		{
			return other;
		}
		else if (other.isEmpty())
		{
			return this;
		}
		else
		{
			return new AvailabilityMultiResult(ImmutableListMultimap.<TrackingId, AvailabilityResult> builder()
					.putAll(results)
					.putAll(other.results)
					.build());
		}
	}

	public Set<TrackingId> getTrackingIds()
	{
		return results.keySet();
	}

	public List<AvailabilityResult> getByTrackingId(final TrackingId trackingId)
	{
		return results.get(trackingId);
	}
}
