/** Generated Model - DO NOT CHANGE */
package de.metas.flatrate.model;

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
	private static final long serialVersionUID = 1831041135L;

    /** Standard Constructor */
    public X_C_SubscriptionProgress (Properties ctx, int C_SubscriptionProgress_ID, String trxName)
    {
      super (ctx, C_SubscriptionProgress_ID, trxName);
      /** if (C_SubscriptionProgress_ID == 0)
        {
			setC_Flatrate_Term_ID (0);
			setC_SubscriptionProgress_ID (0);
			setDropShip_BPartner_ID (0);
			setDropShip_Location_ID (0);
			setEventType (null);
			setIsSubscriptionConfirmed (false); // N
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
	public de.metas.flatrate.model.I_C_Flatrate_Term getC_Flatrate_Term() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.flatrate.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term(de.metas.flatrate.model.I_C_Flatrate_Term C_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.flatrate.model.I_C_Flatrate_Term.class, C_Flatrate_Term);
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

	/** 
	 * ContractStatus AD_Reference_ID=540000
	 * Reference name: SubscriptionStatus
	 */
	public static final int CONTRACTSTATUS_AD_Reference_ID=540000;
	/** Laufend  = Ru */
	public static final String CONTRACTSTATUS_Laufend = "Ru";
	/** Lieferpause = Pa */
	public static final String CONTRACTSTATUS_Lieferpause = "Pa";
	/** Beendet = En */
	public static final String CONTRACTSTATUS_Beendet = "En";
	/** Gekündigt = Qu */
	public static final String CONTRACTSTATUS_Gekuendigt = "Qu";
	/** Wartet auf Bestätigung = St */
	public static final String CONTRACTSTATUS_WartetAufBestaetigung = "St";
	/** Info = In */
	public static final String CONTRACTSTATUS_Info = "In";
	/** Noch nicht begonnen = Wa */
	public static final String CONTRACTSTATUS_NochNichtBegonnen = "Wa";
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
	/** Lieferung = DE */
	public static final String EVENTTYPE_Lieferung = "DE";
	/** Abowechsel = SU */
	public static final String EVENTTYPE_Abowechsel = "SU";
	/** Statuswechsel = ST */
	public static final String EVENTTYPE_Statuswechsel = "ST";
	/** Abo-Ende = SE */
	public static final String EVENTTYPE_Abo_Ende = "SE";
	/** Abo-Beginn = SB */
	public static final String EVENTTYPE_Abo_Beginn = "SB";
	/** Abo-Autoverlängerung = SR */
	public static final String EVENTTYPE_Abo_Autoverlaengerung = "SR";
	/** Abopause-Beginn = PB */
	public static final String EVENTTYPE_Abopause_Beginn = "PB";
	/** Abopause-Ende = PE */
	public static final String EVENTTYPE_Abopause_Ende = "PE";
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

	/** Set Bestätigung eingegangen.
		@param IsSubscriptionConfirmed Bestätigung eingegangen	  */
	@Override
	public void setIsSubscriptionConfirmed (boolean IsSubscriptionConfirmed)
	{
		set_Value (COLUMNNAME_IsSubscriptionConfirmed, Boolean.valueOf(IsSubscriptionConfirmed));
	}

	/** Get Bestätigung eingegangen.
		@return Bestätigung eingegangen	  */
	@Override
	public boolean isSubscriptionConfirmed () 
	{
		Object oo = get_Value(COLUMNNAME_IsSubscriptionConfirmed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
	/** Geplant = P */
	public static final String STATUS_Geplant = "P";
	/** Lieferung Offen = O */
	public static final String STATUS_LieferungOffen = "O";
	/** Ausgeliefert = D */
	public static final String STATUS_Ausgeliefert = "D";
	/** Wird kommissioniert = C */
	public static final String STATUS_WirdKommissioniert = "C";
	/** Ausgeführt = E */
	public static final String STATUS_Ausgefuehrt = "E";
	/** Verzögert = H */
	public static final String STATUS_Verzoegert = "H";
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