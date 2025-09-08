package de.metas.fresh.freshQtyOnHand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

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
public class FreshQtyOnHandId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static FreshQtyOnHandId ofRepoId(final int repoId)
	{
		return new FreshQtyOnHandId(repoId);
	}

	@Nullable
	public static FreshQtyOnHandId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new FreshQtyOnHandId(repoId) : null;
	}

	@Nullable
	public static FreshQtyOnHandId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new FreshQtyOnHandId(repoId) : null;
	}

	public static int toRepoId(@Nullable final FreshQtyOnHandId freshQtyOnHandId)
	{
		return freshQtyOnHandId != null ? freshQtyOnHandId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final FreshQtyOnHandId o1, @Nullable final FreshQtyOnHandId o2)
	{
		return Objects.equals(o1, o2);
	}

	private FreshQtyOnHandId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "FreshQtyOnHandId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
