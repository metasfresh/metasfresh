package de.metas.ui.web.window;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Window miscellaneous constants.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
// NOTE to dev: please keep this class in the root package (e.g. de.metas.ui.web.window) because at least the "logger" depends on this
public final class WindowConstants
{
	/**
	 * Root logger for "window" functionality.
	 *
	 * Changing the level of this logger will affect all loggers.
	 */
	public static final Logger logger = LogManager.getLogger(WindowConstants.class.getPackage().getName());

	private WindowConstants()
	{
	}

	private static final AtomicBoolean protocolDebugging = new AtomicBoolean(false);

	public static boolean isProtocolDebugging()
	{
		return protocolDebugging.get();
	}

	public static void setProtocolDebugging(final boolean protocolDebugging)
	{
		WindowConstants.protocolDebugging.set(protocolDebugging);
	}
}
