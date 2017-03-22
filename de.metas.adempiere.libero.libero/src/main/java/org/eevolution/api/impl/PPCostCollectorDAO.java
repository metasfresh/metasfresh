package org.eevolution.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_CostDetail;
import org.compiere.process.DocAction;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Cost_Collector;

public class PPCostCollectorDAO implements IPPCostCollectorDAO
{
	@Override
	public List<I_PP_Cost_Collector> retrieveForOrder(final I_PP_Order order)
	{
		Check.assumeNotNull(order, "order not null");
		final IQueryBuilder<I_PP_Cost_Collector> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_PP_Cost_Collector.class, order)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMN_PP_Order_ID, order.getPP_Order_ID());

		queryBuilder.orderBy()
				.addColumn(I_PP_Cost_Collector.COLUMN_PP_Cost_Collector_ID);

		return queryBuilder.create().list();
	}

	@Override
	public List<I_PP_Cost_Collector> retrieveForOrderBOMLine(final I_PP_Order_BOMLine orderBOMLine)
	{
		final IQueryBuilder<I_PP_Cost_Collector> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_PP_Cost_Collector.class, orderBOMLine)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMN_PP_Order_BOMLine_ID, orderBOMLine.getPP_Order_BOMLine_ID());

		queryBuilder.orderBy()
				.addColumn(I_PP_Cost_Collector.COLUMN_PP_Cost_Collector_ID);

		return queryBuilder.create().list();
	}

	@Override
	public List<I_PP_Cost_Collector> retrieveForParent(final I_PP_Cost_Collector parentCostCollector)
	{
		Check.assumeNotNull(parentCostCollector, "parentCostCollector not null");

		final IQueryBuilder<I_PP_Cost_Collector> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_PP_Cost_Collector.class, parentCostCollector)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMN_PP_Cost_Collector_Parent_ID, parentCostCollector.getPP_Cost_Collector_ID());

		queryBuilder.orderBy()
				.addColumn(I_PP_Cost_Collector.COLUMN_PP_Cost_Collector_ID);

		return queryBuilder.create().list();

	}

	@Override
	public List<I_M_CostDetail> retrieveCostDetails(final I_PP_Cost_Collector cc)
	{
		final IQueryBuilder<I_M_CostDetail> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_CostDetail.class, cc)
				.addEqualsFilter(I_M_CostDetail.COLUMNNAME_PP_Cost_Collector_ID, cc.getPP_Cost_Collector_ID());

		queryBuilder.orderBy()
				.addColumn(I_M_CostDetail.COLUMNNAME_M_CostDetail_ID);

		return queryBuilder.create().list();
	}

	@Override
	public List<I_PP_Cost_Collector> retrieveExistingReceiptCostCollector(final I_PP_Order ppOrder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_PP_Cost_Collector.class, ppOrder)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_CostCollectorType, X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_DocStatus, X_PP_Cost_Collector.DOCSTATUS_Completed)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_PP_Cost_Collector.class);

	}

	@Override
	public List<I_PP_Cost_Collector> retrieveNotReversedForOrder(final I_PP_Order order)
	{
		Check.assumeNotNull(order, "order not null");
		final IQueryBuilder<I_PP_Cost_Collector> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_PP_Cost_Collector.class, order)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMN_PP_Order_ID, order.getPP_Order_ID())
				.addInArrayOrAllFilter(I_PP_Cost_Collector.COLUMN_DocStatus, DocAction.STATUS_Completed, DocAction.STATUS_Closed);

		queryBuilder.orderBy()
				.addColumn(I_PP_Cost_Collector.COLUMN_PP_Cost_Collector_ID);

		return queryBuilder.create().list();
	}
}
