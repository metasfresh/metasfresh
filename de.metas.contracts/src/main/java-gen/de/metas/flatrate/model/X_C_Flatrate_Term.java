/** Generated Model - DO NOT CHANGE */
package de.metas.flatrate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_Term
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Flatrate_Term extends org.compiere.model.PO implements I_C_Flatrate_Term, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -133858118L;

    /** Standard Constructor */
    public X_C_Flatrate_Term (Properties ctx, int C_Flatrate_Term_ID, String trxName)
    {
      super (ctx, C_Flatrate_Term_ID, trxName);
      /** if (C_Flatrate_Term_ID == 0)
        {
			setAD_User_InCharge_ID (0);
			setBill_BPartner_ID (0); // @C_BPartner_ID@
			setBill_Location_ID (0);
			setC_Flatrate_Conditions_ID (0);
			setC_Flatrate_Data_ID (0);
			setC_Flatrate_Term_ID (0);
			setDocAction (null); // CO
			setDocStatus (null); // DR
			setIsAutoRenew (false); // N
			setIsPostageFree (false); // N
			setIsSimulation (false); // N
			setPlannedQtyPerUnit (BigDecimal.ZERO);
			setProcessing (false); // N
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setType_Conditions (null);
        } */
    }

    /** Load Constructor */
    public X_C_Flatrate_Term (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance_EndOfTerm() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_EndOfTerm_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance_EndOfTerm(org.compiere.model.I_AD_PInstance AD_PInstance_EndOfTerm)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_EndOfTerm_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance_EndOfTerm);
	}

	/** Set Verarbeitung zum Laufzeitende.
		@param AD_PInstance_EndOfTerm_ID Verarbeitung zum Laufzeitende	  */
	@Override
	public void setAD_PInstance_EndOfTerm_ID (int AD_PInstance_EndOfTerm_ID)
	{
		if (AD_PInstance_EndOfTerm_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_EndOfTerm_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_EndOfTerm_ID, Integer.valueOf(AD_PInstance_EndOfTerm_ID));
	}

	/** Get Verarbeitung zum Laufzeitende.
		@return Verarbeitung zum Laufzeitende	  */
	@Override
	public int getAD_PInstance_EndOfTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_EndOfTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User_InCharge() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_InCharge_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User_InCharge(org.compiere.model.I_AD_User AD_User_InCharge)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_InCharge_ID, org.compiere.model.I_AD_User.class, AD_User_InCharge);
	}

	/** Set Betreuer.
		@param AD_User_InCharge_ID 
		Person, die bei einem fachlichen Problem vom System informiert wird.
	  */
	@Override
	public void setAD_User_InCharge_ID (int AD_User_InCharge_ID)
	{
		if (AD_User_InCharge_ID < 1) 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, Integer.valueOf(AD_User_InCharge_ID));
	}

	/** Get Betreuer.
		@return Person, die bei einem fachlichen Problem vom System informiert wird.
	  */
	@Override
	public int getAD_User_InCharge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_InCharge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getBill_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Bill_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setBill_BPartner(org.compiere.model.I_C_BPartner Bill_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_Bill_BPartner_ID, org.compiere.model.I_C_BPartner.class, Bill_BPartner);
	}

	/** Set Rechnungspartner.
		@param Bill_BPartner_ID 
		Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	/** Get Rechnungspartner.
		@return Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getBill_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Bill_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setBill_Location(org.compiere.model.I_C_BPartner_Location Bill_Location)
	{
		set_ValueFromPO(COLUMNNAME_Bill_Location_ID, org.compiere.model.I_C_BPartner_Location.class, Bill_Location);
	}

	/** Set Rechnungsstandort.
		@param Bill_Location_ID 
		Standort des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_Location_ID (int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Integer.valueOf(Bill_Location_ID));
	}

	/** Get Rechnungsstandort.
		@return Standort des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getBill_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Bill_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setBill_User(org.compiere.model.I_AD_User Bill_User)
	{
		set_ValueFromPO(COLUMNNAME_Bill_User_ID, org.compiere.model.I_AD_User.class, Bill_User);
	}

	/** Set Rechnungskontakt.
		@param Bill_User_ID 
		Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_User_ID (int Bill_User_ID)
	{
		if (Bill_User_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_User_ID, Integer.valueOf(Bill_User_ID));
	}

	/** Get Rechnungskontakt.
		@return Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.flatrate.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Conditions_ID, de.metas.flatrate.model.I_C_Flatrate_Conditions.class);
	}

	@Override
	public void setC_Flatrate_Conditions(de.metas.flatrate.model.I_C_Flatrate_Conditions C_Flatrate_Conditions)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Conditions_ID, de.metas.flatrate.model.I_C_Flatrate_Conditions.class, C_Flatrate_Conditions);
	}

	/** Set Vertragsbedingungen.
		@param C_Flatrate_Conditions_ID Vertragsbedingungen	  */
	@Override
	public void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID)
	{
		if (C_Flatrate_Conditions_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, Integer.valueOf(C_Flatrate_Conditions_ID));
	}

	/** Get Vertragsbedingungen.
		@return Vertragsbedingungen	  */
	@Override
	public int getC_Flatrate_Conditions_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Conditions_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.flatrate.model.I_C_Flatrate_Data getC_Flatrate_Data() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Data_ID, de.metas.flatrate.model.I_C_Flatrate_Data.class);
	}

	@Override
	public void setC_Flatrate_Data(de.metas.flatrate.model.I_C_Flatrate_Data C_Flatrate_Data)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Data_ID, de.metas.flatrate.model.I_C_Flatrate_Data.class, C_Flatrate_Data);
	}

	/** Set Datenerfassung.
		@param C_Flatrate_Data_ID Datenerfassung	  */
	@Override
	public void setC_Flatrate_Data_ID (int C_Flatrate_Data_ID)
	{
		if (C_Flatrate_Data_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Data_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Data_ID, Integer.valueOf(C_Flatrate_Data_ID));
	}

	/** Get Datenerfassung.
		@return Datenerfassung	  */
	@Override
	public int getC_Flatrate_Data_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Data_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	@Override
	public de.metas.flatrate.model.I_C_Flatrate_Transition getC_Flatrate_Transition() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.flatrate.model.I_C_Flatrate_Transition.class);
	}

	@Override
	public void setC_Flatrate_Transition(de.metas.flatrate.model.I_C_Flatrate_Transition C_Flatrate_Transition)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.flatrate.model.I_C_Flatrate_Transition.class, C_Flatrate_Transition);
	}

	/** Set Vertragsverlängerung/-übergang.
		@param C_Flatrate_Transition_ID 
		Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	  */
	@Override
	public void setC_Flatrate_Transition_ID (int C_Flatrate_Transition_ID)
	{
		throw new IllegalArgumentException ("C_Flatrate_Transition_ID is virtual column");	}

	/** Get Vertragsverlängerung/-übergang.
		@return Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	  */
	@Override
	public int getC_Flatrate_Transition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Transition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.flatrate.model.I_C_Flatrate_Term getC_FlatrateTerm_Next() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_FlatrateTerm_Next_ID, de.metas.flatrate.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_FlatrateTerm_Next(de.metas.flatrate.model.I_C_Flatrate_Term C_FlatrateTerm_Next)
	{
		set_ValueFromPO(COLUMNNAME_C_FlatrateTerm_Next_ID, de.metas.flatrate.model.I_C_Flatrate_Term.class, C_FlatrateTerm_Next);
	}

	/** Set Nachfolgende Vertragsperiode.
		@param C_FlatrateTerm_Next_ID Nachfolgende Vertragsperiode	  */
	@Override
	public void setC_FlatrateTerm_Next_ID (int C_FlatrateTerm_Next_ID)
	{
		if (C_FlatrateTerm_Next_ID < 1) 
			set_Value (COLUMNNAME_C_FlatrateTerm_Next_ID, null);
		else 
			set_Value (COLUMNNAME_C_FlatrateTerm_Next_ID, Integer.valueOf(C_FlatrateTerm_Next_ID));
	}

	/** Get Nachfolgende Vertragsperiode.
		@return Nachfolgende Vertragsperiode	  */
	@Override
	public int getC_FlatrateTerm_Next_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_FlatrateTerm_Next_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order_Term() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_Term_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order_Term(org.compiere.model.I_C_Order C_Order_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_Term_ID, org.compiere.model.I_C_Order.class, C_Order_Term);
	}

	/** Set Vertrags-Auftrag.
		@param C_Order_Term_ID 
		Auftrag, mit der der Vertrag abgeschlossen wurde
	  */
	@Override
	public void setC_Order_Term_ID (int C_Order_Term_ID)
	{
		throw new IllegalArgumentException ("C_Order_Term_ID is virtual column");	}

	/** Get Vertrags-Auftrag.
		@return Auftrag, mit der der Vertrag abgeschlossen wurde
	  */
	@Override
	public int getC_Order_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_Term_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order_TermChange() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_TermChange_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order_TermChange(org.compiere.model.I_C_Order C_Order_TermChange)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_TermChange_ID, org.compiere.model.I_C_Order.class, C_Order_TermChange);
	}

	/** Set Änderungs-Auftrag.
		@param C_Order_TermChange_ID 
		Auftrag, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde
	  */
	@Override
	public void setC_Order_TermChange_ID (int C_Order_TermChange_ID)
	{
		throw new IllegalArgumentException ("C_Order_TermChange_ID is virtual column");	}

	/** Get Änderungs-Auftrag.
		@return Auftrag, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde
	  */
	@Override
	public int getC_Order_TermChange_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_TermChange_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine_Term() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_Term_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine_Term(org.compiere.model.I_C_OrderLine C_OrderLine_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_Term_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine_Term);
	}

	/** Set Vertrags-Auftragszeile.
		@param C_OrderLine_Term_ID 
		Auftragszeile, mit der der Vertrag abgeschlossen wurde
	  */
	@Override
	public void setC_OrderLine_Term_ID (int C_OrderLine_Term_ID)
	{
		if (C_OrderLine_Term_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_Term_ID, Integer.valueOf(C_OrderLine_Term_ID));
	}

	/** Get Vertrags-Auftragszeile.
		@return Auftragszeile, mit der der Vertrag abgeschlossen wurde
	  */
	@Override
	public int getC_OrderLine_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_Term_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine_TermChange() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_TermChange_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine_TermChange(org.compiere.model.I_C_OrderLine C_OrderLine_TermChange)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_TermChange_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine_TermChange);
	}

	/** Set Änderungs-Auftragszeile.
		@param C_OrderLine_TermChange_ID 
		Auftragszeile, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde
	  */
	@Override
	public void setC_OrderLine_TermChange_ID (int C_OrderLine_TermChange_ID)
	{
		if (C_OrderLine_TermChange_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_TermChange_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_TermChange_ID, Integer.valueOf(C_OrderLine_TermChange_ID));
	}

	/** Get Änderungs-Auftragszeile.
		@return Auftragszeile, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde
	  */
	@Override
	public int getC_OrderLine_TermChange_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_TermChange_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
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

	/** Set Ändern oder Kündigen.
		@param ChangeOrCancelTerm Ändern oder Kündigen	  */
	@Override
	public void setChangeOrCancelTerm (java.lang.String ChangeOrCancelTerm)
	{
		set_Value (COLUMNNAME_ChangeOrCancelTerm, ChangeOrCancelTerm);
	}

	/** Get Ändern oder Kündigen.
		@return Ändern oder Kündigen	  */
	@Override
	public java.lang.String getChangeOrCancelTerm () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ChangeOrCancelTerm);
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

	/** 
	 * DeliveryRule AD_Reference_ID=151
	 * Reference name: C_Order DeliveryRule
	 */
	public static final int DELIVERYRULE_AD_Reference_ID=151;
	/** AfterReceipt = R */
	public static final String DELIVERYRULE_AfterReceipt = "R";
	/** Availability = A */
	public static final String DELIVERYRULE_Availability = "A";
	/** CompleteLine = L */
	public static final String DELIVERYRULE_CompleteLine = "L";
	/** CompleteOrder = O */
	public static final String DELIVERYRULE_CompleteOrder = "O";
	/** Force = F */
	public static final String DELIVERYRULE_Force = "F";
	/** Manual = M */
	public static final String DELIVERYRULE_Manual = "M";
	/** Mit nächster Abolieferung = S */
	public static final String DELIVERYRULE_MitNaechsterAbolieferung = "S";
	/** Set Lieferart.
		@param DeliveryRule 
		Definiert die zeitliche Steuerung von Lieferungen
	  */
	@Override
	public void setDeliveryRule (java.lang.String DeliveryRule)
	{

		set_Value (COLUMNNAME_DeliveryRule, DeliveryRule);
	}

	/** Get Lieferart.
		@return Definiert die zeitliche Steuerung von Lieferungen
	  */
	@Override
	public java.lang.String getDeliveryRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryRule);
	}

	/** 
	 * DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_Shipper = "S";
	/** Set Lieferung.
		@param DeliveryViaRule 
		Wie der Auftrag geliefert wird
	  */
	@Override
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule)
	{

		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	/** Get Lieferung.
		@return Wie der Auftrag geliefert wird
	  */
	@Override
	public java.lang.String getDeliveryViaRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryViaRule);
	}

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Set Belegverarbeitung.
		@param DocAction 
		Der zukünftige Status des Belegs
	  */
	@Override
	public void setDocAction (java.lang.String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Belegverarbeitung.
		@return Der zukünftige Status des Belegs
	  */
	@Override
	public java.lang.String getDocAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocAction);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		Derzeitiger Status des Belegs
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Belegstatus.
		@return Derzeitiger Status des Belegs
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
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

	/** Set Streckengeschäft-Kunde.
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

	/** Get Streckengeschäft-Kunde.
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

	/** Set Streckengeschäft-Ort.
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

	/** Get Streckengeschäft-Ort.
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

	/** Set Streckengeschäft-Ansprechpartner.
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

	/** Get Streckengeschäft-Ansprechpartner.
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

	/** Set Enddatum.
		@param EndDate 
		Last effective date (inclusive)
	  */
	@Override
	public void setEndDate (java.sql.Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get Enddatum.
		@return Last effective date (inclusive)
	  */
	@Override
	public java.sql.Timestamp getEndDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set Vertrag jetzt verlängern.
		@param ExtendTerm Vertrag jetzt verlängern	  */
	@Override
	public void setExtendTerm (java.lang.String ExtendTerm)
	{
		set_Value (COLUMNNAME_ExtendTerm, ExtendTerm);
	}

	/** Get Vertrag jetzt verlängern.
		@return Vertrag jetzt verlängern	  */
	@Override
	public java.lang.String getExtendTerm () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExtendTerm);
	}

	/** Set Vertrag autom. verlängern.
		@param IsAutoRenew 
		Wenn dieser Haken gesetzt ist, werden laufende Verträge automatisch verlängert
	  */
	@Override
	public void setIsAutoRenew (boolean IsAutoRenew)
	{
		set_Value (COLUMNNAME_IsAutoRenew, Boolean.valueOf(IsAutoRenew));
	}

	/** Get Vertrag autom. verlängern.
		@return Wenn dieser Haken gesetzt ist, werden laufende Verträge automatisch verlängert
	  */
	@Override
	public boolean isAutoRenew () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutoRenew);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rechnungskandidat schließen.
		@param IsCloseInvoiceCandidate Rechnungskandidat schließen	  */
	@Override
	public void setIsCloseInvoiceCandidate (boolean IsCloseInvoiceCandidate)
	{
		set_Value (COLUMNNAME_IsCloseInvoiceCandidate, Boolean.valueOf(IsCloseInvoiceCandidate));
	}

	/** Get Rechnungskandidat schließen.
		@return Rechnungskandidat schließen	  */
	@Override
	public boolean isCloseInvoiceCandidate () 
	{
		Object oo = get_Value(COLUMNNAME_IsCloseInvoiceCandidate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Gegenüberstellung mit erbr. Leist..
		@param IsClosingWithActualSum 
		Legt fest, ob die pauschal abgerechenten Beträge den tatsächlich erbrachten Leistungen gegenüber gestellt werden sollen
	  */
	@Override
	public void setIsClosingWithActualSum (boolean IsClosingWithActualSum)
	{
		throw new IllegalArgumentException ("IsClosingWithActualSum is virtual column");	}

	/** Get Gegenüberstellung mit erbr. Leist..
		@return Legt fest, ob die pauschal abgerechenten Beträge den tatsächlich erbrachten Leistungen gegenüber gestellt werden sollen
	  */
	@Override
	public boolean isClosingWithActualSum () 
	{
		Object oo = get_Value(COLUMNNAME_IsClosingWithActualSum);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Abschlusskorrektur vorsehen.
		@param IsClosingWithCorrectionSum 
		Legt fest, ob innerhalb der Vertragslaufzeit (in der Regel zu deren Ende) noch korrigierte Pauschalen-Mengen erfasst werden können
	  */
	@Override
	public void setIsClosingWithCorrectionSum (boolean IsClosingWithCorrectionSum)
	{
		throw new IllegalArgumentException ("IsClosingWithCorrectionSum is virtual column");	}

	/** Get Abschlusskorrektur vorsehen.
		@return Legt fest, ob innerhalb der Vertragslaufzeit (in der Regel zu deren Ende) noch korrigierte Pauschalen-Mengen erfasst werden können
	  */
	@Override
	public boolean isClosingWithCorrectionSum () 
	{
		Object oo = get_Value(COLUMNNAME_IsClosingWithCorrectionSum);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set AB bei neuer Vertragslaufzeit.
		@param IsNewTermCreatesOrder 
		Entscheidet, ob das System beim Fertigstellen einer neuen Vertragslaufzeit (z.B. bei automatischer Verlängerung) eine Auftragsbestätigung erzeugt.
	  */
	@Override
	public void setIsNewTermCreatesOrder (boolean IsNewTermCreatesOrder)
	{
		throw new IllegalArgumentException ("IsNewTermCreatesOrder is virtual column");	}

	/** Get AB bei neuer Vertragslaufzeit.
		@return Entscheidet, ob das System beim Fertigstellen einer neuen Vertragslaufzeit (z.B. bei automatischer Verlängerung) eine Auftragsbestätigung erzeugt.
	  */
	@Override
	public boolean isNewTermCreatesOrder () 
	{
		Object oo = get_Value(COLUMNNAME_IsNewTermCreatesOrder);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Portofrei.
		@param IsPostageFree Portofrei	  */
	@Override
	public void setIsPostageFree (boolean IsPostageFree)
	{
		set_Value (COLUMNNAME_IsPostageFree, Boolean.valueOf(IsPostageFree));
	}

	/** Get Portofrei.
		@return Portofrei	  */
	@Override
	public boolean isPostageFree () 
	{
		Object oo = get_Value(COLUMNNAME_IsPostageFree);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Planspiel.
		@param IsSimulation Planspiel	  */
	@Override
	public void setIsSimulation (boolean IsSimulation)
	{
		set_Value (COLUMNNAME_IsSimulation, Boolean.valueOf(IsSimulation));
	}

	/** Get Planspiel.
		@return Planspiel	  */
	@Override
	public boolean isSimulation () 
	{
		Object oo = get_Value(COLUMNNAME_IsSimulation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_PricingSystem getM_PricingSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class);
	}

	@Override
	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem)
	{
		set_ValueFromPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class, M_PricingSystem);
	}

	/** Set Preissystem.
		@param M_PricingSystem_ID 
		Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public void setM_PricingSystem_ID (int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, Integer.valueOf(M_PricingSystem_ID));
	}

	/** Get Preissystem.
		@return Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public int getM_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PricingSystem_ID);
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

	/** Set Notiz.
		@param Note 
		Optional weitere Information für ein Dokument
	  */
	@Override
	public void setNote (java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Notiz.
		@return Optional weitere Information für ein Dokument
	  */
	@Override
	public java.lang.String getNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Note);
	}

	/** Set Kündigungs/Benachrichtigungsfrist.
		@param NoticeDate 
		Datum vor Ende der Vertragslaufzeit, an dem der laufende Vertrag automatisch verlängert oder aber der Betreuer informiert wird.
	  */
	@Override
	public void setNoticeDate (java.sql.Timestamp NoticeDate)
	{
		set_Value (COLUMNNAME_NoticeDate, NoticeDate);
	}

	/** Get Kündigungs/Benachrichtigungsfrist.
		@return Datum vor Ende der Vertragslaufzeit, an dem der laufende Vertrag automatisch verlängert oder aber der Betreuer informiert wird.
	  */
	@Override
	public java.sql.Timestamp getNoticeDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_NoticeDate);
	}

	/** Set Planmenge pro Maßeinheit.
		@param PlannedQtyPerUnit 
		Geplante Menge der zu erbringenden Leistung (z.B. zu liefernde Teile), pro pauschal abzurechnender Einheit (z.B. Pflegetag).
	  */
	@Override
	public void setPlannedQtyPerUnit (java.math.BigDecimal PlannedQtyPerUnit)
	{
		set_Value (COLUMNNAME_PlannedQtyPerUnit, PlannedQtyPerUnit);
	}

	/** Get Planmenge pro Maßeinheit.
		@return Geplante Menge der zu erbringenden Leistung (z.B. zu liefernde Teile), pro pauschal abzurechnender Einheit (z.B. Pflegetag).
	  */
	@Override
	public java.math.BigDecimal getPlannedQtyPerUnit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PlannedQtyPerUnit);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abschlusskorrektur vorbereiten.
		@param PrepareClosing 
		Prozess zum erstellen eines Abrechnungs-Korrektur-Datensatzes und/oder eines Abrechnungs-Verrechnungs-Datensatzes
	  */
	@Override
	public void setPrepareClosing (java.lang.String PrepareClosing)
	{
		set_Value (COLUMNNAME_PrepareClosing, PrepareClosing);
	}

	/** Get Abschlusskorrektur vorbereiten.
		@return Prozess zum erstellen eines Abrechnungs-Korrektur-Datensatzes und/oder eines Abrechnungs-Verrechnungs-Datensatzes
	  */
	@Override
	public java.lang.String getPrepareClosing () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrepareClosing);
	}

	/** Set Einzelpreis.
		@param PriceActual 
		Effektiver Preis
	  */
	@Override
	public void setPriceActual (java.math.BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Einzelpreis.
		@return Effektiver Preis
	  */
	@Override
	public java.math.BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set Process Now.
		@param Processing Process Now	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Anfangsdatum.
		@param StartDate 
		First effective day (inclusive)
	  */
	@Override
	public void setStartDate (java.sql.Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Anfangsdatum.
		@return First effective day (inclusive)
	  */
	@Override
	public java.sql.Timestamp getStartDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** 
	 * Type_Conditions AD_Reference_ID=540271
	 * Reference name: Type_Conditions
	 */
	public static final int TYPE_CONDITIONS_AD_Reference_ID=540271;
	/** Pauschalengebühr = FlatFee */
	public static final String TYPE_CONDITIONS_Pauschalengebuehr = "FlatFee";
	/** Depotgebühr = HoldingFee */
	public static final String TYPE_CONDITIONS_Depotgebuehr = "HoldingFee";
	/** Abonnement = Subscr */
	public static final String TYPE_CONDITIONS_Abonnement = "Subscr";
	/** Leergutverwaltung = Refundable */
	public static final String TYPE_CONDITIONS_Leergutverwaltung = "Refundable";
	/** QualityBasedInvoicing = QualityBsd */
	public static final String TYPE_CONDITIONS_QualityBasedInvoicing = "QualityBsd";
	/** Liefervereinbarung = Procuremnt */
	public static final String TYPE_CONDITIONS_Liefervereinbarung = "Procuremnt";
	/** Set Vertragsart.
		@param Type_Conditions Vertragsart	  */
	@Override
	public void setType_Conditions (java.lang.String Type_Conditions)
	{

		set_ValueNoCheck (COLUMNNAME_Type_Conditions, Type_Conditions);
	}

	/** Get Vertragsart.
		@return Vertragsart	  */
	@Override
	public java.lang.String getType_Conditions () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type_Conditions);
	}

	/** 
	 * Type_Flatrate AD_Reference_ID=540264
	 * Reference name: Type_Flatrate
	 */
	public static final int TYPE_FLATRATE_AD_Reference_ID=540264;
	/** Keine Verrechnung = NONE */
	public static final String TYPE_FLATRATE_KeineVerrechnung = "NONE";
	/** Korridor % = LIPE */
	public static final String TYPE_FLATRATE_Korridor = "LIPE";
	/** Set Verrechnungsart.
		@param Type_Flatrate 
		Art der Verrechnung bei der Gegenüberstellung mit tatsächliche erbrachten Leistungen
	  */
	@Override
	public void setType_Flatrate (java.lang.String Type_Flatrate)
	{

		throw new IllegalArgumentException ("Type_Flatrate is virtual column");	}

	/** Get Verrechnungsart.
		@return Art der Verrechnung bei der Gegenüberstellung mit tatsächliche erbrachten Leistungen
	  */
	@Override
	public java.lang.String getType_Flatrate () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type_Flatrate);
	}

	/** 
	 * UOMType AD_Reference_ID=540262
	 * Reference name: UOM Type Flatrate
	 */
	public static final int UOMTYPE_AD_Reference_ID=540262;
	/** Gesundheitswesen = HC */
	public static final String UOMTYPE_Gesundheitswesen = "HC";
	/** Abrechnungsgenauigkeit = TD */
	public static final String UOMTYPE_Abrechnungsgenauigkeit = "TD";
	/** Set Einheiten-Typ.
		@param UOMType 
		Dient der Zusammenfassung ähnlicher Maßeinheiten
	  */
	@Override
	public void setUOMType (java.lang.String UOMType)
	{

		throw new IllegalArgumentException ("UOMType is virtual column");	}

	/** Get Einheiten-Typ.
		@return Dient der Zusammenfassung ähnlicher Maßeinheiten
	  */
	@Override
	public java.lang.String getUOMType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UOMType);
	}
}