package de.metas.pricing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
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

@Value
public class PriceListId implements RepoIdAware
{
	@JsonCreator
	public static PriceListId ofRepoId(final int repoId)
	{
		if (repoId == NONE.repoId)
		{
			return NONE;
		}
		return new PriceListId(repoId);
	}

	@Nullable
	public static PriceListId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int getRepoId(final PriceListId PriceListId)
	{
		return PriceListId != null ? PriceListId.getRepoId() : -1;
	}

	public static final PriceListId NONE = new PriceListId(100);

	int repoId;

	private PriceListId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	public boolean isNone()
	{
		return repoId == NONE.repoId;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final PriceListId id) {return id != null ? id.getRepoId() : -1;}
}
