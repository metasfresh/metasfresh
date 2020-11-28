/**
 *
 */
package de.metas.material.planning.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.ResourceType;
import de.metas.material.planning.RoutingService;
import de.metas.material.planning.WorkingTime;
import de.metas.material.planning.pporder.IPPRoutingRepository;
import de.metas.material.planning.pporder.PPRouting;
import de.metas.material.planning.pporder.PPRoutingActivity;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

/**
 * Default Routing Service Implementation
 *
 * @author Teo Sarca
 */
public class DefaultRoutingServiceImpl implements RoutingService
{
	@Override
	public WorkingTime estimateWorkingTimePerOneUnit(final PPRoutingActivity activity)
	{
		final I_C_UOM uomEach = Services.get(IUOMDAO.class).getEachUOM();
		return estimateWorkingTime(activity, Quantity.of(1, uomEach));
	}

	private WorkingTime estimateWorkingTime(@NonNull final PPRoutingActivity activity, @NonNull final Quantity qty)
	{
		return WorkingTime.builder()
				.durationPerOneUnit(activity.getDurationPerOneUnit())
				.unitsPerCycle(activity.getUnitsPerCycle())
				.qty(qty)
				.activityTimeUnit(activity.getDurationUnit())
				.build();
	}

	@Override
	public Duration calculateDuration(@NonNull final PPRoutingActivity activity)
	{
		final Duration setupTime = activity.getSetupTime();
		final Duration duration = estimateWorkingTimePerOneUnit(activity).getDuration();

		final BigDecimal qtyPerBatch = activity.getQtyPerBatch();
		final Duration setupTimePerBatch = Duration.ofNanos((long)(setupTime.toNanos() / qtyPerBatch.doubleValue()));
		return setupTimePerBatch.plus(duration);
	}

	@Override
	public int calculateDurationDays(final PPRoutingId routingId, final ResourceId plantId, final BigDecimal qty)
	{
		if (plantId == null)
		{
			return 0;
		}

		Duration durationTotal = Duration.ZERO;
		final PPRouting routing = Services.get(IPPRoutingRepository.class).getById(routingId);
		for (final PPRoutingActivity activity : routing.getActivities())
		{
			// Qty independent times:
			durationTotal = durationTotal
					.plus(activity.getQueuingTime())
					.plus(activity.getSetupTime())
					.plus(activity.getWaitingTime())
					.plus(activity.getMovingTime());

			// Get OverlapUnits - number of units that must be completed before they are moved the next activity
			int overlapUnits = qty.setScale(0, RoundingMode.UP).intValueExact();
			if (activity.getOverlapUnits() > 0 && activity.getOverlapUnits() < overlapUnits)
			{
				overlapUnits = activity.getOverlapUnits();
			}
			final Duration durationBeforeOverlap = activity.getDurationPerOneUnit().multipliedBy(overlapUnits);

			durationTotal = durationTotal.plus(durationBeforeOverlap);
		}

		//
		final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
		final ResourceType resourceType = resourceProductService.getResourceTypeByResourceId(plantId);
		final BigDecimal availableDayTimeInHours = BigDecimal.valueOf(resourceType.getTimeSlotInHours());
		final int availableDays = resourceType.getAvailableDaysPerWeek();

		// Weekly Factor
		final BigDecimal weeklyFactor = BigDecimal.valueOf(7).divide(BigDecimal.valueOf(availableDays), 8, RoundingMode.UP);

		return BigDecimal.valueOf(durationTotal.toHours())
				.multiply(weeklyFactor)
				.divide(availableDayTimeInHours, 0, RoundingMode.UP)
				.intValueExact();
	}

	@Override
	public Duration getResourceBaseValue(@NonNull final PPRoutingActivity activity)
	{
		return calculateDuration(activity);
	}
}
