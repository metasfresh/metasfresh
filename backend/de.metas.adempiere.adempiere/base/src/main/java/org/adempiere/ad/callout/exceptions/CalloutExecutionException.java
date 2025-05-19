package org.adempiere.ad.callout.exceptions;

import de.metas.util.Check;

import java.io.Serial;

public class CalloutExecutionException extends CalloutException
{
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -1716015503981966091L;

	public static CalloutExecutionException wrapIfNeeded(final Throwable throwable)
	{
		Check.assumeNotNull(throwable, "throwable not null");
		
		if (throwable instanceof CalloutExecutionException exception)
		{
			return exception;
		}
		
		final Throwable cause = extractCause(throwable);
		if(cause != throwable)
		{
			return wrapIfNeeded(cause);
		}
		
		return new CalloutExecutionException(extractMessage(throwable), cause);
	}
	
	public CalloutExecutionException(final String message)
	{
		super(message);
	}

	private CalloutExecutionException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
