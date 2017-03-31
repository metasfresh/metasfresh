package de.metas.product.modelvalidator;

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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.I_M_ProductScalePrice;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;

public class MProductScalePriceValidator implements ModelValidator
{
	private static final Logger logger = LogManager.getLogger(MProductScalePriceValidator.class);

	private final static String SQL_UPDATE_PRODUCTPRICE = //
	" UPDATE "
			+ I_M_ProductPrice.Table_Name //
			+ " SET " //
			+ I_M_ProductPrice.COLUMNNAME_PriceLimit + "=?, "
			+ I_M_ProductPrice.COLUMNNAME_PriceList + "=?, "
			+ I_M_ProductPrice.COLUMNNAME_PriceStd + "=? "
			+ " WHERE M_ProductPrice_ID=?";

	private final static String SQL_SELECT_EXISTING = //
	" SELECT " + I_M_ProductScalePrice.COLUMNNAME_M_ProductScalePrice_ID //
			+ " FROM " + I_M_ProductScalePrice.Table_Name //
			+ " WHERE " //
			+ I_M_ProductScalePrice.COLUMNNAME_M_ProductPrice_ID + "=?" //
			+ "   AND " + I_M_ProductScalePrice.COLUMNNAME_Qty + "=?";

	private int ad_Client_ID = -1;

	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{

		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}
		engine.addModelChange(I_M_ProductScalePrice.Table_Name, this);
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		final I_M_ProductScalePrice productScalePrice = InterfaceWrapperHelper.create(po, I_M_ProductScalePrice.class);

		if (type == ModelValidator.TYPE_BEFORE_CHANGE
				|| type == ModelValidator.TYPE_BEFORE_NEW)
		{

			if (isAlreadyExisting(po.get_ID(), productScalePrice
					.getM_ProductPrice_ID(), productScalePrice.getQty(), po
					.get_TrxName()))
			{

				if (productScalePrice.getQty().compareTo(BigDecimal.ONE) == 0)
				{
					// in the AD we have a read-only logic that makes values of
					// 1 read-only.
					// So in order to give our users a change to correct the
					// value, we set it to something other than 1.
					productScalePrice.setQty(BigDecimal.ZERO);
				}

				throw new AdempiereException("@MProductScalePrice.DuplicateQty@");
			}
		}

		if (type == ModelValidator.TYPE_BEFORE_CHANGE)
		{
			return handleChange(productScalePrice, po.get_TrxName());
		}
		if (type == ModelValidator.TYPE_BEFORE_DELETE)
		{
			return handleDelete(productScalePrice);
		}
		return null;
	}

	private String handleChange(final I_M_ProductScalePrice productScalePrice, final String trxName)
	{
		if (BigDecimal.ONE.compareTo(productScalePrice.getQty()) != 0)
		{
			// nothing to do
			return null;
		}
		PreparedStatement pstmt = null;

		try
		{
			//
			// Update the M_ProductPrice record this po belongs to
			pstmt = DB.prepareStatement(SQL_UPDATE_PRODUCTPRICE, trxName);
			pstmt.setBigDecimal(1, productScalePrice.getPriceLimit());
			pstmt.setBigDecimal(2, productScalePrice.getPriceList());
			pstmt.setBigDecimal(3, productScalePrice.getPriceStd());
			pstmt.setInt(4, productScalePrice.getM_ProductPrice_ID());
			int no = pstmt.executeUpdate();

			if (no == 1)
			{
				// OK, we were able to update M_Product_Price
				return null;
			}
			
			throw new AdempiereException("Didn't find M_Product_Price for M_ProductSalePrice with id " + productScalePrice.getM_ProductScalePrice_ID());
		}
		catch (SQLException e)
		{
			final String msg = "Unable to update M_Product_Price for M_ProductSalePrice with id " + productScalePrice.getM_ProductScalePrice_ID();
			MetasfreshLastError.saveError(logger, msg, e);
			throw new DBException(msg, e);
		}
		finally
		{
			DB.close(pstmt);
		}
	}

	private String handleDelete(final I_M_ProductScalePrice productScalePrice)
	{
		if (BigDecimal.ONE.compareTo(productScalePrice.getQty()) == 0)
		{
			// Can't allow this scalePrice to be deleted.
			throw new AdempiereException("@MProductScalePrice.CantDelete@");
		}
		return null;
	}

	private boolean isAlreadyExisting(final int currentId, final int productPriceId, final BigDecimal qty, final String trxName)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(SQL_SELECT_EXISTING, trxName);
			pstmt.setInt(1, productPriceId);
			pstmt.setBigDecimal(2, qty);

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final int existingId = rs.getInt(1);
				if (existingId == currentId)
				{
					// there is already a scaleprice in the DB, but it is the
					// one we are currently validating.
					return false;
				}
				// there is a different scalePrice with the same priceList and
				// quantity.
				return true;
			}
			return false;
		}
		catch (SQLException e)
		{
			final String msg = "Unable to check if a M_ProductScalePrice for M_ProductPrice with id "
					+ productPriceId + " and quantity " + qty + " already exists";
			MetasfreshLastError.saveError(logger, msg, e);
			throw new DBException(msg, e);
		}
		finally
		{
			DB.close(pstmt);
		}
	}
}
