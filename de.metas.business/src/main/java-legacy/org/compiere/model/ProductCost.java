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
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.acct.api.IAccountDAO;
import org.adempiere.acct.api.ProductAcctType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.acct.api.IProductAcctDAO;

/**
 * Product Cost Model. Summarizes Info in MCost
 *
 * @author Jorg Janke
 * @version $Id: ProductCost.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class ProductCost
{
	// services
	private static final transient Logger log = LogManager.getLogger(ProductCost.class);
	private final transient IProductAcctDAO productAcctDAO = Services.get(IProductAcctDAO.class);
	private final transient IAccountDAO accountDAO = Services.get(IAccountDAO.class);

	/**
	 * Constructor
	 *
	 * @param ctx context
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID asi
	 * @param trxName trx
	 */
	public ProductCost(Properties ctx, int M_Product_ID, int M_AttributeSetInstance_ID, String trxName)
	{
		m_M_Product_ID = M_Product_ID;
		if (m_M_Product_ID > 0)
		{
			m_product = MProduct.get(ctx, M_Product_ID);
		}
		else
		{
			m_product = null;
		}
		
		m_M_AttributeSetInstance_ID = M_AttributeSetInstance_ID;
		m_trxName = trxName;
	}	// ProductCost

	/** The ID */
	private final int m_M_Product_ID;
	/** ASI */
	private final int m_M_AttributeSetInstance_ID;
	/** The Product */
	private final MProduct m_product;
	/** Transaction */
	private final String m_trxName;

	private int m_C_UOM_ID = 0;
	private BigDecimal m_qty = BigDecimal.ZERO;

	/**
	 * Get Product
	 * 
	 * @return Product might be null
	 */
	public MProduct getProduct()
	{
		return m_product;
	}   // getProduct

	/**
	 * Is this a Service
	 *
	 * @return true if service
	 */
	public boolean isService()
	{
		if (m_product != null)
		{
			return !Services.get(IProductBL.class).isItem(m_product);
		}
		return false;
	}	// isService

	/**
	 * Set Quantity in Storage UOM
	 * 
	 * @param qty quantity
	 */
	public void setQty(BigDecimal qty)
	{
		m_qty = qty;
	}   // setQty

	/**
	 * Set Quantity in UOM
	 * 
	 * @param qty quantity
	 * @param C_UOM_ID UOM
	 */
	public void setQty(BigDecimal qty, int C_UOM_ID)
	{
		m_qty = MUOMConversion.convert(C_UOM_ID, m_C_UOM_ID, qty, true);    // StdPrecision
		if (qty != null && m_qty == null)   // conversion error
		{
			log.error("Conversion error - set to " + qty);
			m_qty = qty;
		}
		else
			m_C_UOM_ID = C_UOM_ID;
	}   // setQty

	/** Product Revenue Acct */
	public static final ProductAcctType ACCTTYPE_P_Revenue = ProductAcctType.Revenue;
	/** Product Expense Acct */
	public static final ProductAcctType ACCTTYPE_P_Expense = ProductAcctType.Expense;
	/** Product Asset Acct */
	public static final ProductAcctType ACCTTYPE_P_Asset = ProductAcctType.Asset;
	/** Product COGS Acct */
	public static final ProductAcctType ACCTTYPE_P_Cogs = ProductAcctType.Cogs;
	/** Purchase Price Variance */
	public static final ProductAcctType ACCTTYPE_P_PPV = ProductAcctType.PPV;
	/** Invoice Price Variance */
	public static final ProductAcctType ACCTTYPE_P_IPV = ProductAcctType.IPV;
	/** Trade Discount Revenue */
	public static final ProductAcctType ACCTTYPE_P_TDiscountRec = ProductAcctType.TDiscountRec;
	/** Trade Discount Costs */
	public static final ProductAcctType ACCTTYPE_P_TDiscountGrant = ProductAcctType.TDiscountGrant;
	/** Cost Adjustment */
	public static final ProductAcctType ACCTTYPE_P_CostAdjustment = ProductAcctType.CostAdjustment;
	/** Inventory Clearing */
	public static final ProductAcctType ACCTTYPE_P_InventoryClearing = ProductAcctType.InventoryClearing;
	/** Work in Process */
	public static final ProductAcctType ACCTTYPE_P_WorkInProcess = ProductAcctType.WorkInProcess;
	/** Method Change Variance */
	public static final ProductAcctType ACCTTYPE_P_MethodChangeVariance = ProductAcctType.MethodChangeVariance;
	/** Material Usage Variance */
	public static final ProductAcctType ACCTTYPE_P_UsageVariance = ProductAcctType.UsageVariance;
	/** Material Rate Variance */
	public static final ProductAcctType ACCTTYPE_P_RateVariance = ProductAcctType.RateVariance;
	/** Mix Variance */
	public static final ProductAcctType ACCTTYPE_P_MixVariance = ProductAcctType.MixVariance;
	/** Floor Stock */
	public static final ProductAcctType ACCTTYPE_P_FloorStock = ProductAcctType.FloorStock;
	/** Cost Production */
	public static final ProductAcctType ACCTTYPE_P_CostOfProduction = ProductAcctType.CostOfProduction;
	/** Labor */
	public static final ProductAcctType ACCTTYPE_P_Labor = ProductAcctType.Labor;
	/** Burden */
	public static final ProductAcctType ACCTTYPE_P_Burden = ProductAcctType.Burden;
	/** Outside Processing */
	public static final ProductAcctType ACCTTYPE_P_OutsideProcessing = ProductAcctType.OutsideProcessing;
	/** Outside Overhead */
	public static final ProductAcctType ACCTTYPE_P_Overhead = ProductAcctType.Overhead;
	/** Outside Processing */
	public static final ProductAcctType ACCTTYPE_P_Scrap = ProductAcctType.Scrap;

	/**
	 * Line Account from Product
	 *
	 * @param AcctType see ACCTTYPE_* (1..8)
	 * @param as Accounting Schema
	 * @return Requested Product Account
	 */
	public MAccount getAccount(final ProductAcctType acctType, final MAcctSchema as)
	{
		// No Product - get Default from Product Category
		if (m_M_Product_ID <= 0)
		{
			return getAccountDefault(acctType, as);
		}

		final I_M_Product_Acct productAcct = productAcctDAO.retrieveProductAcctOrNull(as, m_M_Product_ID);
		Check.assumeNotNull(productAcct, "Product {} has accounting records", m_M_Product_ID);
		final Integer validCombinationId = InterfaceWrapperHelper.getValueOrNull(productAcct, acctType.getColumnName());
		if(validCombinationId == null || validCombinationId <= 0)
		{
			return null;
		}
		
		return accountDAO.retrieveAccountById(as.getCtx(), validCombinationId);
	}   // getAccount

	/**
	 * Account from Default Product Category
	 *
	 * @param AcctType see ACCTTYPE_* (1..8)
	 * @param as accounting schema
	 * @return Requested Product Account
	 */
	private final MAccount getAccountDefault(final ProductAcctType acctType, final MAcctSchema as)
	{
		final I_M_Product_Category_Acct pcAcct = productAcctDAO.retrieveDefaultProductCategoryAcct(as);
		final Integer validCombinationId = InterfaceWrapperHelper.getValueOrNull(pcAcct, acctType.getColumnName());
		if(validCombinationId == null || validCombinationId <= 0)
		{
			return null;
		}
		
		return accountDAO.retrieveAccountById(as.getCtx(), validCombinationId);
	}   // getAccountDefault

	/**************************************************************************
	 * Get Total Costs (amt*qty) in Accounting Schema Currency
	 * 
	 * @param as accounting schema
	 * @param AD_Org_ID trx org
	 * @param costingMethod if null uses Accounting Schema - AcctSchema.COSTINGMETHOD_*
	 * @param C_OrderLine_ID optional order line
	 * @param zeroCostsOK zero/no costs are OK
	 * @return cost or null, if qty or costs cannot be determined
	 */
	public BigDecimal getProductCosts(MAcctSchema as, int AD_Org_ID, String costingMethod, int C_OrderLine_ID, boolean zeroCostsOK)
	{
		if (m_qty == null)
		{
			log.debug("No Qty");
			return null;
		}

		// No Product
		if (m_product == null)
		{
			log.debug("No Product");
			return null;
		}
		//
		BigDecimal cost = MCost.getCurrentCost(m_product, m_M_AttributeSetInstance_ID,
				as, AD_Org_ID, costingMethod, m_qty, C_OrderLine_ID, zeroCostsOK, m_trxName);
		if (cost == null)
		{
			log.debug("No Costs");
			return null;
		}
		return cost;
	}   // getProductCosts

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("ProductCost[");
		sb.append("M_Product_ID=").append(m_M_Product_ID)
				.append(",M_AttributeSetInstance_ID").append(m_M_AttributeSetInstance_ID)
				.append(",Qty=").append(m_qty)
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Checks if given <code>costs</code> represent no costs.
	 * @param costs
	 * @return true if no costs
	 */
	public static boolean isNoCosts(final BigDecimal costs)
	{
		if (costs == null)
		{
			return true;
		}

		// NOTE: metas-tsa: zero costs could be OK if they were specifically set (i.e. standard costing with M_Cost.CurrentCostPrice=ZERO)
		// if (costs.signum() == 0)
		// {
		// return true;
		// }

		return false;
	}
}	// ProductCost
