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

/** Generated Model for M_Tour_Instance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Tour_Instance extends org.compiere.model.PO implements I_M_Tour_Instance, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1802905835L;

    /** Standard Constructor */
    public X_M_Tour_Instance (Properties ctx, int M_Tour_Instance_ID, String trxName)
    {
      super (ctx, M_Tour_Instance_ID, trxName);
      /** if (M_Tour_Instance_ID == 0)
        {
			setDeliveryDate (new Timestamp( System.currentTimeMillis() ));
			setM_Tour_ID (0);
			setM_Tour_Instance_ID (0);
			setProcessed (false);
// N
			setQtyDelivered_LU (Env.ZERO);
// 0
			setQtyDelivered_TU (Env.ZERO);
// 0
			setQtyOrdered_LU (Env.ZERO);
// 0
			setQtyOrdered_TU (Env.ZERO);
// 0
			setQtyToDeliver_LU (Env.ZERO);
// 0
			setQtyToDeliver_TU (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_M_Tour_Instance (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_Tour_Instance[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Lieferdatum.
		@param DeliveryDate Lieferdatum	  */
	@Override
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	/** Get Lieferdatum.
		@return Lieferdatum	  */
	@Override
	public java.sql.Timestamp getDeliveryDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DeliveryDate);
	}

	@Override
	public de.metas.shipping.model.I_M_ShipperTransportation getM_ShipperTransportation() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_ShipperTransportation_ID, de.metas.shipping.model.I_M_ShipperTransportation.class);
	}

	@Override
	public void setM_ShipperTransportation(de.metas.shipping.model.I_M_ShipperTransportation M_ShipperTransportation)
	{
		set_ValueFromPO(COLUMNNAME_M_ShipperTransportation_ID, de.metas.shipping.model.I_M_ShipperTransportation.class, M_ShipperTransportation);
	}

	/** Set Shipper Transportation.
		@param M_ShipperTransportation_ID Shipper Transportation	  */
	@Override
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID)
	{
		if (M_ShipperTransportation_ID < 1) 
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, Integer.valueOf(M_ShipperTransportation_ID));
	}

	/** Get Shipper Transportation.
		@return Shipper Transportation	  */
	@Override
	public int getM_ShipperTransportation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipperTransportation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.tourplanning.model.I_M_Tour getM_Tour() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Tour_ID, de.metas.tourplanning.model.I_M_Tour.class);
	}

	@Override
	public void setM_Tour(de.metas.tourplanning.model.I_M_Tour M_Tour)
	{
		set_ValueFromPO(COLUMNNAME_M_Tour_ID, de.metas.tourplanning.model.I_M_Tour.class, M_Tour);
	}

	/** Set Tour.
		@param M_Tour_ID Tour	  */
	@Override
	public void setM_Tour_ID (int M_Tour_ID)
	{
		if (M_Tour_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Tour_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Tour_ID, Integer.valueOf(M_Tour_ID));
	}

	/** Get Tour.
		@return Tour	  */
	@Override
	public int getM_Tour_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Tour_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tour Instance.
		@param M_Tour_Instance_ID Tour Instance	  */
	@Override
	public void setM_Tour_Instance_ID (int M_Tour_Instance_ID)
	{
		if (M_Tour_Instance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Tour_Instance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Tour_Instance_ID, Integer.valueOf(M_Tour_Instance_ID));
	}

	/** Get Tour Instance.
		@return Tour Instance	  */
	@Override
	public int getM_Tour_Instance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Tour_Instance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Gelieferte Menge (LU).
		@param QtyDelivered_LU 
		Gelieferte Menge (LU)
	  */
	@Override
	public void setQtyDelivered_LU (java.math.BigDecimal QtyDelivered_LU)
	{
		set_Value (COLUMNNAME_QtyDelivered_LU, QtyDelivered_LU);
	}

	/** Get Gelieferte Menge (LU).
		@return Gelieferte Menge (LU)
	  */
	@Override
	public java.math.BigDecimal getQtyDelivered_LU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered_LU);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Gelieferte Menge (TU).
		@param QtyDelivered_TU 
		Gelieferte Menge (TU)
	  */
	@Override
	public void setQtyDelivered_TU (java.math.BigDecimal QtyDelivered_TU)
	{
		set_Value (COLUMNNAME_QtyDelivered_TU, QtyDelivered_TU);
	}

	/** Get Gelieferte Menge (TU).
		@return Gelieferte Menge (TU)
	  */
	@Override
	public java.math.BigDecimal getQtyDelivered_TU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered_TU);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bestellte Menge (LU).
		@param QtyOrdered_LU 
		Bestellte Menge (LU)
	  */
	@Override
	public void setQtyOrdered_LU (java.math.BigDecimal QtyOrdered_LU)
	{
		set_Value (COLUMNNAME_QtyOrdered_LU, QtyOrdered_LU);
	}

	/** Get Bestellte Menge (LU).
		@return Bestellte Menge (LU)
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered_LU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered_LU);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bestellte Menge (TU).
		@param QtyOrdered_TU 
		Bestellte Menge (TU)
	  */
	@Override
	public void setQtyOrdered_TU (java.math.BigDecimal QtyOrdered_TU)
	{
		set_Value (COLUMNNAME_QtyOrdered_TU, QtyOrdered_TU);
	}

	/** Get Bestellte Menge (TU).
		@return Bestellte Menge (TU)
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered_TU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered_TU);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ausliefermenge (LU).
		@param QtyToDeliver_LU Ausliefermenge (LU)	  */
	@Override
	public void setQtyToDeliver_LU (java.math.BigDecimal QtyToDeliver_LU)
	{
		set_Value (COLUMNNAME_QtyToDeliver_LU, QtyToDeliver_LU);
	}

	/** Get Ausliefermenge (LU).
		@return Ausliefermenge (LU)	  */
	@Override
	public java.math.BigDecimal getQtyToDeliver_LU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToDeliver_LU);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ausliefermenge (TU).
		@param QtyToDeliver_TU Ausliefermenge (TU)	  */
	@Override
	public void setQtyToDeliver_TU (java.math.BigDecimal QtyToDeliver_TU)
	{
		set_Value (COLUMNNAME_QtyToDeliver_TU, QtyToDeliver_TU);
	}

	/** Get Ausliefermenge (TU).
		@return Ausliefermenge (TU)	  */
	@Override
	public java.math.BigDecimal getQtyToDeliver_TU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToDeliver_TU);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}