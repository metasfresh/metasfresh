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

/** Generated Model for AD_Replication_Run
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_AD_Replication_Run extends PO implements I_AD_Replication_Run, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100614L;

    /** Standard Constructor */
    public X_AD_Replication_Run (Properties ctx, int AD_Replication_Run_ID, String trxName)
    {
      super (ctx, AD_Replication_Run_ID, trxName);
      /** if (AD_Replication_Run_ID == 0)
        {
			setAD_Replication_ID (0);
			setAD_Replication_Run_ID (0);
			setIsReplicated (false);
// N
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Replication_Run (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Replication_Run[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Replication getAD_Replication() throws RuntimeException
    {
		return (I_AD_Replication)MTable.get(getCtx(), I_AD_Replication.Table_Name)
			.getPO(getAD_Replication_ID(), get_TrxName());	}

	/** Set Replication.
		@param AD_Replication_ID 
		Data Replication Target
	  */
	public void setAD_Replication_ID (int AD_Replication_ID)
	{
		if (AD_Replication_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Replication_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Replication_ID, Integer.valueOf(AD_Replication_ID));
	}

	/** Get Replication.
		@return Data Replication Target
	  */
	public int getAD_Replication_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Replication_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Replication Run.
		@param AD_Replication_Run_ID 
		Data Replication Run
	  */
	public void setAD_Replication_Run_ID (int AD_Replication_Run_ID)
	{
		if (AD_Replication_Run_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Replication_Run_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Replication_Run_ID, Integer.valueOf(AD_Replication_Run_ID));
	}

	/** Get Replication Run.
		@return Data Replication Run
	  */
	public int getAD_Replication_Run_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Replication_Run_ID);
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

	/** Set Replicated.
		@param IsReplicated 
		The data is successfully replicated
	  */
	public void setIsReplicated (boolean IsReplicated)
	{
		set_ValueNoCheck (COLUMNNAME_IsReplicated, Boolean.valueOf(IsReplicated));
	}

	/** Get Replicated.
		@return The data is successfully replicated
	  */
	public boolean isReplicated () 
	{
		Object oo = get_Value(COLUMNNAME_IsReplicated);
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
}