<<<<<<< HEAD
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
=======
// Generated Model - DO NOT CHANGE
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
<<<<<<< HEAD
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
=======
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_PInstance_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_PInstance_Log extends org.compiere.model.PO implements I_AD_PInstance_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -232664924L;

    /** Standard Constructor */
    public X_AD_PInstance_Log (final Properties ctx, final int AD_PInstance_Log_ID, @Nullable final String trxName)
    {
      super (ctx, AD_PInstance_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_PInstance_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }

<<<<<<< HEAD
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
=======

	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_PInstance getAD_PInstance()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(final org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	@Override
	public void setAD_PInstance_ID (final int AD_PInstance_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (AD_PInstance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, AD_PInstance_ID);
	}

	@Override
	public int getAD_PInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_ID);
	}

	@Override
	public void setAD_PInstance_Log_ID (final int AD_PInstance_Log_ID)
	{
		if (AD_PInstance_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_Log_ID, AD_PInstance_Log_ID);
	}

	@Override
	public int getAD_PInstance_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_Log_ID);
	}

	@Override
	public void setLog_ID (final int Log_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Log_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_ValueNoCheck (COLUMNNAME_Log_ID, Log_ID);
	}

	@Override
	public int getLog_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Log_ID);
	}

	@Override
	public void setP_Date (final @Nullable java.sql.Timestamp P_Date)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_P_Date, P_Date);
	}

<<<<<<< HEAD
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
=======
	@Override
	public java.sql.Timestamp getP_Date() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_P_Date);
	}

	@Override
	public void setP_Msg (final @Nullable java.lang.String P_Msg)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_P_Msg, P_Msg);
	}

<<<<<<< HEAD
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
=======
	@Override
	public java.lang.String getP_Msg() 
	{
		return get_ValueAsString(COLUMNNAME_P_Msg);
	}

	@Override
	public void setP_Number (final @Nullable BigDecimal P_Number)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_P_Number, P_Number);
	}

<<<<<<< HEAD
	/** Get Process Number.
		@return Process Parameter
	  */
	public BigDecimal getP_Number () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_Number);
		if (bd == null)
			 return Env.ZERO;
		return bd;
=======
	@Override
	public BigDecimal getP_Number() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_P_Number);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setWarnings (final @Nullable java.lang.String Warnings)
	{
		set_Value (COLUMNNAME_Warnings, Warnings);
	}

	@Override
	public java.lang.String getWarnings() 
	{
		return get_ValueAsString(COLUMNNAME_Warnings);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}