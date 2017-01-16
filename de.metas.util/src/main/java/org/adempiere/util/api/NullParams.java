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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

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
		super();
	}

	@Override
	public boolean hasParameter(String parameterName)
	{
		return false;
	}

	@Override
	public String getParameterAsString(String parameterName)
	{
		return null;
	}

	@Override
	public int getParameterAsInt(String parameterName)
	{
		return 0;
	}

	@Override
	public boolean getParameterAsBool(String parameterName)
	{
		return false;
	}

	@Override
	public Timestamp getParameterAsTimestamp(String parameterName)
	{
		return null;
	}

	@Override
	public BigDecimal getParameterAsBigDecimal(String paraCheckNetamttoinvoice)
	{
		return null;
	}

	/**
	 * Returns an empty list.
	 */
	@Override
	public Collection<String> getParameterNames()
	{
		return Collections.emptyList();
	}
}
