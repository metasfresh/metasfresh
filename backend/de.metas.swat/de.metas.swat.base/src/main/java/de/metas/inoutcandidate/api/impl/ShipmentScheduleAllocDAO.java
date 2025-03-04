package de.metas.inoutcandidate.api.impl;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.InOutLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_M_InOutLine;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
	private final IShipmentSchedulePA shipmentScheduleDao = Services.get(IShipmentSchedulePA.class);

	/**
	 * Creates a filter which keeps {@link I_M_ShipmentSchedule_QtyPicked} all records (active or not),
	 * for given shipment schedule, which are <b>not</b> referenced by a shipment line.
	 */
	private IQueryFilter<I_M_ShipmentSchedule_QtyPicked> createNotOnShipmentLineFilter(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final boolean onShipmentLine = false; // NOT delivered ONLY
		return createShipmentLineFilter(shipmentSchedule, onShipmentLine);
	}

	/**
	 * Creates a filter which keeps {@link I_M_ShipmentSchedule_QtyPicked} all records (active or not), for given shipment schedule <b>AND</b> which were already delivered.
	 */
	private IQueryFilter<I_M_ShipmentSchedule_QtyPicked> createOnShipmentLineFilter(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final boolean onShipmentLine = true; // ONLY delivered
		return createShipmentLineFilter(shipmentSchedule, onShipmentLine);
	}

	/**
	 * Creates a filter which keeps {@link I_M_ShipmentSchedule_QtyPicked} all records (active or not), for given shipment schedule, which were:
	 * <ul>
	 * <li>already referenced by a shipment line, if <code>onShipmentLine</code> is true
	 * <li>or NOT referenced by a shipment line, if <code>onShipmentLine</code> is false
	 */
	private IQueryFilter<I_M_ShipmentSchedule_QtyPicked> createShipmentLineFilter(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			final boolean onShipmentLine)
	{
		final ICompositeQueryFilter<I_M_ShipmentSchedule_QtyPicked> filter = queryBL
				.createCompositeQueryFilter(I_M_ShipmentSchedule_QtyPicked.class)
				// For given shipment schedule
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID());

		// Case: only delivered (i.e. M_InOutLine_ID set)
		if (onShipmentLine)
		{
			filter.addNotEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, null);
		}
		// Case: only NOT delivered (i.e. M_InOutLine_ID is NOT set)
		else
		{
			filter.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, null);
		}

		return filter;
	}

	@Override
	public IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> retrieveOnShipmentLineRecordsQuery(
			final I_M_ShipmentSchedule shipmentSchedule)
	{
		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryBuilder = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, shipmentSchedule)
				.addOnlyActiveRecordsFilter()
				.filter(createOnShipmentLineFilter(shipmentSchedule));

		queryBuilder.orderBy()
				.addColumn(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_QtyPicked_ID);

		return queryBuilder;
	}

	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveNotOnShipmentLineRecords(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final Class<T> clazz)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(shipmentSchedule);
		return retrieveNotOnShipmentLineRecords(shipmentSchedule, clazz, trxName);
	}

	private <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveNotOnShipmentLineRecords(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final Class<T> clazz,
			final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(shipmentSchedule);

		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryBuilder = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, ctx, trxName)
				.filter(createNotOnShipmentLineFilter(shipmentSchedule))
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_QtyPicked_ID);

		return queryBuilder.create()
				.list(clazz);
	}

	@NonNull
	@Override
	public BigDecimal retrieveNotOnShipmentLineQty(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final BigDecimal qty = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, shipmentSchedule)
				.filter(createNotOnShipmentLineFilter(shipmentSchedule))
				.addOnlyActiveRecordsFilter()
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
	public <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveByInOutLineId(
			@NonNull final InOutLineId inoutLineId,
			@NonNull final Class<T> modelClass)
	{
		return queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_InOutLine_ID, inoutLineId)
				.create()
				.listImmutable(modelClass);
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
	public List<I_M_ShipmentSchedule> retrieveSchedulesForInOutLine(final I_M_InOutLine inoutLine)
	{
		return retrieveSchedulesForInOutLineQuery(inoutLine)
				.create()
				.list(I_M_ShipmentSchedule.class);
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

		logger.debug("Updated {} M_ShipmentSchedule_QtyPicked to Processed={} for intout={}", updated, newProcessedValue, inOut);
	}

	@Override
	public List<I_M_ShipmentSchedule_QtyPicked> retrieveOnShipmentLineRecords(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleDao.getById(shipmentScheduleId);

		return retrieveOnShipmentLineRecordsQuery(shipmentSchedule).create().list();
	}

	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> ImmutableListMultimap<ShipmentScheduleId, T> retrieveNotOnShipmentLineRecordsByScheduleIds(
			@NonNull final Set<ShipmentScheduleId> scheduleIds,
			@NonNull Class<T> type)
	{
		return retrieveRecordsByScheduleIds(scheduleIds, false, type);
	}

	@Override
	public ImmutableListMultimap<ShipmentScheduleId, I_M_ShipmentSchedule_QtyPicked> retrieveOnShipmentLineRecordsByScheduleIds(@NonNull final Set<ShipmentScheduleId> scheduleIds)
	{
		return retrieveRecordsByScheduleIds(scheduleIds, true, I_M_ShipmentSchedule_QtyPicked.class);
	}

	private <T extends I_M_ShipmentSchedule_QtyPicked> ImmutableListMultimap<ShipmentScheduleId, T> retrieveRecordsByScheduleIds(
			@NonNull final Set<ShipmentScheduleId> scheduleIds,
			final boolean onShipmentLine,
			@NonNull final Class<T> type)
	{
		if (scheduleIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addOnlyActiveRecordsFilter()
				.filter(createOnShipmentLineFilter(scheduleIds, onShipmentLine))
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID)
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID)
				.create()
				.stream(type)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()),
						record -> record
				));
	}

	/**
	 * Creates a filter which keeps {@link I_M_ShipmentSchedule_QtyPicked} all records (active or not), for the given shipment schedule ids, which were:
	 * <ul>
	 * <li>already referenced by a shipment line, if <code>onShipmentLine</code> is true
	 * <li>or NOT referenced by a shipment line, if <code>onShipmentLine</code> is false
	 * </ul>
	 */
	private IQueryFilter<I_M_ShipmentSchedule_QtyPicked> createOnShipmentLineFilter(
			@NonNull final Set<ShipmentScheduleId> scheduleIds,
			final boolean onShipmentLine)
	{
		final ICompositeQueryFilter<I_M_ShipmentSchedule_QtyPicked> filter = queryBL
				.createCompositeQueryFilter(I_M_ShipmentSchedule_QtyPicked.class)
				// For given shipment schedule ids
				.addInArrayFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID, scheduleIds);

		// Case: only delivered (i.e. M_InOutLine_ID set)
		if (onShipmentLine)
		{
			filter.addNotEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, null);
		}
		// Case: only NOT delivered (i.e. M_InOutLine_ID is NOT set)
		else
		{
			filter.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, null);
		}

		return filter;
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
