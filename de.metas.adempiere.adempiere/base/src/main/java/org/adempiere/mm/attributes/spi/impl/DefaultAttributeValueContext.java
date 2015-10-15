package org.adempiere.mm.attributes.spi.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.HashMap;
import java.util.Map;

import org.adempiere.mm.attributes.spi.IAttributeValueContext;

public class DefaultAttributeValueContext implements IAttributeValueContext
{
	private final Map<String, Object> parameters;
	
	public DefaultAttributeValueContext()
	{
		this(null);
	}

	protected DefaultAttributeValueContext(final Map<String, Object> parameters)
	{
		super();

		if (parameters == null || parameters.isEmpty())
		{
			this.parameters = new HashMap<String, Object>();
		}
		else
		{
			this.parameters = new HashMap<String, Object>(parameters);
		}
	}
	
	@Override
	public IAttributeValueContext copy()
	{
		return new DefaultAttributeValueContext(parameters);
	}

	@Override
	public final Object setParameter(final String parameterName, final Object value)
	{
		final Object valueOld = parameters.put(parameterName, value);
		return valueOld;
	}

	@Override
	public final <T> T getParameter(final String parameterName)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)parameters.get(parameterName);
		return value;
	}

	protected final Map<String, Object> getParameters()
	{
		return new HashMap<String, Object>(parameters);
	}
}
