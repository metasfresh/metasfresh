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

import org.compiere.model.I_M_Package;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for M_PackageInfo
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_PackageInfo extends PO implements I_M_PackageInfo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100127L;

    /** Standard Constructor */
    public X_M_PackageInfo (Properties ctx, int M_PackageInfo_ID, String trxName)
    {
      super (ctx, M_PackageInfo_ID, trxName);
      /** if (M_PackageInfo_ID == 0)
        {
			setM_Package_ID (0);
			setM_PackageInfo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_PackageInfo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_PackageInfo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set BarcodeInfo.
		@param BarcodeInfo BarcodeInfo	  */
	public void setBarcodeInfo (String BarcodeInfo)
	{
		set_ValueNoCheck (COLUMNNAME_BarcodeInfo, BarcodeInfo);
	}

	/** Get BarcodeInfo.
		@return BarcodeInfo	  */
	public String getBarcodeInfo () 
	{
		return (String)get_Value(COLUMNNAME_BarcodeInfo);
	}

	/** Set Destination Sort.
		@param D_Sort Destination Sort	  */
	public void setD_Sort (String D_Sort)
	{
		set_ValueNoCheck (COLUMNNAME_D_Sort, D_Sort);
	}

	/** Get Destination Sort.
		@return Destination Sort	  */
	public String getD_Sort () 
	{
		return (String)get_Value(COLUMNNAME_D_Sort);
	}

	public I_M_Package getM_Package() throws RuntimeException
    {
		return (I_M_Package)MTable.get(getCtx(), I_M_Package.Table_Name)
			.getPO(getM_Package_ID(), get_TrxName());	}

	/** Set Packstueck.
		@param M_Package_ID 
		Shipment Package
	  */
	public void setM_Package_ID (int M_Package_ID)
	{
		if (M_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, Integer.valueOf(M_Package_ID));
	}

	/** Get Packstueck.
		@return Shipment Package
	  */
	public int getM_Package_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Package_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_PackageInfo.
		@param M_PackageInfo_ID M_PackageInfo	  */
	public void setM_PackageInfo_ID (int M_PackageInfo_ID)
	{
		if (M_PackageInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PackageInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PackageInfo_ID, Integer.valueOf(M_PackageInfo_ID));
	}

	/** Get M_PackageInfo.
		@return M_PackageInfo	  */
	public int getM_PackageInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PackageInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Origin Sort.
		@param O_Sort Origin Sort	  */
	public void setO_Sort (String O_Sort)
	{
		set_ValueNoCheck (COLUMNNAME_O_Sort, O_Sort);
	}

	/** Get Origin Sort.
		@return Origin Sort	  */
	public String getO_Sort () 
	{
		return (String)get_Value(COLUMNNAME_O_Sort);
	}

	/** Set RoutingText.
		@param RoutingText RoutingText	  */
	public void setRoutingText (String RoutingText)
	{
		set_ValueNoCheck (COLUMNNAME_RoutingText, RoutingText);
	}

	/** Get RoutingText.
		@return RoutingText	  */
	public String getRoutingText () 
	{
		return (String)get_Value(COLUMNNAME_RoutingText);
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

	/** Set ServiceMark.
		@param ServiceMark ServiceMark	  */
	public void setServiceMark (String ServiceMark)
	{
		set_ValueNoCheck (COLUMNNAME_ServiceMark, ServiceMark);
	}

	/** Get ServiceMark.
		@return ServiceMark	  */
	public String getServiceMark () 
	{
		return (String)get_Value(COLUMNNAME_ServiceMark);
	}

	/** Set ServiceText.
		@param ServiceText ServiceText	  */
	public void setServiceText (String ServiceText)
	{
		set_ValueNoCheck (COLUMNNAME_ServiceText, ServiceText);
	}

	/** Get ServiceText.
		@return ServiceText	  */
	public String getServiceText () 
	{
		return (String)get_Value(COLUMNNAME_ServiceText);
	}

	/** Set ServiceValue.
		@param ServiceValue ServiceValue	  */
	public void setServiceValue (String ServiceValue)
	{
		set_ValueNoCheck (COLUMNNAME_ServiceValue, ServiceValue);
	}

	/** Get ServiceValue.
		@return ServiceValue	  */
	public String getServiceValue () 
	{
		return (String)get_Value(COLUMNNAME_ServiceValue);
	}

	/** Set Preislistenversion.
		@param Version 
		Version of the table definition
	  */
	public void setVersion (String Version)
	{
		set_ValueNoCheck (COLUMNNAME_Version, Version);
	}

	/** Get Preislistenversion.
		@return Version of the table definition
	  */
	public String getVersion () 
	{
		return (String)get_Value(COLUMNNAME_Version);
	}
}
