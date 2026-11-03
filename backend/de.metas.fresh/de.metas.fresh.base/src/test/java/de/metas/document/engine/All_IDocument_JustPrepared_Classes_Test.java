/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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


package de.metas.document.engine;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MJournal;
import org.compiere.model.MOrder;
import org.eevolution.model.MPPOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.objenesis.ObjenesisStd;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the contract of {@link IDocument#resetEngineStateForRetry()} for every {@link IDocument} that keeps a "just prepared" flag:
 * it overrides the hook, the hook drops the per-action state, and {@link IDocument#completeIt()} clears the flag on every outcome.
 * <p>
 * The documents are instantiated without a constructor (Objenesis, so without DB access); the per-action fields are found by name.
 */
@SuppressWarnings("NewClassNamingConvention")
public class All_IDocument_JustPrepared_Classes_Test
{
	private static final ObjenesisStd OBJENESIS = new ObjenesisStd();
	private static final Duration COMPLETE_IT_TIMEOUT = Duration.ofSeconds(30);

	private static final Set<String> JUST_PREPARED_FIELD_NAMES = ImmutableSet.of("m_justPrepared", "justPrepared");

	/**
	 * The fields that hold state built up by a document action; the hook resets them to {@code false} / {@code null}, or (for a map) to an empty one.
	 * <p>
	 * Limitation: the fields are recognized by name only. A document that keeps per-action state in a field with another name is not checked
	 * until that name is added here; and a field with one of these names is expected to be reset even if it holds something else.
	 */
	private static final Set<String> PER_ACTION_FIELD_NAMES = ImmutableSet.of(
			"m_justPrepared", "justPrepared",
			"m_processMsg", "processMsg",
			"m_lines", "_lines", "m_taxes", "m_confirms",
			"m_book", "m_inout", "m_creditMemo", "m_inventory", "m_inventoryFrom", "m_inventoryTo", "m_inventoryInfo",
			"m_movement", "linesConcept");

	private static List<Class<?>> documentClassesWithFlag;

	@BeforeAll
	static void scanClasspath()
	{
		AdempiereTestHelper.get().init(); // unit test mode, so that the completeIt() calls below can't reach a database

		final Reflections reflections = new Reflections(new ConfigurationBuilder()
																.addUrls(ClasspathHelper.forClassLoader())
																.forPackages("de", "org.compiere", "org.eevolution")
																.setScanners(new SubTypesScanner()));

		documentClassesWithFlag = reflections.getSubTypesOf(IDocument.class)
				.stream()
				.filter(clazz -> findJustPreparedField(clazz).isPresent())
				.sorted(Comparator.comparing(Class::getName))
				.collect(Collectors.toList());

		System.out.println("Found " + documentClassesWithFlag.size() + " documents with a \"just prepared\" flag: " + documentClassesWithFlag);

		// make sure the scan works and covers the modules on this classpath
		assertThat(documentClassesWithFlag).contains(MOrder.class, MInvoice.class, MInOut.class, MJournal.class, MPPOrder.class, DocumentWrapper.class);
	}

	@Test
	void overrideResetEngineStateForRetry()
	{
		final List<String> classesWithoutHook = documentClassesWithFlag.stream()
				.filter(clazz -> !declaresMethod(clazz, "resetEngineStateForRetry"))
				.map(Class::getName)
				.collect(Collectors.toList());
		assertThat(classesWithoutHook).as("classes with a \"just prepared\" flag that don't override resetEngineStateForRetry()").isEmpty();
	}

	@Test
	void resetEngineStateForRetry_resetsThePerActionFields() throws Exception
	{
		final List<String> problems = new ArrayList<>();
		for (final Class<?> clazz : documentClassesWithFlag)
		{
			final Object document = allocateInstance(clazz);
			final List<Field> perActionFields = getPerActionFields(clazz);
			for (final Field field : perActionFields)
			{
				field.set(document, newNonDefaultValue(field));
			}

			((IDocument)document).resetEngineStateForRetry();

			for (final Field field : perActionFields)
			{
				final Object value = field.get(document);
				final boolean isReset = value == null
						|| Boolean.FALSE.equals(value)
						|| (value instanceof Map && ((Map<?, ?>)value).isEmpty());
				if (!isReset)
				{
					problems.add(clazz.getName() + "." + field.getName() + " = " + value);
				}
			}
		}
		assertThat(problems).as("per-action fields not reset by resetEngineStateForRetry()").isEmpty();
	}

	/**
	 * completeIt() is called with the flag set, so it skips its re-check; it then fails on the uninitialized instance, but must clear the flag nevertheless.
	 */
	@Test
	void completeIt_clearsTheFlagOnEveryOutcome() throws Exception
	{
		final List<String> problems = new ArrayList<>();
		for (final Class<?> clazz : documentClassesWithFlag)
		{
			final Object document = allocateInstance(clazz);
			final Field flag = findJustPreparedField(clazz).orElseThrow(IllegalStateException::new);
			flag.set(document, true);

			// a fresh thread per class, so that a hanging completeIt() affects only its own class
			final ExecutorService executor = Executors.newSingleThreadExecutor();
			try
			{
				final Future<?> completion = executor.submit(() -> {
					try
					{
						((IDocument)document).completeIt();
					}
					catch (final Throwable ignored)
					{
						// expected: the instance has no column values
					}
				});
				completion.get(COMPLETE_IT_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS);
			}
			catch (final TimeoutException ex)
			{
				problems.add(clazz.getName() + " (completeIt() did not return within " + COMPLETE_IT_TIMEOUT + ")");
				continue;
			}
			finally
			{
				executor.shutdownNow();
			}

			if (Boolean.TRUE.equals(flag.get(document)))
			{
				problems.add(clazz.getName());
			}
		}
		assertThat(problems).as("classes whose completeIt() does not clear the \"just prepared\" flag").isEmpty();
	}

	private static Optional<Field> findJustPreparedField(final Class<?> clazz)
	{
		return Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> JUST_PREPARED_FIELD_NAMES.contains(field.getName()))
				.peek(field -> field.setAccessible(true))
				.findFirst();
	}

	private static List<Field> getPerActionFields(final Class<?> clazz)
	{
		return Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()))
				.filter(field -> PER_ACTION_FIELD_NAMES.contains(field.getName()))
				.peek(field -> field.setAccessible(true))
				.collect(Collectors.toList());
	}

	private static boolean declaresMethod(final Class<?> clazz, final String methodName)
	{
		for (final Method method : clazz.getDeclaredMethods())
		{
			if (method.getName().equals(methodName) && method.getParameterCount() == 0)
			{
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object newNonDefaultValue(final Field field) throws Exception
	{
		final Class<?> type = field.getType();
		if (type == boolean.class)
		{
			return true;
		}
		if (type == String.class)
		{
			return "stale";
		}
		if (type.isArray())
		{
			return Array.newInstance(type.getComponentType(), 1);
		}
		if (List.class.isAssignableFrom(type))
		{
			return ImmutableList.of(new Object());
		}
		if (Map.class.isAssignableFrom(type))
		{
			final Map map = new Hashtable();
			map.put(1, new Object());
			return map;
		}
		return allocateInstance(type);
	}

	private static Object allocateInstance(final Class<?> clazz)
	{
		return OBJENESIS.newInstance(clazz);
	}
}
