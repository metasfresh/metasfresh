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

import de.metas.project.ProjectId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class WOProjectService
{
	private final WOProjectRepository woProjectRepository;
	private final WOProjectResourceRepository woProjectResourceRepository;

	public WOProjectService(
			final WOProjectRepository woProjectRepository,
			final WOProjectResourceRepository woProjectResourceRepository)
	{
		this.woProjectRepository = woProjectRepository;
		this.woProjectResourceRepository = woProjectResourceRepository;
	}

	public WOProject getById(@NonNull final ProjectId projectId)
	{
		return woProjectRepository.getById(projectId);
	}

	public List<WOProject> getAllActiveProjects()
	{
		return woProjectRepository.getAllActiveProjects();
	}

	public Map<ProjectId, WOProjectResources> getResourcesByProjectIds(@NonNull final Set<ProjectId> projectIds)
	{
		return woProjectResourceRepository.getByProjectIds(projectIds);
	}
}
