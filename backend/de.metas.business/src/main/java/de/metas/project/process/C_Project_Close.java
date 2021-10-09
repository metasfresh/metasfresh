/*
 * #%L
 * de.metas.business
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

package de.metas.project.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectService;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class C_Project_Close extends JavaProcess implements IProcessPrecondition
{
	private final ProjectService projectService = SpringContextHolder.instance.getBean(ProjectService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a single selection");
		}

		final ProjectId projectId = ProjectId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (projectId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no project selected");
		}

		if (projectService.isClosed(projectId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("already closed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ProjectId projectId = ProjectId.ofRepoId(getRecord_ID());
		projectService.closeProject(projectId);
		return MSG_OK;
	}
}
