/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.project.service;

import de.metas.project.ProjectId;
import de.metas.project.ProjectLine;
import lombok.NonNull;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectLine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HUProjectService
{
	private final ProjectService projectService;

	public HUProjectService(
			@NonNull final ProjectService projectService)
	{
		this.projectService = projectService;
	}

	public I_C_Project getById(@NonNull final ProjectId id)
	{
		return projectService.getById(id);
	}

	public List<ProjectLine> getLines(@NonNull final ProjectId projectId)
	{
		return projectService.getLines(projectId);
	}

	public void createProjectIssue(@NonNull final ProjectIssueRequest request)
	{
		ProjectIssueCommand.builder()
				.projectService(projectService)
				.request(request)
				.build()
				.execute();
	}
}
