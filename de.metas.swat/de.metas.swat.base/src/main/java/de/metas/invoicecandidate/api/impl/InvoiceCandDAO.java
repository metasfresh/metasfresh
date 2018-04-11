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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.IQueryOrderByBuilder;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheModel;
import de.metas.adempiere.util.CacheTrx;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.currency.ICurrencyBL;
import de.metas.document.engine.IDocumentBL;
import de.metas.inout.IInOutDAO;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandRecomputeTagger;
import de.metas.invoicecandidate.api.IInvoiceCandUpdateSchedulerRequest;
import de.metas.invoicecandidate.api.IInvoiceCandUpdateSchedulerService;
import de.metas.invoicecandidate.api.IInvoiceCandidateQuery;
import de.metas.invoicecandidate.api.InvoiceCandRecomputeTag;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.invoicecandidate.model.I_C_Invoice_Detail;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.invoicecandidate.model.I_M_InventoryLine;
import de.metas.invoicecandidate.model.I_M_ProductGroup;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.process.IADPInstanceDAO;
import lombok.NonNull;

public class InvoiceCandDAO implements IInvoiceCandDAO
{
	private final transient Logger logger = InvoiceCandidate_Constants.getLogger(InvoiceCandDAO.class);

	private static final ModelDynAttributeAccessor<I_C_Invoice_Candidate, Boolean> DYNATTR_IC_Avoid_Recreate //
			= new ModelDynAttributeAccessor<>(IInvoiceCandDAO.class.getName() + "Avoid_Recreate", Boolean.class);

	@Override
	public final Iterator<I_C_Invoice_Candidate> retrieveIcForSelection(final Properties ctx, final int AD_PInstance_ID, final String trxName)
	{
		// Note that we can't filter by IsError in the where clause, because it wouldn't work with pagination.
		// Background is that the number of candidates with "IsError=Y" might increase during the run.

		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class, ctx, trxName)
				.setOnlySelection(AD_PInstance_ID);

