/*
 * #%L
 * metasfresh-webui-api
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
import de.metas.project.ProjectAndLineId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.service.HUProjectService;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectLine;

abstract class ServiceOrRepairProjectBasedProcess extends JavaProcess
{
	protected final HUProjectService projectService = SpringContextHolder.instance.getBean(HUProjectService.class);

	protected ProcessPreconditionsResolution checkIsServiceOrRepairProject(@NonNull final ProjectId projectId)
	{
		final I_C_Project project = projectService.getById(projectId);
		final ProjectCategory projectCategory = ProjectCategory.ofNullableCodeOrGeneral(project.getProjectCategory());
		if (!projectCategory.isServiceOrRepair())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not Service/Repair project");
		}

		return ProcessPreconditionsResolution.accept();
	}

	protected ProjectId getProjectId()
	{
		return ProjectId.ofRepoId(getRecord_ID());
	}

	protected ProjectAndLineId getSingleSelectedProjectLineId()
	{
		return CollectionUtils.singleElement(getSelectedProjectLineIds());
	}

	protected ImmutableSet<ProjectAndLineId> getSelectedProjectLineIds()
	{
		final ProjectId projectId = getProjectId();
		return getSelectedIncludedRecordIds(I_C_ProjectLine.class)
				.stream()
				.map(projectLineRepoId -> ProjectAndLineId.ofRepoId(projectId, projectLineRepoId))
				.collect(ImmutableSet.toImmutableSet());
	}

	protected ImmutableSet<ProjectAndLineId> getSelectedProjectLineIds(final @NonNull IProcessPreconditionsContext context)
	{
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (projectId == null)
		{
			return ImmutableSet.of();
		}

		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_C_ProjectLine.Table_Name.equals(recordRef.getTableName()))
				.map(recordRef -> ProjectAndLineId.ofRepoId(projectId, recordRef.getRecord_ID()))
				.collect(ImmutableSet.toImmutableSet());

	}

}
