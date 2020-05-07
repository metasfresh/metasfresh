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

/** Generated Model for A_Depreciation_Table_Detail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_A_Depreciation_Table_Detail extends PO implements I_A_Depreciation_Table_Detail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_A_Depreciation_Table_Detail (Properties ctx, int A_Depreciation_Table_Detail_ID, String trxName)
    {
      super (ctx, A_Depreciation_Table_Detail_ID, trxName);
      /** if (A_Depreciation_Table_Detail_ID == 0)
        {
			setA_Depreciation_Rate (Env.ZERO);
			setA_Depreciation_Table_Code (null);
			setA_Depreciation_Table_Detail_ID (0);
			setA_Period (0);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_A_Depreciation_Table_Detail (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_A_Depreciation_Table_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Rate.
		@param A_Depreciation_Rate Rate	  */
	public void setA_Depreciation_Rate (BigDecimal A_Depreciation_Rate)
	{
		set_Value (COLUMNNAME_A_Depreciation_Rate, A_Depreciation_Rate);
	}

	/** Get Rate.
		@return Rate	  */
	public BigDecimal getA_Depreciation_Rate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_Depreciation_Rate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Depreciation Code.
		@param A_Depreciation_Table_Code Depreciation Code	  */
	public void setA_Depreciation_Table_Code (String A_Depreciation_Table_Code)
	{
		set_ValueNoCheck (COLUMNNAME_A_Depreciation_Table_Code, A_Depreciation_Table_Code);
	}

	/** Get Depreciation Code.
		@return Depreciation Code	  */
	public String getA_Depreciation_Table_Code () 
	{
		return (String)get_Value(COLUMNNAME_A_Depreciation_Table_Code);
	}

	/** Set A_Depreciation_Table_Detail_ID.
		@param A_Depreciation_Table_Detail_ID A_Depreciation_Table_Detail_ID	  */
	public void setA_Depreciation_Table_Detail_ID (int A_Depreciation_Table_Detail_ID)
	{
		if (A_Depreciation_Table_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Depreciation_Table_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Depreciation_Table_Detail_ID, Integer.valueOf(A_Depreciation_Table_Detail_ID));
	}

	/** Get A_Depreciation_Table_Detail_ID.
		@return A_Depreciation_Table_Detail_ID	  */
	public int getA_Depreciation_Table_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Depreciation_Table_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getA_Depreciation_Table_Detail_ID()));
    }

	/** Set Period/Yearly.
		@param A_Period Period/Yearly	  */
	public void setA_Period (int A_Period)
	{
		set_Value (COLUMNNAME_A_Period, Integer.valueOf(A_Period));
	}

	/** Get Period/Yearly.
		@return Period/Yearly	  */
	public int getA_Period () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Period);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** A_Table_Rate_Type AD_Reference_ID=53255 */
	public static final int A_TABLE_RATE_TYPE_AD_Reference_ID=53255;
	/** Amount = AM */
	public static final String A_TABLE_RATE_TYPE_Amount = "AM";
	/** Rate = RT */
	public static final String A_TABLE_RATE_TYPE_Rate = "RT";
	/** Set Type.
		@param A_Table_Rate_Type Type	  */
	public void setA_Table_Rate_Type (String A_Table_Rate_Type)
	{

		set_ValueNoCheck (COLUMNNAME_A_Table_Rate_Type, A_Table_Rate_Type);
	}

	/** Get Type.
		@return Type	  */
	public String getA_Table_Rate_Type () 
	{
		return (String)get_Value(COLUMNNAME_A_Table_Rate_Type);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}