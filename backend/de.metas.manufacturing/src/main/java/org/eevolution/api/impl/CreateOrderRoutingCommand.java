package org.eevolution.api.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRouting.PPOrderRoutingBuilder;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityCode;
import org.eevolution.api.PPOrderRoutingActivityStatus;
import org.eevolution.exceptions.RoutingExpiredException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;

import de.metas.material.planning.WorkingTime;
import de.metas.material.planning.pporder.IPPRoutingRepository;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.material.planning.pporder.PPRouting;
import de.metas.material.planning.pporder.PPRoutingActivity;
import de.metas.material.planning.pporder.PPRoutingActivityId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

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

final class CreateOrderRoutingCommand
{
	private final PPRouting routing;
	private final PPOrderId ppOrderId;
	private final LocalDateTime dateStartSchedule;
	private final Quantity qtyOrdered;

	@Builder
	public CreateOrderRoutingCommand(
			@NonNull final PPRoutingId routingId,
			@NonNull final PPOrderId ppOrderId,
			//
			@NonNull final Quantity qtyOrdered,
			@NonNull final LocalDateTime dateStartSchedule)
	{
		final IPPRoutingRepository routingsRepo = Services.get(IPPRoutingRepository.class);
		routing = routingsRepo.getById(routingId);
		this.ppOrderId = ppOrderId;

		this.qtyOrdered = qtyOrdered;
		this.dateStartSchedule = dateStartSchedule;
	}

	public PPOrderRouting execute()
	{
		if (!routing.isValid())
		{
			throw new LiberoException("@NotValid@ " + routing.getCode());
		}
		if (!routing.isValidAtDate(dateStartSchedule))
		{
			throw new RoutingExpiredException(routing, dateStartSchedule);
		}

		//
		// Order Routing header
		final PPOrderRoutingBuilder orderRoutingBuilder = newPPOrderRouting();

		//
		// Order Routing Activities
		final ImmutableList.Builder<PPOrderRoutingActivity> orderActivities = ImmutableList.builder();
		for (final PPRoutingActivity activity : routing.getActivities())
		{
			if (!activity.isValidAtDate(dateStartSchedule))
			{
				continue;
			}

			final PPOrderRoutingActivity orderActivity = createPPOrderRoutingActivity(activity);
			orderActivities.add(orderActivity);
		}
		orderRoutingBuilder.activities(orderActivities.build());

		//
		// Set first activity
		{
			final PPOrderRoutingActivityCode firstActivityCode = PPOrderRoutingActivityCode.ofString(routing.getFirstActivity().getCode());
			orderRoutingBuilder.firstActivityCode(firstActivityCode);
		}

		//
		// Activity Transitions
		orderRoutingBuilder.codeToNextCodeMap(extractCodeToNextCodeMap(routing));

		return orderRoutingBuilder.build();
	}

	private static ImmutableSetMultimap<PPOrderRoutingActivityCode, PPOrderRoutingActivityCode> extractCodeToNextCodeMap(final PPRouting routing)
	{
		final ImmutableSetMultimap.Builder<PPOrderRoutingActivityCode, PPOrderRoutingActivityCode> codeToNextCode = ImmutableSetMultimap.builder();

		for (final PPRoutingActivity activity : routing.getActivities())
		{
			final PPOrderRoutingActivityCode activityCode = PPOrderRoutingActivityCode.ofString(activity.getCode());

			for (final PPRoutingActivityId nextActivityId : activity.getNextActivityIds())
			{
				final PPRoutingActivity nextActivity = routing.getActivityById(nextActivityId);
				final PPOrderRoutingActivityCode nextActivityCode = PPOrderRoutingActivityCode.ofString(nextActivity.getCode());

				codeToNextCode.put(activityCode, nextActivityCode);
			}
		}

		return codeToNextCode.build();
	}

	private PPOrderRoutingBuilder newPPOrderRouting()
	{
		return PPOrderRouting.builder()
				.ppOrderId(ppOrderId)
				.routingId(routing.getId())
				.durationUnit(routing.getDurationUnit())
				.qtyPerBatch(routing.getQtyPerBatch());
	}

	public PPOrderRoutingActivity createPPOrderRoutingActivity(final PPRoutingActivity activity)
	{
		final TemporalUnit durationUnit = activity.getDurationUnit();
		final Duration durationPerOneUnit = activity.getDurationPerOneUnit();
		final int unitsPerCycle = activity.getUnitsPerCycle();
		final Duration durationRequired = WorkingTime.builder()
				.durationPerOneUnit(durationPerOneUnit)
				.unitsPerCycle(unitsPerCycle)
				.qty(qtyOrdered)
				.activityTimeUnit(durationUnit)
				.build()
				.getDuration();

		final Quantity zero = qtyOrdered.toZero();

		return PPOrderRoutingActivity.builder()
				.id(null) // n/a
				.routingActivityId(activity.getId())
				.code(PPOrderRoutingActivityCode.ofString(activity.getCode()))
				//
				.subcontracting(activity.isSubcontracting())
				.subcontractingVendorId(activity.getSubcontractingVendorId())
				//
				.milestone(activity.isMilestone())
				//
				.resourceId(activity.getResourceId())
				//
				.status(PPOrderRoutingActivityStatus.NOT_STARTED)
				//
				// Standard values
				.durationUnit(durationUnit)
				.queuingTime(activity.getQueuingTime())
				.setupTime(activity.getSetupTime())
				.waitingTime(activity.getWaitingTime())
				.movingTime(activity.getMovingTime())
				.durationPerOneUnit(durationPerOneUnit)
				.unitsPerCycle(unitsPerCycle)
				//
				// Planned values
				.setupTimeRequired(activity.getSetupTime()) // TODO: shall be multiply it by number of batches?
				.durationRequired(durationRequired)
				.qtyRequired(qtyOrdered)
				//
				// Reported values
				.setupTimeReal(Duration.ZERO)
				.durationReal(Duration.ZERO)
				.qtyDelivered(zero)
				.qtyScrapped(zero)
				.qtyRejected(zero)
				//
				.activityTemplateId(activity.getActivityTemplateId())
				//
				.build();
	}
}
