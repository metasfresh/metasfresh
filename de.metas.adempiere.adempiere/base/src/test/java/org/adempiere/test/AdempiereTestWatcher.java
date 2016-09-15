package org.adempiere.test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.util.Check;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Watches current test and dumps the database to console in case of failure.
 *
 * To include in your tests, you need to declare a public field like this:
 *
 * <pre>
 * &#64;Rule
 * public final {@link TestWatcher} testWatcher = new {@link AdempiereTestWatcher}()
 * </pre>
 */
public class AdempiereTestWatcher extends TestWatcher
{
	private static final Logger logger = LogManager.getLogger(AdempiereTestWatcher.class);

	/** Context variables to be printed to screen in case the test fails */
	private final Map<String, Object> context = new LinkedHashMap<>();

	/**
	 * Called after a test succeed.
	 *
	 * Does nothing at this level.
	 */
	@Override
	protected void succeeded(final Description description)
	{
		// nothing
	}

	/**
	 * Called after a test failed. It:
	 * <ul>
	 * <li>dump database content
	 * </ul>
	 */
	@Override
	protected void failed(final Throwable e, final Description description)
	{
		POJOLookupMap.get().dumpStatus("After test failed: " + description.getDisplayName());

		//
		// Dump retained context values
		for (final Entry<String, Object> entry : context.entrySet())
		{
			final String name = entry.getKey();
			final Object value = entry.getValue();
			System.out.println("\n" + name + ": " + value);
		}
	}

	/**
	 * Called after a test finished (successful or not). It:
	 * <ul>
	 * <li>clears database content
	 * </ul>
	 */
	@Override
	protected void finished(final Description description)
	{
		POJOLookupMap.get().clear();
		context.clear();
	}

	/**
	 * Put given variable to context.
	 * 
	 * In case the test will fail, all context variables will be printed to console.
	 * 
	 * @param name
	 * @param value
	 */
	public void putContext(final String name, final Object value)
	{
		Check.assumeNotEmpty(name, "name is not empty");
		context.put(name, value);
	}

	/**
	 * Convenient variant for {@link #putContext(String, Object)} which considers value's class name as the context variable <code>name</code>.
	 * 
	 * @param value value, not null.
	 */
	public void putContext(final Object value)
	{
		if (value == null)
		{
			logger.error("Cannot put a null value to context. This is a development error. Skipped for now", new Exception("TRACE"));
			return;
		}

		final String name = value.getClass().getName();
		putContext(name, value);
	}
}
