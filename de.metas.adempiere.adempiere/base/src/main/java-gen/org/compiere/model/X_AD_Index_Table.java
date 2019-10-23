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

/** Generated Model for AD_Index_Table
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Index_Table extends PO implements I_AD_Index_Table, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100111L;

    /** Standard Constructor */
    public X_AD_Index_Table (Properties ctx, int AD_Index_Table_ID, String trxName)
    {
      super (ctx, AD_Index_Table_ID, trxName);
      /** if (AD_Index_Table_ID == 0)
        {
			setAD_Index_Table_ID (0);
			setAD_Table_ID (0);
			setEntityType (null);
			setIsUnique (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Index_Table (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Index_Table[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Table Index.
		@param AD_Index_Table_ID Table Index	  */
	public void setAD_Index_Table_ID (int AD_Index_Table_ID)
	{
		if (AD_Index_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Index_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Index_Table_ID, Integer.valueOf(AD_Index_Table_ID));
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

	/** Set Before Change Code.
		@param BeforeChangeCode 
		Code to be run if one of the indexed columns were changed. The code runs before the actual change.
	  */
	public void setBeforeChangeCode (String BeforeChangeCode)
	{
		set_Value (COLUMNNAME_BeforeChangeCode, BeforeChangeCode);
	}

	/** Get Before Change Code.
		@return Code to be run if one of the indexed columns were changed. The code runs before the actual change.
	  */
	public String getBeforeChangeCode () 
	{
		return (String)get_Value(COLUMNNAME_BeforeChangeCode);
	}

	/** BeforeChangeCodeType AD_Reference_ID=540080 */
	public static final int BEFORECHANGECODETYPE_AD_Reference_ID=540080;
	/** SQL Simple = SQLS */
	public static final String BEFORECHANGECODETYPE_SQLSimple = "SQLS";
	/** SQL Complex Script = SQLC */
	public static final String BEFORECHANGECODETYPE_SQLComplexScript = "SQLC";
	/** Set Before Change Code Type.
		@param BeforeChangeCodeType Before Change Code Type	  */
	public void setBeforeChangeCodeType (String BeforeChangeCodeType)
	{

		set_Value (COLUMNNAME_BeforeChangeCodeType, BeforeChangeCodeType);
	}

	/** Get Before Change Code Type.
		@return Before Change Code Type	  */
	public String getBeforeChangeCodeType () 
	{
		return (String)get_Value(COLUMNNAME_BeforeChangeCodeType);
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

	/** Set Error Msg.
		@param ErrorMsg Error Msg	  */
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Unique.
		@param IsUnique Unique	  */
	public void setIsUnique (boolean IsUnique)
	{
		set_Value (COLUMNNAME_IsUnique, Boolean.valueOf(IsUnique));
	}

	/** Get Unique.
		@return Unique	  */
	public boolean isUnique () 
	{
		Object oo = get_Value(COLUMNNAME_IsUnique);
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
		set_ValueNoCheck (COLUMNNAME_Name, Name);
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	@Override
	public String getBeforeChangeWarning()
	{
		return (String)get_Value(COLUMNNAME_BeforeChangeWarning);
	}

	@Override
	public void setBeforeChangeWarning(String BeforeChangeWarning)
	{
		set_Value (COLUMNNAME_BeforeChangeWarning, BeforeChangeWarning);
	}
}