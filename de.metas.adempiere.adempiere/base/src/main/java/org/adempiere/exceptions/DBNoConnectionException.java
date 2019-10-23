package org.adempiere.exceptions;

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


/**
 * Exception thrown where no database connection was found.
 * 
 * @author tsa
 *
 */
public class DBNoConnectionException extends DBException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2725091243007392188L;

	public static final DBNoConnectionException wrapIfNeeded(final Throwable throwable)
	{
		if (throwable == null)
		{
			return null;
		}
		else if (throwable instanceof DBNoConnectionException)
		{
			return (DBNoConnectionException)throwable;
		}
		else
		{
			return new DBNoConnectionException(throwable.getLocalizedMessage(), throwable);
		}

	}

	private static final String MSG = "@NoDBConnection@";

	public DBNoConnectionException()
	{
		super(MSG);
	}

	public DBNoConnectionException(final String additionalMessage)
	{
		super(MSG + ": " + additionalMessage);
	}

	public DBNoConnectionException(final String additionalMessage, final Throwable cause)
	{
		super(MSG + ": " + additionalMessage, cause);
	}

	private DBNoConnectionException(Throwable cause)
	{
		super(MSG, cause);
	}
}
