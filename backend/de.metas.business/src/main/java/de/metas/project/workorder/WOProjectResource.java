/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.project.workorder;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class WOProjectResource
{
	@NonNull WOProjectResourceId id;

	@NonNull WOProjectStepId stepId;

	@NonNull ResourceId resourceId;
	@NonNull CalendarDateRange dateRange;

	@NonNull TemporalUnit durationUnit;
	@NonNull Duration duration;

	@Nullable String description;

	public static CalendarDateRange computeDateRangeToEncloseAll(@NonNull List<WOProjectResource> projectResources)
	{
		if (projectResources.isEmpty())
		{
			throw new AdempiereException("No project resources provided");
		}

		final ImmutableList<CalendarDateRange> dateRanges = projectResources.stream()
				.map(WOProjectResource::getDateRange)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		return CalendarDateRange.span(dateRanges);
	}

	public ProjectId getProjectId() {return id.getProjectId();}
}
