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
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for PP_MRP_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_MRP_Alloc extends org.compiere.model.PO implements I_PP_MRP_Alloc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 993974804L;

    /** Standard Constructor */
    public X_PP_MRP_Alloc (Properties ctx, int PP_MRP_Alloc_ID, String trxName)
    {
      super (ctx, PP_MRP_Alloc_ID, trxName);
      /** if (PP_MRP_Alloc_ID == 0)
        {
			setPP_MRP_Demand_ID (0);
			setPP_MRP_Supply_ID (0);
        } */
    }

    /** Load Constructor */
    public X_PP_MRP_Alloc (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_PP_MRP_Alloc[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.eevolution.model.I_PP_MRP getPP_MRP_Demand() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_MRP_Demand_ID, org.eevolution.model.I_PP_MRP.class);
	}

	@Override
	public void setPP_MRP_Demand(org.eevolution.model.I_PP_MRP PP_MRP_Demand)
	{
		set_ValueFromPO(COLUMNNAME_PP_MRP_Demand_ID, org.eevolution.model.I_PP_MRP.class, PP_MRP_Demand);
	}

	/** Set MRP Demand.
		@param PP_MRP_Demand_ID MRP Demand	  */
	@Override
	public void setPP_MRP_Demand_ID (int PP_MRP_Demand_ID)
	{
		if (PP_MRP_Demand_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_MRP_Demand_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_MRP_Demand_ID, Integer.valueOf(PP_MRP_Demand_ID));
	}

	/** Get MRP Demand.
		@return MRP Demand	  */
	@Override
	public int getPP_MRP_Demand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_MRP_Demand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_MRP getPP_MRP_Supply() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_MRP_Supply_ID, org.eevolution.model.I_PP_MRP.class);
	}

	@Override
	public void setPP_MRP_Supply(org.eevolution.model.I_PP_MRP PP_MRP_Supply)
	{
		set_ValueFromPO(COLUMNNAME_PP_MRP_Supply_ID, org.eevolution.model.I_PP_MRP.class, PP_MRP_Supply);
	}

	/** Set MRP Supply.
		@param PP_MRP_Supply_ID MRP Supply	  */
	@Override
	public void setPP_MRP_Supply_ID (int PP_MRP_Supply_ID)
	{
		if (PP_MRP_Supply_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_MRP_Supply_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_MRP_Supply_ID, Integer.valueOf(PP_MRP_Supply_ID));
	}

	/** Get MRP Supply.
		@return MRP Supply	  */
	@Override
	public int getPP_MRP_Supply_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_MRP_Supply_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zugewiesene Menge.
		@param QtyAllocated Zugewiesene Menge	  */
	@Override
	public void setQtyAllocated (java.math.BigDecimal QtyAllocated)
	{
		set_Value (COLUMNNAME_QtyAllocated, QtyAllocated);
	}

	/** Get Zugewiesene Menge.
		@return Zugewiesene Menge	  */
	@Override
	public java.math.BigDecimal getQtyAllocated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAllocated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}