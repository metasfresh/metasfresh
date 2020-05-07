package org.adempiere.acct.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.acct.api.IGLJournalLineBL;
import org.adempiere.acct.api.ITaxAccountable;
import org.adempiere.acct.api.ITaxDeclarationDAO;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.NullLoggable;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationAcct;
import org.compiere.model.I_C_TaxDeclarationLine;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.util.TrxRunnable;

import de.metas.document.engine.IDocumentBL;

/**
 * Builder class which creates the {@link I_C_TaxDeclarationLine}s and {@link I_C_TaxDeclarationAcct}s.
 *
 * @author tsa
 *
 */
public class TaxDeclarationLinesBuilder
{
	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final transient IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final transient IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final transient IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
	private final transient ITaxDeclarationDAO taxDeclarationDAO = Services.get(ITaxDeclarationDAO.class);
	private final transient IGLJournalLineBL journalLineBL = Services.get(IGLJournalLineBL.class);

	// Parameters
	private I_C_TaxDeclaration _taxDeclaration;
	private boolean _deleteOldLines;
	@ToStringBuilder(skip = true)
	private ILoggable loggable = NullLoggable.instance;

	// Status
	private int lineNoOffset_TaxDeclarationLine = 0;
	private int lineNoOffset_TaxDeclarationAcct = 0;
	//
	private int countInvoicesAdded = 0;
	private int countGLJournalLinesAdded;
	private int countLinesCreated = 0;
	private int countAcctLinesCreated = 0;

	public TaxDeclarationLinesBuilder setC_TaxDeclaration(final I_C_TaxDeclaration taxDeclaration)
	{
		_taxDeclaration = taxDeclaration;
		return this;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public TaxDeclarationLinesBuilder setLoggable(final ILoggable loggable)
	{
		Check.assumeNotNull(loggable, "loggable not null");
		this.loggable = loggable;
		return this;
	}

	private final I_C_TaxDeclaration getC_TaxDeclaration()
	{
		Check.assumeNotNull(_taxDeclaration, "_taxDeclaration not null");
		return _taxDeclaration;
	}

	public TaxDeclarationLinesBuilder setDeleteOldLines(final boolean deleteOldLines)
	{
		_deleteOldLines = deleteOldLines;
		return this;
	}

	private boolean isDeleteOldLines()
	{
		return _deleteOldLines;
	}

	/**
	 * Creates {@link I_C_TaxDeclarationLine}s and {@link I_C_TaxDeclarationAcct}s records
	 */
	public void build()
	{
		final I_C_TaxDeclaration taxDeclaration = getC_TaxDeclaration();

		if (isDeleteOldLines())
		{
			taxDeclarationDAO.deleteTaxDeclarationLinesAndAccts(taxDeclaration);
		}

		lineNoOffset_TaxDeclarationLine = taxDeclarationDAO.retrieveLastLineNoOfTaxDeclarationLine(taxDeclaration);
		lineNoOffset_TaxDeclarationAcct = taxDeclarationDAO.retrieveLastLineNoOfTaxDeclarationAcct(taxDeclaration);

		countInvoicesAdded = 0;
		final Iterator<I_C_Invoice> invoices = retrieveInvoices();
		while (invoices.hasNext())
		{
			final I_C_Invoice invoice = invoices.next();
			final boolean added = addInvoice(invoice);
			if (added)
			{
				countInvoicesAdded++;
			}
		}

		countGLJournalLinesAdded = 0;
		final Iterator<I_GL_JournalLine> glJournalLines = retrieveGLJournalLines();
		while (glJournalLines.hasNext())
		{
			final I_GL_JournalLine glJournalLine = glJournalLines.next();
			final boolean added = addGLJournalLine(glJournalLine);
			if (added)
			{
				countGLJournalLinesAdded++;
			}
		}
	}

	private final <T> IQueryBuilder<T> createQueryBuilder(final Class<T> modelClass)
	{
		return queryBL.createQueryBuilder(modelClass, getC_TaxDeclaration()) // FIXME
		;
	}

	private final Properties getCtx()
	{
		return InterfaceWrapperHelper.getCtx(getC_TaxDeclaration());
	}

	private final String getTrxNameInitial()
	{
		return InterfaceWrapperHelper.getTrxName(getC_TaxDeclaration());
	}

	public int getCountInvoicesAdded()
	{
		return countInvoicesAdded;
	}

	public int getCountGLJournalLinesAdded()
	{
		return countGLJournalLinesAdded;
	}

	public int getCountLinesCreated()
	{
		return countLinesCreated;
	}

	public int getCountAcctLinesCreated()
	{
		return countAcctLinesCreated;
	}

	public String getResultSummary()
	{
		return "@C_Invoice_ID@ #" + getCountInvoicesAdded()
				+ ", @GL_JournalLine_ID@ #" + getCountGLJournalLinesAdded()
				+ ", @C_TaxDeclarationLine_ID@ #" + getCountLinesCreated()
				+ ", @C_TaxDeclarationAcct_ID@ #" + getCountAcctLinesCreated();
	}

	private Iterator<I_C_Invoice> retrieveInvoices()
	{
		final I_C_TaxDeclaration taxDeclaration = getC_TaxDeclaration();

		final IQuery<I_C_TaxDeclarationLine> existingTaxDeclarationLinesQuery = createQueryBuilder(I_C_TaxDeclarationLine.class)
				.create();

		final IQueryBuilder<I_C_Invoice> queryBuilder = createQueryBuilder(I_C_Invoice.class)
				//
				// Only invoices from reporting interval
				.addBetweenFilter(I_C_Invoice.COLUMNNAME_DateInvoiced, taxDeclaration.getDateFrom(), taxDeclaration.getDateTo(), DateTruncQueryFilterModifier.DAY)
				//
				// Only those which are processed
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_Processed, true)
				//
				// Only those which were not already included in other tax declaration
				.addNotInSubQueryFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, I_C_TaxDeclarationLine.COLUMNNAME_C_Invoice_ID, existingTaxDeclarationLinesQuery)
		//
		;

