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


import org.compiere.model.I_M_Storage;

/**
 * Backwards-compatible implementation for invoice history retrieval from {@link I_M_Storage}
 *
 * @author al
 */
public class InvoiceHistoryDAO extends AbstractInvoiceHistoryDAO
{
	@Override
	public String buildStorageInvoiceHistorySQL(final boolean showDetail, final int warehouseId, final int asiId)
	{
		final StringBuilder sql;
		if (showDetail)
		{
			sql = new StringBuilder("SELECT s.QtyOnHand, s.QtyReserved, s.QtyOrdered, asi.description, s.M_AttributeSetInstance_ID, ");
		}
		else
		{
			sql = new StringBuilder("SELECT SUM(s.QtyOnHand), SUM(s.QtyReserved), SUM(s.QtyOrdered), asi.description, 0,");
		}

		sql.append(" w.Name, l.Value ")
				.append("FROM " + I_M_Storage.Table_Name + " s")
				.append(" INNER JOIN M_Locator l ON (s.M_Locator_ID=l.M_Locator_ID)")
				.append(" INNER JOIN M_Warehouse w ON (l.M_Warehouse_ID=w.M_Warehouse_ID) ")
				.append(" INNER JOIN M_AttributeSetInstance asi ON (asi.M_AttributeSetInstance_ID = s.M_AttributeSetInstance_ID) ")
				.append("WHERE M_Product_ID=?");

		if (warehouseId != 0)
		{
			sql.append(" AND l.M_Warehouse_ID=?");
		}

		if (asiId > 0)
		{
			sql.append(" AND s.M_AttributeSetInstance_ID=?");
		}

		sql.append(" AND (s.QtyOnHand<>0 OR s.QtyReserved<>0 OR s.QtyOrdered<>0)");

		if (!showDetail)
		{
			sql.append(" GROUP BY asi.description, w.Name, l.Value");
		}

		sql.append(" ORDER BY l.Value");

		return sql.toString();
	}
}
