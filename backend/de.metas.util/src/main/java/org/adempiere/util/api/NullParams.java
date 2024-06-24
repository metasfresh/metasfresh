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

import com.google.common.collect.ImmutableList;
import de.metas.util.lang.RepoIdAware;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * No parameters implementation of {@link IParams}. Get your instance using {@link IParams#NULL}.
 *
 * @author tsa
 *
 */
/* package */final class NullParams implements IParams
{
	public static final transient NullParams instance = new NullParams();

	private NullParams()
	{
	}

	@Override
	public boolean hasParameter(final String parameterName)
	{
		return false;
	}

	@Override
	public Object getParameterAsObject(final String parameterName)
	{
		return null;
	}

	@Override
	public String getParameterAsString(final String parameterName)
	{
		return null;
	}

	@Override
	public int getParameterAsInt(final String parameterName, final int defaultValue)
	{
		return defaultValue;
	}

	@Override
	public <T extends RepoIdAware> T getParameterAsId(String parameterName, Class<T> type)
	{
		return null;
	}

	@Override
	public boolean getParameterAsBool(final String parameterName)
	{
		return false;
	}

	@Nullable
	@Override
	public Boolean getParameterAsBoolean(final String parameterName, @Nullable final Boolean defaultValue)
	{
		return defaultValue;
	}

	@Override
	public Timestamp getParameterAsTimestamp(final String parameterName)
	{
		return null;
	}

	@Override
	public LocalDate getParameterAsLocalDate(final String parameterName)
	{
		return null;
	}

	@Override
	public ZonedDateTime getParameterAsZonedDateTime(final String parameterName)
	{
		return null;
	}

	@Nullable
	@Override
	public Instant getParameterAsInstant(final String parameterName)
	{
		return null;
	}

	@Override
	public BigDecimal getParameterAsBigDecimal(final String paraCheckNetamttoinvoice)
	{
		return null;
	}

	/**
	 * Returns an empty list.
	 */
	@Override
	public Collection<String> getParameterNames()
	{
		return ImmutableList.of();
	}

	@Override
	public <T extends Enum<T>> Optional<T> getParameterAsEnum(final String parameterName, final Class<T> enumType)
	{
		return Optional.empty();
	}
}
