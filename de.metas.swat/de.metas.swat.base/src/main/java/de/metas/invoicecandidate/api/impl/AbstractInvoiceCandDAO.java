package de.metas.invoicecandidate.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.IQueryOrderByBuilder;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ICurrencyConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_InvoiceCandidate_InOutLine;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateQuery;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;

public abstract class AbstractInvoiceCandDAO implements IInvoiceCandDAO
{
	@Override
	public Iterator<I_C_Invoice_Candidate> retrieveIcForSelection(final Properties ctx, final int AD_PInstance_ID, final String trxName)
	{
		// Note that we can't filter by IsError in the where clause, because it wouldn't work with pagination.
		// Background is that the number of candidates with "IsError=Y" might increase during the run.

		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setContext(ctx, trxName)
				.setOnlySelection(AD_PInstance_ID);

		return retrieveInvoiceCandidates(queryBuilder);
	}

	@Override
	public Iterator<I_C_Invoice_Candidate> retrieveNonProcessed(final PlainContextAware plainContextAware)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setContext(plainContextAware)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false);

		queryBuilder.orderBy()
				.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID);

		return queryBuilder
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 2000)
				.iterate(I_C_Invoice_Candidate.class);
	}

	@Override
	public <T extends I_C_Invoice_Candidate> Iterator<T> retrieveInvoiceCandidates(final IQueryBuilder<T> queryBuilder)
	{
		Check.assumeNotNull(queryBuilder, "queryBuilder not null");

		//
		// Make sure we are retrieving in a order which is friendly for processing
		final IQueryOrderByBuilder<T> orderBy = queryBuilder.orderBy();
		orderBy
				.clear()

				// order by they header aggregation key to make sure candidates with the same key end up in the same invoice
				.addColumn(I_C_Invoice_Candidate.COLUMNNAME_HeaderAggregationKey)
				// task 08241: return ICs with a set Bill_User_ID first, because, we can aggregate ICs with different Bill_User_IDs into one invoice, however, if there are any ICs with a Bill_User_ID
				// set, and others with no Bill_User_ID, then we want the Bill_User_ID to end up in the C_Invoice (header) record.
				.addColumn(I_C_Invoice_Candidate.COLUMNNAME_Bill_User_ID, Direction.Ascending, Nulls.Last)
				.addColumn(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID);
		//
		// Retrieve invoice candidates
		return queryBuilder.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(queryBuilder.getModelClass());
	}

	@Override
	public List<I_C_Invoice_Candidate> retrieveReferencing(final Object model)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		return fetchInvoiceCandidates(ctx, tableName, recordId, trxName);
	}

	@Override
	public BigDecimal retrieveInvoicableAmount(final I_C_BPartner billBPartner, final Timestamp date)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(billBPartner);
		final Properties ctx = InterfaceWrapperHelper.getCtx(billBPartner);

		final IInvoiceCandidateQuery query = newInvoiceCandidateQuery();
		query.setBill_BPartner_ID(billBPartner.getC_BPartner_ID());
		query.setDateToInvoice(date);

		final int targetCurrencyId = Services.get(ICurrencyConversionBL.class).getBaseCurrency(ctx).getC_Currency_ID();
		final int adClientId = billBPartner.getAD_Client_ID();
		final int adOrgId = billBPartner.getAD_Org_ID();

		return retrieveInvoicableAmount(ctx, query, targetCurrencyId, adClientId, adOrgId, I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice, trxName);
	}

	@Override
	public IInvoiceCandidateQuery newInvoiceCandidateQuery()
	{
		return new InvoiceCandidateQuery();
	}

	@Override
	public <T extends org.compiere.model.I_C_Invoice> Map<Integer, T> retrieveInvoices(final Properties ctx, final String tableName, final int recordId, final Class<T> clazz,
			final boolean onlyUnpaid, final String trxName)
	{
		final Map<Integer, T> openInvoices = new HashMap<Integer, T>();

		final List<I_C_Invoice_Candidate> icsForCurrentTerm = fetchInvoiceCandidates(ctx, tableName, recordId, trxName);
		Check.assumeNotNull(icsForCurrentTerm, "the method might return the empty list, but not null");

		for (final I_C_Invoice_Candidate ic : icsForCurrentTerm)
		{
			final List<I_C_InvoiceLine> iclList = Services.get(IInvoiceCandDAO.class).retrieveIlForIc(ic);

			for (final I_C_InvoiceLine il : iclList)
			{
				final T invoice = InterfaceWrapperHelper.create(il.getC_Invoice(), clazz);
				// 04022 : Changed method to allow retrieval of all invoices
				if (!onlyUnpaid || !invoice.isPaid())
				{
					openInvoices.put(invoice.getC_Invoice_ID(), invoice);
				}
			}
		}

		return openInvoices;
	}

	@Override
	public I_C_Invoice_Line_Alloc retrieveIlaForIcAndIl(final I_C_Invoice_Candidate invoiceCand, final org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		// @formatter:off
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Line_Alloc.class)
					.setContext(invoiceCand)
					.filter(new EqualsQueryFilter<I_C_Invoice_Line_Alloc>(I_C_Invoice_Line_Alloc.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCand.getC_Invoice_Candidate_ID()))
					.filter(new EqualsQueryFilter<I_C_Invoice_Line_Alloc>(I_C_Invoice_Line_Alloc.COLUMNNAME_C_InvoiceLine_ID, invoiceLine.getC_InvoiceLine_ID()))
					.filter(ActiveRecordQueryFilter.getInstance(I_C_Invoice_Line_Alloc.class))
					.filterByClientId()
				.create()
				.firstOnly(I_C_Invoice_Line_Alloc.class);
		// @formatter:on
	}

	protected abstract int nextRecomputeId();

	@Override
	public <T extends org.compiere.model.I_M_InOutLine> List<T> retrieveInOutLinesForCandidate(final I_C_Invoice_Candidate ic, final Class<T> clazz)
	{
		Check.assumeNotNull(ic, "ic not null");
		Check.assumeNotNull(clazz, "clazz not null");

		// FIXME debug to see why c_invoicecandidate_inoutline have duplicates and take the inoutlines from there
		// for now take it via orderline
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_InOutLine> queryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class)
				.setContext(ic)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID, ic.getC_OrderLine_ID())
				.addOnlyActiveRecordsFilter();

		// Order by M_InOut_ID, M_InOutLine_ID, just to have a predictible order
		queryBuilder.orderBy()
				.addColumn(I_M_InOutLine.COLUMNNAME_M_InOut_ID)
				.addColumn(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);

		return queryBuilder
				.create()
				.list(clazz);
	}

	@Override
	public List<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForOrderLine(final I_C_OrderLine orderLine)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate.class)
				.setContext(orderLine)
				.filter(new EqualsQueryFilter<I_C_Invoice_Candidate>(I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID()))
				.filter(ActiveRecordQueryFilter.getInstance(I_C_Invoice_Candidate.class));

		return queryBuilder.create()
				.list(I_C_Invoice_Candidate.class);
	}

	@Override
	public boolean existsInvoiceCandidateInOutLinesForInvoiceCandidate(final I_C_Invoice_Candidate ic, final I_M_InOutLine iol)
	{
		final IQueryBuilder<I_C_InvoiceCandidate_InOutLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
				.setContext(ic)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID, ic.getC_Invoice_Candidate_ID())
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_M_InOutLine_ID, iol.getM_InOutLine_ID())
				.addOnlyActiveRecordsFilter();

		return queryBuilder.create().match();
	}

	@Override
	public List<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsForInvoiceCandidate(final I_C_Invoice_Candidate invoiceCandidate)
	{
		final boolean onlyActive = true;
		return retrieveICIOLAssociationsForInvoiceCandidate(invoiceCandidate, onlyActive);
	}

	@Override
	public List<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsForInvoiceCandidateInclInactive(final I_C_Invoice_Candidate invoiceCandidate)
	{
		final boolean onlyActive = false;
		return retrieveICIOLAssociationsForInvoiceCandidate(invoiceCandidate, onlyActive);
	}

	private List<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsForInvoiceCandidate(final I_C_Invoice_Candidate invoiceCandidate, final boolean onlyActive)
	{
		final IQueryBuilder<I_C_InvoiceCandidate_InOutLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
				.setContext(invoiceCandidate)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidate.getC_Invoice_Candidate_ID());

		if (onlyActive)
		{
			queryBuilder.addOnlyActiveRecordsFilter();
		}

		queryBuilder.orderBy()
				.addColumn(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_M_InOutLine_ID);

		return queryBuilder.create()
				.list(I_C_InvoiceCandidate_InOutLine.class);
	}

	@Override
	public List<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsForInOutLineInclInactive(final I_M_InOutLine inOutLine)
	{
		final IQueryBuilder<I_C_InvoiceCandidate_InOutLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
				.setContext(inOutLine)
				.filter(new EqualsQueryFilter<I_C_InvoiceCandidate_InOutLine>(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_M_InOutLine_ID, inOutLine.getM_InOutLine_ID()));
		queryBuilder.orderBy()
				.addColumn(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_M_InOutLine_ID);

		return queryBuilder.create()
				.list(I_C_InvoiceCandidate_InOutLine.class);
	}

	@Override
	public List<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForInOutLine(final I_M_InOutLine inOutLine)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_C_Invoice_Candidate> iolreferenceFilter = queryBL.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, Services.get(IADTableDAO.class).retrieveTableId(I_M_InOutLine.Table_Name))
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, inOutLine.getM_InOutLine_ID());

		final ICompositeQueryFilter<I_C_Invoice_Candidate> olReferenceFilter = queryBL.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID, inOutLine.getC_OrderLine_ID());

		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setContext(inOutLine)
				.setJoinOr()
				.filter(iolreferenceFilter)
				.filter(olReferenceFilter);

		return queryBuilder
				.create()
				.list(I_C_Invoice_Candidate.class);
	}

	@Override
	public void save(I_C_Invoice_Candidate invoiceCandidate)
	{
		try
		{
			InterfaceWrapperHelper.save(invoiceCandidate);
		}
		catch (Exception e)
		{
			// If we got an error while saving a new IC, we can do nothing
			if (invoiceCandidate.getC_Invoice_Candidate_ID() <= 0)
			{
				throw e instanceof AdempiereException ? (AdempiereException)e : new AdempiereException(e.getLocalizedMessage(), e);
			}

			// If we don't have an error already set, we are setting the one that we just got it
			if (!invoiceCandidate.isError())
			{
				Services.get(IInvoiceCandBL.class).setError(invoiceCandidate, e);
			}

			saveErrorToDB(invoiceCandidate);
		}
	}

	protected abstract void saveErrorToDB(final I_C_Invoice_Candidate ic);
}
