package de.metas.util.exceptions;

import java.io.Serial;
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
		else if (e instanceof ServicesException exception)
		{
			return exception;
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
	@Serial
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
