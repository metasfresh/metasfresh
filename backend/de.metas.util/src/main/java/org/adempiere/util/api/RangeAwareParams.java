package org.adempiere.util.api;

import com.google.common.collect.ImmutableSet;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * de.metas.util
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

@ToString
@EqualsAndHashCode
public class RangeAwareParams implements IRangeAwareParams
{
	public static RangeAwareParams ofMaps(final Map<String, Object> values, final Map<String, Object> valuesTo)
	{
		final Params params = Params.ofMap(values);
		final Params paramsTo = Params.ofMap(valuesTo);
		if (params.isEmpty() && paramsTo.isEmpty())
		{
			return EMPTY;
		}
		return new RangeAwareParams(params, paramsTo);
	}

	private static final RangeAwareParams EMPTY = new RangeAwareParams();

	private final ImmutableSet<String> parameterNames;
	private final Params values;
	private final Params valuesTo;

	private RangeAwareParams()
	{
		values = Params.EMPTY;
		valuesTo = Params.EMPTY;
		parameterNames = ImmutableSet.of();
	}

	private RangeAwareParams(@NonNull final Params values, @NonNull final Params valuesTo)
	{
		this.values = values;
		this.valuesTo = valuesTo;

		parameterNames = ImmutableSet.<String> builder()
				.addAll(values.getParameterNames())
				.addAll(valuesTo.getParameterNames())
				.build();
	}

	@Override
	public Set<String> getParameterNames()
	{
		return parameterNames;
	}

	@Override
	public boolean hasParameter(final String parameterName)
	{
		return parameterNames.contains(parameterName);
	}

	@Override
	public Object getParameterAsObject(final String parameterName)
	{
		return values.getParameterAsObject(parameterName);
	}

	@Override
	public String getParameterAsString(final String parameterName)
	{
		return values.getParameterAsString(parameterName);
	}

	@Override
	public int getParameterAsInt(final String parameterName, final int defaultValue)
	{
		return values.getParameterAsInt(parameterName, defaultValue);
	}

	@Override
	public <T extends RepoIdAware> T getParameterAsId(String parameterName, Class<T> type)
	{
		return values.getParameterAsId(parameterName, type);
	}

	@Override
	public boolean getParameterAsBool(final String parameterName)
	{
		return values.getParameterAsBool(parameterName);
	}

	@Nullable
	@Override
	public Boolean getParameterAsBoolean(final String parameterName, @Nullable final Boolean defaultValue)
	{
		return values.getParameterAsBoolean(parameterName, defaultValue);
	}

	@Override
	public Timestamp getParameterAsTimestamp(final String parameterName)
	{
		return values.getParameterAsTimestamp(parameterName);
	}

	@Override
	public BigDecimal getParameterAsBigDecimal(final String parameterName)
	{
		return values.getParameterAsBigDecimal(parameterName);
	}

	@Override
	public Object getParameter_ToAsObject(final String parameterName)
	{
		return valuesTo.getParameterAsObject(parameterName);
	}

	@Override
	public String getParameter_ToAsString(final String parameterName)
	{
		return valuesTo.getParameterAsString(parameterName);
	}

	@Override
	public int getParameter_ToAsInt(final String parameterName, final int defaultValue)
	{
		return valuesTo.getParameterAsInt(parameterName, defaultValue);
	}

	@Override
	public boolean getParameter_ToAsBool(final String parameterName)
	{
		return valuesTo.getParameterAsBool(parameterName);
	}

	@Override
	public Timestamp getParameter_ToAsTimestamp(final String parameterName)
	{
		return valuesTo.getParameterAsTimestamp(parameterName);
	}

	@Override
	public BigDecimal getParameter_ToAsBigDecimal(final String parameterName)
	{
		return valuesTo.getParameterAsBigDecimal(parameterName);
	}

	@Override
	public LocalDate getParameterAsLocalDate(String parameterName)
	{
		return values.getParameterAsLocalDate(parameterName);
	}

	@Override
	public LocalDate getParameter_ToAsLocalDate(String parameterName)
	{
		return valuesTo.getParameterAsLocalDate(parameterName);
	}

	@Override
	public ZonedDateTime getParameterAsZonedDateTime(String parameterName)
	{
		return values.getParameterAsZonedDateTime(parameterName);
	}

	@Nullable
	@Override
	public Instant getParameterAsInstant(final String parameterName)
	{
		return values.getParameterAsInstant(parameterName);
	}

	@Override
	public ZonedDateTime getParameter_ToAsZonedDateTime(String parameterName)
	{
		return valuesTo.getParameterAsZonedDateTime(parameterName);
	}

	@Override
	public <T extends Enum<T>> Optional<T> getParameterAsEnum(final String parameterName, final Class<T> enumType)
	{
		return values.getParameterAsEnum(parameterName, enumType);
	}
}
