package de.metas.acct.gljournal_sap.document;

import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MPeriod;
import org.compiere.model.X_GL_Journal;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SAPGLJournalDocumentHandler implements DocumentHandler
{
	private final SAPGLJournalService glJournalService;

	public SAPGLJournalDocumentHandler(final SAPGLJournalService glJournalService) {this.glJournalService = glJournalService;}

	private static I_SAP_GLJournal extractModel(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_SAP_GLJournal.class);
	}

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extractModel(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return extractModel(docFields).getDocumentNo();
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractModel(docFields).getCreatedBy();
	}

	@Override
	public LocalDate getDocumentDate(final DocumentTableFields docFields)
	{
		return TimeUtil.asLocalDate(extractModel(docFields).getDateDoc());
	}

	@Override
	public int getC_Currency_ID(final DocumentTableFields docFields)
	{
		return extractModel(docFields).getC_Currency_ID();
	}

	@Override
	public BigDecimal getApprovalAmt(final DocumentTableFields docFields)
	{
		// copy-paste from MJournal.getApprovalAmt()
		return extractModel(docFields).getTotalDr();
	}

	@Override
	public void approveIt(final DocumentTableFields docFields)
	{
		approveIt(extractModel(docFields));
	}

	private void approveIt(final I_SAP_GLJournal glJournal)
	{
		glJournal.setIsApproved(true);
	}

	@Override
	public String completeIt(final DocumentTableFields docFields)
	{
		final I_SAP_GLJournal glJournal = extractModel(docFields);
		assertPeriodOpen(glJournal);
		glJournalService.assertHasLines(SAPGLJournalId.ofRepoId(glJournal.getSAP_GLJournal_ID()));
		glJournalService.updateTotalsFromLinesNoSave(glJournal);
		assertTotalsBalanced(glJournal);

		glJournal.setDocAction(IDocument.ACTION_None);
		return DocStatus.Completed.getCode();
	}

	private static void assertTotalsBalanced(final I_SAP_GLJournal glJournal)
	{
		final BigDecimal totalsBalance = glJournal.getTotalDr().subtract(glJournal.getTotalCr());
		if (totalsBalance.signum() != 0)
		{
			throw new AdempiereException("Debit and Credit totals are not balanced"); // TODO trl
		}
	}

	private static void assertPeriodOpen(final I_SAP_GLJournal glJournal)
	{
		MPeriod.testPeriodOpen(Env.getCtx(), glJournal.getDateAcct(), glJournal.getC_DocType_ID(), glJournal.getAD_Org_ID());
	}

	@Override
	public void reactivateIt(final DocumentTableFields docFields)
	{
		final I_SAP_GLJournal glJournal = extractModel(docFields);
		assertPeriodOpen(glJournal);
		Services.get(IFactAcctDAO.class).deleteForDocumentModel(glJournal);
		glJournal.setPosted(false);
		glJournal.setProcessed(false);
		glJournal.setDocAction(X_GL_Journal.DOCACTION_Complete);
	}
}
