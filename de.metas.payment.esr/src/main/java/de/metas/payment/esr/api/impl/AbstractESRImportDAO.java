package de.metas.payment.esr.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.adempiere.util.comparator.ComparatorChain;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.organization.OrgId;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.security.permissions.Access;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.TypedAccessor;
import lombok.NonNull;

public abstract class AbstractESRImportDAO implements IESRImportDAO
{
	/**
	 * Used to order lines by <code>LineNo, ESR_ImportLine_ID</code>.
	 */
	protected final ComparatorChain<I_ESR_ImportLine> esrImportLineDefaultComparator = new ComparatorChain<I_ESR_ImportLine>()
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
	public I_C_ReferenceNo_Doc retrieveESRInvoiceReferenceNumberDocument(
			@NonNull final OrgId orgId,
			@NonNull final String esrReferenceNumber)
	{
		final I_C_ReferenceNo referenceNo = fetchESRInvoiceReferenceNumber(esrReferenceNumber, orgId);

		if (referenceNo == null)
		{
			Loggables.addLog("Found no C_ReferenceNo record for esrReferenceNumber={}", esrReferenceNumber);
			return null;
		}


		final int invoiceTableID = getTableId(I_C_Invoice.class);

		final List<I_C_ReferenceNo_Doc> docs = Services.get(IReferenceNoDAO.class).retrieveDocAssignments(referenceNo);
		final List<I_C_ReferenceNo_Doc> invoiceDocs = new ArrayList<I_C_ReferenceNo_Doc>();
		for (final I_C_ReferenceNo_Doc doc : docs)
		{
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

	private I_C_ReferenceNo fetchESRInvoiceReferenceNumber(@NonNull final String esrReferenceNumber, @NonNull final OrgId orgId)
	{
		final IReferenceNoDAO refNoDAO = Services.get(IReferenceNoDAO.class);
		final I_C_ReferenceNo_Type refNoType = refNoDAO.retrieveRefNoTypeByName(ESRConstants.DOCUMENT_REFID_ReferenceNo_Type_InvoiceReferenceNumber);

		// Use wild cards because we won't match after the bank account no (first digits) and the check digit (the last one)
		final String esrReferenceNoToMatch = "%" + esrReferenceNumber + "_";

		final I_C_ReferenceNo referenceNoRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_ReferenceNo.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_ReferenceNo.COLUMNNAME_ReferenceNo, Operator.STRING_LIKE, esrReferenceNoToMatch)
				.addEqualsFilter(I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_Type_ID, refNoType.getC_ReferenceNo_Type_ID())
				.addInArrayFilter(I_C_ReferenceNo_Type.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY) // Note that we do need to filter by AD_Org_ID, because 'esrReferenceNumber' is not guaranteed to be unique!
				.create()
				.setRequiredAccess(Access.READ)
				.firstOnly(I_C_ReferenceNo.class);  // unique constraint uc_referenceno_and_type

		return referenceNoRecord;
	}

	@Override
	public List<I_ESR_ImportLine> retrieveLinesForBankStatementLine(final I_C_BankStatementLine line)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_ESR_ImportLine.class, line)
				.addEqualsFilter(I_ESR_ImportLine.COLUMN_C_BankStatementLine_ID, line.getC_BankStatementLine_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
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
