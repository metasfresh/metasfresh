package org.adempiere.util.api;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.common.util.time.SystemTime;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class Params implements IParams
{
	public static Params ofMap(final Map<String, Object> values)
	{
		if (values == null || values.isEmpty())
		{
			return EMPTY;
		}

		return new Params(new HashMap<>(values));
	}

	public static Params copyOf(@NonNull final IParams from)
	{
		if (from instanceof Params)
		{
			return (Params)from;
		}
		else
		{
			final Collection<String> parameterNames = from.getParameterNames();
			if (parameterNames.isEmpty())
			{
				return EMPTY;
			}

			final HashMap<String, Object> values = new HashMap<>(parameterNames.size());
			for (final String parameterName : parameterNames)
			{
				final Object value = from.getParameterAsObject(parameterName);
				values.put(parameterName, value);
			}

			return new Params(values);
		}
	}

	public static final Params EMPTY = new Params();

	private final Map<String, Object> values;

	private Params()
	{
		values = ImmutableMap.of();
	}

	private Params(@NonNull final Map<String, Object> values)
	{
		this.values = values;
	}

	@Override
	public ImmutableSet<String> getParameterNames()
	{
		return ImmutableSet.copyOf(values.keySet());
	}

	@Override
	public boolean hasParameter(final String parameterName)
	{
		return values.containsKey(parameterName);
	}

	public boolean isEmpty()
	{
		return values.isEmpty();
	}

	@Override
	public Object getParameterAsObject(final String parameterName)
	{
		return values.get(parameterName);
	}

	@Override
	public String getParameterAsString(final String parameterName)
	{
		final Object value = getParameterAsObject(parameterName);
		return value == null ? null : value.toString();
	}

	@Override
	public int getParameterAsInt(final String parameterName, final int defaultValue)
	{
		final Object value = getParameterAsObject(parameterName);
		return NumberUtils.asInt(value, defaultValue);
	}

	@Override
	public <T extends RepoIdAware> T getParameterAsId(@NonNull final String parameterName, @NonNull final Class<T> type)
	{
		final Object value = getParameterAsObject(parameterName);
		if (value == null)
		{
			return null;
		}
		else if (type.isInstance(value))
		{
			@SuppressWarnings("unchecked")
			final T id = (T)value;
			return id;
		}
		else
		{
			int repoId = NumberUtils.asInt(value, -1);
			return RepoIdAwares.ofRepoIdOrNull(repoId, type);
		}
	}

	@Override
	public BigDecimal getParameterAsBigDecimal(final String parameterName)
	{
		final Object value = getParameterAsObject(parameterName);
		final BigDecimal defaultValue = null;
		return NumberUtils.asBigDecimal(value, defaultValue);
	}

	@Override
	public Timestamp getParameterAsTimestamp(final String parameterName)
	{
		final Object value = getParameterAsObject(parameterName);
		return (Timestamp)value;
	}

	@Override
	public LocalDate getParameterAsLocalDate(final String parameterName)
	{
		final Timestamp value = getParameterAsTimestamp(parameterName);
		return value != null
				? value.toLocalDateTime().toLocalDate()
				: null;

	}

	@Override
	public ZonedDateTime getParameterAsZonedDateTime(final String parameterName)
	{
		final Timestamp value = getParameterAsTimestamp(parameterName);
		return value != null
				? value.toLocalDateTime().atZone(SystemTime.zoneId())
				: null;
	}

	@Override
	public boolean getParameterAsBool(final String parameterName)
	{
		final Object value = getParameterAsObject(parameterName);
		return StringUtils.toBoolean(value);
	}

	public Params withParameter(@NonNull final String parameterName, final Object value)
	{
		final Object existingValue = values.get(parameterName);
		if (Objects.equals(value, existingValue))
		{
			return this;
		}
		else
		{
			final Map<String, Object> newValues = new HashMap<>(values);
			newValues.put(parameterName, value);
			return new Params(newValues);
		}
	}
}
