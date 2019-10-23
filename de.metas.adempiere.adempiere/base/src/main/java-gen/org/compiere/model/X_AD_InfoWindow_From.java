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

/** Generated Model for AD_InfoWindow_From
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_InfoWindow_From extends org.compiere.model.PO implements I_AD_InfoWindow_From, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1586087341L;

    /** Standard Constructor */
    public X_AD_InfoWindow_From (Properties ctx, int AD_InfoWindow_From_ID, String trxName)
    {
      super (ctx, AD_InfoWindow_From_ID, trxName);
      /** if (AD_InfoWindow_From_ID == 0)
        {
			setAD_InfoWindow_From_ID (0);
			setAD_InfoWindow_ID (0);
			setEntityType (null);
			setFromClause (null);
        } */
    }

    /** Load Constructor */
    public X_AD_InfoWindow_From (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_InfoWindow_From[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Info Window From.
		@param AD_InfoWindow_From_ID Info Window From	  */
	@Override
	public void setAD_InfoWindow_From_ID (int AD_InfoWindow_From_ID)
	{
		if (AD_InfoWindow_From_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_InfoWindow_From_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_InfoWindow_From_ID, Integer.valueOf(AD_InfoWindow_From_ID));
	}

	/** Get Info Window From.
		@return Info Window From	  */
	@Override
	public int getAD_InfoWindow_From_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_InfoWindow_From_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_InfoWindow getAD_InfoWindow() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_InfoWindow_ID, org.compiere.model.I_AD_InfoWindow.class);
	}

	@Override
	public void setAD_InfoWindow(org.compiere.model.I_AD_InfoWindow AD_InfoWindow)
	{
		set_ValueFromPO(COLUMNNAME_AD_InfoWindow_ID, org.compiere.model.I_AD_InfoWindow.class, AD_InfoWindow);
	}

	/** Set Info-Fenster.
		@param AD_InfoWindow_ID 
		Info and search/select Window
	  */
	@Override
	public void setAD_InfoWindow_ID (int AD_InfoWindow_ID)
	{
		if (AD_InfoWindow_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_InfoWindow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_InfoWindow_ID, Integer.valueOf(AD_InfoWindow_ID));
	}

	/** Get Info-Fenster.
		@return Info and search/select Window
	  */
	@Override
	public int getAD_InfoWindow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_InfoWindow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitäts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitäts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Sql FROM.
		@param FromClause 
		SQL FROM clause
	  */
	@Override
	public void setFromClause (java.lang.String FromClause)
	{
		set_Value (COLUMNNAME_FromClause, FromClause);
	}

	/** Get Sql FROM.
		@return SQL FROM clause
	  */
	@Override
	public java.lang.String getFromClause () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FromClause);
	}
}