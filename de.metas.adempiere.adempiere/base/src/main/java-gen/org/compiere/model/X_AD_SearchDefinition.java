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

/** Generated Model for AD_SearchDefinition
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_SearchDefinition extends PO implements I_AD_SearchDefinition, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_SearchDefinition (Properties ctx, int AD_SearchDefinition_ID, String trxName)
    {
      super (ctx, AD_SearchDefinition_ID, trxName);
      /** if (AD_SearchDefinition_ID == 0)
        {
			setAD_SearchDefinition_ID (0);
			setAD_Table_ID (0);
			setAD_Window_ID (0);
			setDataType (null);
			setIsDefault (false);
// N
			setSearchType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_SearchDefinition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_SearchDefinition[")
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

	/** Set AD_SearchDefinition_ID.
		@param AD_SearchDefinition_ID AD_SearchDefinition_ID	  */
	public void setAD_SearchDefinition_ID (int AD_SearchDefinition_ID)
	{
		if (AD_SearchDefinition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_SearchDefinition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_SearchDefinition_ID, Integer.valueOf(AD_SearchDefinition_ID));
	}

	/** Get AD_SearchDefinition_ID.
		@return AD_SearchDefinition_ID	  */
	public int getAD_SearchDefinition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_SearchDefinition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Window getAD_Window() throws RuntimeException
    {
		return (I_AD_Window)MTable.get(getCtx(), I_AD_Window.Table_Name)
			.getPO(getAD_Window_ID(), get_TrxName());	}

	/** Set Window.
		@param AD_Window_ID 
		Data entry or display window
	  */
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Window.
		@return Data entry or display window
	  */
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Data Type.
		@param DataType 
		Type of data
	  */
	public void setDataType (String DataType)
	{
		set_Value (COLUMNNAME_DataType, DataType);
	}

	/** Get Data Type.
		@return Type of data
	  */
	public String getDataType () 
	{
		return (String)get_Value(COLUMNNAME_DataType);
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

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public I_AD_Window getPO_Window() throws RuntimeException
    {
		return (I_AD_Window)MTable.get(getCtx(), I_AD_Window.Table_Name)
			.getPO(getPO_Window_ID(), get_TrxName());	}

	/** Set PO Window.
		@param PO_Window_ID 
		Purchase Order Window
	  */
	public void setPO_Window_ID (int PO_Window_ID)
	{
		if (PO_Window_ID < 1) 
			set_Value (COLUMNNAME_PO_Window_ID, null);
		else 
			set_Value (COLUMNNAME_PO_Window_ID, Integer.valueOf(PO_Window_ID));
	}

	/** Get PO Window.
		@return Purchase Order Window
	  */
	public int getPO_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Query.
		@param Query 
		SQL
	  */
	public void setQuery (String Query)
	{
		set_Value (COLUMNNAME_Query, Query);
	}

	/** Get Query.
		@return SQL
	  */
	public String getQuery () 
	{
		return (String)get_Value(COLUMNNAME_Query);
	}

	/** Set Search Type.
		@param SearchType 
		Which kind of search is used (Query or Table)
	  */
	public void setSearchType (String SearchType)
	{
		set_Value (COLUMNNAME_SearchType, SearchType);
	}

	/** Get Search Type.
		@return Which kind of search is used (Query or Table)
	  */
	public String getSearchType () 
	{
		return (String)get_Value(COLUMNNAME_SearchType);
	}

	/** Set Transaction Code.
		@param TransactionCode 
		The transaction code represents the search definition
	  */
	public void setTransactionCode (String TransactionCode)
	{
		set_Value (COLUMNNAME_TransactionCode, TransactionCode);
	}

	/** Get Transaction Code.
		@return The transaction code represents the search definition
	  */
	public String getTransactionCode () 
	{
		return (String)get_Value(COLUMNNAME_TransactionCode);
	}
}