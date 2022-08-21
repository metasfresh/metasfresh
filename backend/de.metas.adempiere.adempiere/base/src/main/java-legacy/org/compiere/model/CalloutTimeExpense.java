/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Properties;

/**
 * Time & Expense Report Callout
 * 
 * @author Jorg Janke
 * @version $Id: CalloutTimeExpense.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class CalloutTimeExpense extends CalloutEngine
{
	/**
	 * Expense Report Line
	 * - called from M_Product_ID, S_ResourceAssignment_ID
	 * - set ExpenseAmt
	 * 
	 * @param ctx context
	 * @param WindowNo current Window No
	 * @param mTab Grid Tab
	 * @param mField Grid Field
	 * @param value New Value
	 * @return null or error message
	 */
	public String product(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer M_Product_ID = (Integer)value;
		if (M_Product_ID == null || M_Product_ID.intValue() == 0)
		{
			return "";
		}
		BigDecimal priceActual = null;

		// get expense date - or default to today's date
		Timestamp DateExpense = Env.getContextAsDate(ctx, WindowNo, "DateExpense");
		if (DateExpense == null)
		{
			DateExpense = new Timestamp(System.currentTimeMillis());
		}

		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			boolean noPrice = true;

			// Search Pricelist for current version
			sql = "SELECT bomPriceStd(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceStd,"
					+ "bomPriceList(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceList,"
					+ "bomPriceLimit(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceLimit,"
					+ "p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID "
					+ "FROM M_Product p, M_ProductPrice pp, M_Pricelist pl, M_PriceList_Version pv "
					+ "WHERE p.M_Product_ID=pp.M_Product_ID"
					+ " AND pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID"
					+ " AND pv.M_PriceList_ID=pl.M_PriceList_ID"
					+ " AND pv.IsActive='Y'"
					+ " AND p.M_Product_ID=?"		// 1
					+ " AND pl.M_PriceList_ID=?"	// 2
					+ " ORDER BY pv.ValidFrom DESC";
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_Product_ID.intValue());
			pstmt.setInt(2, Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID"));
			rs = pstmt.executeQuery();
			while (rs.next() && noPrice)
			{
				java.sql.Date plDate = rs.getDate("ValidFrom");
				// we have the price list
				// if order date is after or equal PriceList validFrom
				if (plDate == null || !DateExpense.before(plDate))
				{
					noPrice = false;
					// Price
					priceActual = rs.getBigDecimal("PriceStd");
					if (priceActual == null)
					{
						priceActual = rs.getBigDecimal("PriceList");
					}
					if (priceActual == null)
					{
						priceActual = rs.getBigDecimal("PriceLimit");
					}
					// Currency
					Integer ii = new Integer(rs.getInt("C_Currency_ID"));
					if (!rs.wasNull())
					{
						mTab.setValue("C_Currency_ID", ii);
					}
				}
			}

			// no prices yet - look base pricelist
			if (noPrice)
			{
				// Find if via Base Pricelist
				sql = "SELECT bomPriceStd(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceStd,"
						+ "bomPriceList(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceList,"
						+ "bomPriceLimit(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceLimit,"
						+ "p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID "
						+ "FROM M_Product p, M_ProductPrice pp, M_Pricelist pl, M_Pricelist bpl, M_PriceList_Version pv "
						+ "WHERE p.M_Product_ID=pp.M_Product_ID"
						+ " AND pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID"
						+ " AND pv.M_PriceList_ID=bpl.M_PriceList_ID"
						+ " AND pv.IsActive='Y'"
						+ " AND bpl.M_PriceList_ID=pl.BasePriceList_ID"	// Base
						+ " AND p.M_Product_ID=?"		// 1
						+ " AND pl.M_PriceList_ID=?"	// 2
						+ " ORDER BY pv.ValidFrom DESC";

				// close previous statement
				DB.close(rs, pstmt);
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, M_Product_ID.intValue());
				pstmt.setInt(2, Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID"));
				rs = pstmt.executeQuery();
				while (rs.next() && noPrice)
				{
					java.sql.Date plDate = rs.getDate("ValidFrom");
					// we have the price list
					// if order date is after or equal PriceList validFrom
					if (plDate == null || !DateExpense.before(plDate))
					{
						noPrice = false;
						// Price
						priceActual = rs.getBigDecimal("PriceStd");
						if (priceActual == null)
						{
							priceActual = rs.getBigDecimal("PriceList");
						}
						if (priceActual == null)
						{
							priceActual = rs.getBigDecimal("PriceLimit");
						}
						// Currency
						Integer ii = new Integer(rs.getInt("C_Currency_ID"));
						if (!rs.wasNull())
						{
							mTab.setValue("C_Currency_ID", ii);
						}
					}
				}
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// finish
		if (priceActual == null)
		{
			priceActual = Env.ZERO;
		}
		mTab.setValue("ExpenseAmt", priceActual);
		return "";
	}	// Expense_Product

	/**
	 * Expense - Amount.
	 * - called from ExpenseAmt, C_Currency_ID
	 * - calculates ConvertedAmt
	 * 
	 * @param ctx context
	 * @param WindowNo current Window No
	 * @param mTab Grid Tab
	 * @param mField Grid Field
	 * @param value New Value
	 * @return null or error message
	 */
	public String amount(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())
		{
			return "";
		}

		// get values
		BigDecimal ExpenseAmt = (BigDecimal)mTab.getValue("ExpenseAmt");
		CurrencyId C_Currency_From_ID = CurrencyId.ofRepoId((Integer)mTab.getValue("C_Currency_ID"));
		CurrencyId C_Currency_To_ID = CurrencyId.ofRepoId(Env.getContextAsInt(ctx, "$C_Currency_ID"));
		Instant DateExpense = Env.getContextAsDate(ctx, WindowNo, "DateExpense").toInstant();
		//
		log.debug("Amt=" + ExpenseAmt + ", C_Currency_ID=" + C_Currency_From_ID);
		// Converted Amount = Unit price
		BigDecimal ConvertedAmt = ExpenseAmt;
		// convert if required
		if (ConvertedAmt.signum() != 0 && !CurrencyId.equals(C_Currency_From_ID, C_Currency_To_ID))
		{
			ClientId AD_Client_ID = ClientId.ofRepoId(Env.getContextAsInt(ctx, WindowNo, "AD_Client_ID"));
			OrgId AD_Org_ID = OrgId.ofRepoId(Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID"));
			ConvertedAmt = Services.get(ICurrencyBL.class).convert(
					ConvertedAmt,
					C_Currency_From_ID,
					C_Currency_To_ID,
					DateExpense,
					(CurrencyConversionTypeId)null,
					AD_Client_ID,
					AD_Org_ID);
		}
		mTab.setValue("ConvertedAmt", ConvertedAmt);
		log.debug("= ConvertedAmt=" + ConvertedAmt);

		return "";
	}	// Expense_Amount

}	// CalloutTimeExpense
