package org.adempiere.model;

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
import java.util.Properties;

import org.adempiere.exceptions.DBException;
import org.adempiere.product.service.IProductPA;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.MProductPrice;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_M_ProductPrice;

public final class MProductScalePrice extends X_M_ProductScalePrice {

	private static final long serialVersionUID = -5355400398492838269L;

	private final static String SQL_SELECT_PLV = //
	" SELECT "
			+ I_M_PriceList_Version.COLUMNNAME_M_PriceList_Version_ID //
			+ " FROM "
			+ I_M_PriceList_Version.Table_Name //
			+ " WHERE "
			+ I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID
			+ "=?" //
			+ "   AND "
			+ I_M_PriceList_Version.COLUMNNAME_ValidFrom
			+ "<=now()" //
			+ " ORDER BY " + I_M_PriceList_Version.COLUMNNAME_ValidFrom
			+ " DESC ";

	public MProductScalePrice(Properties ctx, int M_ProductScalePrice_ID,
			String trxName) {

		super(ctx, M_ProductScalePrice_ID, trxName);
	}

	public MProductScalePrice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public static I_M_ProductScalePrice getForPriceList(final int priceListId,
			final int productId, final BigDecimal qty, final String trxName)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			pstmt = DB.prepareStatement(SQL_SELECT_PLV, trxName);
			pstmt.setInt(1, priceListId);
			pstmt.setMaxRows(1);

			rs = pstmt.executeQuery();
			if (!rs.next())
			{
				return null;
			}

			final int priceListVersionId = rs.getInt(1);
			final MProductPrice productPricePO = MProductPrice.get(Env.getCtx(), priceListVersionId, productId, null);
			final I_M_ProductPrice productPrice = InterfaceWrapperHelper.create(productPricePO, I_M_ProductPrice.class);
			
			if (productPrice != null && productPrice.isUseScalePrice())
			{
				return Services
						.get(IProductPA.class)
						.retrieveOrCreateScalePrices(productPricePO.get_ID(), qty, false, trxName);
			}
			return null;
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
