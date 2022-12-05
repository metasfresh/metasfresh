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
import java.util.Optional;

@Value
public class ResourceGroupId implements RepoIdAware
{
	@JsonCreator
	public static ResourceGroupId ofRepoId(final int repoId)
	{
		return new ResourceGroupId(repoId);
	}

	@Nullable
	public static ResourceGroupId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ResourceGroupId(repoId) : null;
	}

	public static Optional<ResourceGroupId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final ResourceGroupId ResourceGroupId)
	{
		return ResourceGroupId != null ? ResourceGroupId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final ResourceGroupId o1, @Nullable final ResourceGroupId o2)
	{
		return Objects.equals(o1, o2);
	}

	int repoId;

	private ResourceGroupId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "S_Resource_Group_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
