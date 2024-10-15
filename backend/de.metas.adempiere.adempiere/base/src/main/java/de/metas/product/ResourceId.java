package de.metas.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * Resource (S_Resource_ID)
 */
@Value
public class ResourceId implements RepoIdAware
{
	public static final ResourceId NO_RESOURCE = new ResourceId(540011);

	@JsonCreator
	public static ResourceId ofRepoId(final int repoId)
	{
		if (repoId == NO_RESOURCE.repoId)
		{
			return NO_RESOURCE;
		}
		else
		{
			return new ResourceId(repoId);
		}
	}

	@Nullable
	public static ResourceId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<ResourceId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static ImmutableSet<ResourceId> ofRepoIds(@NonNull final Collection<Integer> repoIds)
	{
		if (repoIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		return repoIds.stream().map(ResourceId::ofRepoIdOrNull).filter(Objects::nonNull).collect(ImmutableSet.toImmutableSet());
	}

	public static int toRepoId(@Nullable final ResourceId ResourceId)
	{
		return ResourceId != null ? ResourceId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final ResourceId o1, @Nullable final ResourceId o2)
	{
		return Objects.equals(o1, o2);
	}

	int repoId;

	private ResourceId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "S_Resource_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public boolean isNoResource() {return this.repoId == NO_RESOURCE.repoId;}
}
