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

/** Generated Model for AD_Scheduler_Para
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Scheduler_Para extends PO implements I_AD_Scheduler_Para, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_Scheduler_Para (Properties ctx, int AD_Scheduler_Para_ID, String trxName)
    {
      super (ctx, AD_Scheduler_Para_ID, trxName);
      /** if (AD_Scheduler_Para_ID == 0)
        {
			setAD_Process_Para_ID (0);
			setAD_Scheduler_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Scheduler_Para (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Scheduler_Para[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Process_Para getAD_Process_Para() throws RuntimeException
    {
		return (I_AD_Process_Para)MTable.get(getCtx(), I_AD_Process_Para.Table_Name)
			.getPO(getAD_Process_Para_ID(), get_TrxName());	}

	/** Set Process Parameter.
		@param AD_Process_Para_ID Process Parameter	  */
	public void setAD_Process_Para_ID (int AD_Process_Para_ID)
	{
		if (AD_Process_Para_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Process_Para_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Process_Para_ID, Integer.valueOf(AD_Process_Para_ID));
	}

	/** Get Process Parameter.
		@return Process Parameter	  */
	public int getAD_Process_Para_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_Para_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Scheduler getAD_Scheduler() throws RuntimeException
    {
		return (I_AD_Scheduler)MTable.get(getCtx(), I_AD_Scheduler.Table_Name)
			.getPO(getAD_Scheduler_ID(), get_TrxName());	}

	/** Set Scheduler.
		@param AD_Scheduler_ID 
		Schedule Processes
	  */
	public void setAD_Scheduler_ID (int AD_Scheduler_ID)
	{
		if (AD_Scheduler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Scheduler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Scheduler_ID, Integer.valueOf(AD_Scheduler_ID));
	}

	/** Get Scheduler.
		@return Schedule Processes
	  */
	public int getAD_Scheduler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Scheduler_ID);
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

	/** Set Default Parameter.
		@param ParameterDefault 
		Default value of the parameter
	  */
	public void setParameterDefault (String ParameterDefault)
	{
		set_Value (COLUMNNAME_ParameterDefault, ParameterDefault);
	}

	/** Get Default Parameter.
		@return Default value of the parameter
	  */
	public String getParameterDefault () 
	{
		return (String)get_Value(COLUMNNAME_ParameterDefault);
	}
}