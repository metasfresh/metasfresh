package de.metas.handlingunits.trace;

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
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceSpecification.RecursionMode;
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
	public void addEvent(@NonNull final HUTraceEvent huTraceEvent)
	{
		final HUTraceSpecification query = HUTraceSpecification.builder()
				.huId(huTraceEvent.getHuId())
				.eventTime(huTraceEvent.getEventTime())
				.recursionMode(RecursionMode.NONE)
				.build();

		final I_M_HU_Trace dbRecord;
		final List<I_M_HU_Trace> existingDbRecords = queryDbRecord(query);
		if (existingDbRecords.isEmpty())
		{
			dbRecord = newInstance(I_M_HU_Trace.class);
		}
		else
		{
			Check.errorIf(existingDbRecords.size() > 1,
					"Expected only one M_HU_Trace record for the given query, but found {}; query={}, M_HU_Trace records={}",
					existingDbRecords.size(), query, existingDbRecords);
			dbRecord = existingDbRecords.get(0);
		}
		copyToDbRecord(huTraceEvent, dbRecord);
		save(dbRecord);
	}

	public List<HUTraceRecord> query(@NonNull final HUTraceSpecification query)
	{
		return queryDbRecord(query)
				.stream()
				.map(r -> asHuTraceRecord(r))
				.collect(Collectors.toList());
	}

	/**
	 * Return records according to the given specification. If the specification is "empty", i.e. if it specifies no conditions, then return an empty list to prevent an {@code OutOfMemoryError}.
	 * 
	 * @param query
	 * @return
	 * @see HUTraceSpecification
	 */
	private List<I_M_HU_Trace> queryDbRecord(@NonNull final HUTraceSpecification query)
	{
		final IQueryBuilder<I_M_HU_Trace> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class)
				.addOnlyActiveRecordsFilter();

		boolean emptySpec = true;

		if (query.getEventTime() != null)
		{
			final Timestamp eventTime = TimeUtil.asTimestamp(query.getEventTime());
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_EventTime, eventTime);
			emptySpec = false;
		}
		if (query.getHuId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_HU_ID, query.getHuId());
			emptySpec = false;
		}
		if (query.getHuSourceId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_HU_Source_ID, query.getHuSourceId());
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

		// no matter which recursion mode, we can always add the recrods we already have
		result.addAll(nonRecursiveList);

		switch (query.getRecursionMode())
		{
			case NONE:
				// nothing else to be done
				break;
			case BACKWARD:
				// recurse and add the records whose M_HU_IDs show up as M_HU_Source_IDs in the records we already loaded
				final List<Integer> huSourceIDs = nonRecursiveList.stream()
						.map(dbRecord -> dbRecord.getM_HU_Source_ID())
						.filter(huSourceId -> huSourceId > 0)
						.sorted()
						.distinct()
						.collect(Collectors.toList());
				for (final int huSourceId : huSourceIDs)
				{
					result.addAll(queryDbRecord(HUTraceSpecification.builder()
							.huId(huSourceId)
							.recursionMode(RecursionMode.BACKWARD)
							.build()));
				}
				break;
			case FORWARD:
				final List<Integer> huIDs = nonRecursiveList.stream()
						.map(dbRecord -> dbRecord.getM_HU_ID())
						.sorted()
						.distinct()
						.collect(Collectors.toList());
				for (final int huId : huIDs)
				{
					// get the records where our M_HU_IDs are the M_HU_Source_IDs
					final List<I_M_HU_Trace> directFollowupRecords = queryDbRecord(HUTraceSpecification.builder()
							.huSourceId(huId)
							.recursionMode(RecursionMode.NONE)
							.build());
					final List<Integer> directFollowupHuIDs = directFollowupRecords.stream()
							.map(directFollowupRecord -> directFollowupRecord.getM_HU_ID())
							.sorted()
							.distinct()
							.collect(Collectors.toList());

					// and now expand on those direct follow ups
					for (final int directFollowupHuID : directFollowupHuIDs)
					{
						result.addAll(queryDbRecord(HUTraceSpecification.builder()
								.huId(directFollowupHuID)
								.recursionMode(RecursionMode.FORWARD)
								.build()));
					} ;
				} ;
			default:
				break;
		}

		return ImmutableList.copyOf(result);
	}

	private HUTraceRecord asHuTraceRecord(@NonNull final I_M_HU_Trace dbRecord)
	{
		return HUTraceRecord.builder()
				.huId(dbRecord.getM_HU_ID())
				.eventTime(dbRecord.getEventTime().toInstant())
				.huSourceId(dbRecord.getM_HU_Source_ID())
				.inOutId(dbRecord.getM_InOut_ID())
				.movementId(dbRecord.getM_Movement_ID())
				.ppOrderId(dbRecord.getPP_Order_ID())
				.shipmentScheduleId(dbRecord.getM_ShipmentSchedule_ID())
				.build();
	}

	private void copyToDbRecord(@NonNull final HUTraceEvent huTraceRecord, @NonNull final I_M_HU_Trace dbRecord)
	{
		dbRecord.setM_HU_ID(huTraceRecord.getHuId());
		dbRecord.setEventTime(TimeUtil.asTimestamp(huTraceRecord.getEventTime()));
		dbRecord.setM_HU_Source_ID(huTraceRecord.getHuSourceId());
		dbRecord.setM_InOut_ID(huTraceRecord.getInOutId());
		dbRecord.setM_Movement_ID(huTraceRecord.getMovementId());
		dbRecord.setM_ShipmentSchedule_ID(huTraceRecord.getShipmentScheduleId());
		dbRecord.setPP_Order_ID(huTraceRecord.getPpOrderId());
	}
}
