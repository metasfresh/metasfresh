package de.metas.acct.gljournal_sap.document;

import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.organization.InstantAndOrgId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MPeriod;
import org.compiere.model.X_GL_Journal;
import org.compiere.util.Env;

import java.math.BigDecimal;

public class SAPGLJournalDocumentHandler implements DocumentHandler
{
	private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
	private final SAPGLJournalService glJournalService;

	public SAPGLJournalDocumentHandler(final SAPGLJournalService glJournalService) {this.glJournalService = glJournalService;}

	private static I_SAP_GLJournal extractRecord(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_SAP_GLJournal.class);
	}

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extractRecord(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return extractRecord(docFields).getDocumentNo();
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractRecord(docFields).getCreatedBy();
	}

	@Override
	public InstantAndOrgId getDocumentDate(final DocumentTableFields docFields)
	{
		final I_SAP_GLJournal record = extractRecord(docFields);
		return InstantAndOrgId.ofTimestamp(record.getDateDoc(), record.getAD_Org_ID());
	}

	@Override
	public int getC_Currency_ID(final DocumentTableFields docFields)
	{
		return extractRecord(docFields).getC_Currency_ID();
	}

	@Override
	public BigDecimal getApprovalAmt(final DocumentTableFields docFields)
	{
		// copy-paste from MJournal.getApprovalAmt()
		return extractRecord(docFields).getTotalDr();
	}

	@Override
	public void approveIt(final DocumentTableFields docFields)
	{
		approveIt(extractRecord(docFields));
	}

	private void approveIt(final I_SAP_GLJournal glJournal)
	{
		glJournal.setIsApproved(true);
	}

	@Override
	public String completeIt(final DocumentTableFields docFields)
	{
		final I_SAP_GLJournal glJournalRecord = extractRecord(docFields);

		assertPeriodOpen(glJournalRecord);

		glJournalService.updateWhileSaving(
				glJournalRecord,
				glJournal -> {
					glJournal.assertHasLines();
					glJournal.updateLineAcctAmounts(glJournalService.getCurrencyConverter());
					glJournal.assertTotalsBalanced();
				}
		);

		glJournalRecord.setDocAction(IDocument.ACTION_None);
		return DocStatus.Completed.getCode();
	}

	private static void assertPeriodOpen(final I_SAP_GLJournal glJournalRecord)
	{
		MPeriod.testPeriodOpen(Env.getCtx(), glJournalRecord.getDateAcct(), glJournalRecord.getC_DocType_ID(), glJournalRecord.getAD_Org_ID());
	}

	@Override
	public void reactivateIt(final DocumentTableFields docFields)
	{
		final I_SAP_GLJournal glJournalRecord = extractRecord(docFields);
		assertPeriodOpen(glJournalRecord);
		factAcctDAO.deleteForDocumentModel(glJournalRecord);
		glJournalRecord.setPosted(false);
		glJournalRecord.setProcessed(false);
		glJournalRecord.setDocAction(X_GL_Journal.DOCACTION_Complete);
	}
}
