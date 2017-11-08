/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_SubscriptionProgress
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_SubscriptionProgress extends org.compiere.model.PO implements I_C_SubscriptionProgress, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2104501876L;

    /** Standard Constructor */
    public X_C_SubscriptionProgress (Properties ctx, int C_SubscriptionProgress_ID, String trxName)
    {
      super (ctx, C_SubscriptionProgress_ID, trxName);
      /** if (C_SubscriptionProgress_ID == 0)
        {
			setC_Flatrate_Term_ID (0);
			setC_SubscriptionProgress_ID (0);
			setEventType (null);
			setProcessed (false); // N
			setStatus (null); // P
        } */
    }

    /** Load Constructor */
    public X_C_SubscriptionProgress (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term(de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class, C_Flatrate_Term);
	}

	/** Set Pauschale - Vertragsperiode.
		@param C_Flatrate_Term_ID Pauschale - Vertragsperiode	  */
	@Override
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, Integer.valueOf(C_Flatrate_Term_ID));
	}

	/** Get Pauschale - Vertragsperiode.
		@return Pauschale - Vertragsperiode	  */
	@Override
	public int getC_Flatrate_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Term_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * ContractStatus AD_Reference_ID=540000
	 * Reference name: SubscriptionStatus
	 */
	public static final int CONTRACTSTATUS_AD_Reference_ID=540000;
	/** Running = Ru */
	public static final String CONTRACTSTATUS_Running = "Ru";
	/** DeliveryPause = Pa */
	public static final String CONTRACTSTATUS_DeliveryPause = "Pa";
	/** Quit = Qu */
	public static final String CONTRACTSTATUS_Quit = "Qu";
	/** Info = In */
	public static final String CONTRACTSTATUS_Info = "In";
	/** Waiting = Wa */
	public static final String CONTRACTSTATUS_Waiting = "Wa";
	/** EndingContract = Ec */
	public static final String CONTRACTSTATUS_EndingContract = "Ec";
	/** Set Vertrags-Status.
		@param ContractStatus Vertrags-Status	  */
	@Override
	public void setContractStatus (java.lang.String ContractStatus)
	{

		set_Value (COLUMNNAME_ContractStatus, ContractStatus);
	}

	/** Get Vertrags-Status.
		@return Vertrags-Status	  */
	@Override
	public java.lang.String getContractStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContractStatus);
	}

	/** Set Abo-Verlauf.
		@param C_SubscriptionProgress_ID Abo-Verlauf	  */
	@Override
	public void setC_SubscriptionProgress_ID (int C_SubscriptionProgress_ID)
	{
		if (C_SubscriptionProgress_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_SubscriptionProgress_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_SubscriptionProgress_ID, Integer.valueOf(C_SubscriptionProgress_ID));
	}

	/** Get Abo-Verlauf.
		@return Abo-Verlauf	  */
	@Override
	public int getC_SubscriptionProgress_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SubscriptionProgress_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getDropShip_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setDropShip_BPartner(org.compiere.model.I_C_BPartner DropShip_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_BPartner_ID, org.compiere.model.I_C_BPartner.class, DropShip_BPartner);
	}

	/** Set Lieferempfänger.
		@param DropShip_BPartner_ID 
		Business Partner to ship to
	  */
	@Override
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID)
	{
		if (DropShip_BPartner_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, Integer.valueOf(DropShip_BPartner_ID));
	}

	/** Get Lieferempfänger.
		@return Business Partner to ship to
	  */
	@Override
	public int getDropShip_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getDropShip_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setDropShip_Location(org.compiere.model.I_C_BPartner_Location DropShip_Location)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_Location_ID, org.compiere.model.I_C_BPartner_Location.class, DropShip_Location);
	}

	/** Set Lieferadresse.
		@param DropShip_Location_ID 
		Business Partner Location for shipping to
	  */
	@Override
	public void setDropShip_Location_ID (int DropShip_Location_ID)
	{
		if (DropShip_Location_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_ID, Integer.valueOf(DropShip_Location_ID));
	}

	/** Get Lieferadresse.
		@return Business Partner Location for shipping to
	  */
	@Override
	public int getDropShip_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getDropShip_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setDropShip_User(org.compiere.model.I_AD_User DropShip_User)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_User_ID, org.compiere.model.I_AD_User.class, DropShip_User);
	}

	/** Set Lieferkontakt.
		@param DropShip_User_ID 
		Business Partner Contact for drop shipment
	  */
	@Override
	public void setDropShip_User_ID (int DropShip_User_ID)
	{
		if (DropShip_User_ID < 1) 
			set_Value (COLUMNNAME_DropShip_User_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_User_ID, Integer.valueOf(DropShip_User_ID));
	}

	/** Get Lieferkontakt.
		@return Business Partner Contact for drop shipment
	  */
	@Override
	public int getDropShip_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datum.
		@param EventDate Datum	  */
	@Override
	public void setEventDate (java.sql.Timestamp EventDate)
	{
		set_Value (COLUMNNAME_EventDate, EventDate);
	}

	/** Get Datum.
		@return Datum	  */
	@Override
	public java.sql.Timestamp getEventDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EventDate);
	}

	/** 
	 * EventType AD_Reference_ID=540013
	 * Reference name: C_SubscriptionProgress EventType
	 */
	public static final int EVENTTYPE_AD_Reference_ID=540013;
	/** Delivery = DE */
	public static final String EVENTTYPE_Delivery = "DE";
	/** BeginOfPause = PB */
	public static final String EVENTTYPE_BeginOfPause = "PB";
	/** EndOfPause = PE */
	public static final String EVENTTYPE_EndOfPause = "PE";
	/** Set Ereignisart.
		@param EventType Ereignisart	  */
	@Override
	public void setEventType (java.lang.String EventType)
	{

		set_ValueNoCheck (COLUMNNAME_EventType, EventType);
	}

	/** Get Ereignisart.
		@return Ereignisart	  */
	@Override
	public java.lang.String getEventType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EventType);
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
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * Status AD_Reference_ID=540002
	 * Reference name: C_SubscriptionProgress Status
	 */
	public static final int STATUS_AD_Reference_ID=540002;
	/** Planned = P */
	public static final String STATUS_Planned = "P";
	/** Open = O */
	public static final String STATUS_Open = "O";
	/** Delivered = D */
	public static final String STATUS_Delivered = "D";
	/** InPicking = C */
	public static final String STATUS_InPicking = "C";
	/** Done = E */
	public static final String STATUS_Done = "E";
	/** Delayed = H */
	public static final String STATUS_Delayed = "H";
	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	@Override
	public void setStatus (java.lang.String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	@Override
	public java.lang.String getStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}
}