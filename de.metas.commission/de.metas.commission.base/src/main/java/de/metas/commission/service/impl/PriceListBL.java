package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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
import java.sql.SQLException;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.commission.interfaces.I_M_DiscountSchemaLine;
import de.metas.commission.service.IPriceListBL;
import de.metas.logging.LogManager;

public class PriceListBL implements IPriceListBL
{
	private static final Logger logger = LogManager.getLogger(PriceListBL.class);

	@Override
	public String updateCommissionPoints(
			final I_M_PriceList_Version plv,
			final I_M_DiscountSchemaLine dsl,
			final int adPinstanceId,
			final String trxName)
	{
		final StringBuilder resultMsg = new StringBuilder();

		final int plCountryId = InterfaceWrapperHelper.create(plv.getM_PriceList(), I_M_PriceList.class).getC_Country_ID();
		final boolean subtractVAT;
		if (plCountryId <= 0)
		{
			subtractVAT = false;
		}
		else
		{
			subtractVAT = dsl.isCommissionPoints_SubtractVAT();
		}
		// TODO for debugging only!!
		// org.compiere.util.Trx.get(trxName, false).commit();

		// update commission points from same pp
		final String sql =
				" UPDATE M_ProductPrice pp \n"
						+ " SET CommissionPoints= \n"
						+ "    (bpp.base + ?) \n" /* 1 dsl.getCommissionPoints_AddAmt() */
						+ " 		* (1 - ?/100) \n" /* 2 dsl.getCommissionPoints_Discount() */
						+ " 		* (1 - bpp.subtVat/100) \n"
						+ " FROM ( \n"
						+ "   select  \n"
						+ "     pp2.M_ProductPrice_ID, \n"
						+ " 	CASE "
						+ "         WHEN '" + dsl.getCommissionPoints_Base() + "'='LST' THEN pp2.PriceList \n"
						+ "         WHEN '" + dsl.getCommissionPoints_Base() + "'='STD' THEN pp2.PriceStd \n"
						+ "         WHEN '" + dsl.getCommissionPoints_Base() + "'='LMT' THEN pp2.PriceLimit \n"
						+ " 	END as base, \n"
						+ " 	CASE \n"
						+ " 		WHEN '" + (subtractVAT ? "Y" : "N") + "'='Y' THEN COALESCE(t.Rate,0) \n"
						+ " 		ELSE 0 \n"
						+ " 	END as subtVat \n"
						+ "   from M_ProductPrice pp2 \n"
						+ " 	left join C_Tax t \n"
						+ "       ON t.C_TaxCategory_ID=pp2.C_TaxCategory_ID AND t.C_Country_ID=? AND t.To_Country_ID=? AND t.IsTaxExempt='N' \n" /* 3,4 plCountryId */
						+ "   where true \n"
						+ "       AND (t.ValidFrom is NULL OR t.ValidFrom<=?) \n" /* 5 dsl.getConversionDate() */
						+ "       AND NOT EXISTS ( \n" /* The tax rate that we join must be the most recent rate that is valid at the dsl's conversion date */
						+ "             select * from C_Tax t2 \n"
						+ "             where t2.C_TaxCategory_ID=t.C_TaxCategory_ID AND t2.C_Country_ID=t.C_Country_ID AND t2.To_Country_ID=t.To_Country_ID AND t2.IsTaxExempt='N'\n"
						+ "                AND t2.ValidFrom<=? \n" /* 6 dsl.getConversionDate() */
						+ "                AND t2.ValidFrom>t.ValidFrom"
						+ "       )"
						+ " ) bpp \n"
						+ " WHERE \n"
						+ " 	bpp.M_ProductPrice_ID=pp.M_ProductPrice_ID \n"
						+ "     AND M_PriceList_Version_ID=? \n" /* 7 plv.getM_Pricelist_Version_ID() */
						+ " 	AND pp.IsActive='Y' \n"
						+ " 	AND EXISTS ( \n"
						+ "        SELECT * FROM T_Selection s \n"
						+ "        WHERE pp.M_Product_ID=s.T_Selection_ID AND s.AD_PInstance_ID=? \n" /* 8 adPinstanceId */
						+ " 	) \n";

		final PreparedStatement pstmtComPoints = DB.prepareStatement(sql, trxName);
		try
		{
			pstmtComPoints.setBigDecimal(1, dsl.getCommissionPoints_AddAmt());
			pstmtComPoints.setBigDecimal(2, dsl.getCommissionPoints_Discount());
			pstmtComPoints.setInt(3, plCountryId);
			pstmtComPoints.setInt(4, plCountryId);
			pstmtComPoints.setTimestamp(5, dsl.getConversionDate());
			pstmtComPoints.setTimestamp(6, dsl.getConversionDate());
			pstmtComPoints.setInt(7, plv.getM_PriceList_Version_ID());
			pstmtComPoints.setInt(8, adPinstanceId);

			final int result = pstmtComPoints.executeUpdate();
			PriceListBL.logger.info("Updated " + result + " commission points (product price)");
		}
		catch (final SQLException e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(pstmtComPoints);
		}

		final I_C_Currency targetCurrency = plv.getM_PriceList().getC_Currency();

		//
		// Rounding (AD_Reference_ID=155)
		//
		final String sqlUpdRound =
				"UPDATE	M_ProductPrice pp "
						+ " SET	"
						+ "  CommissionPoints = DECODE('" + dsl.getCommissionPoints_Rounding() + "',"
						+ "    'N', CommissionPoints, "
						+ "    '0', ROUND(CommissionPoints, 0)," // Even .00
						+ "    'D', ROUND(CommissionPoints, 1)," // Dime .10
						+ "    'T', ROUND(CommissionPoints, -1), " // Ten 10.00
						+ "    '5', ROUND(CommissionPoints*20,0)/20," // Nickle .05
						+ "    'Q', ROUND(CommissionPoints*4,0)/4," // Quarter .25
						+ "    '9', CASE" // Whole 9 or 5
						+ "      WHEN MOD(ROUND(CommissionPoints),10)<=5 THEN ROUND(CommissionPoints)+(5-MOD(ROUND(CommissionPoints),10))"
						+ "      WHEN MOD(ROUND(CommissionPoints),10)>5 THEN ROUND(CommissionPoints)+(9-MOD(ROUND(CommissionPoints),10)) "
						+ "    END,"
						+ "    ROUND(CommissionPoints, " + targetCurrency.getStdPrecision() + ")"
						+ "  )"
						+ " WHERE "
						+ "   pp.M_PriceList_Version_ID = " + plv.getM_PriceList_Version_ID()
						+ "   AND EXISTS ( "
						+ "        SELECT * FROM T_Selection s "
						+ "        WHERE pp.M_Product_ID=s.T_Selection_ID AND s.AD_PInstance_ID=" + adPinstanceId
						+ "   ) "
						+ "   AND pp.Isactive='Y'";

		final int roundResult = DB.executeUpdateEx(sqlUpdRound, trxName);
		PriceListBL.logger.info("Rounded " + roundResult + " commission points (product price)");
		return resultMsg.toString();
	}

