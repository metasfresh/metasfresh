package de.metas.product.impl;

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


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Storage;
import org.compiere.model.MLocator;
import org.compiere.model.MStorage;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.product.IStoragePA;

public final class StoragePA implements IStoragePA
{

	private static final String SQL_SELECT_QTY_ORDERED = //
	"SELECT SUM(" + I_M_Storage.COLUMNNAME_QtyOrdered + ") " //
			+ "FROM " + I_M_Storage.Table_Name + " s " //
			+ "   LEFT JOIN " + I_M_Locator.Table_Name + " l " //
			+ "   ON s." + I_M_Storage.COLUMNNAME_M_Locator_ID //
			+ "      = l." + I_M_Locator.COLUMNNAME_M_Locator_ID //
			+ " WHERE s." + I_M_Storage.COLUMNNAME_M_Product_ID + "=? " //
			+ "AND l." + I_M_Locator.COLUMNNAME_M_Warehouse_ID + "=?";

	private static final String SQL_ALL_STORAGES = //
	"SELECT s.* "//
			+ " FROM M_Storage s " //
			+ "   LEFT JOIN M_Locator l ON l.M_Locator_ID=s.M_Locator_ID" //
			+ " WHERE s.IsActive='Y' AND s.M_Product_ID=? AND l.M_Warehouse_ID=?";

	@Override
	public Collection<I_M_Storage> retrieveStorages(final int productId,
			final String trxName)
	{

		final I_M_Storage[] storages = MStorage.getOfProduct(Env.getCtx(),
				productId, trxName);

		return Arrays.asList(storages);
	}

	@Override
	public int retrieveWarehouseId(final I_M_Storage storage,
			final String trxName)
	{

		final int locatorId = storage.getM_Locator_ID();
		final I_M_Locator locator = retrieveLocator(locatorId, trxName);

		return locator.getM_Warehouse_ID();
	}

	public I_M_Locator retrieveLocator(final int locatorId, final String trxName)
	{

		return new MLocator(Env.getCtx(), locatorId, trxName);
	}

	/**
	 * Invokes {@link MStorage#getQtyAvailable(int, int, int, int, String)}.
	 */
	@Override
	public BigDecimal retrieveQtyAvailable(final int wareHouseId,
			final int locatorId, final int productId,
			final int attributeSetInstanceId, final String trxName)
	{

		return MStorage.getQtyAvailable(wareHouseId, locatorId, productId,
				attributeSetInstanceId, trxName);
	}

	@Override
	public BigDecimal retrieveQtyOrdered(final int productId,
			final int warehouseId)
	{

		ResultSet rs = null;
		final PreparedStatement pstmt = DB.prepareStatement(
				SQL_SELECT_QTY_ORDERED, null);

		try
		{

			pstmt.setInt(1, productId);
			pstmt.setInt(2, warehouseId);
			rs = pstmt.executeQuery();

			if (rs.next())
			{
				final BigDecimal qtyOrdered = rs.getBigDecimal(1);
				if (qtyOrdered == null)
				{
					return BigDecimal.ZERO;
				}
				return qtyOrdered;
			}

			throw new RuntimeException(
					"Unable to retrive qtyOrdererd for M_Product_ID '"
							+ productId + "'");

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
