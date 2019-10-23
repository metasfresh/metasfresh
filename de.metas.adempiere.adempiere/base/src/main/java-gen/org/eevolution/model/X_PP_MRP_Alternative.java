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

/** Generated Model for PP_MRP_Alternative
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_MRP_Alternative extends org.compiere.model.PO implements I_PP_MRP_Alternative, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1385899933L;

    /** Standard Constructor */
    public X_PP_MRP_Alternative (Properties ctx, int PP_MRP_Alternative_ID, String trxName)
    {
      super (ctx, PP_MRP_Alternative_ID, trxName);
      /** if (PP_MRP_Alternative_ID == 0)
        {
			setM_Product_ID (0);
			setPP_MRP_ID (0);
			setQty (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_PP_MRP_Alternative (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_PP_MRP_Alternative[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.eevolution.model.I_DD_OrderLine_Alternative getDD_OrderLine_Alternative() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DD_OrderLine_Alternative_ID, org.eevolution.model.I_DD_OrderLine_Alternative.class);
	}

	@Override
	public void setDD_OrderLine_Alternative(org.eevolution.model.I_DD_OrderLine_Alternative DD_OrderLine_Alternative)
	{
		set_ValueFromPO(COLUMNNAME_DD_OrderLine_Alternative_ID, org.eevolution.model.I_DD_OrderLine_Alternative.class, DD_OrderLine_Alternative);
	}

	/** Set Distribution Order Line Alternative.
		@param DD_OrderLine_Alternative_ID Distribution Order Line Alternative	  */
	@Override
	public void setDD_OrderLine_Alternative_ID (int DD_OrderLine_Alternative_ID)
	{
		if (DD_OrderLine_Alternative_ID < 1) 
			set_Value (COLUMNNAME_DD_OrderLine_Alternative_ID, null);
		else 
			set_Value (COLUMNNAME_DD_OrderLine_Alternative_ID, Integer.valueOf(DD_OrderLine_Alternative_ID));
	}

	/** Get Distribution Order Line Alternative.
		@return Distribution Order Line Alternative	  */
	@Override
	public int getDD_OrderLine_Alternative_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DD_OrderLine_Alternative_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_MRP getPP_MRP() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_MRP_ID, org.eevolution.model.I_PP_MRP.class);
	}

	@Override
	public void setPP_MRP(org.eevolution.model.I_PP_MRP PP_MRP)
	{
		set_ValueFromPO(COLUMNNAME_PP_MRP_ID, org.eevolution.model.I_PP_MRP.class, PP_MRP);
	}

	/** Set Material Requirement Planning.
		@param PP_MRP_ID Material Requirement Planning	  */
	@Override
	public void setPP_MRP_ID (int PP_MRP_ID)
	{
		if (PP_MRP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_MRP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_MRP_ID, Integer.valueOf(PP_MRP_ID));
	}

	/** Get Material Requirement Planning.
		@return Material Requirement Planning	  */
	@Override
	public int getPP_MRP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_MRP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class);
	}

	@Override
	public void setPP_Order_BOMLine(org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class, PP_Order_BOMLine);
	}

	/** Set Manufacturing Order BOM Line.
		@param PP_Order_BOMLine_ID Manufacturing Order BOM Line	  */
	@Override
	public void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID)
	{
		if (PP_Order_BOMLine_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_BOMLine_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_BOMLine_ID, Integer.valueOf(PP_Order_BOMLine_ID));
	}

	/** Get Manufacturing Order BOM Line.
		@return Manufacturing Order BOM Line	  */
	@Override
	public int getPP_Order_BOMLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_BOMLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	/** Set Produktionsauftrag.
		@param PP_Order_ID Produktionsauftrag	  */
	@Override
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Produktionsauftrag.
		@return Produktionsauftrag	  */
	@Override
	public int getPP_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}