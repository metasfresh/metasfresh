package org.adempiere.ad.dao;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@EqualsAndHashCode
public final class QueryLimit
{
	public static QueryLimit ofInt(final int limit)
	{
		if (isNoLimit(limit))
		{
			return NO_LIMIT;
		}
		else if (limit == ONE.value)
		{
			return ONE;
		}
		else if (limit == TWO.value)
		{
			return TWO;
		}
		else if (limit == ONE_HUNDRED.value)
		{
			return ONE_HUNDRED;
		}
		else if (limit == FIVE_HUNDRED.value)
		{
			return FIVE_HUNDRED;
		}
		else
		{
			return new QueryLimit(limit);
		}
	}

	public static QueryLimit ofNullableOrNoLimit(@Nullable final Integer limit)
	{
		return limit != null ? ofInt(limit) : NO_LIMIT;
	}

	@NonNull
	public static QueryLimit getQueryLimitOrNoLimit(@Nullable final QueryLimit limit)
	{
		return limit != null ? limit : NO_LIMIT;
	}

	public static final QueryLimit NO_LIMIT = new QueryLimit(0);
	public static final QueryLimit ONE = new QueryLimit(1);
	public static final QueryLimit TWO = new QueryLimit(2);
	public static final QueryLimit ONE_HUNDRED = new QueryLimit(100);
	public static final QueryLimit FIVE_HUNDRED = new QueryLimit(500);

	private final int value;

	private QueryLimit(final int value)
	{
		this.value = isNoLimit(value) ? 0 : value;
	}

	@Override
	public String toString()
	{
		return isNoLimit()
				? "NO_LIMIT"
				: String.valueOf(value);
	}

	public int toInt()
	{
		if (isNoLimit())
		{
			throw new AdempiereException("Calling toInt() to NO_LIMIT instances is not allowed. Use toIntOrZero()");
		}

		return value;
	}

	@JsonValue
	public int toIntOrZero()
	{
		return value;
	}

	public int toIntOr(final int noLimitValue)
	{
		return isNoLimit() ? noLimitValue : value;
	}


	public boolean isLimited()
	{
		return !isNoLimit();
	}

	public boolean isNoLimit()
	{
		return isNoLimit(value);
	}

	private static boolean isNoLimit(final int value)
	{
		return value <= 0;
	}

	public QueryLimit ifNoLimitUse(final int limit)
	{
		return isNoLimit() ? ofInt(limit) : this;
	}

	public QueryLimit ifNoLimitUse(@NonNull final QueryLimit limit)
	{
		return isNoLimit() ? limit : this;
	}

	public boolean isLessThanOrEqualTo(final int other)
	{
		return isLimited() && value <= other;
	}

	public boolean isLimitHitOrExceeded(@NonNull final Collection<?> collection)
	{
		return isLimited() && value <= collection.size();
	}

	public QueryLimit minusSizeOf(@NonNull final Collection<?> collection)
	{
		if (isNoLimit() || collection.isEmpty())
		{
			return this;
		}
		else
		{
			final int collectionSize = collection.size();
			final int newLimitInt = value - collectionSize;
			if (newLimitInt <= 0)
			{
				throw new AdempiereException("Invalid collection size. It shall be less than " + value + " but it was " + collectionSize);
			}

			return ofInt(newLimitInt);
		}
	}
}
