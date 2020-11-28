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

/** Generated Model for AD_DesktopWorkbench
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_DesktopWorkbench extends PO implements I_AD_DesktopWorkbench, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_DesktopWorkbench (Properties ctx, int AD_DesktopWorkbench_ID, String trxName)
    {
      super (ctx, AD_DesktopWorkbench_ID, trxName);
      /** if (AD_DesktopWorkbench_ID == 0)
        {
			setAD_Desktop_ID (0);
			setAD_DesktopWorkbench_ID (0);
			setAD_Workbench_ID (0);
			setSeqNo (0);
        } */
    }

    /** Load Constructor */
    public X_AD_DesktopWorkbench (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_AD_DesktopWorkbench[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Desktop getAD_Desktop() throws RuntimeException
    {
		return (I_AD_Desktop)MTable.get(getCtx(), I_AD_Desktop.Table_Name)
			.getPO(getAD_Desktop_ID(), get_TrxName());	}

	/** Set Desktop.
		@param AD_Desktop_ID 
		Collection of Workbenches
	  */
	public void setAD_Desktop_ID (int AD_Desktop_ID)
	{
		if (AD_Desktop_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Desktop_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Desktop_ID, Integer.valueOf(AD_Desktop_ID));
	}

	/** Get Desktop.
		@return Collection of Workbenches
	  */
	public int getAD_Desktop_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Desktop_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Desktop Workbench.
		@param AD_DesktopWorkbench_ID Desktop Workbench	  */
	public void setAD_DesktopWorkbench_ID (int AD_DesktopWorkbench_ID)
	{
		if (AD_DesktopWorkbench_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_DesktopWorkbench_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_DesktopWorkbench_ID, Integer.valueOf(AD_DesktopWorkbench_ID));
	}

	/** Get Desktop Workbench.
		@return Desktop Workbench	  */
	public int getAD_DesktopWorkbench_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_DesktopWorkbench_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Workbench getAD_Workbench() throws RuntimeException
    {
		return (I_AD_Workbench)MTable.get(getCtx(), I_AD_Workbench.Table_Name)
			.getPO(getAD_Workbench_ID(), get_TrxName());	}

	/** Set Workbench.
		@param AD_Workbench_ID 
		Collection of windows, reports
	  */
	public void setAD_Workbench_ID (int AD_Workbench_ID)
	{
		if (AD_Workbench_ID < 1) 
			set_Value (COLUMNNAME_AD_Workbench_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workbench_ID, Integer.valueOf(AD_Workbench_ID));
	}

	/** Get Workbench.
		@return Collection of windows, reports
	  */
	public int getAD_Workbench_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Workbench_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_Workbench_ID()));
    }

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}