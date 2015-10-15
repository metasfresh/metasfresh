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
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;

import de.metas.adempiere.util.CacheCtx;

/**
 * Handles {@link CacheCtx} annotation.
 * 
 * @author tsa
 *
 */
public class CacheCtxParamDescriptor implements ICachedMethodPartDescriptor
{
	private static final transient CLogger logger = CLogger.getCLogger(CacheCtxParamDescriptor.class);

	private final int parameterIndex;

	CacheCtxParamDescriptor(Class<?> parameterType, int parameterIndex, Annotation annotation)
	{
		super();

		this.parameterIndex = parameterIndex;
		if (Properties.class.isAssignableFrom(parameterType))
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
		final Object ctxObj = params[parameterIndex];

		if (ctxObj == null)
		{
			keyBuilder.setSkipCaching();

			final CacheGetException ex = new CacheGetException("Got null context parameter.")
					.setTargetObject(targetObject)
					.setMethodArguments(params)
					.setInvalidParameter(parameterIndex, ctxObj)
					.setAnnotation(CacheCtx.class);
			logger.log(Level.WARNING, "Got null context object. Skip caching", ex);
			return;
		}

		// Make sure it's a Properties instance (shall not happen)
		if (!(ctxObj instanceof Properties))
		{
			keyBuilder.setSkipCaching();

			final CacheGetException ex = new CacheGetException("Invalid parameter type.")
					.setTargetObject(targetObject)
					.setMethodArguments(params)
					.setInvalidParameter(parameterIndex, ctxObj)
					.setAnnotation(CacheCtx.class);
			logger.log(Level.WARNING, "Invalid parameter type for @CacheCtx annotation. Skip caching.", ex);
			return;
		}

		final Properties ctx = (Properties)ctxObj;
		final ArrayKey key = buildCacheKey(ctx);
		keyBuilder.add(key);
	}

	private static final ArrayKey buildCacheKey(final Properties ctx)
	{
		return new ArrayKey(
				Env.getAD_Client_ID(ctx),
				Env.getAD_Role_ID(ctx),
				Env.getAD_User_ID(ctx),
				Env.getAD_Language(ctx));
	}

	/**
	 * Method used to compare if to contexts are considered to be equal from caching perspective.
	 * Equality from caching perspective means that the following is equal:
	 * <ul>
	 * <li>AD_Client_ID
	 * <li>AD_Role_ID
	 * <li>AD_User_ID
	 * <li>AD_Language
	 * </ul>
	 * The aim of this method is to be used for hot fixes, where we can not implement services which annotated cached methods, but want just to fix the issue.
	 * That's also why the method is flagged as deprecated from the very beginning.
	 * 
	 * @param ctx1
	 * @param ctx2
	 * @return true if given contexts shall be considered equal from caching perspective
	 */
	@Deprecated
	public static final boolean isSameCtx(final Properties ctx1, final Properties ctx2)
	{
		if (ctx1 == ctx2)
		{
			return true;
		}
		if (ctx1 == null || ctx2 == null)
		{
			return false;
		}

		return buildCacheKey(ctx1).equals(buildCacheKey(ctx2));
	}
}
