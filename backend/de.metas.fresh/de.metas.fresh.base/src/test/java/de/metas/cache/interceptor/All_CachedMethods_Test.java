package de.metas.cache.interceptor;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.proxy.impl.JavaAssistInterceptor;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.base.Stopwatch;

import de.metas.util.IService;
import lombok.NonNull;
import lombok.ToString;

/**
 * {@link Cached} integration test: makes sure all classes are valid
 * 
 * @author tsa
 *
 */
public class All_CachedMethods_Test
{
	private static final SkipRules skipRules = new SkipRules()
			// Classes which does not implement services => those won't be cached
			// FIXME: we have to fix the underlying problem
			.skipIfClassnameStartsWith("org.compiere.model.MReference")
			.skipIfClassnameStartsWith("org.compiere.model.M_Element")
			.skipIfClassnameStartsWith("de.metas.adempiere.form.terminal.PropertiesPanelModelConfigurator")
			.skipIfClassnameStartsWith("org.adempiere.model.MFreightCost")
			.skipIfClassnameStartsWith("de.metas.dpd.model.MDPDDepot")
			.skipIfClassnameStartsWith("de.metas.dpd.model.MDPDCountry")
			.skipIfClassnameStartsWith("de.metas.dpd.model.MDPDScanCode")
			.skipIfClassnameStartsWith("de.metas.dpd.model.MDPDFileInfo")
			.skipIfClassnameStartsWith("de.metas.banking.misc.ImportBankstatementCtrl")
			.skipIfClassnameStartsWith("de.metas.handlingunits.client.terminal.editor.model.impl.HUKeyNameBuilder")
			// Legacy code
			.skipIfClassnameStartsWith("de.metas.dpd.process.ImportStatusData")
			// Tests:
			.skipIfClassnameStartsWith("de.metas.cache.interceptor.testservices.")
			.skipIfClassnameStartsWith("de.metas.cache.interceptor.CachedMethodDescriptorTest$TestClass")
	//
	;

	private static JavaAssistInterceptor javaAssistInterceptor;
	private static Set<Class<?>> testedClasses;

	@BeforeAll
	public static void beforeAll()
	{
		testedClasses = new HashSet<>();
		javaAssistInterceptor = new JavaAssistInterceptor();
	}

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@ParameterizedTest
	@ArgumentsSource(CachedMethodArgumentsProvider.class)
	public void test(@NonNull final Method method) throws Exception
	{
		skipRules.assumeNotSkipped(method);

		final Class<?> methodClass = method.getDeclaringClass();
		testClass(methodClass);

		new CachedMethodDescriptor(method);
	}

	private final void testClass(final Class<?> clazz)
	{
		// Skip if already tested
		if (!testedClasses.add(clazz))
		{
			return;
		}

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

	//
	//
	//
	//
	//

	@ToString
	private static class SkipRules
	{
		private final Set<String> classnamesToSkip = new HashSet<>();

		public void assumeNotSkipped(final Method method)
		{
			final Class<?> methodClass = method.getDeclaringClass();
			final boolean skipped = isSkipClass(methodClass);
			Assumptions.assumeTrue(!skipped, "skipped");
		}

		private final boolean isSkipClass(final Class<?> clazz)
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

		public SkipRules skipIfClassnameStartsWith(final String classnamePrefix)
		{
			classnamesToSkip.add(classnamePrefix);
			return this;
		}
	}

	//
	//
	//
	//
	//
	//

	public static class CachedMethodArgumentsProvider implements ArgumentsProvider
	{
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context)
		{
			return provideMethods().map(Arguments::of);
		}

		private Stream<Method> provideMethods()
		{
			final Stopwatch stopwatch = Stopwatch.createStarted();

			final Reflections reflections = new Reflections(new ConfigurationBuilder()
					.addUrls(ClasspathHelper.forClassLoader())
					.setScanners(new MethodAnnotationsScanner()));

			final Set<Method> methods = reflections.getMethodsAnnotatedWith(Cached.class);

			stopwatch.stop();
			System.out.println("Found " + methods.size() + " methods annotated with " + Cached.class + ". Took " + stopwatch);

			if (methods.isEmpty())
			{
				throw new AdempiereException("No classes found. Might be because for some reason Reflections does not work correctly with maven surefire plugin."
						+ "\n See https://github.com/metasfresh/metasfresh/issues/4773.");
			}

			return methods.stream()
					.sorted(Comparator.comparing(this::extractFQName));
		}

		private String extractFQName(final Method method)
		{
			return method.getDeclaringClass().getName()
					+ "." + method.getName();
		}

	}
}
