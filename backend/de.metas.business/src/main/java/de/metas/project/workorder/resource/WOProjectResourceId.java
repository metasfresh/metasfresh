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

package de.metas.project.workorder.resource;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.project.ProjectId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class WOProjectResourceId implements RepoIdAware
{
	public static WOProjectResourceId ofRepoId(@NonNull final ProjectId projectId, final int repoId)
	{
		return new WOProjectResourceId(projectId, repoId);
	}

	public static WOProjectResourceId ofRepoId(final int projectRepoId, final int repoId)
	{
		return new WOProjectResourceId(ProjectId.ofRepoId(projectRepoId), repoId);
	}

	public static WOProjectResourceId ofRepoIdObjects(final Object projectRepoIdObj, final Object repoIdObj)
	{
		try
		{
			return new WOProjectResourceId(
					ProjectId.ofRepoId(NumberUtils.asInt(projectRepoIdObj)),
					NumberUtils.asInt(repoIdObj));
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed converting `" + projectRepoIdObj + "` and `" + repoIdObj + "` to " + WOProjectResourceId.class.getSimpleName());
		}
	}

	@Nullable
	public static WOProjectResourceId ofRepoIdOrNull(
			@Nullable final ProjectId projectId,
			@Nullable final Integer repoId)
	{
		return projectId != null && repoId != null && repoId > 0 ? new WOProjectResourceId(projectId, repoId) : null;
	}

	public static int toRepoId(@Nullable final WOProjectResourceId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	ProjectId projectId;

	private WOProjectResourceId(
			@NonNull final ProjectId projectId,
			final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Project_WO_Resource_ID");
		this.projectId = projectId;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final WOProjectResourceId id1, @Nullable final WOProjectResourceId id2)
	{
		return Objects.equals(id1, id2);
	}
}
