package de.metas.material.planning.pporder;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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

@EqualsAndHashCode
@ToString
public final class PPRoutingActivityChangeRequest
{
	public static PPRoutingActivityChangeRequest newInstance(@NonNull final PPRoutingActivityId activityId)
	{
		return new PPRoutingActivityChangeRequest(activityId);
	}

	@NonNull
	private final PPRoutingActivityId activityId;
	@Getter
	private BigDecimal cost;

	private PPRoutingActivityChangeRequest(@NonNull final PPRoutingActivityId activityId)
	{
		this.activityId = activityId;
	}

	public void addCost(@NonNull final BigDecimal cost)
	{
		if (this.cost == null)
		{
			this.cost = cost;
		}
		else
		{
			this.cost = this.cost.add(cost);
		}
	}
}
