package de.metas.inoutcandidate.api.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.document.engine.IDocument;
import de.metas.externalsystem.ExternalSystemIdWithExternalIds;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.ReceiptScheduleQuery;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.impl.ASIQueryFilterModifier;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

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

/**
 * Repository Tables: M_ReceiptSchedule (query owner; also written by {@link de.metas.inoutcandidate.ReceiptScheduleRepository}),
 * M_ReceiptSchedule_Alloc (query owner), M_InOutLine (sub-query filter/navigation only),
 * M_InOut (read, {@link #retrieveCompletedReceipts}), C_OrderLine (sub-query filter only)
 * Repository Cluster: ReceiptScheduleDAO, ReceiptScheduleRepository
 */
public class ReceiptScheduleDAO implements IReceiptScheduleDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private static final AdMessageKey MSG_CANNOT_DELETE_ORDER_LINE_RECEIPT_SCHEDULE = AdMessageKey.of("CannotDeleteOrderLine_ReceiptSchedule");

	@Override
	public Iterator<I_M_ReceiptSchedule> retrieve(final IQuery<I_M_ReceiptSchedule> query)
	{
		// Note that we can't filter by IsError in the where clause, because it wouldn't work with pagination.
		// Background is that the number of candidates with "IsError=Y" might increase during the run.
		final IQueryOrderBy orderBy = Services.get(IQueryBL.class).createQueryOrderByBuilder(I_M_ReceiptSchedule.class)
				.addColumn(I_M_ReceiptSchedule.COLUMNNAME_HeaderAggregationKey)
				.createQueryOrderBy();

		// NOTE: we are not touching the old Query but clone it first and customize the new copy.
		final IQuery<I_M_ReceiptSchedule> queryNew = query.copy();

		return queryNew
				.setOrderBy(orderBy)
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true) // need guaranteed iterator, because the number of unprocessed items is reduced by that process using this query
				.iterate(I_M_ReceiptSchedule.class);
	}

	@Override
	@Nullable
	public I_M_ReceiptSchedule retrieveForRecord(@Nullable final Object model)
	{
		if (model == null)
		{
			return null;
		}
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final int recordId = InterfaceWrapperHelper.getId(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);
		return retrieveForRecord(ctx, tableName, recordId, trxName);
	}

	private I_M_ReceiptSchedule retrieveForRecord(final Properties ctx, final String tableName, final int recordId, final String trxName)
	{
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ReceiptSchedule.class, ctx, trxName)
				.filter(new EqualsQueryFilter<I_M_ReceiptSchedule>(I_M_ReceiptSchedule.COLUMNNAME_AD_Table_ID, adTableId))
				.filter(new EqualsQueryFilter<I_M_ReceiptSchedule>(I_M_ReceiptSchedule.COLUMNNAME_Record_ID, recordId))
				.filterByClientId()
				.create()
				.firstOnly(I_M_ReceiptSchedule.class);

	}

	@Override
	public List<I_M_ReceiptSchedule_Alloc> retrieveRsaForRs(final I_M_ReceiptSchedule rs)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(rs);
		return retrieveRsaForRs(rs, trxName);
	}

	@Override
	public List<I_M_ReceiptSchedule_Alloc> retrieveRsaForRs(final I_M_ReceiptSchedule rs, final String trxName)
	{
		final IQueryBuilder<I_M_ReceiptSchedule_Alloc> queryBuilder = createRsaForRsQueryBuilder(rs, I_M_ReceiptSchedule_Alloc.class, trxName);
		return queryBuilder.create().list(I_M_ReceiptSchedule_Alloc.class);
	}

	@Override
	public <T extends I_M_ReceiptSchedule_Alloc> IQueryBuilder<T> createRsaForRsQueryBuilder(@NonNull final I_M_ReceiptSchedule rs, final Class<T> receiptScheduleAllocClass, final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(rs);

		final IQueryBuilder<T> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(receiptScheduleAllocClass, ctx, trxName)
				.addEqualsFilter(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_ReceiptSchedule_ID, rs.getM_ReceiptSchedule_ID());

		queryBuilder.orderBy()
				.addColumn(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_ReceiptSchedule_Alloc_ID);

		return queryBuilder;
	}

	@Override
	public <T extends I_M_ReceiptSchedule_Alloc> IQueryBuilder<T> createRsaForRsQueryBuilder(final I_M_ReceiptSchedule rs, final Class<T> receiptScheduleAllocClass)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(rs);
		return createRsaForRsQueryBuilder(rs, receiptScheduleAllocClass, trxName);
	}

	@Override
	public I_M_ReceiptSchedule_Alloc retrieveRsaForRs(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final org.compiere.model.I_M_InOutLine receiptLine)
	{
		final IQueryBuilder<I_M_ReceiptSchedule_Alloc> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ReceiptSchedule_Alloc.class, receiptLine)
				.filter(new EqualsQueryFilter<I_M_ReceiptSchedule_Alloc>(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_ReceiptSchedule_ID, receiptSchedule.getM_ReceiptSchedule_ID()))
				.filter(new EqualsQueryFilter<I_M_ReceiptSchedule_Alloc>(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_InOutLine_ID, receiptLine.getM_InOutLine_ID()));

		queryBuilder.orderBy()
				.addColumn(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_ReceiptSchedule_Alloc_ID);

		final I_M_ReceiptSchedule_Alloc existingRsa = queryBuilder.create()
				.firstOnly(I_M_ReceiptSchedule_Alloc.class);

		return existingRsa;
	}

	@Override
	public List<I_M_ReceiptSchedule_Alloc> retrieveRsaForInOut(final org.compiere.model.I_M_InOut receipt)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQuery<I_M_InOutLine> matchingReceiptLineQuery = queryBL.createQueryBuilder(I_M_InOutLine.class, receipt)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, receipt.getM_InOut_ID())
				.create();

		return queryBL
				.createQueryBuilder(I_M_ReceiptSchedule_Alloc.class, receipt)
				.addInSubQueryFilter(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_InOutLine_ID, I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, matchingReceiptLineQuery)
				//
				.orderBy()
				.addColumn(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_ReceiptSchedule_Alloc_ID)
				.endOrderBy()
				//
				.create()
				.list(I_M_ReceiptSchedule_Alloc.class);
	}

	@Override
	public List<I_M_ReceiptSchedule_Alloc> retrieveRsaForInOutLine(final org.compiere.model.I_M_InOutLine line)
	{
		return retrieveRsaForInOutLineQuery(line)
				.create()
				.list(I_M_ReceiptSchedule_Alloc.class);
	}

	private IQueryBuilder<I_M_ReceiptSchedule_Alloc> retrieveRsaForInOutLineQuery(final org.compiere.model.I_M_InOutLine line)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ReceiptSchedule_Alloc.class, line)
				.addEqualsFilter(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_InOutLine_ID, line.getM_InOutLine_ID())
				//
				.orderBy()
				.addColumn(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_ReceiptSchedule_Alloc_ID)
				.endOrderBy();
	}

	@Override
	public List<I_M_InOut> retrieveCompletedReceipts(final I_M_ReceiptSchedule receiptSchedule)
	{
		return queryCompletedReceipts(receiptSchedule).list();
	}

	@Override
	public boolean hasCompletedReceipts(final I_M_ReceiptSchedule receiptSchedule)
	{
		return queryCompletedReceipts(receiptSchedule).anyMatch();
	}

	private IQuery<I_M_InOut> queryCompletedReceipts(final I_M_ReceiptSchedule receiptSchedule)
	{
		return createRsaForRsQueryBuilder(receiptSchedule, I_M_ReceiptSchedule_Alloc.class)
				.andCollect(I_M_ReceiptSchedule_Alloc.COLUMN_M_InOutLine_ID)
				.andCollect(I_M_InOutLine.COLUMN_M_InOut_ID)
				.addInArrayOrAllFilter(I_M_InOut.COLUMNNAME_DocStatus, IDocument.STATUS_Completed)
				.create();
	}

	@Override
	public Set<I_M_ReceiptSchedule> retrieveForInvoiceCandidate(final I_C_Invoice_Candidate candidate)
	{
		final Set<I_M_ReceiptSchedule> schedules;

		final int tableID = candidate.getAD_Table_ID();

		// invoice candidate references an orderline
		if (tableID == InterfaceWrapperHelper.getTableId(I_C_OrderLine.class))
		{

			if (candidate.getC_OrderLine_ID() > 0)
			{
				final org.compiere.model.I_C_OrderLine orderLine = candidate.getC_OrderLine();
				final I_M_ReceiptSchedule schedForOrderLine = retrieveForRecord(orderLine);
				if (schedForOrderLine == null)
				{
					schedules = ImmutableSet.of();
				}
				else
				{
					schedules = ImmutableSet.of(schedForOrderLine);
				}
			}
			else
			{
				schedules = ImmutableSet.of();
			}
		}

		// invoice candidate references an inoutline
		else if (tableID == InterfaceWrapperHelper.getTableId(I_M_InOutLine.class))
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(candidate);
			final String trxName = InterfaceWrapperHelper.getTrxName(candidate);

			final I_M_InOutLine inoutLine = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_M_InOutLine.class, trxName);
			if (inoutLine == null)
			{
				schedules = ImmutableSet.of();
			}
			else
			{
				schedules = ImmutableSet.copyOf(retrieveRsForInOutLine(inoutLine));
			}
		}
		else
		{
			schedules = ImmutableSet.of();
		}

		return schedules;
	}

	@Override
	public List<I_M_ReceiptSchedule> retrieveRsForInOutLine(@Nullable final org.compiere.model.I_M_InOutLine iol)
	{
		if (iol == null)
		{
			return Collections.emptyList();
		}

		final I_M_InOut io = iol.getM_InOut();
		if (io == null)
		{
			// this shall never happen
			return Collections.emptyList();
		}

		if (io.isSOTrx())
		{
			// sales side inouts do not have receipt schedules
			return Collections.emptyList();
		}

		return retrieveRsaForInOutLineQuery(iol)
				.andCollect(I_M_ReceiptSchedule_Alloc.COLUMN_M_ReceiptSchedule_ID)
				.create()
				.list();
	}

	@Override
	public IQueryBuilder<I_M_ReceiptSchedule> createQueryForReceiptScheduleSelection(final Properties ctx, final IQueryFilter<I_M_ReceiptSchedule> userSelectionFilter)
	{
		final IQueryBuilder<I_M_ReceiptSchedule> queryBuilder = queryBL
				.createQueryBuilder(I_M_ReceiptSchedule.class, ctx, ITrx.TRXNAME_None)
				.filter(userSelectionFilter)
				// NOTE: IsClosed=Y always implies Processed=Y (see ReceiptScheduleBL.close/reopen).
				// Both filters are kept for clarity and as a safety net.
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_IsClosed, false)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		return queryBuilder;
	}

	@Override
	public boolean existsExportedReceiptScheduleForOrder(final @NonNull OrderId orderId)
	{
		return queryBL
				.createQueryBuilder(I_M_ReceiptSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_C_Order_ID, orderId)
				// NOTE: IsClosed=Y always implies Processed=Y (see ReceiptScheduleBL.close/reopen).
				// Both filters are kept for clarity and as a safety net.
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_IsClosed, false)
				.addInArrayFilter(I_M_ReceiptSchedule.COLUMNNAME_ExportStatus, APIExportStatus.EXPORTED_STATES)
				.create()
				.anyMatch();
	}

	@NonNull
	public Map<ReceiptScheduleId, I_M_ReceiptSchedule> getByIds(@NonNull final ImmutableSet<ReceiptScheduleId> receiptScheduleIds)
	{
		final List<I_M_ReceiptSchedule> receiptSchedules = InterfaceWrapperHelper.loadByRepoIdAwares(receiptScheduleIds, I_M_ReceiptSchedule.class);

		if (Check.isEmpty(receiptSchedules))
		{
			return ImmutableMap.of();
		}

		return Maps.uniqueIndex(receiptSchedules, (receiptSchedule) -> ReceiptScheduleId.ofRepoId(receiptSchedule.getM_ReceiptSchedule_ID()));
	}

	public I_M_ReceiptSchedule getById(@NonNull final ReceiptScheduleId receiptScheduleId)
	{
		return InterfaceWrapperHelper.load(receiptScheduleId, I_M_ReceiptSchedule.class);
	}

	@Override
	public List<ReceiptScheduleId> retainLUQtySchedules(@NonNull final List<ReceiptScheduleId> receiptSchedules)
	{
		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_C_OrderLine.COLUMNNAME_QtyLU)
				.addCompareFilter(I_C_OrderLine.COLUMNNAME_QtyLU, CompareQueryFilter.Operator.GREATER, 0)
				.andCollectChildren(I_M_ReceiptSchedule.COLUMNNAME_C_OrderLine_ID, I_M_ReceiptSchedule.class)
				.addInArrayFilter(I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID, receiptSchedules)
				.create()
				.listIds(ReceiptScheduleId::ofRepoId);
	}

	@Override
	@NonNull
	public List<ReceiptScheduleId> listIdsByQuery(@NonNull final ReceiptScheduleQuery query)
	{
		return buildQuery(query)
				.listIds(ReceiptScheduleId::ofRepoId);
	}

	@NonNull
	@Override
	public Optional<ReceiptScheduleId> getIdByQuery(@NonNull final ReceiptScheduleQuery query)
	{
		// ofRepoIdOrNull, not ofRepoId: firstIdOnlyOptional wraps Optional.ofNullable(idMapper.apply(firstIdOnly())),
		// so the mapper answers null for the not-found sentinel (-1) instead of the strict ofRepoId throwing
		// "M_ReceiptSchedule_ID > 0 but it was -1". Callers of this method (e.g. ReceiptService) scope the query
		// by a genuinely unique key (external id or order line), so firstIdOnly's multiplicity check is correct here.
		return buildQuery(query)
				.firstIdOnlyOptional(ReceiptScheduleId::ofRepoIdOrNull);
	}

	@Override
	public boolean existsByQuery(@NonNull final ReceiptScheduleQuery query)
	{
		return buildQuery(query).anyMatch();
	}

	@Override
	public void deleteByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		// Intentionally no addOnlyActiveRecordsFilter(): when an order line is deleted, all linked receipt
		// schedules must be cleaned up regardless of their IsActive state. Leaving IsActive=false schedules
		// behind would leave orphaned rows tied to a non-existent order line.
		// Note on double ReceiptScheduleDeletedEvent: for any IsActive=false schedule in the result set,
		// the TYPE_BEFORE_DELETE fired by delete() below will post a ReceiptScheduleDeletedEvent via
		// M_ReceiptSchedule_PostMaterialEvent. A prior event was posted when the schedule was deactivated.
		// This double-fire is harmless: in normal application flow, inactivateReceiptSchedules() immediately
		// follows setIsActive(false) with deleteRecord(), so IsActive=false schedules never persist as a
		// steady state. If one does exist (e.g. after a crash), its QtyToMove will be 0 (the updateQtys
		// interceptor zeroes it on deactivation), making the second event a no-op for MRP.
		// Note on getQtyOrdered in the deletion event: IReceiptScheduleQtysBL.getQtyOrdered() reads the
		// QtyOrdered column directly and is not affected by the Processed=false written by updateDirectly()
		// below, so the event reports the correct ordered quantity.
		final IQuery<I_M_ReceiptSchedule> receiptSchedulesForOrderLine = queryBL
				.createQueryBuilder(I_M_ReceiptSchedule.class)
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.create();

		// Guard: refuse to delete if any receipt schedule has an active alloc with M_InOutLine_ID set.
		// IsActive=false allocs indicate reversed receipts and must not block deletion.
		// Deleting schedules with active received allocs would corrupt receipt and invoice-candidate history.
		// Note: there is a narrow TOCTOU window between this check and the delete() below — a concurrent
		// receipt posting could insert a received alloc after the anyMatch() returns false. This is an
		// accepted risk: receipt confirmation is always a user-triggered, transactional operation making
		// the window extremely unlikely, and the DEFERRABLE INITIALLY DEFERRED FK on M_ReceiptSchedule_Alloc
		// provides a last-resort safety net at commit time.
		final boolean hasReceivedAllocations = queryBL
				.createQueryBuilder(I_M_ReceiptSchedule_Alloc.class)
				.addInSubQueryFilter(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_ReceiptSchedule_ID, I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID, receiptSchedulesForOrderLine)
				.addNotNull(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_InOutLine_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
		if (hasReceivedAllocations)
		{
			throw new AdempiereException(MSG_CANNOT_DELETE_ORDER_LINE_RECEIPT_SCHEDULE);
		}

		// updateDirectly() goes straight to SQL and bypasses ALL model interceptors — not just the processed
		// guard, but also M_ReceiptSchedule_PostMaterialEvent. We need this for two reasons:
		//   1. M_ReceiptSchedule_PostMaterialEvent watches COLUMNNAME_Processed in its ifColumnsChanged list.
		//      A normal model-based update setting Processed=false would fire a spurious ReceiptScheduleUpdatedEvent
		//      immediately before the delete, causing MRP to process a phantom "supply restored" signal that is
		//      immediately contradicted by the ReceiptScheduleDeletedEvent that follows. Using updateDirectly()
		//      suppresses that spurious event entirely.
		//   2. The Processed=true flag prevents model-based deletion (the ADempiere PO layer guards processed
		//      records). Clearing it via SQL unblocks the subsequent delete().
		// This also clears Processed=false on any IsActive=false schedules in scope, which is harmless
		// since they are deleted in the very next statement.
		// The model-based delete() below fires all TYPE_BEFORE_DELETE interceptors, which correctly handle:
		//   - HU lifecycle: M_ReceiptSchedule validator in de.metas.handlingunits.base destroys any
		//     Planning-status HUs pre-assigned to the schedule, then deletes ALL linked alloc rows
		//     model-by-model (active and inactive) so their own interceptors fire. The receipt documents
		//     (M_InOut / M_InOutLine) are NOT touched — only the alloc booking links are removed, which
		//     is safe. The FK on M_ReceiptSchedule_Alloc is DEFERRABLE INITIALLY DEFERRED, so alloc
		//     deletions inside TYPE_BEFORE_DELETE are valid before the parent row is removed.
		//   - MRP: M_ReceiptSchedule_PostMaterialEvent posts ReceiptScheduleDeletedEvent → MRP removes the supply.
		//   - Order line quantities: M_ReceiptSchedule.propagateQtysToOrderLine resets QtyOrderedOverUnder
		//     (harmless when the order line is also being deleted immediately after).
		receiptSchedulesForOrderLine.updateDirectly(rs -> {
			rs.setProcessed(false);
			return IQueryUpdater.MODEL_UPDATED;
		});

		receiptSchedulesForOrderLine.delete();
	}

	@NonNull
	private IQuery<I_M_ReceiptSchedule> buildQuery(@NonNull final ReceiptScheduleQuery query)
	{
		final IQueryBuilder<I_M_ReceiptSchedule> builder = queryBL.createQueryBuilder(I_M_ReceiptSchedule.class)
				.addOnlyActiveRecordsFilter();

		final ExternalSystemIdWithExternalIds externalSystemIdWithExternalIds = query.getExternalSystemIdWithExternalIds();
		if (externalSystemIdWithExternalIds != null)
		{
			builder.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_ExternalSystem_ID, externalSystemIdWithExternalIds.getExternalSystemId().getRepoId());

			final ExternalHeaderIdWithExternalLineIds externalIds = externalSystemIdWithExternalIds.getExternalHeaderIdWithExternalLineIds();
			builder.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_ExternalHeaderId, externalIds.getExternalHeaderId().getValue());

			if (!Check.isEmpty(externalIds.getExternalLineIdsAsString()))
			{
				builder.addInArrayFilter(I_M_ReceiptSchedule.COLUMNNAME_ExternalLineId, externalIds.getExternalLineIdsAsString());
			}
		}

		if (query.getOrderLineId() != null)
		{
			builder.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_C_OrderLine_ID, query.getOrderLineId());
		}

		if (query.getProductId() != null)
		{
			builder.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_M_Product_ID, query.getProductId());
		}

		if (query.getWarehouseId() != null)
		{
			builder.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_ID, query.getWarehouseId());
		}

		if (query.getOrgId() != null)
		{
			builder.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_AD_Org_ID, query.getOrgId());
		}

		if (query.getAttributesKey() != null)
		{
			builder.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_M_AttributeSetInstance_ID, query.getAttributesKey().getAsString(), ASIQueryFilterModifier.instance);
		}

		// Filter by quantity
		if (query.isOnlyNonZeroQty())
		{
			builder.addNotEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_QtyToMove, 0);
		}

		return builder.create();
	}
}
