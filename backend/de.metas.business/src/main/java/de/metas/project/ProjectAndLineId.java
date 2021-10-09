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

package de.metas.project;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class ProjectAndLineId
{
	public static ProjectAndLineId ofRepoId(final int projectRepoId, final int projectLineRepoId)
	{
		final ProjectId projectId = ProjectId.ofRepoId(projectRepoId);
		return new ProjectAndLineId(projectId, projectLineRepoId);
	}

	public static ProjectAndLineId ofRepoId(@NonNull final ProjectId projectId, final int projectLineRepoId)
	{
		return new ProjectAndLineId(projectId, projectLineRepoId);
	}


	@Nullable
	public static ProjectAndLineId ofRepoIdOrNull(final int projectRepoId, final int projectLineRepoId)
	{
		if (projectLineRepoId <= 0)
		{
			return null;
		}

		final ProjectId projectId = ProjectId.ofRepoIdOrNull(projectRepoId);
		if (projectId == null)
		{
			return null;
		}

		return new ProjectAndLineId(projectId, projectLineRepoId);
	}

	ProjectId projectId;
	int projectLineRepoId;

	private ProjectAndLineId(
			@NonNull final ProjectId projectId,
			final int projectLineRepoId)
	{
		Check.assumeGreaterThanZero(projectLineRepoId, "C_ProjectLine_ID");

		this.projectId = projectId;
		this.projectLineRepoId = projectLineRepoId;
	}

	public int getRepoId()
	{
		return getProjectLineRepoId();
	}

	public static int toRepoId(@Nullable final ProjectAndLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final ProjectAndLineId id1, @Nullable final ProjectAndLineId id2)
	{
		return Objects.equals(id1, id2);
	}
}
