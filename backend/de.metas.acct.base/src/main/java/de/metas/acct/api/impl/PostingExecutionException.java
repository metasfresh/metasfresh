package de.metas.acct.api.impl;

import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;

/**
 * Exception thrown when a document posting request orchestration fails.
 */
@SuppressWarnings("serial")
class PostingExecutionException extends AdempiereException
{
	/**
	 * Wraps given <code>throwable</code> as {@link PostingExecutionException}, if it's not already an {@link PostingExecutionException}.
	 *
	 * @param throwable
	 * @return {@link PostingExecutionException} or <code>null</code> if the throwable was null.
	 */
	public static final AdempiereException wrapIfNeeded(final Throwable throwable)
	{
		if (throwable == null)
		{
			return null;
		}
		else if (throwable instanceof PostingExecutionException)
		{
			return (PostingExecutionException)throwable;
		}
		else
		{
			final Throwable cause = extractCause(throwable);
			if (cause instanceof PostingExecutionException)
			{
				return (PostingExecutionException)cause;
			}
			else
			{
				final String message = extractMessage(cause);
				return new PostingExecutionException(message, cause);
			}
		}
	}

	public PostingExecutionException(final String message)
	{
		this(message, /* serverStackTrace */(String)null);
	}

	private PostingExecutionException(final String message, final String serverStackTrace)
	{
		super(buildMessage(message, serverStackTrace));
	}

	private PostingExecutionException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	private static final String buildMessage(final String message, final String serverStackTrace)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(!Check.isEmpty(message) ? message.trim() : "unknow error");

		if (!Check.isEmpty(serverStackTrace))
		{
			sb.append("\nServer stack trace: ").append(serverStackTrace.trim());
		}

		return sb.toString();
	}
}
