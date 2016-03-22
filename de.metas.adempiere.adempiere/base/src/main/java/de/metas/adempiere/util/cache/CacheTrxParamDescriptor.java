package de.metas.adempiere.util.cache;

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
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.adempiere.util.CacheTrx;

/**
 * Handles {@link CacheTrx} annotation.
 * 
 * @author tsa
 *
 */
class CacheTrxParamDescriptor implements ICachedMethodPartDescriptor
{
	private static final transient Logger logger = LogManager.getLogger(CacheTrxParamDescriptor.class);

	private final int parameterIndex;

	public CacheTrxParamDescriptor(Class<?> parameterType, int parameterIndex, Annotation annotation)
	{
		super();

		this.parameterIndex = parameterIndex;
		if (String.class.isAssignableFrom(parameterType))
		{
			// nothing to do
		}
		// NOTE: in future we would also support model objects
		// else if (is model object)
		else
		{
			throw new CacheIntrospectionException("Parameter has unsupported type")
					.setParameter(parameterIndex, parameterType);
		}
	}

	@Override
	public void extractKeyParts(final CacheKeyBuilder keyBuilder, final Object targetObject, final Object[] params)
	{
		final Object trxNameObj = params[parameterIndex];

		final String trxName;
		if (trxNameObj == null)
		{
			trxName = null;
		}
		else if (trxNameObj instanceof String)
		{
			trxName = (String)trxNameObj;
		}
		// Case: it's NOT a String instance (shall not happen)
		else
		{
			keyBuilder.setSkipCaching();

			final CacheGetException ex = new CacheGetException("Invalid parameter type.")
					.setTargetObject(targetObject)
					.setMethodArguments(params)
					.setInvalidParameter(parameterIndex, trxNameObj)
					.setAnnotation(CacheTrx.class);
			logger.warn("Invalid parameter. Skip caching", ex);
			return;
		}

		// NOTE: we assume the caller will separate caches per transaction, so there is no point to have it as part of the cache key
		// More,
		// * consider the case of ThreadInherited transactions which we would have to resolve them here
		// * it shall be the responsability of the builder to include it in key, in case it's really needed
		// keyBuilder.add(trxName);

		keyBuilder.setTrxName(trxName);
	}
}
