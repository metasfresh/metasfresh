package de.metas.adempiere.util.cache;

import java.lang.annotation.Annotation;

import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;

import de.metas.adempiere.util.CacheModelId;
import de.metas.adempiere.util.CacheTrx;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Handles {@link CacheModelId} annotation.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class CacheModelIdParamDescriptor implements ICachedMethodPartDescriptor
{
	private static final Logger logger = LogManager.getLogger(CacheModelIdParamDescriptor.class);

	private final int parameterIndex;

	public CacheModelIdParamDescriptor(final Class<?> parameterType, final int parameterIndex, final Annotation annotation)
	{
		super();

		this.parameterIndex = parameterIndex;
		if (InterfaceWrapperHelper.isModelInterface(parameterType))
		{
			// nothing to do
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
		final Object modelObj = params[parameterIndex];

		int id = -1;
		Exception errorException = null;
		if (modelObj != null)
		{
			try
			{
				id = InterfaceWrapperHelper.getId(modelObj);
			}
			catch (final Exception ex)
			{
				id = -1;
				errorException = ex;
			}
		}

		if (id < 0 || errorException != null)
		{
			keyBuilder.setSkipCaching();

			final CacheGetException ex = new CacheGetException("Invalid parameter type.")
					.addSuppressIfNotNull(errorException)
					.setTargetObject(targetObject)
					.setMethodArguments(params)
					.setInvalidParameter(parameterIndex, modelObj)
					.setAnnotation(CacheTrx.class);
			logger.warn("Invalid parameter. Skip caching", ex);
			return;
		}

		keyBuilder.add(id);
	}

}
