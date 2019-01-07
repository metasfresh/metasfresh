package de.metas.util.exceptions;

/*
 * #%L
 * de.metas.util
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


import java.lang.reflect.InvocationTargetException;

import com.google.common.util.concurrent.UncheckedExecutionException;

/**
 * Exception thrown when a service could not be loaded.
 */
public class ServicesException extends RuntimeException
{
	public static final ServicesException wrapIfNeeded(final Throwable e)
	{
		if (e == null)
		{
			return new ServicesException("Unknown service exception");
		}
		else if (e instanceof ServicesException)
		{
			return (ServicesException)e;
		}
		else if ((e instanceof InvocationTargetException) && (e.getCause() != null))
		{
			return wrapIfNeeded(e.getCause());
		}
		else if ((e instanceof UncheckedExecutionException) && (e.getCause() != null))
		{
			return wrapIfNeeded(e.getCause());
		}
		else
		{
			return new ServicesException(e);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6076066735401985642L;

	public ServicesException(String message, Throwable cause)
	{
		super(message, cause);

	}

	public ServicesException(String message)
	{
		super(message);
	}

	public ServicesException(Throwable cause)
	{
		super(cause);
	}

}
