package de.metas.util.lang;

import java.util.Objects;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

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
public class ClasspathReferenceListAwareEnumsTester
{
	private int exceptionsCount = 0;

	public void test()
	{
		final Reflections reflections = new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forClassLoader())
				.setScanners(new SubTypesScanner()));

		final Set<Class<? extends ReferenceListAwareEnum>> classes = reflections.getSubTypesOf(ReferenceListAwareEnum.class);
		System.out.println("Found " + classes.size() + " classes implementing " + ReferenceListAwareEnum.class);

		if (classes.isEmpty())
		{
			throw new RuntimeException("No classes found. Might be because for some reason Reflections does not work correctly with maven surefire plugin."
					+ "\n See https://github.com/metasfresh/metasfresh/issues/4773.");
		}

		for (final Class<? extends ReferenceListAwareEnum> clazz : classes)
		{
			testClass(clazz);
		}

		assertNoExceptions();

	}

	private final void logException(@NonNull final Class<? extends ReferenceListAwareEnum> clazz, final String message)
	{
		final Throwable cause = null;
		logException(clazz, message, cause);
	}

	private final void logException(@NonNull final Class<? extends ReferenceListAwareEnum> clazz, final String message, final Throwable cause)
	{
		final RuntimeException ex = Check.newException(clazz + ": " + message);
		if (cause != null)
		{
			ex.initCause(cause);
		}
		logException(ex);
	}

	private final void logException(final Exception e)
	{
		e.printStackTrace();

		exceptionsCount++;
	}

	public void assertNoExceptions()
	{
		if (exceptionsCount > 0)
		{
			Assert.fail(exceptionsCount + " exceptions found while checking all cached classes and methods. Check console");
		}
	}

	private void testClass(final Class<? extends ReferenceListAwareEnum> clazz)
	{
		final Set<? extends ReferenceListAwareEnum> values = ReferenceListAwareEnums.values(clazz);
		if (values == null || values.isEmpty())
		{
			logException(clazz, "No enum constants found");
			return;
		}

		for (final ReferenceListAwareEnum valueExpected : values)
		{
			final ReferenceListAwareEnum valueActual;
			try
			{
				valueActual = ReferenceListAwareEnums.ofCode(valueExpected.getCode(), clazz);
			}
			catch (final Exception ex)
			{
				logException(clazz, "Failed calling ofCode method", ex);
				break;
			}

			if (!Objects.equals(valueExpected, valueActual))
			{
				logException(clazz, "Expected `" + valueExpected + "` but got `" + valueActual + "`");
			}

			if (valueExpected != valueActual)
			{
				logException(clazz, "Not identical: expected=`" + valueExpected + "`, actual=`" + valueActual + "`");
			}

		}
	}

}
