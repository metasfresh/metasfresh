package de.metas.migration.sql.postgresql;

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

import java.io.File;

public class PgPassParseException extends RuntimeException
{

	/**
	 *
	 */
	private static final long serialVersionUID = 3185121240527253819L;

	public static PgPassParseException convert(final Throwable exception)
	{
		if (exception == null)
		{
			throw new IllegalArgumentException("exception cannot be null");
		}
		else if (exception instanceof PgPassParseException)
		{
			return (PgPassParseException)exception;
		}
		else
		{
			return new PgPassParseException(exception.getMessage(), exception);
		}
	}

	private File configFile;

	private int configLineNo = -1;

	public PgPassParseException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public PgPassParseException(final String message)
	{
		super(message);
	}

	public PgPassParseException setConfigFile(final File configFile)
	{
		this.configFile = configFile;
		return this;
	}

	public File getConfigFile()
	{
		return configFile;
	}

	public PgPassParseException setConfigLineNo(final int configLineNo)
	{
		this.configLineNo = configLineNo;
		return this;
	}

	public int getLineNo()
	{
		return configLineNo;
	}

	@Override
	public String getMessage()
	{
		final StringBuilder sb = new StringBuilder();

		sb.append("Parse error: ");
		final String message = super.getMessage();
		if (message != null && !message.isEmpty())
		{
			sb.append(message);
		}
		else
		{
			sb.append("unknown");
		}

		if (configFile != null)
		{
			sb.append("\nConfig file: ").append(configFile);
		}

		if (configLineNo > 0)
		{
			sb.append("\nLine: ").append(configLineNo);
		}

		return sb.toString();
	}
}
