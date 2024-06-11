package de.metas.invoicecandidate.api.impl;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.ICurrencyBL;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutDAO;
import de.metas.invoice.InvoiceId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.InvoiceLineAllocId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandRecomputeTagger;
import de.metas.invoicecandidate.api.IInvoiceCandUpdateSchedulerService;
import de.metas.invoicecandidate.api.InvoiceCandRecomputeTag;
import de.metas.invoicecandidate.api.InvoiceCandidateIdsSelection;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.invoicecandidate.api.InvoiceCandidateQuery;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.invoicecandidate.model.I_C_Invoice_Detail;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.invoicecandidate.model.I_M_ProductGroup;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.security.IUserRolePermissions;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import de.metas.util.lang.ExternalId;
import de.metas.util.time.InstantInterval;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import static org.adempiere.model.InterfaceWrapperHelper.delete;

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

public class InvoiceCandDAO implements IInvoiceCandDAO
{
	private final transient Logger logger = InvoiceCandidate_Constants.getLogger(InvoiceCandDAO.class);

	private final transient IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private static final ModelDynAttributeAccessor<I_C_Invoice_Candidate, Boolean> DYNATTR_IC_Avoid_Recreate //
			= new ModelDynAttributeAccessor<>(IInvoiceCandDAO.class.getName() + "Avoid_Recreate", Boolean.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceCandUpdateSchedulerService invoiceCandScheduler = Services.get(IInvoiceCandUpdateSchedulerService.class);

	@Override
	public I_C_Invoice_Candidate getById(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		return InterfaceWrapperHelper.load(invoiceCandidateId, I_C_Invoice_Candidate.class);
	}

	@Override
	public I_C_Invoice_Candidate getByIdOutOfTrx(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		return InterfaceWrapperHelper.loadOutOfTrx(invoiceCandidateId, I_C_Invoice_Candidate.class);
	}

	@Override
	public List<I_C_Invoice_Candidate> getByIds(@NonNull final Collection<InvoiceCandidateId> invoiceCandidateIds)
	{
		return InterfaceWrapperHelper.loadByRepoIdAwares(ImmutableSet.copyOf(invoiceCandidateIds), I_C_Invoice_Candidate.class);
	}

	@Override
	public final Iterator<I_C_Invoice_Candidate> retrieveIcForSelection(
			@NonNull final PInstanceId selectionId,
			@NonNull final IContextAware contextAware)
	{
		// Note that we can't filter by IsError in the where clause, because it wouldn't work with pagination.
		// Background is that the number of candidates with "IsError=Y" might increase during the run.

		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class, contextAware)
				.setOnlySelection(selectionId)
				.clearOrderBys() // be sure to specify no ordering, because we want the iterator to be ordered by C_Invoice_Candidate_ID - and not by any other column that might change on the fly.
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_C_Invoice_Candidate.class);
	}

	@Override
	public final Iterator<I_C_Invoice_Candidate> retrieveNonProcessed(final IContextAware contextAware)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = queryBL
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


	public Iterator<I_C_Invoice_Candidate> retrieveIcForSelectionStableOrdering(@NonNull final PInstanceId pInstanceId)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setOnlySelection(pInstanceId)
				.clearOrderBys()
				//
				// order by they header aggregation key to make sure candidates with the same key end up in the same invoice
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_HeaderAggregationKey)
				//
				// We need to aggregate by DateInvoiced too
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_DateInvoiced)
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_DateAcct)
				//
				// task 08241: return ICs with a set Bill_User_ID first, because, we can aggregate ICs with different Bill_User_IDs into one invoice, however, if there are any ICs with a Bill_User_ID
				// set, and others with no Bill_User_ID, then we want the Bill_User_ID to end up in the C_Invoice (header) record.
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_Bill_User_ID)
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.create()
				//
				// we require a guaranteed iterator to make sure that the ordering is not changed from this point onwards
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_C_Invoice_Candidate.class);
	}

	@Override
	public List<I_C_Invoice_Candidate> retrieveReferencing(@NonNull final TableRecordReference reference)
	{
		return fetchInvoiceCandidates(reference.getTableName(), reference.getRecord_ID());
	}

	@Override
	@NonNull
	public ImmutableSet<InvoiceCandidateId> retrieveReferencingIds(@NonNull final TableRecordReference reference)
	{
		return retrieveInvoiceCandidatesForRecordQuery(reference)
				.create()
				.listIds(InvoiceCandidateId::ofRepoId);
	}

	@Override
	public int deleteAllReferencingInvoiceCandidates(@NonNull final Object model)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		final int recordId = InterfaceWrapperHelper.getId(model);

		// i could do all this with "stream", but i find "old-school" easier to debug
		int deleteCount = 0;
		final List<I_C_Invoice_Candidate> icRecordsToDelete = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, recordId)
				.create()
				.list();
		for (final I_C_Invoice_Candidate icRecordToDelete : icRecordsToDelete)
		{
			setProcessedToFalseIfIcNotNeeded(icRecordToDelete);
			delete(icRecordToDelete);
			deleteCount++;
		}

		return deleteCount;
	}

	/**
	 * Note: no need to save the record; just unset its processed flag to allow deletion if that makes sense.
	 */
	private void setProcessedToFalseIfIcNotNeeded(@NonNull final I_C_Invoice_Candidate icToDelete)
	{
		final boolean manuallyFlaggedAsProcessed = icToDelete.isProcessed() && !icToDelete.isProcessed_Calc();
		if (!manuallyFlaggedAsProcessed)
		{
			return; // nothing to do
		}

		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final boolean hasInvoiceLines = !invoiceCandDAO.retrieveIlForIc(icToDelete).isEmpty();
		if (hasInvoiceLines)
		{
			return; // nothing to do
		}

		// icToDelete was manually set to "processed" to be out of the way; in this case, we can unprocess and delete it.
		icToDelete.setProcessed(false);
	}

	@Override
	public BigDecimal retrieveInvoicableAmount(@NonNull final I_C_BPartner billBPartner, @Nullable final LocalDate date)
	{
		final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

		final String trxName = InterfaceWrapperHelper.getTrxName(billBPartner);
		final Properties ctx = InterfaceWrapperHelper.getCtx(billBPartner);
		final OrgId orgId = OrgId.ofRepoId(billBPartner.getAD_Org_ID());

		final InvoiceCandidateQuery query = InvoiceCandidateQuery.builder()
				.billBPartnerId(BPartnerId.ofRepoId(billBPartner.getC_BPartner_ID()))
				.orgId(orgId)
				.dateToInvoice(date)
				.error(false)
				.build();
		final CurrencyId targetCurrencyId = currencyBL.getBaseCurrency(ctx).getId();
		final int adClientId = billBPartner.getAD_Client_ID();

		return retrieveInvoicableAmount(ctx, query, targetCurrencyId, adClientId, I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice, trxName);
	}

	@Override
	public <T extends org.compiere.model.I_C_Invoice> Map<Integer, T> retrieveInvoices(
			final String tableName,
			final int recordId,
			final Class<T> clazz,
			final boolean onlyUnpaid)
	{
		final Map<Integer, T> openInvoices = new HashMap<>();

		final List<I_C_Invoice_Candidate> icsForCurrentTerm = fetchInvoiceCandidates(tableName, recordId);
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
		return queryBL
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
	public List<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_C_OrderLine_ID, orderLineId)
				.addOnlyActiveRecordsFilter()
				//
				.create()
				.list(I_C_Invoice_Candidate.class);
	}

	@Override
	public List<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForOrderId(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.list();
	}

	@Nullable
	@Override
	public I_C_InvoiceCandidate_InOutLine retrieveInvoiceCandidateInOutLine(@NonNull final I_C_Invoice_Candidate ic, @NonNull final I_M_InOutLine iol)
	{
		// there is a Unique Index on the 2 columns C_IC_IOL_Unique_Active, so i'm sure there's at most 1 line
		return queryBL
				.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class, ic)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMN_C_Invoice_Candidate_ID, ic.getC_Invoice_Candidate_ID())
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMN_M_InOutLine_ID, iol.getM_InOutLine_ID())
				.addOnlyActiveRecordsFilter()
				//
				.create()
				.firstOnly(I_C_InvoiceCandidate_InOutLine.class);
	}

	@Override
	public List<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsExclRE(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoIdOrNull(invoiceCandidate.getC_Invoice_Candidate_ID());
		if (invoiceCandidateId == null)
		{
			return ImmutableList.of(); // no associations for new/not saved ICs
		}

		return retrieveICIOLAssociationsExclRE(invoiceCandidateId);
	}

	@Override
	public List<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsExclRE(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		// load all I_C_InvoiceCandidate_InOutLine and filter locally.
		// i think it's safe to assume that there are not 1000s of records to load and this way the code is simpler
		return queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMN_C_Invoice_Candidate_ID, invoiceCandidateId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_InvoiceCandidate_InOutLine.COLUMN_M_InOutLine_ID)
				.create()
				.stream(I_C_InvoiceCandidate_InOutLine.class)
				.filter(this::isInOutCompletedOrClosed)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public int countICIOLAssociations(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		// count all I_C_InvoiceCandidate_InOutLine regardless of I_M_InOut status
		return queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateId)
				.addOnlyActiveRecordsFilter()
				.create()
				.count();
	}

	private boolean isInOutCompletedOrClosed(@NonNull final I_C_InvoiceCandidate_InOutLine iciol)
	{
		final I_M_InOut inOut = iciol.getM_InOutLine().getM_InOut();

		return inOut.isActive() && DocStatus.ofCode(inOut.getDocStatus()).isCompletedOrClosed();
	}

	@Override
	public List<I_C_InvoiceCandidate_InOutLine> retrieveICIOLAssociationsFor(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		return queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMN_C_Invoice_Candidate_ID, invoiceCandidateId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_InvoiceCandidate_InOutLine.COLUMN_M_InOutLine_ID)
				.create()
				.list(I_C_InvoiceCandidate_InOutLine.class);
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
		return queryBL
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
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_M_InOutLine.class))
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, inoutLine.getM_InOutLine_ID());

			queryBuilder.filter(filter);
		}

		//
		// ICs which are created for inout line's C_OrderLine_ID
		if (inoutLine.getC_OrderLine_ID() > 0)
		{
			final ICompositeQueryFilter<I_C_Invoice_Candidate> filter = queryBL.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_OrderLine.class))
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, inoutLine.getC_OrderLine_ID());

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
	public void saveAll(final Collection<I_C_Invoice_Candidate> invoiceCandidates)
	{
		invoiceCandidates.forEach(this::save);
	}

	@Override
	public int deleteInvoiceDetails(final I_C_Invoice_Candidate ic)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Detail.class, ic)
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
		final IQueryBuilder<I_C_Invoice_Line_Alloc> ilaQueryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Line_Alloc.class, invoiceLine)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID, invoiceLine.getC_InvoiceLine_ID());

		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = ilaQueryBuilder
				.andCollect(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID)
				.addOnlyActiveRecordsFilter();

		icQueryBuilder.orderBy()
				.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID);

		return icQueryBuilder
				.create()
				.list();//
	}

	@NonNull
	public List<I_C_Invoice_Candidate> retrieveInvoiceCandidates(@NonNull final InvoiceId invoiceId)
	{
		return queryBL.createQueryBuilder(I_C_InvoiceLine.class)
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				//collect related invoice line alloc
				.andCollectChildren(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID)
				.addOnlyActiveRecordsFilter()
				//collect related invoice candidates
				.andCollect(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID)
				.addOnlyActiveRecordsFilter()
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

	private IQueryBuilder<I_C_Invoice_Candidate> retrieveForHeaderAggregationKeyQuery(
			@CacheCtx final Properties ctx,
			final String headerAggregationKey,
			@CacheTrx final String trxName)
	{
		return queryBL
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
		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCand.getC_Invoice_Candidate_ID());
		return retrieveIlForIc(invoiceCandidateId);
	}

	@Override
	public final List<I_C_InvoiceLine> retrieveIlForIc(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice_Line_Alloc.class)
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID, invoiceCandidateId)
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
	public final List<I_C_Invoice_Line_Alloc> retrieveIlaForIc(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final IQueryBuilder<I_C_Invoice_Line_Alloc> ilaQueryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Line_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateId);

		ilaQueryBuilder.orderBy()
				.addColumn(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Line_Alloc_ID);

		return ilaQueryBuilder.create().list();
	}

	@Override
	public final List<I_C_Invoice_Line_Alloc> retrieveIlaForIl(final I_C_InvoiceLine il)
	{
		return queryBL
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
	 * @return number of invalidated candidates
	 */
	@Override
	public final int invalidateCand(final I_C_Invoice_Candidate ic)
	{
		return invalidateCands(ImmutableList.of(ic));
	}

	@Override
	public final void invalidateCandsForProductGroup(final I_M_ProductGroup pg)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(pg);
		final Properties ctx = InterfaceWrapperHelper.getCtx(pg);
		final int referingAggregators = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Agg.class, ctx, trxName)
				.addEqualsFilter(I_C_Invoice_Candidate_Agg.COLUMN_M_ProductGroup_ID, pg.getM_ProductGroup_ID())
				.create()
				.count();

		if (referingAggregators > 0)
		{
			// Note: we invalidate *every* candidate, so there is no need to use the different IInvoiceCandidateHandler implementations.
			invalidateAllCands(ctx, trxName);
		}
	}

	@Override
	public void invalidateCandsThatReference(@NonNull final TableRecordReference recordReference)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = retrieveInvoiceCandidatesForRecordQuery(recordReference)
				// Not already processed
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false);
		invalidateCandsFor(icQueryBuilder);
	}

	@Override
	public final int invalidateCandsFor(@NonNull final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder)
	{
		final IQuery<I_C_Invoice_Candidate> icQuery = icQueryBuilder.create();
		return invalidateCandsFor(icQuery);
	}

	@Override
	public final void invalidateCandsFor(@NonNull final ImmutableSet<InvoiceCandidateId> invoiceCandidateIds)
	{
		if (invoiceCandidateIds.isEmpty())
		{
			return; // nothing to do for us
		}

		// note: invalidate, no matter if Processed or not
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addInArrayFilter(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID, invoiceCandidateIds);

		invalidateCandsFor(icQueryBuilder);
	}

	@Override
	public final int invalidateCandsFor(@NonNull final IQuery<I_C_Invoice_Candidate> icQuery)
	{
		// insert all C_Invoice_Candidate_Recompute records with a ChunkUUID so we know later what was added now.
		final String chunkUUID = UUID.randomUUID().toString();

		final int count = icQuery
				.insertDirectlyInto(I_C_Invoice_Candidate_Recompute.class)
				.mapColumn(I_C_Invoice_Candidate_Recompute.COLUMNNAME_C_Invoice_Candidate_ID, I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.mapColumnToConstant(I_C_Invoice_Candidate_Recompute.COLUMNNAME_ChunkUUID, chunkUUID)
				// NOTE: not setting the AD_PInstance_ID to null, because:
				// 1. null is the default
				// 2. there is an issue with the SQL INSERT that is rendered for NULL parameters, i.e. it cannot detect the database type for NULL
				// .mapColumnToConstant(I_C_Invoice_Candidate_Recompute.COLUMNNAME_AD_PInstance_ID, null)
				.execute()
				.getRowsInserted();

		logger.info("Invalidated {} invoice candidates with chunkUUID={}, trxName={} and query={}", count, chunkUUID, icQuery.getTrxName(), icQuery);

		//
		// Schedule an update for invalidated invoice candidates
		if (count > 0)
		{
				final InvoiceCandUpdateSchedulerRequest request = InvoiceCandUpdateSchedulerRequest.of(
						icQuery.getCtx(),
						icQuery.getTrxName());
				invoiceCandScheduler.scheduleForUpdate(request);
		}
		
		return count;
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
	public final void invalidateCandsWithSameTableReference(final I_C_Invoice_Candidate ic)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = retrieveInvoiceCandidatesForRecordQuery(TableRecordReference.ofReferenced(ic))
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
	public final void invalidateCandsForBPartnerInvoiceRule(final BPartnerId bpartnerId)
	{
		final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

		final I_C_BPartner bpartner = partnerDAO.getById(bpartnerId);
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = retrieveForBillPartnerQuery(bpartner)
				.addCoalesceEqualsFilter(X_C_Invoice_Candidate.INVOICERULE_CustomerScheduleAfterDelivery,
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
	}

	protected final void invalidateCandsForSelection(@NonNull final PInstanceId pinstanceId)
	{
		final Properties ctx = Env.getCtx();
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.setOnlySelection(pinstanceId)
				// Invalidate no matter if Processed or not
				// .addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false)
				;

		invalidateCandsFor(icQueryBuilder);
		// logger.info("Invalidated {} C_Invoice_Candidates for AD_PInstance_ID={}", new Object[] { count, adPInstanceId });
	}

	private IQueryBuilder<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForRecordQuery(
			@NonNull final TableRecordReference tableRecordReference)
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, tableRecordReference.getAdTableId())
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, tableRecordReference.getRecord_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();
	}

	@Cached(cacheName = I_C_Invoice_Candidate.Table_Name + "#by#AD_Table_ID#Record_ID")
	public List<I_C_Invoice_Candidate> fetchInvoiceCandidates(
			@NonNull final String tableName,
			final int recordId)
	{
		Check.assume(recordId > 0, "Param 'recordId' needs to be > 0");

		return retrieveInvoiceCandidatesForRecordQuery(TableRecordReference.of(tableName, recordId))
				.create()
				.list(I_C_Invoice_Candidate.class);
	}

	@Override
	public final Iterator<I_C_Invoice_Candidate> fetchInvalidInvoiceCandidates(
			final Properties ctx,
			@NonNull final InvoiceCandRecomputeTag recomputeTag,
			final String trxName)
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ctx, trxName)
				.addEqualsFilter(I_C_Invoice_Candidate_Recompute.COLUMN_AD_PInstance_ID, recomputeTag.getPinstanceId())
				//
				// Collect invoice candidates
				.andCollect(I_C_Invoice_Candidate_Recompute.COLUMN_C_Invoice_Candidate_ID)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				//
				// Order BY: we need to return the not-manual invoice candidates first, because their NetAmtToInvoice is required when we evaluate the manual candidates
				.orderBy()
				.addColumn(I_C_Invoice_Candidate.COLUMNNAME_IsFreightCost, Direction.Ascending, Nulls.First)
				.addColumn(I_C_Invoice_Candidate.COLUMN_IsManual)
				.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID)
				.endOrderBy()
				//
				// Execute query:
				// NOTE (task 03968): performance tweak that is necessary when updating around 70.000 candidates at once:
				// don't use a 'guaranteed' iterator; *we don't need it* and selecting/ordering joining between
				// C_Invoice_Candidate and T_Query_Selection is a performance-killer
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_C_Invoice_Candidate.class);
	}

	@Override
	public InvoiceCandRecomputeTag generateNewRecomputeTag()
	{
		final PInstanceId pinstanceId = Services.get(IADPInstanceDAO.class).createSelectionId();
		return InvoiceCandRecomputeTag.ofPInstanceId(pinstanceId);
	}

	@Override
	public final IInvoiceCandRecomputeTagger tagToRecompute()
	{
		return new InvoiceCandRecomputeTagger(this);
	}

	@NonNull
	@Override
	public ImmutableList<I_C_InvoiceCandidate_InOutLine> retrieveICIOLForInvoiceCandidate(@NonNull final I_C_Invoice_Candidate ic)
	{
		return queryBL
				.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class, ic)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID, ic.getC_Invoice_Candidate_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	private IQueryBuilder<I_C_Invoice_Candidate_Recompute> retrieveInvoiceCandidatesRecomputeFor(
			@NonNull final InvoiceCandRecomputeTagger tagRequest)
	{
		final Properties ctx = tagRequest.getCtx();
		final String trxName = tagRequest.getTrxName();

		final IQueryBuilder<I_C_Invoice_Candidate_Recompute> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ctx, trxName);

		//
		// Append not locked where clause
		queryBuilder.filter(LockedByOrNotLockedAtAllFilter.of(tagRequest.getLockedBy()));

		//
		// Only those which were already tagged with given tag
		final InvoiceCandRecomputeTag taggedWith = tagRequest.getTaggedWith();
		if (taggedWith != null)
		{
			final PInstanceId pinstanceId = InvoiceCandRecomputeTag.getPinstanceIdOrNull(taggedWith);
			queryBuilder.addEqualsFilter(I_C_Invoice_Candidate_Recompute.COLUMN_AD_PInstance_ID, pinstanceId);
		}

		//
		// Only a given set of invoice candidates
		final InvoiceCandidateIdsSelection onlyInvoiceCandidateIds = tagRequest.getOnlyInvoiceCandidateIds();
		if (onlyInvoiceCandidateIds != null)
		{
			onlyInvoiceCandidateIds.apply(new InvoiceCandidateIdsSelection.CaseMapper()
			{
				@Override
				public void empty()
				{
					queryBuilder.filter(ConstantQueryFilter.of(false));
				}

				@Override
				public void fixedSet(final @NonNull ImmutableSet<InvoiceCandidateId> ids)
				{
					queryBuilder.addInArrayOrAllFilter(I_C_Invoice_Candidate_Recompute.COLUMN_C_Invoice_Candidate_ID, ids);
				}

				@Override
				public void selectionId(final @NonNull PInstanceId selectionId)
				{
					queryBuilder.addInSubQueryFilter(
							I_C_Invoice_Candidate_Recompute.COLUMNNAME_C_Invoice_Candidate_ID,
							I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID,
							queryBL.createQueryBuilder(I_C_Invoice_Candidate.class, ctx, trxName)
									.setOnlySelection(selectionId)
									.create());
				}
			});
		}

		// make sure the order of invoice candidate recomputing is somewhat predictable
		queryBuilder.orderBy(I_C_Invoice_Candidate_Recompute.COLUMNNAME_C_Invoice_Candidate_ID);

		//
		// Limit maximum number of invalid invoice candidates to tag for updating
		if (tagRequest.getLimit() > 0)
		{
			queryBuilder.setLimit(QueryLimit.ofInt(tagRequest.getLimit()));
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
				.addSetColumnValue(I_C_Invoice_Candidate_Recompute.COLUMNNAME_AD_PInstance_ID, recomputeTag.getPinstanceId())
				.execute();

		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("Marked {} {} records with recompute tag={}", count, I_C_Invoice_Candidate_Recompute.Table_Name, recomputeTag);

		logger.debug("Query: {}", query);
		logger.debug("Tagger: {}", tagRequest);

		return count;
	}

	/**
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
	 */
	protected final void deleteRecomputeMarkersAndInvalidateCache(
			@NonNull final InvoiceCandRecomputeTagger tagger,
			@Nullable final Collection<Integer> onlyInvoiceCandidateIds)
	{
		final Properties ctx = tagger.getCtx();
		final InvoiceCandRecomputeTag recomputeTag = tagger.getRecomputeTag();
		final String trxName = tagger.getTrxName();

		final IQueryBuilder<I_C_Invoice_Candidate_Recompute> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ctx, trxName)
				.addEqualsFilter(I_C_Invoice_Candidate_Recompute.COLUMN_AD_PInstance_ID, recomputeTag.getPinstanceId());

		//
		// Delete only the specified invoice candidate IDs
		if (!Check.isEmpty(onlyInvoiceCandidateIds))
		{
			queryBuilder.addInArrayOrAllFilter(I_C_Invoice_Candidate_Recompute.COLUMN_C_Invoice_Candidate_ID, onlyInvoiceCandidateIds);
		}

		final IQuery<I_C_Invoice_Candidate_Recompute> query = queryBuilder.create();
		final int count = query.deleteDirectly();

		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("Deleted {} {} entries for tag={}, onlyInvoiceCandidateIds={}", count, I_C_Invoice_Candidate_Recompute.Table_Name, recomputeTag, onlyInvoiceCandidateIds);
		logger.debug("Query: {}", query);

		// invalidate the invoice candidate cache after commit
		Services.get(ITrxManager.class)
				.getTrxListenerManagerOrAutoCommit(trxName)
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> invalidateInvoiceCandidateCache(onlyInvoiceCandidateIds));
	}

	private void invalidateInvoiceCandidateCache(@Nullable final Collection<Integer> onlyInvoiceCandidateIds)
	{
		final CacheInvalidateMultiRequest multiRequest;
		if (Check.isEmpty(onlyInvoiceCandidateIds))
		{
			multiRequest = CacheInvalidateMultiRequest.allRecordsForTable(I_C_Invoice_Candidate.Table_Name);
		}
		else
		{
			multiRequest = CacheInvalidateMultiRequest.fromTableNameAndRecordIds(I_C_Invoice_Candidate.Table_Name, onlyInvoiceCandidateIds);
		}

		final ModelCacheInvalidationService modelCacheInvalidationService = ModelCacheInvalidationService.get();
		modelCacheInvalidationService.invalidate(multiRequest, ModelCacheInvalidationTiming.AFTER_CHANGE);
	}

	protected final int untag(@NonNull final InvoiceCandRecomputeTagger tagger)
	{
		final Properties ctx = tagger.getCtx();
		final InvoiceCandRecomputeTag recomputeTag = tagger.getRecomputeTag();
		final String trxName = tagger.getTrxName();

		final IQuery<I_C_Invoice_Candidate_Recompute> query = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ctx, trxName)
				.addEqualsFilter(I_C_Invoice_Candidate_Recompute.COLUMN_AD_PInstance_ID, recomputeTag.getPinstanceId())
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
	public final boolean hasInvalidInvoiceCandidates(@NonNull final Collection<InvoiceCandidateId> invoiceCandidateIds)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class)
				.addInArrayFilter(I_C_Invoice_Candidate_Recompute.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateIds)
				.create()
				.anyMatch();
	}

	@Override
	public final boolean hasInvalidInvoiceCandidatesForSelection(@NonNull final InvoiceCandidateIdsSelection invoiceCandidateIdsSelection)
	{
		if(invoiceCandidateIdsSelection.isEmpty())
		{
			return false;
		}

		// Without the newOutOfTrx-context, we had InvoiceCandBL.waitForInvoiceCandidatesUpdated consistently hit the 1hr-timeout on one instance,
		// although multiple UpdateInvalidInvoiceCandidatesWorkpackageProcessors executed successfully during that hour.
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class, PlainContextAware.newOutOfTrx());

		// apply the invoiceCandidateIdsSelection to our queryBuilder
		invoiceCandidateIdsSelection.apply(new InvoiceCandidateIdsSelection.CaseMapper()
		{
			@Override
			public void empty()
			{
				queryBuilder.filter(ConstantQueryFilter.of(false));
			}

			@Override
			public void fixedSet(@NonNull final ImmutableSet<InvoiceCandidateId> ids)
			{
				queryBuilder.addInArrayFilter(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID, ids);
			}

			@Override
			public void selectionId(@NonNull final PInstanceId selectionId)
			{
				queryBuilder.setOnlySelection(selectionId);
			}
		});

		return queryBuilder
				.andCollectChildren(I_C_Invoice_Candidate_Recompute.COLUMN_C_Invoice_Candidate_ID)
				.create()
				.anyMatch();
	}

	private IQueryBuilder<I_C_Invoice_Candidate> retrieveForBillPartnerQuery(final I_C_BPartner bpartner)
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class, bpartner)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID, bpartner.getC_BPartner_ID())
				.orderBy()
				.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID)
				.endOrderBy();
	}

	@Override
	public Iterator<I_C_Invoice_Candidate> retrieveForInvoiceSchedule(final I_C_InvoiceSchedule invoiceSchedule)
	{
		final IQuery<I_C_BPartner> bpartnersQuery = queryBL.createQueryBuilder(I_C_BPartner.class, invoiceSchedule)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMN_C_InvoiceSchedule_ID, invoiceSchedule.getC_InvoiceSchedule_ID())
				.create();

		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class, invoiceSchedule)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false)
				.addInSubQueryFilter(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID, I_C_BPartner.COLUMNNAME_C_BPartner_ID, bpartnersQuery)
				.addCoalesceEqualsFilter(X_C_Invoice_Candidate.INVOICERULE_EFFECTIVE_CustomerScheduleAfterDelivery,
						I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override,
						I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule)
				//
				.orderBy()
				.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID)
				.endOrderBy()
				//
				.create()
				.setOption(IQuery.OPTION_IteratorBufferSize, 2000)
				.iterate(I_C_Invoice_Candidate.class);
	}

	@Override
	public final void updateDateInvoiced(
			@Nullable final LocalDate dateInvoiced,
			@NonNull final PInstanceId selectionId)
	{
		updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_DateInvoiced,    // invoiceCandidateColumnName
				dateInvoiced, // value
				false, // updateOnlyIfNull
				selectionId // selectionId
		);
	}

	@Override
	public final void updateDateAcct(
			@Nullable final LocalDate dateAcct,
			@NonNull final PInstanceId selectionId)
	{
		updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_DateAcct,    // invoiceCandidateColumnName
				dateAcct, // value
				false, // updateOnlyIfNull
				selectionId // selectionId
		);
	}

	@Override
	public final void updateNullDateAcctFromDateInvoiced(final PInstanceId selectionId)
	{
		updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_DateAcct,
				ModelColumnNameValue.forColumnName(I_C_Invoice_Candidate.COLUMNNAME_DateInvoiced), // value
				true, // updateOnlyIfNull
				selectionId // selectionId
		);
	}

	@Override
	public final void updatePOReference(final String poReference, final PInstanceId selectionId)
	{
		updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_POReference,    // invoiceCandidateColumnName
				poReference, // value
				false, // updateOnlyIfNull
				selectionId // selectionId
		);
	}

	@Override
	public final void updateApprovalForInvoicingToTrue(@NonNull final PInstanceId selectionId)
	{
		updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing,    // invoiceCandidateColumnName
				true, // value
				false, // updateOnlyIfNull
				selectionId // selectionId
		);
	}

	private PInstanceId retrieveIcsToUpdateSelectionId(final PInstanceId selectionId)
	{
		final PInstanceId selectionToUpdateId = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setOnlySelection(selectionId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_PaymentTerm_ID, null)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_PaymentTerm_Override_ID, null)
				.create()
				.createSelection();

		if (selectionToUpdateId == null)
		{
			Loggables.withLogger(logger, Level.INFO)
					.addLog("updateMissingPaymentTermIds - No C_Invoice_Candidate needs to be updated; selectionId={}",
							selectionId);
		}
		return selectionToUpdateId;
	}

	@Nullable
	private PaymentTermId retrievePaymentTermId(final PInstanceId selectionId)
	{
		final ICompositeQueryFilter<I_C_Invoice_Candidate> paymentTermSetFilter = queryBL
				.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
				.setJoinOr()
				.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_PaymentTerm_ID, null)
				.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_PaymentTerm_Override_ID, null);

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
			Loggables.withLogger(logger, Level.INFO)
					.addLog("updateMissingPaymentTermIds - No C_Invoice_Candidate selected by selectionId={} has a C_PaymentTerm_ID; nothing to update", selectionId);
			return null;
		}

		return CoalesceUtil.coalesceSuppliers(
				() -> PaymentTermId.ofRepoIdOrNull(firstInvoiceCandidateWithPaymentTermId.getC_PaymentTerm_Override_ID()),
				() -> PaymentTermId.ofRepoIdOrNull(firstInvoiceCandidateWithPaymentTermId.getC_PaymentTerm_ID()));
	}

	/**
	 * Mass-update a given invoice candidate column.
	 * <p>
	 * If there were any changes, those invoice candidates will be invalidated.
	 *
	 * @param columnName       {@link I_C_Invoice_Candidate}'s column to update
	 * @param value            value to set (you can also use {@link ModelColumnNameValue})
	 * @param updateOnlyIfNull if true then it will update only if column value is null (not set)
	 * @param selectionId      invoice candidates selection (AD_PInstance_ID)
	 */
	private <T> void updateColumnForSelection(
			@NonNull final String columnName,
			@Nullable final T value,
			final boolean updateOnlyIfNull,
			@NonNull final PInstanceId selectionId)
	{
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
		final PInstanceId selectionToUpdateId = selectionQueryBuilder.create().createSelection();
		if (selectionToUpdateId == null)
		{
			Loggables.withLogger(logger, Level.INFO)
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

		Loggables.withLogger(logger, Level.INFO)
				.addLog("updateColumnForSelection - {} C_Invoice_Candidates were updated; selectionId={}, columnName={}; updateOnlyIfNull={}, newValue={}",
						updateCount, selectionId, columnName, updateOnlyIfNull, value);

		// Invalidate the candidates which we updated
		invalidateCandsForSelection(selectionToUpdateId);
	}

	@Override
	public BigDecimal retrieveInvoicableAmount(
			final Properties ctx,
			@NonNull final InvoiceCandidateQuery query,
			@NonNull final CurrencyId targetCurrencyId,
			final int adClientId,
			final String amountColumnName,
			final String trxName)
	{
		final StringBuilder whereClause = new StringBuilder("1=1");
		final List<Object> params = new ArrayList<>();

		final OrgId orgId = query.getOrgIdNotNull();

		// Bill BPartner
		if (query.getBillBPartnerId() != null)
		{
			whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID).append("=?");
			params.add(query.getBillBPartnerId().getRepoId());
		}

		whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_AD_Org_ID).append("=?");
		params.add(orgId.getRepoId());

		// DateToInvoice
		if (query.getDateToInvoice() != null)
		{
			whereClause.append(" AND ")
					.append(" COALESCE(" + I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Override + "," + I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice + ")").append("<=?");
			params.add(TimeUtil.asTimestamp(
					query.getDateToInvoice(),
					orgDAO.getTimeZone(orgId) /* note that if dateToInvoice is not null, then orgId is also not null */));
		}

		// Filter HeaderAggregationKey
		if (query.getHeaderAggregationKey() != null)
		{
			whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_HeaderAggregationKey).append("=?");
			params.add(query.getHeaderAggregationKey());
		}

		// Exclude C_Invoice_Candidate
		if (query.getExcludeC_Invoice_Candidate_ID() != null)
		{
			whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID).append("<>?");
			params.add(query.getExcludeC_Invoice_Candidate_ID().getRepoId());
		}
		if (query.getMaxManualC_Invoice_Candidate_ID() != null)
		{
			// either the candidate is *not* manual, or its ID is less or equal than MaxManualC_Invoice_Candidate_ID
			whereClause.append(" AND (")
					.append(I_C_Invoice_Candidate.COLUMNNAME_IsManual + "=? OR ")
					.append(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID).append("<=?");
			whereClause.append(")");
			params.add(false);
			params.add(query.getMaxManualC_Invoice_Candidate_ID().getRepoId());
		}

		// Processed
		if (query.getProcessed() != null)
		{
			whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_Processed).append("=?");
			params.add(query.getProcessed());
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

		final HashMap<CurrencyId, HashMap<CurrencyConversionTypeId, BigDecimal>> currencyId2conversion2Amt = new HashMap<>();

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
				final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(rs.getInt(I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID));
				final CurrencyConversionTypeId conversionTypeId = CurrencyConversionTypeId.ofRepoIdOrNull(rs.getInt(I_C_Invoice_Candidate.COLUMNNAME_C_ConversionType_ID));

				HashMap<CurrencyConversionTypeId, BigDecimal> conversion2Amt = currencyId2conversion2Amt.get(currencyId);
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
			throw new DBException(e, sql, params);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		BigDecimal result = BigDecimal.ZERO;
		for (final CurrencyId currencyId : currencyId2conversion2Amt.keySet())
		{
			final Map<CurrencyConversionTypeId, BigDecimal> conversion2Amt = currencyId2conversion2Amt.get(currencyId);

			for (final CurrencyConversionTypeId conversionTypeId : conversion2Amt.keySet())
			{
				final BigDecimal amt = conversion2Amt.get(conversionTypeId);
				final BigDecimal amtConverted = Services.get(ICurrencyBL.class).convert(
						amt,
						currencyId,    // CurFrom_ID,
						targetCurrencyId, // CurTo_ID,
						SystemTime.asInstant(), // ConvDate,
						conversionTypeId,
						ClientId.ofRepoId(adClientId),
						orgId);
				result = result.add(amtConverted);
			}
		}

		return result;
	}

	@Override
	public final List<I_M_InOutLine> retrieveInOutLines(final Properties ctx, final int C_OrderLine_ID, final String trxName)
	{
		return queryBL
				.createQueryBuilder(I_M_InOutLine.class, ctx, trxName)
				.addEqualsFilter(I_M_InOutLine.COLUMN_C_OrderLine_ID, C_OrderLine_ID)
				.create()
				.list(I_M_InOutLine.class);
	}

	protected void saveErrorToDB(@NonNull final I_C_Invoice_Candidate ic)
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

		final String trxName = InterfaceWrapperHelper.getTrxName(ic);
		DB.executeUpdateAndThrowExceptionOnFail(sql, sqlParams, trxName);
	}

	@Override
	public final int invalidateCands(@Nullable final List<I_C_Invoice_Candidate> ics)
	{
		// Extract C_Invoice_Candidate_IDs
		if (ics == null || ics.isEmpty())
		{
			return 0; // nothing to do for us
		}

		final ImmutableSet<InvoiceCandidateId> icIds = ics.stream()
				.filter(Objects::nonNull)
				.map(ic -> InvoiceCandidateId.ofRepoIdOrNull(ic.getC_Invoice_Candidate_ID()))
				.filter(Objects::nonNull)
				.distinct()
				.collect(ImmutableSet.toImmutableSet());
		if (icIds.isEmpty())
		{
			return 0;
		}

		// note: invalidate, no matter if Processed or not
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addInArrayFilter(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID, icIds);

		return invalidateCandsFor(icQueryBuilder);
	}

	@Override
	public final boolean isToRecompute(final I_C_Invoice_Candidate ic)
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ic)
				.addEqualsFilter(I_C_Invoice_Candidate_Recompute.COLUMN_C_Invoice_Candidate_ID, ic.getC_Invoice_Candidate_ID())
				.setLimit(QueryLimit.ONE)
				.create()
				.anyMatch();
	}

	@Override
	public List<I_C_Invoice_Detail> retrieveInvoiceDetails(final I_C_Invoice_Candidate ic)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Detail.class, ic)
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

		return queryBuilder.addInArrayOrAllFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Org_ID, userRolePermissions.getAD_Org_IDs_AsSet());

	}

	@Override
	public String getSQLDefaultFilter(final Properties ctx)
	{
		// Only filter invoice candidates of the organizations this role has access to
		final IUserRolePermissions userRolePermissions = Env.getUserRolePermissions(ctx);

		final StringBuilder defaultFilter = new StringBuilder();

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

	// @Override
	// public IQueryBuilder<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForInventoryLineQuery(final I_M_InventoryLine inventoryLine)
	// {
	// final Properties ctx = InterfaceWrapperHelper.getCtx(inventoryLine);
	// final int adTableId = InterfaceWrapperHelper.getTableId(I_M_InventoryLine.class);
	// final int recordId = inventoryLine.getM_InventoryLine_ID();
	// final String trxName = InterfaceWrapperHelper.getTrxName(inventoryLine);
	//
	// return retrieveInvoiceCandidatesForRecordQuery(ctx, adTableId, recordId, trxName);
	// }

	@Override
	public Set<String> retrieveOrderDocumentNosForIncompleteGroupsFromSelection(final PInstanceId adPInstanceId)
	{
		final String sql = "SELECT * FROM C_Invoice_Candidate_SelectionIncompleteGroups WHERE AD_PInstance_ID=?";
		final List<Object> sqlParams = ImmutableList.of(adPInstanceId);
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

	@Override
	public InvoiceableInvoiceCandIdResult getFirstInvoiceableInvoiceCandId(@NonNull final OrderId orderId)
	{
		final List<I_C_Invoice_Candidate> nonFreightCostICs = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsFreightCost, false)
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_DeliveryDate)
				.create()
				.list();

		if (nonFreightCostICs.isEmpty())
		{
			return new InvoiceableInvoiceCandIdResult(null/* firstInvoiceableInvoiceCandId */, false/* orderHasInvoiceCandidatesToWaitFor */);
		}

		for (final I_C_Invoice_Candidate nonFreightCostIC : nonFreightCostICs)
		{
			if (nonFreightCostIC.getQtyToInvoice().signum() > 0 || nonFreightCostIC.getQtyToInvoice_Override().signum() > 0)
			{
				return new InvoiceableInvoiceCandIdResult(
						InvoiceCandidateId.ofRepoId(nonFreightCostIC.getC_Invoice_Candidate_ID())/* firstInvoiceableInvoiceCandId */,
						true/* orderHasInvoiceCandidatesToWaitFor */);
			}
		}

		return new InvoiceableInvoiceCandIdResult(
				null/* firstInvoiceableInvoiceCandId */,
				true/* orderHasInvoiceCandidatesToWaitFor */);
	}

	@Override
	public void invalidateUninvoicedFreightCostCandidate(@NonNull final OrderId orderId)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> freightCostCandQueryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_IsFreightCost, true)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false);

		invalidateCandsFor(freightCostCandQueryBuilder);
	}

	@Override
	public @NonNull List<I_C_Invoice_Candidate> retrieveApprovedForInvoiceReferencing(final TableRecordReferenceSet singleTableReferences)
	{
		if (singleTableReferences.isEmpty())
		{
			return ImmutableList.of();
		}

		final Set<Integer> recordIds = singleTableReferences.streamByTableName(singleTableReferences.getSingleTableName())
				.map(TableRecordReference::getRecord_ID)
				.collect(ImmutableSet.toImmutableSet());

		return  queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, singleTableReferences.getSingleTableId())
				.addInArrayFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, recordIds)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing, true)
				.create()
				.list(I_C_Invoice_Candidate.class);
	}

	@Override
	public List<I_C_Invoice_Candidate> getByQuery(@NonNull final InvoiceCandidateMultiQuery multiQuery)
	{
		return convertToIQuery(multiQuery)
				.list();
	}

	@Override
	public int createSelectionByQuery(@NonNull final InvoiceCandidateMultiQuery multiQuery, @NonNull final PInstanceId pInstanceId)
	{
		return convertToIQuery(multiQuery)
				.createSelection(pInstanceId);
	}

	@NonNull
	public ImmutableList<I_C_Invoice> getInvoicesForCandidateId(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Line_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateId)
				//collect related invoice lines
				.andCollect(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID)
				.addOnlyActiveRecordsFilter()
				//collect related invoices
				.andCollect(I_C_InvoiceLine.COLUMN_C_Invoice_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.listImmutable(I_C_Invoice.class);
	}

	@Override
	public Optional<InvoiceCandidateId> getInvoiceCandidateIdByInvoiceLineAllocId(final InvoiceLineAllocId invoiceLineAllocId)
	{
		return Optional.ofNullable(getInvoiceLineAlloc(invoiceLineAllocId))
				.map(I_C_Invoice_Line_Alloc::getC_Invoice_Candidate_ID)
				.map(InvoiceCandidateId::ofRepoId);
	}

	private IQuery<I_C_Invoice_Candidate> convertToIQuery(@NonNull final InvoiceCandidateMultiQuery multiQuery)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions, false) /* exploding ORs to unions works only with simple cases, but e.g. currently not if we want to use IQuery.createSelection() down the line */
				.setJoinOr();
		final List<InvoiceCandidateQuery> queries = multiQuery.getQueries();
		for (final InvoiceCandidateQuery query : queries)
		{
			queryBuilder.filter(toFilter(query));
		}
		queryBuilder.orderBy(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID);
		return queryBuilder.create();
	}

	private ICompositeQueryFilter<I_C_Invoice_Candidate> toFilter(@NonNull final InvoiceCandidateQuery query)
	{
		final ICompositeQueryFilter<I_C_Invoice_Candidate> filter = queryBL
				.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter();

		final OrgId orgId = query.getOrgId();
		if (orgId != null)
		{
			filter.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Org_ID, orgId);
		}

		final SOTrx soTrx = query.getSoTrx();
		if (soTrx != null)
		{
			filter.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsSOTrx, soTrx.isSales());
		}

		final InvoiceCandidateId invoiceCandidateId = query.getInvoiceCandidateId();
		if (invoiceCandidateId != null)
		{
			filter.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID, invoiceCandidateId);
		}

		final BPartnerId billBPartnerId = query.getBillBPartnerId();
		if (billBPartnerId != null)
		{
			filter.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID, billBPartnerId);
		}

		final BPartnerId salesRepBPartnerId = query.getSalesRepBPartnerId();
		if (salesRepBPartnerId != null)
		{
			filter.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_BPartner_SalesRep_ID, salesRepBPartnerId);
		}

		final InstantInterval dateOrderedInterval = query.getDateOrderedInterval();
		if (dateOrderedInterval != null)
		{
			final Timestamp from = TimeUtil.asTimestamp(dateOrderedInterval.getFrom());
			final Timestamp to = TimeUtil.asTimestamp(dateOrderedInterval.getTo());

			filter.addBetweenFilter(I_C_Invoice_Candidate.COLUMNNAME_DateOrdered, from, to);
		}

		final LocalDate dateToInvoice = query.getDateToInvoice();
		if (dateToInvoice != null)
		{
			filter.addCoalesceEqualsFilter(
					TimeUtil.asTimestamp(dateToInvoice, orgDAO.getTimeZone(orgId)) /* note that if dateToInvoice is not null, then orgId is also not null */,
					I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Override, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);
		}

		final String headerAggregationKey = query.getHeaderAggregationKey();
		if (!Check.isEmpty(headerAggregationKey, true))
		{
			filter.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_HeaderAggregationKey, headerAggregationKey);
		}

		final InvoiceCandidateId excludeC_Invoice_Candidate_ID = query.getExcludeC_Invoice_Candidate_ID();
		if (excludeC_Invoice_Candidate_ID != null)
		{
			filter.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID, excludeC_Invoice_Candidate_ID);
		}

		final InvoiceCandidateId maxManualC_Invoice_Candidate_ID = query.getMaxManualC_Invoice_Candidate_ID();
		if (maxManualC_Invoice_Candidate_ID != null)
		{
			final ICompositeQueryFilter<I_C_Invoice_Candidate> manualIcMaxFilter = queryBL
					.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
					.setJoinOr()
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_IsManual, false)
					.addCompareFilter(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID, Operator.LESS_OR_EQUAL, maxManualC_Invoice_Candidate_ID.getRepoId());
			filter.addFilter(manualIcMaxFilter);
		}

		final Boolean processed = query.getProcessed();
		if (processed != null)
		{
			filter.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, processed);
		}

		final Boolean error = query.getError();
		if (error != null)
		{
			filter.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_IsError, error);
		}

		final ExternalHeaderIdWithExternalLineIds externalIds = query.getExternalIds();
		if (externalIds != null)
		{
			final String headerIdAsString = externalIds.getExternalHeaderId().getValue();

			final ImmutableList<String> lineIdsAsString = externalIds
					.getExternalLineIds()
					.stream()
					.map(ExternalId::getValue)
					.collect(ImmutableList.toImmutableList());

			final ICompositeQueryFilter<I_C_Invoice_Candidate> invoiceCandidatesFilter = queryBL
					.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_ExternalHeaderId, headerIdAsString)
					.addInArrayOrAllFilter(I_C_Invoice_Candidate.COLUMN_ExternalLineId, lineIdsAsString);
			filter.addFilter(invoiceCandidatesFilter);
		}

		return filter;
	}

	@Nullable
	private I_C_Invoice_Line_Alloc getInvoiceLineAlloc(@NonNull final InvoiceLineAllocId invoiceLineAllocId)
	{
		return InterfaceWrapperHelper.load(invoiceLineAllocId, I_C_Invoice_Line_Alloc.class);
	}
}