		queryBuilder.orderBy()
				.addColumn(I_C_Invoice.COLUMNNAME_DateAcct)
				.addColumn(I_C_Invoice.COLUMNNAME_C_Invoice_ID); // to have a predictable order

		return queryBuilder
				.create()
				// guaranteed because we are inserting in C_TaxDeclarationLine and in our query we check to not have the record already there
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				//
				.iterate(I_C_Invoice.class);
	}

	/**
	 * Create Data
	 *
	 * @param invoice invoice
	 * @return true if invoice was considered
	 */
	private boolean addInvoice(final I_C_Invoice invoice)
	{
		final AtomicBoolean addedRef = new AtomicBoolean(false);
		trxManager.run(getTrxNameInitial(), new TrxRunnable()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				final boolean added = addInvoice0(invoice);
				addedRef.set(added);
			}
		});

		return addedRef.get();
	}

	private boolean addInvoice0(final I_C_Invoice invoice)
	{
		//
		// Skip not posted invoices, but warn the user
		if (!invoice.isPosted())
		{
			loggable.addLog("@Error@: @C_Invoice_ID@ @Posted@=@N@: " + invoiceBL.getSummary(invoice));
			return false;
		}

		//
		// Tax declaration lines (one for each invoice tax record)
		final List<I_C_InvoiceTax> invoiceTaxes = invoiceDAO.retrieveTaxes(invoice);
		final Map<Integer, I_C_TaxDeclarationLine> taxId2taxDeclarationLine = new HashMap<>(invoiceTaxes.size());
		for (final I_C_InvoiceTax invoiceTax : invoiceTaxes)
		{
			final int taxId = invoiceTax.getC_Tax_ID();
			final I_C_TaxDeclarationLine taxDeclarationLine = createTaxDeclarationLine(invoice, invoiceTax);
			final I_C_TaxDeclarationLine taxDeclarationLineOld = taxId2taxDeclarationLine.put(taxId, taxDeclarationLine);
			Check.assumeNull(taxDeclarationLineOld, "More than one invoice tax line for {}, taxId={}", invoice, taxId);
		}

		//
		// Tax declaration accounting records
		final List<I_Fact_Acct> factAcctRecords = factAcctDAO.retrieveQueryForDocument(documentBL.getDocument(invoice))
				// fetch only those Fact_Acct records which are about taxes, i.e.
				.addNotEqualsFilter(I_Fact_Acct.COLUMN_C_Tax_ID, null) // C_Tax_ID is set
				.addEqualsFilter(I_Fact_Acct.COLUMN_Line_ID, null) // Line_ID is NOT set
				//
				.create()
				.list();
		for (final I_Fact_Acct factAcctRecord : factAcctRecords)
		{
			//
			// Link to Tax Declaration Line only if this Fact_Acct is about tax bookings. Which means:
			// * it's document level booking (Line_ID <= 0)
			// * we have a C_TaxDeclarationLine which has the same tax as this booking
			I_C_TaxDeclarationLine taxDeclarationLine = null;
			if (factAcctRecord.getLine_ID() <= 0)
			{
				final int taxId = factAcctRecord.getC_Tax_ID();
				taxDeclarationLine = taxId2taxDeclarationLine.get(taxId);
			}

			createTaxDeclarationAcct(taxDeclarationLine, factAcctRecord);
		}

		return true;
	}

	private final I_C_TaxDeclarationLine newTaxDeclarationLine()
	{
		final I_C_TaxDeclaration taxDeclaration = getC_TaxDeclaration();
		final I_C_TaxDeclarationLine taxDeclarationLine = InterfaceWrapperHelper.create(getCtx(), I_C_TaxDeclarationLine.class, ITrx.TRXNAME_ThreadInherited);
		taxDeclarationLine.setAD_Org_ID(taxDeclaration.getAD_Org_ID());
		taxDeclarationLine.setC_TaxDeclaration(taxDeclaration);
		return taxDeclarationLine;
	}

	private final I_C_TaxDeclarationLine createTaxDeclarationLine(final I_C_Invoice invoice, final I_C_InvoiceTax invoiceTax)
	{
		final I_C_TaxDeclarationLine taxDeclarationLine = newTaxDeclarationLine();

		taxDeclarationLine.setAD_Org_ID(invoice.getAD_Org_ID());
		taxDeclarationLine.setIsManual(false);
		//
		taxDeclarationLine.setC_Invoice(invoice);
		taxDeclarationLine.setIsSOTrx(invoice.isSOTrx());
		taxDeclarationLine.setC_BPartner_ID(invoice.getC_BPartner_ID());
		taxDeclarationLine.setC_Currency_ID(invoice.getC_Currency_ID());
		taxDeclarationLine.setDateAcct(invoice.getDateAcct());
		taxDeclarationLine.setC_DocType_ID(invoice.getC_DocType_ID());
		taxDeclarationLine.setDocumentNo(invoice.getDocumentNo());
		//
		taxDeclarationLine.setC_Tax_ID(invoiceTax.getC_Tax_ID());
		taxDeclarationLine.setTaxBaseAmt(invoiceTax.getTaxBaseAmt());
		taxDeclarationLine.setTaxAmt(invoiceTax.getTaxAmt());

		save(taxDeclarationLine);

		return taxDeclarationLine;
	}

	private final void save(final I_C_TaxDeclarationLine taxDeclarationLine)
	{
		final int line = lineNoOffset_TaxDeclarationLine + (countLinesCreated + 1) * 10;

		taxDeclarationLine.setLine(line);
		InterfaceWrapperHelper.save(taxDeclarationLine);
		countLinesCreated++;
	}

	private void createTaxDeclarationAccts(final I_C_TaxDeclarationLine taxDeclarationLine, final Iterator<I_Fact_Acct> factAcctRecords)
	{
		while (factAcctRecords.hasNext())
		{
			final I_Fact_Acct factAcctRecord = factAcctRecords.next();
			createTaxDeclarationAcct(taxDeclarationLine, factAcctRecord);
		}
	}

	private final I_C_TaxDeclarationAcct createTaxDeclarationAcct(final I_C_TaxDeclarationLine taxDeclarationLine, final I_Fact_Acct fact)
	{
		final I_C_TaxDeclaration taxDeclaration = getC_TaxDeclaration();

		final I_C_TaxDeclarationAcct taxDeclarationAcct = InterfaceWrapperHelper.create(getCtx(), I_C_TaxDeclarationAcct.class, ITrx.TRXNAME_ThreadInherited);
		taxDeclarationAcct.setAD_Org_ID(fact.getAD_Org_ID());
		taxDeclarationAcct.setC_TaxDeclaration(taxDeclaration);

		// Link to C_TaxDeclarationLine ONLY if the Fact_Acct record is about tax booking
		// NOTE: very important, reports are relying on this (e.g. see RV_TaxDeclarationLine)
		if (taxDeclarationLine != null && taxDeclarationLine.getC_Tax_ID() == fact.getC_Tax_ID())
		{
			taxDeclarationAcct.setC_TaxDeclarationLine(taxDeclarationLine);
		}
		else
		{
			taxDeclarationAcct.setC_TaxDeclarationLine(null);
		}

		taxDeclarationAcct.setFact_Acct_ID(fact.getFact_Acct_ID()); // this FK shall block Fact_Acct to be deleted
		taxDeclarationAcct.setC_AcctSchema_ID(fact.getC_AcctSchema_ID());

		//
		// Save
		final int line = lineNoOffset_TaxDeclarationAcct + (countAcctLinesCreated + 1) * 10;
		taxDeclarationAcct.setLine(line);
		InterfaceWrapperHelper.save(taxDeclarationAcct);
		countAcctLinesCreated++;

		return taxDeclarationAcct;
	}

	private final Iterator<I_GL_JournalLine> retrieveGLJournalLines()
	{
		final I_C_TaxDeclaration taxDeclaration = getC_TaxDeclaration();

		final IQuery<I_C_TaxDeclarationLine> existingTaxDeclarationLinesQuery = createQueryBuilder(I_C_TaxDeclarationLine.class)
				.create();

		final ICompositeQueryFilter<I_GL_JournalLine> taxAccountableFilter = queryBL.createCompositeQueryFilter(I_GL_JournalLine.class)
				.setJoinOr()
				.addEqualsFilter(I_GL_JournalLine.COLUMN_DR_AutoTaxAccount, true)
				.addEqualsFilter(I_GL_JournalLine.COLUMN_CR_AutoTaxAccount, true);

		final IQueryBuilder<I_GL_JournalLine> queryBuilder = createQueryBuilder(I_GL_JournalLine.class)
				//
				// Only invoices from reporting interval
				.addBetweenFilter(I_GL_JournalLine.COLUMN_DateAcct, taxDeclaration.getDateFrom(), taxDeclaration.getDateTo(), DateTruncQueryFilterModifier.DAY)
				//
				// Only those which are processed
				.addEqualsFilter(I_GL_JournalLine.COLUMN_Processed, true)
				//
				// Only those which are about taxes
				.filter(taxAccountableFilter)
				//
				// Only those which were not already included in other tax declaration
				.addNotInSubQueryFilter(I_GL_JournalLine.COLUMN_GL_JournalLine_ID, I_C_TaxDeclarationLine.COLUMN_GL_JournalLine_ID, existingTaxDeclarationLinesQuery)
		//
		;

		queryBuilder.orderBy()
				.addColumn(I_GL_JournalLine.COLUMN_DateAcct)
				.addColumn(I_GL_JournalLine.COLUMN_GL_JournalLine_ID); // to have a predictable order

		return queryBuilder
				.create()
				// guaranteed because we are inserting in C_TaxDeclarationLine and in our query we check to not have the record already there
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				//
				.iterate(I_GL_JournalLine.class);
	}

	private boolean addGLJournalLine(final I_GL_JournalLine glJournalLine)
	{
		final AtomicBoolean addedRef = new AtomicBoolean(false);
		trxManager.run(getTrxNameInitial(), new TrxRunnable()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				final boolean added = addGLJournalLine0(glJournalLine);
				addedRef.set(added);
			}
		});

		return addedRef.get();
	}

	private boolean addGLJournalLine0(final I_GL_JournalLine glJournalLine)
	{
		final List<I_Fact_Acct> factAcctRecords = factAcctDAO.retrieveForDocumentLine(I_GL_Journal.Table_Name, glJournalLine.getGL_Journal_ID(), glJournalLine);

		//
		// Skip not posted GL Journal Lines, but warn the user
		if (factAcctRecords.isEmpty())
		{
			final String summary = journalLineBL.getDocumentNo(glJournalLine);
			loggable.addLog("@Error@: @I_GL_JournalLine_ID@ @Posted@=@N@: " + summary);
			return false;
		}

		final I_C_TaxDeclarationLine taxDeclarationLine = createTaxDeclarationLine(glJournalLine);
		createTaxDeclarationAccts(taxDeclarationLine, factAcctRecords.iterator());

		return true;
	}

	private final I_C_TaxDeclarationLine createTaxDeclarationLine(final I_GL_JournalLine glJournalLine)
	{
		final String summary = journalLineBL.getDocumentNo(glJournalLine);

		final I_C_TaxDeclarationLine taxDeclarationLine = newTaxDeclarationLine();

		taxDeclarationLine.setAD_Org_ID(glJournalLine.getAD_Org_ID());
		taxDeclarationLine.setIsManual(false);
		//
		taxDeclarationLine.setGL_JournalLine(glJournalLine);
		taxDeclarationLine.setC_Currency_ID(glJournalLine.getC_Currency_ID());
		taxDeclarationLine.setDateAcct(glJournalLine.getDateAcct());
		taxDeclarationLine.setC_DocType_ID(glJournalLine.getGL_Journal().getC_DocType_ID());
		taxDeclarationLine.setDocumentNo(summary);
		//

		final ITaxAccountable taxAccountable = journalLineBL.asTaxAccountable(glJournalLine);
		// NOTE: Tax on sales transactions is booked on CR, tax on purchase transactions is booked on DR
		final boolean isSOTrx = taxAccountable.isAccountSignCR();
		taxDeclarationLine.setIsSOTrx(isSOTrx);
		taxDeclarationLine.setC_Tax_ID(taxAccountable.getC_Tax_ID());
		taxDeclarationLine.setTaxBaseAmt(taxAccountable.getTaxBaseAmt());
		taxDeclarationLine.setTaxAmt(taxAccountable.getTaxAmt());

		save(taxDeclarationLine);

		return taxDeclarationLine;
	}

}
