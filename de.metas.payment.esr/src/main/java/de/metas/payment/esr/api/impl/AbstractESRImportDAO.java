package de.metas.payment.esr.api.impl;

/*
 * #%L
 * de.metas.payment.esr
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.adempiere.util.comparator.ComparatorChain;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.Services;
import de.metas.util.TypedAccessor;

public abstract class AbstractESRImportDAO implements IESRImportDAO
{
	/**
	 * Used to order lines by <code>LineNo, ESR_ImportLine_ID</code>.
	 */
	protected final ComparatorChain<I_ESR_ImportLine> esrImportLineDefaultComparator =
			new ComparatorChain<I_ESR_ImportLine>()
					.addComparator(
							new AccessorComparator<I_ESR_ImportLine, Integer>(
									new ComparableComparator<Integer>(),
									new TypedAccessor<Integer>()
									{
										@Override
										public Integer getValue(Object o)
										{
											return ((I_ESR_ImportLine)o).getLineNo();
										}
									}))
					.addComparator(
							new AccessorComparator<I_ESR_ImportLine, Integer>(
									new ComparableComparator<Integer>(),
									new TypedAccessor<Integer>()
									{
										@Override
										public Integer getValue(Object o)
										{
											return ((I_ESR_ImportLine)o).getESR_ImportLine_ID();
										}
									}));

	@Override
	public List<I_ESR_ImportLine> retrieveLinesForInvoice(final I_ESR_ImportLine esrImportLine, final I_C_Invoice invoice)
	{
		final List<I_ESR_ImportLine> linesFromDB = fetchLinesForInvoice(esrImportLine.getESR_Import(), invoice);

		// check if a line with the given ID was loaded from the DB. If that's the case, replace it with the given 'esrImportLine'.
		boolean lineReplaced = false;
		for (int i = 0; i < linesFromDB.size(); i++)
		{
			if (linesFromDB.get(i).getESR_ImportLine_ID() == esrImportLine.getESR_ImportLine_ID())
			{
				linesFromDB.set(i, esrImportLine);
				lineReplaced = true;
			}
		}
		if (!lineReplaced)
		{
			// the given 'esrImportLine' was not loaded from DB, maybe because the invoice was just set, but not saved (or will be set soon!)
			linesFromDB.add(esrImportLine);
			Collections.sort(linesFromDB, esrImportLineDefaultComparator);
		}
		return linesFromDB;
	}

	abstract List<I_ESR_ImportLine> fetchLinesForInvoice(final I_ESR_Import esrImport, final I_C_Invoice invoice);

	@Override
	public void deleteLines(I_ESR_Import esrImport)
	{
		final List<I_ESR_ImportLine> esrLines = retrieveLines(esrImport);

		for (I_ESR_ImportLine line : esrLines)
		{
			InterfaceWrapperHelper.delete(line);
		}
	}

	@Override
	public List<I_ESR_ImportLine> retrieveLines(final I_ESR_Import esrImport)
	{
		return retrieveLinesForTrxTypes(esrImport, null);
	}

	@Override
	public I_C_ReferenceNo_Doc retrieveESRInvoiceReferenceNumberDocument(final Properties ctx, final String esrReferenceNumber)
	{
		final I_C_ReferenceNo referenceNo = fetchESRInvoiceReferenceNumber(ctx, esrReferenceNumber);

		if (referenceNo == null)
		{
			return null;
		}

		final int invoiceTableID = Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name);

		final List<I_C_ReferenceNo_Doc> docs = Services.get(IReferenceNoDAO.class).retrieveAllDocAssignments(referenceNo);
		final List<I_C_ReferenceNo_Doc> invoiceDocs = new ArrayList<I_C_ReferenceNo_Doc>();
		for (final I_C_ReferenceNo_Doc doc : docs)
		{
			final int adClientId = doc.getAD_Client_ID();
			final int adOrgId = doc.getAD_Org_ID();
			if (adClientId != 0 && adClientId != Env.getAD_Client_ID(ctx))
			{
				continue;
			}

			if (adOrgId != 0 && adOrgId != Env.getAD_Org_ID(ctx))
			{
				continue;
			}

			if (doc.getAD_Table_ID() != invoiceTableID)
			{
				continue;
			}

			invoiceDocs.add(doc);
		}

		if (invoiceDocs.isEmpty())
		{
			return null;
		}
		else if (invoiceDocs.size() > 1)
		{
			throw new AdempiereException("More then one assigned invoice found for " + esrReferenceNumber + " (" + referenceNo + ")");
		}

		return invoiceDocs.get(0);
	}

	protected abstract I_C_ReferenceNo fetchESRInvoiceReferenceNumber(@CacheCtx final Properties ctx, final String esrReferenceNumber);

	@Override
	public List<I_ESR_ImportLine> retrieveLinesForBankStatementLine(final I_C_BankStatementLine line)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_ESR_ImportLine.class, line)
				.addEqualsFilter(I_ESR_ImportLine.COLUMN_C_BankStatementLine_ID, line.getC_BankStatementLine_ID())
				.create()
				.list(I_ESR_ImportLine.class);
	}

	@Override
	public List<I_ESR_ImportLine> retrieveAllLinesForBankStatementLineRef(final I_C_BankStatementLine_Ref lineRef)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_ESR_ImportLine.class, lineRef)
				.addEqualsFilter(I_ESR_ImportLine.COLUMN_C_BankStatementLine_Ref_ID, lineRef.getC_BankStatementLine_Ref_ID())
				.create()
				.list(I_ESR_ImportLine.class);
	}

	@Override
	public I_ESR_Import retrieveESRImportForPayment(I_C_Payment payment)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_ESR_ImportLine.class, payment)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ESR_ImportLine.COLUMN_C_Payment_ID, payment.getC_Payment_ID())
				//
				.andCollect(I_ESR_ImportLine.COLUMN_ESR_Import_ID)
				.addEqualsFilter(I_ESR_Import.COLUMN_Processed, true)
				//
				.create()
				.firstOnlyOrNull(I_ESR_Import.class);
	}
}
