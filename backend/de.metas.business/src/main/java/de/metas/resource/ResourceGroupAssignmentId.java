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

package de.metas.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class ResourceGroupAssignmentId implements RepoIdAware
{
	int repoId;

	private ResourceGroupAssignmentId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "S_Resource_Group_Assignment_ID");
	}

	@JsonCreator
	public static ResourceGroupAssignmentId ofRepoId(final int repoId)
	{
		return new ResourceGroupAssignmentId(repoId);
	}

	@Nullable
	public static ResourceGroupAssignmentId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ResourceGroupAssignmentId(repoId) : null;
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final ResourceGroupAssignmentId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	public String getAsString()
	{
		return String.valueOf(repoId);
	}

	public static boolean equals(@Nullable final ResourceGroupAssignmentId id1, @Nullable final ResourceGroupAssignmentId id2)
	{
		return Objects.equals(id1, id2);
	}
}
