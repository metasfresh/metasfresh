package de.metas.material.planning.pporder;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import de.metas.bpartner.BPartnerId;
import de.metas.product.ResourceId;
import de.metas.util.lang.Percent;
import de.metas.workflow.WFDurationUnit;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

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

@Value
@Builder(toBuilder = true)
public class PPRoutingActivity
{
	@NonNull PPRoutingActivityId id;

	@NonNull PPRoutingActivityType type;

	@NonNull String code;
	@NonNull String name;

	@NonNull @Default Range<Instant> validDates = Range.all();

	@NonNull ResourceId resourceId;

	@NonNull WFDurationUnit durationUnit;

	@NonNull Duration queuingTime;
	@NonNull Duration setupTime;
	@NonNull Duration waitingTime;
	@NonNull Duration movingTime;
	@NonNull Duration durationPerOneUnit;
	int overlapUnits;
	/**
	 * how many items can be manufactured on a production line in given duration unit.
	 */
	int unitsPerCycle;
	/**
	 * how many units are produced in a batch
	 */
	@NonNull BigDecimal qtyPerBatch;

	/**
	 * The Yield is the percentage of a lot that is expected to be of acceptable quality may fall below 100 percent
	 */
	@NonNull Percent yield;

	boolean subcontracting;
	BPartnerId subcontractingVendorId;

	boolean milestone;
	@NonNull PPAlwaysAvailableToUser alwaysAvailableToUser;
	@Nullable UserInstructions userInstructions;

	@NonNull @Default ImmutableSet<PPRoutingActivityId> nextActivityIds = ImmutableSet.of();

	@Nullable PPRoutingActivityTemplateId activityTemplateId;

	public boolean isValidAtDate(final Instant dateTime)
	{
		return validDates.contains(dateTime);
	}
}
