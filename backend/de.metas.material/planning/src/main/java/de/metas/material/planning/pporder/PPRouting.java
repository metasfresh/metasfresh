package de.metas.material.planning.pporder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import de.metas.user.UserId;
import de.metas.util.lang.Percent;
import de.metas.workflow.WFDurationUnit;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

@Builder
@Value
public class PPRouting
{
	@NonNull PPRoutingId id;
	@Default boolean valid = true;
	@NonNull @Default Range<LocalDate> validDates = Range.all();
	@NonNull String code;
	@NonNull WFDurationUnit durationUnit;
	@NonNull Duration duration;
	@NonNull @Default BigDecimal qtyPerBatch = BigDecimal.ONE;
	/** The Yield is the percentage of a lot that is expected to be of acceptable quality may fall below 100 percent */
	@NonNull @Default Percent yield = Percent.ONE_HUNDRED;
	@Nullable UserId userInChargeId;
	@NonNull PPRoutingActivityId firstActivityId;
	@NonNull @Default ImmutableList<PPRoutingActivity> activities = ImmutableList.of();

	public boolean isValidAtDate(final LocalDateTime dateTime)
	{
		return isValid()
				&& validDates.contains(dateTime.toLocalDate());
	}

	public PPRoutingActivity getActivityById(@NonNull final PPRoutingActivityId activityId)
	{
		return activities.stream()
				.filter(activity -> activityId.equals(activity.getId()))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No activity by " + activityId + " found in " + this));
	}

	public PPRoutingActivity getFirstActivity()
	{
		return getActivityById(getFirstActivityId());
	}
}
