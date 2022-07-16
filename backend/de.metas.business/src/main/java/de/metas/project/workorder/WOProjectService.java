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
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.project.ProjectId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

@Service
public class WOProjectService
{
	private final WOProjectRepository woProjectRepository;
	private final WOProjectResourceRepository woProjectResourceRepository;
	private final WOProjectStepRepository woProjectStepRepository;

	public WOProjectService(
			final WOProjectRepository woProjectRepository,
			final WOProjectResourceRepository woProjectResourceRepository, 
			final WOProjectStepRepository woProjectStepRepository)
	{
		this.woProjectRepository = woProjectRepository;
		this.woProjectResourceRepository = woProjectResourceRepository;
		this.woProjectStepRepository = woProjectStepRepository;
	}

	public WOProject getById(@NonNull final ProjectId projectId)
	{
		return woProjectRepository.getById(projectId);
	}

	public List<WOProject> getAllActiveProjects()
	{
		return woProjectRepository.getAllActiveProjects();
	}

	public WOProjectResourcesCollection getResourcesByProjectIds(@NonNull final Set<ProjectId> projectIds)
	{
		return woProjectResourceRepository.getByProjectIds(projectIds);
	}

	public WOProjectResources getResourcesByProjectId(@NonNull final ProjectId projectId)
	{
		return woProjectResourceRepository.getByProjectId(projectId);
	}

	public Map<ProjectId, WOProjectSteps> getStepsByProjectIds(@NonNull final Set<ProjectId> projectIds)
	{
		return woProjectStepRepository.getByProjectIds(projectIds);
	}

	public WOProjectSteps getStepsByProjectId(@NonNull final ProjectId projectId)
	{
		return woProjectStepRepository.getByProjectId(projectId);
	}

	public void updateStepsDateRange(@NonNull final Set<WOProjectAndStepId> projectAndStepIds)
	{
		if (projectAndStepIds.isEmpty())
		{
			return;
		}

		final ImmutableSet<ProjectId> projectIds = projectAndStepIds.stream().map(WOProjectAndStepId::getProjectId).collect(ImmutableSet.toImmutableSet());
		final WOProjectResourcesCollection resourcesByProjectId = woProjectResourceRepository.getByProjectIds(projectIds);

		final HashMap<WOProjectStepId, CalendarDateRange> stepDateRanges = new HashMap<>();
		for (final WOProjectAndStepId projectAndStepId : projectAndStepIds)
		{
			final ImmutableList<CalendarDateRange> resourceDateRanges = resourcesByProjectId
					.getByProjectId(projectAndStepId.getProjectId())
					.streamByStepId(projectAndStepId.getStepId())
					.map(WOProjectResource::getDateRange)
					.distinct()
					.collect(ImmutableList.toImmutableList());

			if (!resourceDateRanges.isEmpty())
			{
				final CalendarDateRange stepDateRange = CalendarDateRange.span(resourceDateRanges);
				stepDateRanges.put(projectAndStepId.getStepId(), stepDateRange);
			}
		}

		woProjectStepRepository.updateStepDateRanges(stepDateRanges);
	}

	public void updateProjectResourcesByIds(
			@NonNull final Set<WOProjectAndResourceId> projectAndResourceIds,
			@NonNull final UnaryOperator<WOProjectResource> mapper)
	{
		woProjectResourceRepository.updateProjectResourcesByIds(projectAndResourceIds, mapper);
	}
}
