package de.metas.acct.gljournal_sap.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.doc.AcctDocContext;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalCurrencyConversionCtx;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.acct.gljournal_sap.service.SAPGLJournalLoaderAndSaver;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.currency.CurrencyConversionContext;
import de.metas.money.CurrencyId;
import de.metas.util.Check;
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
	private final SAPGLJournalService glJournalService = SpringContextHolder.instance.getBean(SAPGLJournalService.class);
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
		final CurrencyConversionContext currencyConversionCtx = glJournalService.getCurrencyConverter().toCurrencyConversionContext(glJournalCurrencyConversionCtx);
		if (!CurrencyId.equals(as.getCurrencyId(), glJournalCurrencyConversionCtx.getAcctCurrencyId()))
		{
			throw new AdempiereException("The Accounting Currency is no longer the one from document");
		}

		final Fact fact = new Fact(this, as, glJournal.getPostingType());

		// IMPORTANT: we shall not enforce trx lines strategy
		// => Fact_Acct.Counterpart_Fact_Acct_ID won't be set!
		fact.setFactTrxLinesStrategy(null);

		for (final SAPGLJournalLine line : glJournal.getLines())
		{
			final BigDecimal amtSourceDr;
			final BigDecimal amtSourceCr;
			final PostingSign postingSign = line.getPostingSign();
			if (postingSign.isDebit())
			{
				amtSourceDr = line.getAmount().toBigDecimal();
				amtSourceCr = BigDecimal.ZERO;
			}
			else
			{
				Check.assume(postingSign.isCredit(), "PostingType shall be Debit or Credit");

				amtSourceDr = BigDecimal.ZERO;
				amtSourceCr = line.getAmount().toBigDecimal();
			}

			final FactLine factLine = fact.createLine(
					null,
					line.getAccount(),
					glJournalCurrencyConversionCtx.getCurrencyId(),
					amtSourceDr,
					amtSourceCr);
			if (factLine == null)
			{
				continue;
			}

			factLine.setLine_ID(line.getIdNotNull().getRepoId());

			if (line.isTaxLine())
			{
				factLine.setC_Tax_ID(line.getTaxId());
			}

			factLine.setAD_Org_ID(line.getOrgId());
			factLine.setFromDimension(line.getDimension().fallbackTo(glJournal.getDimension()));

			factLine.setDescription(line.getDescription()); // use line description
		}

		return ImmutableList.of(fact);
	}
}
