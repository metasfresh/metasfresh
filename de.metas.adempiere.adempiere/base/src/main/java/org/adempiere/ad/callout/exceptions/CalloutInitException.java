package org.adempiere.ad.callout.exceptions;

import org.adempiere.util.Check;

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
 * Thrown on any error during callouts load or initialization.
 * 
 * @author tsa
 */
public class CalloutInitException extends CalloutException
{
	public static final CalloutInitException wrapIfNeeded(final Throwable throwable)
	{
		Check.assumeNotNull(throwable, "throwable not null");
		
		if (throwable instanceof CalloutInitException)
		{
			return (CalloutInitException)throwable;
		}
		
		final Throwable cause = extractCause(throwable);
		if(cause != throwable)
		{
			return wrapIfNeeded(cause);
		}
		
		return new CalloutInitException(extractMessage(throwable), cause);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5929639632737615796L;

	public CalloutInitException(final String message)
	{
		super(message);
	}

	public CalloutInitException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
