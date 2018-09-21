package org.adempiere.ad.modelvalidator;

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


import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.junit.Assert;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;

/**
 * Checks all {@link Interceptor} or {@link Validator} annotated classes if they are correctly defined.
 * 
 * @author tsa
 *
 */
public class ClasspathAnnotatedModelInterceptorTester
{
	private final Set<Class<?>> testedClasses = new HashSet<>();

	/** Classnames to skip while checking (aka. known issues) */
	private final Set<String> classnamesToSkip = new HashSet<>();

	private final Map<Class<?>, Supplier<?>> instanceProviders = new HashMap<>();

	private int exceptionsCount = 0;

	public ClasspathAnnotatedModelInterceptorTester()
	{
		super();
	}

	public void test()
	{
		final Reflections reflections = new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forClassLoader())
				.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner())
				);

		final Set<Class<?>> classes_withValidator = reflections.getTypesAnnotatedWith(Validator.class);
		System.out.println("Found " + classes_withValidator.size() + " classes annotated with " + Validator.class);

		final Set<Class<?>> classes_withInterceptor = reflections.getTypesAnnotatedWith(Interceptor.class);
		System.out.println("Found " + classes_withInterceptor.size() + " classes annotated with " + Interceptor.class);

		final Set<Class<?>> classes = ImmutableSet.<Class<?>> builder()
				.addAll(classes_withValidator)
				.addAll(classes_withInterceptor)
				.build();
		System.out.println("=> " + classes.size() + " classes to test");

		for (final Class<?> clazz : classes)
		{
			testClass(clazz);
		}

		assertNoExceptions();
	}

	public final ClasspathAnnotatedModelInterceptorTester skipIfClassnameStartsWith(final String classnamePrefix)
	{
		classnamesToSkip.add(classnamePrefix);
		return this;
	}

	public final <T> ClasspathAnnotatedModelInterceptorTester useInstanceProvider(final Class<T> clazz, final Supplier<T> instanceProvider)
	{
		Check.assumeNotNull(clazz, "clazz not null");
		Check.assumeNotNull(instanceProvider, "instanceProvider not null");
		instanceProviders.put(clazz, instanceProvider);
		return this;
	}

	private final void testClass(final Class<?> clazz)
	{
		if (skipClass(clazz))
		{
			System.out.println("Skipped: " + clazz);
			return;
		}

		// Skip if already tested
		if (!testedClasses.add(clazz))
		{
			return;
		}

		// System.out.println("Testing: " + clazz);
		try
		{
			final Object annotatedObject = createAnnotatedObjectInstance(clazz);

			// shall fail if something is not OK:
			new AnnotatedModelInterceptor(annotatedObject);
		}
		catch (Exception ex)
		{
			logException(ex);
		}
		finally
		{
			//
		}

	}

	private Object createAnnotatedObjectInstance(Class<?> clazz)
	{
		try
		{
			//
			// Check for instance provider if any
			Supplier<?> instanceProvider = instanceProviders.get(clazz);
			if (instanceProvider != null)
			{
				final Object instance = instanceProvider.get();
				Check.assumeNotNull(instance, "instance not null");
				return instance;
			}

			//
			// Check for "instance" static field,
			// because we have some model validators which are singletons
			try
			{
				return clazz.getField("instance").get(null);
			}
			catch (NoSuchFieldException e)
			{
				// ignore it
			}

			// Instantiate it using the default constructor
			final Constructor<?> ctor = clazz.getDeclaredConstructor();
			if (!ctor.isAccessible())
			{
				ctor.setAccessible(true);
			}
			return ctor.newInstance();
		}
		catch (Exception e)
		{
			throw new AdempiereException("Failed to create instance for " + clazz, e);
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
			Assert.fail(exceptionsCount + " exceptions found while checking all classes. Check console");
		}
	}
}
