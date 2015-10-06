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
package de.metas.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

/** Generated Model for C_AdvCommissionFact
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvCommissionFact extends PO implements I_C_AdvCommissionFact, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvCommissionFact (Properties ctx, int C_AdvCommissionFact_ID, String trxName)
    {
      super (ctx, C_AdvCommissionFact_ID, trxName);
      /** if (C_AdvCommissionFact_ID == 0)
        {
			setAD_Table_ID (0);
			setC_AdvCommissionFact_ID (0);
			setC_AdvCommissionInstance_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
			setDateFact (new Timestamp( System.currentTimeMillis() ));
			setIsCounterEntry (false);
// N
			setRecord_ID (0);
			setStatus (null);
// PR
        } */
    }

    /** Load Constructor */
    public X_C_AdvCommissionFact (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_AdvCommissionFact[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_PInstance getAD_PInstance() throws RuntimeException
    {
		return (org.compiere.model.I_AD_PInstance)MTable.get(getCtx(), org.compiere.model.I_AD_PInstance.Table_Name)
			.getPO(getAD_PInstance_ID(), get_TrxName());	}

	/** Set Prozess-Instanz.
		@param AD_PInstance_ID 
		Instanz eines Prozesses
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
	}

	/** Get Prozess-Instanz.
		@return Instanz eines Prozesses
	  */
	public int getAD_PInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
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
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Multiplikator Betrag.
		@param AmtMultiplier 
		Betrags-Multiplikator für die Berechnung von Provisionen
	  */
	public void setAmtMultiplier (BigDecimal AmtMultiplier)
	{
		set_ValueNoCheck (COLUMNNAME_AmtMultiplier, AmtMultiplier);
	}

	/** Get Multiplikator Betrag.
		@return Betrags-Multiplikator für die Berechnung von Provisionen
	  */
	public BigDecimal getAmtMultiplier () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtMultiplier);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Fortsetzungs-Datensätze.
		@param C_AdvComFact_IDs_FollowUp 
		IDs der Datensätze, die einen bestehenden Datensatz fortführen
	  */
	public void setC_AdvComFact_IDs_FollowUp (String C_AdvComFact_IDs_FollowUp)
	{
		set_Value (COLUMNNAME_C_AdvComFact_IDs_FollowUp, C_AdvComFact_IDs_FollowUp);
	}

	/** Get Fortsetzungs-Datensätze.
		@return IDs der Datensätze, die einen bestehenden Datensatz fortführen
	  */
	public String getC_AdvComFact_IDs_FollowUp () 
	{
		return (String)get_Value(COLUMNNAME_C_AdvComFact_IDs_FollowUp);
	}

	public de.metas.commission.model.I_C_AdvCommissionFactCand getC_AdvCommissionFactCand() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionFactCand)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionFactCand.Table_Name)
			.getPO(getC_AdvCommissionFactCand_ID(), get_TrxName());	}

	/** Set Provisionsdaten-Wartschlange.
		@param C_AdvCommissionFactCand_ID Provisionsdaten-Wartschlange	  */
	public void setC_AdvCommissionFactCand_ID (int C_AdvCommissionFactCand_ID)
	{
		if (C_AdvCommissionFactCand_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionFactCand_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionFactCand_ID, Integer.valueOf(C_AdvCommissionFactCand_ID));
	}

	/** Get Provisionsdaten-Wartschlange.
		@return Provisionsdaten-Wartschlange	  */
	public int getC_AdvCommissionFactCand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionFactCand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vorgangsdatensatz.
		@param C_AdvCommissionFact_ID Vorgangsdatensatz	  */
	public void setC_AdvCommissionFact_ID (int C_AdvCommissionFact_ID)
	{
		if (C_AdvCommissionFact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionFact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionFact_ID, Integer.valueOf(C_AdvCommissionFact_ID));
	}

	/** Get Vorgangsdatensatz.
		@return Vorgangsdatensatz	  */
	public int getC_AdvCommissionFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionInstance getC_AdvCommissionInstance() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionInstance)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionInstance.Table_Name)
			.getPO(getC_AdvCommissionInstance_ID(), get_TrxName());	}

	/** Set Provisionsvorgang.
		@param C_AdvCommissionInstance_ID Provisionsvorgang	  */
	public void setC_AdvCommissionInstance_ID (int C_AdvCommissionInstance_ID)
	{
		if (C_AdvCommissionInstance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionInstance_ID, Integer.valueOf(C_AdvCommissionInstance_ID));
	}

	/** Get Provisionsvorgang.
		@return Provisionsvorgang	  */
	public int getC_AdvCommissionInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionPayrollLine getC_AdvCommissionPayrollLine() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionPayrollLine)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionPayrollLine.Table_Name)
			.getPO(getC_AdvCommissionPayrollLine_ID(), get_TrxName());	}

	/** Set Prov-Berechungsposition.
		@param C_AdvCommissionPayrollLine_ID Prov-Berechungsposition	  */
	public void setC_AdvCommissionPayrollLine_ID (int C_AdvCommissionPayrollLine_ID)
	{
		if (C_AdvCommissionPayrollLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionPayrollLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionPayrollLine_ID, Integer.valueOf(C_AdvCommissionPayrollLine_ID));
	}

	/** Get Prov-Berechungsposition.
		@return Prov-Berechungsposition	  */
	public int getC_AdvCommissionPayrollLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionPayrollLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_IncidentLineFact getC_IncidentLineFact() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_IncidentLineFact)MTable.get(getCtx(), de.metas.commission.model.I_C_IncidentLineFact.Table_Name)
			.getPO(getC_IncidentLineFact_ID(), get_TrxName());	}

	/** Set C_IncidentLineFact_ID.
		@param C_IncidentLineFact_ID C_IncidentLineFact_ID	  */
	public void setC_IncidentLineFact_ID (int C_IncidentLineFact_ID)
	{
		if (C_IncidentLineFact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_IncidentLineFact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_IncidentLineFact_ID, Integer.valueOf(C_IncidentLineFact_ID));
	}

	/** Get C_IncidentLineFact_ID.
		@return C_IncidentLineFact_ID	  */
	public int getC_IncidentLineFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_IncidentLineFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prov. %.
		@param Commission 
		Commission stated as a percentage
	  */
	public void setCommission (BigDecimal Commission)
	{
		set_ValueNoCheck (COLUMNNAME_Commission, Commission);
	}

	/** Get Prov. %.
		@return Commission stated as a percentage
	  */
	public BigDecimal getCommission () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Commission);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Provisions-Betrag.
		@param CommissionAmt 
		Provisions-Betrag
	  */
	public void setCommissionAmt (BigDecimal CommissionAmt)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionAmt, CommissionAmt);
	}

	/** Get Provisions-Betrag.
		@return Provisions-Betrag
	  */
	public BigDecimal getCommissionAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zeilennetto.
		@param CommissionAmtBase Zeilennetto	  */
	public void setCommissionAmtBase (BigDecimal CommissionAmtBase)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionAmtBase, CommissionAmtBase);
	}

	/** Get Zeilennetto.
		@return Zeilennetto	  */
	public BigDecimal getCommissionAmtBase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionAmtBase);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Provisionspunkte.
		@param CommissionPoints Provisionspunkte	  */
	public void setCommissionPoints (BigDecimal CommissionPoints)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionPoints, CommissionPoints);
	}

	/** Get Provisionspunkte.
		@return Provisionspunkte	  */
	public BigDecimal getCommissionPoints () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionPoints);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Provisionspunkte pro Einh..
		@param CommissionPointsBase 
		Provisionspunkte pro Einheit
	  */
	public void setCommissionPointsBase (BigDecimal CommissionPointsBase)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionPointsBase, CommissionPointsBase);
	}

	/** Get Provisionspunkte pro Einh..
		@return Provisionspunkte pro Einheit
	  */
	public BigDecimal getCommissionPointsBase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionPointsBase);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Provisionspunkte Summe.
		@param CommissionPointsSum Provisionspunkte Summe	  */
	public void setCommissionPointsSum (BigDecimal CommissionPointsSum)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionPointsSum, CommissionPointsSum);
	}

	/** Get Provisionspunkte Summe.
		@return Provisionspunkte Summe	  */
	public BigDecimal getCommissionPointsSum () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionPointsSum);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Belegdatum.
		@param DateDoc 
		Datum des Belegs
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_ValueNoCheck (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Belegdatum.
		@return Datum des Belegs
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Valuta-Datum.
		@param DateFact Valuta-Datum	  */
	public void setDateFact (Timestamp DateFact)
	{
		set_ValueNoCheck (COLUMNNAME_DateFact, DateFact);
	}

	/** Get Valuta-Datum.
		@return Valuta-Datum	  */
	public Timestamp getDateFact () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFact);
	}

	/** FactType AD_Reference_ID=540128 */
	public static final int FACTTYPE_AD_Reference_ID=540128;
	/** Umbuchung = TR */
	public static final String FACTTYPE_Umbuchung = "TR";
	/** Provisionsberechnung = CA */
	public static final String FACTTYPE_Provisionsberechnung = "CA";
	/** Provisionsabrechnung = PR */
	public static final String FACTTYPE_Provisionsabrechnung = "PR";
	/** Provisionszahlung = PA */
	public static final String FACTTYPE_Provisionszahlung = "PA";
	/** Änderung = CH */
	public static final String FACTTYPE_Aenderung = "CH";
	/** Rückgängig = RE */
	public static final String FACTTYPE_Rueckgaengig = "RE";
	/** Zahlungszuordnung = AL */
	public static final String FACTTYPE_Zahlungszuordnung = "AL";
	/** Zahlungszuordn. Rückg. = AR */
	public static final String FACTTYPE_ZahlungszuordnRueckg = "AR";
	/** Vorgang schließen = CL */
	public static final String FACTTYPE_VorgangSchliessen = "CL";
	/** Provisionsber. Rückabwicklung = CU */
	public static final String FACTTYPE_ProvisionsberRueckabwicklung = "CU";
	/** Provisionsabr. Rückabwicklung = PU */
	public static final String FACTTYPE_ProvisionsabrRueckabwicklung = "PU";
	/** Provisionsabr. Korrektur = PC */
	public static final String FACTTYPE_ProvisionsabrKorrektur = "PC";
	/** Provisionsber. Korrektur = CC */
	public static final String FACTTYPE_ProvisionsberKorrektur = "CC";
	/** Prov.-Zahlungszuordnung = PAL */
	public static final String FACTTYPE_Prov_Zahlungszuordnung = "PAL";
	/** Prov.-Zahlungszuordn. Rückg. = PAR */
	public static final String FACTTYPE_Prov_ZahlungszuordnRueckg = "PAR";
	/** Set Datensatz-Art.
		@param FactType Datensatz-Art	  */
	public void setFactType (String FactType)
	{

		set_Value (COLUMNNAME_FactType, FactType);
	}

	/** Get Datensatz-Art.
		@return Datensatz-Art	  */
	public String getFactType () 
	{
		return (String)get_Value(COLUMNNAME_FactType);
	}

	/** Set Gegenbuchung.
		@param IsCounterEntry Gegenbuchung	  */
	public void setIsCounterEntry (boolean IsCounterEntry)
	{
		set_ValueNoCheck (COLUMNNAME_IsCounterEntry, Boolean.valueOf(IsCounterEntry));
	}

	/** Get Gegenbuchung.
		@return Gegenbuchung	  */
	public boolean isCounterEntry () 
	{
		Object oo = get_Value(COLUMNNAME_IsCounterEntry);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Notiz.
		@param Note 
		Optional weitere Information für ein Dokument
	  */
	public void setNote (String Note)
	{
		set_ValueNoCheck (COLUMNNAME_Note, Note);
	}

	/** Get Notiz.
		@return Optional weitere Information für ein Dokument
	  */
	public String getNote () 
	{
		return (String)get_Value(COLUMNNAME_Note);
	}

	/** Set Verarbeitet.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return The document has been processed
	  */
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
	public void setQty (BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	public void setSeqNo (int SeqNo)
	{
		set_ValueNoCheck (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Status AD_Reference_ID=540070 */
	public static final int STATUS_AD_Reference_ID=540070;
	/** prognostiziert = PR */
	public static final String STATUS_Prognostiziert = "PR";
	/** berechnet = CA */
	public static final String STATUS_Berechnet = "CA";
	/** auszuzahlen = PA */
	public static final String STATUS_Auszuzahlen = "PA";
	/** zu berechnen = CP */
	public static final String STATUS_ZuBerechnen = "CP";
	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	public void setStatus (String Status)
	{

		set_ValueNoCheck (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	public String getStatus () 
	{
		return (String)get_Value(COLUMNNAME_Status);
	}
}