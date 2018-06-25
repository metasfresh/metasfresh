package de.metas.inoutcandidate.api.impl;

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

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.logging.LogManager;
import lombok.NonNull;

public class ShipmentScheduleAllocDAO implements IShipmentScheduleAllocDAO
{

	private static final Logger logger = LogManager.getLogger(ShipmentScheduleAllocDAO.class);

	/**
	 * Creates a filter which keeps {@link I_M_ShipmentSchedule_QtyPicked} all records (active or not),
	 * for given shipment schedule, which are <b>not</b> referenced by a shipment line.
	 */
	private final IQueryFilter<I_M_ShipmentSchedule_QtyPicked> createNotOnShipmentLineFilter(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final boolean onShipmentLine = false; // NOT delivered ONLY
		return createShipmentLineFilter(shipmentSchedule, onShipmentLine);
	}

	/**
	 * Creates a filter which keeps {@link I_M_ShipmentSchedule_QtyPicked} all records (active or not), for given shipment schedule <b>AND</b> which were already delivered.
	 *
	 * @param shipmentSchedule
	 */
	private final IQueryFilter<I_M_ShipmentSchedule_QtyPicked> createOnShipmentLineFilter(
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
	private final IQueryFilter<I_M_ShipmentSchedule_QtyPicked> createShipmentLineFilter(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			final boolean onShipmentLine)
	{
		final ICompositeQueryFilter<I_M_ShipmentSchedule_QtyPicked> filter = Services.get(IQueryBL.class)
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
		final IQueryBL queryBL = Services.get(IQueryBL.class);

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

		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, ctx, trxName)
				.filter(createNotOnShipmentLineFilter(shipmentSchedule))
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_QtyPicked_ID);

		return queryBuilder.create()
				.list(clazz);
	}

	@Override
	public BigDecimal retrieveNotOnShipmentLineQty(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final BigDecimal qty = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, shipmentSchedule)
				.filter(createNotOnShipmentLineFilter(shipmentSchedule))
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_QtyPicked, Aggregate.SUM, BigDecimal.class);

		return qty != null ? qty : BigDecimal.ZERO;
	}

	@Override
	public BigDecimal retrieveQtyDelivered(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final BigDecimal qty = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, shipmentSchedule)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
				.andCollect(I_M_InOutLine.COLUMN_M_InOutLine_ID, I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_Processed, true)
				.create()
				.aggregate(I_M_InOutLine.COLUMNNAME_MovementQty, Aggregate.SUM, BigDecimal.class);

		return Util.coalesce(qty, ZERO);
	}

	@Override
	public BigDecimal retrieveQtyPickedAndUnconfirmed(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final BigDecimal qty = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, shipmentSchedule)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_Processed, false)
				.create()
				.aggregate(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_QtyPicked, Aggregate.SUM, BigDecimal.class);

		return Util.coalesce(qty, ZERO);
	}

	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveAllQtyPickedRecords(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final Class<T> modelClass)
	{
		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryBuilder = Services.get(IQueryBL.class)
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
		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryBuilder = Services.get(IQueryBL.class)
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
		final IQueryBL queryBL = Services.get(IQueryBL.class);

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
		final IQueryBL queryBL = Services.get(IQueryBL.class);

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

		final IQueryBL queryBL = Services.get(IQueryBL.class);

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
}
