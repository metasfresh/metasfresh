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

/** Generated Model for DPD_Service
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_DPD_Service extends PO implements I_DPD_Service, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100128L;

    /** Standard Constructor */
    public X_DPD_Service (Properties ctx, int DPD_Service_ID, String trxName)
    {
      super (ctx, DPD_Service_ID, trxName);
      /** if (DPD_Service_ID == 0)
        {
			setDPD_FileInfo_ID (0);
			setDPD_Service_ID (0);
			setServiceCode (null);
			setServiceElements (null);
			setServiceText (null);
        } */
    }

    /** Load Constructor */
    public X_DPD_Service (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_DPD_Service[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set DPD_Service.
		@param DPD_Service_ID DPD_Service	  */
	public void setDPD_Service_ID (int DPD_Service_ID)
	{
		if (DPD_Service_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_Service_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_Service_ID, Integer.valueOf(DPD_Service_ID));
	}

	/** Get DPD_Service.
		@return DPD_Service	  */
	public int getDPD_Service_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_Service_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ServiceCode.
		@param ServiceCode ServiceCode	  */
	public void setServiceCode (String ServiceCode)
	{
		set_Value (COLUMNNAME_ServiceCode, ServiceCode);
	}

	/** Get ServiceCode.
		@return ServiceCode	  */
	public String getServiceCode () 
	{
		return (String)get_Value(COLUMNNAME_ServiceCode);
	}

	/** Set ServiceElements.
		@param ServiceElements ServiceElements	  */
	public void setServiceElements (String ServiceElements)
	{
		set_Value (COLUMNNAME_ServiceElements, ServiceElements);
	}

	/** Get ServiceElements.
		@return ServiceElements	  */
	public String getServiceElements () 
	{
		return (String)get_Value(COLUMNNAME_ServiceElements);
	}

	/** Set ServiceMark.
		@param ServiceMark ServiceMark	  */
	public void setServiceMark (String ServiceMark)
	{
		set_Value (COLUMNNAME_ServiceMark, ServiceMark);
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
		set_Value (COLUMNNAME_ServiceText, ServiceText);
	}

	/** Get ServiceText.
		@return ServiceText	  */
	public String getServiceText () 
	{
		return (String)get_Value(COLUMNNAME_ServiceText);
	}
}
