/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_Reservation
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_Reservation extends org.compiere.model.PO implements I_M_HU_Reservation, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1667084167L;

    /** Standard Constructor */
    public X_M_HU_Reservation (Properties ctx, int M_HU_Reservation_ID, String trxName)
    {
      super (ctx, M_HU_Reservation_ID, trxName);
      /** if (M_HU_Reservation_ID == 0)
        {
			setC_BPartner_Customer_ID (0);
			setC_UOM_ID (0);
			setM_HU_Reservation_ID (0);
			setQtyReserved (BigDecimal.ZERO);
			setVHU_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_HU_Reservation (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Kunde.
		@param C_BPartner_Customer_ID Kunde	  */
	@Override
	public void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, Integer.valueOf(C_BPartner_Customer_ID));
	}

	/** Get Kunde.
		@return Kunde	  */
	@Override
	public int getC_BPartner_Customer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Customer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLineSO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLineSO(org.compiere.model.I_C_OrderLine C_OrderLineSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLineSO);
	}

	/** Set Auftragsposition.
		@param C_OrderLineSO_ID 
		Auftragsposition
	  */
	@Override
	public void setC_OrderLineSO_ID (int C_OrderLineSO_ID)
	{
		if (C_OrderLineSO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLineSO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLineSO_ID, Integer.valueOf(C_OrderLineSO_ID));
	}

	/** Get Auftragsposition.
		@return Auftragsposition
	  */
	@Override
	public int getC_OrderLineSO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLineSO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_HU_Reservation.
		@param M_HU_Reservation_ID M_HU_Reservation	  */
	@Override
	public void setM_HU_Reservation_ID (int M_HU_Reservation_ID)
	{
		if (M_HU_Reservation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Reservation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Reservation_ID, Integer.valueOf(M_HU_Reservation_ID));
	}

	/** Get M_HU_Reservation.
		@return M_HU_Reservation	  */
	@Override
	public int getM_HU_Reservation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Reservation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Offen.
		@param QtyReserved 
		Offene Menge
	  */
	@Override
	public void setQtyReserved (java.math.BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	/** Get Offen.
		@return Offene Menge
	  */
	@Override
	public java.math.BigDecimal getQtyReserved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReserved);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getVHU()
	{
		return get_ValueAsPO(COLUMNNAME_VHU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setVHU(de.metas.handlingunits.model.I_M_HU VHU)
	{
		set_ValueFromPO(COLUMNNAME_VHU_ID, de.metas.handlingunits.model.I_M_HU.class, VHU);
	}

	/** Set CU Handling Unit (VHU).
		@param VHU_ID CU Handling Unit (VHU)	  */
	@Override
	public void setVHU_ID (int VHU_ID)
	{
		if (VHU_ID < 1) 
			set_Value (COLUMNNAME_VHU_ID, null);
		else 
			set_Value (COLUMNNAME_VHU_ID, Integer.valueOf(VHU_ID));
	}

	/** Get CU Handling Unit (VHU).
		@return CU Handling Unit (VHU)	  */
	@Override
	public int getVHU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_VHU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}