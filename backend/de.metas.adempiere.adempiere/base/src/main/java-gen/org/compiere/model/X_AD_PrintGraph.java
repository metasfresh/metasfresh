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

/** Generated Model for AD_PrintGraph
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_PrintGraph extends PO implements I_AD_PrintGraph, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_PrintGraph (Properties ctx, int AD_PrintGraph_ID, String trxName)
    {
      super (ctx, AD_PrintGraph_ID, trxName);
      /** if (AD_PrintGraph_ID == 0)
        {
			setAD_PrintFormat_ID (0);
// 0
			setAD_PrintGraph_ID (0);
			setData_PrintFormatItem_ID (0);
			setDescription_PrintFormatItem_ID (0);
			setGraphType (null);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_PrintGraph (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_PrintGraph[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_PrintFormat getAD_PrintFormat() throws RuntimeException
    {
		return (I_AD_PrintFormat)MTable.get(getCtx(), I_AD_PrintFormat.Table_Name)
			.getPO(getAD_PrintFormat_ID(), get_TrxName());	}

	/** Set Print Format.
		@param AD_PrintFormat_ID 
		Data Print Format
	  */
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, Integer.valueOf(AD_PrintFormat_ID));
	}

	/** Get Print Format.
		@return Data Print Format
	  */
	public int getAD_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Graph.
		@param AD_PrintGraph_ID 
		Graph included in Reports
	  */
	public void setAD_PrintGraph_ID (int AD_PrintGraph_ID)
	{
		if (AD_PrintGraph_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrintGraph_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrintGraph_ID, Integer.valueOf(AD_PrintGraph_ID));
	}

	/** Get Graph.
		@return Graph included in Reports
	  */
	public int getAD_PrintGraph_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintGraph_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_PrintFormatItem getData1_PrintFormatItem() throws RuntimeException
    {
		return (I_AD_PrintFormatItem)MTable.get(getCtx(), I_AD_PrintFormatItem.Table_Name)
			.getPO(getData1_PrintFormatItem_ID(), get_TrxName());	}

	/** Set Data Column 2.
		@param Data1_PrintFormatItem_ID 
		Data Column for Line Charts
	  */
	public void setData1_PrintFormatItem_ID (int Data1_PrintFormatItem_ID)
	{
		if (Data1_PrintFormatItem_ID < 1) 
			set_Value (COLUMNNAME_Data1_PrintFormatItem_ID, null);
		else 
			set_Value (COLUMNNAME_Data1_PrintFormatItem_ID, Integer.valueOf(Data1_PrintFormatItem_ID));
	}

	/** Get Data Column 2.
		@return Data Column for Line Charts
	  */
	public int getData1_PrintFormatItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Data1_PrintFormatItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_PrintFormatItem getData2_PrintFormatItem() throws RuntimeException
    {
		return (I_AD_PrintFormatItem)MTable.get(getCtx(), I_AD_PrintFormatItem.Table_Name)
			.getPO(getData2_PrintFormatItem_ID(), get_TrxName());	}

	/** Set Data Column 3.
		@param Data2_PrintFormatItem_ID 
		Data Column for Line Charts
	  */
	public void setData2_PrintFormatItem_ID (int Data2_PrintFormatItem_ID)
	{
		if (Data2_PrintFormatItem_ID < 1) 
			set_Value (COLUMNNAME_Data2_PrintFormatItem_ID, null);
		else 
			set_Value (COLUMNNAME_Data2_PrintFormatItem_ID, Integer.valueOf(Data2_PrintFormatItem_ID));
	}

	/** Get Data Column 3.
		@return Data Column for Line Charts
	  */
	public int getData2_PrintFormatItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Data2_PrintFormatItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_PrintFormatItem getData3_PrintFormatItem() throws RuntimeException
    {
		return (I_AD_PrintFormatItem)MTable.get(getCtx(), I_AD_PrintFormatItem.Table_Name)
			.getPO(getData3_PrintFormatItem_ID(), get_TrxName());	}

	/** Set Data Column 4.
		@param Data3_PrintFormatItem_ID 
		Data Column for Line Charts
	  */
	public void setData3_PrintFormatItem_ID (int Data3_PrintFormatItem_ID)
	{
		if (Data3_PrintFormatItem_ID < 1) 
			set_Value (COLUMNNAME_Data3_PrintFormatItem_ID, null);
		else 
			set_Value (COLUMNNAME_Data3_PrintFormatItem_ID, Integer.valueOf(Data3_PrintFormatItem_ID));
	}

	/** Get Data Column 4.
		@return Data Column for Line Charts
	  */
	public int getData3_PrintFormatItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Data3_PrintFormatItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_PrintFormatItem getData4_PrintFormatItem() throws RuntimeException
    {
		return (I_AD_PrintFormatItem)MTable.get(getCtx(), I_AD_PrintFormatItem.Table_Name)
			.getPO(getData4_PrintFormatItem_ID(), get_TrxName());	}

	/** Set Data Column 5.
		@param Data4_PrintFormatItem_ID 
		Data Column for Line Charts
	  */
	public void setData4_PrintFormatItem_ID (int Data4_PrintFormatItem_ID)
	{
		if (Data4_PrintFormatItem_ID < 1) 
			set_Value (COLUMNNAME_Data4_PrintFormatItem_ID, null);
		else 
			set_Value (COLUMNNAME_Data4_PrintFormatItem_ID, Integer.valueOf(Data4_PrintFormatItem_ID));
	}

	/** Get Data Column 5.
		@return Data Column for Line Charts
	  */
	public int getData4_PrintFormatItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Data4_PrintFormatItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_PrintFormatItem getData_PrintFormatItem() throws RuntimeException
    {
		return (I_AD_PrintFormatItem)MTable.get(getCtx(), I_AD_PrintFormatItem.Table_Name)
			.getPO(getData_PrintFormatItem_ID(), get_TrxName());	}

	/** Set Data Column.
		@param Data_PrintFormatItem_ID 
		Data Column for Pie and Line Charts
	  */
	public void setData_PrintFormatItem_ID (int Data_PrintFormatItem_ID)
	{
		if (Data_PrintFormatItem_ID < 1) 
			set_Value (COLUMNNAME_Data_PrintFormatItem_ID, null);
		else 
			set_Value (COLUMNNAME_Data_PrintFormatItem_ID, Integer.valueOf(Data_PrintFormatItem_ID));
	}

	/** Get Data Column.
		@return Data Column for Pie and Line Charts
	  */
	public int getData_PrintFormatItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Data_PrintFormatItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_AD_PrintFormatItem getDescription_PrintFormatItem() throws RuntimeException
    {
		return (I_AD_PrintFormatItem)MTable.get(getCtx(), I_AD_PrintFormatItem.Table_Name)
			.getPO(getDescription_PrintFormatItem_ID(), get_TrxName());	}

	/** Set Description Column.
		@param Description_PrintFormatItem_ID 
		Description Column for Pie/Line/Bar Charts
	  */
	public void setDescription_PrintFormatItem_ID (int Description_PrintFormatItem_ID)
	{
		if (Description_PrintFormatItem_ID < 1) 
			set_Value (COLUMNNAME_Description_PrintFormatItem_ID, null);
		else 
			set_Value (COLUMNNAME_Description_PrintFormatItem_ID, Integer.valueOf(Description_PrintFormatItem_ID));
	}

	/** Get Description Column.
		@return Description Column for Pie/Line/Bar Charts
	  */
	public int getDescription_PrintFormatItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Description_PrintFormatItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** GraphType AD_Reference_ID=265 */
	public static final int GRAPHTYPE_AD_Reference_ID=265;
	/** Pie Chart = P */
	public static final String GRAPHTYPE_PieChart = "P";
	/** Line Chart = L */
	public static final String GRAPHTYPE_LineChart = "L";
	/** Bar Chart = B */
	public static final String GRAPHTYPE_BarChart = "B";
	/** Set Graph Type.
		@param GraphType 
		Type of graph to be painted
	  */
	public void setGraphType (String GraphType)
	{

		set_Value (COLUMNNAME_GraphType, GraphType);
	}

	/** Get Graph Type.
		@return Type of graph to be painted
	  */
	public String getGraphType () 
	{
		return (String)get_Value(COLUMNNAME_GraphType);
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