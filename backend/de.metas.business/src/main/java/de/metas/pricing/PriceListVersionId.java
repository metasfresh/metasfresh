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
public class PriceListVersionId implements RepoIdAware
{
	@JsonCreator
	public static PriceListVersionId ofRepoId(final int repoId)
	{
		return new PriceListVersionId(repoId);
	}

	public static PriceListVersionId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	private PriceListVersionId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_PriceList_Version_ID");
	}

	public static int toRepoId(@Nullable final PriceListVersionId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
