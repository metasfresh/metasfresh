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

import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MJournal;
import org.compiere.model.MOrder;
import org.eevolution.model.MPPOrder;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Every {@link IDocument} that keeps a "just prepared" flag also keeps per-action state, which has to be dropped before a document action
 * is retried on the same instance; so it has to override {@link IDocument#resetEngineStateForRetry()}.
 */
@SuppressWarnings("NewClassNamingConvention")
public class All_IDocument_JustPrepared_Classes_Test
{
	private static final List<String> JUST_PREPARED_FIELD_NAMES = Arrays.asList("m_justPrepared", "justPrepared");

	@Test
	void documentsWithJustPreparedFlag_overrideResetEngineStateForRetry()
	{
		final Reflections reflections = new Reflections(new ConfigurationBuilder()
																.addUrls(ClasspathHelper.forClassLoader())
																.forPackages("de", "org.compiere", "org.eevolution")
																.setScanners(new SubTypesScanner()));

		final List<Class<?>> classesWithFlag = reflections.getSubTypesOf(IDocument.class)
				.stream()
				.filter(All_IDocument_JustPrepared_Classes_Test::declaresJustPreparedFlag)
				.collect(Collectors.toList());

		// make sure the scan works and covers the modules on this classpath
		assertThat(classesWithFlag).contains(MOrder.class, MInvoice.class, MInOut.class, MJournal.class, MPPOrder.class, DocumentWrapper.class);

		final List<String> classesWithoutHook = classesWithFlag.stream()
				.filter(clazz -> !overridesResetHook(clazz))
				.map(Class::getName)
				.sorted()
				.collect(Collectors.toList());
		assertThat(classesWithoutHook).as("classes with a \"just prepared\" flag that don't override resetEngineStateForRetry()").isEmpty();
	}

	private static boolean declaresJustPreparedFlag(final Class<?> clazz)
	{
		final Set<String> fieldNames = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toSet());
		return JUST_PREPARED_FIELD_NAMES.stream().anyMatch(fieldNames::contains);
	}

	private static boolean overridesResetHook(final Class<?> clazz)
	{
		try
		{
			clazz.getDeclaredMethod("resetEngineStateForRetry");
			return true;
		}
		catch (final NoSuchMethodException ex)
		{
			return false;
		}
	}
}
