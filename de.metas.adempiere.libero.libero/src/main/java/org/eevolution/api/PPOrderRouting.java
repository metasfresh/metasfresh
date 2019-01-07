package org.eevolution.api;

import java.math.BigDecimal;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;

import de.metas.material.planning.pporder.PPOrderId;
import de.metas.material.planning.pporder.PPRoutingId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
public class PPOrderRouting
{
	PPOrderId ppOrderId;

	PPRoutingId routingId;

	TemporalUnit durationUnit;
	BigDecimal qtyPerBatch;

	//
	// Activities
	@Getter(AccessLevel.NONE)
	ImmutableMap<PPOrderRoutingActivityCode, PPOrderRoutingActivity> activitiesByCode;
	@Getter(AccessLevel.NONE)
	PPOrderRoutingActivityCode firstActivityCode;
	@Getter(AccessLevel.NONE)
	private ImmutableSetMultimap<PPOrderRoutingActivityCode, PPOrderRoutingActivityCode> codeToNextCodeMap;

	@Builder
	private PPOrderRouting(
			@NonNull final PPOrderId ppOrderId,
			@NonNull final PPRoutingId routingId,
			@NonNull final TemporalUnit durationUnit,
			@Nullable final BigDecimal qtyPerBatch,
			@NonNull final PPOrderRoutingActivityCode firstActivityCode,
			@NonNull final ImmutableList<PPOrderRoutingActivity> activities,
			@NonNull final ImmutableSetMultimap<PPOrderRoutingActivityCode, PPOrderRoutingActivityCode> codeToNextCodeMap)
	{
		this.ppOrderId = ppOrderId;
		this.routingId = routingId;
		this.durationUnit = durationUnit;
		this.qtyPerBatch = qtyPerBatch != null ? qtyPerBatch : BigDecimal.ONE;
		this.firstActivityCode = firstActivityCode;
		activitiesByCode = Maps.uniqueIndex(activities, PPOrderRoutingActivity::getCode);
		this.codeToNextCodeMap = codeToNextCodeMap;
	}

	public ImmutableCollection<PPOrderRoutingActivity> getActivities()
	{
		return activitiesByCode.values();
	}

	public List<PPOrderRoutingActivity> getActivitiesInOrder()
	{
		// TODO: really order the activities
		return ImmutableList.copyOf(getActivities());
	}

	public boolean isSomethingProcessed()
	{
		return getActivities()
				.stream()
				.anyMatch(PPOrderRoutingActivity::isSomethingProcessed);
	}

	public PPOrderRoutingActivity getFirstActivity()
	{
		return getActivityByCode(firstActivityCode);
	}

	private PPOrderRoutingActivity getActivityByCode(@NonNull final PPOrderRoutingActivityCode code)
	{
		final PPOrderRoutingActivity activity = activitiesByCode.get(code);
		if (activity == null)
		{
			throw new AdempiereException("No activity found for " + code + " in " + this);
		}
		return activity;
	}

	public PPOrderRoutingActivity getActivityById(@NonNull final PPOrderRoutingActivityId activityId)
	{
		return getActivities()
				.stream()
				.filter(activity -> PPOrderRoutingActivityId.equals(activity.getId(), activityId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No activity found by " + activityId + " in " + this));
	}

	public boolean isFirstActivity(@NonNull final PPOrderRoutingActivity activity)
	{
		return Objects.equals(firstActivityCode, activity.getCode());
	}

	public PPOrderRoutingActivity getNextActivityOrNull(@NonNull final PPOrderRoutingActivity activity)
	{
		final ImmutableSet<PPOrderRoutingActivityCode> nextActivityCodes = getNextActivityCodes(activity);
		if (nextActivityCodes.isEmpty())
		{
			return null;
		}

		final PPOrderRoutingActivityCode nextActivityCode = nextActivityCodes.iterator().next();
		return getActivityByCode(nextActivityCode);
	}

	public ImmutableList<PPOrderRoutingActivity> getNextActivities(@NonNull final PPOrderRoutingActivity activity)
	{
		return getNextActivityCodes(activity)
				.stream()
				.map(this::getActivityByCode)
				.collect(ImmutableList.toImmutableList());
	}

	public PPOrderRoutingActivity getPreviousActivityOrNull(@NonNull final PPOrderRoutingActivity activity)
	{
		final ImmutableSet<PPOrderRoutingActivityCode> previousActivityCodes = getPreviousActivityCodes(activity);
		if (previousActivityCodes.isEmpty())
		{
			return null;
		}

		final PPOrderRoutingActivityCode previousActivityCode = previousActivityCodes.iterator().next();
		return getActivityByCode(previousActivityCode);
	}

	private ImmutableSet<PPOrderRoutingActivityCode> getNextActivityCodes(final PPOrderRoutingActivity activity)
	{
		return codeToNextCodeMap.get(activity.getCode());
	}

	private ImmutableSet<PPOrderRoutingActivityCode> getPreviousActivityCodes(final PPOrderRoutingActivity activity)
	{
		final ImmutableSetMultimap<PPOrderRoutingActivityCode, PPOrderRoutingActivityCode> codeToPreviousCodeMap = codeToNextCodeMap.inverse();
		return codeToPreviousCodeMap.get(activity.getCode());
	}

	public PPOrderRoutingActivity getLastActivity()
	{
		return getActivities()
				.stream()
				.filter(this::isFinalActivity)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No final activity found in " + this));
	}

	private boolean isFinalActivity(final PPOrderRoutingActivity activity)
	{
		return getNextActivityCodes(activity).isEmpty();
	}

	public void voidIt()
	{
		getActivities().forEach(PPOrderRoutingActivity::voidIt);
	}

	public void reportProgress(final PPOrderActivityProcessReport report)
	{
		getActivityById(report.getActivityId()).reportProgress(report);
	}

	public void closeActivity(final PPOrderRoutingActivityId activityId)
	{
		getActivityById(activityId).closeIt();
	}
}
