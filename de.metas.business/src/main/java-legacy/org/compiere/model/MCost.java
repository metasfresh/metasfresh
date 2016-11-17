/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 * Contributor(s): Teo Sarca                                                  *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.api.LoggerTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Services;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.currency.ICurrencyBL;
import de.metas.product.IProductBL;

/**
 * 	Product Cost Model
 *
 *  @author Jorg Janke
 *  @version $Id: MCost.java,v 1.6 2006/07/30 00:51:02 jjanke Exp $
 *
 *  @author Carlos Ruiz - globalqss
 *		<li>integrate bug fix from Teo Sarca - [ 1619112 ] Posible problem for LastPO costing, Batch/Lot level
 *
 *  @author Red1
 *  	<li>FR: [ 2214883 ] Remove SQL code and Replace for Query - red1 (only non-join query)
 *  
 *  @author Teo Sarca
 *  	<li>BF [ 2847648 ] Manufacture & shipment cost errors
 *  		https://sourceforge.net/tracker/?func=detail&aid=2847648&group_id=176962&atid=934929
 */
public class MCost extends X_M_Cost
{
	/**
	 *
	 */
	private static final long serialVersionUID = -127982599769472918L;


	/**
	 * 	Retrieve/Calculate Current Cost Price
	 *	@param product product
	 *	@param M_AttributeSetInstance_ID real asi
	 *	@param as accounting schema
	 *	@param AD_Org_ID real org
	 *	@param costingMethod AcctSchema.COSTINGMETHOD_*
	 *	@param qty qty
	 *	@param C_OrderLine_ID optional order line
	 *	@param zeroCostsOK zero/no costs are OK
	 *	@param trxName trx
	 *	@return current cost price or null
	 */
	public static BigDecimal getCurrentCost (I_M_Product product,
		int M_AttributeSetInstance_ID,
		MAcctSchema as, int AD_Org_ID, String costingMethod,
		BigDecimal qty, int C_OrderLine_ID,
		boolean zeroCostsOK, String trxName)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		
		final String CostingLevel = productBL.getCostingLevel(product, as);
		if (MAcctSchema.COSTINGLEVEL_Client.equals(CostingLevel))
		{
			AD_Org_ID = 0;
			M_AttributeSetInstance_ID = 0;
		}
		else if (MAcctSchema.COSTINGLEVEL_Organization.equals(CostingLevel))
			M_AttributeSetInstance_ID = 0;
		else if (MAcctSchema.COSTINGLEVEL_BatchLot.equals(CostingLevel))
			AD_Org_ID = 0;
		//	Costing Method
		if (costingMethod == null)
		{
			costingMethod = productBL.getCostingMethod(product, as);
			if (costingMethod == null)
			{
				throw new IllegalArgumentException("No Costing Method");
			}
		}

		//	Create/Update Costs
		MCostDetail.processProduct (product, trxName);

