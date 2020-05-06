package de.metas.migration.applier.impl;

/*
 * #%L
 * de.metas.migration.base
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

import org.junit.Ignore;

/**
 * Manual test used to check how an {@link SwingUIScriptsApplierListener#uiAsk(String, String, Object[], Object)} window is displayed.
 *
 * @author tsa
 *
 */
@Ignore
public class SwingUIScriptsApplierListenerManualTest
{
	public static void main(final String[] args)
	{
		final Object[] options = new Object[] { "option 1", "option 2", "option 3" };
		final Object defaultOption = options[1]; // option 2
		final Object result = SwingUIScriptsApplierListener.uiAsk("test title", "test message", options, defaultOption);

		System.out.println("Result: " + result);
	}
}
