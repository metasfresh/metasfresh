package org.adempiere.util.api.impl;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.ObjectUtils;

import com.google.common.collect.ImmutableList;

/* package */class Params implements IParams
{
	private final Map<String, Object> params;

	public Params(final Map<String, Object> params)
	{
		Check.assumeNotNull(params, "Parameter 'params' is not null");
		this.params = new HashMap<>(params);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public boolean hasParameter(final String parameterName)
	{
		return params.containsKey(parameterName);
	}

	@Override
	public String getParameterAsString(final String parameterName)
	{
		final Object value = params.get(parameterName);
		return value == null ? null : value.toString();
	}

	@Override
	public int getParameterAsInt(final String parameterName)
	{
		final Object value = params.get(parameterName);
		final int defaultValue = 0;
		return NumberUtils.asInt(value, defaultValue);
	}

	@Override
	public BigDecimal getParameterAsBigDecimal(String parameterName)
	{
		final Object value = params.get(parameterName);
		final BigDecimal defaultValue = null;
		return NumberUtils.asBigDecimal(value, defaultValue);
	}

	@Override
	public Timestamp getParameterAsTimestamp(final String parameterName)
	{
		final Object value = params.get(parameterName);
		return (Timestamp)value;
	}

	@Override
	public boolean getParameterAsBool(String parameterName)
	{
		final Object value = params.get(parameterName);
		return (Boolean)value;
	}

	/**
	 * Returns an immutable collection from the {@code keySet} of the map this instance wraps.
	 */
	@Override
	public Collection<String> getParameterNames()
	{
		return ImmutableList.copyOf(params.keySet());
	}
}
