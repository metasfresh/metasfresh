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
package de.metas.commission.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for C_AdvCommissionFact
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvCommissionFact 
{

    /** TableName=C_AdvCommissionFact */
    public static final String Table_Name = "C_AdvCommissionFact";

    /** AD_Table_ID=540074 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant fð² ¤iese Installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/** Set Prozess-Instanz.
	  * Instanz eines Prozesses
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/** Get Prozess-Instanz.
	  * Instanz eines Prozesses
	  */
	public int getAD_PInstance_ID();

	public org.compiere.model.I_AD_PInstance getAD_PInstance() throws RuntimeException;

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set DB-Tabelle.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get DB-Tabelle.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

    /** Column name AmtMultiplier */
    public static final String COLUMNNAME_AmtMultiplier = "AmtMultiplier";

	/** Set Multiplikator Betrag.
	  * Betrags-Multiplikator für die Berechnung von Provisionen
	  */
	public void setAmtMultiplier (BigDecimal AmtMultiplier);

	/** Get Multiplikator Betrag.
	  * Betrags-Multiplikator für die Berechnung von Provisionen
	  */
	public BigDecimal getAmtMultiplier();

    /** Column name C_AdvComFact_IDs_FollowUp */
    public static final String COLUMNNAME_C_AdvComFact_IDs_FollowUp = "C_AdvComFact_IDs_FollowUp";

	/** Set Fortsetzungs-Datensätze.
	  * IDs der Datensätze, die einen bestehenden Datensatz fortführen
	  */
	public void setC_AdvComFact_IDs_FollowUp (String C_AdvComFact_IDs_FollowUp);

	/** Get Fortsetzungs-Datensätze.
	  * IDs der Datensätze, die einen bestehenden Datensatz fortführen
	  */
	public String getC_AdvComFact_IDs_FollowUp();

    /** Column name C_AdvCommissionFactCand_ID */
    public static final String COLUMNNAME_C_AdvCommissionFactCand_ID = "C_AdvCommissionFactCand_ID";

	/** Set Provisionsdaten-Wartschlange	  */
	public void setC_AdvCommissionFactCand_ID (int C_AdvCommissionFactCand_ID);

	/** Get Provisionsdaten-Wartschlange	  */
	public int getC_AdvCommissionFactCand_ID();

	public de.metas.commission.model.I_C_AdvCommissionFactCand getC_AdvCommissionFactCand() throws RuntimeException;

    /** Column name C_AdvCommissionFact_ID */
    public static final String COLUMNNAME_C_AdvCommissionFact_ID = "C_AdvCommissionFact_ID";

	/** Set Vorgangsdatensatz	  */
	public void setC_AdvCommissionFact_ID (int C_AdvCommissionFact_ID);

	/** Get Vorgangsdatensatz	  */
	public int getC_AdvCommissionFact_ID();

    /** Column name C_AdvCommissionInstance_ID */
    public static final String COLUMNNAME_C_AdvCommissionInstance_ID = "C_AdvCommissionInstance_ID";

	/** Set Provisionsvorgang	  */
	public void setC_AdvCommissionInstance_ID (int C_AdvCommissionInstance_ID);

	/** Get Provisionsvorgang	  */
	public int getC_AdvCommissionInstance_ID();

	public de.metas.commission.model.I_C_AdvCommissionInstance getC_AdvCommissionInstance() throws RuntimeException;

    /** Column name C_AdvCommissionPayrollLine_ID */
    public static final String COLUMNNAME_C_AdvCommissionPayrollLine_ID = "C_AdvCommissionPayrollLine_ID";

	/** Set Prov-Berechungsposition	  */
	public void setC_AdvCommissionPayrollLine_ID (int C_AdvCommissionPayrollLine_ID);

	/** Get Prov-Berechungsposition	  */
	public int getC_AdvCommissionPayrollLine_ID();

	public de.metas.commission.model.I_C_AdvCommissionPayrollLine getC_AdvCommissionPayrollLine() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Währung.
	  * Die Währung für diesen Eintrag
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Währung.
	  * Die Währung für diesen Eintrag
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_IncidentLineFact_ID */
    public static final String COLUMNNAME_C_IncidentLineFact_ID = "C_IncidentLineFact_ID";

	/** Set C_IncidentLineFact_ID	  */
	public void setC_IncidentLineFact_ID (int C_IncidentLineFact_ID);

	/** Get C_IncidentLineFact_ID	  */
	public int getC_IncidentLineFact_ID();

	public de.metas.commission.model.I_C_IncidentLineFact getC_IncidentLineFact() throws RuntimeException;

    /** Column name Commission */
    public static final String COLUMNNAME_Commission = "Commission";

	/** Set Prov. %.
	  * Commission stated as a percentage
	  */
	public void setCommission (BigDecimal Commission);

	/** Get Prov. %.
	  * Commission stated as a percentage
	  */
	public BigDecimal getCommission();

    /** Column name CommissionAmt */
    public static final String COLUMNNAME_CommissionAmt = "CommissionAmt";

	/** Set Provisions-Betrag.
	  * Provisions-Betrag
	  */
	public void setCommissionAmt (BigDecimal CommissionAmt);

	/** Get Provisions-Betrag.
	  * Provisions-Betrag
	  */
	public BigDecimal getCommissionAmt();

    /** Column name CommissionAmtBase */
    public static final String COLUMNNAME_CommissionAmtBase = "CommissionAmtBase";

	/** Set Zeilennetto	  */
	public void setCommissionAmtBase (BigDecimal CommissionAmtBase);

	/** Get Zeilennetto	  */
	public BigDecimal getCommissionAmtBase();

    /** Column name CommissionPoints */
    public static final String COLUMNNAME_CommissionPoints = "CommissionPoints";

	/** Set Provisionspunkte	  */
	public void setCommissionPoints (BigDecimal CommissionPoints);

	/** Get Provisionspunkte	  */
	public BigDecimal getCommissionPoints();

    /** Column name CommissionPointsBase */
    public static final String COLUMNNAME_CommissionPointsBase = "CommissionPointsBase";

	/** Set Provisionspunkte pro Einh..
	  * Provisionspunkte pro Einheit
	  */
	public void setCommissionPointsBase (BigDecimal CommissionPointsBase);

	/** Get Provisionspunkte pro Einh..
	  * Provisionspunkte pro Einheit
	  */
	public BigDecimal getCommissionPointsBase();

    /** Column name CommissionPointsSum */
    public static final String COLUMNNAME_CommissionPointsSum = "CommissionPointsSum";

	/** Set Provisionspunkte Summe	  */
	public void setCommissionPointsSum (BigDecimal CommissionPointsSum);

	/** Get Provisionspunkte Summe	  */
	public BigDecimal getCommissionPointsSum();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Belegdatum.
	  * Datum des Belegs
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Belegdatum.
	  * Datum des Belegs
	  */
	public Timestamp getDateDoc();

    /** Column name DateFact */
    public static final String COLUMNNAME_DateFact = "DateFact";

	/** Set Valuta-Datum	  */
	public void setDateFact (Timestamp DateFact);

	/** Get Valuta-Datum	  */
	public Timestamp getDateFact();

    /** Column name FactType */
    public static final String COLUMNNAME_FactType = "FactType";

	/** Set Datensatz-Art	  */
	public void setFactType (String FactType);

	/** Get Datensatz-Art	  */
	public String getFactType();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public boolean isActive();

    /** Column name IsCounterEntry */
    public static final String COLUMNNAME_IsCounterEntry = "IsCounterEntry";

	/** Set Gegenbuchung	  */
	public void setIsCounterEntry (boolean IsCounterEntry);

	/** Get Gegenbuchung	  */
	public boolean isCounterEntry();

    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/** Set Notiz.
	  * Optional weitere Information für ein Dokument
	  */
	public void setNote (String Note);

	/** Get Notiz.
	  * Optional weitere Information für ein Dokument
	  */
	public String getNote();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Verarbeitet.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Verarbeitet.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Menge.
	  * Menge
	  */
	public void setQty (BigDecimal Qty);

	/** Get Menge.
	  * Menge
	  */
	public BigDecimal getQty();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Datensatz-ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Datensatz-ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Reihenfolge.
	  * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	  */
	public void setSeqNo (int SeqNo);

	/** Get Reihenfolge.
	  * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	  */
	public int getSeqNo();

    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/** Set Status.
	  * Status of the currently running check
	  */
	public void setStatus (String Status);

	/** Get Status.
	  * Status of the currently running check
	  */
	public String getStatus();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
