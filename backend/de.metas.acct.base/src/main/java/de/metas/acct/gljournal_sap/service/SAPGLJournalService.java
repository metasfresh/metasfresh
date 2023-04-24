package de.metas.acct.gljournal_sap.service;

import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.SAPGLJournalLineId;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.document.engine.DocStatus;
import de.metas.util.lang.SeqNo;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class SAPGLJournalService
{
	private final SAPGLJournalRepository glJournalRepository;
	@Getter private final SAPGLJournalCurrencyConverter currencyConverter;
	@Getter private final SAPGLJournalTaxProvider taxProvider;

	public SAPGLJournalService(
			@NonNull final SAPGLJournalRepository glJournalRepository,
			@NonNull final SAPGLJournalCurrencyConverter currencyConverter,
			@NonNull final SAPGLJournalTaxProvider taxProvider)
	{
		this.glJournalRepository = glJournalRepository;
		this.currencyConverter = currencyConverter;
		this.taxProvider = taxProvider;
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

	@NonNull
	public SAPGLJournalLineId createLine(@NonNull final SAPGLJournalLineCreateRequest request, @NonNull final SAPGLJournalId id)
	{
		final Supplier<SAPGLJournalLineId> lineIdSupplier = glJournalRepository.updateById(
				id,
				glJournal -> {
					return glJournal.addLine(request, currencyConverter);
				}
		);

		final SAPGLJournalLineId lineId = lineIdSupplier.get();
		if (lineId == null)
		{
			// shall not happen
			throw new AdempiereException("Failed creating line for " + request);
		}

		return lineId;
	}

	public void regenerateTaxLines(final SAPGLJournalId glJournalId)
	{
		updateById(glJournalId, glJournal -> glJournal.regenerateTaxLines(taxProvider, currencyConverter));
	}

	public DocStatus getDocStatus(final SAPGLJournalId glJournalId)
	{
		return glJournalRepository.getDocStatus(glJournalId);
	}

	@NonNull
	public SAPGLJournal reverse(@NonNull final SAPGLJournalReverseRequest copyRequest)
	{
		final SAPGLJournal journalToBeCopied = glJournalRepository.getById(copyRequest.getSourceJournalId());
		final SAPGLJournalCreateRequest createRequest = SAPGLJournalCreateRequest.of(journalToBeCopied,
																					 copyRequest.getDateDoc(),
																					 true);

		glJournalRepository.setDocStatus(copyRequest.getSourceJournalId(),DocStatus.Reversed.getCode());

		return glJournalRepository.create(createRequest, currencyConverter);
	}
}
