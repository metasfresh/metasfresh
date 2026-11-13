package de.metas.pos;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class POSCashJournalService
{
	@NonNull private final POSCashJournalRepository repository;

	public POSCashJournal getById(@NonNull final POSCashJournalId cashJournalId)
	{
		return repository.getById(cashJournalId);
	}
		
	public POSCashJournal newJournal(final POSCashJournalCreateRequest request)
	{
		return repository.newJournal(request);
	}

	/**
	 * Journals the order's successful payments that are not journaled yet on any journal (see {@link POSCashJournal#addPayments}).
	 * The check runs under the journal's lock; the callers (order completion) also hold the order's lock, so no two of them
	 * journal the same order at the same time.
	 */
	public POSCashJournal addOrderPayments(@NonNull final POSCashJournalId cashJournalId, @NonNull final POSOrder posOrder)
	{
		return repository.changeJournalById(cashJournalId, journal -> journal.addPayments(
				posOrder,
				repository.retrieveJournaledPaymentIds(posOrder.streamPaymentsNotDeleted()
						.filter(POSPayment::isSuccessful)
						.map(POSPayment::getLocalIdNotNull)
						.collect(ImmutableSet.toImmutableSet()))));
	}

	public POSCashJournal changeJournalById(@NonNull final POSCashJournalId cashJournalId, @NonNull Consumer<POSCashJournal> updater)
	{
		return repository.changeJournalById(cashJournalId, updater);
	}
}
