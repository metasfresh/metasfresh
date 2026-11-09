package de.metas.payment.sumup;

import org.adempiere.exceptions.AdempiereException;

/**
 * A failed call to the SumUp API.
 * <p>
 * {@link #isDefiniteRejection()} tells the caller whether SumUp certainly did not act on the request
 * (HTTP 4xx) or whether the outcome is unknown (5xx, timeout/IO error, unreadable response).
 */
public class SumUpCallException extends AdempiereException
{
	private final boolean definiteRejection;

	public SumUpCallException(final Throwable cause, final boolean definiteRejection)
	{
		super(cause.getClass().getSimpleName() + ": " + extractMessage(cause), cause);
		this.definiteRejection = definiteRejection;
	}

	public boolean isDefiniteRejection()
	{
		return definiteRejection;
	}
}
