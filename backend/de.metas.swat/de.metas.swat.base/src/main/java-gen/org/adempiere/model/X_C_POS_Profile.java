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
package org.adempiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_POS_Profile
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_POS_Profile extends org.compiere.model.PO implements I_C_POS_Profile, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2026887857L;

    /** Standard Constructor */
    public X_C_POS_Profile (Properties ctx, int C_POS_Profile_ID, String trxName)
    {
      super (ctx, C_POS_Profile_ID, trxName);
      /** if (C_POS_Profile_ID == 0)
        {
			setAD_Role_ID (0);
			setC_POS_Profile_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_POS_Profile (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_POS_Profile[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	/** Set Rolle.
		@param AD_Role_ID 
		Responsibility Role
	  */
	@Override
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Rolle.
		@return Responsibility Role
	  */
	@Override
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set POS Profile.
		@param C_POS_Profile_ID POS Profile	  */
	@Override
	public void setC_POS_Profile_ID (int C_POS_Profile_ID)
	{
		if (C_POS_Profile_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_Profile_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_Profile_ID, Integer.valueOf(C_POS_Profile_ID));
	}

	/** Get POS Profile.
		@return POS Profile	  */
	@Override
	public int getC_POS_Profile_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_POS_Profile_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_POS_Profile_Included_Tab.
		@param C_POS_Profile_Included_Tab C_POS_Profile_Included_Tab	  */
	@Override
	public void setC_POS_Profile_Included_Tab (java.lang.String C_POS_Profile_Included_Tab)
	{
		set_Value (COLUMNNAME_C_POS_Profile_Included_Tab, C_POS_Profile_Included_Tab);
	}

	/** Get C_POS_Profile_Included_Tab.
		@return C_POS_Profile_Included_Tab	  */
	@Override
	public java.lang.String getC_POS_Profile_Included_Tab () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_POS_Profile_Included_Tab);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}