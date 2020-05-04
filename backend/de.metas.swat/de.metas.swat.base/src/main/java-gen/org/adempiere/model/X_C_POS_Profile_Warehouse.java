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

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_POS_Profile_Warehouse
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_POS_Profile_Warehouse extends org.compiere.model.PO implements I_C_POS_Profile_Warehouse, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 251246636L;

    /** Standard Constructor */
    public X_C_POS_Profile_Warehouse (Properties ctx, int C_POS_Profile_Warehouse_ID, String trxName)
    {
      super (ctx, C_POS_Profile_Warehouse_ID, trxName);
      /** if (C_POS_Profile_Warehouse_ID == 0)
        {
			setC_POS_Profile_ID (0);
			setC_POS_Profile_Warehouse_ID (0);
			setM_Warehouse_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_POS_Profile_Warehouse (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_POS_Profile_Warehouse[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public I_C_POS_Profile getC_POS_Profile() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_POS_Profile_ID, I_C_POS_Profile.class);
	}

	@Override
	public void setC_POS_Profile(I_C_POS_Profile C_POS_Profile)
	{
		set_ValueFromPO(COLUMNNAME_C_POS_Profile_ID, I_C_POS_Profile.class, C_POS_Profile);
	}

	/** Set POS Profile.
		@param C_POS_Profile_ID POS Profile	  */
	@Override
	public void setC_POS_Profile_ID (int C_POS_Profile_ID)
	{
		if (C_POS_Profile_ID < 1) 
			set_Value (COLUMNNAME_C_POS_Profile_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_Profile_ID, Integer.valueOf(C_POS_Profile_ID));
	}

	/** Get POS Profile.
		@return POS Profile	  */
	@Override
	public int getC_POS_Profile_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_POS_Profile_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set POS Profile Warehouse.
		@param C_POS_Profile_Warehouse_ID POS Profile Warehouse	  */
	@Override
	public void setC_POS_Profile_Warehouse_ID (int C_POS_Profile_Warehouse_ID)
	{
		if (C_POS_Profile_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_Profile_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_Profile_Warehouse_ID, Integer.valueOf(C_POS_Profile_Warehouse_ID));
	}

	/** Get POS Profile Warehouse.
		@return POS Profile Warehouse	  */
	@Override
	public int getC_POS_Profile_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_POS_Profile_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Lager oder Ort für Dienstleistung
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Lager oder Ort für Dienstleistung
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}