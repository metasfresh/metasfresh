package de.metas.payment.sumup;

import lombok.NonNull;

import javax.annotation.Nullable;

@FunctionalInterface
public interface SumUpTransactionStatusChangedListener
{
	void onStatusChanged(@NonNull final SumUpTransaction trx, @Nullable final SumUpTransaction trxBeforeChange);
}
