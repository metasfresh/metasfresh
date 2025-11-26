package de.metas.inoutcandidate.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.ShipmentScheduleAllocQuery;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_M_InOutLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;
import static java.math.BigDecimal.ZERO;

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

public class ShipmentScheduleAllocDAO implements IShipmentScheduleAllocDAO
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleAllocDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> retrieveOnShipmentLineRecordsQuery(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return toSqlQuery(
				ShipmentScheduleAllocQuery.builder()
						.scheduleIds(ShipmentScheduleAndJobScheduleIdSet.of(shipmentScheduleId))
						.alreadyShipped(true)
						.build()
		);
	}

	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveNotOnShipmentLineRecords(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final Class<T> clazz)
	{
		return list(
				clazz,
				ShipmentScheduleAllocQuery.builder()
						.scheduleIds(ShipmentScheduleAndJobScheduleIdSet.of(shipmentScheduleId))
						.alreadyShipped(false)
						.build()
		);
	}

	@Override
	public Stream<I_M_ShipmentSchedule_QtyPicked> stream(@NonNull final ShipmentScheduleAllocQuery query)
	{
		return stream(I_M_ShipmentSchedule_QtyPicked.class, query);
	}

	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> Stream<T> stream(@NonNull Class<T> type, @NonNull final ShipmentScheduleAllocQuery query)
	{
		return toSqlQuery(query).create().stream(type);
	}

	public List<I_M_ShipmentSchedule_QtyPicked> list(@NonNull final ShipmentScheduleAllocQuery query)
	{
		return list(I_M_ShipmentSchedule_QtyPicked.class, query);
	}

	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> List<T> list(@NonNull Class<T> type, @NonNull final ShipmentScheduleAllocQuery query)
	{
		return toSqlQuery(query).create().list(type);
	}

	private IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> toSqlQuery(final @NonNull ShipmentScheduleAllocQuery query)
	{
		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> sqlQueryBuilder = queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID)
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_Picking_Job_Schedule_ID)
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID)
				.addOnlyActiveRecordsFilter();

		addFilterByShipmentScheduleAndJobScheduleIds(sqlQueryBuilder.getCompositeFilter(), query.getScheduleIds());
		addAlreadyShippedFilter(sqlQueryBuilder.getCompositeFilter(), query.getAlreadyShipped());

		if (query.getOnlyLUIds() != null && !query.getOnlyLUIds().isEmpty())
		{
			sqlQueryBuilder.addInArrayFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_LU_HU_ID, query.getOnlyLUIds());
		}

		return sqlQueryBuilder;
	}

	private static void addFilterByShipmentScheduleAndJobScheduleIds(
			@NonNull final ICompositeQueryFilter<I_M_ShipmentSchedule_QtyPicked> mainFilter,
			@Nullable final ShipmentScheduleAndJobScheduleIdSet scheduleIds)
	{
		if (scheduleIds == null || scheduleIds.isEmpty()) {return;}

		final ICompositeQueryFilter<I_M_ShipmentSchedule_QtyPicked> scheduleFilters = mainFilter.addCompositeQueryFilter()
				.setJoinOr();
		final HashSet<ShipmentScheduleId> entireShipmentScheduleIds = new HashSet<>();

		scheduleIds.forEachShipmentScheduleId((shipmentScheduleId, jobScheduleIds) -> {
			if (jobScheduleIds == null || jobScheduleIds.isEmpty())
			{
				entireShipmentScheduleIds.add(shipmentScheduleId);
			}
			else
			{
				scheduleFilters.addCompositeQueryFilter()
						.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
						.addInArrayFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_Picking_Job_Schedule_ID, jobScheduleIds);
			}
		});

		if (!entireShipmentScheduleIds.isEmpty())
		{
			scheduleFilters.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, entireShipmentScheduleIds);
		}
	}

	private static void addAlreadyShippedFilter(@NonNull final ICompositeQueryFilter<I_M_ShipmentSchedule_QtyPicked> filter, @Nullable final Boolean onShipmentLine)
	{
		if (onShipmentLine == null) {return;}

		// Case: only delivered (i.e., M_InOutLine_ID set)
		if (onShipmentLine)
		{
			filter.addNotEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, null);
		}
		// Case: only NOT delivered (i.e., M_InOutLine_ID is NOT set)
		else
		{
			filter.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, null);
		}
	}

	@NonNull
	@Override
	public BigDecimal retrieveNotOnShipmentLineQty(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return retrieveQtyPicked(
				ShipmentScheduleAllocQuery.builder()
						.scheduleIds(ShipmentScheduleAndJobScheduleIdSet.of(shipmentScheduleId))
						.alreadyShipped(false)
						.build()
		);
	}

	private BigDecimal retrieveQtyPicked(@NonNull final ShipmentScheduleAllocQuery query)
	{
		final BigDecimal qty = toSqlQuery(query)
				.create()
				.aggregate(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_QtyPicked, Aggregate.SUM, BigDecimal.class);

		return coalesceNotNull(qty, ZERO);
	}

	@NonNull
	@Override
	public BigDecimal retrieveQtyDelivered(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final BigDecimal qty = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, shipmentSchedule)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
				.andCollect(I_M_InOutLine.COLUMN_M_InOutLine_ID, I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_Processed, true)
				.create()
				.aggregate(I_M_InOutLine.COLUMNNAME_MovementQty, Aggregate.SUM, BigDecimal.class);

		return coalesceNotNull(qty, ZERO);
	}

	@NonNull
	@Override
	public BigDecimal retrieveQtyPickedAndUnconfirmed(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final BigDecimal qty = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, shipmentSchedule)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_Processed, false)
				.create()
				.aggregate(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_QtyPicked, Aggregate.SUM, BigDecimal.class);

		return coalesceNotNull(qty, ZERO);
	}

	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveAllQtyPickedRecords(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final Class<T> modelClass)
	{
		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryBuilder = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, shipmentSchedule)
				// For given shipment schedule
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID());

		queryBuilder.orderBy()
				.addColumn(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_QtyPicked_ID);

		return queryBuilder
				.create()
				.list(modelClass);
	}

	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveAllQtyPickedRecords(
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds,
			@NonNull final Class<T> modelClass)
	{
		if (shipmentScheduleIds.isEmpty()) {return ImmutableList.of();}

		return queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addInArrayFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID)
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_QtyPicked_ID)
				.create()
				.list(modelClass);
	}

	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveAllForInOutLine(
			@NonNull final I_M_InOutLine inoutLine,
			@NonNull final Class<T> modelClass)
	{
		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryBuilder = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, inoutLine)
				// For given shipment schedule
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, inoutLine.getM_InOutLine_ID());

		queryBuilder.orderBy()
				.addColumn(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_QtyPicked_ID);

		return queryBuilder.create()
				.list(modelClass);
	}

	@Override
	public List<I_M_ShipmentSchedule> retrieveSchedulesForInOut(final org.compiere.model.I_M_InOut inOut)
	{
		final IQueryBuilder<I_M_ShipmentSchedule> linesBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class, inOut)
				.addEqualsFilter(I_M_InOutLine.COLUMN_M_InOut_ID, inOut.getM_InOut_ID())
				.andCollectChildren(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, I_M_ShipmentSchedule_QtyPicked.class)
				.andCollect(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID);
		return linesBuilder.addOnlyActiveRecordsFilter().create().list(I_M_ShipmentSchedule.class);
	}

	@Override
	public IQueryBuilder<I_M_ShipmentSchedule> retrieveSchedulesForInOutLineQuery(final I_M_InOutLine inoutLine)
	{
		final IQueryBuilder<I_M_ShipmentSchedule> linesBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class, inoutLine)
				.addEqualsFilter(I_M_InOutLine.COLUMN_M_InOutLine_ID, inoutLine.getM_InOutLine_ID())
				.andCollectChildren(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, I_M_ShipmentSchedule_QtyPicked.class)
				.andCollect(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID);
		return linesBuilder
				.addOnlyActiveRecordsFilter();
	}

	@Override
	public void updateM_ShipmentSchedule_QtyPicked_ProcessedForShipment(@NonNull final I_M_InOut inOut)
	{
		final boolean newProcessedValue = inOut.isProcessed();

		final ICompositeQueryUpdater<I_M_ShipmentSchedule_QtyPicked> queryUpdater = queryBL
				.createCompositeQueryUpdater(I_M_ShipmentSchedule_QtyPicked.class)
				.addSetColumnValue(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_Processed, newProcessedValue);

		final int updated = queryBL
				.createQueryBuilder(I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOutLine.COLUMN_M_InOut_ID, inOut.getM_InOut_ID())
				.andCollectChildren(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, I_M_ShipmentSchedule_QtyPicked.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.update(queryUpdater);

		logger.debug("Updated {} M_ShipmentSchedule_QtyPicked to Processed={} for inout={}", updated, newProcessedValue, inOut);
	}

	@Override
	public List<I_M_ShipmentSchedule_QtyPicked> retrieveOnShipmentLineRecords(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return retrieveOnShipmentLineRecordsQuery(shipmentScheduleId).create().list();
	}

	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> ImmutableListMultimap<ShipmentScheduleId, T> retrieveNotOnShipmentLineRecordsByScheduleIds(
			@NonNull final Set<ShipmentScheduleId> scheduleIds,
			@NonNull Class<T> type)
	{
		return retrieveRecordsByScheduleIds(
				ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(scheduleIds),
				false,
				type);
	}

	@Override
	public ImmutableListMultimap<ShipmentScheduleId, I_M_ShipmentSchedule_QtyPicked> retrieveOnShipmentLineRecordsByScheduleIds(@NonNull final Set<ShipmentScheduleId> scheduleIds)
	{
		return retrieveOnShipmentLineRecordsByScheduleIds(ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(scheduleIds));
	}

	@Override
	public ImmutableListMultimap<ShipmentScheduleId, I_M_ShipmentSchedule_QtyPicked> retrieveOnShipmentLineRecordsByScheduleIds(@NonNull final ShipmentScheduleAndJobScheduleIdSet scheduleIds)
	{
		return retrieveRecordsByScheduleIds(
				scheduleIds,
				true,
				I_M_ShipmentSchedule_QtyPicked.class);
	}

	private <T extends I_M_ShipmentSchedule_QtyPicked> ImmutableListMultimap<ShipmentScheduleId, T> retrieveRecordsByScheduleIds(
			@NonNull final ShipmentScheduleAndJobScheduleIdSet scheduleIds,
			final boolean onShipmentLine,
			@NonNull final Class<T> type)
	{
		if (scheduleIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return stream(
				type,
				ShipmentScheduleAllocQuery.builder()
						.scheduleIds(scheduleIds)
						.alreadyShipped(onShipmentLine)
						.build()
		)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()),
						record -> record
				));
	}

	@NonNull
	public <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrievePickedOnTheFlyAndNotDelivered(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final Class<T> modelClass)
	{
		return queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleId)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_IsAnonymousHuPickedOnTheFly, true)
				.create()
				.list(modelClass);
	}

	@Override
	@NonNull
	public Set<OrderId> retrieveOrderIds(@NonNull final org.compiere.model.I_M_InOut inOut)
	{
		return queryBL.createQueryBuilder(I_M_InOutLine.class, inOut)
				.addEqualsFilter(I_M_InOutLine.COLUMN_M_InOut_ID, inOut.getM_InOut_ID())
				.andCollectChildren(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, I_M_ShipmentSchedule_QtyPicked.class)
				.andCollect(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID)
				.andCollect(I_M_ShipmentSchedule.COLUMN_C_Order_ID)
				.create()
				.listIds()
				.stream()
				.map(OrderId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
