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

package de.metas.project.budget;

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
public class BudgetProjectResourceId implements RepoIdAware
{
	ProjectId projectId;
	int repoId;

	private BudgetProjectResourceId(
			@NonNull final ProjectId projectId,
			final int repoId)
	{
		this.projectId = projectId;
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Project_Resource_Budget_ID");
	}

	public static BudgetProjectResourceId ofRepoId(final int projectRepoId, final int repoId)
	{
		return new BudgetProjectResourceId(ProjectId.ofRepoId(projectRepoId), repoId);
	}

	@Nullable
	public static BudgetProjectResourceId ofRepoIdOrNull(final int projectRepoId, final int repoId)
	{
		return projectRepoId > 0 && repoId > 0 ? ofRepoId(projectRepoId, repoId) : null;
	}

	public static BudgetProjectResourceId ofRepoIdObjects(final Object projectRepoIdObj, final Object repoIdObj)
	{
		try
		{
			return new BudgetProjectResourceId(
					ProjectId.ofRepoId(NumberUtils.asInt(projectRepoIdObj)),
					NumberUtils.asInt(repoIdObj));
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed converting `" + projectRepoIdObj + "` and `" + repoIdObj + "` to " + BudgetProjectResourceId.class.getSimpleName());
		}
	}

	public static int toRepoId(@Nullable final BudgetProjectResourceId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final BudgetProjectResourceId id1, @Nullable final BudgetProjectResourceId id2)
	{
		return Objects.equals(id1, id2);
	}
}
