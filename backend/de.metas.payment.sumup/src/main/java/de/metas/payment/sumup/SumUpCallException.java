package de.metas.payment.sumup;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/**
 * A failed call to the SumUp API.
 * <p>
 * {@link #isDefiniteRejection()} tells the caller whether SumUp certainly did not act on the request
 * (HTTP 4xx) or whether the outcome is unknown (5xx, timeout/IO error, unreadable response, or a failure in metasfresh
 * after SumUp accepted the request, see {@link #ofFailureAfterAccepted}).
 */
public class SumUpCallException extends AdempiereException
{
	private final boolean definiteRejection;
	@Nullable private final SumUpClientTransactionId clientTransactionId;

	public SumUpCallException(final Throwable cause, final boolean definiteRejection)
	{
		super(cause.getClass().getSimpleName() + ": " + extractMessage(cause), cause);
		this.definiteRejection = definiteRejection;
		this.clientTransactionId = null;
	}

	private SumUpCallException(
			@NonNull final String message,
			@NonNull final Throwable cause,
			@Nullable final SumUpClientTransactionId clientTransactionId)
	{
		super(message, cause);
		this.definiteRejection = false;
		this.clientTransactionId = clientTransactionId;
	}

	/**
	 * SumUp accepted the request (e.g. a reader checkout, which the reader may now charge), but metasfresh failed right
	 * after, e.g. while saving the SumUp transaction. The outcome is unknown, never a definite rejection. The message
	 * names SumUp's {@code client_transaction_id}, if known, so that the charge can be found in the SumUp dashboard even
	 * if metasfresh has no SumUp transaction for it.
	 */
	public static SumUpCallException ofFailureAfterAccepted(
			@NonNull final Throwable cause,
			@Nullable final SumUpClientTransactionId clientTransactionId)
	{
		final String clientTransactionIdStr = clientTransactionId != null ? clientTransactionId.getAsString() : "?";
		final SumUpCallException ex = new SumUpCallException(
				"SumUp accepted the request (client_transaction_id=" + clientTransactionIdStr + "), but metasfresh failed afterwards: "
						+ cause.getClass().getSimpleName() + ": " + extractMessage(cause),
				cause,
				clientTransactionId);
		ex.setParameter("SumUpClientTransactionId", clientTransactionIdStr);
		return ex;
	}

	/**
	 * @return SumUp's {@code client_transaction_id} of the accepted request, if this is a failure after SumUp accepted it
	 * ({@link #ofFailureAfterAccepted}) and the id is known; null otherwise
	 */
	@Nullable
	public SumUpClientTransactionId getClientTransactionId()
	{
		return clientTransactionId;
	}

	public boolean isDefiniteRejection()
	{
		return definiteRejection;
	}
}
