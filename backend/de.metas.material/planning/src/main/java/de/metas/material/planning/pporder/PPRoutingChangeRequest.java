package de.metas.material.planning.pporder;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/*
 * #%L
 * metasfresh-material-planning
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

@Data
public class PPRoutingChangeRequest
{
	public static PPRoutingChangeRequest newInstance(@NonNull final PPRoutingId routingId)
	{
		return new PPRoutingChangeRequest(routingId);
	}

	private final PPRoutingId routingId;

	private Percent yield;

	private Duration queuingTime;
	private Duration setupTime;
	private Duration durationPerOneUnit;
	private Duration waitingTime;
	private Duration movingTime;

	@Setter(AccessLevel.NONE)
	private BigDecimal cost;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	Map<PPRoutingActivityId, PPRoutingActivityChangeRequest> activities = new HashMap<>();

	private PPRoutingChangeRequest(@NonNull final PPRoutingId routingId)
	{
		this.routingId = routingId;
	}

	public void addActivityCost(@NonNull final PPRoutingActivityId activityId, @NonNull final BigDecimal cost)
	{
		final PPRoutingActivityChangeRequest activityChangeRequest = activities.computeIfAbsent(activityId, PPRoutingActivityChangeRequest::newInstance);
		activityChangeRequest.addCost(cost);

		if (this.cost == null)
		{
			this.cost = cost;
		}
		else
		{
			this.cost = this.cost.add(cost);
		}
	}

	public BigDecimal getActivityCostOrNull(@NonNull final PPRoutingActivityId activityId)
	{
		final PPRoutingActivityChangeRequest activity = activities.get(activityId);
		if (activity == null)
		{
			return null;
		}

		return activity.getCost();

	}
}
