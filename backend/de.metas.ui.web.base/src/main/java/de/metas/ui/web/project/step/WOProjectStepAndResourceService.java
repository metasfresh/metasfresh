/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.project.step;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.project.ProjectId;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectRepository;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceRepository;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectStepRepository;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static de.metas.project.ProjectConstants.RESERVATION_PROJECT_TYPE;

@Service
public class WOProjectStepAndResourceService
{
	@NonNull
	private final WOProjectRepository woProjectRepository;

	@NonNull
	private final WOProjectStepRepository stepRepository;

	@NonNull
	private final WOProjectResourceRepository resourceRepository;

	@NonNull
	private final ProjectTypeRepository projectTypeRepository;

	public WOProjectStepAndResourceService(
			@NonNull final WOProjectRepository woProjectRepository,
			@NonNull final WOProjectStepRepository stepRepository,
			@NonNull final WOProjectResourceRepository resourceRepository,
			@NonNull final ProjectTypeRepository projectTypeRepository)
	{
		this.woProjectRepository = woProjectRepository;
		this.stepRepository = stepRepository;
		this.resourceRepository = resourceRepository;
		this.projectTypeRepository = projectTypeRepository;
	}

	@NonNull
	public ImmutableList<WOProjectStepAndResource> getUnresolvedStepResourcesForWOProject(@NonNull final ProjectId projectId)
	{
		final WOProject project = woProjectRepository.getById(projectId);

		final ProjectId parentProjectId = project.getProjectParentId();
		if (parentProjectId == null)
		{
			return ImmutableList.of();
		}

		final ProjectType projectType = projectTypeRepository.getById(project.getProjectTypeId());
		if (projectType.isReservation())
		{
			return getUnresolvedStepResourcesForProjectIds(ImmutableSet.of(projectId));
		}

		return getUnresolvedStepResourcesForProjectIds(getReservationOrderIdsByParentProject(parentProjectId));
	}

	@NonNull
	public Stream<WOProjectStepAndResource> streamUnresolvedByResourceIds(@NonNull final ImmutableSet<WOProjectResourceId> resourceIds)
	{
		return resourceRepository.streamForResourceIds(resourceIds)
				.collect(toWOProjectStepAndResources())
				.stream()
				.filter(WOProjectStepAndResource::isNotFullyResolved);
	}

	@NonNull
	private ImmutableList<WOProjectStepAndResource> getUnresolvedStepResourcesForProjectIds(@NonNull final ImmutableSet<ProjectId> projectIds)
	{
		return resourceRepository.streamForProjectIds(projectIds)
				.collect(toWOProjectStepAndResources())
				.stream()
				.filter(WOProjectStepAndResource::isNotFullyResolved)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImmutableSet<ProjectId> getReservationOrderIdsByParentProject(@NonNull final ProjectId parentProjectId)
	{
		final ProjectType reservationProjectType = projectTypeRepository.getByName(RESERVATION_PROJECT_TYPE)
				.orElseThrow(() -> new AdempiereException("ReservationOrder project type cannot be missing!"));

		return woProjectRepository.getByParentProjectAndProjectType(parentProjectId, reservationProjectType.getId());
	}

	private Collector<WOProjectResource, ?, ImmutableList<WOProjectStepAndResource>> toWOProjectStepAndResources()
	{
		return GuavaCollectors.collectUsingListAccumulator(this::toWOProjectStepAndResources);
	}

	private ImmutableList<WOProjectStepAndResource> toWOProjectStepAndResources(final List<WOProjectResource> resources)
	{
		if (resources.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableSet<WOProjectStepId> stepIds = resources.stream().map(WOProjectResource::getWoProjectStepId).collect(ImmutableSet.toImmutableSet());
		final ImmutableMap<WOProjectStepId, WOProjectStep> steps = Maps.uniqueIndex(stepRepository.getByIds(stepIds), WOProjectStep::getWoProjectStepId);

		return resources.stream()
				.map(resource -> WOProjectStepAndResource.of(steps.get(resource.getWoProjectStepId()), resource))
				.collect(ImmutableList.toImmutableList());
	}
}
