package de.metas.inoutcandidate.api.impl;

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
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOutLine;

import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;

public class ShipmentScheduleAllocDAO implements IShipmentScheduleAllocDAO
{
	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrievePickedNotDeliveredRecords(I_M_ShipmentSchedule shipmentSchedule, Class<T> clazz)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(shipmentSchedule);
		return retrievePickedNotDeliveredRecords(shipmentSchedule, clazz, trxName);
	}

	/**
	 * Creates a filter which keeps {@link I_M_ShipmentSchedule_QtyPicked} all records (active or not), for given shipment schedule, which were <b>not</b> already delivered.
	 *
	 * @param shipmentSchedule
	 */
	private final IQueryFilter<I_M_ShipmentSchedule_QtyPicked> createPickedNotDeliveredFilter(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final boolean delivered = false; // NOT delivered ONLY
		return createPickedAndDeliveredStatusFilter(shipmentSchedule, delivered);
	}

	/**
	 * Creates a filter which keeps {@link I_M_ShipmentSchedule_QtyPicked} all records (active or not), for given shipment schedule <b>AND</b> which were already delivered.
	 *
	 * @param shipmentSchedule
	 */
	private final IQueryFilter<I_M_ShipmentSchedule_QtyPicked> createPickedAndDeliveredFilter(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final boolean delivered = true; // ONLY delivered
		return createPickedAndDeliveredStatusFilter(shipmentSchedule, delivered);
	}

	/**
	 * Creates a filter which keeps {@link I_M_ShipmentSchedule_QtyPicked} all records (active or not), for given shipment schedule, which were:
	 * <ul>
	 * <li>already delivered, if <code>delivered</code> is true
	 * <li>or NOT delivered, if <code>delivered</code> is false
	 *
	 * @param shipmentSchedule
	 * @param delivered
	 * @return filter
	 */
	private final IQueryFilter<I_M_ShipmentSchedule_QtyPicked> createPickedAndDeliveredStatusFilter(
			final I_M_ShipmentSchedule shipmentSchedule,
			final boolean delivered)
	{
		final ICompositeQueryFilter<I_M_ShipmentSchedule_QtyPicked> filter = Services.get(IQueryBL.class)
				.createCompositeQueryFilter(I_M_ShipmentSchedule_QtyPicked.class)
				// For given shipment schedule
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID());

		// Case: only delivered (i.e. M_InOutLine_ID set)
		if (delivered)
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
	public IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> retrievePickedAndDeliveredRecordsQuery(final I_M_ShipmentSchedule shipmentSchedule)
	{

		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, shipmentSchedule)
				.filter(createPickedAndDeliveredFilter(shipmentSchedule))
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_QtyPicked_ID);

		return queryBuilder;
	}

	private <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrievePickedNotDeliveredRecords(
			final I_M_ShipmentSchedule shipmentSchedule, Class<T> clazz,
			final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(shipmentSchedule);

		final IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, ctx, trxName)
				.filter(createPickedNotDeliveredFilter(shipmentSchedule))
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_QtyPicked_ID);

		return queryBuilder.create()
				.list(clazz);
	}

	@Override
	public BigDecimal retrievePickedNotDeliveredQty(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final BigDecimal qty = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, shipmentSchedule)
				.filter(createPickedNotDeliveredFilter(shipmentSchedule))
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_QtyPicked, IQuery.AGGREGATE_SUM, BigDecimal.class);

		return qty != null ? qty : BigDecimal.ZERO;
	}

	@Override
	public BigDecimal retrieveQtyDelivered(final I_M_ShipmentSchedule shipmentSchedule)
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
				.aggregate(I_M_InOutLine.COLUMNNAME_MovementQty, IQuery.AGGREGATE_SUM, BigDecimal.class);

		return qty != null ? qty : BigDecimal.ZERO;
	}

	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveAllQtyPickedRecords(final I_M_ShipmentSchedule shipmentSchedule, final Class<T> modelClass)
	{
		Check.assumeNotNull(shipmentSchedule, "shipmentSchedule not null");
		Check.assumeNotNull(modelClass, "modelClass not null");

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
	public <T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveAllForInOutLine(final I_M_InOutLine inoutLine, final Class<T> modelClass)
	{
		Check.assumeNotNull(inoutLine, "inoutLine not null");
		Check.assumeNotNull(modelClass, "modelClass not null");

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
}
