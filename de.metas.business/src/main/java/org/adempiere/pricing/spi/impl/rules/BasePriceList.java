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
import org.adempiere.util.time.SystemTime;
import org.compiere.util.DB;

public class BasePriceList extends AbstractPriceListBasedRule
{
	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final int m_M_Product_ID = pricingCtx.getM_Product_ID();
		final int m_M_PriceList_ID = pricingCtx.getM_PriceList_ID();
		Timestamp m_PriceDate = pricingCtx.getPriceDate();
		if (m_PriceDate == null)
			m_PriceDate = SystemTime.asTimestamp();
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
		int m_C_TaxCategory_ID = -1; // metas
		int ppUOMId = -1;

		//
		//

		String sql = "SELECT bomPriceStd(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceStd,"	// 1
				+ " bomPriceList(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceList,"		// 2
				+ " bomPriceLimit(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceLimit,"	// 3
				+ " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,"	// 4..7
				+ " pl.EnforcePriceLimit, pl.IsTaxIncluded "	// 8..9
				+ " , pp.C_TaxCategory_ID " // metas
				+ ", pp.C_UOM_ID " // 11
				+ " FROM M_Product p"
				+ " INNER JOIN M_ProductPrice pp ON (p.M_Product_ID=pp.M_Product_ID)"
				+ " INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID)"
				+ " INNER JOIN M_Pricelist bpl ON (pv.M_PriceList_ID=bpl.M_PriceList_ID)"
				+ " INNER JOIN M_Pricelist pl ON (bpl.M_PriceList_ID=pl.BasePriceList_ID) "
				+ "WHERE pv.IsActive='Y'"
				+ " AND pp.IsActive='Y'"
				+ " AND p.M_Product_ID=?"				// #1
				+ " AND pl.M_PriceList_ID=?"			// #2
				+ " ORDER BY pv.ValidFrom DESC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, m_M_PriceList_ID);
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
					ppUOMId = rs.getInt(11);
					m_C_Currency_ID = rs.getInt(6);
					m_M_Product_Category_ID = rs.getInt(7);
					m_enforcePriceLimit = "Y".equals(rs.getString(8));
					m_isTaxIncluded = "Y".equals(rs.getString(9));
					m_C_TaxCategory_ID = rs.getInt("C_TaxCategory_ID");
					//
					log.debug("M_PriceList_ID=" + m_M_PriceList_ID
							+ "(" + plDate + ")" + " - " + m_PriceStd);
					m_calculated = true;
					break;
				}
			}
		}
		catch (SQLException e)
		{
			m_calculated = false;
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//
		//

		if (!m_calculated)
		{
			log.trace("Not found (BPL)");
			return;
		}

		result.setPriceStd(m_PriceStd);
		result.setPriceList(m_PriceList);
		result.setPriceLimit(m_PriceLimit);
		result.setC_Currency_ID(m_C_Currency_ID);
		result.setM_Product_Category_ID(m_M_Product_Category_ID);
		result.setEnforcePriceLimit(m_enforcePriceLimit);
		result.setTaxIncluded(m_isTaxIncluded);
		// result.setM_PriceList_Version_ID(m_M_PriceList_Version_ID);
		result.setC_TaxCategory_ID(m_C_TaxCategory_ID);
		result.setCalculated(true);
		
		// 06942 : use product price uom all the time
		if (ppUOMId <= 0)
		{
			result.setPrice_UOM_ID(m_C_UOM_ID);
		}
		else
		{
			result.setPrice_UOM_ID(ppUOMId);
		}
	}

}
