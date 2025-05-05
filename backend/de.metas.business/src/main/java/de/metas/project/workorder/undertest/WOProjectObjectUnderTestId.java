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

package de.metas.project.workorder.undertest;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.project.ProjectId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_Project_WO_ObjectUnderTest;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class WOProjectObjectUnderTestId implements RepoIdAware
{
	public static WOProjectObjectUnderTestId ofRepoId(@NonNull final ProjectId projectId, final int repoId)
	{
		return new WOProjectObjectUnderTestId(projectId, repoId);
	}

	public static WOProjectObjectUnderTestId ofRepoId(final int projectRepoId, final int repoId)
	{
		return new WOProjectObjectUnderTestId(ProjectId.ofRepoId(projectRepoId), repoId);
	}

	@Nullable
	public static WOProjectObjectUnderTestId ofRepoIdOrNull(
			@Nullable final ProjectId projectId,
			@Nullable final Integer repoId)
	{
		return projectId != null && repoId != null && repoId > 0 ? ofRepoId(projectId, repoId) : null;
	}

	public static int toRepoId(@Nullable final WOProjectObjectUnderTestId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	@NonNull
	ProjectId projectId;

	private WOProjectObjectUnderTestId(@NonNull final ProjectId projectId, final int repoId)
	{
		this.projectId = projectId;
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_Project_WO_ObjectUnderTest.COLUMNNAME_C_Project_WO_ObjectUnderTest_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final WOProjectObjectUnderTestId id1, @Nullable final WOProjectObjectUnderTestId id2)
	{
		return Objects.equals(id1, id2);
	}
}
