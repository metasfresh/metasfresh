package de.metas.handlingunits.trace;

import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.model.util.ModelByIdComparator;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import de.metas.handlingunits.trace.HUTraceSpecification.RecursionMode;
import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Service
public class HUTraceRepository
{
	private static final Logger logger = LogManager.getLogger(HUTraceRepository.class);

	/**
	 * Persists the given event.
	 * If an event with the same properties was already persisted earlier
	 * then that record is loaded and logger, but nothing more is done.
	 * 
	 * @param huTraceEvent
	 * @return {@code true} if a new record was inserted, {@code false} if an existing one was updated.
	 */
	public boolean addEvent(@NonNull final HUTraceEvent huTraceEvent)
	{
		final HUTraceSpecification query = huTraceEvent.asQuery();

		final I_M_HU_Trace dbRecord;
		final List<I_M_HU_Trace> existingDbRecords = queryDbRecord(query);
		final boolean inserted = existingDbRecords.isEmpty();
		if (inserted)
		{
			dbRecord = newInstance(I_M_HU_Trace.class);
			logger.info("Found no existing M_HU_Trace record; creating new one; query={}", query);

			copyToDbRecord(huTraceEvent, dbRecord);
			save(dbRecord);
		}
		else
		{
			Check.errorIf(existingDbRecords.size() > 1,
					"Expected only one M_HU_Trace record for the given query, but found {}; query={}, M_HU_Trace records={}",
					existingDbRecords.size(), query, existingDbRecords);

			dbRecord = existingDbRecords.get(0);
			logger.info("Found exiting M_HU_Trace record; nothing to do; query={}; record={}", query, dbRecord);
		}

		return inserted;
	}

	/**
	 * Return records according to the given specification. If the specification is "empty", i.e. if it specifies no conditions, then return an empty list to prevent an {@code OutOfMemoryError}.
	 * 
	 * @param query
	 * @return
	 * @see HUTraceSpecification
	 */
	public List<HUTraceEvent> query(@NonNull final HUTraceSpecification query)
	{
		return queryDbRecord(query)
				.stream()
				.map(r -> asHuTraceEvent(r))
				.collect(Collectors.toList());
	}

	/**
	 * Return all records; this makes absolutely no sense in production; Intended to be used only use for testing.
	 * 
	 * @return
	 */
	@VisibleForTesting
	/* package */ List<HUTraceEvent> queryAll()
	{
		Check.errorUnless(Adempiere.isUnitTestMode(), "The method queryAll() shall only be invoked from test code");
		
		final IQueryBuilder<I_M_HU_Trace> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class)
				.orderBy().addColumn(I_M_HU_Trace.COLUMN_M_HU_Trace_ID).endOrderBy();

