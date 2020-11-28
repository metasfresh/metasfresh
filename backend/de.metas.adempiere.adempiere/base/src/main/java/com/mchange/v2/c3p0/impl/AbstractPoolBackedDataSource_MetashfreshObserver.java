package com.mchange.v2.c3p0.impl;

import java.lang.reflect.Method;

import org.adempiere.exceptions.AdempiereException;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public final class AbstractPoolBackedDataSource_MetashfreshObserver
{
	public static C3P0PooledConnectionPoolManager getPoolManager(final AbstractPoolBackedDataSource poolBackedDataSource)
	{
		try
		{
			// com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource.getPoolManager()
			final Method method = AbstractPoolBackedDataSource.class.getDeclaredMethod("getPoolManager");
			if (!method.isAccessible())
			{
				method.setAccessible(true);
			}

			final C3P0PooledConnectionPoolManager poolManager = (C3P0PooledConnectionPoolManager)method.invoke(poolBackedDataSource);
			return poolManager;
		}
		catch (final Exception ex)
		{
			// NOTE: this method was tested on c3p0-0.9.5.5
			throw new AdempiereException("Failed extracting " + C3P0PooledConnectionPoolManager.class + " from " + poolBackedDataSource + ". "
					+ "\n Different c3p0 version?",
					ex);
		}
	}
}
