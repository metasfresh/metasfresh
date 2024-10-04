package de.metas.payment.sumup;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SumUpTransactionListenersRegistry
{
	private final ImmutableList<SumUpTransactionStatusChangedListener> statusChangedListeners;

	public SumUpTransactionListenersRegistry(
			@NonNull Optional<List<SumUpTransactionStatusChangedListener>> statusChangedListeners
	)
	{
		this.statusChangedListeners = statusChangedListeners.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
	}

	public void fireNewTransaction(@NonNull final SumUpTransaction trx)
	{
		statusChangedListeners.forEach(listener -> listener.onStatusChanged(trx, null));
	}

	public void fireStatusChangedIfNeeded(@NonNull final SumUpTransaction trx, @NonNull final SumUpTransaction trxBeforeChange)
	{
		if (SumUpTransactionStatus.equals(trx.getStatus(), trxBeforeChange.getStatus()))
		{
			return;
		}

		statusChangedListeners.forEach(listener -> listener.onStatusChanged(trx, trxBeforeChange));
	}
}
