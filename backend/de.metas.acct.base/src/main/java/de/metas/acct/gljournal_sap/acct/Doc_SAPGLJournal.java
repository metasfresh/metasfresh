package de.metas.acct.gljournal_sap.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.Account;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.doc.AcctDocContext;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalCurrencyConversionCtx;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.acct.gljournal_sap.service.SAPGLJournalCurrencyConverter;
import de.metas.acct.gljournal_sap.service.SAPGLJournalLoaderAndSaver;
import de.metas.acct.gljournal_sap.service.SAPGLJournalTaxProvider;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.tax.api.TaxId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;

import java.math.BigDecimal;
import java.util.List;

public class Doc_SAPGLJournal extends Doc<DocLine<?>>
{
	private final SAPGLJournalTaxProvider glJournalTaxProvider = SpringContextHolder.instance.getBean(SAPGLJournalTaxProvider.class);
	private final SAPGLJournalCurrencyConverter glJournalCurrencyConverter = SpringContextHolder.instance.getBean(SAPGLJournalCurrencyConverter.class);

	private SAPGLJournal glJournal;

	public Doc_SAPGLJournal(final AcctDocContext ctx)
	{
		super(ctx);
	}

	@Override
	protected void loadDocumentDetails()
	{
		final SAPGLJournalLoaderAndSaver loader = new SAPGLJournalLoaderAndSaver();

		final I_SAP_GLJournal model = getModel(I_SAP_GLJournal.class);
		loader.addToCacheAndAvoidSaving(model);

		glJournal = loader.getById(SAPGLJournalLoaderAndSaver.extractId(model));
	}

	@Override
	protected BigDecimal getBalance()
	{
		// TODO
		return BigDecimal.ZERO;
	}

	@Override
	protected List<Fact> createFacts(final AcctSchema as)
	{
		if (!AcctSchemaId.equals(as.getId(), glJournal.getAcctSchemaId()))
		{
			return ImmutableList.of();
		}

		final SAPGLJournalCurrencyConversionCtx glJournalCurrencyConversionCtx = glJournal.getConversionCtx();
		if (!CurrencyId.equals(as.getCurrencyId(), glJournalCurrencyConversionCtx.getAcctCurrencyId()))
		{
			throw new AdempiereException("The Accounting Currency is no longer the one from document")
					.appendParametersToMessage()
					.setParameter("acctSchema", as)
					.setParameter("glJournalCurrencyConversionCtx", glJournalCurrencyConversionCtx);
		}

		final Fact fact = new Fact(this, as, glJournal.getPostingType());

		// IMPORTANT: we shall not enforce trx lines strategy
		// => Fact_Acct.Counterpart_Fact_Acct_ID won't be set!
		fact.setFactTrxLinesStrategy(null);

		for (final SAPGLJournalLine line : glJournal.getLines())
		{
			if (isGrossLineToExplodeToNetAndTaxLines(line))
			{
				createFactsForLine_ExplodeGrossLineToNetAndTaxAmt(fact, line);
			}
			else
			{
				createFactsForLine_Standard(fact, line);
			}
		}

		createCurrencyExchangeGainLoss(fact);

		return ImmutableList.of(fact);
	}

	private void updateFactLineFrom(final FactLine factLine, final SAPGLJournalLine from)
	{
		factLine.setLine_ID(from.getIdNotNull().getRepoId());
		factLine.setAD_Org_ID(from.getOrgId());
		factLine.setBPartnerId(from.getBpartnerId());
		factLine.setFromDimension(from.getDimension().fallbackTo(glJournal.getDimension()));
		factLine.setDescription(from.getDescription()); // use from description
	}

	private boolean isGrossLineToExplodeToNetAndTaxLines(@NonNull final SAPGLJournalLine taxBaseLine)
	{
		return taxBaseLine.isExplodeToNetAndTaxLines()
				&& taxBaseLine.isBaseTaxLine()
				// Check if user already generated a GL Journal tax line.
				// In that case we won't automatically generate accounting transactions for tax
				// because those accounting transactions will be created when booking the GL Journal tax line:
				&& !glJournal.isTaxLineGeneratedForBaseLine(taxBaseLine);
	}

