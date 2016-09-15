package org.adempiere.ad.callout.exceptions;

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
		
		final Throwable cause = extractCause(throwable);
		if(cause != throwable)
		{
			return of(cause);
		}
		
		return new CalloutExecutionException(extractMessage(throwable), cause);
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
