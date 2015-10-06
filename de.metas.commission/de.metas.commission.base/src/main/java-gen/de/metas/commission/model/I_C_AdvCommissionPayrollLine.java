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

/** Generated Interface for C_AdvCommissionPayrollLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvCommissionPayrollLine 
{

    /** TableName=C_AdvCommissionPayrollLine */
    public static final String Table_Name = "C_AdvCommissionPayrollLine";

    /** AD_Table_ID=540085 */
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

    /** Column name C_AdvCommissionInstance_ID */
    public static final String COLUMNNAME_C_AdvCommissionInstance_ID = "C_AdvCommissionInstance_ID";

	/** Set Provisionsvorgang	  */
	public void setC_AdvCommissionInstance_ID (int C_AdvCommissionInstance_ID);

	/** Get Provisionsvorgang	  */
	public int getC_AdvCommissionInstance_ID();

	public de.metas.commission.model.I_C_AdvCommissionInstance getC_AdvCommissionInstance() throws RuntimeException;

    /** Column name C_AdvCommissionPayroll_ID */
    public static final String COLUMNNAME_C_AdvCommissionPayroll_ID = "C_AdvCommissionPayroll_ID";

	/** Set Anlage zur Provisionsabrechnung	  */
	public void setC_AdvCommissionPayroll_ID (int C_AdvCommissionPayroll_ID);

	/** Get Anlage zur Provisionsabrechnung	  */
	public int getC_AdvCommissionPayroll_ID();

	public de.metas.commission.model.I_C_AdvCommissionPayroll getC_AdvCommissionPayroll() throws RuntimeException;

    /** Column name C_AdvCommissionPayrollLine_ID */
    public static final String COLUMNNAME_C_AdvCommissionPayrollLine_ID = "C_AdvCommissionPayrollLine_ID";

	/** Set Prov-Berechungsposition	  */
	public void setC_AdvCommissionPayrollLine_ID (int C_AdvCommissionPayrollLine_ID);

	/** Get Prov-Berechungsposition	  */
	public int getC_AdvCommissionPayrollLine_ID();

    /** Column name C_AdvCommissionTerm_ID */
    public static final String COLUMNNAME_C_AdvCommissionTerm_ID = "C_AdvCommissionTerm_ID";

	/** Set Provisionsart	  */
	public void setC_AdvCommissionTerm_ID (int C_AdvCommissionTerm_ID);

	/** Get Provisionsart	  */
	public int getC_AdvCommissionTerm_ID();

	public de.metas.commission.model.I_C_AdvCommissionTerm getC_AdvCommissionTerm() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Währung.
	  * Die Wå©²ung fð² ¤iesen Eintrag
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Währung.
	  * Die Wå©²ung fð² ¤iesen Eintrag
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

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
	  * Provisionspunkte für ein Stück
	  */
	public void setCommissionPointsBase (BigDecimal CommissionPointsBase);

	/** Get Provisionspunkte pro Einh..
	  * Provisionspunkte für ein Stück
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

    /** Column name C_TaxCategory_ID */
    public static final String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/** Set Steuerkategorie.
	  * Steuerkategorie
	  */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/** Get Steuerkategorie.
	  * Steuerkategorie
	  */
	public int getC_TaxCategory_ID();

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException;

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

    /** Column name IsCommissionLock */
    public static final String COLUMNNAME_IsCommissionLock = "IsCommissionLock";

	/** Set Provisionssperre	  */
	public void setIsCommissionLock (boolean IsCommissionLock);

	/** Get Provisionssperre	  */
	public boolean isCommissionLock();

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Zeile Nr..
	  * Einzelne Zeile in dem Dokument
	  */
	public void setLine (int Line);

	/** Get Zeile Nr..
	  * Einzelne Zeile in dem Dokument
	  */
	public int getLine();

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
