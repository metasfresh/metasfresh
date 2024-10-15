package de.metas.money;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
public class CurrencyConversionTypeId implements RepoIdAware
{
	@JsonCreator
	public static CurrencyConversionTypeId ofRepoId(final int repoId)
	{
		return new CurrencyConversionTypeId(repoId);
	}

	@Nullable
	public static CurrencyConversionTypeId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<CurrencyConversionTypeId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final CurrencyConversionTypeId CurrencyConversionTypeId)
	{
		return CurrencyConversionTypeId != null ? CurrencyConversionTypeId.getRepoId() : -1;
	}

	int repoId;

	private CurrencyConversionTypeId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_ConversionType_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final CurrencyConversionTypeId currencyConversionTypeId1, @Nullable final CurrencyConversionTypeId currencyConversionTypeId2)
	{
		return Objects.equals(currencyConversionTypeId1, currencyConversionTypeId2);
	}
}
