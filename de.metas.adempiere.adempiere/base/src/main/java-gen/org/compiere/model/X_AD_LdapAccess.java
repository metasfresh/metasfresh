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

/** Generated Model for AD_LdapAccess
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_LdapAccess extends PO implements I_AD_LdapAccess, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_LdapAccess (Properties ctx, int AD_LdapAccess_ID, String trxName)
    {
      super (ctx, AD_LdapAccess_ID, trxName);
      /** if (AD_LdapAccess_ID == 0)
        {
			setAD_LdapAccess_ID (0);
			setAD_LdapProcessor_ID (0);
			setIsError (false);
        } */
    }

    /** Load Constructor */
    public X_AD_LdapAccess (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_LdapAccess[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Ldap Access.
		@param AD_LdapAccess_ID 
		Ldap Access Log
	  */
	public void setAD_LdapAccess_ID (int AD_LdapAccess_ID)
	{
		if (AD_LdapAccess_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_LdapAccess_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_LdapAccess_ID, Integer.valueOf(AD_LdapAccess_ID));
	}

	/** Get Ldap Access.
		@return Ldap Access Log
	  */
	public int getAD_LdapAccess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_LdapAccess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_LdapProcessor getAD_LdapProcessor() throws RuntimeException
    {
		return (I_AD_LdapProcessor)MTable.get(getCtx(), I_AD_LdapProcessor.Table_Name)
			.getPO(getAD_LdapProcessor_ID(), get_TrxName());	}

	/** Set Ldap Processor.
		@param AD_LdapProcessor_ID 
		LDAP Server to authenticate and authorize external systems based on Adempiere
	  */
	public void setAD_LdapProcessor_ID (int AD_LdapProcessor_ID)
	{
		if (AD_LdapProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_LdapProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_LdapProcessor_ID, Integer.valueOf(AD_LdapProcessor_ID));
	}

	/** Get Ldap Processor.
		@return LDAP Server to authenticate and authorize external systems based on Adempiere
	  */
	public int getAD_LdapProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_LdapProcessor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_User getAD_User() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_User_ID()));
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

	/** Set Error.
		@param IsError 
		An Error occured in the execution
	  */
	public void setIsError (boolean IsError)
	{
		set_ValueNoCheck (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Error.
		@return An Error occured in the execution
	  */
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_R_InterestArea getR_InterestArea() throws RuntimeException
    {
		return (I_R_InterestArea)MTable.get(getCtx(), I_R_InterestArea.Table_Name)
			.getPO(getR_InterestArea_ID(), get_TrxName());	}

	/** Set Interest Area.
		@param R_InterestArea_ID 
		Interest Area or Topic
	  */
	public void setR_InterestArea_ID (int R_InterestArea_ID)
	{
		if (R_InterestArea_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_InterestArea_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_InterestArea_ID, Integer.valueOf(R_InterestArea_ID));
	}

	/** Get Interest Area.
		@return Interest Area or Topic
	  */
	public int getR_InterestArea_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_InterestArea_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Summary.
		@param Summary 
		Textual summary of this request
	  */
	public void setSummary (String Summary)
	{
		set_ValueNoCheck (COLUMNNAME_Summary, Summary);
	}

	/** Get Summary.
		@return Textual summary of this request
	  */
	public String getSummary () 
	{
		return (String)get_Value(COLUMNNAME_Summary);
	}
}