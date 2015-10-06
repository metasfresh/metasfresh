package de.metas.purchasing.service.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.purchasing.model.I_M_PurchaseSchedule;
import de.metas.purchasing.model.X_M_PurchaseSchedule;
import de.metas.purchasing.service.IPurchaseSchedulePA;

public class PurchaseSchedulePA implements IPurchaseSchedulePA
{

	public static final String SELECT_TO_INCLUDE = "SELECT * FROM "
			+ I_M_PurchaseSchedule.Table_Name + " WHERE "
			+ I_M_PurchaseSchedule.COLUMNNAME_IncludeInPurchase + " ='Y'";

	public static final String SQL_PRODUCT_WHAREOUSE = "SELECT * FROM "
			+ I_M_PurchaseSchedule.Table_Name + " WHERE "
			+ I_M_PurchaseSchedule.COLUMNNAME_M_Product_ID + " =? AND "
			+ I_M_PurchaseSchedule.COLUMNNAME_M_Warehouse_ID + "=? AND ("
			+ I_M_PurchaseSchedule.COLUMNNAME_PricePO + " IN (?,0) OR ? IS NULL)"
			+ " ORDER BY Created";

	public List<I_M_PurchaseSchedule> retrieveToIncludeInPO(String trxName)
	{

		final PreparedStatement pstmt = DB.prepareStatement(SELECT_TO_INCLUDE,				trxName);

		ResultSet rs = null;

		try
		{
			final ArrayList<I_M_PurchaseSchedule> result = new ArrayList<I_M_PurchaseSchedule>();
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				result.add(new X_M_PurchaseSchedule(Env.getCtx(), rs, trxName));
			}
			return result;

		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
