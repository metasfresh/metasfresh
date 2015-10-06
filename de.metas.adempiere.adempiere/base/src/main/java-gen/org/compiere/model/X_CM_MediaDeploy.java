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
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for CM_MediaDeploy
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_CM_MediaDeploy extends PO implements I_CM_MediaDeploy, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_CM_MediaDeploy (Properties ctx, int CM_MediaDeploy_ID, String trxName)
    {
      super (ctx, CM_MediaDeploy_ID, trxName);
      /** if (CM_MediaDeploy_ID == 0)
        {
			setCM_MediaDeploy_ID (0);
			setCM_Media_ID (0);
			setCM_Media_Server_ID (0);
			setIsDeployed (false);
        } */
    }

    /** Load Constructor */
    public X_CM_MediaDeploy (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CM_MediaDeploy[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Media Deploy.
		@param CM_MediaDeploy_ID 
		Media Deployment Log
	  */
	public void setCM_MediaDeploy_ID (int CM_MediaDeploy_ID)
	{
		if (CM_MediaDeploy_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_MediaDeploy_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_MediaDeploy_ID, Integer.valueOf(CM_MediaDeploy_ID));
	}

	/** Get Media Deploy.
		@return Media Deployment Log
	  */
	public int getCM_MediaDeploy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_MediaDeploy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CM_Media getCM_Media() throws RuntimeException
    {
		return (I_CM_Media)MTable.get(getCtx(), I_CM_Media.Table_Name)
			.getPO(getCM_Media_ID(), get_TrxName());	}

	/** Set Media Item.
		@param CM_Media_ID 
		Contains media content like images, flash movies etc.
	  */
	public void setCM_Media_ID (int CM_Media_ID)
	{
		if (CM_Media_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_Media_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_Media_ID, Integer.valueOf(CM_Media_ID));
	}

	/** Get Media Item.
		@return Contains media content like images, flash movies etc.
	  */
	public int getCM_Media_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Media_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CM_Media_Server getCM_Media_Server() throws RuntimeException
    {
		return (I_CM_Media_Server)MTable.get(getCtx(), I_CM_Media_Server.Table_Name)
			.getPO(getCM_Media_Server_ID(), get_TrxName());	}

	/** Set Media Server.
		@param CM_Media_Server_ID 
		Media Server list to which content should get transfered
	  */
	public void setCM_Media_Server_ID (int CM_Media_Server_ID)
	{
		if (CM_Media_Server_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_Media_Server_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_Media_Server_ID, Integer.valueOf(CM_Media_Server_ID));
	}

	/** Get Media Server.
		@return Media Server list to which content should get transfered
	  */
	public int getCM_Media_Server_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Media_Server_ID);
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

	/** Set Deployed.
		@param IsDeployed 
		Entity is deployed
	  */
	public void setIsDeployed (boolean IsDeployed)
	{
		set_Value (COLUMNNAME_IsDeployed, Boolean.valueOf(IsDeployed));
	}

	/** Get Deployed.
		@return Entity is deployed
	  */
	public boolean isDeployed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDeployed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last Synchronized.
		@param LastSynchronized 
		Date when last synchronized
	  */
	public void setLastSynchronized (Timestamp LastSynchronized)
	{
		set_Value (COLUMNNAME_LastSynchronized, LastSynchronized);
	}

	/** Get Last Synchronized.
		@return Date when last synchronized
	  */
	public Timestamp getLastSynchronized () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastSynchronized);
	}
}