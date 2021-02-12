package de.metas.money;

import java.util.Objects;
import java.util.Optional;

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
public class CurrencyId implements RepoIdAware
{
	@JsonCreator
	public static CurrencyId ofRepoId(final int repoId)
	{
		return new CurrencyId(repoId);
	}

	@Nullable
	public static CurrencyId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<CurrencyId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(final CurrencyId currencyId)
	{
		return currencyId != null ? currencyId.getRepoId() : -1;
	}

	int repoId;

	private CurrencyId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Currency_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final CurrencyId currencyId1, @Nullable final CurrencyId currencyId2)
	{
		return Objects.equals(currencyId1, currencyId2);
	}
}
