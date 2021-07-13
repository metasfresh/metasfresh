/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.project.model;

import de.metas.project.ProjectId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class ServiceRepairProjectCostCollectorId implements RepoIdAware
{
	public static ServiceRepairProjectCostCollectorId ofRepoId(final ProjectId projectId, final int repoId)
	{
		return new ServiceRepairProjectCostCollectorId(projectId, repoId);
	}

	public static ServiceRepairProjectCostCollectorId ofRepoId(final int projectRepoId, final int repoId)
	{
		return new ServiceRepairProjectCostCollectorId(ProjectId.ofRepoId(projectRepoId), repoId);
	}

	@Nullable
	public static ServiceRepairProjectCostCollectorId ofRepoIdOrNull(final int projectRepoId, final int repoId)
	{
		if (repoId <= 0)
		{
			return null;
		}

		final ProjectId projectId = ProjectId.ofRepoIdOrNull(projectRepoId);
		if (projectId == null)
		{
			return null;
		}

		return new ServiceRepairProjectCostCollectorId(projectId, repoId);
	}

	public static int toRepoId(@Nullable final ServiceRepairProjectCostCollectorId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	ProjectId projectId;
	int repoId;

	private ServiceRepairProjectCostCollectorId(@NonNull final ProjectId projectId, final int repoId)
	{
		this.projectId = projectId;
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Project_Repair_CostCollector_ID");
	}

	public static boolean equals(
			@Nullable final ServiceRepairProjectCostCollectorId id1,
			@Nullable final ServiceRepairProjectCostCollectorId id2)
	{
		return Objects.equals(id1, id2);
	}
}