		return getCurrentCost (
			product, M_AttributeSetInstance_ID,
			as, AD_Org_ID, as.getM_CostType_ID(), costingMethod, qty,
			C_OrderLine_ID, zeroCostsOK, trxName);
	}	//	getCurrentCost

	/**
	 * 	Get Current Cost Price for Costing Level
	 *	@param product product
	 *	@param M_ASI_ID costing level asi
	 *	@param Org_ID costing level org
	 *	@param M_CostType_ID cost type
	 *	@param as AcctSchema
	 *	@param costingMethod method
	 *	@param qty quantity
	 *	@param C_OrderLine_ID optional order line
	 *	@param zeroCostsOK zero/no costs are OK
	 *	@param trxName trx
	 *	@return cost price or null
	 */
	private static BigDecimal getCurrentCost (I_M_Product product, int M_ASI_ID,
		MAcctSchema as, int Org_ID, int M_CostType_ID,
		String costingMethod, BigDecimal qty, int C_OrderLine_ID,
		boolean zeroCostsOK,
		final String trxName)
	{
		String costElementType = null;
		BigDecimal percent = null;
		//
		BigDecimal materialCostEach = Env.ZERO;
		BigDecimal otherCostEach = Env.ZERO;
		BigDecimal percentage = Env.ZERO;
		int count = 0;
		//
		String sql = "SELECT"
			+ " COALESCE(SUM(c.CurrentCostPrice),0),"		// 1
			+ " ce.CostElementType, ce.CostingMethod,"		// 2,3
			+ " c.Percent, c.M_CostElement_ID ,"			// 4,5
			+ " COALESCE(SUM(c.CurrentCostPriceLL),0) "		// 6
			+ " FROM M_Cost c"
			+ " LEFT OUTER JOIN M_CostElement ce ON (c.M_CostElement_ID=ce.M_CostElement_ID) "
			+ "WHERE c.AD_Client_ID=? AND c.AD_Org_ID=?"		//	#1/2
			+ " AND c.M_Product_ID=?"							//	#3
			+ " AND (c.M_AttributeSetInstance_ID=? OR c.M_AttributeSetInstance_ID=0)"	//	#4
			+ " AND c.M_CostType_ID=? AND c.C_AcctSchema_ID=?"	//	#5/6
			+ " AND (ce.CostingMethod IS NULL OR ce.CostingMethod=?) "	//	#7
			+ "GROUP BY ce.CostElementType, ce.CostingMethod, c.Percent, c.M_CostElement_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, product.getAD_Client_ID());
			pstmt.setInt (2, Org_ID);
			pstmt.setInt (3, product.getM_Product_ID());
			pstmt.setInt (4, M_ASI_ID);
			pstmt.setInt (5, M_CostType_ID);
			pstmt.setInt (6, as.getC_AcctSchema_ID());
			pstmt.setString (7, costingMethod);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				BigDecimal currentCostPrice = rs.getBigDecimal(1);
				BigDecimal currentCostPriceLL = rs.getBigDecimal(6);
				costElementType = rs.getString(2);
				String cm = rs.getString(3);
				percent = rs.getBigDecimal(4);
				//M_CostElement_ID = rs.getInt(5);
				s_log.trace("CurrentCostPrice=" + currentCostPrice
					+ ", CurrentCostPriceLL=" + currentCostPriceLL
					+ ", CostElementType=" + costElementType
					+ ", CostingMethod=" + cm
					+ ", Percent=" + percent);
				//
				if (currentCostPrice.signum() != 0 || currentCostPriceLL.signum() != 0)
				{
					if (cm != null)
					{
						materialCostEach = materialCostEach.add(currentCostPrice).add(currentCostPriceLL);
					}
					else
					{
						otherCostEach = otherCostEach.add(currentCostPrice).add(currentCostPriceLL);
					}
				}
				if (percent != null && percent.signum() != 0)
					percentage = percentage.add(percent);
				count++;
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		if (count > 1)	//	Print summary
			s_log.trace("MaterialCost=" + materialCostEach
				+ ", OtherCosts=" + otherCostEach
				+ ", Percentage=" + percentage);

		//	Seed Initial Costs
		if (materialCostEach.signum() == 0)		//	no costs
		{
			if (zeroCostsOK)
				return Env.ZERO;
			
			// Case: we are using StandardCosting and user explicitelly set the M_Cost.CurrentCostPrice to ZERO.
			// We shall respect that.
			if (MCostElement.COSTINGMETHOD_StandardCosting.equals(costingMethod) && count > 0)
			{
				return Env.ZERO;
			}
			
			materialCostEach = getSeedCosts(product, M_ASI_ID,
				as, Org_ID, costingMethod, C_OrderLine_ID,
				trxName);
		}
		if (materialCostEach == null)
			return null;

		//	Material Costs
		BigDecimal materialCost = materialCostEach.multiply(qty);
		//	Standard costs - just Material Costs
		if (MCostElement.COSTINGMETHOD_StandardCosting.equals(costingMethod))
		{
			s_log.trace("MaterialCosts = " + materialCost);
			return materialCost;
		}
		if (MCostElement.COSTINGMETHOD_Fifo.equals(costingMethod)
			|| MCostElement.COSTINGMETHOD_Lifo.equals(costingMethod))
		{
			MCostElement ce = MCostElement.getMaterialCostElement(as, costingMethod);
			materialCost = MCostQueue.getCosts(product, M_ASI_ID,
				as, Org_ID, ce, qty, trxName);
		}

		//	Other Costs
		BigDecimal otherCost = otherCostEach.multiply(qty);

		//	Costs
		BigDecimal costs = otherCost.add(materialCost);
		if (costs.signum() == 0)
			return null;

		s_log.trace("Sum Costs = " + costs);
		int precision = as.getCostingPrecision();
		if (percentage.signum() == 0)	//	no percentages
		{
			if (costs.scale() > precision)
				costs = costs.setScale(precision, BigDecimal.ROUND_HALF_UP);
			return costs;
		}
		//
		BigDecimal percentCost = costs.multiply(percentage);
		percentCost = percentCost.divide(Env.ONEHUNDRED, precision, BigDecimal.ROUND_HALF_UP);
		costs = costs.add(percentCost);
		if (costs.scale() > precision)
			costs = costs.setScale(precision, BigDecimal.ROUND_HALF_UP);
		s_log.trace("Sum Costs = " + costs + " (Add=" + percentCost + ")");
		return costs;
	}	//	getCurrentCost

	/**
	 * 	Get Seed Costs
	 *	@param product product
	 *	@param M_ASI_ID costing level asi
	 *	@param as accounting schema
	 *	@param Org_ID costing level org
	 *	@param costingMethod costing method
	 *	@param C_OrderLine_ID optional order line
	 *	@return price or null
	 */
	/*package*/ static BigDecimal getSeedCosts (I_M_Product product, int M_ASI_ID,
		MAcctSchema as, int Org_ID, String costingMethod, int C_OrderLine_ID,
		final String trxName)
	{
		BigDecimal retValue = null;
		//	Direct Data
		if (MCostElement.COSTINGMETHOD_AverageInvoice.equals(costingMethod))
			retValue = calculateAverageInv(product, M_ASI_ID, as, Org_ID);
		else if (MCostElement.COSTINGMETHOD_AveragePO.equals(costingMethod))
			retValue = calculateAveragePO(product, M_ASI_ID, as, Org_ID);
		else if (MCostElement.COSTINGMETHOD_Fifo.equals(costingMethod))
			retValue = calculateFiFo(product, M_ASI_ID, as, Org_ID);
		else if (MCostElement.COSTINGMETHOD_Lifo.equals(costingMethod))
			retValue = calculateLiFo(product, M_ASI_ID, as, Org_ID);
		else if (MCostElement.COSTINGMETHOD_LastInvoice.equals(costingMethod))
			retValue = getLastInvoicePrice(product, M_ASI_ID, Org_ID, as.getC_Currency_ID(), trxName);
		else if (MCostElement.COSTINGMETHOD_LastPOPrice.equals(costingMethod))
		{
			if (C_OrderLine_ID != 0)
				retValue = getPOPrice(product, C_OrderLine_ID, as.getC_Currency_ID(), trxName);
			if (retValue == null || retValue.signum() == 0)
				retValue = getLastPOPrice(product, M_ASI_ID, Org_ID, as.getC_Currency_ID(), trxName);
		}
		else if (MCostElement.COSTINGMETHOD_StandardCosting.equals(costingMethod))
		{
			//	migrate old costs
			final Properties ctx = InterfaceWrapperHelper.getCtx(product);
			MProductCosting pc = MProductCosting.get(ctx, product.getM_Product_ID(),
				as.getC_AcctSchema_ID(), ITrx.TRXNAME_None);
			if (pc != null)
				retValue = pc.getCurrentCostPrice();
		}
		else if (MCostElement.COSTINGMETHOD_UserDefined.equals(costingMethod))
			;
		else
			throw new IllegalArgumentException("Unknown Costing Method = " + costingMethod);
		if (retValue != null && retValue.signum() != 0)
		{
			s_log.debug(product.getName() + ", CostingMethod=" + costingMethod + " - " + retValue);
			return retValue;
		}

		//	Look for exact Order Line
		if (C_OrderLine_ID != 0)
		{
			retValue = getPOPrice(product, C_OrderLine_ID, as.getC_Currency_ID(), trxName);
			if (retValue != null && retValue.signum() != 0)
			{
				s_log.debug(product.getName() + ", PO - " + retValue);
				return retValue;
			}
		}

		//	Look for Standard Costs first
		if (!MCostElement.COSTINGMETHOD_StandardCosting.equals(costingMethod))
		{
			MCostElement ce = MCostElement.getMaterialCostElement(as, MCostElement.COSTINGMETHOD_StandardCosting);
			MCost cost = get(product, M_ASI_ID, as, Org_ID, ce.getM_CostElement_ID(), trxName);
			if (cost != null && cost.getCurrentCostPrice().signum() != 0)
			{
				s_log.debug(product.getName() + ", Standard - " + retValue);
				return cost.getCurrentCostPrice();
			}
		}

		//	We do not have a price
		//	PO first
		if (MCostElement.COSTINGMETHOD_AveragePO.equals(costingMethod)
			|| MCostElement.COSTINGMETHOD_LastPOPrice.equals(costingMethod)
			|| MCostElement.COSTINGMETHOD_StandardCosting.equals(costingMethod))
		{
			//	try Last PO
			retValue = getLastPOPrice(product, M_ASI_ID, Org_ID, as.getC_Currency_ID(), trxName);
			if (Org_ID != 0 && (retValue == null || retValue.signum() == 0))
				retValue = getLastPOPrice(product, M_ASI_ID, 0, as.getC_Currency_ID(), trxName);
			if (retValue != null && retValue.signum() != 0)
			{
				s_log.debug(product.getName() + ", LastPO = " + retValue);
				return retValue;
			}
		}
		else	//	Inv first
		{
			//	try last Inv
			retValue = getLastInvoicePrice(product, M_ASI_ID, Org_ID, as.getC_Currency_ID(), trxName);
			if (Org_ID != 0 && (retValue == null || retValue.signum() == 0))
				retValue = getLastInvoicePrice(product, M_ASI_ID, 0, as.getC_Currency_ID(), trxName);
			if (retValue != null && retValue.signum() != 0)
			{
				s_log.debug(product.getName() + ", LastInv = " + retValue);
				return retValue;
			}
		}

		//	Still Nothing
		//	Inv second
		if (MCostElement.COSTINGMETHOD_AveragePO.equals(costingMethod)
			|| MCostElement.COSTINGMETHOD_LastPOPrice.equals(costingMethod)
			|| MCostElement.COSTINGMETHOD_StandardCosting.equals(costingMethod))
		{
			//	try last Inv
			retValue = getLastInvoicePrice(product, M_ASI_ID, Org_ID, as.getC_Currency_ID(), trxName);
			if (Org_ID != 0 && (retValue == null || retValue.signum() == 0))
				retValue = getLastInvoicePrice(product, M_ASI_ID, 0, as.getC_Currency_ID(), trxName);
			if (retValue != null && retValue.signum() != 0)
			{
				s_log.debug(product.getName() + ", LastInv = " + retValue);
				return retValue;
			}
		}
		else	//	PO second
		{
			//	try Last PO
			retValue = getLastPOPrice(product, M_ASI_ID, Org_ID, as.getC_Currency_ID(), trxName);
			if (Org_ID != 0 && (retValue == null || retValue.signum() == 0))
				retValue = getLastPOPrice(product, M_ASI_ID, 0, as.getC_Currency_ID(), trxName);
			if (retValue != null && retValue.signum() != 0)
			{
				s_log.debug(product.getName() + ", LastPO = " + retValue);
				return retValue;
			}
		}

		//	Still nothing try ProductPO
		final Properties ctx = InterfaceWrapperHelper.getCtx(product);
		MProductPO[] pos = MProductPO.getOfProduct(ctx, product.getM_Product_ID(), ITrx.TRXNAME_None);
		for (int i = 0; i < pos.length; i++)
		{
			BigDecimal price = pos[i].getPricePO();
			if (price == null || price.signum() == 0)
				price = pos[i].getPriceList();
			if (price != null && price.signum() != 0)
			{
				final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
				price = currencyConversionBL.convert(ctx, price,
					pos[i].getC_Currency_ID(), as.getC_Currency_ID(),
					as.getAD_Client_ID(), Org_ID);
				if (price != null && price.signum() != 0)
				{
					if (pos[i].getC_UOM_ID() != product.getC_UOM_ID())
					{
						final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
						price = uomConversionBL.convertFromProductUOM (Env.getCtx(),
								product,
								pos[i].getC_UOM(),
								price);
						if (price == null) s_log.warn("@NoUOMConversion@ @M_Product_ID@:"+product.getValue()+", @To_UOM_ID@:"+pos[i].getC_UOM_ID()); // metas: 01428
					}
				}
				if (price != null && price.signum() != 0)
				{
					retValue = price;
					s_log.debug(product.getName() + ", Product_PO = " + retValue);
					return retValue;
				}
			}
		}

		//	Still nothing try Purchase Price List
		//	....

		s_log.debug(product.getName() + " = " + retValue);
		return retValue;
	}	//	getSeedCosts


	/**
	 * 	Get Last Invoice Price in currency
	 *	@param product product
	 *	@param M_ASI_ID attribute set instance
	 *	@param AD_Org_ID org
	 *	@param C_Currency_ID accounting currency
	 *	@return last invoice price in currency
	 */
	public static BigDecimal getLastInvoicePrice (I_M_Product product,
		int M_ASI_ID, int AD_Org_ID, int C_Currency_ID,
		final String trxName)
	{
		BigDecimal retValue = null;
		String sql = "SELECT currencyConvert(il.PriceActual, i.C_Currency_ID, ?, i.DateAcct, i.C_ConversionType_ID, il.AD_Client_ID, il.AD_Org_ID) "
			// ,il.PriceActual, il.QtyInvoiced, i.DateInvoiced, il.Line
			+ "FROM C_InvoiceLine il "
			+ " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID) "
			+ "WHERE il.M_Product_ID=?"
			+ " AND i.IsSOTrx='N'";
		if (AD_Org_ID != 0)
			sql += " AND il.AD_Org_ID=?";
		else if (M_ASI_ID != 0)
			sql += " AND il.M_AttributeSetInstance_ID=?";
		sql += " ORDER BY i.DateInvoiced DESC, il.Line DESC";
		//
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, C_Currency_ID);
			pstmt.setInt (2, product.getM_Product_ID());
			if (AD_Org_ID != 0)
				pstmt.setInt (3, AD_Org_ID);
			else if (M_ASI_ID != 0)
				pstmt.setInt(3, M_ASI_ID);
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = rs.getBigDecimal(1);
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}

		if (retValue != null)
		{
			s_log.trace(product.getName() + " = " + retValue);
			return retValue;
		}
		return null;
	}	//	getLastInvoicePrice

	/**
	 * 	Get Last PO Price in currency
	 *	@param product product
	 *	@param M_ASI_ID attribute set instance
	 *	@param AD_Org_ID org
	 *	@param C_Currency_ID accounting currency
	 *	@return last PO price in currency or null
	 */
	public static BigDecimal getLastPOPrice (I_M_Product product,
		int M_ASI_ID, int AD_Org_ID, int C_Currency_ID,
		final String trxName)
	{
		BigDecimal retValue = null;
		String sql = "SELECT currencyConvert(ol.PriceCost, o.C_Currency_ID, ?, o.DateAcct, o.C_ConversionType_ID, ol.AD_Client_ID, ol.AD_Org_ID),"
			+ " currencyConvert(ol.PriceActual, o.C_Currency_ID, ?, o.DateAcct, o.C_ConversionType_ID, ol.AD_Client_ID, ol.AD_Org_ID) "
			//	,ol.PriceCost,ol.PriceActual, ol.QtyOrdered, o.DateOrdered, ol.Line
			+ "FROM C_OrderLine ol"
			+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
			+ "WHERE ol.M_Product_ID=?"
			+ " AND o.IsSOTrx='N'";
		if (AD_Org_ID != 0)
			sql += " AND ol.AD_Org_ID=?";
		else if (M_ASI_ID != 0)
			sql += " AND ol.M_AttributeSetInstance_ID=?";
		sql += " ORDER BY o.DateOrdered DESC, ol.Line DESC";
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, C_Currency_ID);
			pstmt.setInt (2, C_Currency_ID);
			pstmt.setInt (3, product.getM_Product_ID());
			if (AD_Org_ID != 0)
				pstmt.setInt (4, AD_Org_ID);
			else if (M_ASI_ID != 0)
				pstmt.setInt(4, M_ASI_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				retValue = rs.getBigDecimal(1);
				if (retValue == null || retValue.signum() == 0)
					retValue = rs.getBigDecimal(2);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		if (retValue != null)
		{
			s_log.trace(product.getName() + " = " + retValue);
			return retValue;
		}
		return null;
	}	//	getLastPOPrice

	/**
	 * 	Get PO Price in currency
	 * 	@param product product
	 *	@param C_OrderLine_ID order line
	 *	@param C_Currency_ID accounting currency
	 *	@return last PO price in currency or null
	 */
	public static BigDecimal getPOPrice (I_M_Product product, int C_OrderLine_ID, int C_Currency_ID,
			final String trxName)
	{
		BigDecimal retValue = null;
		String sql = "SELECT currencyConvert(ol.PriceCost, o.C_Currency_ID, ?, o.DateAcct, o.C_ConversionType_ID, ol.AD_Client_ID, ol.AD_Org_ID),"
			+ " currencyConvert(ol.PriceActual, o.C_Currency_ID, ?, o.DateAcct, o.C_ConversionType_ID, ol.AD_Client_ID, ol.AD_Org_ID) "
			//	,ol.PriceCost,ol.PriceActual, ol.QtyOrdered, o.DateOrdered, ol.Line
			+ "FROM C_OrderLine ol"
			+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
			+ "WHERE ol.C_OrderLine_ID=?"
			+ " AND o.IsSOTrx='N'";
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, C_Currency_ID);
			pstmt.setInt (2, C_Currency_ID);
			pstmt.setInt (3, C_OrderLine_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				retValue = rs.getBigDecimal(1);
				if (retValue == null || retValue.signum() == 0)
					retValue = rs.getBigDecimal(2);
			}
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		if (retValue != null)
		{
			s_log.trace(product.getName() + " = " + retValue);
			return retValue;
		}
		return null;
	}	//	getPOPrice

	/**
	 * Create costing for client.
	 * 
	 * NOTE:
	 * <ul>
	 * <li>this method assumes you are running out of transaction and it will always handle the transaction
	 * <li>it will never throw exception but it errors will be logged 
	 * </ul>
	 *
	 * @param client client
	 */
	public static void create(final I_AD_Client client)
	{
		// Get Products.
		// If there are no products, there is no point to go forward
		final Iterator<I_M_Product> products = retrieveProductsWithNotProcessedCostDetails(client);
		if (!products.hasNext())
		{
			return;
		}
		
		final Properties ctx = InterfaceWrapperHelper.getCtx(client);
		final MAcctSchema[] acctSchemas = MAcctSchema.getClientAcctSchema(ctx, client.getAD_Client_ID());

		Services.get(ITrxItemProcessorExecutorService.class)
				.<I_M_Product, Void> createExecutor()
				.setContext(ctx, ITrx.TRXNAME_None)
				.setExceptionHandler(LoggerTrxItemExceptionHandler.instance)
				.setProcessor(new TrxItemProcessorAdapter<I_M_Product, Void>()
				{
					@Override
					public void process(final I_M_Product product) throws Exception
					{
						for (final MAcctSchema acctSchema : acctSchemas)
						{
							final BigDecimal cost = getCurrentCost(
									product, // product
									0, // M_AttributeSetInstance_ID
									acctSchema, // C_AcctSchema
									0, // AD_Org_ID
									null,  // CostingMethod
									Env.ONE, // Qty
									0, // C_OrderLine_ID
									false, // zeroCostsOK: create non-zero costs
									getTrxName());
							s_log.info("{} = {}", new Object[] { product.getName(), cost });
						}
					}
				})
				.process(products);
	}
	
	private static final Iterator<I_M_Product> retrieveProductsWithNotProcessedCostDetails(final I_AD_Client client)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(client);
		
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_CostDetail.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_CostDetail.COLUMN_Processed, false) // not processed cost details
				//
				// Collect products from those cost details
				.andCollect(I_M_CostDetail.COLUMN_M_Product_ID)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product.COLUMN_AD_Client_ID, client.getAD_Client_ID()) // only for our client
				//
				// Retrieve an iterator of those products
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.iterate(I_M_Product.class);
	}
	
	/**
	 * 	Create standard Costing records for Product
	 *	@param product product
	 */
	// metas: 01432: changed from protected to public
	public static void create (final I_M_Product product)
	{
			s_log.info(product.getName());
			final Properties ctx = InterfaceWrapperHelper.getCtx(product);
			final String trxName = InterfaceWrapperHelper.getTrxName(product);
			

			//	Cost Elements
			Collection <MCostElement> ces = MCostElement.getCostElementsWithCostingMethods(product);

			final MAcctSchema[] mass = MAcctSchema.getClientAcctSchema(ctx, product.getAD_Client_ID());
			MOrg[] orgs = null;

			int M_ASI_ID = 0;		//	No Attribute
			for (final MAcctSchema as : mass)
			{
				if(as.isSkipOrg(product.getAD_Org_ID()))
				{
					continue;
				}
				final String cl =  Services.get(IProductBL.class).getCostingLevel(product, as);
				
				//	Create Std Costing
				if (MAcctSchema.COSTINGLEVEL_Client.equals(cl))
				{
					for(MCostElement ce : ces)
					{
						MCost cost = MCost.get (product, M_ASI_ID,
							as, 0, ce.getM_CostElement_ID(), trxName);
						if (cost.is_new())
						{
							if (cost.save())
								s_log.info("Std.Cost for " + product.getName()
									+ " - " + as.getName());
							else
								s_log.warn("Not created: Std.Cost for " + product.getName()
									+ " - " + as.getName());
						}
					}
				}
				else if (MAcctSchema.COSTINGLEVEL_Organization.equals(cl))
				{
					if (orgs == null)
						orgs = MOrg.getOfClient(ctx, product.getAD_Client_ID());
					for (MOrg o : orgs)
					{
						for(MCostElement ce : ces)
						{
							MCost cost = MCost.get (product, M_ASI_ID,
								as, o.getAD_Org_ID(), ce.getM_CostElement_ID(), trxName);
							if (cost.is_new())
							{
								if (cost.save())
									s_log.info("Std.Cost for " + product.getName()
										+ " - " + o.getName()
										+ " - " + as.getName());
								else
									s_log.warn("Not created: Std.Cost for " + product.getName()
										+ " - " + o.getName()
										+ " - " + as.getName());
							}
						}
					}	//	for all orgs
				}
				else
				{
					s_log.warn("Not created: Std.Cost for " + product.getName()
						+ " - Costing Level on Batch/Lot");
				}
			}	//	accounting schema loop
	}	//	create

	/**
	 * 	Delete standard Costing records for Product
	 *	@param product product
	 **/
	protected static void delete (MProduct product)
	{
		s_log.info(product.getName());
		//	Cost Elements
		Collection <MCostElement> ces = MCostElement.getCostElementsWithCostingMethods(product);

			MAcctSchema[] mass = MAcctSchema.getClientAcctSchema(product.getCtx(), product.getAD_Client_ID());
			MOrg[] orgs = null;

			int M_ASI_ID = 0;		//	No Attribute
			for (MAcctSchema as : mass)
			{
				if(as.isSkipOrg(product.getAD_Org_ID()))
				{
					continue;
				}
				final String cl =  Services.get(IProductBL.class).getCostingLevel(product, as);
				
				//	Create Std Costing
				if (MAcctSchema.COSTINGLEVEL_Client.equals(cl))
				{
					for(MCostElement ce : ces)
					{
						MCost cost = MCost.get (product, M_ASI_ID,
							as, 0, ce.getM_CostElement_ID(), product.get_TrxName());
						if(cost != null)
						cost.deleteEx(true);
					}
				}
				else if (MAcctSchema.COSTINGLEVEL_Organization.equals(cl))
				{
					if (orgs == null)
						orgs = MOrg.getOfClient(product);
					for (MOrg o : orgs)
					{
						for(MCostElement ce : ces)
						{
							MCost cost = MCost.get (product, M_ASI_ID,
								as, o.getAD_Org_ID(), ce.getM_CostElement_ID(), product.get_TrxName());
							if(cost != null)
								cost.deleteEx(true);
						}
					}	//	for all orgs
				}
				else
				{
					s_log.warn("Not created: Std.Cost for " + product.getName()
						+ " - Costing Level on Batch/Lot");
				}
			}	//	accounting schema loop
	}	//	create

	/**************************************************************************
	 * 	Calculate Average Invoice from Trx
	 *	@param product product
	 *	@param M_AttributeSetInstance_ID optional asi
	 *	@param as acct schema
	 *	@param AD_Org_ID optonal org
	 *	@return average costs or null
	 */
	public static BigDecimal calculateAverageInv (final I_M_Product product, int M_AttributeSetInstance_ID,
		MAcctSchema as, int AD_Org_ID)
	{
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(product);
		
		String sql = "SELECT t.MovementQty, mi.Qty, il.QtyInvoiced, il.PriceActual,"
			+ " i.C_Currency_ID, i.DateAcct, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID, t.M_Transaction_ID "
			+ "FROM M_Transaction t"
			+ " INNER JOIN M_MatchInv mi ON (t.M_InOutLine_ID=mi.M_InOutLine_ID)"
			+ " INNER JOIN C_InvoiceLine il ON (mi.C_InvoiceLine_ID=il.C_InvoiceLine_ID)"
			+ " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID) "
			+ "WHERE t.M_Product_ID=?";
		if (AD_Org_ID != 0)
			sql += " AND t.AD_Org_ID=?";
		else if (M_AttributeSetInstance_ID != 0)
			sql += " AND t.M_AttributeSetInstance_ID=?";
		sql += " ORDER BY t.M_Transaction_ID";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BigDecimal newStockQty = Env.ZERO;
		//
		BigDecimal newAverageAmt = Env.ZERO;
		int oldTransaction_ID = 0;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, product.getM_Product_ID());
			if (AD_Org_ID != 0)
				pstmt.setInt (2, AD_Org_ID);
			else if (M_AttributeSetInstance_ID != 0)
				pstmt.setInt (2, M_AttributeSetInstance_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				BigDecimal oldStockQty = newStockQty;
				BigDecimal movementQty = rs.getBigDecimal(1);
				int M_Transaction_ID = rs.getInt(10);
				if (M_Transaction_ID != oldTransaction_ID)
					newStockQty = oldStockQty.add(movementQty);
				M_Transaction_ID = oldTransaction_ID;
				//
				BigDecimal matchQty = rs.getBigDecimal(2);
				if (matchQty == null)
				{
					s_log.trace("Movement=" + movementQty + ", StockQty=" + newStockQty);
					continue;
				}
				//	Assumption: everything is matched
				BigDecimal price = rs.getBigDecimal(4);
				int C_Currency_ID = rs.getInt(5);
				Timestamp DateAcct = rs.getTimestamp(6);
				int C_ConversionType_ID = rs.getInt(7);
				int Client_ID = rs.getInt(8);
				int Org_ID = rs.getInt(9);
				BigDecimal cost = currencyConversionBL.convert(ctx, price,
					C_Currency_ID, as.getC_Currency_ID(),
					DateAcct, C_ConversionType_ID, Client_ID, Org_ID);
				//
				BigDecimal oldAverageAmt = newAverageAmt;
				BigDecimal averageCurrent = oldStockQty.multiply(oldAverageAmt);
				BigDecimal averageIncrease = matchQty.multiply(cost);
				BigDecimal newAmt = averageCurrent.add(averageIncrease);
				newAmt = newAmt.setScale(as.getCostingPrecision(), BigDecimal.ROUND_HALF_UP);
				newAverageAmt = newAmt.divide(newStockQty, as.getCostingPrecision(), BigDecimal.ROUND_HALF_UP);
				s_log.trace("Movement=" + movementQty + ", StockQty=" + newStockQty
					+ ", Match=" + matchQty + ", Cost=" + cost + ", NewAvg=" + newAverageAmt);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		if (newAverageAmt != null && newAverageAmt.signum() != 0)
		{
			s_log.trace(product.getName() + " = " + newAverageAmt);
			return newAverageAmt;
		}
		return null;
	}	//	calculateAverageInv

	/**
	 * 	Calculate Average PO
	 *	@param product product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param as acct schema
	 *	@param AD_Org_ID org
	 *	@return costs or null
	 */
	public static BigDecimal calculateAveragePO (I_M_Product product, int M_AttributeSetInstance_ID,
		MAcctSchema as, int AD_Org_ID)
	{
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(product);
		
		String sql = "SELECT t.MovementQty, mp.Qty, ol.QtyOrdered, ol.PriceCost, ol.PriceActual,"	//	1..5
			+ " o.C_Currency_ID, o.DateAcct, o.C_ConversionType_ID,"	//	6..8
			+ " o.AD_Client_ID, o.AD_Org_ID, t.M_Transaction_ID "		//	9..11
			+ "FROM M_Transaction t"
			+ " INNER JOIN M_MatchPO mp ON (t.M_InOutLine_ID=mp.M_InOutLine_ID)"
			+ " INNER JOIN C_OrderLine ol ON (mp.C_OrderLine_ID=ol.C_OrderLine_ID)"
			+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
			+ "WHERE t.M_Product_ID=?";
		if (AD_Org_ID != 0)
			sql += " AND t.AD_Org_ID=?";
		else if (M_AttributeSetInstance_ID != 0)
			sql += " AND t.M_AttributeSetInstance_ID=?";
		sql += " ORDER BY t.M_Transaction_ID";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BigDecimal newStockQty = Env.ZERO;
		//
		BigDecimal newAverageAmt = Env.ZERO;
		int oldTransaction_ID = 0;
		try
		{
			pstmt = DB.prepareStatement (sql, ITrx.TRXNAME_None);
			pstmt.setInt (1, product.getM_Product_ID());
			if (AD_Org_ID != 0)
				pstmt.setInt (2, AD_Org_ID);
			else if (M_AttributeSetInstance_ID != 0)
				pstmt.setInt (2, M_AttributeSetInstance_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				BigDecimal oldStockQty = newStockQty;
				BigDecimal movementQty = rs.getBigDecimal(1);
				int M_Transaction_ID = rs.getInt(11);
				if (M_Transaction_ID != oldTransaction_ID)
					newStockQty = oldStockQty.add(movementQty);
				M_Transaction_ID = oldTransaction_ID;
				//
				BigDecimal matchQty = rs.getBigDecimal(2);
				if (matchQty == null)
				{
					s_log.trace("Movement=" + movementQty + ", StockQty=" + newStockQty);
					continue;
				}
				//	Assumption: everything is matched
				BigDecimal price = rs.getBigDecimal(4);
				if (price == null || price.signum() == 0)	//	PO Cost
					price = rs.getBigDecimal(5);			//	Actual
				int C_Currency_ID = rs.getInt(6);
				Timestamp DateAcct = rs.getTimestamp(7);
				int C_ConversionType_ID = rs.getInt(8);
				int Client_ID = rs.getInt(9);
				int Org_ID = rs.getInt(10);
				BigDecimal cost = currencyConversionBL.convert(ctx, price,
					C_Currency_ID, as.getC_Currency_ID(),
					DateAcct, C_ConversionType_ID, Client_ID, Org_ID);
				//
				BigDecimal oldAverageAmt = newAverageAmt;
				BigDecimal averageCurrent = oldStockQty.multiply(oldAverageAmt);
				BigDecimal averageIncrease = matchQty.multiply(cost);
				BigDecimal newAmt = averageCurrent.add(averageIncrease);
				newAmt = newAmt.setScale(as.getCostingPrecision(), BigDecimal.ROUND_HALF_UP);
				newAverageAmt = newAmt.divide(newStockQty, as.getCostingPrecision(), BigDecimal.ROUND_HALF_UP);
				s_log.trace("Movement=" + movementQty + ", StockQty=" + newStockQty
					+ ", Match=" + matchQty + ", Cost=" + cost + ", NewAvg=" + newAverageAmt);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		if (newAverageAmt != null && newAverageAmt.signum() != 0)
		{
			s_log.trace(product.getName() + " = " + newAverageAmt);
			return newAverageAmt;
		}
		return null;
	}	//	calculateAveragePO

	/**
	 * 	Calculate FiFo Cost
	 *	@param product product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param as acct schema
	 *	@param AD_Org_ID org
	 *	@return costs or null
	 */
	public static BigDecimal calculateFiFo (I_M_Product product, int M_AttributeSetInstance_ID,
		MAcctSchema as, int AD_Org_ID)
	{
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(product);
		
		String sql = "SELECT t.MovementQty, mi.Qty, il.QtyInvoiced, il.PriceActual,"
			+ " i.C_Currency_ID, i.DateAcct, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID, t.M_Transaction_ID "
			+ "FROM M_Transaction t"
			+ " INNER JOIN M_MatchInv mi ON (t.M_InOutLine_ID=mi.M_InOutLine_ID)"
			+ " INNER JOIN C_InvoiceLine il ON (mi.C_InvoiceLine_ID=il.C_InvoiceLine_ID)"
			+ " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID) "
			+ "WHERE t.M_Product_ID=?";
		if (AD_Org_ID != 0)
			sql += " AND t.AD_Org_ID=?";
		else if (M_AttributeSetInstance_ID != 0)
			sql += " AND t.M_AttributeSetInstance_ID=?";
		sql += " ORDER BY t.M_Transaction_ID";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//
		int oldTransaction_ID = 0;
		ArrayList<QtyCost> fifo = new ArrayList<QtyCost>();
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, product.getM_Product_ID());
			if (AD_Org_ID != 0)
				pstmt.setInt (2, AD_Org_ID);
			else if (M_AttributeSetInstance_ID != 0)
				pstmt.setInt (2, M_AttributeSetInstance_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				BigDecimal movementQty = rs.getBigDecimal(1);
				int M_Transaction_ID = rs.getInt(10);
				if (M_Transaction_ID == oldTransaction_ID)
					continue;	//	assuming same price for receipt
				M_Transaction_ID = oldTransaction_ID;
				//
				BigDecimal matchQty = rs.getBigDecimal(2);
				if (matchQty == null)	//	out (negative)
				{
					if (fifo.size() > 0)
					{
						QtyCost pp = (QtyCost)fifo.get(0);
						pp.Qty = pp.Qty.add(movementQty);
						BigDecimal remainder = pp.Qty;
						if (remainder.signum() == 0)
							fifo.remove(0);
						else
						{
							while (remainder.signum() != 0)
							{
								if (fifo.size() == 1)	//	Last
								{
									pp.Cost = Env.ZERO;
									remainder = Env.ZERO;
								}
								else
								{
									fifo.remove(0);
									pp = (QtyCost)fifo.get(0);
									pp.Qty = pp.Qty.add(movementQty);
									remainder = pp.Qty;
								}
							}
						}
					}
					else
					{
						QtyCost pp = new QtyCost (movementQty, Env.ZERO);
						fifo.add(pp);
					}
					s_log.trace("Movement=" + movementQty + ", Size=" + fifo.size());
					continue;
				}
				//	Assumption: everything is matched
				BigDecimal price = rs.getBigDecimal(4);
				int C_Currency_ID = rs.getInt(5);
				Timestamp DateAcct = rs.getTimestamp(6);
				int C_ConversionType_ID = rs.getInt(7);
				int Client_ID = rs.getInt(8);
				int Org_ID = rs.getInt(9);
				BigDecimal cost = currencyConversionBL.convert(ctx, price,
					C_Currency_ID, as.getC_Currency_ID(),
					DateAcct, C_ConversionType_ID, Client_ID, Org_ID);

				//	Add Stock
				boolean used = false;
				if (fifo.size() == 1)
				{
					QtyCost pp = (QtyCost)fifo.get(0);
					if (pp.Qty.signum() < 0)
					{
						pp.Qty = pp.Qty.add(movementQty);
						if (pp.Qty.signum() == 0)
							fifo.remove(0);
						else
							pp.Cost = cost;
						used = true;
					}

				}
				if (!used)
				{
					QtyCost pp = new QtyCost (movementQty, cost);
					fifo.add(pp);
				}
				s_log.trace("Movement=" + movementQty + ", Size=" + fifo.size());
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		if (fifo.size() == 0)
		{
			return null;
		}
		QtyCost pp = (QtyCost)fifo.get(0);
		s_log.trace(product.getName() + " = " + pp.Cost);
		return pp.Cost;
	}	//	calculateFiFo

	/**
	 * 	Calculate LiFo costs
	 *	@param product product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param as acct schema
	 *	@param AD_Org_ID org
	 *	@return costs or null
	 */
	public static BigDecimal calculateLiFo (I_M_Product product, int M_AttributeSetInstance_ID,
		MAcctSchema as, int AD_Org_ID)
	{
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(product);
		
		String sql = "SELECT t.MovementQty, mi.Qty, il.QtyInvoiced, il.PriceActual,"
			+ " i.C_Currency_ID, i.DateAcct, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID, t.M_Transaction_ID "
			+ "FROM M_Transaction t"
			+ " INNER JOIN M_MatchInv mi ON (t.M_InOutLine_ID=mi.M_InOutLine_ID)"
			+ " INNER JOIN C_InvoiceLine il ON (mi.C_InvoiceLine_ID=il.C_InvoiceLine_ID)"
			+ " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID) "
			+ "WHERE t.M_Product_ID=?";
		if (AD_Org_ID != 0)
			sql += " AND t.AD_Org_ID=?";
		else if (M_AttributeSetInstance_ID != 0)
			sql += " AND t.M_AttributeSetInstance_ID=?";
		//	Starting point?
		sql += " ORDER BY t.M_Transaction_ID DESC";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//
		int oldTransaction_ID = 0;
		ArrayList<QtyCost> lifo = new ArrayList<QtyCost>();
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, product.getM_Product_ID());
			if (AD_Org_ID != 0)
				pstmt.setInt (2, AD_Org_ID);
			else if (M_AttributeSetInstance_ID != 0)
				pstmt.setInt (2, M_AttributeSetInstance_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				BigDecimal movementQty = rs.getBigDecimal(1);
				int M_Transaction_ID = rs.getInt(10);
				if (M_Transaction_ID == oldTransaction_ID)
					continue;	//	assuming same price for receipt
				M_Transaction_ID = oldTransaction_ID;
				//
				BigDecimal matchQty = rs.getBigDecimal(2);
				if (matchQty == null)	//	out (negative)
				{
					if (lifo.size() > 0)
					{
						QtyCost pp = (QtyCost)lifo.get(lifo.size()-1);
						pp.Qty = pp.Qty.add(movementQty);
						BigDecimal remainder = pp.Qty;
						if (remainder.signum() == 0)
							lifo.remove(lifo.size()-1);
						else
						{
							while (remainder.signum() != 0)
							{
								if (lifo.size() == 1)	//	Last
								{
									pp.Cost = Env.ZERO;
									remainder = Env.ZERO;
								}
								else
								{
									lifo.remove(lifo.size()-1);
									pp = (QtyCost)lifo.get(lifo.size()-1);
									pp.Qty = pp.Qty.add(movementQty);
									remainder = pp.Qty;
								}
							}
						}
					}
					else
					{
						QtyCost pp = new QtyCost (movementQty, Env.ZERO);
						lifo.add(pp);
					}
					s_log.trace("Movement=" + movementQty + ", Size=" + lifo.size());
					continue;
				}
				//	Assumption: everything is matched
				BigDecimal price = rs.getBigDecimal(4);
				int C_Currency_ID = rs.getInt(5);
				Timestamp DateAcct = rs.getTimestamp(6);
				int C_ConversionType_ID = rs.getInt(7);
				int Client_ID = rs.getInt(8);
				int Org_ID = rs.getInt(9);
				BigDecimal cost = currencyConversionBL.convert(ctx, price,
					C_Currency_ID, as.getC_Currency_ID(),
					DateAcct, C_ConversionType_ID, Client_ID, Org_ID);
				//
				QtyCost pp = new QtyCost (movementQty, cost);
				lifo.add(pp);
				s_log.trace("Movement=" + movementQty + ", Size=" + lifo.size());
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		if (lifo.size() == 0)
		{
			return null;
		}
		QtyCost pp = (QtyCost)lifo.get(lifo.size()-1);
		s_log.trace(product.getName() + " = " + pp.Cost);
		return pp.Cost;
	}	//	calculateLiFo


	/**************************************************************************
	 *	MCost Qty-Cost Pair
	 */
	public static class QtyCost
	{
		/**
		 * 	Constructor
		 *	@param qty qty
		 *	@param cost cost
		 */
		public QtyCost (BigDecimal qty, BigDecimal cost)
		{
			Qty = qty;
			Cost = cost;
		}
		/** Qty		*/
		public BigDecimal	Qty = null;
		/** Cost	*/
		public BigDecimal	Cost = null;

		/**
		 * 	String Representation
		 *	@return info
		 */
		@Override
		public String toString ()
		{
			StringBuffer sb = new StringBuffer ("Qty=").append(Qty)
				.append (",Cost=").append (Cost);
			return sb.toString ();
		}	//	toString
	}	//	QtyCost


	/**
	 * 	Get/Create Cost Record.
	 * 	CostingLevel is not validated
	 *	@param product product
	 *	@param M_AttributeSetInstance_ID costing level asi
	 *	@param as accounting schema
	 *	@param AD_Org_ID costing level org
	 *	@param M_CostElement_ID element
	 *	@return cost price or null
	 */
	public static MCost get (I_M_Product product, int M_AttributeSetInstance_ID,
		I_C_AcctSchema as, int AD_Org_ID, int M_CostElement_ID, String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(product);
		
		MCost cost = null;
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		String whereClause = "AD_Client_ID=? AND AD_Org_ID=?"
			+ " AND M_Product_ID=?"
			+ " AND M_AttributeSetInstance_ID=?"
			+ " AND M_CostType_ID=? AND C_AcctSchema_ID=?"
			+ " AND M_CostElement_ID=?";
		cost = new Query(ctx, MCost.Table_Name, whereClause, trxName)
		.setParameters(new Object[]{
						product.getAD_Client_ID(),
						AD_Org_ID,
						product.getM_Product_ID(),
						M_AttributeSetInstance_ID,
						as.getM_CostType_ID(),
						as.getC_AcctSchema_ID(),
						M_CostElement_ID})
		.firstOnly();
		//FR: [ 2214883 ] - end -
		//	New
		if (cost == null)
			cost = new MCost (product, M_AttributeSetInstance_ID,
				as, AD_Org_ID, M_CostElement_ID);
		return cost;
	}	//	get

	/**
	 * Get Cost Record
	 * @param ctx context
	 * @param AD_Client_ID client
	 * @param AD_Org_ID org
	 * @param M_Product_ID product
	 * @param M_CostType_ID cost type
	 * @param C_AcctSchema_ID as
	 * @param M_CostElement_ID cost element
	 * @param M_AttributeSetInstance_ID asi
	 * @param trxName transaction name
	 * @return cost or null
	 */
	public static MCost get (Properties ctx, int AD_Client_ID, int AD_Org_ID, int M_Product_ID,
		int M_CostType_ID, int C_AcctSchema_ID, int M_CostElement_ID,
		int M_AttributeSetInstance_ID,
		String trxName)
	{
		final String whereClause = "AD_Client_ID=? AND AD_Org_ID=?"
									+" AND "+COLUMNNAME_M_Product_ID+"=?"
									+" AND "+COLUMNNAME_M_CostType_ID+"=?"
									+" AND "+COLUMNNAME_C_AcctSchema_ID+"=?"
									+" AND "+COLUMNNAME_M_CostElement_ID+"=?"
									+" AND "+COLUMNNAME_M_AttributeSetInstance_ID+"=?";
		final Object[] params = new Object[]{AD_Client_ID, AD_Org_ID, M_Product_ID,
												M_CostType_ID, C_AcctSchema_ID,
												M_CostElement_ID, M_AttributeSetInstance_ID};
		return new Query(ctx, Table_Name, whereClause, trxName)
					.setOnlyActiveRecords(true)
					.setParameters(params)
					.firstOnly();
	}	//	get

	/**	Logger	*/
	private static Logger 	s_log = LogManager.getLogger(MCost.class);


	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param ignored multi-key
	 *	@param trxName trx
	 */
	public MCost (Properties ctx, int ignored, String trxName)
	{
		super (ctx, ignored, trxName);
		if (ignored == 0)
		{
		//	setC_AcctSchema_ID (0);
		//	setM_CostElement_ID (0);
		//	setM_CostType_ID (0);
		//	setM_Product_ID (0);
			setM_AttributeSetInstance_ID(0);
			//
			setCurrentCostPrice (Env.ZERO);
			setFutureCostPrice (Env.ZERO);
			setCurrentQty (Env.ZERO);
			setCumulatedAmt (Env.ZERO);
			setCumulatedQty (Env.ZERO);
		}
		else
			throw new IllegalArgumentException("Multi-Key");
	}	//	MCost

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MCost (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
		m_manual = false;
	}	//	MCost

	/**
	 * 	Parent Constructor
	 *	@param product Product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param as Acct Schema
	 *	@param AD_Org_ID org
	 *	@param M_CostElement_ID cost element
	 */
	public MCost(I_M_Product product,
			int M_AttributeSetInstance_ID,
			I_C_AcctSchema as,
			int AD_Org_ID,
			int M_CostElement_ID)
	{
		this (InterfaceWrapperHelper.getCtx(product)
				, 0,
				InterfaceWrapperHelper.getTrxName(product));
		setClientOrg(product.getAD_Client_ID(), AD_Org_ID);
		setC_AcctSchema_ID(as.getC_AcctSchema_ID());
		setM_CostType_ID(as.getM_CostType_ID());
		setM_Product_ID(product.getM_Product_ID());
		setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		setM_CostElement_ID(M_CostElement_ID);
		//
		m_manual = false;
	}	//	MCost

	/** Data is entered Manually		*/
	private boolean m_manual = true;

	/**
	 * 	Add Cumulative Amt/Qty and Current Qty
	 *	@param amt amt
	 *	@param qty qty
	 */
	public void add (BigDecimal amt, BigDecimal qty)
	{
		setCumulatedAmt(getCumulatedAmt().add(amt));
		setCumulatedQty(getCumulatedQty().add(qty));
		setCurrentQty(getCurrentQty().add(qty));
	}	//	add

	/**
	 * 	Add Amt/Qty and calculate weighted average.
	 * 	((OldAvg*OldQty)+(Price*Qty)) / (OldQty+Qty)
	 *	@param amt total amt (price * qty)
	 *	@param qty qty
	 */
	public void setWeightedAverage (BigDecimal amt, BigDecimal qty)
	{
		BigDecimal oldSum = getCurrentCostPrice().multiply(getCurrentQty());
		BigDecimal newSum = amt;	//	is total already
		BigDecimal sumAmt = oldSum.add(newSum);
		BigDecimal sumQty = getCurrentQty().add(qty);
		if (sumQty.signum() != 0)
		{
			BigDecimal cost = sumAmt.divide(sumQty, getPrecision(), BigDecimal.ROUND_HALF_UP);
			setCurrentCostPrice(cost);
		}
		//
		setCumulatedAmt(getCumulatedAmt().add(amt));
		setCumulatedQty(getCumulatedQty().add(qty));
		setCurrentQty(getCurrentQty().add(qty));
	}	//	setWeightedAverage

	/**
	 * 	Get Costing Precision
	 *	@return precision (6)
	 */
	private int getPrecision()
	{
		MAcctSchema as = MAcctSchema.get(getCtx(), getC_AcctSchema_ID());
		if (as != null)
			return as.getCostingPrecision();
		return 6;
	}	//	gerPrecision

	/**
	 * 	Set Current Cost Price
	 *	@param currentCostPrice if null set to 0
	 */
	@Override
	public void setCurrentCostPrice (BigDecimal currentCostPrice)
	{
		if (currentCostPrice != null)
			super.setCurrentCostPrice (currentCostPrice);
		else
			super.setCurrentCostPrice (Env.ZERO);
	}	//	setCurrentCostPrice

	/**
	 * 	Get History Average (Amt/Qty)
	 *	@return average if amt/aty <> 0 otherwise null
	 */
	public BigDecimal getHistoryAverage()
	{
		BigDecimal retValue = null;
		if (getCumulatedQty().signum() != 0
			&& getCumulatedAmt().signum() != 0)
			retValue = getCumulatedAmt()
				.divide(getCumulatedQty(), getPrecision(), BigDecimal.ROUND_HALF_UP);
		return retValue;
	}	//	getHistoryAverage

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MCost[");
		sb.append ("AD_Client_ID=").append (getAD_Client_ID());
		if (getAD_Org_ID() != 0)
			sb.append (",AD_Org_ID=").append (getAD_Org_ID());
		sb.append (",M_Product_ID=").append (getM_Product_ID());
		if (getM_AttributeSetInstance_ID() != 0)
			sb.append (",AD_ASI_ID=").append (getM_AttributeSetInstance_ID());
	//	sb.append (",C_AcctSchema_ID=").append (getC_AcctSchema_ID());
	//	sb.append (",M_CostType_ID=").append (getM_CostType_ID());
		sb.append (",M_CostElement_ID=").append (getM_CostElement_ID());
		//
		sb.append (", CurrentCost=").append (getCurrentCostPrice())
			.append (", C.Amt=").append (getCumulatedAmt())
			.append (",C.Qty=").append (getCumulatedQty())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Get Cost Element
	 *	@return cost element
	 */
	public MCostElement getCostElement()
	{
		int M_CostElement_ID = getM_CostElement_ID();
		if (M_CostElement_ID == 0)
			return null;
		return MCostElement.get(getCtx(), M_CostElement_ID);
	}	//	getCostElement

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true if can be saved
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		//The method getCostElement() not should be cached because is a transaction
		//MCostElement ce = getCostElement();
		MCostElement ce = (MCostElement)getM_CostElement();
		//	Check if data entry makes sense
		if (m_manual)
		{
			MAcctSchema as = new MAcctSchema (getCtx(), getC_AcctSchema_ID(), null);
			MProduct product = MProduct.get(getCtx(), getM_Product_ID());
			String CostingLevel = Services.get(IProductBL.class).getCostingLevel(product, as);
			if (MAcctSchema.COSTINGLEVEL_Client.equals(CostingLevel))
			{
				if (getAD_Org_ID() != 0 || getM_AttributeSetInstance_ID() != 0)
				{
					throw new AdempiereException("@CostingLevelClient@");
				}
			}
			else if (MAcctSchema.COSTINGLEVEL_BatchLot.equals(CostingLevel))
			{
				if (getM_AttributeSetInstance_ID() == 0
					&& ce.isCostingMethod())
				{
					throw new FillMandatoryException(I_M_Cost.COLUMNNAME_M_AttributeSetInstance_ID);
				}
				if (getAD_Org_ID() != 0)
				{
					setAD_Org_ID(0);
				}
			}
		}

		//	Cannot enter calculated
		if (m_manual && ce != null && ce.isCalculated())
		{
			throw new AdempiereException("@IsCalculated@");
		}
		//	Percentage
		if (ce != null)
		{
			if (ce.isCalculated()
				|| MCostElement.COSTELEMENTTYPE_Material.equals(ce.getCostElementType())
				&& getPercent() != 0)
				setPercent(0);
		}
		if (getPercent() != 0)
		{
			if (getCurrentCostPrice().signum() != 0)
				setCurrentCostPrice(Env.ZERO);
			if (getFutureCostPrice().signum() != 0)
				setFutureCostPrice(Env.ZERO);
			if (getCumulatedAmt().signum() != 0)
				setCumulatedAmt(Env.ZERO);
			if (getCumulatedQty().signum() != 0)
				setCumulatedQty(Env.ZERO);
		}
		return true;
	}	//	beforeSave


	/**
	 * 	Before Delete
	 *	@return true
	 */
	@Override
	protected boolean beforeDelete ()
	{
		return true;
	}	//	beforeDelete
}	//	MCost
