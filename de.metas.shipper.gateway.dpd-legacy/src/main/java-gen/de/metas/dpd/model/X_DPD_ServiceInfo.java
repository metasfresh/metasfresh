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
package de.metas.dpd.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_AD_Language;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for DPD_ServiceInfo
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_DPD_ServiceInfo extends PO implements I_DPD_ServiceInfo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100128L;

    /** Standard Constructor */
    public X_DPD_ServiceInfo (Properties ctx, int DPD_ServiceInfo_ID, String trxName)
    {
      super (ctx, DPD_ServiceInfo_ID, trxName);
      /** if (DPD_ServiceInfo_ID == 0)
        {
			setAD_Language_ID (0);
			setDPD_FileInfo_ID (0);
			setDPD_ServiceInfo_ID (0);
			setServiceCode (null);
			setServiceFieldInfo (null);
        } */
    }

    /** Load Constructor */
    public X_DPD_ServiceInfo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_DPD_ServiceInfo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Language getAD_Language() throws RuntimeException
    {
		return (I_AD_Language)MTable.get(getCtx(), I_AD_Language.Table_Name)
			.getPO(getAD_Language_ID(), get_TrxName());	}

	/** Set Language ID.
		@param AD_Language_ID Language ID	  */
	public void setAD_Language_ID (int AD_Language_ID)
	{
		if (AD_Language_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Language_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Language_ID, Integer.valueOf(AD_Language_ID));
	}

	/** Get Language ID.
		@return Language ID	  */
	public int getAD_Language_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Language_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.dpd.model.I_DPD_FileInfo getDPD_FileInfo() throws RuntimeException
    {
		return (de.metas.dpd.model.I_DPD_FileInfo)MTable.get(getCtx(), de.metas.dpd.model.I_DPD_FileInfo.Table_Name)
			.getPO(getDPD_FileInfo_ID(), get_TrxName());	}

	/** Set DPD_FileInfo.
		@param DPD_FileInfo_ID DPD_FileInfo	  */
	public void setDPD_FileInfo_ID (int DPD_FileInfo_ID)
	{
		if (DPD_FileInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_FileInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_FileInfo_ID, Integer.valueOf(DPD_FileInfo_ID));
	}

	/** Get DPD_FileInfo.
		@return DPD_FileInfo	  */
	public int getDPD_FileInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_FileInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DPD_ServiceInfo.
		@param DPD_ServiceInfo_ID DPD_ServiceInfo	  */
	public void setDPD_ServiceInfo_ID (int DPD_ServiceInfo_ID)
	{
		if (DPD_ServiceInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_ServiceInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_ServiceInfo_ID, Integer.valueOf(DPD_ServiceInfo_ID));
	}

	/** Get DPD_ServiceInfo.
		@return DPD_ServiceInfo	  */
	public int getDPD_ServiceInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_ServiceInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ServiceCode.
		@param ServiceCode ServiceCode	  */
	public void setServiceCode (String ServiceCode)
	{
		set_ValueNoCheck (COLUMNNAME_ServiceCode, ServiceCode);
	}

	/** Get ServiceCode.
		@return ServiceCode	  */
	public String getServiceCode () 
	{
		return (String)get_Value(COLUMNNAME_ServiceCode);
	}

	/** Set ServiceFieldInfo.
		@param ServiceFieldInfo ServiceFieldInfo	  */
	public void setServiceFieldInfo (String ServiceFieldInfo)
	{
		set_ValueNoCheck (COLUMNNAME_ServiceFieldInfo, ServiceFieldInfo);
	}

	/** Get ServiceFieldInfo.
		@return ServiceFieldInfo	  */
	public String getServiceFieldInfo () 
	{
		return (String)get_Value(COLUMNNAME_ServiceFieldInfo);
	}
}
