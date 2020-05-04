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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for AD_PInstance_Log
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_PInstance_Log extends PO implements I_AD_PInstance_Log, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_PInstance_Log (Properties ctx, int AD_PInstance_Log_ID, String trxName)
    {
      super (ctx, AD_PInstance_Log_ID, trxName);
      /** if (AD_PInstance_Log_ID == 0)
        {
			setAD_PInstance_ID (0);
			setLog_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_PInstance_Log (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_PInstance_Log[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_PInstance getAD_PInstance() throws RuntimeException
    {
		return (I_AD_PInstance)MTable.get(getCtx(), I_AD_PInstance.Table_Name)
			.getPO(getAD_PInstance_ID(), get_TrxName());	}

	/** Set Process Instance.
		@param AD_PInstance_ID 
		Instance of the process
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
	}

	/** Get Process Instance.
		@return Instance of the process
	  */
	public int getAD_PInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Log.
		@param Log_ID Log	  */
	public void setLog_ID (int Log_ID)
	{
		if (Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Log_ID, Integer.valueOf(Log_ID));
	}

	/** Get Log.
		@return Log	  */
	public int getLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Log_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Date.
		@param P_Date 
		Process Parameter
	  */
	public void setP_Date (Timestamp P_Date)
	{
		set_ValueNoCheck (COLUMNNAME_P_Date, P_Date);
	}

	/** Get Process Date.
		@return Process Parameter
	  */
	public Timestamp getP_Date () 
	{
		return (Timestamp)get_Value(COLUMNNAME_P_Date);
	}

	/** Set Process ID.
		@param P_ID Process ID	  */
	public void setP_ID (int P_ID)
	{
		if (P_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_P_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_P_ID, Integer.valueOf(P_ID));
	}

	/** Get Process ID.
		@return Process ID	  */
	public int getP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Message.
		@param P_Msg Process Message	  */
	public void setP_Msg (String P_Msg)
	{
		set_ValueNoCheck (COLUMNNAME_P_Msg, P_Msg);
	}

	/** Get Process Message.
		@return Process Message	  */
	public String getP_Msg () 
	{
		return (String)get_Value(COLUMNNAME_P_Msg);
	}

	/** Set Process Number.
		@param P_Number 
		Process Parameter
	  */
	public void setP_Number (BigDecimal P_Number)
	{
		set_ValueNoCheck (COLUMNNAME_P_Number, P_Number);
	}

	/** Get Process Number.
		@return Process Parameter
	  */
	public BigDecimal getP_Number () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_Number);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}