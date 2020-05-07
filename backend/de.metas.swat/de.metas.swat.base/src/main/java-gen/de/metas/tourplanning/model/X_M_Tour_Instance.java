/** Generated Model - DO NOT CHANGE */
package de.metas.tourplanning.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Tour_Instance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Tour_Instance extends org.compiere.model.PO implements I_M_Tour_Instance, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1581472456L;

    /** Standard Constructor */
    public X_M_Tour_Instance (Properties ctx, int M_Tour_Instance_ID, String trxName)
    {
      super (ctx, M_Tour_Instance_ID, trxName);
      /** if (M_Tour_Instance_ID == 0)
        {
			setDeliveryDate (new Timestamp( System.currentTimeMillis() ));
			setM_Tour_ID (0);
			setM_Tour_Instance_ID (0);
			setProcessed (false); // N
			setQtyDelivered_LU (BigDecimal.ZERO); // 0
			setQtyDelivered_TU (BigDecimal.ZERO); // 0
			setQtyOrdered_LU (BigDecimal.ZERO); // 0
			setQtyOrdered_TU (BigDecimal.ZERO); // 0
			setQtyToDeliver_LU (BigDecimal.ZERO); // 0
			setQtyToDeliver_TU (BigDecimal.ZERO); // 0
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

	/** Set Transport Auftrag.
		@param M_ShipperTransportation_ID Transport Auftrag	  */
	@Override
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID)
	{
		if (M_ShipperTransportation_ID < 1) 
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, Integer.valueOf(M_ShipperTransportation_ID));
	}

	/** Get Transport Auftrag.
		@return Transport Auftrag	  */
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
			 return BigDecimal.ZERO;
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
			 return BigDecimal.ZERO;
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
			 return BigDecimal.ZERO;
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
			 return BigDecimal.ZERO;
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
			 return BigDecimal.ZERO;
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
			 return BigDecimal.ZERO;
		return bd;
	}
}