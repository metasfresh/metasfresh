package de.metas.pos;

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

	public POSCashJournal changeJournalById(@NonNull final POSCashJournalId cashJournalId, @NonNull Consumer<POSCashJournal> updater)
	{
		return repository.changeJournalById(cashJournalId, updater);
	}
}
