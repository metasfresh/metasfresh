package org.adempiere.test;

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
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Watches current test and dumps the database to console in case of failure.
 *
 * To include in your tests, you need to declare a public field like this:
 *
 * <pre>
 * @Rule
 * public final {@link TestWatcher} testWatcher = new {@link AdempiereTestWatcher}()
 * </pre>
 */
public class AdempiereTestWatcher extends TestWatcher
{
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
		//org.adempiere.ad.wrapper.POJOLookupMap.get().dumpStatus()
		POJOLookupMap.get().dumpStatus("After test failed: " + description.getDisplayName());
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
	}
}
