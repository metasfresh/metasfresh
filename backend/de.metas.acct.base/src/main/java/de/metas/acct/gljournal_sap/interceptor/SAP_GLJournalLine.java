package de.metas.acct.gljournal_sap.interceptor;

import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.SAPGLJournalLineId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournalLine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_SAP_GLJournalLine.class)
@Component
public class SAP_GLJournalLine
{
	private final SAPGLJournalService glJournalService;

	public SAP_GLJournalLine(final SAPGLJournalService glJournalService)
	{
		this.glJournalService = glJournalService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_SAP_GLJournalLine record, final int timing)
	{
		final I_C_ElementValue account = record.getC_ValidCombination().getAccount();
		record.setIsOpenItem(account.isOpenItem());
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_SAP_GLJournalLine record, final ModelChangeType timing)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			if (timing.isNew() || InterfaceWrapperHelper.isValueChanged(record, I_SAP_GLJournalLine.COLUMNNAME_PostingSign, I_SAP_GLJournalLine.COLUMNNAME_AmtAcct))
			{
				final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(record.getSAP_GLJournal_ID());
				glJournalService.updateTotalsFromLines(glJournalId);
			}
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void beforeDelete(final I_SAP_GLJournalLine record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(record.getSAP_GLJournal_ID());
			final SAPGLJournalLineId glJournalLineId = SAPGLJournalLineId.ofRepoId(glJournalId, record.getSAP_GLJournalLine_ID());
			glJournalService.updateById(
					glJournalId,
					glJournal -> glJournal.removeLinesIf(line -> SAPGLJournalLineId.equals(line.getParentId(), glJournalLineId)));
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void afterDelete(final I_SAP_GLJournalLine record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(record.getSAP_GLJournal_ID());
			glJournalService.updateById(glJournalId, SAPGLJournal::updateTotals);
		}
	}

}
