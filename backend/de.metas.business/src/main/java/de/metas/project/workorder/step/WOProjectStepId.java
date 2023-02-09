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

package de.metas.project.workorder.step;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.project.ProjectId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class WOProjectStepId implements RepoIdAware
{

	public static WOProjectStepId ofRepoId(@NonNull final ProjectId projectId, final int repoId)
	{
		return new WOProjectStepId(projectId, repoId);
	}

	public static WOProjectStepId ofRepoId(final int projectRepoId, final int repoId)
	{
		return new WOProjectStepId(ProjectId.ofRepoId(projectRepoId), repoId);
	}

	@Nullable
	public static WOProjectStepId ofRepoIdOrNull(
			@Nullable final ProjectId projectId,
			@Nullable final Integer repoId)
	{
		return projectId != null && repoId != null && repoId > 0 ? ofRepoId(projectId, repoId) : null;
	}

	public static int toRepoId(@Nullable final WOProjectStepId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	@NonNull
	ProjectId projectId;

	private WOProjectStepId(@NonNull final ProjectId projectId, final int repoId)
	{
		this.projectId = projectId;
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Project_WO_Step_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final WOProjectStepId id1, @Nullable final WOProjectStepId id2)
	{
		return Objects.equals(id1, id2);
	}
}
