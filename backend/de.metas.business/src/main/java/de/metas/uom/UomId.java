package de.metas.uom;

import java.util.Objects;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

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
public class UomId implements RepoIdAware
{
	public static final UomId EACH = new UomId(100);

	@JsonCreator
	public static UomId ofRepoId(final int repoId)
	{
		if (repoId == EACH.repoId)
		{
			return EACH;
		}
		else
		{
			return new UomId(repoId);
		}
	}

	public static UomId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(final UomId uomId)
	{
		return uomId != null ? uomId.getRepoId() : -1;
	}

	int repoId;

	private UomId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_UOM_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final UomId id1, @Nullable final UomId id2)
	{
		return Objects.equals(id1, id2);
	}
}
