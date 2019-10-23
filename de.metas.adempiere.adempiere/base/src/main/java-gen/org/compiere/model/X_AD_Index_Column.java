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

/** Generated Model for AD_Index_Column
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Index_Column extends PO implements I_AD_Index_Column, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100111L;

    /** Standard Constructor */
    public X_AD_Index_Column (Properties ctx, int AD_Index_Column_ID, String trxName)
    {
      super (ctx, AD_Index_Column_ID, trxName);
      /** if (AD_Index_Column_ID == 0)
        {
			setAD_Column_ID (0);
			setAD_Index_Column_ID (0);
			setAD_Index_Table_ID (0);
			setEntityType (null);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 FROM AD_Index_Column WHERE AD_Index_Table_ID=@AD_Index_Table_ID@
        } */
    }

    /** Load Constructor */
    public X_AD_Index_Column (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Index_Column[")
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

	/** Set Index Column.
		@param AD_Index_Column_ID Index Column	  */
	public void setAD_Index_Column_ID (int AD_Index_Column_ID)
	{
		if (AD_Index_Column_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Index_Column_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Index_Column_ID, Integer.valueOf(AD_Index_Column_ID));
	}

	/** Get Index Column.
		@return Index Column	  */
	public int getAD_Index_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Index_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Index_Table getAD_Index_Table() throws RuntimeException
    {
		return (I_AD_Index_Table)MTable.get(getCtx(), I_AD_Index_Table.Table_Name)
			.getPO(getAD_Index_Table_ID(), get_TrxName());	}

	/** Set Table Index.
		@param AD_Index_Table_ID Table Index	  */
	public void setAD_Index_Table_ID (int AD_Index_Table_ID)
	{
		if (AD_Index_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Index_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Index_Table_ID, Integer.valueOf(AD_Index_Table_ID));
	}

	/** Get Table Index.
		@return Table Index	  */
	public int getAD_Index_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Index_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Column SQL.
		@param ColumnSQL 
		Virtual Column (r/o)
	  */
	public void setColumnSQL (String ColumnSQL)
	{
		set_Value (COLUMNNAME_ColumnSQL, ColumnSQL);
	}

	/** Get Column SQL.
		@return Virtual Column (r/o)
	  */
	public String getColumnSQL () 
	{
		return (String)get_Value(COLUMNNAME_ColumnSQL);
	}

	/** EntityType AD_Reference_ID=389 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entity Type.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entity Type.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	public String getEntityType () 
	{
		return (String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}