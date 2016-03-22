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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.model.I_M_PriceList_Version;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.model.I_M_ProductPrice;

/**
 * Calculate Price using Price List Version
 *
 * @author tsa
 *
 */
public class PriceListVersion extends AbstractPriceListBasedRule
{
	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!super.applies(pricingCtx, result))
		{
			return false;
		}

		if (pricingCtx.getM_PriceList_Version_ID() <= 0)
		{
			return false;
		}

		return true;
	}

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final int m_M_Product_ID = pricingCtx.getM_Product_ID();

		org.compiere.model.I_M_PriceList_Version plv = pricingCtx.getM_PriceList_Version();

		// task 09688: in order to make the material tracking module (and others!) testable decoupled, incl. price list versions and stuff,
		// we get rid of the hardcoded SQL. For the time beeing it's still here (commented), so we can see how it used to be.
		// !!IMPORTANT!! with this change of implementation, we loose the bomPriceList calculation.
		// Should bomPricing be needed in future, please consider adding a dedicated pricing rule
		final I_M_ProductPrice productPrice = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PriceList_Version.class, pricingCtx.getCtx(), pricingCtx.getTrxName())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_Version_ID, plv.getM_PriceList_Version_ID())
				.andCollectChildren(I_M_ProductPrice.COLUMN_M_PriceList_Version_ID, I_M_ProductPrice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, m_M_Product_ID)
				.create()
				.firstOnly(I_M_ProductPrice.class);

		// //
		// String sql = "SELECT bomPriceStd(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceStd," // 1
		// + " bomPriceList(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceList," // 2
		// + " bomPriceLimit(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceLimit," // 3
		// + " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID," // 4..7
		// + " pl.EnforcePriceLimit, pl.IsTaxIncluded " // 8..9
		// + ", pp.C_TaxCategory_ID " // metas
		// + ", pp.C_UOM_ID " // 11
		// + " FROM M_Product p"
		// + " INNER JOIN M_ProductPrice pp ON (p.M_Product_ID=pp.M_Product_ID)"
		// + " INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID)"
		// + " INNER JOIN M_Pricelist pl ON (pv.M_PriceList_ID=pl.M_PriceList_ID) "
		// + "WHERE pv.IsActive='Y'"
		// + " AND pp.IsActive='Y'"
		// + " AND p.M_Product_ID=?" // #1
		// + " AND pv.M_PriceList_Version_ID=?"; // #2
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		// try
		// {
		// pstmt = DB.prepareStatement(sql, null);
		// pstmt.setInt(1, m_M_Product_ID);
		// pstmt.setInt(2, m_M_PriceList_Version_ID);
		// rs = pstmt.executeQuery();
		// if (rs.next())
		// {
		// // Prices
		// m_PriceStd = rs.getBigDecimal(1);
		// if (rs.wasNull())
		// m_PriceStd = Env.ZERO;
		// m_PriceList = rs.getBigDecimal(2);
		// if (rs.wasNull())
		// m_PriceList = Env.ZERO;
		// m_PriceLimit = rs.getBigDecimal(3);
		// if (rs.wasNull())
		// m_PriceLimit = Env.ZERO;
		// //
		// m_C_UOM_ID = rs.getInt(4);
		// m_C_Currency_ID = rs.getInt(6);
		// m_M_Product_Category_ID = rs.getInt(7);
		// m_enforcePriceLimit = "Y".equals(rs.getString(8));
		// m_isTaxIncluded = "Y".equals(rs.getString(9));
		// m_C_TaxCategory_ID = rs.getInt("C_TaxCategory_ID"); // metas
		// ppUOMId = rs.getInt(11);
		// //
		// log.debug("M_PriceList_Version_ID=" + m_M_PriceList_Version_ID + " - " + m_PriceStd);
		// m_calculated = true;
		// }
		// }
		// catch (SQLException e)
		// {
		// m_calculated = false;
		// throw new DBException(e, sql);
		// }
		// finally
		// {
		// DB.close(rs, pstmt);
		// rs = null;
		// pstmt = null;
		// }

		//
		//
		if (productPrice == null)
		{
			log.trace("Not found (PLV)");
			return;
		}

		final org.compiere.model.I_M_PriceList priceList = plv.getM_PriceList();
		final I_M_Product product = productPrice.getM_Product();

		result.setPriceStd(productPrice.getPriceStd());
		result.setPriceList(productPrice.getPriceList());
		result.setPriceLimit(productPrice.getPriceLimit());
		result.setC_Currency_ID(priceList.getC_Currency_ID());
		result.setM_Product_Category_ID(product.getM_Product_Category_ID());
		result.setEnforcePriceLimit(priceList.isEnforcePriceLimit());
		result.setTaxIncluded(priceList.isTaxIncluded());
		result.setC_TaxCategory_ID(productPrice.getC_TaxCategory_ID());
		result.setM_PriceList_Version_ID(plv.getM_PriceList_Version_ID()); // make sure that the result doesn't lack this important info, even if it was already known from the context!
		result.setCalculated(true);

		// 06942 : use product price uom all the time
		if (productPrice.getC_UOM_ID() <= 0)
		{
			result.setPrice_UOM_ID(product.getC_UOM_ID());
		}
		else
		{
			result.setPrice_UOM_ID(productPrice.getC_UOM_ID());
		}
	}
}
