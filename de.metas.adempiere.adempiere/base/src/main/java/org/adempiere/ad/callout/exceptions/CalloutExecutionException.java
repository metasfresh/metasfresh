package org.adempiere.ad.callout.exceptions;

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


import java.lang.reflect.InvocationTargetException;

import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.util.Check;

public class CalloutExecutionException extends CalloutException
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1716015503981966091L;

	public static final CalloutExecutionException of(final Throwable throwable)
	{
		Check.assumeNotNull(throwable, "throwable not null");
		
		if (throwable instanceof CalloutExecutionException)
		{
			return (CalloutExecutionException)throwable;
		}
		else if (throwable instanceof InvocationTargetException)
		{
			final InvocationTargetException ite = (InvocationTargetException)throwable;
			final Throwable target = ite.getTargetException();
			if (target != null)
			{
				return of(target);
			}
		}
		
		return new CalloutExecutionException(extractMessage(throwable), throwable);
	}
	
	public CalloutExecutionException(final String message)
	{
		super(message);
	}

	public CalloutExecutionException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public CalloutExecutionException(final ICalloutInstance method, final String message, final Throwable cause)
	{
		this(message, cause);
		setCalloutInstance(method);
	}

	public CalloutExecutionException(final ICalloutInstance method, final String message)
	{
		super(message);
		setCalloutInstance(method);
	}
}
