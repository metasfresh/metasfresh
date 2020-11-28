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

/** Generated Model for W_BasketLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_W_BasketLine extends PO implements I_W_BasketLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_W_BasketLine (Properties ctx, int W_BasketLine_ID, String trxName)
    {
      super (ctx, W_BasketLine_ID, trxName);
      /** if (W_BasketLine_ID == 0)
        {
			setDescription (null);
			setLine (0);
			setPrice (Env.ZERO);
			setProduct (null);
			setQty (Env.ZERO);
			setW_Basket_ID (0);
			setW_BasketLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_W_BasketLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_W_BasketLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getLine()));
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
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** Set Price.
		@param Price 
		Price
	  */
	public void setPrice (BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Price.
		@return Price
	  */
	public BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Product.
		@param Product Product	  */
	public void setProduct (String Product)
	{
		set_Value (COLUMNNAME_Product, Product);
	}

	/** Get Product.
		@return Product	  */
	public String getProduct () 
	{
		return (String)get_Value(COLUMNNAME_Product);
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

	public I_W_Basket getW_Basket() throws RuntimeException
    {
		return (I_W_Basket)MTable.get(getCtx(), I_W_Basket.Table_Name)
			.getPO(getW_Basket_ID(), get_TrxName());	}

	/** Set W_Basket_ID.
		@param W_Basket_ID 
		Web Basket
	  */
	public void setW_Basket_ID (int W_Basket_ID)
	{
		if (W_Basket_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_W_Basket_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_W_Basket_ID, Integer.valueOf(W_Basket_ID));
	}

	/** Get W_Basket_ID.
		@return Web Basket
	  */
	public int getW_Basket_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Basket_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Basket Line.
		@param W_BasketLine_ID 
		Web Basket Line
	  */
	public void setW_BasketLine_ID (int W_BasketLine_ID)
	{
		if (W_BasketLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_W_BasketLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_W_BasketLine_ID, Integer.valueOf(W_BasketLine_ID));
	}

	/** Get Basket Line.
		@return Web Basket Line
	  */
	public int getW_BasketLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_BasketLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}