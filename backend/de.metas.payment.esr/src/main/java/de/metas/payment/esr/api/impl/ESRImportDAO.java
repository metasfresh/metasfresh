package de.metas.payment.esr.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.adempiere.util.comparator.ComparatorChain;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.BankStatementLineId;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.invoice_gateway.spi.model.InvoiceId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.ESRImportId;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.security.permissions.Access;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

public class ESRImportDAO implements IESRImportDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Used to order lines by <code>LineNo, ESR_ImportLine_ID</code>.
	 */
	private final ComparatorChain<I_ESR_ImportLine> esrImportLineDefaultComparator = new ComparatorChain<I_ESR_ImportLine>()
			.addComparator(new AccessorComparator<>(new ComparableComparator<>(), o -> ((I_ESR_ImportLine)o).getLineNo()))
			.addComparator(new AccessorComparator<>(new ComparableComparator<>(), o -> ((I_ESR_ImportLine)o).getESR_ImportLine_ID()));

	@Override
	public void save(@NonNull final I_ESR_Import esrImport)
	{
		saveRecord(esrImport);
	}

	@Override
	public void saveOutOfTrx(@NonNull final I_ESR_Import esrImport)
	{
		InterfaceWrapperHelper.save(esrImport, ITrx.TRXNAME_None);
	}

	@Override
	public void save(@NonNull final I_ESR_ImportLine esrImportLine)
	{
		saveRecord(esrImportLine);
	}
	
	@Override
	public List<I_ESR_Import> getByIds(@NonNull final Set<ESRImportId> esrImportIds)
	{
		if (esrImportIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_ESR_Import.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_ESR_Import.COLUMNNAME_ESR_Import_ID, esrImportIds)
				.create()
				.list();
	}

	@Override
	public List<I_ESR_ImportLine> retrieveLinesForInvoice(final I_ESR_ImportLine esrImportLine, final I_C_Invoice invoice)
	{
		final ArrayList<I_ESR_ImportLine> linesFromDB = new ArrayList<>(fetchLinesForInvoice(esrImportLine.getESR_Import(), invoice));

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

	private List<I_ESR_ImportLine> fetchLinesForInvoice(final I_ESR_Import esrImport, final I_C_Invoice invoice)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);
		final String trxName = InterfaceWrapperHelper.getTrxName(esrImport);
		final ESRImportId esrImportId = ESRImportId.ofRepoId(esrImport.getESR_Import_ID());
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		return queryBL.createQueryBuilder(I_ESR_ImportLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ESR_ImportLine.COLUMNNAME_ESR_Import_ID, esrImportId)
				.addEqualsFilter(I_ESR_ImportLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				.orderBy(I_ESR_ImportLine.COLUMNNAME_LineNo)
				.orderBy(I_ESR_ImportLine.COLUMNNAME_ESR_ImportLine_ID) // LineNo should suffice, but who knows :-)
				.create()
				.list();
	}

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
	public List<I_ESR_ImportLine> retrieveLines(@NonNull final I_ESR_Import esrImport)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);
		final String trxName = InterfaceWrapperHelper.getTrxName(esrImport);
		final ESRImportId esrImportId = ESRImportId.ofRepoId(esrImport.getESR_Import_ID());
		return retrieveLines(ctx, esrImportId, trxName);
	}

	@Override
	public List<I_ESR_ImportLine> retrieveLines(@NonNull final ESRImportId esrImportId)
	{
		return retrieveLines(Env.getCtx(), esrImportId, ITrx.TRXNAME_ThreadInherited);
	}

	private List<I_ESR_ImportLine> retrieveLines(final Properties ctx, final ESRImportId esrImportId, final String trxName)
	{
		return queryBL.createQueryBuilder(I_ESR_ImportLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ESR_ImportLine.COLUMNNAME_ESR_Import_ID, esrImportId)
				.orderBy(I_ESR_ImportLine.COLUMNNAME_LineNo)
				.orderBy(I_ESR_ImportLine.COLUMNNAME_ESR_ImportLine_ID) // LineNo should suffice, but who knows :-)
				.create()
				.list();
	}

	@Override
	public List<I_ESR_ImportLine> retrieveLines(@NonNull final Collection<PaymentId> paymentIds)
	{
		if (paymentIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_ESR_ImportLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_ESR_ImportLine.COLUMNNAME_C_Payment_ID, paymentIds)
				.orderBy(I_ESR_ImportLine.COLUMNNAME_ESR_Import_ID)
				.orderBy(I_ESR_ImportLine.COLUMNNAME_LineNo)
				.orderBy(I_ESR_ImportLine.COLUMNNAME_ESR_ImportLine_ID)
				.create()
				.list();
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
		final List<I_C_ReferenceNo_Doc> invoiceDocs = new ArrayList<>();
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

		final I_C_ReferenceNo referenceNoRecord = queryBL.createQueryBuilder(I_C_ReferenceNo.class)
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
	public List<I_ESR_ImportLine> retrieveAllLinesByBankStatementLineIds(@NonNull final Collection<BankStatementLineId> bankStatementLineIds)
	{
		if (bankStatementLineIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_ESR_ImportLine.class)
				.addInArrayFilter(I_ESR_ImportLine.COLUMNNAME_C_BankStatementLine_ID, bankStatementLineIds)
				// .addOnlyActiveRecordsFilter()
				.create()
				.list(I_ESR_ImportLine.class);
	}

	@Override
	public List<I_ESR_ImportLine> retrieveAllLinesByBankStatementLineRefId(@NonNull final BankStatementAndLineAndRefId bankStatementLineRefId)
	{
		return queryBL.createQueryBuilder(I_ESR_ImportLine.class)
				.addEqualsFilter(I_ESR_ImportLine.COLUMNNAME_C_BankStatementLine_Ref_ID, bankStatementLineRefId.getBankStatementLineRefId())
				// .addOnlyActiveRecordsFilter()
				.create()
				.list(I_ESR_ImportLine.class);
	}

	@Override
	public Iterator<I_ESR_Import> retrieveESRImports(final Properties ctx, final int orgId)
	{
		return queryBL.createQueryBuilder(I_ESR_Import.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ESR_Import.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.iterate(I_ESR_Import.class);
	}

	@Override
	public I_ESR_Import retrieveESRImportForPayment(I_C_Payment payment)
	{
		return queryBL.createQueryBuilder(I_ESR_ImportLine.class, payment)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ESR_ImportLine.COLUMNNAME_C_Payment_ID, payment.getC_Payment_ID())
				//
				.andCollect(I_ESR_ImportLine.COLUMN_ESR_Import_ID)
				.addEqualsFilter(I_ESR_Import.COLUMN_Processed, true)
				//
				.create()
				.firstOnlyOrNull(I_ESR_Import.class);
	}

	@Override
	public int countLines(@NonNull final I_ESR_Import esrImport, @Nullable final Boolean processed)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);
		final String trxName = InterfaceWrapperHelper.getTrxName(esrImport);
		final ESRImportId esrImportId = ESRImportId.ofRepoId(esrImport.getESR_Import_ID());

		final IQueryBuilder<I_ESR_ImportLine> queryBuilder = queryBL.createQueryBuilder(I_ESR_ImportLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ESR_ImportLine.COLUMNNAME_ESR_Import_ID, esrImportId);

		if (processed != null)
		{
			queryBuilder.addEqualsFilter(I_ESR_ImportLine.COLUMNNAME_Processed, processed);
		}

		return queryBuilder.create().count();
	}

	@Override
	public I_ESR_ImportLine fetchLineForESRLineText(@NonNull final I_ESR_Import esrImport, @NonNull final String esrImportLineText)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);
		final String trxName = InterfaceWrapperHelper.getTrxName(esrImport);
		final ESRImportId esrImportId = ESRImportId.ofRepoId(esrImport.getESR_Import_ID());
		final String strippedText = esrImportLineText.trim();

		return queryBL.createQueryBuilder(I_ESR_ImportLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ESR_ImportLine.COLUMNNAME_ESR_Import_ID, esrImportId)
				.addStringLikeFilter(I_ESR_ImportLine.COLUMNNAME_ESRLineText, strippedText, /* ignoreCase */true)
				.create()
				.firstOnly(I_ESR_ImportLine.class);
	}

}
