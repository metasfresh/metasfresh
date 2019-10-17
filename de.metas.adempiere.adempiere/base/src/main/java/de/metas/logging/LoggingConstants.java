package de.metas.logging;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public final class LoggingConstants
{
	private LoggingConstants(){}

	/**
	 * Name of a system property that can be set via <code>-D</code> to specify the logging directory.<br>
	 * Takes precedence over {@link #ENV_VAR_LogDir}.
	 */
	public static final String SYSTEM_PROP_LogDir = "de.metas.logdir";

	/**
	 * Name of an environment variable that can be exported to specify the logging directory.<br>
	 * The environment variable can be overridden by setting {@link #SYSTEM_PROP_LogDir},
	 */
	public static final String ENV_VAR_LogDir = "METASFRESH_LOG_DIRECTORY";

	public static final String DEFAULT_LogFilePrefix = "metasfresh";

	public static final String DEFAULT_LogFileDatePattern = "%d{yyyy-MM-dd}_%d{HHmmss,aux}";
}
