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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for HR_Concept_Category
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_HR_Concept_Category extends PO implements I_HR_Concept_Category, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_HR_Concept_Category (Properties ctx, int HR_Concept_Category_ID, String trxName)
    {
      super (ctx, HR_Concept_Category_ID, trxName);
      /** if (HR_Concept_Category_ID == 0)
        {
			setHR_Concept_Category_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_HR_Concept_Category (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_Concept_Category[")
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

	public I_C_ValidCombination getHR_Concept_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getHR_Concept_Acct(), get_TrxName());	}

	/** Set Payroll Concept Account.
		@param HR_Concept_Acct Payroll Concept Account	  */
	public void setHR_Concept_Acct (int HR_Concept_Acct)
	{
		set_Value (COLUMNNAME_HR_Concept_Acct, Integer.valueOf(HR_Concept_Acct));
	}

	/** Get Payroll Concept Account.
		@return Payroll Concept Account	  */
	public int getHR_Concept_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Concept_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payroll Concept Category.
		@param HR_Concept_Category_ID Payroll Concept Category	  */
	public void setHR_Concept_Category_ID (int HR_Concept_Category_ID)
	{
		if (HR_Concept_Category_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_Concept_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_Concept_Category_ID, Integer.valueOf(HR_Concept_Category_ID));
	}

	/** Get Payroll Concept Category.
		@return Payroll Concept Category	  */
	public int getHR_Concept_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Concept_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}