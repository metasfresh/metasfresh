package org.compiere.apps.search.dao.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.apps.search.dao.IInvoiceHistoryDAO;
import org.compiere.util.Env;

import de.metas.interfaces.I_C_OrderLine;

/**
 * Contains common decoupled methods
 *
 * @author al
 */
public abstract class AbstractInvoiceHistoryDAO implements IInvoiceHistoryDAO
{
	@Override
	public List<OrderLineHistoryVO> retrieveOrderLineHistory(final int productId, final int attributeSetInstanceId, final int warehouseId)
	{
		Check.assume(productId > 0, "productId > 0");
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IContextAware contextProvider = new PlainContextAware(Env.getCtx());
		final IQueryBuilder<I_C_OrderLine> queryBuilder = queryBL.createQueryBuilder(I_C_OrderLine.class, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_QtyReserved, 0)
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID, productId);

		if (warehouseId > 0)
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID, attributeSetInstanceId);
		}
		if (attributeSetInstanceId > 0)
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Warehouse_ID, warehouseId);
		}

		final IQueryOrderBy orderBy = queryBL.createQueryOrderByBuilder(I_C_OrderLine.class)
				.addColumn(org.compiere.model.I_C_OrderLine.COLUMNNAME_DatePromised)
				.createQueryOrderBy();

		final List<I_C_OrderLine> orderLines = queryBuilder.create()
				.setOrderBy(orderBy)
				.list(I_C_OrderLine.class);

		//
		// Build VO result
		final List<OrderLineHistoryVO> result = new ArrayList<OrderLineHistoryVO>();
		for (final I_C_OrderLine orderLine : orderLines)
		{
			final OrderLineHistoryVO vo = new OrderLineHistoryVO(orderLine);
			result.add(vo);
		}
		return result;
	}
}
