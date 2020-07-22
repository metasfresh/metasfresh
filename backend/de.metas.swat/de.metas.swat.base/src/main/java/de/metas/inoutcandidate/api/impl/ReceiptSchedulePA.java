/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.inoutcandidate.api.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IReceiptSchedulePA;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.order.OrderId;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.adempiere.ad.trx.api.ITrx;

import java.util.Properties;

public class ReceiptSchedulePA implements IReceiptSchedulePA
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/** When mass cache invalidation, above this threshold we will invalidate ALL shipment schedule records instead of particular IDS */
	private static final int CACHE_INVALIDATE_ALL_THRESHOLD = 200;

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
				pinstanceId,
				false /* invalidate */
		);
	}

	/**
	 * Mass-update a given shipment schedule column.
	 *
	 * If there were any changes and the invalidate parameter is on true, those shipment schedules will be invalidated.
	 *
	 * @param inoutCandidateColumnName {@link I_M_ReceiptSchedule}'s column to update
	 * @param value value to set (you can also use {@link ModelColumnNameValue})
	 * @param updateOnlyIfNull if true then it will update only if column value is null (not set)
	 * @param selectionId ShipmentSchedule selection (AD_PInstance_ID)
	 * @param trxName
	 */
	private final <ValueType> void updateColumnForSelection(
			final String inoutCandidateColumnName,
			final ValueType value,
			final boolean updateOnlyIfNull,
			final PInstanceId selectionId,
			final boolean invalidate)
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
