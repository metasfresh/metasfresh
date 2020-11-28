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

/** Generated Model for CM_Container_URL
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_CM_Container_URL extends PO implements I_CM_Container_URL, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_CM_Container_URL (Properties ctx, int CM_Container_URL_ID, String trxName)
    {
      super (ctx, CM_Container_URL_ID, trxName);
      /** if (CM_Container_URL_ID == 0)
        {
			setChecked (new Timestamp( System.currentTimeMillis() ));
			setCM_Container_ID (0);
			setCM_Container_URL_ID (0);
			setLast_Result (null);
			setStatus (null);
        } */
    }

    /** Load Constructor */
    public X_CM_Container_URL (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CM_Container_URL[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Last Checked.
		@param Checked 
		Info when we did the last check
	  */
	public void setChecked (Timestamp Checked)
	{
		set_Value (COLUMNNAME_Checked, Checked);
	}

	/** Get Last Checked.
		@return Info when we did the last check
	  */
	public Timestamp getChecked () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Checked);
	}

	public I_CM_Container getCM_Container() throws RuntimeException
    {
		return (I_CM_Container)MTable.get(getCtx(), I_CM_Container.Table_Name)
			.getPO(getCM_Container_ID(), get_TrxName());	}

	/** Set Web Container.
		@param CM_Container_ID 
		Web Container contains content like images, text etc.
	  */
	public void setCM_Container_ID (int CM_Container_ID)
	{
		if (CM_Container_ID < 1) 
			set_Value (COLUMNNAME_CM_Container_ID, null);
		else 
			set_Value (COLUMNNAME_CM_Container_ID, Integer.valueOf(CM_Container_ID));
	}

	/** Get Web Container.
		@return Web Container contains content like images, text etc.
	  */
	public int getCM_Container_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Container_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Container URL.
		@param CM_Container_URL_ID 
		Contains info on used URLs
	  */
	public void setCM_Container_URL_ID (int CM_Container_URL_ID)
	{
		if (CM_Container_URL_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_Container_URL_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_Container_URL_ID, Integer.valueOf(CM_Container_URL_ID));
	}

	/** Get Container URL.
		@return Contains info on used URLs
	  */
	public int getCM_Container_URL_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Container_URL_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Last Result.
		@param Last_Result 
		Contains data on the last check result
	  */
	public void setLast_Result (String Last_Result)
	{
		set_Value (COLUMNNAME_Last_Result, Last_Result);
	}

	/** Get Last Result.
		@return Contains data on the last check result
	  */
	public String getLast_Result () 
	{
		return (String)get_Value(COLUMNNAME_Last_Result);
	}

	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	public void setStatus (String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	public String getStatus () 
	{
		return (String)get_Value(COLUMNNAME_Status);
	}
}