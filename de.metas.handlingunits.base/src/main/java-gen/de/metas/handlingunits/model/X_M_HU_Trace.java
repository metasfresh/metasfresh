/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_Trace
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_Trace extends org.compiere.model.PO implements I_M_HU_Trace, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 237124240L;

    /** Standard Constructor */
    public X_M_HU_Trace (Properties ctx, int M_HU_Trace_ID, String trxName)
    {
      super (ctx, M_HU_Trace_ID, trxName);
      /** if (M_HU_Trace_ID == 0)
        {
			setM_HU_ID (0);
			setM_HU_Trace_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_HU_Trace (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Zeitpunkt.
		@param EventTime Zeitpunkt	  */
	@Override
	public void setEventTime (java.sql.Timestamp EventTime)
	{
		set_Value (COLUMNNAME_EventTime, EventTime);
	}

	/** Get Zeitpunkt.
		@return Zeitpunkt	  */
	@Override
	public java.sql.Timestamp getEventTime () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EventTime);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	/** Set Handling Units.
		@param M_HU_ID Handling Units	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
	}

	/** Get Handling Units.
		@return Handling Units	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU_Source() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Source_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU_Source(de.metas.handlingunits.model.I_M_HU M_HU_Source)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Source_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU_Source);
	}

	/** Set Quell-HU.
		@param M_HU_Source_ID Quell-HU	  */
	@Override
	public void setM_HU_Source_ID (int M_HU_Source_ID)
	{
		if (M_HU_Source_ID < 1) 
			set_Value (COLUMNNAME_M_HU_Source_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_Source_ID, Integer.valueOf(M_HU_Source_ID));
	}

	/** Get Quell-HU.
		@return Quell-HU	  */
	@Override
	public int getM_HU_Source_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Source_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rückverfolgbarkeit.
		@param M_HU_Trace_ID Rückverfolgbarkeit	  */
	@Override
	public void setM_HU_Trace_ID (int M_HU_Trace_ID)
	{
		if (M_HU_Trace_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trace_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trace_ID, Integer.valueOf(M_HU_Trace_ID));
	}

	/** Get Rückverfolgbarkeit.
		@return Rückverfolgbarkeit	  */
	@Override
	public int getM_HU_Trace_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Trace_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	/** Set Lieferung/Wareneingang.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	@Override
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Lieferung/Wareneingang.
		@return Material Shipment Document
	  */
	@Override
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Movement_ID, org.compiere.model.I_M_Movement.class);
	}

	@Override
	public void setM_Movement(org.compiere.model.I_M_Movement M_Movement)
	{
		set_ValueFromPO(COLUMNNAME_M_Movement_ID, org.compiere.model.I_M_Movement.class, M_Movement);
	}

	/** Set Warenbewegung.
		@param M_Movement_ID 
		Bewegung von Warenbestand
	  */
	@Override
	public void setM_Movement_ID (int M_Movement_ID)
	{
		if (M_Movement_ID < 1) 
			set_Value (COLUMNNAME_M_Movement_ID, null);
		else 
			set_Value (COLUMNNAME_M_Movement_ID, Integer.valueOf(M_Movement_ID));
	}

	/** Get Warenbewegung.
		@return Bewegung von Warenbestand
	  */
	@Override
	public int getM_Movement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Movement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_ShipmentSchedule_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
	}

	@Override
	public void setM_ShipmentSchedule(de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule)
	{
		set_ValueFromPO(COLUMNNAME_M_ShipmentSchedule_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class, M_ShipmentSchedule);
	}

	/** Set Lieferdisposition.
		@param M_ShipmentSchedule_ID Lieferdisposition	  */
	@Override
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
	}

	/** Get Lieferdisposition.
		@return Lieferdisposition	  */
	@Override
	public int getM_ShipmentSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipmentSchedule_ID);
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
}