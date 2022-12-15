package de.metas.acct.gljournal_sap.service;

import de.metas.acct.gljournal_sap.DebitAndCreditAmountsBD;
import de.metas.acct.gljournal_sap.SAPGLJournalCurrencyConversionCtx;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.FixedConversionRate;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SAPGLJournalService
{
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final GLJournalRepository glJournalRepository;
	private final SAPGLJournalLineRepository glJournalLineRepository;

	public SAPGLJournalService(
			@NonNull final GLJournalRepository glJournalRepository,
			@NonNull final SAPGLJournalLineRepository glJournalLineRepository)
	{
		this.glJournalRepository = glJournalRepository;
		this.glJournalLineRepository = glJournalLineRepository;
	}

	public SAPGLJournalCurrencyConversionCtx getConversionCtx(@NonNull final SAPGLJournalId glJournalId)
	{
		final I_SAP_GLJournal glJournal = getRecordById(glJournalId);

		final CurrencyId currencyId = CurrencyId.ofRepoId(glJournal.getC_Currency_ID());
		final CurrencyId acctCurrencyId = CurrencyId.ofRepoId(glJournal.getAcct_Currency_ID());

		final FixedConversionRate fixedConversionRate;
		final BigDecimal currencyRateBD = glJournal.getCurrencyRate();
		if (currencyRateBD.signum() != 0)
		{
			fixedConversionRate = FixedConversionRate.builder()
					.fromCurrencyId(currencyId)
					.toCurrencyId(acctCurrencyId)
					.multiplyRate(currencyRateBD)
					.build();
		}
		else
		{
			fixedConversionRate = null;
		}

		return SAPGLJournalCurrencyConversionCtx.builder()
				.acctCurrencyId(acctCurrencyId)
				.currencyId(currencyId)
				.conversionTypeId(CurrencyConversionTypeId.ofRepoIdOrNull(glJournal.getC_ConversionType_ID()))
				.date(glJournal.getDateAcct().toInstant())
				.fixedConversionRate(fixedConversionRate)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(glJournal.getAD_Client_ID(), glJournal.getAD_Org_ID()))
				.build();
	}

	public BigDecimal convertToAcctCurrency(@NonNull final SAPGLJournalCurrencyConversionCtx ctx, @NonNull final BigDecimal amount)
	{
		final CurrencyConversionResult result = currencyBL.convert(toCurrencyConversionContext(ctx), amount, ctx.getCurrencyId(), ctx.getAcctCurrencyId());
		return result.getAmount();
	}

	private CurrencyConversionContext toCurrencyConversionContext(@NonNull final SAPGLJournalCurrencyConversionCtx ctx)
	{
		CurrencyConversionContext currencyConversionContext = currencyBL.createCurrencyConversionContext(
				TimeUtil.asLocalDate(ctx.getDate()),
				ctx.getConversionTypeId(),
				ctx.getClientAndOrgId().getClientId(),
				ctx.getClientAndOrgId().getOrgId());

		if (ctx.getFixedConversionRate() != null)
		{
			currencyConversionContext = currencyConversionContext.withFixedConversionRate(ctx.getFixedConversionRate());
		}

		return currencyConversionContext;
	}

	private I_SAP_GLJournal getRecordById(@NonNull final SAPGLJournalId glJournalId)
	{
		final I_SAP_GLJournal glJournal = InterfaceWrapperHelper.load(glJournalId, I_SAP_GLJournal.class);
		if (glJournal == null)
		{
			throw new AdempiereException("No GL Journal found for " + glJournalId);
		}
		return glJournal;
	}

	public SeqNo getNextSeqNo(@NonNull final SAPGLJournalId glJournalId)
	{
		return glJournalLineRepository.getNextSeqNo(glJournalId);
	}

	public void updateTotalsFromLines(@NonNull final SAPGLJournalId glJournalId)
	{
		final DebitAndCreditAmountsBD totals = glJournalLineRepository.computeDebitAndCreditAmountsAcct(glJournalId);
		glJournalRepository.updateTotals(glJournalId, totals);
	}

	public void updateTotalsFromLinesNoSave(@NonNull final I_SAP_GLJournal glJournal)
	{
		final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(glJournal.getSAP_GLJournal_ID());
		final DebitAndCreditAmountsBD totals = glJournalLineRepository.computeDebitAndCreditAmountsAcct(glJournalId);
		glJournal.setTotalDr(totals.getDebit());
		glJournal.setTotalCr(totals.getCredit());
	}

	public void assertHasLines(@NonNull final SAPGLJournalId glJournalId)
	{
		if (!glJournalLineRepository.hasLines(glJournalId))
		{
			throw new AdempiereException("@NoLines@");
		}
	}

}