	@Override
	public void updateSalcePriceCommissionPoints(
			final I_M_PriceList_Version plv,
			final I_M_DiscountSchemaLine dsl,
			final int adPinstanceId,
			final String trxName)
	{
		final int plCountryId = InterfaceWrapperHelper.create(plv.getM_PriceList(), I_M_PriceList.class).getC_Country_ID();
		final boolean subtractVAT;
		if (plCountryId <= 0)
		{
			subtractVAT = false;
		}
		else
		{
			subtractVAT = dsl.isCommissionPoints_SubtractVAT();
		}

		// update commission points from same psp
		final String sql =
				" UPDATE M_ProductScalePrice psp \n"
						+ " SET CommissionPoints= \n"
						+ "    (bpsp.base + ?) \n" /* 1 dsl.getCommissionPoints_AddAmt() */
						+ " 		* (1 - ?/100) \n" /* 2 dsl.getCommissionPoints_Discount() */
						+ " 		* (1 - bpsp.subtVat/100) \n"
						+ " FROM ( \n"
						+ "   select  \n"
						+ "     psp2.M_ProductScalePrice_ID, psp2.M_ProductPrice_ID, pp.M_PriceList_Version_ID, pp.M_Product_ID, \n"
						+ " 	CASE \n"
						+ "         WHEN '" + dsl.getCommissionPoints_Base() + "'='LST' THEN psp2.PriceList \n"
						+ "         WHEN '" + dsl.getCommissionPoints_Base() + "'='STD' THEN psp2.PriceStd \n"
						+ "         WHEN '" + dsl.getCommissionPoints_Base() + "'='LMT' THEN psp2.PriceLimit \n"
						+ " 	END as base, \n"
						+ " 	CASE \n"
						+ " 		WHEN '" + (subtractVAT ? "Y" : "N") + "'='Y' THEN COALESCE(t.Rate,0) \n"
						+ " 		ELSE 0 \n"
						+ " 	END as subtVat \n"
						+ "   from M_ProductScalePrice psp2 \n"
						+ "     left join M_ProductPrice pp \n"
						+ "       ON pp.M_ProductPrice_ID=psp2.M_ProductPrice_ID \n"
						+ " 	left join C_Tax t \n"
						+ "       ON t.C_TaxCategory_ID=pp.C_TaxCategory_ID AND t.C_Country_ID=? AND t.To_Country_ID=? AND t.IsTaxExempt='N' \n" /* 3,4 plCountryId */
						+ "   where true \n"
						+ "       AND (t.ValidFrom is NULL OR t.ValidFrom<=?) \n" /* 5 dsl.getConversionDate() */
						+ "       AND NOT EXISTS ( \n" /* The tax rate that we join must be the most recent rate that is valid at the dsl's conversion date */
						+ "             select * from C_Tax t2 \n"
						+ "             where t2.C_TaxCategory_ID=t.C_TaxCategory_ID AND t2.C_Country_ID=t.C_Country_ID AND t2.To_Country_ID=t.To_Country_ID AND t2.IsTaxExempt='N'\n"
						+ "                AND t2.ValidFrom<=? \n" /* 6 dsl.getConversionDate() */
						+ "                AND t2.ValidFrom>t.ValidFrom"
						+ "       )"
						+ " ) bpsp \n"
						+ " WHERE  \n"
						+ " 	bpsp.M_ProductScalePrice_ID=psp.M_ProductScalePrice_ID \n"
						+ "     AND bpsp.M_PriceList_Version_ID=? \n" /* 7 plv.getM_Pricelist_Version_ID() */
						+ " 	AND psp.IsActive='Y' \n"
						+ " 	AND EXISTS ( \n"
						+ "        SELECT * FROM T_Selection s \n"
						+ "        WHERE bpsp.M_Product_ID=s.T_Selection_ID AND s.AD_PInstance_ID=? \n" /* 5 adPinstanceId */
						+ " 	) \n";

		final PreparedStatement pstmtComPoints = DB.prepareStatement(sql, trxName);
		try
		{
			pstmtComPoints.setBigDecimal(1, dsl.getCommissionPoints_AddAmt());
			pstmtComPoints.setBigDecimal(2, dsl.getCommissionPoints_Discount());
			pstmtComPoints.setInt(3, plCountryId);
			pstmtComPoints.setInt(4, plCountryId);
			pstmtComPoints.setTimestamp(5, dsl.getConversionDate());
			pstmtComPoints.setTimestamp(6, dsl.getConversionDate());
			pstmtComPoints.setInt(7, plv.getM_PriceList_Version_ID());
			pstmtComPoints.setInt(8, adPinstanceId);

			final int result = pstmtComPoints.executeUpdate();
			PriceListBL.logger.info("Updated " + result + " commission points (product scale price)");
		}
		catch (final SQLException e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(pstmtComPoints);
		}

		//
		// Rounding (AD_Reference_ID=155)
		final I_C_Currency targetCurrency = plv.getM_PriceList().getC_Currency();

		final String sqlUpdRound =
				"UPDATE	M_ProductScalePrice psp "
						+ " SET	"
						+ "  CommissionPoints = DECODE('" + dsl.getCommissionPoints_Rounding() + "',"
						+ "    'N', psp.CommissionPoints, "
						+ "    '0', ROUND(psp.CommissionPoints, 0)," // Even .00
						+ "    'D', ROUND(psp.CommissionPoints, 1)," // Dime .10
						+ "    'T', ROUND(psp.CommissionPoints, -1), " // Ten 10.00
						+ "    '5', ROUND(psp.CommissionPoints*20,0)/20," // Nickle .05
						+ "    'Q', ROUND(psp.CommissionPoints*4,0)/4," // Quarter .25
						+ "    '9', CASE" // Whole 9 or 5
						+ "      WHEN MOD(ROUND(psp.CommissionPoints),10)<=5 THEN ROUND(psp.CommissionPoints)+(5-MOD(ROUND(psp.CommissionPoints),10))"
						+ "      WHEN MOD(ROUND(psp.CommissionPoints),10)>5 THEN ROUND(psp.CommissionPoints)+(9-MOD(ROUND(psp.CommissionPoints),10)) "
						+ "    END,"
						+ "    ROUND(psp.CommissionPoints, " + targetCurrency.getStdPrecision() + ")"
						+ "  )"
						+ " FROM "
						+ "   M_ProductPrice pp "
						+ " WHERE "
						+ "   pp.M_ProductPrice_ID=psp.M_ProductPrice_ID "
						+ "   AND pp.M_PriceList_Version_ID = " + plv.getM_PriceList_Version_ID()
						+ "   AND EXISTS ( "
						+ "        SELECT * FROM T_Selection s "
						+ "        WHERE pp.M_Product_ID=s.T_Selection_ID AND s.AD_PInstance_ID=" + adPinstanceId
						+ "   ) "
						+ "   AND pp.UseScalePrice='Y'"
						+ "   AND pp.Isactive='Y'";

		final int roundResult = DB.executeUpdateEx(sqlUpdRound, trxName);
		PriceListBL.logger.info("Rounded " + roundResult + " commission points (product scale price)");
	}
}
