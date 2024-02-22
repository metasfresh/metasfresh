package de.metas.money;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

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
	public static final CurrencyId USD = new CurrencyId(100);
	public static final CurrencyId EUR = new CurrencyId(102);
	public static final CurrencyId CHF = new CurrencyId(318);

	@JsonCreator
	public static CurrencyId ofRepoId(final int repoId)
	{
		if (repoId == USD.repoId)
		{
			return USD;
		}
		else if (repoId == EUR.repoId)
		{
			return EUR;
		}
		else if (repoId == CHF.repoId)
		{
			return CHF;
		}
		else
		{
			return new CurrencyId(repoId);
		}
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

	public static int toRepoId(@Nullable final CurrencyId currencyId)
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

	@NonNull
	@SafeVarargs
	public static <T> CurrencyId getCommonCurrencyIdOfAll(
			@NonNull final Function<T, CurrencyId> getCurrencyId,
			@NonNull final String name,
			@Nullable final T... objects)
	{
		if (objects == null || objects.length == 0)
		{
			throw new AdempiereException("No " + name + " provided");
		}
		else if (objects.length == 1 && objects[0] != null)
		{
			return getCurrencyId.apply(objects[0]);
		}
		else
		{
			CurrencyId commonCurrencyId = null;
			for (final T object : objects)
			{
				if (object == null)
				{
					continue;
				}

				final CurrencyId currencyId = getCurrencyId.apply(object);
				if (commonCurrencyId == null)
				{
					commonCurrencyId = currencyId;
				}
				else if (!CurrencyId.equals(commonCurrencyId, currencyId))
				{
					throw new AdempiereException("All given " + name + "(s) shall have the same currency: " + Arrays.asList(objects));
				}
			}

			if (commonCurrencyId == null)
			{
				throw new AdempiereException("At least one non null " + name + " instance was expected: " + Arrays.asList(objects));
			}

			return commonCurrencyId;
		}
	}

	@SafeVarargs
	public static <T> void assertCurrencyMatching(
			@NonNull final Function<T, CurrencyId> getCurrencyId,
			@NonNull final String name,
			@Nullable final T... objects)
	{
		if (objects == null || objects.length <= 1)
		{
			return;
		}

		CurrencyId expectedCurrencyId = null;
		for (final T object : objects)
		{
			if (object == null)
			{
				continue;
			}

			final CurrencyId currencyId = getCurrencyId.apply(object);
			if (expectedCurrencyId == null)
			{
				expectedCurrencyId = currencyId;
			}
			else if (!CurrencyId.equals(currencyId, expectedCurrencyId))
			{
				throw new AdempiereException("" + name + " has invalid currency: " + object + ". Expected: " + expectedCurrencyId);
			}
		}
	}

}
