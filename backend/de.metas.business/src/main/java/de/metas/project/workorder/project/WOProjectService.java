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
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
import de.metas.project.workorder.calendar.WOProjectCalendarQuery;
import de.metas.project.workorder.calendar.WOProjectResourceCalendarQuery;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceRepository;
import de.metas.project.workorder.resource.WOProjectResources;
import de.metas.project.workorder.resource.WOProjectResourcesCollection;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectStepRepository;
import de.metas.project.workorder.step.WOProjectSteps;
import de.metas.util.Check;
import de.metas.util.InSetPredicate;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;

@Service
public class WOProjectService
{
	private final WOProjectRepository woProjectRepository;
	private final WOProjectResourceRepository woProjectResourceRepository;
	private final WOProjectStepRepository woProjectStepRepository;
	private final ProjectRepository projectRepository;

	public WOProjectService(
			final WOProjectRepository woProjectRepository,
			final WOProjectResourceRepository woProjectResourceRepository,
			final WOProjectStepRepository woProjectStepRepository,
			final ProjectRepository projectRepository)
	{
		this.woProjectRepository = woProjectRepository;
		this.woProjectResourceRepository = woProjectResourceRepository;
		this.woProjectStepRepository = woProjectStepRepository;
		this.projectRepository = projectRepository;
	}

	@NonNull
	public WOProject getById(@NonNull final ProjectId projectId)
	{
		return woProjectRepository.getById(projectId);
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

	public ImmutableSet<ResourceId> getResourceIdsByProjectResourceIds(@NonNull final Set<WOProjectResourceId> projectResourceIds)
	{
		return woProjectResourceRepository.getResourceIdsByProjectResourceIds(projectResourceIds);
	}

	public WOProjectSteps getStepsByProjectId(@NonNull final ProjectId projectId)
	{
		return woProjectStepRepository.getStepsByProjectId(projectId);
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

	public void updateWOProjectFromParent(@NonNull final I_C_Project project)
	{
		if (project.getC_Project_Parent_ID() <= 0)
		{
			return;
		}

		final I_C_Project parentProjectRecord = projectRepository.getRecordById(ProjectId.ofRepoId(project.getC_Project_Parent_ID()));
		Check.assumeNotNull(parentProjectRecord, "Parent record cannot be missing if an ID is provided!");

		final String logMessage = propagateValuesFromParent(project, parentProjectRecord).toString();

		if (Check.isNotBlank(logMessage)) // log this, particularly in case the change is done via API
		{
			Loggables.get().addLog("Set the following columns from C_Project_Parent_ID={}: {}", project.getC_Project_Parent_ID(), logMessage);
		}
	}

	@NonNull
	private StringBuilder propagateValuesFromParent(@NonNull final I_C_Project project, @NonNull final I_C_Project parentProjectRecord)
	{
		final StringBuilder message = new StringBuilder();
		if (project.getSalesRep_ID() <= 0)
		{
			project.setSalesRep_ID(parentProjectRecord.getSalesRep_ID());
			message.append(I_C_Project.COLUMNNAME_SalesRep_ID);
		}

		if (project.getSpecialist_Consultant_ID() <= 0)
		{
			project.setSpecialist_Consultant_ID(parentProjectRecord.getSpecialist_Consultant_ID());
			message.append(I_C_Project.COLUMNNAME_Specialist_Consultant_ID);
		}

		if (project.getC_BPartner_ID() <= 0)
		{
			project.setC_BPartner_ID(parentProjectRecord.getC_BPartner_ID());
			message.append(I_C_Project.COLUMNNAME_C_BPartner_ID);
		}

		project.setC_Project_Reference_Ext(parentProjectRecord.getC_Project_Reference_Ext());
		message.append(I_C_Project.COLUMNNAME_C_Project_Reference_Ext);

		project.setInternalPriority(parentProjectRecord.getInternalPriority());
		message.append(I_C_Project.COLUMNNAME_InternalPriority);

		project.setBPartnerDepartment(parentProjectRecord.getBPartnerDepartment());
		message.append(I_C_Project.COLUMNNAME_BPartnerDepartment);

		return message;
	}
}
