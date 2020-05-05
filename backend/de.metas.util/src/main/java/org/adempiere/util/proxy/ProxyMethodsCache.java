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

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Contains a cached map between interface methods and implementation methods.
 * 
 * Use it when you are using java dynamic proxy and you want to cache the binding between interface method and wrapped object.
 * It will use introspection to retrieve its information, but will do so only once (lazily) and then store the information.
 * 
 * @author tsa
 * 
 */
public final class ProxyMethodsCache
{
	private static final ProxyMethodsCache instance = new ProxyMethodsCache();

	private final LoadingCache<Class<?>, ProxyClassMethodBindingsMap> interfaceClass2implBindingsMap = CacheBuilder.newBuilder()
			.build(new CacheLoader<Class<?>, ProxyClassMethodBindingsMap>()
			{
				@Override
				public ProxyClassMethodBindingsMap load(Class<?> interfaceClass) throws Exception
				{
					return new ProxyClassMethodBindingsMap(interfaceClass);
				}
			});

	public static final ProxyMethodsCache getInstance()
	{
		return instance;
	}

	private ProxyMethodsCache()
	{
		super();
	}

	/**
	 * Gets the implementation method of a given interface method.
	 * 
	 * @param interfaceMethod
	 * @param implClass implementation class where the implementation method will be searched
	 * @return implementation method or <code>null</code> if it was not found
	 */
	public final Method getMethodImplementation(final Method interfaceMethod, final Class<?> implClass)
	{
		final Class<?> interfaceClass = interfaceMethod.getDeclaringClass();
		final ProxyClassMethodBindingsMap implBindingsMap = getProxyClassMethodBindingsMap(interfaceClass);
		final ProxyClassMethodBindings bindings = implBindingsMap.getMethodImplementationBindings(implClass);
		final Method implMethod = bindings.getMethodImplementation(interfaceMethod);
		return implMethod;
	}

	/**
	 * Create (if it doesn't yet exist) and look up the method binding.
	 * 
	 * @param interfaceClass
	 * @return
	 */
	private final ProxyClassMethodBindingsMap getProxyClassMethodBindingsMap(final Class<?> interfaceClass)
	{
		try
		{
			return interfaceClass2implBindingsMap.get(interfaceClass);
		}
		catch (ExecutionException e)
		{
			// shall not happen
			throw new RuntimeException("Failed loading bindings for " + interfaceClass);
		}
	}
}
