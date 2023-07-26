package org.eevolution.api.impl;

import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.LESS_OR_EQUAL;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.util.TimeUtil.asTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;

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
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Order;

import com.google.common.collect.HashMultimap;

import de.metas.document.engine.DocStatus;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import org.eevolution.api.PPOrderId;
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
	public <T extends I_PP_Order> List<T> getByIds(@NonNull final Set<PPOrderId> orderIds, @NonNull final Class<T> type)
	{
		return loadByRepoIdAwares(orderIds, type);
	}

	@Override
	public List<I_PP_Order> retrieveReleasedManufacturingOrdersForWarehouse(final WarehouseId warehouseId)
	{
		final IQueryBuilder<I_PP_Order> queryBuilder = toSqlQueryBuilder(ManufacturingOrderQuery.builder()
				.warehouseId(warehouseId)
				.onlyCompleted(true)
				.build());

		return queryBuilder
				.orderBy(I_PP_Order.COLUMN_DocumentNo)
				.create()
				.list(I_PP_Order.class);
	}

	@Override
	public List<I_PP_Order> retrieveManufacturingOrders(@NonNull final ManufacturingOrderQuery query)
	{
		final IQueryBuilder<I_PP_Order> queryBuilder = toSqlQueryBuilder(query);

		return queryBuilder.orderBy(I_PP_Order.COLUMN_DocumentNo)
				.create()
				.list(I_PP_Order.class);
	}

	private IQueryBuilder<I_PP_Order> toSqlQueryBuilder(final ManufacturingOrderQuery query)
	{
		final IQueryBuilder<I_PP_Order> queryBuilder = queryBL.createQueryBuilder(I_PP_Order.class)
				.addOnlyActiveRecordsFilter();

		// Plant
		if (query.getPlantId() != null)
		{
			queryBuilder.addEqualsFilter(I_PP_Order.COLUMN_S_Resource_ID, query.getPlantId());
		}

		// Warehouse
		if (query.getWarehouseId() != null)
		{
			queryBuilder.addEqualsFilter(I_PP_Order.COLUMNNAME_M_Warehouse_ID, query.getWarehouseId());
		}

		// Only Releases Manufacturing orders
		if (query.isOnlyCompleted())
		{
			queryBuilder.addEqualsFilter(I_PP_Order.COLUMN_Processed, true);
			queryBuilder.addEqualsFilter(I_PP_Order.COLUMN_DocStatus, DocStatus.Completed);
		}

		// Export Status
		if (query.getExportStatus() != null)
		{
			queryBuilder.addEqualsFilter(I_PP_Order.COLUMNNAME_ExportStatus, query.getExportStatus().getCode());
		}
		if (query.getCanBeExportedFrom() != null)
		{
			queryBuilder.addCompareFilter(I_PP_Order.COLUMN_CanBeExportedFrom, LESS_OR_EQUAL, asTimestamp(query.getCanBeExportedFrom()));
		}

		// Limit
		if (query.getLimit().isLimited())
		{
			queryBuilder.setLimit(query.getLimit());
		}

		//
		return queryBuilder;
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
	public void save(@NonNull final I_PP_Order order)
	{
		saveRecord(order);
	}

	@Override
	public void saveAll(@NonNull final Collection<I_PP_Order> orders)
	{
		orders.forEach(this::save);
	}

	@Override
	public void exportStatusMassUpdate(
			@NonNull final Map<PPOrderId, APIExportStatus> exportStatuses)
	{
		if (exportStatuses.isEmpty())
		{
			return;
		}

		final HashMultimap<APIExportStatus, PPOrderId> orderIdsByExportStatus = HashMultimap.create();
		for (Map.Entry<PPOrderId, APIExportStatus> entry : exportStatuses.entrySet())
		{
			orderIdsByExportStatus.put(entry.getValue(), entry.getKey());
		}

		for (final APIExportStatus exportStatus : orderIdsByExportStatus.keySet())
		{
			final Set<PPOrderId> orderIds = orderIdsByExportStatus.get(exportStatus);

			queryBL.createQueryBuilder(I_PP_Order.class)
					.addInArrayFilter(I_PP_Order.COLUMNNAME_PP_Order_ID, orderIds)
					.create()
					//
					.updateDirectly()
					.addSetColumnValue(I_PP_Order.COLUMNNAME_ExportStatus, exportStatus.getCode())
					.execute();
		}
	}

	@Override
	public IQueryBuilder<I_PP_Order> createQueryForPPOrderSelection(final IQueryFilter<I_PP_Order> userSelectionFilter)
	{
		final IQueryBuilder<I_PP_Order> queryBuilder = queryBL
				.createQueryBuilder(I_PP_Order.class)
				.filter(userSelectionFilter)
				.addNotEqualsFilter(I_PP_Order.COLUMNNAME_DocStatus, X_PP_Order.DOCSTATUS_Closed)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		return queryBuilder;
	}
}
