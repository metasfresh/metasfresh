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

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_PromotionGroupLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_PromotionGroupLine extends PO implements I_M_PromotionGroupLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_M_PromotionGroupLine (Properties ctx, int M_PromotionGroupLine_ID, String trxName)
    {
      super (ctx, M_PromotionGroupLine_ID, trxName);
      /** if (M_PromotionGroupLine_ID == 0)
        {
			setM_Product_ID (0);
			setM_PromotionGroup_ID (0);
			setM_PromotionGroupLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_PromotionGroupLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_PromotionGroupLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_M_PromotionGroup getM_PromotionGroup() throws RuntimeException
    {
		return (I_M_PromotionGroup)MTable.get(getCtx(), I_M_PromotionGroup.Table_Name)
			.getPO(getM_PromotionGroup_ID(), get_TrxName());	}

	/** Set Promotion Group.
		@param M_PromotionGroup_ID Promotion Group	  */
	public void setM_PromotionGroup_ID (int M_PromotionGroup_ID)
	{
		if (M_PromotionGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PromotionGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PromotionGroup_ID, Integer.valueOf(M_PromotionGroup_ID));
	}

	/** Get Promotion Group.
		@return Promotion Group	  */
	public int getM_PromotionGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PromotionGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Promotion Group Line.
		@param M_PromotionGroupLine_ID Promotion Group Line	  */
	public void setM_PromotionGroupLine_ID (int M_PromotionGroupLine_ID)
	{
		if (M_PromotionGroupLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PromotionGroupLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PromotionGroupLine_ID, Integer.valueOf(M_PromotionGroupLine_ID));
	}

	/** Get Promotion Group Line.
		@return Promotion Group Line	  */
	public int getM_PromotionGroupLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PromotionGroupLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}