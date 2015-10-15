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


import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * For a given interface class, holds a map from "implementation class" to {@link ProxyClassMethodBindings}.
 * 
 * Don't use it directly, it is used by {@link ProxyMethodsCache}.
 * 
 * @author tsa
 *
 */
final class ProxyClassMethodBindingsMap
{
	private final Class<?> interfaceClass;
	private final LoadingCache<Class<?>, ProxyClassMethodBindings> implClass2bindings = CacheBuilder.newBuilder()
			.build(new CacheLoader<Class<?>, ProxyClassMethodBindings>()
			{

				@Override
				public ProxyClassMethodBindings load(final Class<?> implClass) throws Exception
				{
					return new ProxyClassMethodBindings(interfaceClass, implClass);
				}
			});

	public ProxyClassMethodBindingsMap(final Class<?> interfaceClass)
	{
		super();
		this.interfaceClass = interfaceClass;
	}

	public ProxyClassMethodBindings getMethodImplementationBindings(final Class<?> implClass)
	{
		try
		{
			return implClass2bindings.get(implClass);
		}
		catch (ExecutionException e)
		{
			throw new RuntimeException("Failed loading implementation binding for " + implClass, e.getCause());
		}
	}
}
