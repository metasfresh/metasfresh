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

package de.metas.project.workorder.project;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.budget.BudgetProject;
import de.metas.project.status.RStatusService;
import de.metas.project.workorder.calendar.WOProjectCalendarQuery;
import de.metas.project.workorder.calendar.WOProjectResourceCalendarQuery;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceRepository;
import de.metas.project.workorder.resource.WOProjectResources;
import de.metas.project.workorder.resource.WOProjectResourcesCollection;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectStepRepository;
import de.metas.project.workorder.step.WOProjectSteps;
import de.metas.project.workorder.step.WOProjectStepsCollection;
import de.metas.project.workorder.step.WOStepResources;
import de.metas.util.Check;
import de.metas.util.InSetPredicate;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static de.metas.project.ProjectConstants.DEFAULT_WO_CALENDAR_ENTRY_COLOR;
import static org.apache.commons.lang3.math.NumberUtils.min;

@Service
public class WOProjectService
{
	private final WOProjectRepository woProjectRepository;
	private final WOProjectResourceRepository woProjectResourceRepository;
	private final WOProjectStepRepository woProjectStepRepository;
	private final RStatusService statusService;
	private final ProjectTypeRepository projectTypeRepository;

	public WOProjectService(
			final WOProjectRepository woProjectRepository,
			final WOProjectResourceRepository woProjectResourceRepository,
			final WOProjectStepRepository woProjectStepRepository,
			final RStatusService statusService,
			final ProjectTypeRepository projectTypeRepository)
	{
		this.woProjectRepository = woProjectRepository;
		this.woProjectResourceRepository = woProjectResourceRepository;
		this.woProjectStepRepository = woProjectStepRepository;
		this.statusService = statusService;
		this.projectTypeRepository = projectTypeRepository;
	}

	@NonNull
	public WOProject getById(@NonNull final ProjectId projectId)
	{
		return woProjectRepository.getById(projectId);
	}

	@NonNull
	public List<WOProject> getAllActiveProjects()
	{
		return woProjectRepository.getAllActiveProjectsByProjectCalendarQuery(WOProjectCalendarQuery.ANY);
	}

	@NonNull
	public List<WOProject> getAllActiveProjects(@NonNull final Set<ProjectId> projectIds)
	{
		return woProjectRepository.getAllActiveProjectsByProjectCalendarQuery(WOProjectCalendarQuery.builder()
				.projectIds(InSetPredicate.only(projectIds))
				.build());
	}

	@NonNull
	public ImmutableSet<ProjectId> getActiveProjectIds(@NonNull final WOProjectCalendarQuery query)
	{
		return woProjectRepository.getActiveProjectIdsByProjectCalendarQuery(query);
	}

	@NonNull
	public ImmutableList<WOProjectResource> getResources(@NonNull final WOProjectResourceCalendarQuery query)
	{
		final InSetPredicate<WOProjectResourceId> projectResourceIds = woProjectResourceRepository.getProjectResourceIdsPredicate(query);
		if (projectResourceIds.isNone())
		{
			return ImmutableList.of();
		}

		return woProjectResourceRepository.getByIds(projectResourceIds);
	}

	public WOProjectResources getResourcesByProjectId(@NonNull final ProjectId projectId)
	{
		return woProjectResourceRepository.getByProjectId(projectId);
	}

	public WOProjectResourcesCollection getResourcesByProjectIds(@NonNull final Set<ProjectId> projectIds)
	{
		return woProjectResourceRepository.getByProjectIds(projectIds);
	}

	public ImmutableSet<ResourceIdAndType> getResourceIdsByProjectResourceIds(@NonNull final Set<WOProjectResourceId> projectResourceIds)
	{
		return woProjectResourceRepository.getResourceIdsByProjectResourceIds(projectResourceIds);
	}

	public WOProjectSteps getStepsByProjectId(@NonNull final ProjectId projectId)
	{
		return woProjectStepRepository.getStepsByProjectId(projectId);
	}

	public WOProjectStepsCollection getStepsByProjectIds(@NonNull final Set<ProjectId> projectIds)
	{
		return woProjectStepRepository.getByProjectIds(projectIds);
	}

	public ImmutableList<WOProjectStep> getStepsByIds(@NonNull final Set<WOProjectStepId> stepIds)
	{
		return woProjectStepRepository.getByIds(stepIds);
	}

	public void updateStepsDateRange(@NonNull final Set<WOProjectStepId> projectStepIds)
	{
		if (projectStepIds.isEmpty())
		{
			return;
		}

		final ImmutableSet<ProjectId> projectIds = projectStepIds.stream().map(WOProjectStepId::getProjectId).collect(ImmutableSet.toImmutableSet());
		final WOProjectResourcesCollection resourcesByProjectId = woProjectResourceRepository.getByProjectIds(projectIds);

		final HashMap<WOProjectStepId, CalendarDateRange> stepDateRanges = new HashMap<>();
		for (final WOProjectStepId projectStepId : projectStepIds)
		{
			final ImmutableList<CalendarDateRange> resourceDateRanges = resourcesByProjectId
					.getByProjectId(projectStepId.getProjectId())
					.streamByStepId(projectStepId)
					.map(WOProjectResource::getDateRange)
					.filter(Objects::nonNull)
					.distinct()
					.collect(ImmutableList.toImmutableList());

			if (!resourceDateRanges.isEmpty())
			{
				final CalendarDateRange stepDateRange = CalendarDateRange.span(resourceDateRanges);
				stepDateRanges.put(projectStepId, stepDateRange);
			}
		}

		woProjectStepRepository.updateStepDateRanges(stepDateRanges);
	}

