package de.metas.cache.interceptor;

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


import java.lang.annotation.Annotation;

import de.metas.cache.annotation.CacheReloadIfTrue;

/**
 * Handles {@link CacheReloadIfTrue} annotation.
 * 
 * @author tsa
 *
 */
public class CacheReloadIfTrueParamDescriptor implements ICachedMethodPartDescriptor
{
	private final int parameterIndex;

	public CacheReloadIfTrueParamDescriptor(final Class<?> parameterType, final int parameterIndex, final Annotation annotation)
	{
		super();

		this.parameterIndex = parameterIndex;

		if (Boolean.class == parameterType)
		{
			// OK, nothing to do
		}
		else if (boolean.class == parameterType)
		{
			// OK, nothing to do
		}
		else
		{
			throw new CacheIntrospectionException("Parameter has unsupported type")
					.setParameter(parameterIndex, parameterType);
		}
	}

	@Override
	public void extractKeyParts(final CacheKeyBuilder keyBuilder, final Object targetObject, final Object[] params)
	{
		final Boolean cacheReloadFlagObj = (Boolean)params[parameterIndex];
		final boolean cacheReloadFlag = cacheReloadFlagObj == null ? false : cacheReloadFlagObj;

		if (cacheReloadFlag)
		{
			keyBuilder.setCacheReload();
		}
	}

}
