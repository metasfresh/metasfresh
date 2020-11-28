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
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for HR_ListLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_HR_ListLine extends PO implements I_HR_ListLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_HR_ListLine (Properties ctx, int HR_ListLine_ID, String trxName)
    {
      super (ctx, HR_ListLine_ID, trxName);
      /** if (HR_ListLine_ID == 0)
        {
			setHR_ListLine_ID (0);
			setMaxValue (Env.ZERO);
			setMinValue (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_HR_ListLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_ListLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Col_1.
		@param Col_1 Col_1	  */
	public void setCol_1 (BigDecimal Col_1)
	{
		set_Value (COLUMNNAME_Col_1, Col_1);
	}

	/** Get Col_1.
		@return Col_1	  */
	public BigDecimal getCol_1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Col_1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Col_2.
		@param Col_2 Col_2	  */
	public void setCol_2 (BigDecimal Col_2)
	{
		set_Value (COLUMNNAME_Col_2, Col_2);
	}

	/** Get Col_2.
		@return Col_2	  */
	public BigDecimal getCol_2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Col_2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Col_3.
		@param Col_3 Col_3	  */
	public void setCol_3 (BigDecimal Col_3)
	{
		set_Value (COLUMNNAME_Col_3, Col_3);
	}

	/** Get Col_3.
		@return Col_3	  */
	public BigDecimal getCol_3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Col_3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Col_4.
		@param Col_4 Col_4	  */
	public void setCol_4 (BigDecimal Col_4)
	{
		set_Value (COLUMNNAME_Col_4, Col_4);
	}

	/** Get Col_4.
		@return Col_4	  */
	public BigDecimal getCol_4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Col_4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Col_5.
		@param Col_5 Col_5	  */
	public void setCol_5 (BigDecimal Col_5)
	{
		set_Value (COLUMNNAME_Col_5, Col_5);
	}

	/** Get Col_5.
		@return Col_5	  */
	public BigDecimal getCol_5 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Col_5);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Col_6.
		@param Col_6 Col_6	  */
	public void setCol_6 (BigDecimal Col_6)
	{
		set_Value (COLUMNNAME_Col_6, Col_6);
	}

	/** Get Col_6.
		@return Col_6	  */
	public BigDecimal getCol_6 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Col_6);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Col_7.
		@param Col_7 Col_7	  */
	public void setCol_7 (BigDecimal Col_7)
	{
		set_Value (COLUMNNAME_Col_7, Col_7);
	}

	/** Get Col_7.
		@return Col_7	  */
	public BigDecimal getCol_7 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Col_7);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Col_8.
		@param Col_8 Col_8	  */
	public void setCol_8 (BigDecimal Col_8)
	{
		set_Value (COLUMNNAME_Col_8, Col_8);
	}

	/** Get Col_8.
		@return Col_8	  */
	public BigDecimal getCol_8 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Col_8);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Payroll List Line.
		@param HR_ListLine_ID Payroll List Line	  */
	public void setHR_ListLine_ID (int HR_ListLine_ID)
	{
		if (HR_ListLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_ListLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_ListLine_ID, Integer.valueOf(HR_ListLine_ID));
	}

	/** Get Payroll List Line.
		@return Payroll List Line	  */
	public int getHR_ListLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ListLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_ListVersion getHR_ListVersion() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_ListVersion)MTable.get(getCtx(), org.eevolution.model.I_HR_ListVersion.Table_Name)
			.getPO(getHR_ListVersion_ID(), get_TrxName());	}

	/** Set Payroll List Version.
		@param HR_ListVersion_ID Payroll List Version	  */
	public void setHR_ListVersion_ID (int HR_ListVersion_ID)
	{
		if (HR_ListVersion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_ListVersion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_ListVersion_ID, Integer.valueOf(HR_ListVersion_ID));
	}

	/** Get Payroll List Version.
		@return Payroll List Version	  */
	public int getHR_ListVersion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ListVersion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Max Value.
		@param MaxValue Max Value	  */
	public void setMaxValue (BigDecimal MaxValue)
	{
		set_Value (COLUMNNAME_MaxValue, MaxValue);
	}

	/** Get Max Value.
		@return Max Value	  */
	public BigDecimal getMaxValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxValue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min Value.
		@param MinValue Min Value	  */
	public void setMinValue (BigDecimal MinValue)
	{
		set_Value (COLUMNNAME_MinValue, MinValue);
	}

	/** Get Min Value.
		@return Min Value	  */
	public BigDecimal getMinValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinValue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }
}