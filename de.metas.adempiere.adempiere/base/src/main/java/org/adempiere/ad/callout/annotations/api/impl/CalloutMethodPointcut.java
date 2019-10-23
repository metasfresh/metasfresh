package org.adempiere.ad.callout.annotations.api.impl;

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


import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.ad.callout.annotations.api.ICalloutMethodPointcut;

import de.metas.util.Check;

public final class CalloutMethodPointcut implements ICalloutMethodPointcut
{
	private final Method method;
	private final Set<String> columnNames;
	private final Class<?> modelClass;
	private final boolean paramCalloutFieldRequired;
	private final boolean skipIfCopying;

	public CalloutMethodPointcut(final Class<?> modelClass,
			final Method method,
			final String[] columnNames,
			final boolean paramCalloutFieldRequired,
			final boolean skipIfCopying)
	{
		super();

		Check.assumeNotNull(modelClass, "modelClass not null");
		this.modelClass = modelClass;

		Check.assumeNotNull(method, "method not null");
		this.method = method;

		Check.assumeNotNull(columnNames, "columnNames not null");
		Check.assume(columnNames.length > 0, "columnNames not empty");

		this.columnNames = new HashSet<String>(columnNames.length);
		for (final String columnName : columnNames)
		{
			Check.assumeNotNull(columnName, "columnName not null");
			this.columnNames.add(columnName);
		}

		this.paramCalloutFieldRequired = paramCalloutFieldRequired;

		this.skipIfCopying = skipIfCopying;
	}

	@Override
	public Method getMethod()
	{
		return method;
	}

	@Override
	public Set<String> getColumnNames()
	{
		return columnNames;
	}

	@Override
	public Class<?> getModelClass()
	{
		return modelClass;
	}

	@Override
	public boolean isParamCalloutFieldRequired()
	{
		return paramCalloutFieldRequired;
	}

	@Override
	public boolean isSkipIfCopying()
	{
		return skipIfCopying;
	}
}
