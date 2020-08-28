package de.metas.inoutcandidate.api.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.document.engine.IDocument;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderId;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

public class ReceiptScheduleDAO implements IReceiptScheduleDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * When mass cache invalidation, above this threshold we will invalidate ALL shipment schedule records instead of particular IDS
	 */
	private static final int CACHE_INVALIDATE_ALL_THRESHOLD = 200;

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
	public I_M_ReceiptSchedule retrieveForRecord(Object model)
	{
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
		return createRsaForRsQueryBuilder(receiptSchedule, I_M_ReceiptSchedule_Alloc.class)
				.andCollect(I_M_ReceiptSchedule_Alloc.COLUMN_M_InOutLine_ID)
				.andCollect(I_M_InOutLine.COLUMN_M_InOut_ID)
				.addInArrayOrAllFilter(I_M_InOut.COLUMNNAME_DocStatus, IDocument.STATUS_Completed)
				.create()
				.list();
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
	public IQueryBuilder<I_M_ReceiptSchedule> createQueryForShipmentScheduleSelection(final Properties ctx, final IQueryFilter<I_M_ReceiptSchedule> userSelectionFilter)
	{
		final IQueryBuilder<I_M_ReceiptSchedule> queryBuilder = queryBL
				.createQueryBuilder(I_M_ReceiptSchedule.class, ctx, ITrx.TRXNAME_None)
				.filter(userSelectionFilter)
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_Processed, false)
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
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_Processed, false)
				.addInArrayFilter(I_M_ReceiptSchedule.COLUMNNAME_ExportStatus, APIExportStatus.EXPORTED_STATES)
				.create()
				.anyMatch();
	}

	@Override
	public void updateExportStatus(final String exportStatus, final PInstanceId pinstanceId)
	{
		updateColumnForSelection(
				I_M_ReceiptSchedule.COLUMNNAME_ExportStatus,
				exportStatus,
				false /* updateOnlyIfNull */,
				pinstanceId
		);
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

	/**
	 * Mass-update a given shipment schedule column.
	 * <p>
	 * If there were any changes and the invalidate parameter is on true, those shipment schedules will be invalidated.
	 *
	 * @param inoutCandidateColumnName {@link I_M_ReceiptSchedule}'s column to update
	 * @param value                    value to set (you can also use {@link ModelColumnNameValue})
	 * @param updateOnlyIfNull         if true then it will update only if column value is null (not set)
	 * @param selectionId              ShipmentSchedule selection (AD_PInstance_ID)
	 * @param trxName
	 */
	private final <ValueType> void updateColumnForSelection(
			final String inoutCandidateColumnName,
			final ValueType value,
			final boolean updateOnlyIfNull,
			final PInstanceId selectionId
	)
	{
		//
		// Create the selection which we will need to update
		final IQueryBuilder<I_M_ReceiptSchedule> selectionQueryBuilder = queryBL
				.createQueryBuilder(I_M_ReceiptSchedule.class)
				.setOnlySelection(selectionId)
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_Processed, false) // do not touch the processed shipment schedules
				;

		if (updateOnlyIfNull)
		{
			selectionQueryBuilder.addEqualsFilter(inoutCandidateColumnName, null);
		}
		final PInstanceId selectionToUpdateId = selectionQueryBuilder.create().createSelection();
		if (selectionToUpdateId == null)
		{
			// nothing to update
			return;
		}

		//
		// Update our new selection
		final int countUpdated = queryBL.createQueryBuilder(I_M_ReceiptSchedule.class)
				.setOnlySelection(selectionToUpdateId)
				.create()
				.updateDirectly()
				.addSetColumnValue(inoutCandidateColumnName, value)
				.execute();

		//
		// Cache invalidate
		// We have to do this even if invalidate=false
		cacheInvalidateBySelectionId(selectionToUpdateId, countUpdated);
	}

	private void cacheInvalidateBySelectionId(
			@NonNull final PInstanceId selectionId,
			final long estimatedSize)
	{
		final CacheInvalidateMultiRequest request;
		if (estimatedSize < 0)
		{
			// unknown estimated size
			request = CacheInvalidateMultiRequest.allRecordsForTable(I_M_ReceiptSchedule.Table_Name);
		}
		else if (estimatedSize == 0)
		{
			// no records
			// unknown estimated size
			request = null;
		}
		else if (estimatedSize <= CACHE_INVALIDATE_ALL_THRESHOLD)
		{
			// relatively small amount of records
			// => fetch and reset individually
			final ImmutableSet<ReceiptScheduleId> shipmentScheduleIds = queryBL.createQueryBuilder(I_M_ReceiptSchedule.class)
					.setOnlySelection(selectionId)
					.create()
					.listIds(ReceiptScheduleId::ofRepoId);
			if (!shipmentScheduleIds.isEmpty())
			{
				request = CacheInvalidateMultiRequest.rootRecords(I_M_ReceiptSchedule.Table_Name, shipmentScheduleIds);
			}
			else
			{
				// no records found => do nothing
				request = null;
			}
		}
		else
		{
			// large amount of records
			// => instead of fetching all IDs better invalidate the whole table
			request = CacheInvalidateMultiRequest.allRecordsForTable(I_M_ReceiptSchedule.Table_Name);
		}

		//
		// Perform the actual cache invalidation
		if (request != null)
		{
			CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(ITrx.TRXNAME_ThreadInherited, request);
		}
	}
}
