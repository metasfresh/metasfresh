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
package de.metas.flatrate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_Transition
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Flatrate_Transition extends org.compiere.model.PO implements I_C_Flatrate_Transition, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2024646661L;

    /** Standard Constructor */
    public X_C_Flatrate_Transition (Properties ctx, int C_Flatrate_Transition_ID, String trxName)
    {
      super (ctx, C_Flatrate_Transition_ID, trxName);
      /** if (C_Flatrate_Transition_ID == 0)
        {
			setC_Calendar_Contract_ID (0);
			setC_Flatrate_Transition_ID (0);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setEndsWithCalendarYear (false);
// N
			setIsAutoCompleteNewTerm (false);
// N
			setIsAutoRenew (false);
// N
			setIsNotifyUserInCharge (false);
// N
			setName (null);
			setProcessed (false);
// N
			setProcessing (false);
// N
			setTermDuration (0);
			setTermDurationUnit (null);
			setTermOfNotice (0);
			setTermOfNoticeUnit (null);
        } */
    }

    /** Load Constructor */
    public X_C_Flatrate_Transition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_Flatrate_Transition[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_C_Calendar getC_Calendar_Contract() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_Contract_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar_Contract(org.compiere.model.I_C_Calendar C_Calendar_Contract)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_Contract_ID, org.compiere.model.I_C_Calendar.class, C_Calendar_Contract);
	}

	/** Set Abrechnungs-/Lieferkalender.
		@param C_Calendar_Contract_ID 
		Bezeichnung des Kalenders, der die Abrechnungs- bzw. bei Abonnements die Lieferperioden (z.B. Monate) definiert
	  */
	@Override
	public void setC_Calendar_Contract_ID (int C_Calendar_Contract_ID)
	{
		if (C_Calendar_Contract_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_Contract_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_Contract_ID, Integer.valueOf(C_Calendar_Contract_ID));
	}

	/** Get Abrechnungs-/Lieferkalender.
		@return Bezeichnung des Kalenders, der die Abrechnungs- bzw. bei Abonnements die Lieferperioden (z.B. Monate) definiert
	  */
	@Override
	public int getC_Calendar_Contract_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Calendar_Contract_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.flatrate.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions_Next() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Conditions_Next_ID, de.metas.flatrate.model.I_C_Flatrate_Conditions.class);
	}

	@Override
	public void setC_Flatrate_Conditions_Next(de.metas.flatrate.model.I_C_Flatrate_Conditions C_Flatrate_Conditions_Next)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Conditions_Next_ID, de.metas.flatrate.model.I_C_Flatrate_Conditions.class, C_Flatrate_Conditions_Next);
	}

	/** Set Nächste Vertragsbedingungen.
		@param C_Flatrate_Conditions_Next_ID 
		Auswahl der Vertragsbedingungen, die bei einer Vertragsverlängerung in der folgenden Vertragsperide anzuwenden sind
	  */
	@Override
	public void setC_Flatrate_Conditions_Next_ID (int C_Flatrate_Conditions_Next_ID)
	{
		if (C_Flatrate_Conditions_Next_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_Next_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_Next_ID, Integer.valueOf(C_Flatrate_Conditions_Next_ID));
	}

	/** Get Nächste Vertragsbedingungen.
		@return Auswahl der Vertragsbedingungen, die bei einer Vertragsverlängerung in der folgenden Vertragsperide anzuwenden sind
	  */
	@Override
	public int getC_Flatrate_Conditions_Next_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Conditions_Next_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vertragsverlängerung/-übergang.
		@param C_Flatrate_Transition_ID 
		Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	  */
	@Override
	public void setC_Flatrate_Transition_ID (int C_Flatrate_Transition_ID)
	{
		if (C_Flatrate_Transition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Transition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Transition_ID, Integer.valueOf(C_Flatrate_Transition_ID));
	}

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

	/** Set Lieferintervall.
		@param DeliveryInterval Lieferintervall	  */
	@Override
	public void setDeliveryInterval (int DeliveryInterval)
	{
		set_Value (COLUMNNAME_DeliveryInterval, Integer.valueOf(DeliveryInterval));
	}

	/** Get Lieferintervall.
		@return Lieferintervall	  */
	@Override
	public int getDeliveryInterval () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeliveryInterval);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * DeliveryIntervalUnit AD_Reference_ID=540281
	 * Reference name: TermDurationUnit
	 */
	public static final int DELIVERYINTERVALUNIT_AD_Reference_ID=540281;
	/** Monat(e) = month */
	public static final String DELIVERYINTERVALUNIT_MonatE = "month";
	/** Woche(n) = week */
	public static final String DELIVERYINTERVALUNIT_WocheN = "week";
	/** Tag(e) = day */
	public static final String DELIVERYINTERVALUNIT_TagE = "day";
	/** Jahr(e) = year */
	public static final String DELIVERYINTERVALUNIT_JahrE = "year";
	/** Set Einheit des Lieferintervalls.
		@param DeliveryIntervalUnit Einheit des Lieferintervalls	  */
	@Override
	public void setDeliveryIntervalUnit (java.lang.String DeliveryIntervalUnit)
	{

		set_Value (COLUMNNAME_DeliveryIntervalUnit, DeliveryIntervalUnit);
	}

	/** Get Einheit des Lieferintervalls.
		@return Einheit des Lieferintervalls	  */
	@Override
	public java.lang.String getDeliveryIntervalUnit () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryIntervalUnit);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Genehmigen = AP */
	public static final String DOCACTION_Genehmigen = "AP";
	/** Ablehnen = RJ */
	public static final String DOCACTION_Ablehnen = "RJ";
	/** Buchen = PO */
	public static final String DOCACTION_Buchen = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Stornieren - Korrektur = RC */
	public static final String DOCACTION_Stornieren_Korrektur = "RC";
	/** Rückbuchung = RA */
	public static final String DOCACTION_Rueckbuchung = "RA";
	/** Annulieren = IN */
	public static final String DOCACTION_Annulieren = "IN";
	/** Reaktivieren = RE */
	public static final String DOCACTION_Reaktivieren = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Entsperren = XL */
	public static final String DOCACTION_Entsperren = "XL";
	/** Warten und fertigstellen = WC */
	public static final String DOCACTION_WartenUndFertigstellen = "WC";
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
	/** Genehmigt = AP */
	public static final String DOCSTATUS_Genehmigt = "AP";
	/** Nicht genehmigt = NA */
	public static final String DOCSTATUS_NichtGenehmigt = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unbekannt = ?? */
	public static final String DOCSTATUS_Unbekannt = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Warten auf Zahlung = WP */
	public static final String DOCSTATUS_WartenAufZahlung = "WP";
	/** Warten auf Bestätigung = WC */
	public static final String DOCSTATUS_WartenAufBestaetigung = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Endet mit Kalenderjahr.
		@param EndsWithCalendarYear Endet mit Kalenderjahr	  */
	@Override
	public void setEndsWithCalendarYear (boolean EndsWithCalendarYear)
	{
		set_Value (COLUMNNAME_EndsWithCalendarYear, Boolean.valueOf(EndsWithCalendarYear));
	}

	/** Get Endet mit Kalenderjahr.
		@return Endet mit Kalenderjahr	  */
	@Override
	public boolean isEndsWithCalendarYear () 
	{
		Object oo = get_Value(COLUMNNAME_EndsWithCalendarYear);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Neue Vertragslaufzeit autom. Fertigstellen.
		@param IsAutoCompleteNewTerm 
		Legt fest, ob das System die automatisch neu erzeugte Vertragsperiode sofort fertigstellen soll.
	  */
	@Override
	public void setIsAutoCompleteNewTerm (boolean IsAutoCompleteNewTerm)
	{
		set_Value (COLUMNNAME_IsAutoCompleteNewTerm, Boolean.valueOf(IsAutoCompleteNewTerm));
	}

	/** Get Neue Vertragslaufzeit autom. Fertigstellen.
		@return Legt fest, ob das System die automatisch neu erzeugte Vertragsperiode sofort fertigstellen soll.
	  */
	@Override
	public boolean isAutoCompleteNewTerm () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutoCompleteNewTerm);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Betreuer Informieren.
		@param IsNotifyUserInCharge Betreuer Informieren	  */
	@Override
	public void setIsNotifyUserInCharge (boolean IsNotifyUserInCharge)
	{
		set_Value (COLUMNNAME_IsNotifyUserInCharge, Boolean.valueOf(IsNotifyUserInCharge));
	}

	/** Get Betreuer Informieren.
		@return Betreuer Informieren	  */
	@Override
	public boolean isNotifyUserInCharge () 
	{
		Object oo = get_Value(COLUMNNAME_IsNotifyUserInCharge);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
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

	/** Set Vertragslaufzeit.
		@param TermDuration Vertragslaufzeit	  */
	@Override
	public void setTermDuration (int TermDuration)
	{
		set_Value (COLUMNNAME_TermDuration, Integer.valueOf(TermDuration));
	}

	/** Get Vertragslaufzeit.
		@return Vertragslaufzeit	  */
	@Override
	public int getTermDuration () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TermDuration);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * TermDurationUnit AD_Reference_ID=540281
	 * Reference name: TermDurationUnit
	 */
	public static final int TERMDURATIONUNIT_AD_Reference_ID=540281;
	/** Monat(e) = month */
	public static final String TERMDURATIONUNIT_MonatE = "month";
	/** Woche(n) = week */
	public static final String TERMDURATIONUNIT_WocheN = "week";
	/** Tag(e) = day */
	public static final String TERMDURATIONUNIT_TagE = "day";
	/** Jahr(e) = year */
	public static final String TERMDURATIONUNIT_JahrE = "year";
	/** Set Einheit der Vertragslaufzeit.
		@param TermDurationUnit Einheit der Vertragslaufzeit	  */
	@Override
	public void setTermDurationUnit (java.lang.String TermDurationUnit)
	{

		set_Value (COLUMNNAME_TermDurationUnit, TermDurationUnit);
	}

	/** Get Einheit der Vertragslaufzeit.
		@return Einheit der Vertragslaufzeit	  */
	@Override
	public java.lang.String getTermDurationUnit () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TermDurationUnit);
	}

	/** Set Ablauffrist.
		@param TermOfNotice 
		Zeit vor Vertragsablauf, zu der bestimmte Aktionen durchgeführt werden sollen.
	  */
	@Override
	public void setTermOfNotice (int TermOfNotice)
	{
		set_Value (COLUMNNAME_TermOfNotice, Integer.valueOf(TermOfNotice));
	}

	/** Get Ablauffrist.
		@return Zeit vor Vertragsablauf, zu der bestimmte Aktionen durchgeführt werden sollen.
	  */
	@Override
	public int getTermOfNotice () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TermOfNotice);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * TermOfNoticeUnit AD_Reference_ID=540281
	 * Reference name: TermDurationUnit
	 */
	public static final int TERMOFNOTICEUNIT_AD_Reference_ID=540281;
	/** Monat(e) = month */
	public static final String TERMOFNOTICEUNIT_MonatE = "month";
	/** Woche(n) = week */
	public static final String TERMOFNOTICEUNIT_WocheN = "week";
	/** Tag(e) = day */
	public static final String TERMOFNOTICEUNIT_TagE = "day";
	/** Jahr(e) = year */
	public static final String TERMOFNOTICEUNIT_JahrE = "year";
	/** Set Einheit der Kündigungsfrist.
		@param TermOfNoticeUnit Einheit der Kündigungsfrist	  */
	@Override
	public void setTermOfNoticeUnit (java.lang.String TermOfNoticeUnit)
	{

		set_Value (COLUMNNAME_TermOfNoticeUnit, TermOfNoticeUnit);
	}

	/** Get Einheit der Kündigungsfrist.
		@return Einheit der Kündigungsfrist	  */
	@Override
	public java.lang.String getTermOfNoticeUnit () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TermOfNoticeUnit);
	}
}