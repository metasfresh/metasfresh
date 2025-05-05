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

package de.metas.project.workorder.resource;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.util.lang.ExternalId;
import de.metas.workflow.WFDurationUnit;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Value
public class WOProjectResource
{
	@NonNull
	OrgId orgId;

	@NonNull
	WOProjectResourceId woProjectResourceId;

	@NonNull
	WOProjectStepId woProjectStepId;

	@Nullable
	CalendarDateRange dateRange;

	@NonNull
	ResourceIdAndType resourceIdAndType;

	@Nullable
	Boolean isActive;

	@NonNull
	Duration duration;

	@NonNull
	WFDurationUnit durationUnit;

	@Nullable
	ProjectId budgetProjectId;

	@Nullable
	BudgetProjectResourceId projectResourceBudgetId;

	@Nullable
	ExternalId externalId;

	@Nullable
	String testFacilityGroupName;

	@Nullable
	String description;

	@Nullable
	Duration resolvedHours;

	@Builder(toBuilder = true)
	private WOProjectResource(
			@NonNull final OrgId orgId,
			@NonNull final WOProjectResourceId woProjectResourceId,
			@NonNull final WOProjectStepId woProjectStepId,
			@Nullable final CalendarDateRange dateRange,
			@NonNull final ResourceIdAndType resourceIdAndType,
			@NonNull final Duration duration,
			@NonNull final WFDurationUnit durationUnit,
			final boolean isActive,
			@Nullable final ProjectId budgetProjectId,
			@Nullable final BudgetProjectResourceId projectResourceBudgetId,
			@Nullable final ExternalId externalId,
			@Nullable final String testFacilityGroupName,
			@Nullable final String description,
			@Nullable final Duration resolvedHours)
	{
		if (!ProjectId.equals(woProjectResourceId.getProjectId(), woProjectStepId.getProjectId()))
		{
			throw new AdempiereException("Project step and resource ID shall share the same projectId: " + woProjectStepId + ", " + woProjectResourceId);
		}

		this.orgId = orgId;
		this.woProjectResourceId = woProjectResourceId;
		this.woProjectStepId = woProjectStepId;
		this.dateRange = dateRange;
		this.resourceIdAndType = resourceIdAndType;
		this.duration = duration;
		this.durationUnit = durationUnit;
		this.isActive = isActive;
		this.budgetProjectId = budgetProjectId;
		this.projectResourceBudgetId = projectResourceBudgetId;
		this.externalId = externalId;
		this.testFacilityGroupName = testFacilityGroupName;
		this.description = description;
		this.resolvedHours = resolvedHours;
	}

	@NonNull
	public static CalendarDateRange computeDateRangeToEncloseAll(@NonNull final List<WOProjectResource> projectResources)
	{
		if (projectResources.isEmpty())
		{
			throw new AdempiereException("No project resources provided");
		}

		final ImmutableList<CalendarDateRange> dateRanges = projectResources.stream()
				.map(WOProjectResource::getDateRange)
				.filter(Objects::nonNull)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		return CalendarDateRange.span(dateRanges);
	}

	public ProjectId getProjectId()
	{
		return woProjectResourceId.getProjectId();
	}

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

	@NonNull
	public Duration getResolvedHours()
	{
		return resolvedHours == null ? Duration.ZERO : resolvedHours;
	}

	@NonNull
	public Duration getUnresolvedHours()
	{
		return getDuration().minus(getResolvedHours());
	}

	public boolean isAllDay()
	{
		return Optional.ofNullable(dateRange)
				.map(CalendarDateRange::isAllDay)
				.orElse(false);
	}

	public boolean isNotFullyResolved()
	{
		return getResolvedHours().isZero() || duration.minus(getResolvedHours()).compareTo(Duration.ZERO) > 0;
	}
}