	public void updateProjectResourcesByIds(
			@NonNull final Set<WOProjectResourceId> projectResourceIds,
			@NonNull final UnaryOperator<WOProjectResource> mapper)
	{
		woProjectResourceRepository.updateProjectResourcesByIds(projectResourceIds, mapper);
	}

	public void updateWOChildProjectsFromParent(@NonNull final BudgetProject budgetProject)
	{
		woProjectRepository.getByParentProjectId(budgetProject.getProjectId())
				.forEach(woProject -> syncWithParentAndUpdate(woProject, budgetProject));
	}

	public void syncWithParentAndUpdate(@NonNull final WOProject woProject, @NonNull final BudgetProject parentProject)
	{
		final WOProject.WOProjectBuilder projectToUpdateBuilder = woProject.toBuilder()
				.bpartnerDepartment(parentProject.getBpartnerDepartment())
				.salesRepId(parentProject.getSalesRepId())
				.specialistConsultantID(parentProject.getSpecialistConsultantID())
				.projectReferenceExt(parentProject.getProjectReferenceExt())
				.internalPriority(parentProject.getInternalPriority());

		final StringBuilder message = new StringBuilder()
				.append(I_C_Project.COLUMNNAME_SalesRep_ID)
				.append(I_C_Project.COLUMNNAME_Specialist_Consultant_ID)
				.append(I_C_Project.COLUMNNAME_C_Project_Reference_Ext)
				.append(I_C_Project.COLUMNNAME_InternalPriority)
				.append(I_C_Project.COLUMNNAME_BPartnerDepartment);

		if (woProject.getBPartnerId() == null)
		{
			projectToUpdateBuilder.bPartnerId(parentProject.getBPartnerId());
			message.append(I_C_Project.COLUMNNAME_C_BPartner_ID);
		}

		woProjectRepository.update(projectToUpdateBuilder.build());

		final String logMessage = message.toString();

		if (Check.isNotBlank(logMessage)) // log this, particularly in case the change is done via API
		{
			Loggables.get().addLog("Update WO_Project_ID = {} with the following columns from C_Project_Parent_ID={}: {}",
					woProject.getProjectId().getRepoId(), parentProject.getProjectId().getRepoId(), logMessage);
		}
	}

	@NonNull
	public String getCalendarColor(@NonNull final WOProject woProject)
	{
		return Optional.ofNullable(woProject.getStatusId())
				.map(statusService::getCalendarColorForStatus)
				.orElse(DEFAULT_WO_CALENDAR_ENTRY_COLOR);
	}

	@NonNull
	public Optional<ProjectId> getParentProjectId(@NonNull final ProjectId projectId)
	{
		return Optional.ofNullable(getById(projectId).getProjectParentId());
	}

	public boolean isReservationOrder(@NonNull final ProjectId projectId)
	{
		final WOProject project = getById(projectId);

		return projectTypeRepository.isReservationOrder(project.getProjectTypeId());
	}

	public boolean hasResourcesAssigned(@NonNull final ProjectId projectId)
	{
		return woProjectResourceRepository.existsResourcesForProject(projectId);
	}

	public void allocateHoursToResources(
			@NonNull final Integer hoursToResolve,
			@NonNull final Stream<WOProjectResourceId> resourceIdsStream)
	{
		resourceIdsStream.forEach(
				resourceId -> {
					final WOProjectResource woProjectResource = woProjectResourceRepository.getById(resourceId);

					final Duration toResolve = woProjectResource.getResolvedHours()
							.plusHours(min(woProjectResource.getUnresolvedHours().toHours(), hoursToResolve));

					woProjectResourceRepository.updateAll(ImmutableList.of(woProjectResource.toBuilder()
							.resolvedHours(toResolve)
							.build()));
				}
		);
	}

	public void resetAllocatedHours(@NonNull final Stream<WOProjectResourceId> resourceIdsStream)
	{
		resourceIdsStream.forEach(
				resourceId -> {
					final WOProjectResource woProjectResource = woProjectResourceRepository.getById(resourceId);

					woProjectResourceRepository.updateAll(ImmutableList.of(woProjectResource.toBuilder()
							.resolvedHours(Duration.ZERO)
							.build()));
				}
		);
	}

	@NonNull
	public WOStepResources getWOStepResources(@NonNull final WOProjectStepId woProjectStepId)
	{
		final ImmutableSet<WOProjectResourceId> resourceIds = woProjectResourceRepository.listByStepIds(ImmutableSet.of(woProjectStepId))
				.stream()
				.map(WOProjectResource::getWoProjectResourceId)
				.collect(ImmutableSet.toImmutableSet());

		return WOStepResources.builder()
				.resourceIds(resourceIds)
				.woProjectStepId(woProjectStepId)
				.build();
	}
}
