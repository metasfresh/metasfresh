package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
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

import de.metas.dunning.api.IDunningContext;
import de.metas.util.Check;

public abstract class AbstractDunningContext implements IDunningContext
{
	private Map<String, Object> properties;

	public AbstractDunningContext()
	{
		super();
	}

	public AbstractDunningContext(final IDunningContext context)
	{
		this();

		if (context instanceof AbstractDunningContext)
		{
			final AbstractDunningContext c = (AbstractDunningContext)context;
			if (c.properties != null && !c.properties.isEmpty())
			{
				this.properties = new HashMap<String, Object>(c.properties);
			}
		}
	}

	@Override
	public void setProperty(final String propertyName, final Object value)
	{
		Check.assumeNotNull(propertyName, "propertyName not null");

		if (properties == null)
		{
			properties = new HashMap<String, Object>();
		}
		properties.put(propertyName, value);
	}

	@Override
	public <T> T getProperty(final String propertyName)
	{
		final T defaultValue = null;
		return getProperty(propertyName, defaultValue);
	}

	@Override
	public <T> T getProperty(final String propertyName, final T defaultValue)
	{
		Check.assumeNotNull(propertyName, "propertyName not null");

		if (properties == null)
		{
			return defaultValue;
		}
		final Object valueObj = properties.get(propertyName);
		if (valueObj == null)
		{
			return defaultValue;
		}

		@SuppressWarnings("unchecked")
		final T value = (T)valueObj;

		return value;
	}

	@Override
	public boolean isProperty(final String propertyName)
	{
		final boolean defaultValue = false;
		return isProperty(propertyName, defaultValue);
	}

	@Override
	public boolean isProperty(final String propertyName, final boolean defaultValue)
	{
		final Boolean value = getProperty(propertyName);
		if (value == null)
		{
			return defaultValue;
		}
		return value.booleanValue();
	}

}
