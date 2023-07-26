package de.metas.ui.web.accounting.process;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Fact_Acct;

import com.google.common.collect.ImmutableList;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

import de.metas.acct.api.IFactAcctDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutionResult.RecordsToOpen;
import de.metas.util.Services;

public final class Fact_Acct_OpenLinkedFacts_ProcessHelper
{
	final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);

	public void openLinkedFactAccounts(final int factAcctRecordId, final ProcessExecutionResult result)
	{
		final I_Fact_Acct factAcct = factAcctDAO.getById(factAcctRecordId);

		final int factAcctTableId = getTableId(I_Fact_Acct.class);

		final TableRecordReference documentReference = TableRecordReference.of(factAcct.getAD_Table_ID(), factAcct.getRecord_ID());

		final IDocument document = documentBL.getDocument(documentReference);

		final ImmutableList<TableRecordReference> linkedFactAccts = factAcctDAO.retrieveQueryForDocument(document)
				.create()
				.listIds()
				.stream()
				.map(recordId -> TableRecordReference.of(factAcctTableId, recordId))
				.collect(ImmutableList.toImmutableList());

		result.setRecordToOpen(RecordsToOpen.builder()
				.records(linkedFactAccts)
				.automaticallySetReferencingDocumentPaths(false)
				.build());
	}
}
