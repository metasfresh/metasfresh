/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_Product_Costing
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_Product_Costing extends PO implements I_M_Product_Costing, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_M_Product_Costing (Properties ctx, int M_Product_Costing_ID, String trxName)
    {
      super (ctx, M_Product_Costing_ID, trxName);
      /** if (M_Product_Costing_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setCostAverage (Env.ZERO);
			setCostAverageCumAmt (Env.ZERO);
			setCostAverageCumQty (Env.ZERO);
			setCostStandard (Env.ZERO);
			setCostStandardCumAmt (Env.ZERO);
			setCostStandardCumQty (Env.ZERO);
			setCostStandardPOAmt (Env.ZERO);
			setCostStandardPOQty (Env.ZERO);
			setCurrentCostPrice (Env.ZERO);
			setFutureCostPrice (Env.ZERO);
			setM_Product_ID (0);
			setPriceLastInv (Env.ZERO);
			setPriceLastPO (Env.ZERO);
			setTotalInvAmt (Env.ZERO);
			setTotalInvQty (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_Product_Costing (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_M_Product_Costing[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_AcctSchema getC_AcctSchema() throws RuntimeException
    {
		return (I_C_AcctSchema)MTable.get(getCtx(), I_C_AcctSchema.Table_Name)
			.getPO(getC_AcctSchema_ID(), get_TrxName());	}

	/** Set Accounting Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Accounting Schema.
		@return Rules for accounting
	  */
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Average Cost.
		@param CostAverage 
		Weighted average costs
	  */
	public void setCostAverage (BigDecimal CostAverage)
	{
		set_ValueNoCheck (COLUMNNAME_CostAverage, CostAverage);
	}

	/** Get Average Cost.
		@return Weighted average costs
	  */
	public BigDecimal getCostAverage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostAverage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Average Cost Amount Sum.
		@param CostAverageCumAmt 
		Cumulative average cost amounts (internal)
	  */
	public void setCostAverageCumAmt (BigDecimal CostAverageCumAmt)
	{
		set_ValueNoCheck (COLUMNNAME_CostAverageCumAmt, CostAverageCumAmt);
	}

	/** Get Average Cost Amount Sum.
		@return Cumulative average cost amounts (internal)
	  */
	public BigDecimal getCostAverageCumAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostAverageCumAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Average Cost Quantity Sum.
		@param CostAverageCumQty 
		Cumulative average cost quantities (internal)
	  */
	public void setCostAverageCumQty (BigDecimal CostAverageCumQty)
	{
		set_ValueNoCheck (COLUMNNAME_CostAverageCumQty, CostAverageCumQty);
	}

	/** Get Average Cost Quantity Sum.
		@return Cumulative average cost quantities (internal)
	  */
	public BigDecimal getCostAverageCumQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostAverageCumQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Standard Cost.
		@param CostStandard 
		Standard Costs
	  */
	public void setCostStandard (BigDecimal CostStandard)
	{
		set_ValueNoCheck (COLUMNNAME_CostStandard, CostStandard);
	}

	/** Get Standard Cost.
		@return Standard Costs
	  */
	public BigDecimal getCostStandard () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostStandard);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Std Cost Amount Sum.
		@param CostStandardCumAmt 
		Standard Cost Invoice Amount Sum (internal)
	  */
	public void setCostStandardCumAmt (BigDecimal CostStandardCumAmt)
	{
		set_ValueNoCheck (COLUMNNAME_CostStandardCumAmt, CostStandardCumAmt);
	}

	/** Get Std Cost Amount Sum.
		@return Standard Cost Invoice Amount Sum (internal)
	  */
	public BigDecimal getCostStandardCumAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostStandardCumAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Std Cost Quantity Sum.
		@param CostStandardCumQty 
		Standard Cost Invoice Quantity Sum (internal)
	  */
	public void setCostStandardCumQty (BigDecimal CostStandardCumQty)
	{
		set_ValueNoCheck (COLUMNNAME_CostStandardCumQty, CostStandardCumQty);
	}

	/** Get Std Cost Quantity Sum.
		@return Standard Cost Invoice Quantity Sum (internal)
	  */
	public BigDecimal getCostStandardCumQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostStandardCumQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Std PO Cost Amount Sum.
		@param CostStandardPOAmt 
		Standard Cost Purchase Order Amount Sum (internal)
	  */
	public void setCostStandardPOAmt (BigDecimal CostStandardPOAmt)
	{
		set_ValueNoCheck (COLUMNNAME_CostStandardPOAmt, CostStandardPOAmt);
	}

	/** Get Std PO Cost Amount Sum.
		@return Standard Cost Purchase Order Amount Sum (internal)
	  */
	public BigDecimal getCostStandardPOAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostStandardPOAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Std PO Cost Quantity Sum.
		@param CostStandardPOQty 
		Standard Cost Purchase Order Quantity Sum (internal)
	  */
	public void setCostStandardPOQty (BigDecimal CostStandardPOQty)
	{
		set_ValueNoCheck (COLUMNNAME_CostStandardPOQty, CostStandardPOQty);
	}

	/** Get Std PO Cost Quantity Sum.
		@return Standard Cost Purchase Order Quantity Sum (internal)
	  */
	public BigDecimal getCostStandardPOQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostStandardPOQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Current Cost Price.
		@param CurrentCostPrice 
		The currently used cost price
	  */
	public void setCurrentCostPrice (BigDecimal CurrentCostPrice)
	{
		set_Value (COLUMNNAME_CurrentCostPrice, CurrentCostPrice);
	}

	/** Get Current Cost Price.
		@return The currently used cost price
	  */
	public BigDecimal getCurrentCostPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrentCostPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Future Cost Price.
		@param FutureCostPrice Future Cost Price	  */
	public void setFutureCostPrice (BigDecimal FutureCostPrice)
	{
		set_Value (COLUMNNAME_FutureCostPrice, FutureCostPrice);
	}

	/** Get Future Cost Price.
		@return Future Cost Price	  */
	public BigDecimal getFutureCostPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FutureCostPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Last Invoice Price.
		@param PriceLastInv 
		Price of the last invoice for the product
	  */
	public void setPriceLastInv (BigDecimal PriceLastInv)
	{
		set_ValueNoCheck (COLUMNNAME_PriceLastInv, PriceLastInv);
	}

	/** Get Last Invoice Price.
		@return Price of the last invoice for the product
	  */
	public BigDecimal getPriceLastInv () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLastInv);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Last PO Price.
		@param PriceLastPO 
		Price of the last purchase order for the product
	  */
	public void setPriceLastPO (BigDecimal PriceLastPO)
	{
		set_ValueNoCheck (COLUMNNAME_PriceLastPO, PriceLastPO);
	}

	/** Get Last PO Price.
		@return Price of the last purchase order for the product
	  */
	public BigDecimal getPriceLastPO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLastPO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Invoice Amount.
		@param TotalInvAmt 
		Cumulative total lifetime invoice amount
	  */
	public void setTotalInvAmt (BigDecimal TotalInvAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TotalInvAmt, TotalInvAmt);
	}

	/** Get Total Invoice Amount.
		@return Cumulative total lifetime invoice amount
	  */
	public BigDecimal getTotalInvAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalInvAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Invoice Quantity.
		@param TotalInvQty 
		Cumulative total lifetime invoice quantity
	  */
	public void setTotalInvQty (BigDecimal TotalInvQty)
	{
		set_ValueNoCheck (COLUMNNAME_TotalInvQty, TotalInvQty);
	}

	/** Get Total Invoice Quantity.
		@return Cumulative total lifetime invoice quantity
	  */
	public BigDecimal getTotalInvQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalInvQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}