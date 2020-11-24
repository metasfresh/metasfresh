package de.metas.ui.web.process.adprocess;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.base.Stopwatch;

import de.metas.process.JavaProcess;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class All_WebuiProcessClassInfo_Test
{
	@ParameterizedTest
	@ArgumentsSource(ProcessClassArgumentsProvider.class)
	public void createWebuiProcessClassInfo(@NonNull final Class<?> processClass) throws Exception
	{
		WebuiProcessClassInfo.createWebuiProcessClassInfo(processClass);
	}

	//
	//
	//
	//
	//

	public static class ProcessClassArgumentsProvider implements ArgumentsProvider
	{
		@Override
		public Stream<? extends Arguments> provideArguments(final ExtensionContext context)
		{
			return provideClasses().map(Arguments::of);
		}

		private Stream<Class<? extends JavaProcess>> provideClasses()
		{
			final Stopwatch stopwatch = Stopwatch.createStarted();

			final Reflections reflections = new Reflections(new ConfigurationBuilder()
					.addUrls(ClasspathHelper.forClassLoader())
					.setScanners(new SubTypesScanner()));

			final Set<Class<? extends JavaProcess>> classes = reflections.getSubTypesOf(JavaProcess.class);

			if (classes.isEmpty())
			{
				throw new RuntimeException("No classes found. Might be because for some reason Reflections does not work correctly with maven surefire plugin."
						+ "\n See https://github.com/metasfresh/metasfresh/issues/4773.");
			}

			stopwatch.stop();
			System.out.println("Found " + classes.size() + " classes implementing " + JavaProcess.class + ". Took " + stopwatch + ". ");

			return classes.stream()
					.sorted(Comparator.comparing(Class::getName));
		}
	}

}
