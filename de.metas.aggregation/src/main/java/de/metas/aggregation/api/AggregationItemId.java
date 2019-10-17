package de.metas.aggregation.api;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.aggregation
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class AggregationItemId implements RepoIdAware
{
	@JsonCreator
	public static AggregationItemId ofRepoId(final int repoId)
	{
		return new AggregationItemId(repoId);
	}

	public static AggregationItemId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new AggregationItemId(repoId) : null;
	}

	int repoId;

	private AggregationItemId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_AggregationItem_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final AggregationItemId id)
	{
		return id != null ? id.getRepoId() : -1;
	}
}
