package de.metas.handlingunits.order.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.order.api.IHUOrderLineDAO;
import de.metas.util.Check;
import de.metas.util.Services;

public class HUOrderLineDAO implements IHUOrderLineDAO
{

	@Override
	public List<I_C_OrderLine> retrieveLinkedOrderLines(final I_C_OrderLine orderLine)
	{
		Check.assumeNotNull(orderLine, "Order line is not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderLine);

		final IQueryBuilder<I_C_OrderLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_OrderLine.class, ctx, trxName)
				.filter(ActiveRecordQueryFilter.getInstance(I_C_OrderLine.class))
				.filterByClientId()
				.filter(new EqualsQueryFilter<I_C_OrderLine>(I_C_OrderLine.COLUMNNAME_C_PackingMaterial_OrderLine_ID, orderLine.getC_OrderLine_ID()));

		queryBuilder.orderBy().addColumn(org.compiere.model.I_C_OrderLine.COLUMNNAME_C_OrderLine_ID);

		return queryBuilder.create().list(I_C_OrderLine.class);
	}

}
