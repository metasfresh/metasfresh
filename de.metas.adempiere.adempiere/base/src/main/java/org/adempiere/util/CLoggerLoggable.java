package org.adempiere.util;

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

import java.util.logging.Level;

import org.compiere.util.CLogger;

/**
 * Wraps {@link CLogger} as {@link ILoggable}
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class CLoggerLoggable extends JULLoggable
{
	/**
	 * @param logger
	 * @param level the logging level to be used when {@link #addLog(String)} is called.
	 */
	public CLoggerLoggable(final CLogger logger, final Level level)
	{
		super(logger, level);
	}
}
