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
import org.compiere.util.KeyNamePair;

/** Generated Model for C_RfQLineQty
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_RfQLineQty extends PO implements I_C_RfQLineQty, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_RfQLineQty (Properties ctx, int C_RfQLineQty_ID, String trxName)
    {
      super (ctx, C_RfQLineQty_ID, trxName);
      /** if (C_RfQLineQty_ID == 0)
        {
			setBenchmarkPrice (Env.ZERO);
			setC_RfQLine_ID (0);
			setC_RfQLineQty_ID (0);
			setC_UOM_ID (0);
			setIsOfferQty (false);
			setIsPurchaseQty (false);
			setIsRfQQty (true);
// Y
			setQty (Env.ZERO);
// 1
        } */
    }

    /** Load Constructor */
    public X_C_RfQLineQty (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_C_RfQLineQty[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Benchmark Price.
		@param BenchmarkPrice 
		Price to compare responses to
	  */
	public void setBenchmarkPrice (BigDecimal BenchmarkPrice)
	{
		set_Value (COLUMNNAME_BenchmarkPrice, BenchmarkPrice);
	}

	/** Get Benchmark Price.
		@return Price to compare responses to
	  */
	public BigDecimal getBenchmarkPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BenchmarkPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Best Response Amount.
		@param BestResponseAmt 
		Best Response Amount
	  */
	public void setBestResponseAmt (BigDecimal BestResponseAmt)
	{
		set_Value (COLUMNNAME_BestResponseAmt, BestResponseAmt);
	}

	/** Get Best Response Amount.
		@return Best Response Amount
	  */
	public BigDecimal getBestResponseAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BestResponseAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_RfQLine getC_RfQLine() throws RuntimeException
    {
		return (I_C_RfQLine)MTable.get(getCtx(), I_C_RfQLine.Table_Name)
			.getPO(getC_RfQLine_ID(), get_TrxName());	}

	/** Set RfQ Line.
		@param C_RfQLine_ID 
		Request for Quotation Line
	  */
	public void setC_RfQLine_ID (int C_RfQLine_ID)
	{
		if (C_RfQLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQLine_ID, Integer.valueOf(C_RfQLine_ID));
	}

	/** Get RfQ Line.
		@return Request for Quotation Line
	  */
	public int getC_RfQLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RfQ Line Quantity.
		@param C_RfQLineQty_ID 
		Request for Quotation Line Quantity
	  */
	public void setC_RfQLineQty_ID (int C_RfQLineQty_ID)
	{
		if (C_RfQLineQty_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQLineQty_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQLineQty_ID, Integer.valueOf(C_RfQLineQty_ID));
	}

	/** Get RfQ Line Quantity.
		@return Request for Quotation Line Quantity
	  */
	public int getC_RfQLineQty_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQLineQty_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_UOM getC_UOM() throws RuntimeException
    {
		return (I_C_UOM)MTable.get(getCtx(), I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_UOM_ID()));
    }

	/** Set Offer Quantity.
		@param IsOfferQty 
		This quantity is used in the Offer to the Customer
	  */
	public void setIsOfferQty (boolean IsOfferQty)
	{
		set_Value (COLUMNNAME_IsOfferQty, Boolean.valueOf(IsOfferQty));
	}

	/** Get Offer Quantity.
		@return This quantity is used in the Offer to the Customer
	  */
	public boolean isOfferQty () 
	{
		Object oo = get_Value(COLUMNNAME_IsOfferQty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Purchase Quantity.
		@param IsPurchaseQty 
		This quantity is used in the Purchase Order to the Supplier
	  */
	public void setIsPurchaseQty (boolean IsPurchaseQty)
	{
		set_Value (COLUMNNAME_IsPurchaseQty, Boolean.valueOf(IsPurchaseQty));
	}

	/** Get Purchase Quantity.
		@return This quantity is used in the Purchase Order to the Supplier
	  */
	public boolean isPurchaseQty () 
	{
		Object oo = get_Value(COLUMNNAME_IsPurchaseQty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set RfQ Quantity.
		@param IsRfQQty 
		The quantity is used when generating RfQ Responses
	  */
	public void setIsRfQQty (boolean IsRfQQty)
	{
		set_Value (COLUMNNAME_IsRfQQty, Boolean.valueOf(IsRfQQty));
	}

	/** Get RfQ Quantity.
		@return The quantity is used when generating RfQ Responses
	  */
	public boolean isRfQQty () 
	{
		Object oo = get_Value(COLUMNNAME_IsRfQQty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Margin %.
		@param Margin 
		Margin for a product as a percentage
	  */
	public void setMargin (BigDecimal Margin)
	{
		set_Value (COLUMNNAME_Margin, Margin);
	}

	/** Get Margin %.
		@return Margin for a product as a percentage
	  */
	public BigDecimal getMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Margin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Offer Amount.
		@param OfferAmt 
		Amount of the Offer
	  */
	public void setOfferAmt (BigDecimal OfferAmt)
	{
		set_Value (COLUMNNAME_OfferAmt, OfferAmt);
	}

	/** Get Offer Amount.
		@return Amount of the Offer
	  */
	public BigDecimal getOfferAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OfferAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}