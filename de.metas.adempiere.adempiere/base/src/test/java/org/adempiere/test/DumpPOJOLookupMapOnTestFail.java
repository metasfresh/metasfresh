package org.adempiere.test;

import java.util.Optional;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Watches current test and dumps the database to console in case of failure.
 *
 * To include in your jupiter (junit-5) tests, you need to annotate you test class like this
 *
 * <pre>
  &#64;ExtendWith(DumpPOJOLookupMapOnTestFail.class)
 * </pre>
 */
public class DumpPOJOLookupMapOnTestFail implements org.junit.jupiter.api.extension.TestWatcher
{
	/**
	 * Called after a test failed. It:
	 * <ul>
	 * <li>dump database content
	 * </ul>
	 */
	@Override
	public void testFailed(ExtensionContext extensionContext, Throwable cause)
	{
		POJOLookupMap.get().dumpStatus("After test failed: " + extensionContext.getDisplayName());
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void testDisabled(ExtensionContext context, Optional<String> reason)
	{
		// does nothing
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void testSuccessful(ExtensionContext context)
	{
		// does nothing
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void testAborted(ExtensionContext context, Throwable cause)
	{
		// does nothing
	}
}
