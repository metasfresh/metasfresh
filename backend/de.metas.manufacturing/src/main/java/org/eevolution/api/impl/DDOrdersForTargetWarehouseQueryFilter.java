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


import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.compiere.model.I_M_Locator;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import de.metas.util.Services;

/**
 * Filters {@link I_DD_Order}s which have an {@link I_DD_OrderLine} having the specified target/destination warehouse.
 * 
 * @author tsa
 * 
 */
/* package */class DDOrdersForTargetWarehouseQueryFilter implements ISqlQueryFilter, IQueryFilter<I_DD_Order>
{
	private final static String SQL = "EXISTS ("
			+ "SELECT 1 FROM DD_OrderLine ol"
			+ " INNER JOIN  M_Locator l ON (l.M_Locator_ID=ol.M_LocatorTo_ID) "
			+ " WHERE "
			+ " ol.DD_Order_ID=DD_Order.DD_Order_ID"
			+ " AND ol.IsActive='Y'"
			+ " AND l.M_Warehouse_ID=?"
			+ ")";

	private final int targetWarehouseId;

	public DDOrdersForTargetWarehouseQueryFilter(final int targetWarehouseId)
	{
		super();
		this.targetWarehouseId = targetWarehouseId;
	}

	@Override
	public String getSql()
	{
		return SQL;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		return Collections.<Object> singletonList(targetWarehouseId);
	}

	@Override
	public boolean accept(final I_DD_Order order)
	{
		for (final I_DD_OrderLine line : Services.get(IDDOrderDAO.class).retrieveLines(order))
		{
			if (accept(line))
			{
				return true;
			}
		}

		return false;
	}

	private final boolean accept(final I_DD_OrderLine line)
	{
		if (!line.isActive())
		{
			return false;
		}

		final I_M_Locator locatorTo = line.getM_LocatorTo();
		if (locatorTo == null)
		{
			return false;
		}

		final int warehouseToId = locatorTo.getM_Warehouse_ID();
		if (warehouseToId != this.targetWarehouseId)
		{
			return false;
		}

		return true;
	}
}
