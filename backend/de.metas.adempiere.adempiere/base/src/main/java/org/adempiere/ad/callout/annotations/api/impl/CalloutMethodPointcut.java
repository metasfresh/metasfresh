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

import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.api.ICalloutMethodPointcut;

import de.metas.util.Check;

public final class CalloutMethodPointcut implements ICalloutMethodPointcut
{
	@Getter
	private final Method method;

	@Getter
	private final Set<String> columnNames;

	@Getter
	private final Class<?> modelClass;

	@Getter
	private final boolean paramCalloutFieldRequired;

	@Getter
	private final boolean skipIfCopying;

	@Getter
	private final boolean skipIfIndirectlyCalled;

	public CalloutMethodPointcut(
			@NonNull final Class<?> modelClass,
			@NonNull final Method method,
			@NonNull final String[] columnNames,
			final boolean paramCalloutFieldRequired,
			final boolean skipIfCopying,
			final boolean skipIfIndirectlyCalled)
	{
		this.modelClass = modelClass;
		this.method = method;

		Check.assume(columnNames.length > 0, "columnNames not empty");

		this.columnNames = new HashSet<>(columnNames.length);
		for (final String columnName : columnNames)
		{
			Check.assumeNotNull(columnName, "columnName not null");
			this.columnNames.add(columnName);
		}

		this.paramCalloutFieldRequired = paramCalloutFieldRequired;

		this.skipIfCopying = skipIfCopying;
		this.skipIfIndirectlyCalled = skipIfIndirectlyCalled;
	}
}
