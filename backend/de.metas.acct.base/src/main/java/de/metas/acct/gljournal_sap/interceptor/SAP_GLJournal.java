package de.metas.acct.gljournal_sap.interceptor;

import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalCurrencyConversionCtx;
import de.metas.acct.gljournal_sap.service.SAPGLJournalLoaderAndSaver;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.copy_with_details.CopyRecordFactory;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_SAP_GLJournal.class)
@Component
public class SAP_GLJournal
{
	private final SAPGLJournalService glJournalService;

	public SAP_GLJournal(final SAPGLJournalService glJournalService) {this.glJournalService = glJournalService;}

	@Init
	public void init()
	{
		CopyRecordFactory.enableForTableName(I_SAP_GLJournal.Table_Name);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_SAP_GLJournal record, final ModelChangeType timing)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			if (isConversionCtxChanged(record, timing))
			{
				glJournalService.updateWhileSaving(
						record,
						glJournal -> glJournal.updateLineAcctAmounts(glJournalService.getCurrencyConverter()));
			}
		}
	}

	private static boolean isConversionCtxChanged(final I_SAP_GLJournal record, final ModelChangeType timing)
	{
		if (timing.isNew())
		{
			return true;
		}

		final SAPGLJournalCurrencyConversionCtx conversionCtx = SAPGLJournalLoaderAndSaver.extractConversionCtx(record);
		final SAPGLJournalCurrencyConversionCtx conversionCtxOld = SAPGLJournalLoaderAndSaver.extractConversionCtx(InterfaceWrapperHelper.createOld(record, I_SAP_GLJournal.class));
		return !SAPGLJournalCurrencyConversionCtx.equals(conversionCtx, conversionCtxOld);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void beforeDelete(final I_SAP_GLJournal record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			// shall not happen
			if (record.isProcessed())
			{
				throw new AdempiereException("Record is processed");
			}

			glJournalService.updateWhileSaving(record, SAPGLJournal::removeAllLines);
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(final I_SAP_GLJournal record)
	{
		glJournalService.fireAfterComplete(record);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void beforeReactivate(final I_SAP_GLJournal record)
	{
		glJournalService.fireBeforeReactivate(record);
	}

}
