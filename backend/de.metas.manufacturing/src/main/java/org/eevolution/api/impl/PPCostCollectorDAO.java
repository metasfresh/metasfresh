package org.eevolution.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery.Aggregate;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Cost_Collector;

import de.metas.document.engine.IDocument;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.util.Services;
import lombok.NonNull;

public class PPCostCollectorDAO implements IPPCostCollectorDAO
{
	@Override
	public I_PP_Cost_Collector getById(@NonNull final PPCostCollectorId costCollectorId)
	{
		return getById(costCollectorId, I_PP_Cost_Collector.class);
	}

	@Override
	public <T extends I_PP_Cost_Collector> T getById(@NonNull final PPCostCollectorId costCollectorId, @NonNull final Class<T> modelClass)
	{
		return load(costCollectorId, modelClass);
	}

	@Override
	public List<I_PP_Cost_Collector> getByIds(@NonNull final Set<PPCostCollectorId> costCollectorIds)
	{
		return getByIds(costCollectorIds, I_PP_Cost_Collector.class);
	}

	@Override
	public <T extends I_PP_Cost_Collector> List<T> getByIds(@NonNull final Set<PPCostCollectorId> costCollectorIds, @NonNull final Class<T> modelClass)
	{
		return loadByRepoIdAwares(costCollectorIds, modelClass);
	}

	@Override
	public List<I_PP_Cost_Collector> getByOrderId(@NonNull final PPOrderId ppOrderId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMN_PP_Order_ID, ppOrderId)
				.orderBy(I_PP_Cost_Collector.COLUMN_PP_Cost_Collector_ID)
				.create()
				.list(I_PP_Cost_Collector.class);
	}

	@Override
	public List<I_PP_Cost_Collector> getByParent(@NonNull final I_PP_Cost_Collector parentCostCollector)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Cost_Collector.class, parentCostCollector)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMN_PP_Cost_Collector_Parent_ID, parentCostCollector.getPP_Cost_Collector_ID())
				.orderBy(I_PP_Cost_Collector.COLUMN_PP_Cost_Collector_ID)
				.create()
				.list();
	}

	@Override
	public List<I_PP_Cost_Collector> getReceiptsByOrderId(@NonNull final PPOrderId ppOrderId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, ppOrderId)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_CostCollectorType, CostCollectorType.MaterialReceipt.getCode())
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_DocStatus, X_PP_Cost_Collector.DOCSTATUS_Completed)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_PP_Cost_Collector.class);

	}

	@Override
	public List<I_PP_Cost_Collector> getCompletedOrClosedByOrderId(@NonNull final PPOrderId orderId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMN_PP_Order_ID, orderId)
				.addInArrayFilter(I_PP_Cost_Collector.COLUMN_DocStatus, IDocument.STATUS_Completed, IDocument.STATUS_Closed)
				.orderBy(I_PP_Cost_Collector.COLUMN_PP_Cost_Collector_ID)
				.create()
				.list();
	}

	@Override
	public BigDecimal getQtyUsageVariance(@NonNull final PPOrderBOMLineId orderBOMLineId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final BigDecimal qtyUsageVariance = queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_BOMLine_ID, orderBOMLineId)
				.addInArrayFilter(I_PP_Cost_Collector.COLUMNNAME_DocStatus, X_PP_Cost_Collector.DOCSTATUS_Completed, X_PP_Cost_Collector.DOCSTATUS_Closed)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_CostCollectorType, CostCollectorType.UsageVariance.getCode())
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_PP_Cost_Collector.COLUMNNAME_MovementQty, Aggregate.MAX, BigDecimal.class);

		return qtyUsageVariance != null ? qtyUsageVariance : BigDecimal.ZERO;
	}

	@Override
	public Duration getTotalSetupTimeReal(@NonNull final PPOrderRoutingActivity activity, @NonNull final CostCollectorType costCollectorType)
	{
		return computeDuration(activity, costCollectorType, I_PP_Cost_Collector.COLUMNNAME_SetupTimeReal);
	}

	public List<I_PP_Cost_Collector> retrieveNotReversedForOrder(@NonNull final I_PP_Order order)
	{
		final IQueryBuilder<I_PP_Cost_Collector> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_PP_Cost_Collector.class, order)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMN_PP_Order_ID, order.getPP_Order_ID())
				.addInArrayOrAllFilter(I_PP_Cost_Collector.COLUMN_DocStatus, IDocument.STATUS_Completed, IDocument.STATUS_Closed);
		queryBuilder.orderBy()
				.addColumn(I_PP_Cost_Collector.COLUMN_PP_Cost_Collector_ID);
		return queryBuilder.create().list();
	}

	@Override
	public Duration getDurationReal(@NonNull final PPOrderRoutingActivity activity, @NonNull final CostCollectorType costCollectorType)
	{
		return computeDuration(activity, costCollectorType, I_PP_Cost_Collector.COLUMNNAME_DurationReal);
	}

	private Duration computeDuration(final PPOrderRoutingActivity activity, final CostCollectorType costCollectorType, final String durationColumnName)
	{
		BigDecimal duration = Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, activity.getOrderId())
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_Node_ID, activity.getId())
				.addInArrayFilter(I_PP_Cost_Collector.COLUMNNAME_DocStatus, X_PP_Cost_Collector.DOCSTATUS_Completed, X_PP_Cost_Collector.DOCSTATUS_Closed)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_CostCollectorType, costCollectorType.getCode())
				.create()
				.aggregate(durationColumnName, Aggregate.SUM, BigDecimal.class);

		if (duration == null)
		{
			duration = BigDecimal.ZERO;
		}

		final int durationInt = duration.setScale(0, RoundingMode.UP).intValueExact();

		return Duration.of(durationInt, activity.getDurationUnit());
	}

	@Override
	public void save(@NonNull final I_PP_Cost_Collector cc)
	{
		saveRecord(cc);
	}
}
