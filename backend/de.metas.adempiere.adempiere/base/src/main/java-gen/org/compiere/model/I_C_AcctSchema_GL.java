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
package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_AcctSchema_GL
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_AcctSchema_GL 
{

    /** TableName=C_AcctSchema_GL */
    public static final String Table_Name = "C_AcctSchema_GL";

    /** AD_Table_ID=266 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_AD_Client>(I_C_AcctSchema_GL.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_AD_Org>(I_C_AcctSchema_GL.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_AcctSchema>(I_C_AcctSchema_GL.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Cash Rounding Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCashRounding_Acct (int CashRounding_Acct);

	/**
	 * Get Cash Rounding Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCashRounding_Acct();

	@Nullable
	org.compiere.model.I_C_ValidCombination getCashRounding_A();

	void setCashRounding_A(@Nullable org.compiere.model.I_C_ValidCombination CashRounding_A);

	ModelColumn<I_C_AcctSchema_GL, I_C_ValidCombination> COLUMN_CashRounding_Acct = new ModelColumn<>(I_C_AcctSchema_GL.class, "CashRounding_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_CashRounding_Acct = "CashRounding_Acct";

	/**
	 * Set Commitment Offset.
	 * Budgetary Commitment Offset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCommitmentOffset_Acct (int CommitmentOffset_Acct);

	/**
	 * Get Commitment Offset.
	 * Budgetary Commitment Offset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCommitmentOffset_Acct();

	public org.compiere.model.I_C_ValidCombination getCommitmentOffset_A();

	public void setCommitmentOffset_A(org.compiere.model.I_C_ValidCombination CommitmentOffset_A);

    /** Column definition for CommitmentOffset_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_CommitmentOffset_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_GL.class, "CommitmentOffset_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name CommitmentOffset_Acct */
    public static final String COLUMNNAME_CommitmentOffset_Acct = "CommitmentOffset_Acct";

	/**
	 * Set Commitment Offset Sales.
	 * Budgetary Commitment Offset Account for Sales
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCommitmentOffsetSales_Acct (int CommitmentOffsetSales_Acct);

	/**
	 * Get Commitment Offset Sales.
	 * Budgetary Commitment Offset Account for Sales
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCommitmentOffsetSales_Acct();

	public org.compiere.model.I_C_ValidCombination getCommitmentOffsetSales_A();

	public void setCommitmentOffsetSales_A(org.compiere.model.I_C_ValidCombination CommitmentOffsetSales_A);

    /** Column definition for CommitmentOffsetSales_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_CommitmentOffsetSales_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_GL.class, "CommitmentOffsetSales_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name CommitmentOffsetSales_Acct */
    public static final String COLUMNNAME_CommitmentOffsetSales_Acct = "CommitmentOffsetSales_Acct";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, Object>(I_C_AcctSchema_GL.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_AD_User>(I_C_AcctSchema_GL.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Konto für Währungsdifferenzen.
	 * Konto, das verwendet wird, wenn eine Währung unausgeglichen ist
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCurrencyBalancing_Acct (int CurrencyBalancing_Acct);

	/**
	 * Get Konto für Währungsdifferenzen.
	 * Konto, das verwendet wird, wenn eine Währung unausgeglichen ist
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCurrencyBalancing_Acct();

	public org.compiere.model.I_C_ValidCombination getCurrencyBalancing_A();

	public void setCurrencyBalancing_A(org.compiere.model.I_C_ValidCombination CurrencyBalancing_A);

    /** Column definition for CurrencyBalancing_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_CurrencyBalancing_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_GL.class, "CurrencyBalancing_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name CurrencyBalancing_Acct */
    public static final String COLUMNNAME_CurrencyBalancing_Acct = "CurrencyBalancing_Acct";

	/**
	 * Set Gewinnvortrag vor Verwendung.
	 * Konto für Gewinnvortrag vor Verwendung
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIncomeSummary_Acct (int IncomeSummary_Acct);

	/**
	 * Get Gewinnvortrag vor Verwendung.
	 * Konto für Gewinnvortrag vor Verwendung
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getIncomeSummary_Acct();

	public org.compiere.model.I_C_ValidCombination getIncomeSummary_A();

	public void setIncomeSummary_A(org.compiere.model.I_C_ValidCombination IncomeSummary_A);

    /** Column definition for IncomeSummary_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_IncomeSummary_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_GL.class, "IncomeSummary_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name IncomeSummary_Acct */
    public static final String COLUMNNAME_IncomeSummary_Acct = "IncomeSummary_Acct";

	/**
	 * Set Forderungen geg. verbundenen Unternehmen.
	 * Konto für Forderungen geg. verbundenen Unternehmen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIntercompanyDueFrom_Acct (int IntercompanyDueFrom_Acct);

	/**
	 * Get Forderungen geg. verbundenen Unternehmen.
	 * Konto für Forderungen geg. verbundenen Unternehmen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getIntercompanyDueFrom_Acct();

	public org.compiere.model.I_C_ValidCombination getIntercompanyDueFrom_A();

	public void setIntercompanyDueFrom_A(org.compiere.model.I_C_ValidCombination IntercompanyDueFrom_A);

    /** Column definition for IntercompanyDueFrom_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_IntercompanyDueFrom_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_GL.class, "IntercompanyDueFrom_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name IntercompanyDueFrom_Acct */
    public static final String COLUMNNAME_IntercompanyDueFrom_Acct = "IntercompanyDueFrom_Acct";

	/**
	 * Set Verbindlichkeiten geg. verbundenen Unternehmen.
	 * Konto für Verbindlichkeiten gegenüber verbundenen Unternehmen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIntercompanyDueTo_Acct (int IntercompanyDueTo_Acct);

	/**
	 * Get Verbindlichkeiten geg. verbundenen Unternehmen.
	 * Konto für Verbindlichkeiten gegenüber verbundenen Unternehmen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getIntercompanyDueTo_Acct();

	public org.compiere.model.I_C_ValidCombination getIntercompanyDueTo_A();

	public void setIntercompanyDueTo_A(org.compiere.model.I_C_ValidCombination IntercompanyDueTo_A);

    /** Column definition for IntercompanyDueTo_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_IntercompanyDueTo_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_GL.class, "IntercompanyDueTo_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name IntercompanyDueTo_Acct */
    public static final String COLUMNNAME_IntercompanyDueTo_Acct = "IntercompanyDueTo_Acct";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, Object>(I_C_AcctSchema_GL.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PPV Offset.
	 * Purchase Price Variance Offset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPPVOffset_Acct (int PPVOffset_Acct);

	/**
	 * Get PPV Offset.
	 * Purchase Price Variance Offset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPPVOffset_Acct();

	public org.compiere.model.I_C_ValidCombination getPPVOffset_A();

	public void setPPVOffset_A(org.compiere.model.I_C_ValidCombination PPVOffset_A);

    /** Column definition for PPVOffset_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_PPVOffset_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_GL.class, "PPVOffset_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name PPVOffset_Acct */
    public static final String COLUMNNAME_PPVOffset_Acct = "PPVOffset_Acct";

	/**
	 * Set Saldenvortrag Sachkonten.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRetainedEarning_Acct (int RetainedEarning_Acct);

	/**
	 * Get Saldenvortrag Sachkonten.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRetainedEarning_Acct();

	public org.compiere.model.I_C_ValidCombination getRetainedEarning_A();

	public void setRetainedEarning_A(org.compiere.model.I_C_ValidCombination RetainedEarning_A);

    /** Column definition for RetainedEarning_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_RetainedEarning_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_GL.class, "RetainedEarning_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name RetainedEarning_Acct */
    public static final String COLUMNNAME_RetainedEarning_Acct = "RetainedEarning_Acct";

	/**
	 * Set CpD-Konto.
	 * CpD-Konto (Conto-pro-Diverse) für doppelte Buchführung bei manuellen Buchungen.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSuspenseBalancing_Acct (int SuspenseBalancing_Acct);

	/**
	 * Get CpD-Konto.
	 * CpD-Konto (Conto-pro-Diverse) für doppelte Buchführung bei manuellen Buchungen.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSuspenseBalancing_Acct();

	public org.compiere.model.I_C_ValidCombination getSuspenseBalancing_A();

	public void setSuspenseBalancing_A(org.compiere.model.I_C_ValidCombination SuspenseBalancing_A);

    /** Column definition for SuspenseBalancing_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_SuspenseBalancing_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_GL.class, "SuspenseBalancing_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name SuspenseBalancing_Acct */
    public static final String COLUMNNAME_SuspenseBalancing_Acct = "SuspenseBalancing_Acct";

	/**
	 * Set CpD-Fehlerkonto.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSuspenseError_Acct (int SuspenseError_Acct);

	/**
	 * Get CpD-Fehlerkonto.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSuspenseError_Acct();

	public org.compiere.model.I_C_ValidCombination getSuspenseError_A();

	public void setSuspenseError_A(org.compiere.model.I_C_ValidCombination SuspenseError_A);

    /** Column definition for SuspenseError_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_SuspenseError_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_GL.class, "SuspenseError_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name SuspenseError_Acct */
    public static final String COLUMNNAME_SuspenseError_Acct = "SuspenseError_Acct";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, Object>(I_C_AcctSchema_GL.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_AD_User>(I_C_AcctSchema_GL.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Wähungsunterschiede verbuchen.
	 * Sollen Währungsdifferenzen verbucht werden?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUseCurrencyBalancing (boolean UseCurrencyBalancing);

	/**
	 * Get Wähungsunterschiede verbuchen.
	 * Sollen Währungsdifferenzen verbucht werden?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseCurrencyBalancing();

    /** Column definition for UseCurrencyBalancing */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_UseCurrencyBalancing = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, Object>(I_C_AcctSchema_GL.class, "UseCurrencyBalancing", null);
    /** Column name UseCurrencyBalancing */
    public static final String COLUMNNAME_UseCurrencyBalancing = "UseCurrencyBalancing";

	/**
	 * Set doppelte Buchführung.
	 * Verwendet doppelte Buchführung über Zwischenkonto bei manuellen Buchungen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUseSuspenseBalancing (boolean UseSuspenseBalancing);

	/**
	 * Get doppelte Buchführung.
	 * Verwendet doppelte Buchführung über Zwischenkonto bei manuellen Buchungen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseSuspenseBalancing();

    /** Column definition for UseSuspenseBalancing */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_UseSuspenseBalancing = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, Object>(I_C_AcctSchema_GL.class, "UseSuspenseBalancing", null);
    /** Column name UseSuspenseBalancing */
    public static final String COLUMNNAME_UseSuspenseBalancing = "UseSuspenseBalancing";

	/**
	 * Set CpD-Fehlerkonto verwenden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUseSuspenseError (boolean UseSuspenseError);

	/**
	 * Get CpD-Fehlerkonto verwenden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseSuspenseError();

    /** Column definition for UseSuspenseError */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_UseSuspenseError = new org.adempiere.model.ModelColumn<I_C_AcctSchema_GL, Object>(I_C_AcctSchema_GL.class, "UseSuspenseError", null);
    /** Column name UseSuspenseError */
    public static final String COLUMNNAME_UseSuspenseError = "UseSuspenseError";
}
