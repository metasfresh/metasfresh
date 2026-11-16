package de.metas.payment.sumup;

import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Result of {@link SumUpService#importAcceptedCheckout}: the (existing or newly saved) transaction, or "not found" (SumUp
 * does not know the transaction), or the error of the failed SumUp lookup, e.g. an outage. Nothing is saved in the last two cases.
 */
@Value
public class SumUpImportResult
{
	@Nullable SumUpTransaction transaction;
	boolean isNotFound;
	@Nullable Exception lookupError;

	private SumUpImportResult(@Nullable final SumUpTransaction transaction, final boolean isNotFound, @Nullable final Exception lookupError)
	{
		this.transaction = transaction;
		this.isNotFound = isNotFound;
		this.lookupError = lookupError;
	}

	public static SumUpImportResult ofTransaction(@NonNull final SumUpTransaction transaction) {return new SumUpImportResult(transaction, false, null);}

	public static SumUpImportResult notFound() {return new SumUpImportResult(null, true, null);}

	public static SumUpImportResult ofLookupError(@NonNull final Exception lookupError) {return new SumUpImportResult(null, false, lookupError);}
}
