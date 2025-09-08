package org.adempiere.ad.callout.exceptions;

import de.metas.util.Check;

public class CalloutExecutionException extends CalloutException
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1716015503981966091L;

	public static CalloutExecutionException wrapIfNeeded(final Throwable throwable)
	{
		Check.assumeNotNull(throwable, "throwable not null");
		
		if (throwable instanceof CalloutExecutionException)
		{
			return (CalloutExecutionException)throwable;
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