	private void createFactsForLine_Standard(@NonNull final Fact fact, @NonNull final SAPGLJournalLine line)
	{
		final SAPGLJournalCurrencyConversionCtx glJournalCurrencyConversionCtx = glJournal.getConversionCtx();

		final PostingSign postingSign = line.getPostingSign();

		final FactLine factLine = fact.createLine()
				.setAccount(line.getAccount())
				.setAmtSource(postingSign, line.getAmount().assertCurrencyId(glJournalCurrencyConversionCtx.getCurrencyId()))
				.setAmtAcct(postingSign, line.getAmountAcct())
				.openItemKey(line.getOpenItemTrxInfo())
				.alsoAddZeroLine()
				.buildAndAdd();

		if (factLine == null)
		{
			return;
		}

		updateFactLineFrom(factLine, line);

		if (line.isTaxLine())
		{
			factLine.setTaxIdAndUpdateVatCode(line.getTaxId());
		}
	}

	private void createFactsForLine_ExplodeGrossLineToNetAndTaxAmt(@NonNull final Fact fact, @NonNull final SAPGLJournalLine grossLine)
	{
		final AcctSchemaId acctSchemaId = fact.getAcctSchemaId();
		final TaxId taxId = Check.assumeNotNull(grossLine.getTaxId(), "gross line shall have the tax set: {}", grossLine);
		final boolean isReverseCharge = glJournalTaxProvider.isReverseCharge(taxId);

		final PostingSign postingSign = grossLine.getPostingSign();
		final Money netAmt;
		final Money taxAmt;
		if (!isReverseCharge)
		{
			final Money grossAmt = grossLine.getAmount();
			taxAmt = glJournalTaxProvider.calculateTaxAmt(grossAmt, taxId, true);
			netAmt = grossAmt.subtract(taxAmt);
		}
		else
		{
			netAmt = grossLine.getAmount();
			taxAmt = glJournalTaxProvider.calculateTaxAmt(netAmt, taxId, false);
		}

		final Money netAmtAcct = glJournalCurrencyConverter.convertToAcctCurrency(netAmt, glJournal.getConversionCtx());
		final Money taxAmtAcct = glJournalCurrencyConverter.convertToAcctCurrency(taxAmt, glJournal.getConversionCtx());

		final FactLine netFactLine = fact.createLine()
				.setAccount(grossLine.getAccount())
				.setAmtSource(postingSign, netAmt)
				.setAmtAcct(postingSign, netAmtAcct)
				.setC_Tax_ID(taxId)
				.alsoAddZeroLine()
				.buildAndAddNotNull();
		updateFactLineFrom(netFactLine, grossLine);

		final FactLine taxFactLine = fact.createLine()
				.setAccount(glJournalTaxProvider.getTaxAccount(taxId, acctSchemaId, postingSign))
				.setAmtSource(postingSign, taxAmt)
				.setAmtAcct(postingSign, taxAmtAcct)
				.setC_Tax_ID(taxId)
				.alsoAddZeroLine()
				.buildAndAddNotNull();
		updateFactLineFrom(taxFactLine, grossLine);

		if (isReverseCharge)
		{
			final FactLine taxFactLine2 = fact.createLine()
					.setAccount(glJournalTaxProvider.getTaxAccount(taxId, acctSchemaId, postingSign.reverse()))
					.setAmtSource(postingSign.reverse(), taxAmt)
					.setAmtAcct(postingSign.reverse(), taxAmtAcct)
					.setC_Tax_ID(taxId)
					.alsoAddZeroLine()
					.buildAndAddNotNull();
			updateFactLineFrom(taxFactLine2, grossLine);
		}
	}

	private void createCurrencyExchangeGainLoss(@NonNull final Fact fact)
	{
		//
		// Make sure source amounts are balanced
		fact.balanceSource();
		if (!fact.isSourceBalanced())
		{
			throw newPostingException()
					.setDetailMessage("Source amounts shall be balanced in order to calculate currency conversion gain/loss")
					.setFact(fact);
		}

		final Money acctBalance = fact.getAcctBalance().toMoney();
		if (acctBalance.isZero())
		{
			return;
		}

		final PostingSign postingSign;
		final Account account;
		final Money amt;
		if (acctBalance.signum() > 0)
		{
			postingSign = PostingSign.CREDIT;
			account = fact.getAcctSchema().getDefaultAccounts().getRealizedGainAcct();
			amt = acctBalance;
		}
		else // acctBalance < 0
		{
			postingSign = PostingSign.DEBIT;
			account = fact.getAcctSchema().getDefaultAccounts().getRealizedLossAcct();
			amt = acctBalance.negate();
		}

		final FactLine factLine = fact.createLine()
				.setAccount(account)
				.setAmtSource(postingSign, amt)
				.setAmtAcct(postingSign, amt)
				.alsoAddZeroLine()
				.buildAndAddNotNull();

		factLine.setFromDimension(glJournal.getDimension());
	}
}
