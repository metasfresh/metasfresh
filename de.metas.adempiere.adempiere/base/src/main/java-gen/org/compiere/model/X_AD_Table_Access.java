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

/** Generated Model for AD_Table_Access
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Table_Access extends PO implements I_AD_Table_Access, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_Table_Access (Properties ctx, int AD_Table_Access_ID, String trxName)
    {
      super (ctx, AD_Table_Access_ID, trxName);
      /** if (AD_Table_Access_ID == 0)
        {
			setAccessTypeRule (null);
// A
			setAD_Role_ID (0);
			setAD_Table_ID (0);
			setIsCanExport (false);
			setIsCanReport (false);
			setIsExclude (true);
// Y
			setIsReadOnly (false);
        } */
    }

    /** Load Constructor */
    public X_AD_Table_Access (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Table_Access[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** AccessTypeRule AD_Reference_ID=293 */
	public static final int ACCESSTYPERULE_AD_Reference_ID=293;
	/** Accessing = A */
	public static final String ACCESSTYPERULE_Accessing = "A";
	/** Reporting = R */
	public static final String ACCESSTYPERULE_Reporting = "R";
	/** Exporting = E */
	public static final String ACCESSTYPERULE_Exporting = "E";
	/** Set Access Type.
		@param AccessTypeRule 
		The type of access for this rule
	  */
	public void setAccessTypeRule (String AccessTypeRule)
	{

		set_ValueNoCheck (COLUMNNAME_AccessTypeRule, AccessTypeRule);
	}

	/** Get Access Type.
		@return The type of access for this rule
	  */
	public String getAccessTypeRule () 
	{
		return (String)get_Value(COLUMNNAME_AccessTypeRule);
	}

	public I_AD_Role getAD_Role() throws RuntimeException
    {
		return (I_AD_Role)MTable.get(getCtx(), I_AD_Role.Table_Name)
			.getPO(getAD_Role_ID(), get_TrxName());	}

	/** Set Role.
		@param AD_Role_ID 
		Responsibility Role
	  */
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Role.
		@return Responsibility Role
	  */
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
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
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_Table_ID()));
    }

	/** Set Can Export.
		@param IsCanExport 
		Users with this role can export data
	  */
	public void setIsCanExport (boolean IsCanExport)
	{
		set_Value (COLUMNNAME_IsCanExport, Boolean.valueOf(IsCanExport));
	}

	/** Get Can Export.
		@return Users with this role can export data
	  */
	public boolean isCanExport () 
	{
		Object oo = get_Value(COLUMNNAME_IsCanExport);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Can Report.
		@param IsCanReport 
		Users with this role can create reports
	  */
	public void setIsCanReport (boolean IsCanReport)
	{
		set_Value (COLUMNNAME_IsCanReport, Boolean.valueOf(IsCanReport));
	}

	/** Get Can Report.
		@return Users with this role can create reports
	  */
	public boolean isCanReport () 
	{
		Object oo = get_Value(COLUMNNAME_IsCanReport);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Exclude.
		@param IsExclude 
		Exclude access to the data - if not selected Include access to the data
	  */
	public void setIsExclude (boolean IsExclude)
	{
		set_Value (COLUMNNAME_IsExclude, Boolean.valueOf(IsExclude));
	}

	/** Get Exclude.
		@return Exclude access to the data - if not selected Include access to the data
	  */
	public boolean isExclude () 
	{
		Object oo = get_Value(COLUMNNAME_IsExclude);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Read Only.
		@param IsReadOnly 
		Field is read only
	  */
	public void setIsReadOnly (boolean IsReadOnly)
	{
		set_Value (COLUMNNAME_IsReadOnly, Boolean.valueOf(IsReadOnly));
	}

	/** Get Read Only.
		@return Field is read only
	  */
	public boolean isReadOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}