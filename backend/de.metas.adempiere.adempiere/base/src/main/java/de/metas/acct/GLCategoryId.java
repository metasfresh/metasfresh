/*
 * #%L
 * de.metas.acct.base
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

package de.metas.acct;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class GLCategoryId implements RepoIdAware
{
	public static final GLCategoryId NONE = new GLCategoryId(0);

	int repoId;

	@JsonCreator
	public static GLCategoryId ofRepoId(final int repoId)
	{
		return new GLCategoryId(repoId);
	}

	@NonNull
	public static GLCategoryId ofRepoIdOrNone(final int repoId)
	{
		return repoId > 0 ? new GLCategoryId(repoId) : NONE;
	}

	public static int toRepoId(@Nullable final GLCategoryId glCategoryId)
	{
		return glCategoryId != null ? glCategoryId.getRepoId() : -1;
	}

	@Nullable
	public static GLCategoryId ofRepoIdOrNull(final int repoId)
	{
		if (repoId < 0)
		{
			return null;
		}

		return ofRepoIdOrNone(repoId);
	}

	private GLCategoryId(final int repoId)
	{
		this.repoId = Check.assumeGreaterOrEqualToZero(repoId, "GL_Category_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
