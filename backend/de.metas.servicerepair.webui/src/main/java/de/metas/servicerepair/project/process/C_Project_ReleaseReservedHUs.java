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
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.project.ProjectId;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import lombok.NonNull;

public class C_Project_ReleaseReservedHUs
		extends ServiceOrRepairProjectBasedProcess
		implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (projectId == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final ImmutableSet<ServiceRepairProjectTaskId> taskIdsOfTypeSpareParts = getSelectedSparePartsTaskIds(context);
		if (taskIdsOfTypeSpareParts.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no spare parts tasks selected");
		}

		return checkIsServiceOrRepairProject(projectId);
	}

	@Override
	protected String doIt()
	{
		final ImmutableSet<ServiceRepairProjectTaskId> taskIds = getSelectedTaskIds();
		projectService.releaseCompanyOwnedReservedSpareParts(taskIds);
		return MSG_OK;
	}
}
