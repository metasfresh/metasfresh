package de.metas.payment.sumup;

import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Result of {@link SumUpService#importAcceptedCheckout}: either the (existing or newly saved) transaction, or the error of
 * the failed SumUp lookup (nothing is saved then).
 */
@Value
public class SumUpImportResult
{
	@Nullable SumUpTransaction transaction;
	@Nullable Exception lookupError;

	private SumUpImportResult(@Nullable final SumUpTransaction transaction, @Nullable final Exception lookupError)
	{
		this.transaction = transaction;
		this.lookupError = lookupError;
	}

	public static SumUpImportResult ofTransaction(@NonNull final SumUpTransaction transaction) {return new SumUpImportResult(transaction, null);}

	public static SumUpImportResult ofLookupError(@NonNull final Exception lookupError) {return new SumUpImportResult(null, lookupError);}
}
