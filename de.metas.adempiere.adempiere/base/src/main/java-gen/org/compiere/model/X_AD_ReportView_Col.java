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
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_ReportView_Col
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_ReportView_Col extends PO implements I_AD_ReportView_Col, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_ReportView_Col (Properties ctx, int AD_ReportView_Col_ID, String trxName)
    {
      super (ctx, AD_ReportView_Col_ID, trxName);
      /** if (AD_ReportView_Col_ID == 0)
        {
			setAD_ReportView_Col_ID (0);
			setAD_ReportView_ID (0);
			setFunctionColumn (null);
			setIsGroupFunction (false);
        } */
    }

    /** Load Constructor */
    public X_AD_ReportView_Col (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_ReportView_Col[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Column getAD_Column() throws RuntimeException
    {
		return (I_AD_Column)MTable.get(getCtx(), I_AD_Column.Table_Name)
			.getPO(getAD_Column_ID(), get_TrxName());	}

	/** Set Column.
		@param AD_Column_ID 
		Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Column.
		@return Column in the table
	  */
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Report view Column.
		@param AD_ReportView_Col_ID Report view Column	  */
	public void setAD_ReportView_Col_ID (int AD_ReportView_Col_ID)
	{
		if (AD_ReportView_Col_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ReportView_Col_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ReportView_Col_ID, Integer.valueOf(AD_ReportView_Col_ID));
	}

	/** Get Report view Column.
		@return Report view Column	  */
	public int getAD_ReportView_Col_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ReportView_Col_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_ReportView getAD_ReportView() throws RuntimeException
    {
		return (I_AD_ReportView)MTable.get(getCtx(), I_AD_ReportView.Table_Name)
			.getPO(getAD_ReportView_ID(), get_TrxName());	}

	/** Set Report View.
		@param AD_ReportView_ID 
		View used to generate this report
	  */
	public void setAD_ReportView_ID (int AD_ReportView_ID)
	{
		if (AD_ReportView_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ReportView_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ReportView_ID, Integer.valueOf(AD_ReportView_ID));
	}

	/** Get Report View.
		@return View used to generate this report
	  */
	public int getAD_ReportView_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ReportView_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_ReportView_ID()));
    }

	/** Set Function Column.
		@param FunctionColumn 
		Overwrite Column with Function 
	  */
	public void setFunctionColumn (String FunctionColumn)
	{
		set_Value (COLUMNNAME_FunctionColumn, FunctionColumn);
	}

	/** Get Function Column.
		@return Overwrite Column with Function 
	  */
	public String getFunctionColumn () 
	{
		return (String)get_Value(COLUMNNAME_FunctionColumn);
	}

	/** Set SQL Group Function.
		@param IsGroupFunction 
		This function will generate a Group By Clause
	  */
	public void setIsGroupFunction (boolean IsGroupFunction)
	{
		set_Value (COLUMNNAME_IsGroupFunction, Boolean.valueOf(IsGroupFunction));
	}

	/** Get SQL Group Function.
		@return This function will generate a Group By Clause
	  */
	public boolean isGroupFunction () 
	{
		Object oo = get_Value(COLUMNNAME_IsGroupFunction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}