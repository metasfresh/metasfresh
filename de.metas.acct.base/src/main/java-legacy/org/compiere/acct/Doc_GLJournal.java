/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.acct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.acct.api.IGLJournalLineBL;
import org.adempiere.acct.api.IGLJournalLineDAO;
import org.adempiere.acct.api.ITaxAccountable;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.MAcctSchema;
import org.compiere.model.X_GL_JournalLine;

/**
 * Post GL Journal Documents.
 *
 * <pre>
 *  Table:              GL_Journal (224)
 *  Document Types:     GLJ
 * </pre>
 *
 * @author Jorg Janke
 * @version $Id: Doc_GLJournal.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_GLJournal extends Doc
{
	// Services
	private final transient IGLJournalLineDAO glJournalLineDAO = Services.get(IGLJournalLineDAO.class);
	private final transient IGLJournalLineBL glJournalLineBL = Services.get(IGLJournalLineBL.class);

	/**
	 * Constructor
	 *
	 * @param ass accounting schemata
	 * @param rs record
	 * @param trxName trx
	 */
	public Doc_GLJournal(final IDocBuilder docBuilder)
	{
		super(docBuilder);
	}	// Doc_GL_Journal

	/** Posting Type */
	private String m_PostingType = null;
	private int m_C_AcctSchema_ID = 0;

	/**
	 * Load Specific Document Details
	 *
	 * @return error message or null
	 */
	@Override
	protected String loadDocumentDetails()
	{
		final I_GL_Journal journal = getModel(I_GL_Journal.class);
		m_PostingType = journal.getPostingType();
		m_C_AcctSchema_ID = journal.getC_AcctSchema_ID();

		// Contained Objects
		p_lines = loadLines(journal);
		log.debug("Lines=" + p_lines.length);
		return null;
	}   // loadDocumentDetails

	/**
	 * Load Invoice Line
	 *
	 * @param journal journal
	 * @return DocLine Array
	 */
	private DocLine[] loadLines(final I_GL_Journal journal)
	{
		final List<DocLine> docLinesAll = new ArrayList<>();
		final List<I_GL_JournalLine> glJournalLines = glJournalLineDAO.retrieveLines(journal);
		for (final I_GL_JournalLine glJournalLine : glJournalLines)
		{
			final String type = glJournalLine.getType();
			final List<DocLine> docLines;
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

		// Return Array
		return docLinesAll.toArray(new DocLine[docLinesAll.size()]);
	}	// loadLines

	/**
	 * Regular {@link I_GL_JournalLine}.
	 *
	 * <pre>
	 * Account_DR	DR				AmtSourceDr/AmtAcctDr
	 * Account_CR			CR		AmtSourceCr/AmtAcctCr
	 * </pre>
	 *
	 * @param glJournalLine
	 * @return
	 */
	private final List<DocLine> createDocLines_Normal(final I_GL_JournalLine glJournalLine)
	{
		final List<DocLine> docLines = new ArrayList<>();

		if (glJournalLine.isAllowAccountDR())
		{
			final DocLine docLineDR = createDocLine(glJournalLine);
			docLineDR.setAmount(glJournalLine.getAmtSourceDr(), BigDecimal.ZERO);
			docLineDR.setC_ConversionType_ID(glJournalLine.getC_ConversionType_ID());
			docLineDR.setConvertedAmt(glJournalLine.getAmtAcctDr(), BigDecimal.ZERO);
			docLineDR.setAccount(glJournalLine.getAccount_DR());

			docLines.add(docLineDR);
		}
		if (glJournalLine.isAllowAccountCR())
		{
			final DocLine docLineCR = createDocLine(glJournalLine);
			docLineCR.setAmount(BigDecimal.ZERO, glJournalLine.getAmtSourceCr());
			docLineCR.setC_ConversionType_ID(glJournalLine.getC_ConversionType_ID());
			docLineCR.setConvertedAmt(BigDecimal.ZERO, glJournalLine.getAmtAcctCr());
			docLineCR.setAccount(glJournalLine.getAccount_CR());

			docLines.add(docLineCR);
		}

		return docLines;
	}

	private final List<DocLine> createDocLines_Tax(final I_GL_JournalLine glJournalLine)
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
	 *
	 * Tax Accounting (Credit)
	 *
	 * <pre>
	 * Account_DR	DR					AmtSourceCr (TaxTotalAmt)
	 * Account_CR				CR		TaxBaseAmt
	 * Tax_Acct					CR		TaxAmt
	 * </pre>
	 *
	 * @param glJournalLine
	 * @return
	 */
	private final List<DocLine> createDocLines_Tax(final I_GL_JournalLine glJournalLine, final boolean isTaxOnDebit)
	{
		final ITaxAccountable autoTaxRecord = glJournalLineBL.asTaxAccountable(glJournalLine, isTaxOnDebit);

		final List<DocLine> docLines = new ArrayList<>();

		// Tax Total Amount (CR/DR)
		{
			final DocLine docLine_TaxTotalAmt = createDocLine(glJournalLine);
			docLine_TaxTotalAmt.setAmountDrOrCr(autoTaxRecord.getTaxTotalAmt(), !isTaxOnDebit);
			docLine_TaxTotalAmt.setAccount(autoTaxRecord.getTaxTotal_Acct());
			docLines.add(docLine_TaxTotalAmt);
		}

		// Tax Base Amount (DR/CR)
		{
			final DocLine docLine_TaxBaseAmt = createDocLine(glJournalLine);
			docLine_TaxBaseAmt.setAmountDrOrCr(autoTaxRecord.getTaxBaseAmt(), isTaxOnDebit);
			docLine_TaxBaseAmt.setAccount(autoTaxRecord.getTaxBase_Acct());
			docLines.add(docLine_TaxBaseAmt);
		}

		// Tax Amount (DR/CR)
		{
			final DocLine docLine_TaxAmt = createDocLine(glJournalLine);
			docLine_TaxAmt.setAmountDrOrCr(autoTaxRecord.getTaxAmt(), isTaxOnDebit);
			docLine_TaxAmt.setAccount(autoTaxRecord.getTax_Acct());
			docLine_TaxAmt.setC_Tax_ID(autoTaxRecord.getC_Tax_ID());
			docLines.add(docLine_TaxAmt);
		}

		return docLines;
	}

	private final DocLine createDocLine(final I_GL_JournalLine glJournalLine)
	{
		final DocLine_GLJournal docLine = new DocLine_GLJournal(glJournalLine, this);
		docLine.setC_ConversionType_ID(glJournalLine.getC_ConversionType_ID());
		docLine.setC_Tax_ID(0); // avoid setting C_Tax_ID by default
		docLine.setC_AcctSchema_ID(m_C_AcctSchema_ID);
		return docLine;
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

		for (final DocLine docLine : p_lines)
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
	public List<Fact> createFacts(final MAcctSchema as)
	{
		final List<Fact> facts = new ArrayList<>();

		// Other Acct Schema
		if (as.getC_AcctSchema_ID() != m_C_AcctSchema_ID)
		{
			return facts;
		}

		// create Fact Header
		final Fact fact = new Fact(this, as, m_PostingType);

		// GLJ
		if (getDocumentType().equals(DOCTYPE_GLJournal))
		{
			// account DR CR
			for (final DocLine line : p_lines)
			{
				final DocLine_GLJournal docLine = (DocLine_GLJournal)line;
				if (docLine.getC_AcctSchema_ID() > 0 && docLine.getC_AcctSchema_ID() != as.getC_AcctSchema_ID())
				{
					continue;
				}

				fact.createLine(docLine,
						docLine.getAccount(),
						docLine.getC_Currency_ID(),
						docLine.getAmtSourceDr(),
						docLine.getAmtSourceCr());
			}	// for all lines
		}
		else
		{
			throw newPostingException()
					.setC_AcctSchema(as)
					.setDetailMessage("DocumentType unknown: " + getDocumentType());
		}
		//
		facts.add(fact);
		return facts;
	}   // createFact

}   // Doc_GLJournal
