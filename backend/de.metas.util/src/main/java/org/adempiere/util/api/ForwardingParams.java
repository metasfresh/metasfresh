package org.adempiere.util.api;

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
import java.util.Collection;
import java.util.Optional;

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
public class ForwardingParams implements IParams
{
	private final IParams params;

	public ForwardingParams(@NonNull final IParams params)
	{
		this.params = params;
	}

	@Override
	public boolean hasParameter(final String parameterName)
	{
		return params.hasParameter(parameterName);
	}

	@Override
	public Collection<String> getParameterNames()
	{
		return params.getParameterNames();
	}

	@Override
	public Object getParameterAsObject(final String parameterName)
	{
		return params.getParameterAsObject(parameterName);
	}

	@Override
	public String getParameterAsString(final String parameterName)
	{
		return params.getParameterAsString(parameterName);
	}

	@Override
	public int getParameterAsInt(final String parameterName, final int defaultValue)
	{
		return params.getParameterAsInt(parameterName, defaultValue);
	}

	@Override
	public <T extends RepoIdAware> T getParameterAsId(String parameterName, Class<T> type)
	{
		return params.getParameterAsId(parameterName, type);
	}

	@Override
	public BigDecimal getParameterAsBigDecimal(final String parameterName)
	{
		return params.getParameterAsBigDecimal(parameterName);
	}

	@Override
	public Timestamp getParameterAsTimestamp(final String parameterName)
	{
		return params.getParameterAsTimestamp(parameterName);
	}

	@Override
	public LocalDate getParameterAsLocalDate(final String parameterName)
	{
		return params.getParameterAsLocalDate(parameterName);
	}

	@Override
	public ZonedDateTime getParameterAsZonedDateTime(final String parameterName)
	{
		return params.getParameterAsZonedDateTime(parameterName);
	}

	@Nullable
	@Override
	public Instant getParameterAsInstant(final String parameterName)
	{
		return params.getParameterAsInstant(parameterName);
	}

	@Override
	public boolean getParameterAsBool(final String parameterName)
	{
		return params.getParameterAsBool(parameterName);
	}

	@Nullable
	@Override
	public Boolean getParameterAsBoolean(final String parameterName, @Nullable final Boolean defaultValue)
	{
		return params.getParameterAsBoolean(parameterName, defaultValue);
	}

	@Override
	public <T extends Enum<T>> Optional<T> getParameterAsEnum(final String parameterName, final Class<T> enumType)
	{
		return params.getParameterAsEnum(parameterName, enumType);
	}
}
