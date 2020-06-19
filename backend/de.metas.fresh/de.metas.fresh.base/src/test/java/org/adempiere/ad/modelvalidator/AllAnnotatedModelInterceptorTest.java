package org.adempiere.ad.modelvalidator;

import java.util.stream.Stream;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * Makes sure all annotated model interceptors found in classpath are correctly defined.
 *
 * @author tsa
 *
 */
public class AllAnnotatedModelInterceptorTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		LogManager.setLevel(Level.INFO); // avoid the log being flooded with DEBUG messages when the classpath is scanned
	}

	@ParameterizedTest
	@ArgumentsSource(ModelInterceptorArgumentsProvider.class)
	public void instantiateAndValidateModelInterceptor(@NonNull final Class<?> modelInterceptorClass) throws Exception
	{
		new ClasspathAnnotatedModelInterceptorTester()
				.failOnFirstError(true)
				.testClass(modelInterceptorClass);
	}

	public static class ModelInterceptorArgumentsProvider implements ArgumentsProvider
	{
		@Override
		public Stream<? extends Arguments> provideArguments(final ExtensionContext context)
		{
			return provideClasses().map(Arguments::of);
		}

		public Stream<Class<?>> provideClasses()
		{
			final ClasspathAnnotatedModelInterceptorTester provider = new ClasspathAnnotatedModelInterceptorTester();
			return provider.getAllClasses().stream();
		}
	}

}
