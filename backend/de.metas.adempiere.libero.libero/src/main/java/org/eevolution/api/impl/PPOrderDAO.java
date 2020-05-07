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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.Query;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Order;
import org.eevolution.model.X_PP_Order_BOM;

import de.metas.document.engine.IDocument;

public class PPOrderDAO implements IPPOrderDAO
{
	@Override
	public I_PP_Order retrieveMakeToOrderForOrderLine(final I_C_OrderLine line)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order.class, line)
				.addEqualsFilter(I_PP_Order.COLUMNNAME_C_OrderLine_MTO_ID, line.getC_OrderLine_ID())
				.addEqualsFilter(I_PP_Order.COLUMNNAME_M_Product_ID, line.getM_Product_ID())
				.create()
				.firstOnly(I_PP_Order.class);
	}

	@Override
	public List<I_PP_Order> retrieveAllForOrderLine(final I_C_OrderLine line)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order.class, line)
				.addEqualsFilter(I_PP_Order.COLUMNNAME_C_OrderLine_ID, line.getC_OrderLine_ID())
				.addEqualsFilter(I_PP_Order.COLUMNNAME_M_Product_ID, line.getM_Product_ID())
				.create()
				.list(I_PP_Order.class);
	}

	@Override
	public List<I_PP_Order> retrieveMakeToOrderForInOut(final I_M_InOut inout)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(inout);
		final String trxName = InterfaceWrapperHelper.getTrxName(inout);

		final String whereClause = "C_OrderLine_ID IS NOT NULL"
				+ " AND EXISTS (SELECT 1 FROM M_InOutLine iol"
				+ " WHERE iol.M_InOut_ID=? AND PP_Order.C_OrderLine_MTO_ID = iol.C_OrderLine_ID) AND "
				+ I_PP_Order.COLUMNNAME_DocStatus + " =? "
				+ " AND EXISTS (SELECT 1 FROM PP_Order_BOM "
				+ " WHERE PP_Order_BOM.PP_Order_ID=PP_Order.PP_Order_ID AND PP_Order_BOM.BOMType IN (?, ?))";

		final List<I_PP_Order> orders = new Query(ctx, I_PP_Order.Table_Name, whereClause, trxName)
				.setParameters(new Object[] { inout.getM_InOut_ID(),
						X_PP_Order.DOCSTATUS_InProgress,
						X_PP_Order_BOM.BOMTYPE_Make_To_Kit,
						X_PP_Order_BOM.BOMTYPE_Make_To_Order
				})
				.list(I_PP_Order.class);

		return orders;
	}

	@Override
	public List<I_PP_Order> retrieveReleasedManufacturingOrdersForWarehouse(final Properties ctx, final int warehouseId)
	{
		final IQueryBuilder<I_PP_Order> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order.class, ctx, ITrx.TRXNAME_None)
				// For Warehouse
				.addEqualsFilter(I_PP_Order.COLUMN_M_Warehouse_ID, warehouseId)
				// Only Releases Manufacturing orders
				.addEqualsFilter(I_PP_Order.COLUMN_Processed, true)
				.addEqualsFilter(I_PP_Order.COLUMN_DocStatus, IDocument.STATUS_Completed)
				// Only those which are active
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		queryBuilder.orderBy()
				.addColumn(I_PP_Order.COLUMN_DocumentNo);

		return queryBuilder
				.create()
				.list(I_PP_Order.class);
	}

	@Override
	public int retrievePPOrderIdByOrderLineId(int orderLineId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Order.class)
				.addEqualsFilter(I_PP_Order.COLUMN_C_OrderLine_ID, orderLineId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly();
	}
}
