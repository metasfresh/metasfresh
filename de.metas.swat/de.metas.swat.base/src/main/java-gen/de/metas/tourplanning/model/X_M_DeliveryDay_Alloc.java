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
package de.metas.tourplanning.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_DeliveryDay_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_DeliveryDay_Alloc extends org.compiere.model.PO implements I_M_DeliveryDay_Alloc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1069078495L;

    /** Standard Constructor */
    public X_M_DeliveryDay_Alloc (Properties ctx, int M_DeliveryDay_Alloc_ID, String trxName)
    {
      super (ctx, M_DeliveryDay_Alloc_ID, trxName);
      /** if (M_DeliveryDay_Alloc_ID == 0)
        {
			setAD_Table_ID (0);
			setM_DeliveryDay_Alloc_ID (0);
			setM_DeliveryDay_ID (0);
			setM_Product_ID (0);
			setQtyDelivered (Env.ZERO);
// 0
			setQtyOrdered (Env.ZERO);
// 0
			setQtyToDeliver (Env.ZERO);
// 0
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_DeliveryDay_Alloc (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_DeliveryDay_Alloc[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Delivery Day (alloc).
		@param M_DeliveryDay_Alloc_ID Delivery Day (alloc)	  */
	@Override
	public void setM_DeliveryDay_Alloc_ID (int M_DeliveryDay_Alloc_ID)
	{
		if (M_DeliveryDay_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DeliveryDay_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DeliveryDay_Alloc_ID, Integer.valueOf(M_DeliveryDay_Alloc_ID));
	}

	/** Get Delivery Day (alloc).
		@return Delivery Day (alloc)	  */
	@Override
	public int getM_DeliveryDay_Alloc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DeliveryDay_Alloc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.tourplanning.model.I_M_DeliveryDay getM_DeliveryDay() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_DeliveryDay_ID, de.metas.tourplanning.model.I_M_DeliveryDay.class);
	}

	@Override
	public void setM_DeliveryDay(de.metas.tourplanning.model.I_M_DeliveryDay M_DeliveryDay)
	{
		set_ValueFromPO(COLUMNNAME_M_DeliveryDay_ID, de.metas.tourplanning.model.I_M_DeliveryDay.class, M_DeliveryDay);
	}

	/** Set Delivery Days.
		@param M_DeliveryDay_ID Delivery Days	  */
	@Override
	public void setM_DeliveryDay_ID (int M_DeliveryDay_ID)
	{
		if (M_DeliveryDay_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DeliveryDay_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DeliveryDay_ID, Integer.valueOf(M_DeliveryDay_ID));
	}

	/** Get Delivery Days.
		@return Delivery Days	  */
	@Override
	public int getM_DeliveryDay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DeliveryDay_ID);
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
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** Set Gelieferte Menge.
		@param QtyDelivered 
		Gelieferte Menge
	  */
	@Override
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Gelieferte Menge.
		@return Gelieferte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bestellte Menge.
		@param QtyOrdered 
		Bestellte Menge
	  */
	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellte Menge.
		@return Bestellte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ausliefermenge.
		@param QtyToDeliver Ausliefermenge	  */
	@Override
	public void setQtyToDeliver (java.math.BigDecimal QtyToDeliver)
	{
		set_Value (COLUMNNAME_QtyToDeliver, QtyToDeliver);
	}

	/** Get Ausliefermenge.
		@return Ausliefermenge	  */
	@Override
	public java.math.BigDecimal getQtyToDeliver () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToDeliver);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}