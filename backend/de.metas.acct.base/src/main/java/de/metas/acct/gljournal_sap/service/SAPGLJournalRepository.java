package de.metas.acct.gljournal_sap.service;

import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.document.engine.DocStatus;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.function.Consumer;
import java.util.function.Function;

@Repository
public class SAPGLJournalRepository
{
	@NonNull
	public SAPGLJournal getById(@NonNull final SAPGLJournalId id)
	{
		final SAPGLJournalLoaderAndSaver loader = new SAPGLJournalLoaderAndSaver();
		return loader.getById(id);
	}

	public SeqNo getNextSeqNo(@NonNull final SAPGLJournalId glJournalId)
	{
		final SAPGLJournalLoaderAndSaver loader = new SAPGLJournalLoaderAndSaver();
		return loader.getNextSeqNo(glJournalId);
	}

	public void updateWhileSaving(
			@NonNull final I_SAP_GLJournal record,
			@NonNull final Consumer<SAPGLJournal> consumer)
	{
		final SAPGLJournalId glJournalId = SAPGLJournalLoaderAndSaver.extractId(record);

		final SAPGLJournalLoaderAndSaver loaderAndSaver = new SAPGLJournalLoaderAndSaver();
		loaderAndSaver.addToCacheAndAvoidSaving(record);
		loaderAndSaver.updateById(glJournalId, consumer);
	}

	public void updateById(
			@NonNull final SAPGLJournalId glJournalId,
			@NonNull final Consumer<SAPGLJournal> consumer)
	{
		final SAPGLJournalLoaderAndSaver loaderAndSaver = new SAPGLJournalLoaderAndSaver();
		loaderAndSaver.updateById(glJournalId, consumer);
	}

	public <R> R updateById(
			@NonNull final SAPGLJournalId glJournalId,
			@NonNull final Function<SAPGLJournal, R> processor)
	{
		final SAPGLJournalLoaderAndSaver loaderAndSaver = new SAPGLJournalLoaderAndSaver();
		return loaderAndSaver.updateById(glJournalId, processor);
	}

	public DocStatus getDocStatus(final SAPGLJournalId glJournalId)
	{
		final SAPGLJournalLoaderAndSaver loader = new SAPGLJournalLoaderAndSaver();
		return loader.getDocStatus(glJournalId);
	}

	@NonNull
	public SAPGLJournal create(
			@NonNull final SAPGLJournalCreateRequest createRequest,
			@NonNull final SAPGLJournalCurrencyConverter currencyConverter)
	{
		final SAPGLJournalLoaderAndSaver loaderAndSaver = new SAPGLJournalLoaderAndSaver();
		return loaderAndSaver.create(createRequest, currencyConverter);
	}

	public void setDocStatus(final @NonNull SAPGLJournalId glJournalId, final String code)
	{
		final I_SAP_GLJournal sourceRecord = InterfaceWrapperHelper.load(glJournalId, I_SAP_GLJournal.class);
		sourceRecord.setDocStatus(DocStatus.Reversed.getCode());
		InterfaceWrapperHelper.save(sourceRecord);
	}
}
