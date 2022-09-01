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

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@ToString
@EqualsAndHashCode
public final class Params implements IParams
{
	public static ParamsBuilder builder() {return new ParamsBuilder();}

	public static Params ofMap(final Map<String, Object> values)
	{
		if (values == null || values.isEmpty())
		{
			return EMPTY;
		}

		final ParamsBuilder builder = builder();
		values.forEach(builder::valueObj);
		return builder.build();
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

			final ParamsBuilder builder = builder();
			for (final String parameterName : parameterNames)
			{
				final Object value = from.getParameterAsObject(parameterName);
				builder.valueObj(parameterName, value);
			}

			return builder.build();
		}
	}

	public static final Params EMPTY = new Params();

	private final ImmutableSet<String> parameterNames;
	private final ImmutableMap<String, Object> values;

	private Params()
	{
		parameterNames = ImmutableSet.of();
		values = ImmutableMap.of();
	}

	private Params(
			@NonNull final ImmutableSet<String> parameterNames,
			@NonNull final ImmutableMap<String, Object> values)
	{
		this.parameterNames = parameterNames;
		this.values = values;
	}

	public ParamsBuilder toBuilder()
	{
		return new ParamsBuilder(this);
	}

	@Override
	public ImmutableSet<String> getParameterNames()
	{
		return parameterNames;
	}

	@Override
	public boolean hasParameter(final String parameterName)
	{
		return parameterNames.contains(parameterName);
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

	@Nullable
	public <T> Class<T> getParameterAsObject(@NonNull final Class<T> type)
	{
		//noinspection unchecked
		return (Class<T>)values.get(toParameterName(type));
	}

	private static <T> String toParameterName(@NonNull final Class<T> type)
	{
		return type.getName();
	}

	@Nullable
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

	@Nullable
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
			@SuppressWarnings("unchecked") final T id = (T)value;
			return id;
		}
		else
		{
			final int repoId = NumberUtils.asInt(value, -1);
			return RepoIdAwares.ofRepoIdOrNull(repoId, type);
		}
	}

	@Override
	public BigDecimal getParameterAsBigDecimal(final String parameterName)
	{
		final Object value = getParameterAsObject(parameterName);
		return NumberUtils.asBigDecimal(value, null);
	}

	@Override
	public Timestamp getParameterAsTimestamp(final String parameterName)
	{
		final Object value = getParameterAsObject(parameterName);
		return (Timestamp)value;
	}

	@Nullable
	@Override
	public LocalDate getParameterAsLocalDate(final String parameterName)
	{
		final Timestamp value = getParameterAsTimestamp(parameterName);
		return value != null
				? value.toLocalDateTime().toLocalDate()
				: null;

	}

	@Nullable
	@Override
	public ZonedDateTime getParameterAsZonedDateTime(final String parameterName)
	{
		final Timestamp value = getParameterAsTimestamp(parameterName);
		return value != null
				? value.toInstant().atZone(SystemTime.zoneId())
				: null;
	}

	@Nullable
	@Override
	public Instant getParameterAsInstant(final String parameterName)
	{
		final Timestamp value = getParameterAsTimestamp(parameterName);
		return value != null
				? value.toInstant()
				: null;
	}

	@Override
	public boolean getParameterAsBool(final String parameterName)
	{
		//noinspection ConstantConditions
		return getParameterAsBoolean(parameterName, false);
	}

	@Nullable
	@Override
	public Boolean getParameterAsBoolean(final String parameterName, @Nullable final Boolean defaultValue)
	{
		final Object value = getParameterAsObject(parameterName);
		return StringUtils.toBoolean(value, defaultValue);
	}

	
	
	@SuppressWarnings("unused")
	public Params withParameter(@NonNull final String parameterName, final Object value)
	{
		final Object existingValue = values.get(parameterName);
		return !Objects.equals(value, existingValue)
				? toBuilder().valueObj(parameterName, value).build()
				: this;
	}

	public Map<String, Object> toJson()
	{
		return toJson(Function.identity());
	}

	public Map<String, Object> toJson(@NonNull final Function<Object, Object> toJsonConverter)
	{
		final LinkedHashMap<String, Object> result = new LinkedHashMap<>(parameterNames.size());
		for (final String parameterName : parameterNames)
		{
			final Object valueObj = values.get(parameterName);
			final Object valueJson = toJsonConverter.apply(valueObj);
			result.put(parameterName, valueJson);
		}
		return result;
	}

	public static class ParamsBuilder
	{
		private final LinkedHashSet<String> parameterNames = new LinkedHashSet<>();
		private final HashMap<String, Object> values = new HashMap<>();

		private ParamsBuilder()
		{
		}

		private ParamsBuilder(@NonNull final Params from)
		{
			parameterNames.addAll(from.parameterNames);
			values.putAll(from.values);
		}

		public Params build()
		{
			if (parameterNames.isEmpty())
			{
				return EMPTY;
			}
			else
			{
				return new Params(
						ImmutableSet.copyOf(parameterNames),
						ImmutableMap.copyOf(values));
			}
		}

		public ParamsBuilder value(@NonNull final String parameterName, @Nullable final String valueStr)
		{
			return valueObj(parameterName, valueStr);
		}

		public ParamsBuilder value(@NonNull final String parameterName, @Nullable final Integer valueInt)
		{
			return valueObj(parameterName, valueInt);
		}

		public <T extends RepoIdAware> ParamsBuilder value(@NonNull final String parameterName, @Nullable final T id)
		{
			return valueObj(parameterName, id);
		}

		public ParamsBuilder value(@NonNull final String parameterName, final boolean valueBoolean)
		{
			return valueObj(parameterName, valueBoolean);
		}

		public ParamsBuilder value(@NonNull final String parameterName, @Nullable final BigDecimal valueBD)
		{
			return valueObj(parameterName, valueBD);
		}

		public ParamsBuilder value(@NonNull final String parameterName, @Nullable final ZonedDateTime valueZDT)
		{
			return valueObj(parameterName, valueZDT);
		}

		public ParamsBuilder value(@NonNull final String parameterName, @Nullable final LocalDate valueLD)
		{
			return valueObj(parameterName, valueLD);
		}

		public ParamsBuilder valueObj(@NonNull final String parameterName, @Nullable final Object value)
		{
			parameterNames.add(parameterName);

			if (value != null)
			{
				values.put(parameterName, value);
			}
			else
			{
				values.remove(parameterName);
			}

			return this;
		}

		public ParamsBuilder valueObj(@NonNull final Object value)
		{
			return valueObj(toParameterName(value.getClass()), value);
		}

	}
}
