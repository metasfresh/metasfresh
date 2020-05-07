package org.adempiere.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Ignore;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.collect.ImmutableSet;

import lombok.NonNull;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Ignore
public class AvailableSingletonServicesTester
{
	public static final AvailableSingletonServicesTester newInstance()
	{
		return new AvailableSingletonServicesTester();
	}

	private final List<Predicate<String>> serviceInterfaceClassnamesSkipPredicates = new ArrayList<>();

	private final List<Class<? extends ISingletonService>> failedToLoadServiceInterfaceClasses = new ArrayList<>();
	private int testCount = 0;

	private AvailableSingletonServicesTester()
	{
	}

	public AvailableSingletonServicesTester skipServiceInterface(@NonNull final String serviceInterfaceClassnameToSkip)
	{
		serviceInterfaceClassnamesSkipPredicates.add(classname -> serviceInterfaceClassnameToSkip.equals(classname));
		return this;
	}

	public AvailableSingletonServicesTester skipServiceInterface(@NonNull final Class<? extends ISingletonService> serviceInterfaceClass)
	{
		skipServiceInterface(serviceInterfaceClass.getName());
		return this;
	}

	public AvailableSingletonServicesTester skipServiceInterfaceIfStartsWith(@NonNull final String classnamePrefix)
	{
		serviceInterfaceClassnamesSkipPredicates.add(classname -> classname.startsWith(classnamePrefix));
		return this;
	}

	public void test()
	{
		final Set<Class<? extends ISingletonService>> singletonServiceInterfaces = getSingletonServiceInterfaces();
		singletonServiceInterfaces.stream()
				.filter(this::isEligible)
				.forEach(this::test);

		System.out.println("Tested " + testCount + " service interfaces");

		assertNoExceptions();
	}

	private boolean isEligible(final Class<? extends ISingletonService> serviceInterfaceClass)
	{
		final String serviceInterfaceClassname = serviceInterfaceClass.getName();
		for (final Predicate<String> classnameSkipPredicate : serviceInterfaceClassnamesSkipPredicates)
		{
			if (classnameSkipPredicate.test(serviceInterfaceClassname))
			{
				return false;
			}
		}

		return true;
	}

	private void test(final Class<? extends ISingletonService> serviceInterfaceClass)
	{
		try
		{
			final ISingletonService serviceImpl = Services.get(serviceInterfaceClass);
			if (serviceImpl == null)
			{
				throw new RuntimeException("No service implementation found for " + serviceInterfaceClass);
			}
		}
		catch (final Throwable ex)
		{
			logException(ex, serviceInterfaceClass);
		}
		finally
		{
			testCount++;
		}
	}

	private void logException(final Throwable ex, final Class<? extends ISingletonService> serviceInterfaceClass)
	{
		ex.printStackTrace();

		failedToLoadServiceInterfaceClasses.add(serviceInterfaceClass);
	}

	public void assertNoExceptions()
	{
		final int exceptionsCount = failedToLoadServiceInterfaceClasses.size();
		if (exceptionsCount > 0)
		{
			System.err.println("Failed to load following services: --------------------------------------------------------------");
			failedToLoadServiceInterfaceClasses.forEach(System.err::println);

			Assert.fail(exceptionsCount + " exceptions found while checking all cached classes and methods. Check console");
		}
	}

	private Set<Class<? extends ISingletonService>> getSingletonServiceInterfaces()
	{
		final Reflections reflections = new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forClassLoader())
				.setScanners(new SubTypesScanner()));

		return reflections.getSubTypesOf(ISingletonService.class)
				.stream()
				.filter(Class::isInterface)
				.collect(ImmutableSet.toImmutableSet());
	}
}
