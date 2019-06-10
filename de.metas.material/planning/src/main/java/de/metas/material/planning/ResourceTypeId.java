package de.metas.material.planning;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

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
 * Resource Type (S_ResourceType_ID)
 */
@Value
public class ResourceTypeId implements RepoIdAware
{
	@JsonCreator
	public static ResourceTypeId ofRepoId(final int repoId)
	{
		return new ResourceTypeId(repoId);
	}

	public static ResourceTypeId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ResourceTypeId(repoId) : null;
	}

	public static int toRepoId(final ResourceTypeId ResourceTypeId)
	{
		return ResourceTypeId != null ? ResourceTypeId.getRepoId() : -1;
	}

	public static boolean equals(final ResourceTypeId o1, final ResourceTypeId o2)
	{
		return Objects.equals(o1, o2);
	}

	int repoId;

	private ResourceTypeId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "S_ResourceType_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
