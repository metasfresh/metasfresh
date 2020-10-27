package org.eevolution.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.LocalDateTime;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Order;

import de.metas.document.engine.DocStatus;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;

public class PPOrderDAO implements IPPOrderDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_PP_Order getById(@NonNull final PPOrderId ppOrderId)
	{
		return getById(ppOrderId, I_PP_Order.class);
	}

	@Override
	public <T extends I_PP_Order> T getById(@NonNull final PPOrderId ppOrderId, @NonNull final Class<T> type)
	{
		return InterfaceWrapperHelper.load(ppOrderId, type);
	}

	@Override
	public List<I_PP_Order> getByIds(final Set<PPOrderId> orderIds)
	{
		return loadByRepoIdAwares(orderIds, I_PP_Order.class);
	}

	@Override
	public List<I_PP_Order> retrieveReleasedManufacturingOrdersForWarehouse(final WarehouseId warehouseId)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_PP_Order.class)
				// For Warehouse
				.addEqualsFilter(I_PP_Order.COLUMNNAME_M_Warehouse_ID, warehouseId)
				// Only Releases Manufacturing orders
				.addEqualsFilter(I_PP_Order.COLUMN_Processed, true)
				.addEqualsFilter(I_PP_Order.COLUMN_DocStatus, DocStatus.Completed)
				// Only those which are active
				.addOnlyActiveRecordsFilter()
				//
				.orderBy(I_PP_Order.COLUMN_DocumentNo)
				//
				.create()
				.list(I_PP_Order.class);
	}

	@Override
	public PPOrderId retrievePPOrderIdByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return queryBL
				.createQueryBuilder(I_PP_Order.class)
				.addEqualsFilter(I_PP_Order.COLUMN_C_OrderLine_ID, orderLineId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly(PPOrderId::ofRepoIdOrNull);
	}

	@Override
	public Stream<I_PP_Order> streamOpenPPOrderIdsOrderedByDatePromised(@Nullable final ResourceId plantId)
	{
		final IQueryBuilder<I_PP_Order> queryBuilder = queryBL
				.createQueryBuilder(I_PP_Order.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_PP_Order.COLUMN_DocStatus, X_PP_Order.DOCSTATUS_InProgress, X_PP_Order.DOCSTATUS_Completed)
				.orderBy(I_PP_Order.COLUMNNAME_DatePromised);

		if (plantId != null)
		{
			queryBuilder.addEqualsFilter(I_PP_Order.COLUMN_S_Resource_ID, plantId);
		}

		return queryBuilder.create().iterateAndStream();
	}

	@Override
	public void changeOrderScheduling(
			@NonNull final PPOrderId orderId,
			@NonNull final LocalDateTime scheduledStartDate,
			@NonNull final LocalDateTime scheduledFinishDate)
	{
		final I_PP_Order order = getById(orderId);
		order.setDateStartSchedule(TimeUtil.asTimestamp(scheduledStartDate));
		order.setDateFinishSchedule(TimeUtil.asTimestamp(scheduledFinishDate));
		save(order);
	}

	@Override
	public void save(final I_PP_Order order)
	{
		saveRecord(order);
	}
}
