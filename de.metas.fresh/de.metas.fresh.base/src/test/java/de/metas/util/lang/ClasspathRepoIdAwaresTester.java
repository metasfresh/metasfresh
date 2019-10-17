package de.metas.util.lang;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class ClasspathRepoIdAwaresTester
{
	private static final String ALL = "*";

	private final ObjectMapper jsonMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	private int exceptionsCount = 0;

	private final HashSet<String> skip_ofRepoId_classNames = new HashSet<>();
	private final HashSet<String> skip_ofRepoIdOrNull_classNames = new HashSet<>();
	private final HashSet<String> skip_serializeAsNumber_classNames = new HashSet<>();

	public ClasspathRepoIdAwaresTester skip(@NonNull final Class<?> clazz)
	{
		return skip(clazz.getName());
	}

	public ClasspathRepoIdAwaresTester skip(@NonNull final String className)
	{
		skip_ofRepoId_classNames.add(className);
		skip_ofRepoIdOrNull_classNames.add(className);
		skip_serializeAsNumber_classNames.add(className);
		return this;
	}

	public ClasspathRepoIdAwaresTester skip_serializeAsNumber_tests()
	{
		skip_serializeAsNumber_classNames.add(ALL);
		return this;
	}

	public ClasspathRepoIdAwaresTester skip_serializeAsNumber_tests(@NonNull final String className)
	{
		skip_serializeAsNumber_classNames.add(className);
		return this;
	}

	private boolean isSkipSerializeAsNumberTests(@NonNull final String className)
	{
		return skip_serializeAsNumber_classNames.contains(className)
				|| skip_serializeAsNumber_classNames.contains(ALL);
	}

	public void test()
	{
		final Reflections reflections = new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forClassLoader())
				.setScanners(new SubTypesScanner()));

		final Set<Class<? extends RepoIdAware>> classes = reflections.getSubTypesOf(RepoIdAware.class);
		System.out.println("Found " + classes.size() + " classes implementing " + RepoIdAware.class);

		if (classes.isEmpty())
		{
			throw new RuntimeException("No classes found. Might be because for some reason Reflections does not work correctly with maven surefire plugin."
					+ "\n See https://github.com/metasfresh/metasfresh/issues/4773.");
		}

		for (final Class<? extends RepoIdAware> repoIdClass : classes)
		{
			testRepoIdAwareClass(repoIdClass);
		}

		assertNoExceptions();
	}

	private void testRepoIdAwareClass(@NonNull final Class<? extends RepoIdAware> repoIdClass)
	{
		final String className = repoIdClass.getName();

		//
		// Make sure ofRepoId method is implemented - needed for things like java process params binding
		if (!skip_ofRepoId_classNames.contains(className))
		{
			testAndLogException(() -> RepoIdAwares.ofRepoId(100, repoIdClass));
			testEquals(repoIdClass);
		}
		else
		{
			System.out.println("Skip testing ofRepoId for " + repoIdClass);
		}

		//
		// Make sure ofRepoIdOrNull method is implemented - needed for things like java process params binding
		if (!skip_ofRepoIdOrNull_classNames.contains(className))
		{
			testAndLogException(() -> RepoIdAwares.ofRepoIdOrNull(100, repoIdClass));
		}
		else
		{
			System.out.println("Skip testing ofRepoIdOrNull for " + repoIdClass);
		}

		//
		// Serialization
		if (!isSkipSerializeAsNumberTests(className))
		{
			testSerializableAsNumber(repoIdClass);
		}
		else
		{
			System.out.println("Skip testSerializableAsNumber for " + repoIdClass);
		}
	}

	private void testEquals(Class<? extends RepoIdAware> repoIdClass)
	{
		try
		{
			final RepoIdAware repoId1 = RepoIdAwares.ofRepoId(100, repoIdClass);
			final RepoIdAware repoId2 = RepoIdAwares.ofRepoId(100, repoIdClass);
			if (!Objects.equals(repoId1, repoId2))
			{
				throw Check.newException(repoIdClass + ": " + repoId1 + " not equals with " + repoId2);
			}
		}
		catch (final Exception ex)
		{
			logException(ex);
		}

	}

	private void testSerializableAsNumber(@NonNull final Class<? extends RepoIdAware> repoIdClass)
	{
		try
		{
			final RepoIdAware repoId = RepoIdAwares.ofRepoId(100, repoIdClass);
			final String json = jsonMapper.writeValueAsString(repoId);
			final String expectedJson = String.valueOf(repoId.getRepoId());
			if (!Objects.equals(json, expectedJson))
			{
				throw Check.newException(repoIdClass + ": Got wrong JSON for " + repoId + ". Expected `" + expectedJson + "` but got `" + json + "`");
			}

			final RepoIdAware repoId2 = jsonMapper.readValue(json, repoIdClass);
			if (!Objects.deepEquals(repoId, repoId2))
			{
				throw Check.newException(repoIdClass + ": Got wrong deserialization object for JSON `" + json + "`. Expected `" + repoId + "` but got `" + repoId2 + "`");
			}
		}
		catch (final Exception ex)
		{
			logException(ex);
		}
	}

	private void testAndLogException(@NonNull final Runnable runnable)
	{
		try
		{
			runnable.run();
		}
		catch (final Exception ex)
		{
			logException(ex);
		}
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
