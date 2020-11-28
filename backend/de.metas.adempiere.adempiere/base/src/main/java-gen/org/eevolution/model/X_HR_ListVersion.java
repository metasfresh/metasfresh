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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for HR_ListVersion
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_HR_ListVersion extends PO implements I_HR_ListVersion, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_HR_ListVersion (Properties ctx, int HR_ListVersion_ID, String trxName)
    {
      super (ctx, HR_ListVersion_ID, trxName);
      /** if (HR_ListVersion_ID == 0)
        {
			setHR_List_ID (0);
			setHR_ListVersion_ID (0);
			setName (null);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
			setValidTo (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_HR_ListVersion (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_ListVersion[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.eevolution.model.I_HR_List getHR_ListBase() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_List)MTable.get(getCtx(), org.eevolution.model.I_HR_List.Table_Name)
			.getPO(getHR_ListBase_ID(), get_TrxName());	}

	/** Set Payroll List Base.
		@param HR_ListBase_ID Payroll List Base	  */
	public void setHR_ListBase_ID (int HR_ListBase_ID)
	{
		if (HR_ListBase_ID < 1) 
			set_Value (COLUMNNAME_HR_ListBase_ID, null);
		else 
			set_Value (COLUMNNAME_HR_ListBase_ID, Integer.valueOf(HR_ListBase_ID));
	}

	/** Get Payroll List Base.
		@return Payroll List Base	  */
	public int getHR_ListBase_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_ListBase_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_List getHR_List() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_List)MTable.get(getCtx(), org.eevolution.model.I_HR_List.Table_Name)
			.getPO(getHR_List_ID(), get_TrxName());	}

	/** Set Payroll List.
		@param HR_List_ID Payroll List	  */
	public void setHR_List_ID (int HR_List_ID)
	{
		if (HR_List_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_List_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_List_ID, Integer.valueOf(HR_List_ID));
	}

	/** Get Payroll List.
		@return Payroll List	  */
	public int getHR_List_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_List_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}