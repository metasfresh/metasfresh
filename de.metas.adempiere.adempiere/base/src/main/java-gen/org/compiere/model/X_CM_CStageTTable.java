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

/** Generated Model for CM_CStageTTable
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_CM_CStageTTable extends PO implements I_CM_CStageTTable, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_CM_CStageTTable (Properties ctx, int CM_CStageTTable_ID, String trxName)
    {
      super (ctx, CM_CStageTTable_ID, trxName);
      /** if (CM_CStageTTable_ID == 0)
        {
			setCM_CStage_ID (0);
			setCM_CStageTTable_ID (0);
			setCM_TemplateTable_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_CM_CStageTTable (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuffer sb = new StringBuffer ("X_CM_CStageTTable[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_CM_CStage getCM_CStage() throws RuntimeException
    {
		return (I_CM_CStage)MTable.get(getCtx(), I_CM_CStage.Table_Name)
			.getPO(getCM_CStage_ID(), get_TrxName());	}

	/** Set Web Container Stage.
		@param CM_CStage_ID 
		Web Container Stage contains the staging content like images, text etc.
	  */
	public void setCM_CStage_ID (int CM_CStage_ID)
	{
		if (CM_CStage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_CStage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_CStage_ID, Integer.valueOf(CM_CStage_ID));
	}

	/** Get Web Container Stage.
		@return Web Container Stage contains the staging content like images, text etc.
	  */
	public int getCM_CStage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_CStage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Stage T.Table.
		@param CM_CStageTTable_ID 
		Containet Stage Template Table
	  */
	public void setCM_CStageTTable_ID (int CM_CStageTTable_ID)
	{
		if (CM_CStageTTable_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_CStageTTable_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_CStageTTable_ID, Integer.valueOf(CM_CStageTTable_ID));
	}

	/** Get Stage T.Table.
		@return Containet Stage Template Table
	  */
	public int getCM_CStageTTable_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_CStageTTable_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CM_TemplateTable getCM_TemplateTable() throws RuntimeException
    {
		return (I_CM_TemplateTable)MTable.get(getCtx(), I_CM_TemplateTable.Table_Name)
			.getPO(getCM_TemplateTable_ID(), get_TrxName());	}

	/** Set Template Table.
		@param CM_TemplateTable_ID 
		CM Template Table Link
	  */
	public void setCM_TemplateTable_ID (int CM_TemplateTable_ID)
	{
		if (CM_TemplateTable_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_TemplateTable_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_TemplateTable_ID, Integer.valueOf(CM_TemplateTable_ID));
	}

	/** Get Template Table.
		@return CM Template Table Link
	  */
	public int getCM_TemplateTable_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_TemplateTable_ID);
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

	/** Set Other SQL Clause.
		@param OtherClause 
		Other SQL Clause
	  */
	public void setOtherClause (String OtherClause)
	{
		set_Value (COLUMNNAME_OtherClause, OtherClause);
	}

	/** Get Other SQL Clause.
		@return Other SQL Clause
	  */
	public String getOtherClause () 
	{
		return (String)get_Value(COLUMNNAME_OtherClause);
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sql WHERE.
		@param WhereClause 
		Fully qualified SQL WHERE clause
	  */
	public void setWhereClause (String WhereClause)
	{
		set_Value (COLUMNNAME_WhereClause, WhereClause);
	}

	/** Get Sql WHERE.
		@return Fully qualified SQL WHERE clause
	  */
	public String getWhereClause () 
	{
		return (String)get_Value(COLUMNNAME_WhereClause);
	}
}