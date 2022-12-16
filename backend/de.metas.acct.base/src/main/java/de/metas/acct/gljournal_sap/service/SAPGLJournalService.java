package de.metas.acct.gljournal_sap.service;

import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.util.lang.SeqNo;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class SAPGLJournalService
{
	@Getter
	private final SAPGLJournalCurrencyConverter currencyConverter;
	private final SAPGLJournalRepository glJournalRepository;

	public SAPGLJournalService(
			@NonNull final SAPGLJournalCurrencyConverter currencyConverter,
			@NonNull final SAPGLJournalRepository glJournalRepository)
	{
		this.currencyConverter = currencyConverter;
		this.glJournalRepository = glJournalRepository;
	}

	public SeqNo getNextSeqNo(@NonNull final SAPGLJournalId glJournalId)
	{
		return glJournalRepository.getNextSeqNo(glJournalId);
	}

	public void updateTotalsFromLines(@NonNull final SAPGLJournalId glJournalId)
	{
		updateById(glJournalId, SAPGLJournal::updateTotals);
	}

	public void updateWhileSaving(@NonNull final I_SAP_GLJournal record, @NonNull final Consumer<SAPGLJournal> consumer)
	{
		glJournalRepository.updateWhileSaving(record, consumer);
	}

	public void updateById(@NonNull final SAPGLJournalId glJournalId, @NonNull final Consumer<SAPGLJournal> consumer)
	{
		glJournalRepository.updateById(glJournalId, consumer);
	}
}
