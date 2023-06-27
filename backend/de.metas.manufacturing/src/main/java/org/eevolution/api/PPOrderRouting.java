package org.eevolution.api;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.product.ProductId;
import de.metas.workflow.WFDurationUnit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;

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
	@NonNull PPOrderId ppOrderId;

	@NonNull PPRoutingId routingId;

	@NonNull WFDurationUnit durationUnit;
	@NonNull BigDecimal qtyPerBatch;

	//
	// Activities
	@Getter(AccessLevel.NONE)
	@NonNull ImmutableList<PPOrderRoutingActivity> activities;
	@Getter(AccessLevel.NONE)
	ImmutableMap<PPOrderRoutingActivityCode, PPOrderRoutingActivity> activitiesByCode;
	@Getter(AccessLevel.NONE)
	PPOrderRoutingActivityCode firstActivityCode;
	@Getter(AccessLevel.NONE)
	ImmutableSetMultimap<PPOrderRoutingActivityCode, PPOrderRoutingActivityCode> codeToNextCodeMap;
	ImmutableList<PPOrderRoutingProduct> products;

	@Builder(toBuilder = true)
	private PPOrderRouting(
			@NonNull final PPOrderId ppOrderId,
			@NonNull final PPRoutingId routingId,
			@NonNull final WFDurationUnit durationUnit,
			@Nullable final BigDecimal qtyPerBatch,
			@NonNull final PPOrderRoutingActivityCode firstActivityCode,
			@NonNull final ImmutableList<PPOrderRoutingActivity> activities,
			@NonNull final ImmutableList<PPOrderRoutingProduct> products,
			@NonNull final ImmutableSetMultimap<PPOrderRoutingActivityCode, PPOrderRoutingActivityCode> codeToNextCodeMap)
	{
		this.ppOrderId = ppOrderId;
		this.routingId = routingId;
		this.durationUnit = durationUnit;
		this.qtyPerBatch = qtyPerBatch != null ? qtyPerBatch : BigDecimal.ONE;
		this.firstActivityCode = firstActivityCode;
		this.activities = activities;
		this.activitiesByCode = Maps.uniqueIndex(activities, PPOrderRoutingActivity::getCode);
		this.codeToNextCodeMap = codeToNextCodeMap;
		this.products = products;
	}

	public PPOrderRouting copy()
	{
		return toBuilder()
				.activities(this.activities.stream()
						.map(PPOrderRoutingActivity::copy)
						.collect(ImmutableList.toImmutableList()))
				.products(this.products.stream()
						.map(PPOrderRoutingProduct::copy)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public static boolean equals(@Nullable PPOrderRouting o1, @Nullable PPOrderRouting o2)
	{
		return Objects.equals(o1, o2);
	}

	public ImmutableCollection<PPOrderRoutingActivity> getActivities()
	{
		return activitiesByCode.values();
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

	@Nullable
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

	@Nullable
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

	public ImmutableSet<ProductId> getProductIdsByActivityId(@NonNull final PPOrderRoutingActivityId activityId)
	{
		return getProducts()
				.stream()
				.filter(activityProduct -> activityProduct.getId() != null && PPOrderRoutingActivityId.equals(activityProduct.getId().getActivityId(), activityId))
				.map(PPOrderRoutingProduct::getProductId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public void voidIt()
	{
		getActivities().forEach(PPOrderRoutingActivity::voidIt);
	}

	public void reportProgress(final PPOrderActivityProcessReport report)
	{
		getActivityById(report.getActivityId()).reportProgress(report);
	}

	public void completeActivity(final PPOrderRoutingActivityId activityId)
	{
		getActivityById(activityId).completeIt();
	}

	public void closeActivity(final PPOrderRoutingActivityId activityId)
	{
		getActivityById(activityId).closeIt();
	}

	public void uncloseActivity(final PPOrderRoutingActivityId activityId)
	{
		getActivityById(activityId).uncloseIt();
	}

}
