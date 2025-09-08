package de.metas.payment.sumup;

import lombok.NonNull;

@FunctionalInterface
public interface SumUpTransactionStatusChangedListener
{
	void onStatusChanged(@NonNull SumUpTransactionStatusChangedEvent event);
}
