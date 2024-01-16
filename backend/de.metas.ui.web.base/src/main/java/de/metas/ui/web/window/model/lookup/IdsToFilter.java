/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.window.model.lookup;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class IdsToFilter
{
	private final boolean noValue;
	@Nullable private final Object singleValue;
	@Nullable private final ImmutableList<Object> multipleValues;

	public static final IdsToFilter NO_VALUE = new IdsToFilter();

	private IdsToFilter(
			final boolean noValue,
			@Nullable final Object singleValue,
			@Nullable final ImmutableList<Object> multipleValues)
	{
		this.noValue = noValue;
		this.singleValue = singleValue;
		this.multipleValues = multipleValues;
	}

	/**
	 * no value constructor
	 */
	private IdsToFilter()
	{
		this.noValue = true;
		this.singleValue = null;
		this.multipleValues = null;
	}

	public static IdsToFilter ofSingleValue(@Nullable final Object obj)
	{
		if (obj == null)
		{
			return NO_VALUE;
		}
		else if (obj instanceof Collection)
		{
			throw new AdempiereException("Invalid single value: " + obj);
		}
		else
		{
			return new IdsToFilter(false, obj, null);
		}
	}

	public static IdsToFilter ofMultipleValues(@NonNull final Collection<?> ids)
	{
		final ImmutableList<Object> multipleValues = normalizeMultipleValues(ids);
		if (multipleValues.isEmpty())
		{
			return NO_VALUE;
		}
		else if (multipleValues.size() == 1)
		{
			final Object singleValue = multipleValues.get(0);
			return ofSingleValue(singleValue);
		}
		else
		{
			return new IdsToFilter(false, null, multipleValues);
		}
	}

	private static ImmutableList<Object> normalizeMultipleValues(@NonNull final Collection<?> ids)
	{
		if (ids instanceof ImmutableList)
		{
			// consider it already normalized

			//noinspection unchecked
			return (ImmutableList<Object>)ids;
		}
		else
		{
			return ids.stream()
					.filter(Objects::nonNull)
					.collect(ImmutableList.toImmutableList());
		}
	}

	public static boolean equals(@Nullable final IdsToFilter obj1, @Nullable final IdsToFilter obj2)
	{
		return Objects.equals(obj1, obj2);
	}

	public boolean isNoValue()
	{
		return noValue;
	}

	public boolean isSingleValue()
	{
		return singleValue != null;
	}

	public boolean isMultipleValues()
	{
		return multipleValues != null;
	}

	@Nullable
	public Object getSingleValueAsObject()
	{
		if (noValue)
		{
			return null;
		}
		else if (singleValue != null)
		{
			return singleValue;
		}
		else
		{
			throw new AdempiereException("Not a single value instance: " + this);
		}
	}

	public ImmutableList<Object> getMultipleValues()
	{
		if (multipleValues != null)
		{
			return multipleValues;
		}
		else
		{
			throw new AdempiereException("Not a multiple values instance: " + this);
		}
	}

	@Nullable
	public Integer getSingleValueAsInteger(@Nullable final Integer defaultValue)
	{
		final Object value = getSingleValueAsObject();
		if (value == null)
		{
			return defaultValue;
		}
		else if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		else
		{
			final String valueStr = StringUtils.trimBlankToNull(value.toString());
			if (valueStr == null)
			{
				return defaultValue;
			}
			else
			{
				return Integer.parseInt(valueStr);
			}
		}
	}

	@Nullable
	public String getSingleValueAsString()
	{
		final Object value = getSingleValueAsObject();
		return value != null ? value.toString() : null;
	}

	public Stream<IdsToFilter> streamSingleValues()
	{
		if (noValue)
		{
			return Stream.empty();
		}
		else if (singleValue != null)
		{
			return Stream.of(this);
		}
		else
		{
			Objects.requireNonNull(multipleValues);
			return multipleValues.stream().map(IdsToFilter::ofSingleValue);
		}
	}

	public ImmutableList<Object> toImmutableList()
	{
		if (noValue)
		{
			return ImmutableList.of();
		}
		else if (singleValue != null)
		{
			return ImmutableList.of(singleValue);
		}
		else
		{
			Objects.requireNonNull(multipleValues);
			return multipleValues;
		}
	}

	public IdsToFilter mergeWith(@NonNull final IdsToFilter other)
	{
		if (other.isNoValue())
		{
			return this;
		}
		else if (isNoValue())
		{
			return other;
		}
		else
		{
			final ImmutableSet<Object> multipleValues = Stream.concat(toImmutableList().stream(), other.toImmutableList().stream())
					.distinct()
					.collect(ImmutableSet.toImmutableSet());
			return ofMultipleValues(multipleValues);
		}
	}
}
