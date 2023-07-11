package de.metas.acct.gljournal_sap.callout;

import de.metas.acct.gljournal_sap.SAPGLJournalCurrencyConversionCtx;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalLoaderAndSaver;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.acct.model.I_SAP_GLJournalLine;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.money.Money;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.TabCallout;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Callout(value = I_SAP_GLJournalLine.class, recursionAvoidanceLevel = Callout.RecursionAvoidanceLevel.CalloutMethod)
@TabCallout(I_SAP_GLJournalLine.class)
@Component
public class SAP_GLJournalLine implements ITabCallout
{
	private final SAPGLJournalService glJournalService;

	public SAP_GLJournalLine(
			@NonNull final SAPGLJournalService glJournalService)
	{
		this.glJournalService = glJournalService;
	}

	@PostConstruct
	public void postConstruct()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_SAP_GLJournalLine glJournalLine = calloutRecord.getModel(I_SAP_GLJournalLine.class);
		final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(glJournalLine.getSAP_GLJournal_ID());
		final SeqNo seqNo = glJournalService.getNextSeqNo(glJournalId);
		glJournalLine.setLine(seqNo.toInt());
	}

	@CalloutMethod(columnNames = I_SAP_GLJournalLine.COLUMNNAME_PostingSign)
	public void onPostingSign(final I_SAP_GLJournalLine glJournalLine)
	{
		updateAmtAcct(glJournalLine);
	}

	@CalloutMethod(columnNames = I_SAP_GLJournalLine.COLUMNNAME_C_ValidCombination_ID)
	public void onC_ValidCombination_ID(final I_SAP_GLJournalLine glJournalLine)
	{
		final FAOpenItemTrxInfo openItemTrxInfo = glJournalService.computeTrxInfo(glJournalLine).orElse(null);
		glJournalLine.setIsOpenItem(openItemTrxInfo != null);
		SAPGLJournalLoaderAndSaver.updateRecordFromOpenItemTrxInfo(glJournalLine, openItemTrxInfo);
	}

	@CalloutMethod(columnNames = I_SAP_GLJournalLine.COLUMNNAME_Amount)
	public void onAmount(final I_SAP_GLJournalLine glJournalLine)
	{
		updateAmtAcct(glJournalLine);
	}

	private void updateAmtAcct(final I_SAP_GLJournalLine glJournalLine)
	{
		final SAPGLJournalCurrencyConversionCtx conversionCtx = getConversionCtx(glJournalLine);
		final Money amtAcct = glJournalService.getCurrencyConverter().convertToAcctCurrency(glJournalLine.getAmount(), conversionCtx);
		glJournalLine.setAmtAcct(amtAcct.toBigDecimal());
	}

	private static SAPGLJournalCurrencyConversionCtx getConversionCtx(final I_SAP_GLJournalLine glJournalLine)
	{
		// NOTE: calling model getter because we expect to be fetched from webui Document
		final I_SAP_GLJournal glJournal = glJournalLine.getSAP_GLJournal();

		return SAPGLJournalLoaderAndSaver.extractConversionCtx(glJournal);
	}
}