		return retrieveInvoiceCandidates(queryBuilder);
	}

	@Override
	public final Iterator<I_C_Invoice_Candidate> retrieveNonProcessed(final IContextAware contextAware)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class, contextAware)
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
	public <T extends I_C_Invoice_Candidate> Iterator<T> retrieveInvoiceCandidates(
			@NonNull final IQueryBuilder<T> queryBuilder)
	{
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

		final int targetCurrencyId = Services.get(ICurrencyBL.class).getBaseCurrency(ctx).getC_Currency_ID();
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
	public <T extends org.compiere.model.I_C_Invoice> Map<Integer, T> retrieveInvoices(
			final Properties ctx,
			final String tableName,
			final int recordId,
			final Class<T> clazz,
			final boolean onlyUnpaid,
			final String trxName)
	{
		final Map<Integer, T> openInvoices = new HashMap<>();

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
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Line_Alloc.class, invoiceCand)
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID, invoiceCand.getC_Invoice_Candidate_ID())
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID, invoiceLine.getC_InvoiceLine_ID())
				.addOnlyActiveRecordsFilter()
				.filterByClientId()
				//
				.create()
				.firstOnly(I_C_Invoice_Line_Alloc.class);
		// @formatter:on
	}

	@Override
	public <T extends org.compiere.model.I_M_InOutLine> List<T> retrieveInOutLinesForCandidate(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final Class<T> clazz)
	{
		// FIXME debug to see why c_invoicecandidate_inoutline have duplicates and take the inoutlines from there
		// for now take it via orderline
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_InOutLine> queryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class, ic)
				.addEqualsFilter(I_M_InOutLine.COLUMN_C_OrderLine_ID, ic.getC_OrderLine_ID())
				.addOnlyActiveRecordsFilter();

		// Order by M_InOut_ID, M_InOutLine_ID, just to have a predictible order
		queryBuilder.orderBy()
				.addColumn(I_M_InOutLine.COLUMN_M_InOut_ID)
				.addColumn(I_M_InOutLine.COLUMN_M_InOutLine_ID);

		return queryBuilder
				.create()
				.list(clazz);
	}

	@Override
	public List<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForOrderLine(final I_C_OrderLine orderLine)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class, orderLine)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_C_OrderLine_ID, orderLine.getC_OrderLine_ID())
				.addOnlyActiveRecordsFilter()
				//
				.create()
				.list(I_C_Invoice_Candidate.class);
	}

	@Override
	public boolean existsInvoiceCandidateInOutLinesForInvoiceCandidate(final I_C_Invoice_Candidate ic, final I_M_InOutLine iol)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class, ic)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMN_C_Invoice_Candidate_ID, ic.getC_Invoice_Candidate_ID())
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMN_M_InOutLine_ID, iol.getM_InOutLine_ID())
				.addOnlyActiveRecordsFilter()
				//
				.create()
				.match();
	}

	@Cached(cacheName = I_C_InvoiceCandidate_InOutLine.Table_Name + "#by#" + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
	@Override
	public List<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsExclRE(@CacheModel final I_C_Invoice_Candidate invoiceCandidate)
	{
		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);

		// load all I_C_InvoiceCandidate_InOutLine and filter locally.
		// i think it's safe to assume that there are not 1000s of records to load and this way the code is simpler
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<I_C_InvoiceCandidate_InOutLine> result = queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class, invoiceCandidate)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMN_C_Invoice_Candidate_ID, invoiceCandidate.getC_Invoice_Candidate_ID())
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_C_InvoiceCandidate_InOutLine.COLUMN_M_InOutLine_ID).endOrderBy()
				.create()
				.stream(I_C_InvoiceCandidate_InOutLine.class)
				.filter(iciol -> {

					final I_M_InOut inOut = iciol.getM_InOutLine().getM_InOut();

					return inOut.isActive() && docActionBL.isDocumentCompletedOrClosed(inOut);
				})
				.collect(Collectors.toList());

		return result;
	}

	@Override
	public List<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsForInOutLineInclInactive(final I_M_InOutLine inOutLine)
	{
		return retrieveICIOLAssociationsForInOutLineInclInactiveQuery(inOutLine)
				.create()
				.list(I_C_InvoiceCandidate_InOutLine.class);
	}

	private IQueryBuilder<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsForInOutLineInclInactiveQuery(final I_M_InOutLine inOutLine)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class, inOutLine)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMN_M_InOutLine_ID, inOutLine.getM_InOutLine_ID())
				//
				.orderBy()
				.addColumn(I_C_InvoiceCandidate_InOutLine.COLUMN_M_InOutLine_ID)
				.endOrderBy()
		//
		;
	}

	@Override
	public final IQuery<I_C_Invoice_Candidate> retrieveInvoiceCandidatesQueryForInOuts(final Collection<? extends I_M_InOut> inouts)
	{
		Check.assumeNotEmpty(inouts, "inouts is not empty");

		final List<I_M_InOutLine> inoutLines = Services.get(IInOutDAO.class).retrieveLinesForInOuts(inouts);
		return inoutLines.stream()
				.map(this::retrieveInvoiceCandidatesForInOutLineQuery)
				.map(IQueryBuilder::create)
				.reduce(IQuery.unionDistict())
				.get();
	}

	@Override
	public final List<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForInOutLine(final I_M_InOutLine inoutLine)
	{
		return retrieveInvoiceCandidatesForInOutLineQuery(inoutLine)
				.create()
				.list(I_C_Invoice_Candidate.class);
	}

	@Override
	public final IQueryBuilder<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForInOutLineQuery(
			@NonNull final I_M_InOutLine inoutLine)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class, inoutLine)
				// NOTE: advice the query builder to explode the expressions to SQL UNIONs because that is MUCH more efficient on PostgreSQL.
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions)
				.setJoinOr();

		//
		// ICs which are directly created for this inout line
		{
			final ICompositeQueryFilter<I_C_Invoice_Candidate> filter = queryBL.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_M_InOutLine.class))
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Record_ID, inoutLine.getM_InOutLine_ID());

			queryBuilder.filter(filter);
		}

		//
		// ICs which are created for inout line's C_OrderLine_ID
		if (inoutLine.getC_OrderLine_ID() > 0)
		{
			final ICompositeQueryFilter<I_C_Invoice_Candidate> filter = queryBL.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_OrderLine.class))
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Record_ID, inoutLine.getC_OrderLine_ID());

			queryBuilder.filter(filter);
		}

		//
		// IC-IOL associations
		{
			final IQuery<I_C_InvoiceCandidate_InOutLine> queryForICIOLs = retrieveICIOLAssociationsForInOutLineInclInactiveQuery(inoutLine)
					.addOnlyActiveRecordsFilter()
					.create();

			queryBuilder.addInSubQueryFilter(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID, I_C_InvoiceCandidate_InOutLine.COLUMN_C_Invoice_Candidate_ID, queryForICIOLs);
		}

		return queryBuilder;
	}

	@Override
	public final void save(final I_C_Invoice_Candidate invoiceCandidate)
	{
		try
		{
			InterfaceWrapperHelper.save(invoiceCandidate);
		}
		catch (final Exception saveException)
		{
			// If we got an error while saving a new IC, we can do nothing
			if (invoiceCandidate.getC_Invoice_Candidate_ID() <= 0)
			{
				throw AdempiereException.wrapIfNeeded(saveException);
			}

			// If we don't have an error already set, we are setting the one that we just got it
			if (!invoiceCandidate.isError())
			{
				Services.get(IInvoiceCandBL.class).setError(invoiceCandidate, saveException);
			}

			saveErrorToDB(invoiceCandidate);
		}
	}

	@Override
	public int deleteInvoiceDetails(final I_C_Invoice_Candidate ic)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Detail.class, ic)
				.addEqualsFilter(I_C_Invoice_Detail.COLUMN_C_Invoice_Candidate_ID, ic.getC_Invoice_Candidate_ID())
				.create()
				.delete();
	}

	@Override
	public void deleteAndAvoidRecreateScheduling(final I_C_Invoice_Candidate ic)
	{
		DYNATTR_IC_Avoid_Recreate.setValue(ic, true);
		InterfaceWrapperHelper.delete(ic);
	}

	@Override
	public boolean isAvoidRecreate(final I_C_Invoice_Candidate ic)
	{
		return DYNATTR_IC_Avoid_Recreate.is(ic, true);
	}

	@Override
	public List<I_C_Invoice_Candidate> retrieveIcForIl(final I_C_InvoiceLine invoiceLine)
	{
		final IQueryBuilder<I_C_Invoice_Line_Alloc> ilaQueryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Line_Alloc.class, invoiceLine)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID, invoiceLine.getC_InvoiceLine_ID());

		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = ilaQueryBuilder
				.andCollect(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID)
				.addOnlyActiveRecordsFilter();

		icQueryBuilder.orderBy()
				.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID);

		return icQueryBuilder
				.create()
				.list();
	}

	@Override
	public final Iterator<I_C_Invoice_Candidate> retrieveForHeaderAggregationKey(final Properties ctx, final String headerAggregationKey, final String trxName)
	{
		return retrieveForHeaderAggregationKeyQuery(ctx, headerAggregationKey, trxName)
				.create()
				.setOption(IQuery.OPTION_IteratorBufferSize, 100) // 50 is the default, but there might be orders with more than 50 lines
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true) // guaranteed=true, we can assume there won't be more than a some hundreds of invoice candidates with the same
				// headerAggregationKey
				.iterate(I_C_Invoice_Candidate.class);
	}

	private final IQueryBuilder<I_C_Invoice_Candidate> retrieveForHeaderAggregationKeyQuery(final Properties ctx, final String headerAggregationKey, final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_HeaderAggregationKey, headerAggregationKey)
				.orderBy()
				.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID)
				.endOrderBy();
	}

	@Override
	public final List<I_C_InvoiceLine> retrieveIlForIc(final I_C_Invoice_Candidate invoiceCand)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Line_Alloc.class, invoiceCand)
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID, invoiceCand.getC_Invoice_Candidate_ID())
				//
				// Collect invoice lines
				.andCollect(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy()
				.addColumn(I_C_InvoiceLine.COLUMN_C_InvoiceLine_ID)
				.endOrderBy()
				//
				// Execute query
				.create()
				.list(I_C_InvoiceLine.class);
	}

	@Override
	public final List<I_C_Invoice_Line_Alloc> retrieveIlaForIc(final I_C_Invoice_Candidate invoiceCand)
	{
		final IQueryBuilder<I_C_Invoice_Line_Alloc> ilaQueryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Line_Alloc.class, invoiceCand)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCand.getC_Invoice_Candidate_ID());

		ilaQueryBuilder.orderBy()
				.addColumn(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Line_Alloc_ID);

		return ilaQueryBuilder.create().list();
	}

	@Override
	public final List<I_C_Invoice_Line_Alloc> retrieveIlaForIl(final I_C_InvoiceLine il)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Line_Alloc.class, il)
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID, il.getC_InvoiceLine_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				//
				.orderBy()
				.addColumn(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Line_Alloc_ID)
				.endOrderBy()
				//
				.create()
				.list(I_C_Invoice_Line_Alloc.class);
	}

	/**
	 * Adds a record to {@link I_C_Invoice_Candidate_Recompute} to mark the given invoice candidate as invalid. This insertion doesn't interfere with other transactions. It's no problem if two of more
	 * concurrent transactions insert a record for the same invoice candidate.
	 *
	 * @param ic
	 */
	@Override
	public final void invalidateCand(final I_C_Invoice_Candidate ic)
	{
		invalidateCands(ImmutableList.of(ic));
	}

	@Override
	public final void invalidateCandsForProductGroup(final I_M_ProductGroup pg)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(pg);
		final Properties ctx = InterfaceWrapperHelper.getCtx(pg);
		final int referingAggregators = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Agg.class, ctx, trxName)
				.addEqualsFilter(I_C_Invoice_Candidate_Agg.COLUMN_M_ProductGroup_ID, pg.getM_ProductGroup_ID())
				.create()
				.count();

		if (referingAggregators > 0)
		{
			// Note: we invalidate *every* candidate, so there is no need to use the different IInvoiceCandidateHandler
			// implementations.
			invalidateAllCands(ctx, trxName);
		}
	}

	@Override
	public final void invalidateCandsFor(@NonNull final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder)
	{
		final IQuery<I_C_Invoice_Candidate> icQuery = icQueryBuilder.create();
		invalidateCandsFor(icQuery);
	}

	@Override
	public final void invalidateCandsFor(@NonNull final IQuery<I_C_Invoice_Candidate> icQuery)
	{
		final int count = icQuery.insertDirectlyInto(I_C_Invoice_Candidate_Recompute.class)
				.mapColumn(I_C_Invoice_Candidate_Recompute.COLUMNNAME_C_Invoice_Candidate_ID, I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				// NOTE: not setting the AD_PInstance_ID to null, because:
				// 1. that's the default
				// 2. there is an issue with the SQL INSERT that is rendered for NULL parameters, i.e. it cannot detect the database type for NULL
				// .mapColumnToConstant(I_C_Invoice_Candidate_Recompute.COLUMNNAME_AD_PInstance_ID, null)
				.execute()
				.getRowsInserted();

		logger.debug("Invalidated {} invoice candidates for {}", new Object[] { count, icQuery });

		//
		// Schedule an update for invalidated invoice candidates
		if (count > 0)
		{
			final IInvoiceCandUpdateSchedulerRequest request = InvoiceCandUpdateSchedulerRequest.of(icQuery.getCtx(), icQuery.getTrxName());
			Services.get(IInvoiceCandUpdateSchedulerService.class).scheduleForUpdate(request);
		}
	}

	@Override
	public final void invalidateCandsForHeaderAggregationKey(final Properties ctx, final String headerAggregationKey, final String trxName)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = retrieveForHeaderAggregationKeyQuery(ctx, headerAggregationKey, trxName)
				// Not already processed
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false);

		invalidateCandsFor(icQueryBuilder);
		// logger.info("Invalidated {} C_Invoice_Candidates for HeaderAggregationKey={}", new Object[] { count, headerAggregationKey });
	}

	@Override
	public final void invalidateCandsWithSameReference(final I_C_Invoice_Candidate ic)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
		final String trxName = InterfaceWrapperHelper.getTrxName(ic);
		final int adTableId = ic.getAD_Table_ID();
		final int recordId = ic.getRecord_ID();
		if (adTableId <= 0 || recordId <= 0)
		{
			throw new AdempiereException("Invoice candidate has no AD_Table_ID/Record_ID set: " + ic);
		}

		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = retrieveInvoiceCandidatesForRecordQuery(ctx, adTableId, recordId, trxName)
				// Not already processed
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false);
		invalidateCandsFor(icQueryBuilder);
		// logger.info("Invalidated {} C_Invoice_Candidates for AD_Table_ID={} and Record_ID={}", new Object[] { count, adTableId, recordId });
	}

	@Override
	public final void invalidateCandsForBPartner(final I_C_BPartner bpartner)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = retrieveForBillPartnerQuery(bpartner)
				// Not already processed
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false);

		invalidateCandsFor(icQueryBuilder);
		// logger.info("Invalidated {} C_Invoice_Candidates for bPartner={}", new Object[] { count, bpartner });
	}

	@Override
	public final void invalidateCandsForAggregationBuilder(final I_C_Aggregation aggregation)
	{
		if (aggregation == null)
		{
			return;
		}
		final int aggregationId = aggregation.getC_Aggregation_ID();
		if (aggregationId <= 0)
		{
			return;
		}

		//
		// Make sure the aggregation is about C_Invoice_Candidate table
		final int invoiceCandidateTableId = InterfaceWrapperHelper.getTableId(I_C_Invoice_Candidate.class);
		final int adTableId = aggregation.getAD_Table_ID();
		final I_C_Aggregation aggregationOld = InterfaceWrapperHelper.createOld(aggregation, I_C_Aggregation.class);
		final int adTableIdOld = aggregationOld.getAD_Table_ID();
		if (adTableId != invoiceCandidateTableId && adTableIdOld != invoiceCandidateTableId)
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(aggregation);
		final String trxName = InterfaceWrapperHelper.getTrxName(aggregation);

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class, ctx, trxName)
				// Not already processed
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false);

		//
		// Add Header/Line AggregationKeyBuilder_ID filter
		{
			icQueryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_HeaderAggregationKeyBuilder_ID, aggregationId)
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_LineAggregationKeyBuilder_ID, aggregationId);
		}

		//
		// Invalidate
		invalidateCandsFor(icQueryBuilder);
		// logger.info("Invalidated {} C_Invoice_Candidates for aggregation={}", new Object[] { count, aggregation });
	}

	@Override
	public final void invalidateCandsForBPartnerInvoiceRule(final I_C_BPartner bpartner)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = retrieveForBillPartnerQuery(bpartner)
				.addCoalesceEqualsFilter(X_C_Invoice_Candidate.INVOICERULE_KundenintervallNachLieferung,
						I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override,
						I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule)
				// Not already processed
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false);

		invalidateCandsFor(icQueryBuilder);
		// logger.info("Invalidated {} C_Invoice_Candidates for bpartner={}", new Object[] { count, bpartner });
	}

	@Override
	public final void invalidateAllCands(final Properties ctx, final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_C_Invoice_Candidate_Recompute> alreadyInvalidatedICsQuery = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ctx, trxName)
				.create();

		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class, ctx, trxName)
				// Not already processed
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false)
				// Not already invalidated
				.addNotInSubQueryFilter(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID, I_C_Invoice_Candidate_Recompute.COLUMN_C_Invoice_Candidate_ID, alreadyInvalidatedICsQuery);

		invalidateCandsFor(icQueryBuilder);
		// logger.info("Invalidated {} records", count);
	}

	protected final void invalidateCandsForSelection(final int adPInstanceId, final String trxName)
	{
		final Properties ctx = Env.getCtx();
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class, ctx, trxName)
				.setOnlySelection(adPInstanceId)
		// Invalidate no matter if Processed or not
		// .addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false)
		;

		invalidateCandsFor(icQueryBuilder);
		// logger.info("Invalidated {} C_Invoice_Candidates for AD_PInstance_ID={}", new Object[] { count, adPInstanceId });
	}

	private final IQueryBuilder<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForRecordQuery(final Properties ctx, final int adTableId, final int recordId, final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class, ctx, trxName)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Record_ID, recordId)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy()
				.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID)
				.endOrderBy();
	}

	private final IQueryBuilder<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForRecordQuery(final Properties ctx, final String tableName, final int recordId, final String trxName)
	{
		Check.assumeNotEmpty(tableName, "Param 'tableName' is not empty");
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		return retrieveInvoiceCandidatesForRecordQuery(ctx, adTableId, recordId, trxName);
	}

	@Cached(cacheName = I_C_Invoice_Candidate.Table_Name + "#by#AD_Table_ID#Record_ID")
	@Override
	public List<I_C_Invoice_Candidate> fetchInvoiceCandidates(@CacheCtx final Properties ctx, final String tableName, final int recordId, @CacheTrx final String trxName)
	{
		Check.assume(recordId > 0, "Param 'recordId' is > 0");

		return retrieveInvoiceCandidatesForRecordQuery(ctx, tableName, recordId, trxName)
				.create()
				.list(I_C_Invoice_Candidate.class);
	}

	@Override
	public final Iterator<I_C_Invoice_Candidate> fetchInvalidInvoiceCandidates(
			final Properties ctx,
			@NonNull final InvoiceCandRecomputeTag recomputeTag,
			final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ctx, trxName)
				.addEqualsFilter(I_C_Invoice_Candidate_Recompute.COLUMN_AD_PInstance_ID, recomputeTag.getAD_PInstance_ID())
				//
				// Collect invoice candidates
				.andCollect(I_C_Invoice_Candidate_Recompute.COLUMN_C_Invoice_Candidate_ID)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				//
				// Order BY: we need to return the not-manual invoice candidates first, because their NetAmtToInvoice is required when we evaluate the manual candidates
				.orderBy()
				.addColumn(I_C_Invoice_Candidate.COLUMN_IsManual)
				.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID)
				.endOrderBy()
				//
				// Execute query:
				// NOTE (task 03968): performance tweak that is necessary when updating around 70.000 candidates at once:
				// don't use a 'guaranteed' iterator; *we don't need it* and selecting/ordering joining between
				// C_Invoice_Candidate and T_Query_Selection is a performance-killer (at least on our 32bit instance)
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_C_Invoice_Candidate.class);
	}

	@Override
	public InvoiceCandRecomputeTag generateNewRecomputeTag()
	{
		final int adPInstanceId = Services.get(IADPInstanceDAO.class).createAD_PInstance_ID(Env.getCtx());
		return InvoiceCandRecomputeTag.ofAD_PInstance_ID(adPInstanceId);
	}

	@Override
	public final IInvoiceCandRecomputeTagger tagToRecompute()
	{
		return new InvoiceCandRecomputeTagger(this);
	}

	private final IQueryBuilder<I_C_Invoice_Candidate_Recompute> retrieveInvoiceCandidatesRecomputeFor(
			@NonNull final InvoiceCandRecomputeTagger tagRequest)
	{
		final Properties ctx = tagRequest.getCtx();
		final String trxName = tagRequest.getTrxName();

		final IQueryBuilder<I_C_Invoice_Candidate_Recompute> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ctx, trxName);

		//
		// Append not locked where clause
		queryBuilder.filter(LockedByOrNotLockedAtAllFilter.of(tagRequest.getLockedBy()));

		//
		// Only those which were already tagged with given tag
		final InvoiceCandRecomputeTag taggedWith = tagRequest.getTaggedWith();
		if (taggedWith != null)
		{
			final Integer adPInstanceId = InvoiceCandRecomputeTag.getAD_PInstance_ID_OrNull(taggedWith);
			queryBuilder.addEqualsFilter(I_C_Invoice_Candidate_Recompute.COLUMN_AD_PInstance_ID, adPInstanceId);
		}

		//
		// Only a given set of invoice candidates
		if (tagRequest.isOnlyC_Invoice_Candidate_IDs())
		{
			final Set<Integer> invoiceCandidateIds = tagRequest.getOnlyC_Invoice_Candidate_IDs();
			if (invoiceCandidateIds == null || invoiceCandidateIds.isEmpty())
			{
				// i.e. tag none
				queryBuilder.filter(ConstantQueryFilter.<I_C_Invoice_Candidate_Recompute> of(false));
			}
			else
			{
				queryBuilder.addInArrayOrAllFilter(I_C_Invoice_Candidate_Recompute.COLUMN_C_Invoice_Candidate_ID, invoiceCandidateIds);
			}
		}

		//
		// Limit maximum number of invalid invoice candidates to tag for updating
		if (tagRequest.getLimit() > 0)
		{
			queryBuilder.setLimit(tagRequest.getLimit());
		}

		return queryBuilder;
	}

	protected final int tagToRecompute(@NonNull final InvoiceCandRecomputeTagger tagRequest)
	{
		final InvoiceCandRecomputeTag recomputeTag = tagRequest.getRecomputeTag();

		final IQueryBuilder<I_C_Invoice_Candidate_Recompute> queryBuilder = retrieveInvoiceCandidatesRecomputeFor(tagRequest);
		final IQuery<I_C_Invoice_Candidate_Recompute> query = queryBuilder.create();
		final int count = query
				.updateDirectly()
				.addSetColumnValue(I_C_Invoice_Candidate_Recompute.COLUMNNAME_AD_PInstance_ID, recomputeTag.getAD_PInstance_ID())
				.execute();
		logger.debug("Marked {} {} records with recompute tag={}", count, I_C_Invoice_Candidate_Recompute.Table_Name, recomputeTag);
		logger.debug("Query: {}", query);
		logger.debug("Tagger: {}", tagRequest);

		return count;
	}

	/**
	 * @param tagRequest
	 * @return how many {@link I_C_Invoice_Candidate_Recompute} records will be tagged by given {@link InvoiceCandRecomputeTagger}.
	 */
	protected final int countToBeTagged(final InvoiceCandRecomputeTagger tagRequest)
	{
		return retrieveInvoiceCandidatesRecomputeFor(tagRequest)
				.create()
				.count();
	}

	/**
	 * Deletes those records from table I_C_Invoice_Candidate_Recompute.Table_Name that were formerly tagged with the given recompute tag.
	 *
	 * @param recomputeTag
	 * @param onlyInvoiceCandidateIds
	 * @param trxName
	 */
	protected final void deleteRecomputeMarkers(
			@NonNull final InvoiceCandRecomputeTagger tagger,
			final Collection<Integer> onlyInvoiceCandidateIds)
	{
		final Properties ctx = tagger.getCtx();
		final InvoiceCandRecomputeTag recomputeTag = tagger.getRecomputeTag();
		final String trxName = tagger.getTrxName();

		final IQueryBuilder<I_C_Invoice_Candidate_Recompute> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ctx, trxName)
				.addEqualsFilter(I_C_Invoice_Candidate_Recompute.COLUMN_AD_PInstance_ID, recomputeTag.getAD_PInstance_ID());

		//
		// Delete only the specified invoice candidate IDs
		if (!Check.isEmpty(onlyInvoiceCandidateIds))
		{
			queryBuilder.addInArrayOrAllFilter(I_C_Invoice_Candidate_Recompute.COLUMN_C_Invoice_Candidate_ID, onlyInvoiceCandidateIds);
		}

		final IQuery<I_C_Invoice_Candidate_Recompute> query = queryBuilder.create();
		final int count = query.deleteDirectly();
		logger.debug("Deleted {} {} entries for tag={}, onlyInvoiceCandidateIds={}", count, I_C_Invoice_Candidate_Recompute.Table_Name, recomputeTag, onlyInvoiceCandidateIds);
		logger.debug("Query: {}", query);
	}

	protected final int untag(@NonNull final InvoiceCandRecomputeTagger tagger)
	{
		final Properties ctx = tagger.getCtx();
		final InvoiceCandRecomputeTag recomputeTag = tagger.getRecomputeTag();
		final String trxName = tagger.getTrxName();

		final IQuery<I_C_Invoice_Candidate_Recompute> query = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ctx, trxName)
				.addEqualsFilter(I_C_Invoice_Candidate_Recompute.COLUMN_AD_PInstance_ID, recomputeTag.getAD_PInstance_ID())
				.create();
		final int count = query
				.updateDirectly()
				.addSetColumnValue(I_C_Invoice_Candidate_Recompute.COLUMNNAME_AD_PInstance_ID, null)
				.execute();

		logger.debug("Un-tag {} {} records with were tagged with recompute tag={}", count, I_C_Invoice_Candidate_Recompute.Table_Name, recomputeTag);
		logger.debug("Query: {}", query);
		logger.debug("Tagger: {}", tagger);

		return count;
	}

	@Override
	public final boolean hasInvalidInvoiceCandidatesForTag(final InvoiceCandRecomputeTag tag)
	{
		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final Integer adPInstanceId = InvoiceCandRecomputeTag.getAD_PInstance_ID_OrNull(tag);

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ctx, trxName)
				.addEqualsFilter(I_C_Invoice_Candidate_Recompute.COLUMN_AD_PInstance_ID, adPInstanceId)
				.create()
				.match();
	}

	@Override
	public final List<I_C_Invoice_Candidate> retrieveForBillPartner(final I_C_BPartner bpartner)
	{
		return retrieveForBillPartnerQuery(bpartner)
				.create()
				.list(I_C_Invoice_Candidate.class);
	}

	private final IQueryBuilder<I_C_Invoice_Candidate> retrieveForBillPartnerQuery(final I_C_BPartner bpartner)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class, bpartner)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Bill_BPartner_ID, bpartner.getC_BPartner_ID())
				.orderBy()
				.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID)
				.endOrderBy();
	}

	@Override
	public List<I_C_Invoice_Candidate> retrieveForInvoiceSchedule(final I_C_InvoiceSchedule invoiceSchedule)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_C_BPartner> bpartnersQuery = queryBL.createQueryBuilder(I_C_BPartner.class, invoiceSchedule)
				.addEqualsFilter(I_C_BPartner.COLUMN_C_InvoiceSchedule_ID, invoiceSchedule.getC_InvoiceSchedule_ID())
				.create();

		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class, invoiceSchedule)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addInSubQueryFilter(I_C_Invoice_Candidate.COLUMN_Bill_BPartner_ID, I_C_BPartner.COLUMN_C_BPartner_ID, bpartnersQuery)
				.addCoalesceEqualsFilter(X_C_Invoice_Candidate.INVOICERULE_EFFECTIVE_KundenintervallNachLieferung,
						I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override,
						I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule)
				//
				.orderBy()
				.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID)
				.endOrderBy()
				//
				.create()
				.list(I_C_Invoice_Candidate.class);
	}

	@Override
	public final void updateDateInvoiced(final Timestamp dateInvoiced, final int selectionId)
	{
		updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_DateInvoiced,    // invoiceCandidateColumnName
				dateInvoiced,    // value
				false,    // updateOnlyIfNull
				selectionId    // selectionId
		);
	}

	@Override
	public final void updateDateAcct(final Timestamp dateAcct, final int selectionId)
	{
		updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_DateAcct,    // invoiceCandidateColumnName
				dateAcct,    // value
				false,    // updateOnlyIfNull
				selectionId    // selectionId
		);
	}

	@Override
	public final void updatePOReference(final String poReference, final int selectionId)
	{
		updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_POReference,    // invoiceCandidateColumnName
				poReference,    // value
				false,    // updateOnlyIfNull
				selectionId    // selectionId
		);
	}

	@Override
	public void updateMissingPaymentTermIds(final int selectionId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final int selectionToUpdateId = retrieveIcsToUpdateSelectionId(selectionId);
		if (selectionToUpdateId <= 0)
		{
			return;
		}
		final Integer paymentTermId = retrievePaymentTermId(selectionId);
		if (paymentTermId <= 0)
		{
			return;
		}

		final int updateCount = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setOnlySelection(selectionToUpdateId)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_C_Invoice_Candidate.COLUMNNAME_C_PaymentTerm_Override_ID, paymentTermId)
				.execute();

		Loggables.get().withLogger(logger, Level.INFO)
				.addLog("updateMissingPaymentTermIds - {} C_Invoice_Candidates were updated; selectionId={}, paymentTermId={}",
						updateCount, selectionId, paymentTermId);

		// Invalidate the candidates which we updated
		invalidateCandsForSelection(selectionToUpdateId, ITrx.TRXNAME_ThreadInherited);

	}

	private int retrieveIcsToUpdateSelectionId(final int selectionId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final int selectionToUpdateId = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setOnlySelection(selectionId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_C_PaymentTerm_ID, null)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_C_PaymentTerm_Override_ID, null)
				.create()
				.createSelection();

		if (selectionToUpdateId <= 0)
		{
			Loggables.get().withLogger(logger, Level.INFO)
					.addLog("updateMissingPaymentTermIds - No C_Invoice_Candidate needs to be updated; selectionId={}",
							selectionId);
		}
		return selectionToUpdateId;
	}

	private int retrievePaymentTermId(final int selectionId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_C_Invoice_Candidate> paymentTermSetFilter = queryBL
				.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
				.setJoinOr()
				.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMN_C_PaymentTerm_ID, null)
				.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMN_C_PaymentTerm_Override_ID, null);

		final I_C_Invoice_Candidate firstInvoiceCandidateWithPaymentTermId = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setOnlySelection(selectionId)
				.filter(paymentTermSetFilter)
				.orderBy()
				.addColumnAscending(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID).endOrderBy()
				.create()
				.first();
		if (firstInvoiceCandidateWithPaymentTermId == null)
		{
			Loggables.get().withLogger(logger, Level.INFO)
					.addLog("updateMissingPaymentTermIds - No C_Invoice_Candidate selected by selectionId={} has a C_PaymentTerm_ID; nothing to update", selectionId);
			return -1;
		}

		final Integer paymentTermId = InterfaceWrapperHelper.getValueOverrideOrValue(
				firstInvoiceCandidateWithPaymentTermId,
				I_C_Invoice_Candidate.COLUMNNAME_C_PaymentTerm_ID);
		return paymentTermId;
	}

	@Override
	public final <T> void updateColumnForSelection(
			final String columnName,
			final T value,
			final boolean updateOnlyIfNull,
			final int selectionId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		//
		// Create the selection which we will need to update
		final IQueryBuilder<I_C_Invoice_Candidate> selectionQueryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setOnlySelection(selectionId)
				.addNotEqualsFilter(columnName, value) // skip those which have our value set
		;
		if (updateOnlyIfNull)
		{
			selectionQueryBuilder.addEqualsFilter(columnName, null);
		}
		final int selectionToUpdateId = selectionQueryBuilder.create().createSelection();
		if (selectionToUpdateId <= 0)
		{
			Loggables.get().withLogger(logger, Level.INFO)
					.addLog("updateColumnForSelection - No C_Invoice_Candidate needs to be updated; selectionId={}, columnName={}; updateOnlyIfNull={}, newValue={}",
							selectionId, columnName, updateOnlyIfNull, value);
			return;
		}

		// Update our new selection
		final IQuery<I_C_Invoice_Candidate> updateQuery = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setOnlySelection(selectionToUpdateId)
				.create();

		final ICompositeQueryUpdater<I_C_Invoice_Candidate> updater = queryBL
				.createCompositeQueryUpdater(I_C_Invoice_Candidate.class)
				.addSetColumnValue(columnName, value);

		final int updateCount = updateQuery.updateDirectly(updater);

		Loggables.get().withLogger(logger, Level.INFO)
				.addLog("updateColumnForSelection - {} C_Invoice_Candidates were updated; selectionId={}, columnName={}; updateOnlyIfNull={}, newValue={}",
						updateCount, selectionId, columnName, updateOnlyIfNull, value);

		// Invalidate the candidates which we updated
		invalidateCandsForSelection(selectionToUpdateId, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	public BigDecimal retrieveInvoicableAmount(
			final Properties ctx,
			final IInvoiceCandidateQuery query,
			final int targetCurrencyId,
			final int adClientId,
			final int adOrgId,
			final String amountColumnName,
			final String trxName)
	{
		final StringBuilder whereClause = new StringBuilder("1=1");
		final List<Object> params = new ArrayList<>();

		// Bill BPartner
		if (query.getBill_BPartner_ID() > 0)
		{
			whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID).append("=?");
			params.add(query.getBill_BPartner_ID());
		}

		// DateToInvoice
		if (query.getDateToInvoice() != null)
		{
			whereClause.append(" AND ")
					.append(" COALESCE(" + I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Override + "," + I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice + ")").append("<=?");
			params.add(TimeUtil.getDay(query.getDateToInvoice()));
		}

		// Filter HeaderAggregationKey
		if (query.getHeaderAggregationKey() != null)
		{
			whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_HeaderAggregationKey).append("=?");
			params.add(query.getHeaderAggregationKey());
		}

		// Exclude C_Invoice_Candidate
		if (query.getExcludeC_Invoice_Candidate_ID() > 0)
		{
			whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID).append("<>?");
			params.add(query.getExcludeC_Invoice_Candidate_ID());
		}
		if (query.getMaxManualC_Invoice_Candidate_ID() > 0)
		{
			// either the candidate is *not* manual, or its ID is less or equal than MaxManualC_Invoice_Candidate_ID
			whereClause.append(" AND (")
					.append(I_C_Invoice_Candidate.COLUMNNAME_IsManual + "=? OR ")
					.append(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID).append("<=?");
			whereClause.append(")");
			params.add(false);
			params.add(query.getMaxManualC_Invoice_Candidate_ID());
		}

		// Processed
		if (query.getProcessed() != null)
		{
			whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_Processed).append("=?");
			params.add(query.getProcessed().booleanValue());
		}

		// Exclude those with errors
		whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_IsError).append("=?");
		params.add(false);

		// Filter by AD_Client_ID
		whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_AD_Client_ID).append("=?");
		params.add(adClientId);

		final String sql = "SELECT "
				+ I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID
				+ ", " + I_C_Invoice_Candidate.COLUMNNAME_C_ConversionType_ID
				+ ", SUM(" + amountColumnName + ") as NetAmt"
				+ " FROM " + I_C_Invoice_Candidate.Table_Name
				+ " WHERE " + whereClause
				+ " GROUP BY "
				+ I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID + ","
				+ I_C_Invoice_Candidate.COLUMNNAME_C_ConversionType_ID;

		final Map<Integer, Map<Integer, BigDecimal>> currencyId2conversion2Amt = new HashMap<>();

		final PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
		ResultSet rs = null;
		try
		{
			DB.setParameters(pstmt, params);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final BigDecimal netAmt = rs.getBigDecimal("NetAmt");
				if (rs.wasNull())
				{
					continue;
				}
				final int currencyId = rs.getInt(I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID);
				final int conversionTypeId = rs.getInt(I_C_Invoice_Candidate.COLUMNNAME_C_ConversionType_ID);
				Map<Integer, BigDecimal> conversion2Amt = currencyId2conversion2Amt.get(currencyId);
				if (conversion2Amt == null)
				{
					conversion2Amt = new HashMap<>();
					currencyId2conversion2Amt.put(currencyId, conversion2Amt);
				}
				conversion2Amt.put(conversionTypeId, netAmt);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		// Conversion date to be used on currency conversion
		final Timestamp dateConv = SystemTime.asTimestamp();

		BigDecimal result = BigDecimal.ZERO;
		for (final Integer currencyId : currencyId2conversion2Amt.keySet())
		{
			final Map<Integer, BigDecimal> conversion2Amt = currencyId2conversion2Amt.get(currencyId);

			for (final Integer conversionTypeId : conversion2Amt.keySet())
			{
				final BigDecimal amt = conversion2Amt.get(conversionTypeId);
				final BigDecimal amtConverted = Services.get(ICurrencyBL.class).convert(ctx,
						amt,
						currencyId,    // CurFrom_ID,
						targetCurrencyId,    // CurTo_ID,
						dateConv,    // ConvDate,
						conversionTypeId,
						adClientId, adOrgId);
				result = result.add(amtConverted);
			}
		}

		return result;
	}

	@Override
	public final List<I_M_InOutLine> retrieveInOutLines(final Properties ctx, final int C_OrderLine_ID, final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_InOutLine.class, ctx, trxName)
				.addEqualsFilter(I_M_InOutLine.COLUMN_C_OrderLine_ID, C_OrderLine_ID)
				.create()
				.list(I_M_InOutLine.class);
	}

	protected void saveErrorToDB(final I_C_Invoice_Candidate ic)
	{
		final String sql = "UPDATE " + I_C_Invoice_Candidate.Table_Name + " SET "
				+ " " + I_C_Invoice_Candidate.COLUMNNAME_SchedulerResult + "=?"
				+ "," + I_C_Invoice_Candidate.COLUMNNAME_IsError + "=?"
				+ "," + I_C_Invoice_Candidate.COLUMNNAME_ErrorMsg + "=?"
				+ "," + I_C_Invoice_Candidate.COLUMNNAME_AD_Note_ID + "=?"
				+ " WHERE " + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + "=?";
		final Object[] sqlParams = new Object[] {
				ic.getSchedulerResult(), ic.isError(), ic.getErrorMsg(), ic.getAD_Note_ID(), ic.getC_Invoice_Candidate_ID()
		};

		final boolean ignoreError = false;
		final String trxName = InterfaceWrapperHelper.getTrxName(ic);
		DB.executeUpdate(sql, sqlParams, ignoreError, trxName);
	}

	@Override
	public final void invalidateCands(@Nullable final List<I_C_Invoice_Candidate> ics)
	{
		// Extract C_Invoice_Candidate_IDs
		if (ics == null || ics.isEmpty())
		{
			return; // nothing to do for us
		}

		final ImmutableList<Integer> icIds = ics.stream()
				.filter(Predicates.notNull())
				.filter(ic -> ic.getC_Invoice_Candidate_ID() > 0)
				.map(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		if (icIds.isEmpty())
		{
			return;
		}

		// note: invalidate, no matter if Processed or not
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addInArrayOrAllFilter(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID, icIds);

		invalidateCandsFor(icQueryBuilder);
		// logger.info("Invalidated {} invoice candidates for C_Invoice_Candidate_IDs={}", new Object[] { count, icIds });
	}

	@Override
	public final boolean isToRecompute(final I_C_Invoice_Candidate ic)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ic)
				.addEqualsFilter(I_C_Invoice_Candidate_Recompute.COLUMN_C_Invoice_Candidate_ID, ic.getC_Invoice_Candidate_ID())
				.setLimit(1)
				.create()
				.match();
	}

	@Override
	public List<I_C_Invoice_Detail> retrieveInvoiceDetails(final I_C_Invoice_Candidate ic)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Detail.class, ic)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Detail.COLUMN_C_Invoice_Candidate_ID, ic.getC_Invoice_Candidate_ID())
				.orderBy()
				.addColumn(I_C_Invoice_Detail.COLUMNNAME_SeqNo)
				.addColumn(I_C_Invoice_Detail.COLUMNNAME_IsPrinted, Direction.Descending, Nulls.Last)
				.addColumn(I_C_Invoice_Detail.COLUMNNAME_IsDetailOverridesLine, Direction.Descending, Nulls.Last)
				.addColumn(I_C_Invoice_Detail.COLUMNNAME_IsPrintBefore, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.list();
	}

	@Override
	public IQueryBuilder<I_C_Invoice_Candidate> applyDefaultFilter(
			@NonNull final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder)
	{
		final Properties ctx = queryBuilder.getCtx();

		// shall never happen
		if (ctx.isEmpty())
		{
			return queryBuilder;
		}

		// Only filter invoice candidates of the organizations this role has access to
		final IUserRolePermissions userRolePermissions = Env.getUserRolePermissions(ctx);

		return queryBuilder.addInArrayOrAllFilter(I_C_Invoice_Candidate.COLUMN_AD_Org_ID, userRolePermissions.getAD_Org_IDs_AsSet());

	}

	@Override
	public String getSQLDefaultFilter(final Properties ctx)
	{
		// Only filter invoice candidates of the organizations this role has access to
		final IUserRolePermissions userRolePermissions = Env.getUserRolePermissions(ctx);

		final StringBuilder defaultFilter = new StringBuilder("");

		final String orgIDsAsString = userRolePermissions.getAD_Org_IDs_AsString();

		if (!Check.isEmpty(orgIDsAsString))
		{

			defaultFilter.append(I_C_Invoice_Candidate.COLUMNNAME_AD_Org_ID)
					.append(" IN (")
					.append(orgIDsAsString)
					.append(")");

		}

		return defaultFilter.toString();
	}

	@Override
	public IQueryBuilder<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForInventoryLineQuery(final I_M_InventoryLine inventoryLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(inventoryLine);
		final int adTableId = InterfaceWrapperHelper.getTableId(I_M_InventoryLine.class);
		final int recordId = inventoryLine.getM_InventoryLine_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(inventoryLine);

		return retrieveInvoiceCandidatesForRecordQuery(ctx, adTableId, recordId, trxName);
	}

	@Override
	public Set<String> retrieveOrderDocumentNosForIncompleteGroupsFromSelection(final int adPInstanceId)
	{
		final String sql = "SELECT * FROM C_Invoice_Candidate_SelectionIncompleteGroups WHERE AD_PInstance_ID=?";
		final List<Object> sqlParams = Arrays.asList(adPInstanceId);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final ImmutableSet.Builder<String> orderDocumentNos = ImmutableSet.builder();
			while (rs.next())
			{
				final String orderDocumentNo = rs.getString("OrderDocumentNo");
				orderDocumentNos.add(orderDocumentNo);
			}
			return orderDocumentNos.build();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
