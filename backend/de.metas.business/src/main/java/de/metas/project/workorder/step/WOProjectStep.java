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

package de.metas.project.workorder.step;

import de.metas.calendar.util.CalendarDateRange;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
public class WOProjectStep
{
	@NonNull
	WOProjectStepId woProjectStepId;

	@NonNull
	String name;

	@NonNull
	Integer seqNo;

	@NonNull
	OrgId orgId;

	@Nullable CalendarDateRange dateRange;

	@Nullable
	String description;

	@Nullable
	ExternalId externalId;

	@Nullable
	Instant woPartialReportDate;

	@Nullable
	Integer woPlannedResourceDurationHours;

	@Nullable
	Instant deliveryDate;

	@Nullable
	Instant woTargetStartDate;

	@Nullable
	Instant woTargetEndDate;

	@Nullable
	Integer woPlannedPersonDurationHours;

	@Nullable
	WOStepStatus woStepStatus;

	@Nullable
	Instant woFindingsReleasedDate;

	@Nullable
	Instant woFindingsCreatedDate;

	public ProjectId getProjectId() {return woProjectStepId.getProjectId();}

	@NonNull
	public Optional<Instant> getStartDate()
	{
		return Optional.ofNullable(dateRange)
				.map(CalendarDateRange::getStartDate);
	}

	@NonNull
	public Optional<Instant> getEndDate()
	{
		return Optional.ofNullable(dateRange)
				.map(CalendarDateRange::getEndDate);
	}
}