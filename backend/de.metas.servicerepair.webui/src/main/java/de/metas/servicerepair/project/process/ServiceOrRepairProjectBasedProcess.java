/*
 * #%L
 * de.metas.servicerepair.webui
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.servicerepair.project.process;

import com.google.common.collect.ImmutableSet;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.project.ProjectId;
import de.metas.servicerepair.project.model.ServiceRepairProjectInfo;
import de.metas.servicerepair.project.model.ServiceRepairProjectTask;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.servicerepair.project.service.ServiceRepairProjectService;
import de.metas.servicerepair.repository.model.I_C_Project_Repair_Task;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.List;

abstract class ServiceOrRepairProjectBasedProcess extends JavaProcess
{
	protected final ServiceRepairProjectService projectService = SpringContextHolder.instance.getBean(ServiceRepairProjectService.class);

	protected ProcessPreconditionsResolution checkIsServiceOrRepairProject(@NonNull final ProjectId projectId)
	{
		return projectService.isServiceOrRepair(projectId)
				? ProcessPreconditionsResolution.accept()
				: ProcessPreconditionsResolution.rejectWithInternalReason("not Service/Repair project");
	}

	protected ProjectId getProjectId()
	{
		return ProjectId.ofRepoId(getRecord_ID());
	}

	protected ServiceRepairProjectInfo getProject()
	{
		final ProjectId projectId = getProjectId();
		return projectService.getById(projectId);
	}

	protected ServiceRepairProjectTaskId getSingleSelectedTaskId()
	{
		return CollectionUtils.singleElement(getSelectedTaskIds());
	}

	protected ImmutableSet<ServiceRepairProjectTaskId> getSelectedTaskIds()
	{
		final ProjectId projectId = getProjectId();
		return getSelectedIncludedRecordIds(I_C_Project_Repair_Task.class)
				.stream()
				.map(taskRepoId -> ServiceRepairProjectTaskId.ofRepoId(projectId, taskRepoId))
				.collect(ImmutableSet.toImmutableSet());
	}

	protected ImmutableSet<ServiceRepairProjectTaskId> getSelectedSparePartsTaskIds(final @NonNull IProcessPreconditionsContext context)
	{
		final ImmutableSet<ServiceRepairProjectTaskId> taskIds = getSelectedTaskIds(context);
		return projectService.retainIdsOfTypeSpareParts(taskIds);
	}

	protected ImmutableSet<ServiceRepairProjectTaskId> getSelectedTaskIds(final @NonNull IProcessPreconditionsContext context)
	{
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (projectId == null)
		{
			return ImmutableSet.of();
		}

		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_C_Project_Repair_Task.Table_Name.equals(recordRef.getTableName()))
				.map(recordRef -> ServiceRepairProjectTaskId.ofRepoId(projectId, recordRef.getRecord_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	protected List<ServiceRepairProjectTask> getSelectedTasks(final @NonNull IProcessPreconditionsContext context)
	{
		return projectService.getTaskByIds(getSelectedTaskIds(context));
	}

	protected List<ServiceRepairProjectTask> getSelectedTasks()
	{
		return projectService.getTaskByIds(getSelectedTaskIds());
	}
}
