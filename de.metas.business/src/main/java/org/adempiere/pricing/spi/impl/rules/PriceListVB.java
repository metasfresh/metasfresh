package org.adempiere.pricing.spi.impl.rules;

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
import java.sql.Timestamp;

import org.adempiere.exceptions.DBException;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.compiere.util.DB;

public class PriceListVB extends AbstractPriceListBasedRule
{
	@Override
	public void calculate(IPricingContext pricingCtx, IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}
		calculatePL_VB(pricingCtx, result);
	}

	private void calculatePL_VB(IPricingContext pricingCtx, IPricingResult result)
	{
		final int m_M_Product_ID = pricingCtx.getM_Product_ID();
		final int m_M_PriceList_ID = pricingCtx.getM_PriceList_ID();
		final int m_C_BPartner_ID = pricingCtx.getC_BPartner_ID();
		final BigDecimal m_Qty = pricingCtx.getQty();
		Timestamp m_PriceDate = pricingCtx.getPriceDate();
		if (m_PriceDate == null)
			m_PriceDate = new Timestamp(System.currentTimeMillis());
		//
		boolean m_calculated = false;
		BigDecimal m_PriceStd = null;
		BigDecimal m_PriceList = null;
		BigDecimal m_PriceLimit = null;
		int m_C_UOM_ID = -1;
		int m_C_Currency_ID = -1;
		int m_M_Product_Category_ID = -1;
		boolean m_enforcePriceLimit = false;
		boolean m_isTaxIncluded = false;
		int ppUOMId = -1;

		//
		//

		// Get Prices for Price List
		String sql = "SELECT pp.PriceStd,"	// 1
				+ " pp.PriceList,"		// 2
				+ " pp.PriceLimit,"	// 3
				+ " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,pl.EnforcePriceLimit, "	// 4..8
				+ " pp.C_UOM_ID " // 9
				+ "FROM M_Product p"
				+ " INNER JOIN M_ProductPriceVendorBreak pp ON (p.M_Product_ID=pp.M_Product_ID)"
				+ " INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID)"
				+ " INNER JOIN M_Pricelist pl ON (pv.M_PriceList_ID=pl.M_PriceList_ID) "
				+ "WHERE pv.IsActive='Y'"
				+ " AND pp.IsActive='Y'"
				+ " AND p.M_Product_ID=?"				// #1
				+ " AND pv.M_PriceList_ID=?"			// #2
				+ " AND pp.C_BPartner_ID=?"				// #3
				+ " AND ?>=pp.BreakValue"				// #4
				+ " ORDER BY pv.ValidFrom DESC, BreakValue DESC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, m_M_PriceList_ID);
			pstmt.setInt(3, m_C_BPartner_ID);
			pstmt.setBigDecimal(4, m_Qty);
			rs = pstmt.executeQuery();
			while (!m_calculated && rs.next())
			{
				Timestamp plDate = rs.getTimestamp(5);
				// we have the price list
				// if order date is after or equal PriceList validFrom
				if (plDate == null || !m_PriceDate.before(plDate))
				{
					// Prices
					m_PriceStd = rs.getBigDecimal(1);
					if (rs.wasNull())
						m_PriceStd = BigDecimal.ZERO;
					m_PriceList = rs.getBigDecimal(2);
					if (rs.wasNull())
						m_PriceList = BigDecimal.ZERO;
					m_PriceLimit = rs.getBigDecimal(3);
					if (rs.wasNull())
						m_PriceLimit = BigDecimal.ZERO;
					//
					m_C_UOM_ID = rs.getInt(4);
					m_C_Currency_ID = rs.getInt(6);
					m_M_Product_Category_ID = rs.getInt(7);
					m_enforcePriceLimit = "Y".equals(rs.getString(8));
					ppUOMId = rs.getInt(9); 
					//
					log.debug("M_PriceList_ID=" + m_M_PriceList_ID + "(" + plDate + ")" + " - " + m_PriceStd);
					m_calculated = true;
					break;
				}
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (!m_calculated)
		{
			log.trace("Not found (PL_VB)");
			return;
		}

		//
		//
		result.setPriceStd(m_PriceStd);
		result.setPriceList(m_PriceList);
		result.setPriceLimit(m_PriceLimit);
		result.setC_Currency_ID(m_C_Currency_ID);
		result.setM_Product_Category_ID(m_M_Product_Category_ID);
		result.setEnforcePriceLimit(m_enforcePriceLimit);
		result.setTaxIncluded(m_isTaxIncluded);
		result.setCalculated(true);
		result.setDisallowDiscount(true);
		// 06942 : use product price uom all the time
		if (ppUOMId <= 0)
		{
			result.setPrice_UOM_ID(m_C_UOM_ID);
		}
		else
		{
			result.setPrice_UOM_ID(ppUOMId);
		}
	}	// calculatePL_VB

}
