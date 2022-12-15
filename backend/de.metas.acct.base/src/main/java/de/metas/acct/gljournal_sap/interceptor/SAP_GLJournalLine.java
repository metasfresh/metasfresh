package de.metas.acct.gljournal_sap.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.gljournal_sap.SAPGLJournalCurrencyConversionCtx;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournalLine;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;

@Interceptor(I_SAP_GLJournalLine.class)
@Component
public class SAP_GLJournalLine
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final SAPGLJournalService glJournalService;

	public SAP_GLJournalLine(final SAPGLJournalService glJournalService) {this.glJournalService = glJournalService;}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_SAP_GLJournalLine record)
	{
		if (InterfaceWrapperHelper.isValueChanged(record, I_SAP_GLJournalLine.COLUMNNAME_PostingSign, I_SAP_GLJournalLine.COLUMNNAME_Amount))
		{
			final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(record.getSAP_GLJournal_ID());
			final SAPGLJournalCurrencyConversionCtx conversionCtx = glJournalService.getConversionCtx(glJournalId);
			final BigDecimal amtAcct = glJournalService.convertToAcctCurrency(conversionCtx, record.getAmount());
			record.setAmtAcct(amtAcct);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_SAP_GLJournalLine record)
	{
		if (InterfaceWrapperHelper.isValueChanged(record, I_SAP_GLJournalLine.COLUMNNAME_PostingSign, I_SAP_GLJournalLine.COLUMNNAME_AmtAcct))
		{
			final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(record.getSAP_GLJournal_ID());
			trxManager.accumulateAndProcessAfterCommit("sapGLJournal.updateTotals", ImmutableSet.of(glJournalId), this::updateTotalsFromLinesNow);
		}
	}

	private void updateTotalsFromLinesNow(@NonNull final Collection<SAPGLJournalId> glJournalIds)
	{
		// NOTE: no need to bother avoiding SQL N+1 issue because usually
		glJournalIds.stream().distinct().forEach(glJournalService::updateTotalsFromLines);
	}

}
