package org.adempiere.util.proxy;

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


import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

import org.adempiere.util.reflect.NullMethod;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * For a given interface class and implementation class, holds a binding map between methods.
 * 
 * Don't use it directly, it is used by {@link ProxyMethodsCache}.
 * 
 * @author tsa
 *
 */
final class ProxyClassMethodBindings
{
	// private final Class<?> interfaceClass;
	private final Class<?> implClass;

	private final LoadingCache<Method, Method> interfaceMethod2implMethod = CacheBuilder.newBuilder()
			.build(new CacheLoader<Method, Method>()
			{

				@Override
				public Method load(Method interfaceMethod) throws Exception
				{
					final Method implMethod = findMethodImplementationOrNull(interfaceMethod);
					return implMethod == null ? NullMethod.NULL : implMethod;
				}
			});

	public ProxyClassMethodBindings(final Class<?> interfaceClass, final Class<?> implClass)
	{
		super();

		// NOTE: both parameters are not always not null, but we are not checking them for optimization reasons
		// this.interfaceClass = interfaceClass;
		this.implClass = implClass;
	}

	private final Method findMethodImplementationOrNull(final Method interfaceMethod)
	{
		try
		{
			final String name = interfaceMethod.getName();
			final Class<?>[] parameterTypes = interfaceMethod.getParameterTypes();
			final Method implMethod = implClass.getMethod(name, parameterTypes);
			return implMethod;
		}
		catch (SecurityException e)
		{
			throw new IllegalStateException("Cannot get implementation method for " + implClass + ", interfaceMethod=" + interfaceMethod, e);
		}
		catch (NoSuchMethodException e)
		{
			return null;
		}
	}

	public Method getMethodImplementation(final Method interfaceMethod)
	{
		try
		{
			final Method implMethod = interfaceMethod2implMethod.get(interfaceMethod);
			if (implMethod == null || implMethod == NullMethod.NULL)
			{
				return null;
			}
			return implMethod;
		}
		catch (ExecutionException e)
		{
			// NOTE: shall not happen
			final String message = "Error while trying to find implementation method"
					+ "\n Interface method: " + interfaceMethod
					+ "\n Implementation class: " + implClass;
			throw new RuntimeException(message, e.getCause());
		}
	}
}
