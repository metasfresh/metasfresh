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

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for DPD_Route
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_DPD_Route extends PO implements I_DPD_Route, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100128L;

    /** Standard Constructor */
    public X_DPD_Route (Properties ctx, int DPD_Route_ID, String trxName)
    {
      super (ctx, DPD_Route_ID, trxName);
      /** if (DPD_Route_ID == 0)
        {
			setBarcodeID (null);
			setD_Depot (null);
			setDPD_FileInfo_ID (0);
			setDPD_Route_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DPD_Route (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_DPD_Route[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set BarcodeID.
		@param BarcodeID BarcodeID	  */
	public void setBarcodeID (String BarcodeID)
	{
		set_Value (COLUMNNAME_BarcodeID, BarcodeID);
	}

	/** Get BarcodeID.
		@return BarcodeID	  */
	public String getBarcodeID () 
	{
		return (String)get_Value(COLUMNNAME_BarcodeID);
	}

	/** Set BeginPostCode.
		@param BeginPostCode BeginPostCode	  */
	public void setBeginPostCode (String BeginPostCode)
	{
		set_Value (COLUMNNAME_BeginPostCode, BeginPostCode);
	}

	/** Get BeginPostCode.
		@return BeginPostCode	  */
	public String getBeginPostCode () 
	{
		return (String)get_Value(COLUMNNAME_BeginPostCode);
	}

	/** Set ISO Laendercode.
		@param CountryCode 
		Zweibuchstabiger ISO Laendercode gemaess ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	public void setCountryCode (String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	/** Get ISO Laendercode.
		@return Zweibuchstabiger ISO Laendercode gemaess ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	public String getCountryCode () 
	{
		return (String)get_Value(COLUMNNAME_CountryCode);
	}

	/** Set D_Depot.
		@param D_Depot D_Depot	  */
	public void setD_Depot (String D_Depot)
	{
		set_Value (COLUMNNAME_D_Depot, D_Depot);
	}

	/** Get D_Depot.
		@return D_Depot	  */
	public String getD_Depot () 
	{
		return (String)get_Value(COLUMNNAME_D_Depot);
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

	/** Set DPD_Route.
		@param DPD_Route_ID DPD_Route	  */
	public void setDPD_Route_ID (int DPD_Route_ID)
	{
		if (DPD_Route_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_Route_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_Route_ID, Integer.valueOf(DPD_Route_ID));
	}

	/** Get DPD_Route.
		@return DPD_Route	  */
	public int getDPD_Route_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_Route_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Destination Sort.
		@param D_Sort Destination Sort	  */
	public void setD_Sort (String D_Sort)
	{
		set_Value (COLUMNNAME_D_Sort, D_Sort);
	}

	/** Get Destination Sort.
		@return Destination Sort	  */
	public String getD_Sort () 
	{
		return (String)get_Value(COLUMNNAME_D_Sort);
	}

	/** Set EndPostCode.
		@param EndPostCode EndPostCode	  */
	public void setEndPostCode (String EndPostCode)
	{
		set_Value (COLUMNNAME_EndPostCode, EndPostCode);
	}

	/** Get EndPostCode.
		@return EndPostCode	  */
	public String getEndPostCode () 
	{
		return (String)get_Value(COLUMNNAME_EndPostCode);
	}

	/** Set GroupingPriority.
		@param GroupingPriority GroupingPriority	  */
	public void setGroupingPriority (String GroupingPriority)
	{
		set_Value (COLUMNNAME_GroupingPriority, GroupingPriority);
	}

	/** Get GroupingPriority.
		@return GroupingPriority	  */
	public String getGroupingPriority () 
	{
		return (String)get_Value(COLUMNNAME_GroupingPriority);
	}

	/** Set Origin Sort.
		@param O_Sort Origin Sort	  */
	public void setO_Sort (String O_Sort)
	{
		set_Value (COLUMNNAME_O_Sort, O_Sort);
	}

	/** Get Origin Sort.
		@return Origin Sort	  */
	public String getO_Sort () 
	{
		return (String)get_Value(COLUMNNAME_O_Sort);
	}

	/** Set RoutingPlaces.
		@param RoutingPlaces RoutingPlaces	  */
	public void setRoutingPlaces (String RoutingPlaces)
	{
		set_Value (COLUMNNAME_RoutingPlaces, RoutingPlaces);
	}

	/** Get RoutingPlaces.
		@return RoutingPlaces	  */
	public String getRoutingPlaces () 
	{
		return (String)get_Value(COLUMNNAME_RoutingPlaces);
	}

	/** Set SendingDate.
		@param SendingDate SendingDate	  */
	public void setSendingDate (String SendingDate)
	{
		set_Value (COLUMNNAME_SendingDate, SendingDate);
	}

	/** Get SendingDate.
		@return SendingDate	  */
	public String getSendingDate () 
	{
		return (String)get_Value(COLUMNNAME_SendingDate);
	}

	/** Set ServiceCodes.
		@param ServiceCodes ServiceCodes	  */
	public void setServiceCodes (String ServiceCodes)
	{
		set_Value (COLUMNNAME_ServiceCodes, ServiceCodes);
	}

	/** Get ServiceCodes.
		@return ServiceCodes	  */
	public String getServiceCodes () 
	{
		return (String)get_Value(COLUMNNAME_ServiceCodes);
	}
}