		return queryBuilder.create()
				.stream()
				.map(r -> asHuTraceEvent(r))
				.collect(Collectors.toList());
	}

	private List<I_M_HU_Trace> queryDbRecord(@NonNull final HUTraceSpecification query)
	{
		final IQueryBuilder<I_M_HU_Trace> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class)
				.addOnlyActiveRecordsFilter();

		final boolean emptySpec = configureQueryBuilder(query, queryBuilder);
		if (emptySpec)
		{
			return ImmutableList.of();
		}

		final List<I_M_HU_Trace> nonRecursiveList = queryBuilder
				.orderBy().addColumn(I_M_HU_Trace.COLUMN_EventTime).endOrderBy()
				.create()
				.list();

		// use the tree set to make sure we have no duplicates
		final Set<I_M_HU_Trace> result = new TreeSet<I_M_HU_Trace>(ModelByIdComparator.instance);

		// no matter which recursion mode, we can always add the records we already have
		result.addAll(nonRecursiveList);

		switch (query.getRecursionMode())
		{
			case NONE:
				// nothing else to be done
				break;
			case BACKWARD:
				// recurse and add the records whose M_HU_IDs show up as M_HU_Source_IDs in the records we already loaded
				final List<Integer> vhuSourceIDs = nonRecursiveList.stream()
						.map(dbRecord -> dbRecord.getVHU_Source_ID())
						.filter(vhuSourceId -> vhuSourceId > 0)
						.sorted()
						.distinct()
						.collect(Collectors.toList());
				for (final int vhuSourceId : vhuSourceIDs)
				{
					result.addAll(queryDbRecord(HUTraceSpecification.builder()
							.vhuId(vhuSourceId)
							.recursionMode(RecursionMode.BACKWARD)
							.build()));
				}
				break;
			case FORWARD:
				final List<Integer> vhuIDs = nonRecursiveList.stream()
						.map(dbRecord -> dbRecord.getVHU_ID())
						.sorted()
						.distinct()
						.collect(Collectors.toList());
				for (final int vhuId : vhuIDs)
				{
					// get the records where our M_HU_IDs are the M_HU_Source_IDs
					final List<I_M_HU_Trace> directFollowupRecords = queryDbRecord(HUTraceSpecification.builder()
							.vhuSourceId(vhuId)
							.recursionMode(RecursionMode.NONE)
							.build());
					final List<Integer> directFollowupVhuIDs = directFollowupRecords.stream()
							.map(directFollowupRecord -> directFollowupRecord.getVHU_ID())
							.sorted()
							.distinct()
							.collect(Collectors.toList());

					// and now expand on those direct follow ups
					for (final int directFollowupVhuID : directFollowupVhuIDs)
					{
						result.addAll(queryDbRecord(HUTraceSpecification.builder()
								.vhuId(directFollowupVhuID)
								.recursionMode(RecursionMode.FORWARD)
								.build()));
					}
				}
				break;
			default:
				break;
		}

		return ImmutableList.copyOf(result);
	}

	private boolean configureQueryBuilder(
			@NonNull final HUTraceSpecification query, 
			@NonNull final IQueryBuilder<I_M_HU_Trace> queryBuilder)
	{
		boolean emptySpec = true;

		if (query.getEventTime() != null)
		{
			final Timestamp eventTime = TimeUtil.asTimestamp(query.getEventTime());
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_EventTime, eventTime);
			emptySpec = false;
		}
		if (query.getVhuId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_VHU_ID, query.getVhuId());
			emptySpec = false;
		}
		if (query.getProductId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_Product_ID, query.getProductId());
			emptySpec = false;
		}
		if (query.getQty() != null)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_Qty, query.getQty());
			emptySpec = false;
		}
		if (!Check.isEmpty(query.getVhuStatus()))
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_VHUStatus, query.getVhuStatus());
			emptySpec = false;
		}
		if (query.getVhuSourceId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_VHU_Source_ID, query.getVhuSourceId());
			emptySpec = false;
		}
		if (query.getTopLevelHuId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_HU_ID, query.getTopLevelHuId());
			emptySpec = false;
		}
		if (query.getInOutId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_InOut_ID, query.getInOutId());
			emptySpec = false;
		}
		if (query.getMovementId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_Movement_ID, query.getMovementId());
			emptySpec = false;
		}
		if (query.getCostCollectorId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_PP_Cost_Collector_ID, query.getCostCollectorId());
			emptySpec = false;
		}
		if (query.getPpOrderId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_PP_Order_ID, query.getPpOrderId());
			emptySpec = false;
		}
		if (query.getShipmentScheduleId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_ShipmentSchedule_ID, query.getShipmentScheduleId());
			emptySpec = false;
		}
		if (query.getDocTypeId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_C_DocType_ID, query.getDocTypeId());
			emptySpec = false;
		}
		if (!Check.isEmpty(query.getDocStatus()))
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_DocStatus, query.getDocStatus());
			emptySpec = false;
		}
		if (query.getHuTrxLineId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_HU_Trx_Line_ID, query.getHuTrxLineId());
			emptySpec = false;
		}
		return emptySpec;
	}

	private HUTraceEvent asHuTraceEvent(@NonNull final I_M_HU_Trace dbRecord)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.costCollectorId(dbRecord.getPP_Cost_Collector_ID())
				.ppOrderId(dbRecord.getPP_Order_ID())
				.docStatus(dbRecord.getDocStatus())
				.eventTime(dbRecord.getEventTime().toInstant())
				.vhuId(dbRecord.getVHU_ID())
				.productId(dbRecord.getM_Product_ID())
				.qty(dbRecord.getQty())
				.huTrxLineId(dbRecord.getM_HU_Trx_Line_ID())
				.vhuStatus(dbRecord.getVHUStatus())
				.topLevelHuId(dbRecord.getM_HU_ID())
				.vhuSourceId(dbRecord.getVHU_Source_ID())
				.inOutId(dbRecord.getM_InOut_ID())
				.movementId(dbRecord.getM_Movement_ID())
				.shipmentScheduleId(dbRecord.getM_ShipmentSchedule_ID())
				.type(HUTraceType.valueOf(dbRecord.getHUTraceType()));

		if (!isNull(dbRecord, I_M_HU_Trace.COLUMNNAME_C_DocType_ID))
		{
			builder.docTypeId(dbRecord.getC_DocType_ID()); // note that zero means "new", and not "nothing" or null
		}

		return builder.build();
	}

	private void copyToDbRecord(
			@NonNull final HUTraceEvent huTraceRecord,
			@NonNull final I_M_HU_Trace dbRecord)
	{
		if (huTraceRecord.getDocTypeId() != null)
		{
			dbRecord.setC_DocType_ID(huTraceRecord.getDocTypeId()); // note that zero means "new", and not "nothing" or null
		}
		dbRecord.setDocStatus(huTraceRecord.getDocStatus());
		dbRecord.setEventTime(TimeUtil.asTimestamp(huTraceRecord.getEventTime()));
		dbRecord.setHUTraceType(huTraceRecord.getType().toString());
		dbRecord.setVHU_ID(huTraceRecord.getVhuId());
		dbRecord.setM_Product_ID(huTraceRecord.getProductId());
		dbRecord.setQty(huTraceRecord.getQty());
		dbRecord.setVHUStatus(huTraceRecord.getVhuStatus());
		dbRecord.setM_HU_Trx_Line_ID(huTraceRecord.getHuTrxLineId());
		dbRecord.setM_HU_ID(huTraceRecord.getTopLevelHuId());
		dbRecord.setVHU_Source_ID(huTraceRecord.getVhuSourceId());
		dbRecord.setM_InOut_ID(huTraceRecord.getInOutId());
		dbRecord.setM_Movement_ID(huTraceRecord.getMovementId());
		dbRecord.setM_ShipmentSchedule_ID(huTraceRecord.getShipmentScheduleId());
		dbRecord.setPP_Cost_Collector_ID(huTraceRecord.getCostCollectorId());
		dbRecord.setPP_Order_ID(huTraceRecord.getPpOrderId());
	}
}
