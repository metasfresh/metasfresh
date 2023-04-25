package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.acct.gljournal.IGLJournalLineBL;
import de.metas.acct.gljournal.IGLJournalLineDAO;
import de.metas.acct.tax.ITaxAccountable;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.FixedConversionRate;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.X_GL_JournalLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Post GL Journal Documents.
 *
 * <pre>
 *  Table:              GL_Journal (224)
 *  Document Types:     GLJ
 * </pre>
 *
 * @author Jorg Janke
 */
public class Doc_GLJournal extends Doc<DocLine_GLJournal>
{
	// Services
	private final IGLJournalLineDAO glJournalLineDAO = Services.get(IGLJournalLineDAO.class);
	private final IGLJournalLineBL glJournalLineBL = Services.get(IGLJournalLineBL.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	public Doc_GLJournal(final AcctDocContext ctx)
	{
		super(ctx);
	}

	private PostingType postingType = null;
	private AcctSchemaId acctSchemaId;

	@Override
	protected void loadDocumentDetails()
	{
		final I_GL_Journal journal = getModel(I_GL_Journal.class);
		postingType = PostingType.ofCode(journal.getPostingType());
		acctSchemaId = AcctSchemaId.ofRepoId(journal.getC_AcctSchema_ID());

		setDocLines(loadLines(journal));
	}

	/**
	 * Load Invoice Line
	 *
	 * @param journal journal
	 * @return DocLine Array
	 */
	private List<DocLine_GLJournal> loadLines(final I_GL_Journal journal)
	{
		final List<DocLine_GLJournal> docLinesAll = new ArrayList<>();
		final List<I_GL_JournalLine> glJournalLines = glJournalLineDAO.retrieveLines(journal);
		for (final I_GL_JournalLine glJournalLine : glJournalLines)
		{
			final String type = glJournalLine.getType();
			final List<DocLine_GLJournal> docLines;
			if (X_GL_JournalLine.TYPE_Normal.equals(type))
			{
				docLines = createDocLines_Normal(glJournalLine);
			}
			else if (X_GL_JournalLine.TYPE_Tax.equals(type))
			{
				docLines = createDocLines_Tax(glJournalLine);
			}
			else
			{
				throw new AdempiereException("@NotSupported@ @Type@: " + type);
			}

			docLinesAll.addAll(docLines);
		}

		return docLinesAll;
	}    // loadLines

	/**
	 * Regular {@link I_GL_JournalLine}.
	 *
	 * <pre>
	 * Account_DR	DR				AmtSourceDr/AmtAcctDr
	 * Account_CR			CR		AmtSourceCr/AmtAcctCr
	 * </pre>
	 */
	private List<DocLine_GLJournal> createDocLines_Normal(final I_GL_JournalLine glJournalLine)
	{
		final List<DocLine_GLJournal> docLines = new ArrayList<>();

		if (glJournalLine.isAllowAccountDR())
		{
			final DocLine_GLJournal docLineDR = createDocLine(glJournalLine);
			docLineDR.setAmount(glJournalLine.getAmtSourceDr(), BigDecimal.ZERO);
			docLineDR.setC_ConversionType_ID(glJournalLine.getC_ConversionType_ID());
			docLineDR.setConvertedAmt(glJournalLine.getAmtAcctDr(), BigDecimal.ZERO);
			docLineDR.setAccount(glJournalLine.getAccount_DR());

			docLines.add(docLineDR);
		}
		if (glJournalLine.isAllowAccountCR())
		{
			final DocLine_GLJournal docLineCR = createDocLine(glJournalLine);
			docLineCR.setAmount(BigDecimal.ZERO, glJournalLine.getAmtSourceCr());
			docLineCR.setC_ConversionType_ID(glJournalLine.getC_ConversionType_ID());
			docLineCR.setConvertedAmt(BigDecimal.ZERO, glJournalLine.getAmtAcctCr());
			docLineCR.setAccount(glJournalLine.getAccount_CR());

			docLines.add(docLineCR);
		}

		return docLines;
	}

	private List<DocLine_GLJournal> createDocLines_Tax(final I_GL_JournalLine glJournalLine)
	{
		if (glJournalLine.isDR_AutoTaxAccount())
		{
			return createDocLines_Tax(glJournalLine, true);
		}
		else if (glJournalLine.isCR_AutoTaxAccount())
		{
			return createDocLines_Tax(glJournalLine, false);
		}
		else
		{
			throw new AdempiereException("@NotSet@ @DR_AutoTaxAccount@ / @CR_AutoTaxAccount@");
		}
	}

	/**
	 * Tax Accounting (Debit)
	 *
	 * <pre>
	 * Account_CR				CR		AmtSourceCr (TaxTotalAmt)
	 * Account_DR	DR					TaxBaseAmt
	 * Tax_Acct		DR					TaxAmt
	 * </pre>
	 * <p>
	 * Tax Accounting (Credit)
	 *
	 * <pre>
	 * Account_DR	DR					AmtSourceCr (TaxTotalAmt)
	 * Account_CR				CR		TaxBaseAmt
	 * Tax_Acct					CR		TaxAmt
	 * </pre>
	 */
	private List<DocLine_GLJournal> createDocLines_Tax(final I_GL_JournalLine glJournalLine, final boolean isTaxOnDebit)
	{
		final ITaxAccountable autoTaxRecord = glJournalLineBL.asTaxAccountable(glJournalLine, isTaxOnDebit);

		final List<DocLine_GLJournal> docLines = new ArrayList<>();

		// Tax Total Amount (CR/DR)
		{
			final DocLine_GLJournal docLine_TaxTotalAmt = createDocLine(glJournalLine);
			docLine_TaxTotalAmt.setAmountDrOrCr(autoTaxRecord.getTaxTotalAmt(), !isTaxOnDebit);
			docLine_TaxTotalAmt.setAccount(autoTaxRecord.getTaxTotal_Acct());
			docLines.add(docLine_TaxTotalAmt);
		}

		// Tax Base Amount (DR/CR)
		{
			final DocLine_GLJournal docLine_TaxBaseAmt = createDocLine(glJournalLine);
			docLine_TaxBaseAmt.setAmountDrOrCr(autoTaxRecord.getTaxBaseAmt(), isTaxOnDebit);
			docLine_TaxBaseAmt.setAccount(autoTaxRecord.getTaxBase_Acct());
			docLines.add(docLine_TaxBaseAmt);
		}

		// Tax Amount (DR/CR)
		{
			final DocLine_GLJournal docLine_TaxAmt = createDocLine(glJournalLine);
			docLine_TaxAmt.setAmountDrOrCr(autoTaxRecord.getTaxAmt(), isTaxOnDebit);
			docLine_TaxAmt.setAccount(autoTaxRecord.getTax_Acct());
			docLine_TaxAmt.setTaxId(TaxId.ofRepoIdOrNull(autoTaxRecord.getC_Tax_ID()));
			docLines.add(docLine_TaxAmt);
		}

		return docLines;
	}

	private DocLine_GLJournal createDocLine(final I_GL_JournalLine glJournalLine)
	{
		final DocLine_GLJournal docLine = new DocLine_GLJournal(glJournalLine, this);
		docLine.setC_ConversionType_ID(glJournalLine.getC_ConversionType_ID());
		docLine.setFixedCurrencyRate(glJournalLine.getCurrencyRate());
		docLine.setTaxId(null); // avoid setting C_Tax_ID by default
		docLine.setAcctSchemaId(acctSchemaId);

		final Quantity qty = extractQty(glJournalLine);
		if (qty != null)
		{
			docLine.setQty(qty);
		}

		return docLine;
	}

	@Nullable
	private static Quantity extractQty(final I_GL_JournalLine glJournalLine)
	{
		final int uomId = glJournalLine.getC_UOM_ID();
		if (uomId <= 0)
		{
			return null;
		}

		final I_C_UOM uom = Services.get(IUOMDAO.class).getById(uomId);
		return Quantity.of(glJournalLine.getQty(), uom);
	}

	/**************************************************************************
	 * Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 *
	 * @return positive amount, if total invoice is bigger than lines
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal balance = BigDecimal.ZERO;

		for (final DocLine_GLJournal docLine : getDocLines())
		{
			final BigDecimal docLineBalance = docLine.getAmtSource();
			balance = balance.add(docLineBalance);
		}

		return balance;
	}   // getBalance

	/**
	 * Create Facts (the accounting logic) for GLJ. (only for the accounting scheme, it was created)
	 *
	 * <pre>
	 *      account     DR          CR
	 * </pre>
	 *
	 * @param as acct schema
	 * @return Fact
	 */
	@Override
	public List<Fact> createFacts(final AcctSchema as)
	{
		// Other Acct Schema
		if (!AcctSchemaId.equals(as.getId(), acctSchemaId))
		{
			return ImmutableList.of();
		}

		// create Fact Header
		final Fact fact = new Fact(this, as, postingType);
		fact.setFactTrxLinesStrategy(Doc_GLJournal_FactTrxStrategy.instance);

		// GLJ
		if (getDocumentType().equals(DOCTYPE_GLJournal))
		{
			// account DR CR
			for (final DocLine_GLJournal line : getDocLines())
			{
				if (line.getAcctSchemaId() != null && !Objects.equals(line.getAcctSchemaId(), as.getId()))
				{
					continue;
				}

				final CurrencyConversionContext currencyConversionCtx = createCurrencyConversionContext(
						line,
						as.getCurrencyId());

				final FactLine factLine = fact.createLine(line,
						line.getAccount(),
						line.getCurrencyId(),
						line.getAmtSourceDr(),
						line.getAmtSourceCr());
				if (factLine == null)
				{
					continue;
				}

				factLine.setCurrencyConversionCtx(currencyConversionCtx);
				factLine.convert();
			}    // for all lines
		}
		else
		{
			throw newPostingException()
					.setAcctSchema(as)
					.setDetailMessage("DocumentType unknown: " + getDocumentType());
		}
		//
		return ImmutableList.of(fact);
	}   // createFact

	private CurrencyConversionContext createCurrencyConversionContext(
			@NonNull final DocLine_GLJournal line,
			@NonNull final CurrencyId acctSchemaCurrencyId)
	{
		CurrencyConversionContext currencyConversionCtx = currencyBL.createCurrencyConversionContext(
				line.getDateAcct(),
				line.getCurrencyConversionTypeId(),
				line.getClientId(),
				line.getOrgId());

		final BigDecimal fixedCurrencyRate = line.getFixedCurrencyRate();
		if (fixedCurrencyRate != null && fixedCurrencyRate.signum() != 0)
		{
			currencyConversionCtx = currencyConversionCtx.withFixedConversionRate(FixedConversionRate.builder()
					.fromCurrencyId(line.getCurrencyId())
					.toCurrencyId(acctSchemaCurrencyId)
					.multiplyRate(fixedCurrencyRate)
					.build());
		}

		return currencyConversionCtx;
	}

}   // Doc_GLJournal
