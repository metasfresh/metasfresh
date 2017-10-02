package org.adempiere.invoice.service.impl;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_InOutLine;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.document.engine.IDocument;

/**
 * Implements those methods from {@link IInvoiceDAO} that are DB decoupled.
 * 
 * @author ts
 * 
 */
public abstract class AbstractInvoiceDAO implements IInvoiceDAO
{

	@Override
	public BigDecimal retrieveOpenAmt(final org.compiere.model.I_C_Invoice invoice)
	{
		return Services.get(IAllocationDAO.class).retrieveOpenAmt(invoice, true);
	}

	@Override
	public List<I_C_InvoiceLine> retrieveLines(final org.compiere.model.I_C_Invoice invoice, final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final int invoiceId = invoice.getC_Invoice_ID();
		return retrieveLines(ctx, invoiceId, trxName);
	}

	@Override
	public List<I_C_InvoiceLine> retrieveLines(final org.compiere.model.I_C_Invoice invoice)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		final int invoiceId = invoice.getC_Invoice_ID();
		return retrieveLines(ctx, invoiceId, trxName);
	}

	@Cached(cacheName = I_C_InvoiceLine.Table_Name + "#By#C_Invoice_ID")
	protected List<I_C_InvoiceLine> retrieveLines(@CacheCtx final Properties ctx, final int invoiceId, @CacheTrx final String trxName)
	{
		return retrieveLinesQuery(ctx, invoiceId, trxName)
				.create()
				.list(I_C_InvoiceLine.class);
	}

	private final IQueryBuilder<I_C_InvoiceLine> retrieveLinesQuery(final Properties ctx, final int invoiceId, final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_InvoiceLine.class, ctx, trxName)
				// FIXME find out if this needs to return *all* lines or just active ones
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				//
				.orderBy()
				.addColumn(I_C_InvoiceLine.COLUMNNAME_Line)
				.endOrderBy()
		//
		;
	}

	@Override
	public IQueryBuilder<I_C_InvoiceLine> retrieveLinesQuery(final org.compiere.model.I_C_Invoice invoice)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		final int invoiceId = invoice.getC_Invoice_ID();
		return retrieveLinesQuery(ctx, invoiceId, trxName);
	}

	@Override
	public I_C_InvoiceLine retrieveReversalLine(final I_C_InvoiceLine line, final int reversalInvoiceId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_InvoiceLine.class, line)
				.filter(new EqualsQueryFilter<I_C_InvoiceLine>(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, reversalInvoiceId))
				.filter(new EqualsQueryFilter<I_C_InvoiceLine>(I_C_InvoiceLine.COLUMNNAME_Line, line.getLine()))
				.create()
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(I_C_InvoiceLine.class);
	}

	@Override
	public List<I_C_InvoiceLine> retrieveLines(final I_M_InOutLine inoutLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(inoutLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(inoutLine);

		final IQueryBuilder<I_C_InvoiceLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_InvoiceLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.filter(new EqualsQueryFilter<I_C_InvoiceLine>(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID, inoutLine.getM_InOutLine_ID()));

		queryBuilder.orderBy()
				.addColumn(I_C_InvoiceLine.COLUMNNAME_Line);

		return queryBuilder.create().list(I_C_InvoiceLine.class);
	}

	@Override
	public List<I_C_Invoice> retrievePostedWithoutFactAcct(final Properties ctx, final Date startTime)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final IQueryBuilder<I_C_Invoice> queryBuilder = queryBL.createQueryBuilder(I_C_Invoice.class, ctx, trxName)
				.addOnlyActiveRecordsFilter();

		queryBuilder
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_Posted, true) // Posted
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_Processed, true) // Processed
				.addInArrayOrAllFilter(I_C_Invoice.COLUMNNAME_DocStatus, IDocument.STATUS_Closed, IDocument.STATUS_Completed); // DocStatus in ('CO', 'CL')

		// Exclude the entries that don't have either GrandTotal or TotalLines. These entries will produce 0 in posting
		final ICompositeQueryFilter<I_C_Invoice> nonZeroFilter = queryBL.createCompositeQueryFilter(I_C_Invoice.class).setJoinOr()
				.addNotEqualsFilter(I_C_Invoice.COLUMNNAME_GrandTotal, BigDecimal.ZERO)
				.addNotEqualsFilter(I_C_Invoice.COLUMNNAME_TotalLines, BigDecimal.ZERO);

		queryBuilder.filter(nonZeroFilter);

		// Only the documents created after the given start time
		if (startTime != null)
		{
			queryBuilder.addCompareFilter(I_C_Invoice.COLUMNNAME_Created, Operator.GREATER_OR_EQUAL, startTime);
		}

		// Check if there are fact accounts created for each document
		final IQueryBuilder<I_Fact_Acct> factAcctQuery = queryBL.createQueryBuilder(I_Fact_Acct.class, ctx, trxName)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_Invoice.class));

		queryBuilder
				.addNotInSubQueryFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, I_Fact_Acct.COLUMNNAME_Record_ID, factAcctQuery.create()) // has no accounting
		;

		return queryBuilder
				.create()
				.list();

	}
	
	
	@Override
	public Iterator<I_C_Invoice> retrieveCreditMemosForInvoice(final I_C_Invoice invoice)
	{
		List<I_C_Invoice> creditMemos = new ArrayList<>();

		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		final Iterator<I_C_Invoice> referencesForInvoice = retrieveReferencesForInvoice(invoice);

		while (referencesForInvoice.hasNext())
		{
			final I_C_Invoice referencedInvoice = referencesForInvoice.next();

			if (invoiceBL.isCreditMemo(referencedInvoice))
			{
				creditMemos.add(referencedInvoice);
			}
		}

		return creditMemos.iterator();
	}

	@Override
	public Iterator<I_C_Invoice> retrieveAdjustmentChargesForInvoice(final I_C_Invoice invoice)
	{
		List<I_C_Invoice> adjustmentCharges = new ArrayList<>();

		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		final Iterator<I_C_Invoice> referencesForInvoice = retrieveReferencesForInvoice(invoice);

		while (referencesForInvoice.hasNext())
		{
			final I_C_Invoice referencedInvoice = referencesForInvoice.next();

			if (invoiceBL.isAdjustmentCharge(referencedInvoice))
			{
				adjustmentCharges.add(referencedInvoice);
			}
		}

		return adjustmentCharges.iterator();
	}

	
	private Iterator<I_C_Invoice> retrieveReferencesForInvoice(final I_C_Invoice invoice)
	{
		// services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_C_Invoice.class, invoice)
				.filterByClientId()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_Ref_Invoice_ID, invoice.getC_Invoice_ID())

				.create()
				.iterate(I_C_Invoice.class);
	}
}
