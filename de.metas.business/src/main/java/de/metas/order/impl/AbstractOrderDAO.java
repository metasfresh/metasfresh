package de.metas.order.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import lombok.NonNull;

public abstract class AbstractOrderDAO implements IOrderDAO
{
	@Override
	public List<I_C_OrderLine> retrieveOrderLines(final org.compiere.model.I_C_Order order)
	{
		return retrieveOrderLines(order, I_C_OrderLine.class);
	}

	@Override
	public <T extends org.compiere.model.I_C_OrderLine> List<T> retrieveOrderLines(
			@NonNull final I_C_Order order,
			@NonNull final Class<T> clazz)
	{
		Properties ctx = InterfaceWrapperHelper.getCtx(order);
		String trxName = InterfaceWrapperHelper.getTrxName(order);
		final int orderId = order.getC_Order_ID();
		final List<T> orderLines = retrieveOrderLines(ctx, orderId, trxName, clazz);

		orderLines.forEach(orderLine -> orderLine.setC_Order(order));
		return orderLines;
	}
	
	@Override
	public List<I_C_OrderLine> retrieveOrderLines(final int orderId)
	{
		return retrieveOrderLines(Env.getCtx(), orderId, ITrx.TRXNAME_ThreadInherited, I_C_OrderLine.class);
	}


	// tsa: commented out because it's not safe to cache the list of order lines and return it without even cloning.
	// @Cached(cacheName = I_C_OrderLine.Table_Name + "#via#" + I_C_OrderLine.COLUMNNAME_C_Order_ID) 
	public <T extends org.compiere.model.I_C_OrderLine> List<T> retrieveOrderLines(
			@CacheCtx final Properties ctx,
			final int orderId,
			@CacheTrx final String trxName,
			@NonNull final Class<T> clazz)
	{
		final List<T> orderLines = Services.get(IQueryBL.class)
				.createQueryBuilder(org.compiere.model.I_C_OrderLine.class, ctx, trxName)
				.addEqualsFilter(I_C_OrderLine.COLUMN_C_Order_ID, orderId)
				.orderBy()
				.addColumn(org.compiere.model.I_C_OrderLine.COLUMN_Line)
				.addColumn(org.compiere.model.I_C_OrderLine.COLUMN_C_OrderLine_ID)
				.endOrderBy()
				.create()
				.list(clazz);

		return orderLines;
	}

	@Override
	public final List<Integer> retrieveAllOrderLineIds(final I_C_Order order)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(org.compiere.model.I_C_OrderLine.class, order)
				.addEqualsFilter(I_C_OrderLine.COLUMN_C_Order_ID, order.getC_Order_ID())
				.create()
				.listIds();
	}

	@Override
	public <T extends org.compiere.model.I_C_OrderLine> T retrieveOrderLine(I_C_Order order, int lineNo, Class<T> clazz)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(org.compiere.model.I_C_OrderLine.class, order)
				.addEqualsFilter(I_C_OrderLine.COLUMN_C_Order_ID, order.getC_Order_ID())
				.addEqualsFilter(I_C_OrderLine.COLUMN_Line, lineNo)
				.create()
				.firstOnly(clazz);
	}

	@Override
	public boolean hasCompletedOrders(final Properties ctx, final int bpartnerId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Order.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Order.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addInArrayOrAllFilter(I_C_Order.COLUMNNAME_DocStatus, X_C_Order.DOCSTATUS_Completed, X_C_Order.DOCSTATUS_Closed)
				.create()
				.match();
	}

	@Override
	public List<I_M_InOut> retrieveInOuts(final I_C_Order order)
	{
		return retrieveInOutsQuery(order)
				.create()
				.list(I_M_InOut.class);
	}

	@Override
	public boolean hasInOuts(final I_C_Order order)
	{
		return retrieveInOutsQuery(order)
				.create()
				.match();
	}

	private IQueryBuilder<I_M_InOut> retrieveInOutsQuery(final I_C_Order order)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_InOut.class, order)
				.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.filterByClientId()
				.addOnlyActiveRecordsFilter()
				.orderByDescending(org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID);
	}
}
