package org.compiere.apps.search.dao.impl;

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


import de.metas.handlingunits.model.I_RV_M_HU_Storage_InvoiceHistory;

/**
 * Contains HU-specific implementations for gathering information about handling unit storage in relation to invoicing history
 * <p>
 * <b>IMPORANT:</b> disabled due to performance issues as of task 08777; consider re-enabling it in task 08881.
 * 
 * @author al
 */
public class HUInvoiceHistoryDAO extends AbstractInvoiceHistoryDAO
{
	@Override
	public String buildStorageInvoiceHistorySQL(final boolean showDetail, final int warehouseId, final int asiId)
	{
		final StringBuilder sql = new StringBuilder("SELECT ")
				.append("s.").append(I_RV_M_HU_Storage_InvoiceHistory.COLUMNNAME_QtyOnHand).append(", ")
				.append("s.").append(I_RV_M_HU_Storage_InvoiceHistory.COLUMNNAME_QtyReserved).append(", ")
				.append("s.").append(I_RV_M_HU_Storage_InvoiceHistory.COLUMNNAME_QtyOrdered).append(", ")
				.append("s.").append(I_RV_M_HU_Storage_InvoiceHistory.COLUMNNAME_HUStorageASIKey).append(", ")
				.append("s.").append(I_RV_M_HU_Storage_InvoiceHistory.COLUMNNAME_HUStorageASIKey).append(", ")
				.append("w.").append(org.compiere.model.I_M_Warehouse.COLUMNNAME_Name).append(", ")
				.append("l.").append(org.compiere.model.I_M_Locator.COLUMNNAME_Value).append(" ");

		sql.append("FROM ").append(I_RV_M_HU_Storage_InvoiceHistory.Table_Name).append(" s")
				.append(" LEFT JOIN ").append(org.compiere.model.I_M_Locator.Table_Name).append(" l ON (s.M_Locator_ID=l.M_Locator_ID)")
				.append(" LEFT JOIN ").append(org.compiere.model.I_M_Warehouse.Table_Name).append(" w ON (l.M_Warehouse_ID=w.M_Warehouse_ID) ")
				.append("WHERE M_Product_ID=?");

		if (warehouseId != 0)
		{
			sql.append(" AND (1=1 OR l.M_Warehouse_ID=?)"); // Note the 1=1; We're mocking the warehouse filter to preserve legacy code and not screw with the prepared statement
		}

		if (asiId > 0)
		{
			sql.append(" OR GenerateHUStorageASIKey(?)=s.").append(I_RV_M_HU_Storage_InvoiceHistory.COLUMNNAME_HUStorageASIKey); // ASI dummy (keep original query by ASI)
		}

		sql.append(" AND (s.").append(I_RV_M_HU_Storage_InvoiceHistory.COLUMNNAME_QtyOnHand).append("<>0")
				.append(" OR s.").append(I_RV_M_HU_Storage_InvoiceHistory.COLUMNNAME_QtyReserved).append("<>0")
				.append(" OR s.").append(I_RV_M_HU_Storage_InvoiceHistory.COLUMNNAME_QtyOrdered).append("<>0)");

		return sql.toString();
	}
}
