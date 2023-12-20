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

package de.metas.project.workorder.stepresource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.project.ProjectId;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.workorder.WOProjectStepResource;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectRepository;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceRepository;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepRepository;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

import static de.metas.project.ProjectConstants.RESERVATION_PROJECT_TYPE;

@Service
public class WOProjectStepResourceService
{
	@NonNull
	private final WOProjectRepository woProjectRepository;

	@NonNull
	private final WOProjectStepRepository stepRepository;

	@NonNull
	private final WOProjectResourceRepository resourceRepository;

	@NonNull
	private final ProjectTypeRepository projectTypeRepository;

	public WOProjectStepResourceService(
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
	public ImmutableList<WOProjectStepResource> getUnresolvedStepResourcesForWOProject(@NonNull final ProjectId projectId)
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
	public Stream<WOProjectStepResource> streamUnresolvedByResourceIds(@NonNull final ImmutableSet<WOProjectResourceId> resourceIds)
	{
		return resourceRepository.streamForResourceIds(resourceIds)
				.filter(WOProjectResource::isNotFullyResolved)
				.map(this::buildWOProjectStepResource);
	}

	@NonNull
	private ImmutableList<WOProjectStepResource> getUnresolvedStepResourcesForProjectIds(@NonNull final ImmutableSet<ProjectId> projectIds)
	{
		return resourceRepository.streamUnresolvedForProjectIds(projectIds)
				.map(this::buildWOProjectStepResource)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImmutableSet<ProjectId> getReservationOrderIdsByParentProject(@NonNull final ProjectId parentProjectId)
	{
		final ProjectType reservationProjectType = projectTypeRepository.getByName(RESERVATION_PROJECT_TYPE)
				.orElseThrow(() -> new AdempiereException("ReservationOrder project type cannot be missing!"));

		return woProjectRepository.getByParentProjectAndProjectType(parentProjectId, reservationProjectType.getId());
	}

	@NonNull
	private WOProjectStepResource buildWOProjectStepResource(@NonNull final WOProjectResource resource)
	{
		final WOProjectStep woProjectStep = stepRepository.getById(resource.getWoProjectStepId());

		return WOProjectStepResource.builder()
				.stepId(woProjectStep.getWoProjectStepId())
				.resourceId(resource.getWoProjectResourceId())
				.projectId(resource.getProjectId())
				.stepName(woProjectStep.getName())
				.totalHours(resource.getDuration())
				.resolvedHours(resource.getResolvedHours())
				.build();
	}
}
