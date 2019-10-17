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


import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.proxy.impl.JavaAssistInterceptor;
import org.junit.Assert;
import org.junit.Ignore;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import de.metas.util.IService;

/**
 * Helper class used to test and make sure ALL classes and methods from current classpath are correctly annotated with {@link Cached}.
 * 
 * @author tsa
 *
 */
@Ignore
public class ClasspathCachedMethodsTester
{
	private final JavaAssistInterceptor javaAssistInterceptor;

	private final Set<Class<?>> testedClasses = new HashSet<>();

	/** Classnames to skip while checking (aka. known issues) */
	private final Set<String> classnamesToSkip = new HashSet<>();

	private int exceptionsCount = 0;

	public ClasspathCachedMethodsTester()
	{
		javaAssistInterceptor = new JavaAssistInterceptor();
	}

	public void test()
	{
		final Reflections reflections = new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forClassLoader())
				.setScanners(new MethodAnnotationsScanner())
				);

		final Set<Method> methods = reflections.getMethodsAnnotatedWith(Cached.class);
		System.out.println("Found " + methods.size() + " methods annotated with " + Cached.class);

		if(methods.isEmpty())
		{
			throw new AdempiereException("No classes found. Might be because for some reason Reflections does not work correctly with maven surefire plugin."
					+ "\n See https://github.com/metasfresh/metasfresh/issues/4773.");
		}

		for (final Method method : methods)
		{
			testMethod(method);
		}

		assertNoExceptions();
	}

	public ClasspathCachedMethodsTester skipIfClassnameStartsWith(final String classnamePrefix)
	{
		classnamesToSkip.add(classnamePrefix);
		return this;
	}

	private final void testMethod(final Method method)
	{
		final Class<?> methodClass = method.getDeclaringClass();
		if (skipClass(methodClass))
		{
			return;
		}

		testClass(methodClass);

		try
		{
			new CachedMethodDescriptor(method);
		}
		catch (CacheIntrospectionException e)
		{
			final CacheIntrospectionException ex = CacheIntrospectionException.wrapIfNeeded(e)
					.setMethod(method);
			logException(ex);
		}

	}

	/**
	 * Test if the class is OK for caching.
	 * 
	 * @param clazz
	 */
	private final void testClass(final Class<?> clazz)
	{
		// Skip if already tested
		if (!testedClasses.add(clazz))
		{
			return;
		}

		try
		{
			if (IService.class.isAssignableFrom(clazz))
			{
				@SuppressWarnings("unchecked")
				final Class<? extends IService> serviceClass = (Class<? extends IService>)clazz;
				javaAssistInterceptor.createInterceptedClass(serviceClass);
			}
			else
			{
				throw new RuntimeException("Class " + clazz + " is not a service");
			}
		}
		catch (Exception e)
		{
			logException(e);
		}
	}

	private final boolean skipClass(final Class<?> clazz)
	{
		final String classname = clazz.getName();

		for (final String classnameToSkip : classnamesToSkip)
		{
			if (classname.startsWith(classnameToSkip))
			{
				return true;
			}
		}

		return false;
	}

	private final void logException(final Exception e)
	{
		e.printStackTrace();

		this.exceptionsCount++;
	}

	public void assertNoExceptions()
	{
		if (exceptionsCount > 0)
		{
			Assert.fail(exceptionsCount + " exceptions found while checking all cached classes and methods. Check console");
		}
	}

}
