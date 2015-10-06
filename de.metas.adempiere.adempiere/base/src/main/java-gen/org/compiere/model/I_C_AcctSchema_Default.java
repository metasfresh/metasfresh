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


/** Generated Interface for C_AcctSchema_Default
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_AcctSchema_Default 
{

    /** TableName=C_AcctSchema_Default */
    public static final String Table_Name = "C_AcctSchema_Default";

    /** AD_Table_ID=315 */
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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_AD_Client>(I_C_AcctSchema_Default.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_AD_Org>(I_C_AcctSchema_Default.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bank.
	 * Bank Konto
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setB_Asset_Acct (int B_Asset_Acct);

	/**
	 * Get Bank.
	 * Bank Konto
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getB_Asset_Acct();

	public org.compiere.model.I_C_ValidCombination getB_Asset_A();

	public void setB_Asset_A(org.compiere.model.I_C_ValidCombination B_Asset_A);

    /** Column definition for B_Asset_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_Asset_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "B_Asset_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name B_Asset_Acct */
    public static final String COLUMNNAME_B_Asset_Acct = "B_Asset_Acct";

	/**
	 * Set Nebenkosten des Geldverkehrs.
	 * Konto für Nebenkosten des Geldverkehrs
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setB_Expense_Acct (int B_Expense_Acct);

	/**
	 * Get Nebenkosten des Geldverkehrs.
	 * Konto für Nebenkosten des Geldverkehrs
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getB_Expense_Acct();

	public org.compiere.model.I_C_ValidCombination getB_Expense_A();

	public void setB_Expense_A(org.compiere.model.I_C_ValidCombination B_Expense_A);

    /** Column definition for B_Expense_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_Expense_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "B_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name B_Expense_Acct */
    public static final String COLUMNNAME_B_Expense_Acct = "B_Expense_Acct";

	/**
	 * Set Zins Aufwendungen.
	 * Konto für Zins Aufwendungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setB_InterestExp_Acct (int B_InterestExp_Acct);

	/**
	 * Get Zins Aufwendungen.
	 * Konto für Zins Aufwendungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getB_InterestExp_Acct();

	public org.compiere.model.I_C_ValidCombination getB_InterestExp_A();

	public void setB_InterestExp_A(org.compiere.model.I_C_ValidCombination B_InterestExp_A);

    /** Column definition for B_InterestExp_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_InterestExp_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "B_InterestExp_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name B_InterestExp_Acct */
    public static final String COLUMNNAME_B_InterestExp_Acct = "B_InterestExp_Acct";

	/**
	 * Set Zinserträge.
	 * Konto für Zinserträge
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setB_InterestRev_Acct (int B_InterestRev_Acct);

	/**
	 * Get Zinserträge.
	 * Konto für Zinserträge
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getB_InterestRev_Acct();

	public org.compiere.model.I_C_ValidCombination getB_InterestRev_A();

	public void setB_InterestRev_A(org.compiere.model.I_C_ValidCombination B_InterestRev_A);

    /** Column definition for B_InterestRev_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_InterestRev_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "B_InterestRev_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name B_InterestRev_Acct */
    public static final String COLUMNNAME_B_InterestRev_Acct = "B_InterestRev_Acct";

	/**
	 * Set Bank In Transit.
	 * Konto für Bank In Transit
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setB_InTransit_Acct (int B_InTransit_Acct);

	/**
	 * Get Bank In Transit.
	 * Konto für Bank In Transit
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getB_InTransit_Acct();

	public org.compiere.model.I_C_ValidCombination getB_InTransit_A();

	public void setB_InTransit_A(org.compiere.model.I_C_ValidCombination B_InTransit_A);

    /** Column definition for B_InTransit_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_InTransit_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "B_InTransit_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name B_InTransit_Acct */
    public static final String COLUMNNAME_B_InTransit_Acct = "B_InTransit_Acct";

	/**
	 * Set Bezahlung selektiert.
	 * Konto für selektierte Zahlungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setB_PaymentSelect_Acct (int B_PaymentSelect_Acct);

	/**
	 * Get Bezahlung selektiert.
	 * Konto für selektierte Zahlungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getB_PaymentSelect_Acct();

	public org.compiere.model.I_C_ValidCombination getB_PaymentSelect_A();

	public void setB_PaymentSelect_A(org.compiere.model.I_C_ValidCombination B_PaymentSelect_A);

    /** Column definition for B_PaymentSelect_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_PaymentSelect_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "B_PaymentSelect_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name B_PaymentSelect_Acct */
    public static final String COLUMNNAME_B_PaymentSelect_Acct = "B_PaymentSelect_Acct";

	/**
	 * Set Erträge aus Kursdifferenzen.
	 * Konto für Erträge aus Kursdifferenzen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setB_RevaluationGain_Acct (int B_RevaluationGain_Acct);

	/**
	 * Get Erträge aus Kursdifferenzen.
	 * Konto für Erträge aus Kursdifferenzen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getB_RevaluationGain_Acct();

	public org.compiere.model.I_C_ValidCombination getB_RevaluationGain_A();

	public void setB_RevaluationGain_A(org.compiere.model.I_C_ValidCombination B_RevaluationGain_A);

    /** Column definition for B_RevaluationGain_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_RevaluationGain_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "B_RevaluationGain_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name B_RevaluationGain_Acct */
    public static final String COLUMNNAME_B_RevaluationGain_Acct = "B_RevaluationGain_Acct";

	/**
	 * Set Währungsverluste.
	 * Konto für Währungsverluste
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setB_RevaluationLoss_Acct (int B_RevaluationLoss_Acct);

	/**
	 * Get Währungsverluste.
	 * Konto für Währungsverluste
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getB_RevaluationLoss_Acct();

	public org.compiere.model.I_C_ValidCombination getB_RevaluationLoss_A();

	public void setB_RevaluationLoss_A(org.compiere.model.I_C_ValidCombination B_RevaluationLoss_A);

    /** Column definition for B_RevaluationLoss_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_RevaluationLoss_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "B_RevaluationLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name B_RevaluationLoss_Acct */
    public static final String COLUMNNAME_B_RevaluationLoss_Acct = "B_RevaluationLoss_Acct";

	/**
	 * Set Bank Settlement Gain.
	 * Bank Settlement Gain Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setB_SettlementGain_Acct (int B_SettlementGain_Acct);

	/**
	 * Get Bank Settlement Gain.
	 * Bank Settlement Gain Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getB_SettlementGain_Acct();

	public org.compiere.model.I_C_ValidCombination getB_SettlementGain_A();

	public void setB_SettlementGain_A(org.compiere.model.I_C_ValidCombination B_SettlementGain_A);

    /** Column definition for B_SettlementGain_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_SettlementGain_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "B_SettlementGain_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name B_SettlementGain_Acct */
    public static final String COLUMNNAME_B_SettlementGain_Acct = "B_SettlementGain_Acct";

	/**
	 * Set Bank Settlement Loss.
	 * Bank Settlement Loss Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setB_SettlementLoss_Acct (int B_SettlementLoss_Acct);

	/**
	 * Get Bank Settlement Loss.
	 * Bank Settlement Loss Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getB_SettlementLoss_Acct();

	public org.compiere.model.I_C_ValidCombination getB_SettlementLoss_A();

	public void setB_SettlementLoss_A(org.compiere.model.I_C_ValidCombination B_SettlementLoss_A);

    /** Column definition for B_SettlementLoss_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_SettlementLoss_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "B_SettlementLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name B_SettlementLoss_Acct */
    public static final String COLUMNNAME_B_SettlementLoss_Acct = "B_SettlementLoss_Acct";

	/**
	 * Set Nicht zugeordnete Zahlungen.
	 * Konto für nicht zugeordnete Zahlungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setB_UnallocatedCash_Acct (int B_UnallocatedCash_Acct);

	/**
	 * Get Nicht zugeordnete Zahlungen.
	 * Konto für nicht zugeordnete Zahlungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getB_UnallocatedCash_Acct();

	public org.compiere.model.I_C_ValidCombination getB_UnallocatedCash_A();

	public void setB_UnallocatedCash_A(org.compiere.model.I_C_ValidCombination B_UnallocatedCash_A);

    /** Column definition for B_UnallocatedCash_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_UnallocatedCash_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "B_UnallocatedCash_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name B_UnallocatedCash_Acct */
    public static final String COLUMNNAME_B_UnallocatedCash_Acct = "B_UnallocatedCash_Acct";

	/**
	 * Set Bank Unidentified Receipts.
	 * Bank Unidentified Receipts Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setB_Unidentified_Acct (int B_Unidentified_Acct);

	/**
	 * Get Bank Unidentified Receipts.
	 * Bank Unidentified Receipts Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getB_Unidentified_Acct();

	public org.compiere.model.I_C_ValidCombination getB_Unidentified_A();

	public void setB_Unidentified_A(org.compiere.model.I_C_ValidCombination B_Unidentified_A);

    /** Column definition for B_Unidentified_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_Unidentified_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "B_Unidentified_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name B_Unidentified_Acct */
    public static final String COLUMNNAME_B_Unidentified_Acct = "B_Unidentified_Acct";

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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_AcctSchema>(I_C_AcctSchema_Default.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Erhaltene Anzahlungen.
	 * Konto für Erhaltene Anzahlungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Prepayment_Acct (int C_Prepayment_Acct);

	/**
	 * Get Erhaltene Anzahlungen.
	 * Konto für Erhaltene Anzahlungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Prepayment_Acct();

	public org.compiere.model.I_C_ValidCombination getC_Prepayment_A();

	public void setC_Prepayment_A(org.compiere.model.I_C_ValidCombination C_Prepayment_A);

    /** Column definition for C_Prepayment_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_C_Prepayment_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "C_Prepayment_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name C_Prepayment_Acct */
    public static final String COLUMNNAME_C_Prepayment_Acct = "C_Prepayment_Acct";

	/**
	 * Set Forderungen aus Lieferungen.
	 * Konto für Forderungen aus Lieferungen (und Leistungen)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Receivable_Acct (int C_Receivable_Acct);

	/**
	 * Get Forderungen aus Lieferungen.
	 * Konto für Forderungen aus Lieferungen (und Leistungen)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Receivable_Acct();

	public org.compiere.model.I_C_ValidCombination getC_Receivable_A();

	public void setC_Receivable_A(org.compiere.model.I_C_ValidCombination C_Receivable_A);

    /** Column definition for C_Receivable_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_C_Receivable_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "C_Receivable_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name C_Receivable_Acct */
    public static final String COLUMNNAME_C_Receivable_Acct = "C_Receivable_Acct";

	/**
	 * Set Forderungen aus Dienstleistungen.
	 * Konto für Forderungen aus Dienstleistungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Receivable_Services_Acct (int C_Receivable_Services_Acct);

	/**
	 * Get Forderungen aus Dienstleistungen.
	 * Konto für Forderungen aus Dienstleistungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Receivable_Services_Acct();

	public org.compiere.model.I_C_ValidCombination getC_Receivable_Services_A();

	public void setC_Receivable_Services_A(org.compiere.model.I_C_ValidCombination C_Receivable_Services_A);

    /** Column definition for C_Receivable_Services_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_C_Receivable_Services_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "C_Receivable_Services_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name C_Receivable_Services_Acct */
    public static final String COLUMNNAME_C_Receivable_Services_Acct = "C_Receivable_Services_Acct";

	/**
	 * Set Kasse.
	 * Konto für Kasse
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCB_Asset_Acct (int CB_Asset_Acct);

	/**
	 * Get Kasse.
	 * Konto für Kasse
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCB_Asset_Acct();

	public org.compiere.model.I_C_ValidCombination getCB_Asset_A();

	public void setCB_Asset_A(org.compiere.model.I_C_ValidCombination CB_Asset_A);

    /** Column definition for CB_Asset_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_CB_Asset_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "CB_Asset_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name CB_Asset_Acct */
    public static final String COLUMNNAME_CB_Asset_Acct = "CB_Asset_Acct";

	/**
	 * Set Geldtransit.
	 * Konto für Geldtransit
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCB_CashTransfer_Acct (int CB_CashTransfer_Acct);

	/**
	 * Get Geldtransit.
	 * Konto für Geldtransit
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCB_CashTransfer_Acct();

	public org.compiere.model.I_C_ValidCombination getCB_CashTransfer_A();

	public void setCB_CashTransfer_A(org.compiere.model.I_C_ValidCombination CB_CashTransfer_A);

    /** Column definition for CB_CashTransfer_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_CB_CashTransfer_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "CB_CashTransfer_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name CB_CashTransfer_Acct */
    public static final String COLUMNNAME_CB_CashTransfer_Acct = "CB_CashTransfer_Acct";

	/**
	 * Set Kassendifferenz.
	 * Konto für Kassendifferenz
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCB_Differences_Acct (int CB_Differences_Acct);

	/**
	 * Get Kassendifferenz.
	 * Konto für Kassendifferenz
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCB_Differences_Acct();

	public org.compiere.model.I_C_ValidCombination getCB_Differences_A();

	public void setCB_Differences_A(org.compiere.model.I_C_ValidCombination CB_Differences_A);

    /** Column definition for CB_Differences_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_CB_Differences_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "CB_Differences_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name CB_Differences_Acct */
    public static final String COLUMNNAME_CB_Differences_Acct = "CB_Differences_Acct";

	/**
	 * Set Kasse Aufwand.
	 * Konto für Kasse Aufwand
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCB_Expense_Acct (int CB_Expense_Acct);

	/**
	 * Get Kasse Aufwand.
	 * Konto für Kasse Aufwand
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCB_Expense_Acct();

	public org.compiere.model.I_C_ValidCombination getCB_Expense_A();

	public void setCB_Expense_A(org.compiere.model.I_C_ValidCombination CB_Expense_A);

    /** Column definition for CB_Expense_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_CB_Expense_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "CB_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name CB_Expense_Acct */
    public static final String COLUMNNAME_CB_Expense_Acct = "CB_Expense_Acct";

	/**
	 * Set Kasse Ertrag.
	 * Konto für Kasse Ertrag
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCB_Receipt_Acct (int CB_Receipt_Acct);

	/**
	 * Get Kasse Ertrag.
	 * Konto für Kasse Ertrag
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCB_Receipt_Acct();

	public org.compiere.model.I_C_ValidCombination getCB_Receipt_A();

	public void setCB_Receipt_A(org.compiere.model.I_C_ValidCombination CB_Receipt_A);

    /** Column definition for CB_Receipt_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_CB_Receipt_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "CB_Receipt_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name CB_Receipt_Acct */
    public static final String COLUMNNAME_CB_Receipt_Acct = "CB_Receipt_Acct";

	/**
	 * Set Betriebliche Aufwendungen.
	 * Konto für Betriebliche Aufwendungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCh_Expense_Acct (int Ch_Expense_Acct);

	/**
	 * Get Betriebliche Aufwendungen.
	 * Konto für Betriebliche Aufwendungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCh_Expense_Acct();

	public org.compiere.model.I_C_ValidCombination getCh_Expense_A();

	public void setCh_Expense_A(org.compiere.model.I_C_ValidCombination Ch_Expense_A);

    /** Column definition for Ch_Expense_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_Ch_Expense_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "Ch_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name Ch_Expense_Acct */
    public static final String COLUMNNAME_Ch_Expense_Acct = "Ch_Expense_Acct";

	/**
	 * Set Sonstige Einnahmen.
	 * Konto für Sonstige Einnahmen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCh_Revenue_Acct (int Ch_Revenue_Acct);

	/**
	 * Get Sonstige Einnahmen.
	 * Konto für Sonstige Einnahmen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCh_Revenue_Acct();

	public org.compiere.model.I_C_ValidCombination getCh_Revenue_A();

	public void setCh_Revenue_A(org.compiere.model.I_C_ValidCombination Ch_Revenue_A);

    /** Column definition for Ch_Revenue_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_Ch_Revenue_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "Ch_Revenue_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name Ch_Revenue_Acct */
    public static final String COLUMNNAME_Ch_Revenue_Acct = "Ch_Revenue_Acct";

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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, Object>(I_C_AcctSchema_Default.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_AD_User>(I_C_AcctSchema_Default.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Löhne und Gehälter.
	 * Konto für Löhne und Gehälter
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setE_Expense_Acct (int E_Expense_Acct);

	/**
	 * Get Löhne und Gehälter.
	 * Konto für Löhne und Gehälter
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getE_Expense_Acct();

	public org.compiere.model.I_C_ValidCombination getE_Expense_A();

	public void setE_Expense_A(org.compiere.model.I_C_ValidCombination E_Expense_A);

    /** Column definition for E_Expense_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_E_Expense_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "E_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name E_Expense_Acct */
    public static final String COLUMNNAME_E_Expense_Acct = "E_Expense_Acct";

	/**
	 * Set Geleistete Anzahlungen Mitarbeiter.
	 * Konto für geleistete Anzahlungen an Mitarbeiter
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setE_Prepayment_Acct (int E_Prepayment_Acct);

	/**
	 * Get Geleistete Anzahlungen Mitarbeiter.
	 * Konto für geleistete Anzahlungen an Mitarbeiter
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getE_Prepayment_Acct();

	public org.compiere.model.I_C_ValidCombination getE_Prepayment_A();

	public void setE_Prepayment_A(org.compiere.model.I_C_ValidCombination E_Prepayment_A);

    /** Column definition for E_Prepayment_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_E_Prepayment_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "E_Prepayment_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name E_Prepayment_Acct */
    public static final String COLUMNNAME_E_Prepayment_Acct = "E_Prepayment_Acct";

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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, Object>(I_C_AcctSchema_Default.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Not-invoiced Receipts.
	 * Account for not-invoiced Material Receipts
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setNotInvoicedReceipts_Acct (int NotInvoicedReceipts_Acct);

	/**
	 * Get Not-invoiced Receipts.
	 * Account for not-invoiced Material Receipts
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getNotInvoicedReceipts_Acct();

	public org.compiere.model.I_C_ValidCombination getNotInvoicedReceipts_A();

	public void setNotInvoicedReceipts_A(org.compiere.model.I_C_ValidCombination NotInvoicedReceipts_A);

    /** Column definition for NotInvoicedReceipts_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_NotInvoicedReceipts_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "NotInvoicedReceipts_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name NotInvoicedReceipts_Acct */
    public static final String COLUMNNAME_NotInvoicedReceipts_Acct = "NotInvoicedReceipts_Acct";

	/**
	 * Set Unfertige Erzeugnisse.
	 * Konto für unfertige Erzeugnisse
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setNotInvoicedReceivables_Acct (int NotInvoicedReceivables_Acct);

	/**
	 * Get Unfertige Erzeugnisse.
	 * Konto für unfertige Erzeugnisse
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getNotInvoicedReceivables_Acct();

	public org.compiere.model.I_C_ValidCombination getNotInvoicedReceivables_A();

	public void setNotInvoicedReceivables_A(org.compiere.model.I_C_ValidCombination NotInvoicedReceivables_A);

    /** Column definition for NotInvoicedReceivables_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_NotInvoicedReceivables_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "NotInvoicedReceivables_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name NotInvoicedReceivables_Acct */
    public static final String COLUMNNAME_NotInvoicedReceivables_Acct = "NotInvoicedReceivables_Acct";

	/**
	 * Set Nicht abgerechnete Einnahmen.
	 * Konto für nicht abgerechnete Einnahmen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setNotInvoicedRevenue_Acct (int NotInvoicedRevenue_Acct);

	/**
	 * Get Nicht abgerechnete Einnahmen.
	 * Konto für nicht abgerechnete Einnahmen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getNotInvoicedRevenue_Acct();

	public org.compiere.model.I_C_ValidCombination getNotInvoicedRevenue_A();

	public void setNotInvoicedRevenue_A(org.compiere.model.I_C_ValidCombination NotInvoicedRevenue_A);

    /** Column definition for NotInvoicedRevenue_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_NotInvoicedRevenue_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "NotInvoicedRevenue_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name NotInvoicedRevenue_Acct */
    public static final String COLUMNNAME_NotInvoicedRevenue_Acct = "NotInvoicedRevenue_Acct";

	/**
	 * Set Warenbestand.
	 * Konto für Warenbestand
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Asset_Acct (int P_Asset_Acct);

	/**
	 * Get Warenbestand.
	 * Konto für Warenbestand
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Asset_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Asset_A();

	public void setP_Asset_A(org.compiere.model.I_C_ValidCombination P_Asset_A);

    /** Column definition for P_Asset_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Asset_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_Asset_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Asset_Acct */
    public static final String COLUMNNAME_P_Asset_Acct = "P_Asset_Acct";

	/**
	 * Set Burden.
	 * The Burden account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Burden_Acct (int P_Burden_Acct);

	/**
	 * Get Burden.
	 * The Burden account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Burden_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Burden_A();

	public void setP_Burden_A(org.compiere.model.I_C_ValidCombination P_Burden_A);

    /** Column definition for P_Burden_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Burden_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_Burden_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Burden_Acct */
    public static final String COLUMNNAME_P_Burden_Acct = "P_Burden_Acct";

	/**
	 * Set Produkt Vertriebsausgaben.
	 * Konto für Produkt Vertriebsausgaben
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_COGS_Acct (int P_COGS_Acct);

	/**
	 * Get Produkt Vertriebsausgaben.
	 * Konto für Produkt Vertriebsausgaben
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_COGS_Acct();

	public org.compiere.model.I_C_ValidCombination getP_COGS_A();

	public void setP_COGS_A(org.compiere.model.I_C_ValidCombination P_COGS_A);

    /** Column definition for P_COGS_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_COGS_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_COGS_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_COGS_Acct */
    public static final String COLUMNNAME_P_COGS_Acct = "P_COGS_Acct";

	/**
	 * Set Bezugsnebenkosten.
	 * Konto für Bezugsnebenkosten
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_CostAdjustment_Acct (int P_CostAdjustment_Acct);

	/**
	 * Get Bezugsnebenkosten.
	 * Konto für Bezugsnebenkosten
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_CostAdjustment_Acct();

	public org.compiere.model.I_C_ValidCombination getP_CostAdjustment_A();

	public void setP_CostAdjustment_A(org.compiere.model.I_C_ValidCombination P_CostAdjustment_A);

    /** Column definition for P_CostAdjustment_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_CostAdjustment_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_CostAdjustment_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_CostAdjustment_Acct */
    public static final String COLUMNNAME_P_CostAdjustment_Acct = "P_CostAdjustment_Acct";

	/**
	 * Set Cost Of Production.
	 * The Cost Of Production account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_CostOfProduction_Acct (int P_CostOfProduction_Acct);

	/**
	 * Get Cost Of Production.
	 * The Cost Of Production account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_CostOfProduction_Acct();

	public org.compiere.model.I_C_ValidCombination getP_CostOfProduction_A();

	public void setP_CostOfProduction_A(org.compiere.model.I_C_ValidCombination P_CostOfProduction_A);

    /** Column definition for P_CostOfProduction_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_CostOfProduction_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_CostOfProduction_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_CostOfProduction_Acct */
    public static final String COLUMNNAME_P_CostOfProduction_Acct = "P_CostOfProduction_Acct";

	/**
	 * Set Produkt Aufwand.
	 * Konto für Produkt Aufwand
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Expense_Acct (int P_Expense_Acct);

	/**
	 * Get Produkt Aufwand.
	 * Konto für Produkt Aufwand
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Expense_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Expense_A();

	public void setP_Expense_A(org.compiere.model.I_C_ValidCombination P_Expense_A);

    /** Column definition for P_Expense_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Expense_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Expense_Acct */
    public static final String COLUMNNAME_P_Expense_Acct = "P_Expense_Acct";

	/**
	 * Set Floor Stock.
	 * The Floor Stock account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_FloorStock_Acct (int P_FloorStock_Acct);

	/**
	 * Get Floor Stock.
	 * The Floor Stock account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_FloorStock_Acct();

	public org.compiere.model.I_C_ValidCombination getP_FloorStock_A();

	public void setP_FloorStock_A(org.compiere.model.I_C_ValidCombination P_FloorStock_A);

    /** Column definition for P_FloorStock_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_FloorStock_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_FloorStock_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_FloorStock_Acct */
    public static final String COLUMNNAME_P_FloorStock_Acct = "P_FloorStock_Acct";

	/**
	 * Set Inventory Clearing.
	 * Product Inventory Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_InventoryClearing_Acct (int P_InventoryClearing_Acct);

	/**
	 * Get Inventory Clearing.
	 * Product Inventory Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_InventoryClearing_Acct();

	public org.compiere.model.I_C_ValidCombination getP_InventoryClearing_A();

	public void setP_InventoryClearing_A(org.compiere.model.I_C_ValidCombination P_InventoryClearing_A);

    /** Column definition for P_InventoryClearing_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_InventoryClearing_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_InventoryClearing_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_InventoryClearing_Acct */
    public static final String COLUMNNAME_P_InventoryClearing_Acct = "P_InventoryClearing_Acct";

	/**
	 * Set Preisdifferenz Einkauf Rechnung.
	 * Konto für Preisdifferenz Einkauf Rechnung
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_InvoicePriceVariance_Acct (int P_InvoicePriceVariance_Acct);

	/**
	 * Get Preisdifferenz Einkauf Rechnung.
	 * Konto für Preisdifferenz Einkauf Rechnung
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_InvoicePriceVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_InvoicePriceVariance_A();

	public void setP_InvoicePriceVariance_A(org.compiere.model.I_C_ValidCombination P_InvoicePriceVariance_A);

    /** Column definition for P_InvoicePriceVariance_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_InvoicePriceVariance_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_InvoicePriceVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_InvoicePriceVariance_Acct */
    public static final String COLUMNNAME_P_InvoicePriceVariance_Acct = "P_InvoicePriceVariance_Acct";

	/**
	 * Set Labor.
	 * The Labor account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Labor_Acct (int P_Labor_Acct);

	/**
	 * Get Labor.
	 * The Labor account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Labor_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Labor_A();

	public void setP_Labor_A(org.compiere.model.I_C_ValidCombination P_Labor_A);

    /** Column definition for P_Labor_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Labor_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_Labor_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Labor_Acct */
    public static final String COLUMNNAME_P_Labor_Acct = "P_Labor_Acct";

	/**
	 * Set Method Change Variance.
	 * The Method Change Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_MethodChangeVariance_Acct (int P_MethodChangeVariance_Acct);

	/**
	 * Get Method Change Variance.
	 * The Method Change Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_MethodChangeVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_MethodChangeVariance_A();

	public void setP_MethodChangeVariance_A(org.compiere.model.I_C_ValidCombination P_MethodChangeVariance_A);

    /** Column definition for P_MethodChangeVariance_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_MethodChangeVariance_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_MethodChangeVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_MethodChangeVariance_Acct */
    public static final String COLUMNNAME_P_MethodChangeVariance_Acct = "P_MethodChangeVariance_Acct";

	/**
	 * Set Mix Variance.
	 * The Mix Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_MixVariance_Acct (int P_MixVariance_Acct);

	/**
	 * Get Mix Variance.
	 * The Mix Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_MixVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_MixVariance_A();

	public void setP_MixVariance_A(org.compiere.model.I_C_ValidCombination P_MixVariance_A);

    /** Column definition for P_MixVariance_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_MixVariance_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_MixVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_MixVariance_Acct */
    public static final String COLUMNNAME_P_MixVariance_Acct = "P_MixVariance_Acct";

	/**
	 * Set Outside Processing.
	 * The Outside Processing Account is the account used in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_OutsideProcessing_Acct (int P_OutsideProcessing_Acct);

	/**
	 * Get Outside Processing.
	 * The Outside Processing Account is the account used in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_OutsideProcessing_Acct();

	public org.compiere.model.I_C_ValidCombination getP_OutsideProcessing_A();

	public void setP_OutsideProcessing_A(org.compiere.model.I_C_ValidCombination P_OutsideProcessing_A);

    /** Column definition for P_OutsideProcessing_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_OutsideProcessing_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_OutsideProcessing_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_OutsideProcessing_Acct */
    public static final String COLUMNNAME_P_OutsideProcessing_Acct = "P_OutsideProcessing_Acct";

	/**
	 * Set Overhead.
	 * The Overhead account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Overhead_Acct (int P_Overhead_Acct);

	/**
	 * Get Overhead.
	 * The Overhead account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Overhead_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Overhead_A();

	public void setP_Overhead_A(org.compiere.model.I_C_ValidCombination P_Overhead_A);

    /** Column definition for P_Overhead_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Overhead_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_Overhead_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Overhead_Acct */
    public static final String COLUMNNAME_P_Overhead_Acct = "P_Overhead_Acct";

	/**
	 * Set Preisdifferenz Bestellung.
	 * Konto für Differenz zwischen Standard Kosten und Preis in Bestellung.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_PurchasePriceVariance_Acct (int P_PurchasePriceVariance_Acct);

	/**
	 * Get Preisdifferenz Bestellung.
	 * Konto für Differenz zwischen Standard Kosten und Preis in Bestellung.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_PurchasePriceVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_PurchasePriceVariance_A();

	public void setP_PurchasePriceVariance_A(org.compiere.model.I_C_ValidCombination P_PurchasePriceVariance_A);

    /** Column definition for P_PurchasePriceVariance_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_PurchasePriceVariance_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_PurchasePriceVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_PurchasePriceVariance_Acct */
    public static final String COLUMNNAME_P_PurchasePriceVariance_Acct = "P_PurchasePriceVariance_Acct";

	/**
	 * Set Rate Variance.
	 * The Rate Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_RateVariance_Acct (int P_RateVariance_Acct);

	/**
	 * Get Rate Variance.
	 * The Rate Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_RateVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_RateVariance_A();

	public void setP_RateVariance_A(org.compiere.model.I_C_ValidCombination P_RateVariance_A);

    /** Column definition for P_RateVariance_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_RateVariance_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_RateVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_RateVariance_Acct */
    public static final String COLUMNNAME_P_RateVariance_Acct = "P_RateVariance_Acct";

	/**
	 * Set Produkt Ertrag.
	 * Konto für Produkt Ertrag
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Revenue_Acct (int P_Revenue_Acct);

	/**
	 * Get Produkt Ertrag.
	 * Konto für Produkt Ertrag
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Revenue_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Revenue_A();

	public void setP_Revenue_A(org.compiere.model.I_C_ValidCombination P_Revenue_A);

    /** Column definition for P_Revenue_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Revenue_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_Revenue_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Revenue_Acct */
    public static final String COLUMNNAME_P_Revenue_Acct = "P_Revenue_Acct";

	/**
	 * Set Scrap.
	 * The Scrap account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Scrap_Acct (int P_Scrap_Acct);

	/**
	 * Get Scrap.
	 * The Scrap account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Scrap_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Scrap_A();

	public void setP_Scrap_A(org.compiere.model.I_C_ValidCombination P_Scrap_A);

    /** Column definition for P_Scrap_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Scrap_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_Scrap_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Scrap_Acct */
    public static final String COLUMNNAME_P_Scrap_Acct = "P_Scrap_Acct";

	/**
	 * Set Gewährte Rabatte.
	 * Konto für gewährte Rabatte
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_TradeDiscountGrant_Acct (int P_TradeDiscountGrant_Acct);

	/**
	 * Get Gewährte Rabatte.
	 * Konto für gewährte Rabatte
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_TradeDiscountGrant_Acct();

	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountGrant_A();

	public void setP_TradeDiscountGrant_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountGrant_A);

    /** Column definition for P_TradeDiscountGrant_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_TradeDiscountGrant_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_TradeDiscountGrant_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_TradeDiscountGrant_Acct */
    public static final String COLUMNNAME_P_TradeDiscountGrant_Acct = "P_TradeDiscountGrant_Acct";

	/**
	 * Set Erhaltene Rabatte.
	 * Konto für erhaltene Rabatte
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_TradeDiscountRec_Acct (int P_TradeDiscountRec_Acct);

	/**
	 * Get Erhaltene Rabatte.
	 * Konto für erhaltene Rabatte
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_TradeDiscountRec_Acct();

	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountRec_A();

	public void setP_TradeDiscountRec_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountRec_A);

    /** Column definition for P_TradeDiscountRec_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_TradeDiscountRec_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_TradeDiscountRec_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_TradeDiscountRec_Acct */
    public static final String COLUMNNAME_P_TradeDiscountRec_Acct = "P_TradeDiscountRec_Acct";

	/**
	 * Set Usage Variance.
	 * The Usage Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_UsageVariance_Acct (int P_UsageVariance_Acct);

	/**
	 * Get Usage Variance.
	 * The Usage Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_UsageVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_UsageVariance_A();

	public void setP_UsageVariance_A(org.compiere.model.I_C_ValidCombination P_UsageVariance_A);

    /** Column definition for P_UsageVariance_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_UsageVariance_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_UsageVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_UsageVariance_Acct */
    public static final String COLUMNNAME_P_UsageVariance_Acct = "P_UsageVariance_Acct";

	/**
	 * Set Unfertige Leistungen.
	 * Das Konto Unfertige Leistungen wird im Produktionaauftrag verwendet
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_WIP_Acct (int P_WIP_Acct);

	/**
	 * Get Unfertige Leistungen.
	 * Das Konto Unfertige Leistungen wird im Produktionaauftrag verwendet
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_WIP_Acct();

	public org.compiere.model.I_C_ValidCombination getP_WIP_A();

	public void setP_WIP_A(org.compiere.model.I_C_ValidCombination P_WIP_A);

    /** Column definition for P_WIP_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_WIP_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "P_WIP_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_WIP_Acct */
    public static final String COLUMNNAME_P_WIP_Acct = "P_WIP_Acct";

	/**
	 * Set Gewährte Skonti.
	 * Konto für gewährte Skonti
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayDiscount_Exp_Acct (int PayDiscount_Exp_Acct);

	/**
	 * Get Gewährte Skonti.
	 * Konto für gewährte Skonti
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPayDiscount_Exp_Acct();

	public org.compiere.model.I_C_ValidCombination getPayDiscount_Exp_A();

	public void setPayDiscount_Exp_A(org.compiere.model.I_C_ValidCombination PayDiscount_Exp_A);

    /** Column definition for PayDiscount_Exp_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_PayDiscount_Exp_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "PayDiscount_Exp_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name PayDiscount_Exp_Acct */
    public static final String COLUMNNAME_PayDiscount_Exp_Acct = "PayDiscount_Exp_Acct";

	/**
	 * Set Erhaltene Skonti.
	 * Konto für erhaltene Skonti
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayDiscount_Rev_Acct (int PayDiscount_Rev_Acct);

	/**
	 * Get Erhaltene Skonti.
	 * Konto für erhaltene Skonti
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPayDiscount_Rev_Acct();

	public org.compiere.model.I_C_ValidCombination getPayDiscount_Rev_A();

	public void setPayDiscount_Rev_A(org.compiere.model.I_C_ValidCombination PayDiscount_Rev_A);

    /** Column definition for PayDiscount_Rev_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_PayDiscount_Rev_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "PayDiscount_Rev_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name PayDiscount_Rev_Acct */
    public static final String COLUMNNAME_PayDiscount_Rev_Acct = "PayDiscount_Rev_Acct";

	/**
	 * Set Project Asset.
	 * Project Asset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPJ_Asset_Acct (int PJ_Asset_Acct);

	/**
	 * Get Project Asset.
	 * Project Asset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPJ_Asset_Acct();

	public org.compiere.model.I_C_ValidCombination getPJ_Asset_A();

	public void setPJ_Asset_A(org.compiere.model.I_C_ValidCombination PJ_Asset_A);

    /** Column definition for PJ_Asset_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_PJ_Asset_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "PJ_Asset_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name PJ_Asset_Acct */
    public static final String COLUMNNAME_PJ_Asset_Acct = "PJ_Asset_Acct";

	/**
	 * Set Unfertige Leistungen.
	 * Konto für Unfertige Leistungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPJ_WIP_Acct (int PJ_WIP_Acct);

	/**
	 * Get Unfertige Leistungen.
	 * Konto für Unfertige Leistungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPJ_WIP_Acct();

	public org.compiere.model.I_C_ValidCombination getPJ_WIP_A();

	public void setPJ_WIP_A(org.compiere.model.I_C_ValidCombination PJ_WIP_A);

    /** Column definition for PJ_WIP_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_PJ_WIP_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "PJ_WIP_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name PJ_WIP_Acct */
    public static final String COLUMNNAME_PJ_WIP_Acct = "PJ_WIP_Acct";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, Object>(I_C_AcctSchema_Default.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Realisierte Währungsgewinne.
	 * Konto für Realisierte Währungsgewinne
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRealizedGain_Acct (int RealizedGain_Acct);

	/**
	 * Get Realisierte Währungsgewinne.
	 * Konto für Realisierte Währungsgewinne
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRealizedGain_Acct();

	public org.compiere.model.I_C_ValidCombination getRealizedGain_A();

	public void setRealizedGain_A(org.compiere.model.I_C_ValidCombination RealizedGain_A);

    /** Column definition for RealizedGain_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_RealizedGain_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "RealizedGain_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name RealizedGain_Acct */
    public static final String COLUMNNAME_RealizedGain_Acct = "RealizedGain_Acct";

	/**
	 * Set Realisierte Währungsverluste.
	 * Konto für realisierte Währungsverluste
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRealizedLoss_Acct (int RealizedLoss_Acct);

	/**
	 * Get Realisierte Währungsverluste.
	 * Konto für realisierte Währungsverluste
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRealizedLoss_Acct();

	public org.compiere.model.I_C_ValidCombination getRealizedLoss_A();

	public void setRealizedLoss_A(org.compiere.model.I_C_ValidCombination RealizedLoss_A);

    /** Column definition for RealizedLoss_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_RealizedLoss_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "RealizedLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name RealizedLoss_Acct */
    public static final String COLUMNNAME_RealizedLoss_Acct = "RealizedLoss_Acct";

	/**
	 * Set Vorsteuer.
	 * Konto für Vorsteuer
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setT_Credit_Acct (int T_Credit_Acct);

	/**
	 * Get Vorsteuer.
	 * Konto für Vorsteuer
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getT_Credit_Acct();

	public org.compiere.model.I_C_ValidCombination getT_Credit_A();

	public void setT_Credit_A(org.compiere.model.I_C_ValidCombination T_Credit_A);

    /** Column definition for T_Credit_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_T_Credit_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "T_Credit_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name T_Credit_Acct */
    public static final String COLUMNNAME_T_Credit_Acct = "T_Credit_Acct";

	/**
	 * Set Geschuldete MwSt..
	 * Konto für geschuldete MwSt.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setT_Due_Acct (int T_Due_Acct);

	/**
	 * Get Geschuldete MwSt..
	 * Konto für geschuldete MwSt.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getT_Due_Acct();

	public org.compiere.model.I_C_ValidCombination getT_Due_A();

	public void setT_Due_A(org.compiere.model.I_C_ValidCombination T_Due_A);

    /** Column definition for T_Due_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_T_Due_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "T_Due_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name T_Due_Acct */
    public static final String COLUMNNAME_T_Due_Acct = "T_Due_Acct";

	/**
	 * Set Sonstige Steuern.
	 * Account for paid tax you cannot reclaim
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setT_Expense_Acct (int T_Expense_Acct);

	/**
	 * Get Sonstige Steuern.
	 * Account for paid tax you cannot reclaim
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getT_Expense_Acct();

	public org.compiere.model.I_C_ValidCombination getT_Expense_A();

	public void setT_Expense_A(org.compiere.model.I_C_ValidCombination T_Expense_A);

    /** Column definition for T_Expense_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_T_Expense_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "T_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name T_Expense_Acct */
    public static final String COLUMNNAME_T_Expense_Acct = "T_Expense_Acct";

	/**
	 * Set Verbindlichkeiten aus Steuern.
	 * Konto für Verbindlichkeiten aus Steuern
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setT_Liability_Acct (int T_Liability_Acct);

	/**
	 * Get Verbindlichkeiten aus Steuern.
	 * Konto für Verbindlichkeiten aus Steuern
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getT_Liability_Acct();

	public org.compiere.model.I_C_ValidCombination getT_Liability_A();

	public void setT_Liability_A(org.compiere.model.I_C_ValidCombination T_Liability_A);

    /** Column definition for T_Liability_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_T_Liability_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "T_Liability_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name T_Liability_Acct */
    public static final String COLUMNNAME_T_Liability_Acct = "T_Liability_Acct";

	/**
	 * Set Steuerüberzahlungen.
	 * Konto für Steuerüberzahlungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setT_Receivables_Acct (int T_Receivables_Acct);

	/**
	 * Get Steuerüberzahlungen.
	 * Konto für Steuerüberzahlungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getT_Receivables_Acct();

	public org.compiere.model.I_C_ValidCombination getT_Receivables_A();

	public void setT_Receivables_A(org.compiere.model.I_C_ValidCombination T_Receivables_A);

    /** Column definition for T_Receivables_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_T_Receivables_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "T_Receivables_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name T_Receivables_Acct */
    public static final String COLUMNNAME_T_Receivables_Acct = "T_Receivables_Acct";

	/**
	 * Set Vorausberechnete Einnahmen.
	 * Konto für vorausberechnete Einnahmen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUnEarnedRevenue_Acct (int UnEarnedRevenue_Acct);

	/**
	 * Get Vorausberechnete Einnahmen.
	 * Konto für vorausberechnete Einnahmen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUnEarnedRevenue_Acct();

	public org.compiere.model.I_C_ValidCombination getUnEarnedRevenue_A();

	public void setUnEarnedRevenue_A(org.compiere.model.I_C_ValidCombination UnEarnedRevenue_A);

    /** Column definition for UnEarnedRevenue_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_UnEarnedRevenue_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "UnEarnedRevenue_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name UnEarnedRevenue_Acct */
    public static final String COLUMNNAME_UnEarnedRevenue_Acct = "UnEarnedRevenue_Acct";

	/**
	 * Set Nicht realisierte Währungsgewinne.
	 * Konto für nicht realisierte Währungsgewinne
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUnrealizedGain_Acct (int UnrealizedGain_Acct);

	/**
	 * Get Nicht realisierte Währungsgewinne.
	 * Konto für nicht realisierte Währungsgewinne
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUnrealizedGain_Acct();

	public org.compiere.model.I_C_ValidCombination getUnrealizedGain_A();

	public void setUnrealizedGain_A(org.compiere.model.I_C_ValidCombination UnrealizedGain_A);

    /** Column definition for UnrealizedGain_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_UnrealizedGain_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "UnrealizedGain_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name UnrealizedGain_Acct */
    public static final String COLUMNNAME_UnrealizedGain_Acct = "UnrealizedGain_Acct";

	/**
	 * Set Nicht realisierte Währungsverluste.
	 * Konto für nicht realisierte Währungsverluste
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUnrealizedLoss_Acct (int UnrealizedLoss_Acct);

	/**
	 * Get Nicht realisierte Währungsverluste.
	 * Konto für nicht realisierte Währungsverluste
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUnrealizedLoss_Acct();

	public org.compiere.model.I_C_ValidCombination getUnrealizedLoss_A();

	public void setUnrealizedLoss_A(org.compiere.model.I_C_ValidCombination UnrealizedLoss_A);

    /** Column definition for UnrealizedLoss_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_UnrealizedLoss_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "UnrealizedLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name UnrealizedLoss_Acct */
    public static final String COLUMNNAME_UnrealizedLoss_Acct = "UnrealizedLoss_Acct";

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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, Object>(I_C_AcctSchema_Default.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_AD_User>(I_C_AcctSchema_Default.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Verbindlichkeiten aus Lieferungen.
	 * Konto für Verbindlichkeiten aus Lieferungen (und Leistungen)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setV_Liability_Acct (int V_Liability_Acct);

	/**
	 * Get Verbindlichkeiten aus Lieferungen.
	 * Konto für Verbindlichkeiten aus Lieferungen (und Leistungen)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getV_Liability_Acct();

	public org.compiere.model.I_C_ValidCombination getV_Liability_A();

	public void setV_Liability_A(org.compiere.model.I_C_ValidCombination V_Liability_A);

    /** Column definition for V_Liability_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_V_Liability_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "V_Liability_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name V_Liability_Acct */
    public static final String COLUMNNAME_V_Liability_Acct = "V_Liability_Acct";

	/**
	 * Set Verbindlichkeiten aus Dienstleistungen.
	 * Konto für Verbindlichkeiten aus Dienstleistungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setV_Liability_Services_Acct (int V_Liability_Services_Acct);

	/**
	 * Get Verbindlichkeiten aus Dienstleistungen.
	 * Konto für Verbindlichkeiten aus Dienstleistungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getV_Liability_Services_Acct();

	public org.compiere.model.I_C_ValidCombination getV_Liability_Services_A();

	public void setV_Liability_Services_A(org.compiere.model.I_C_ValidCombination V_Liability_Services_A);

    /** Column definition for V_Liability_Services_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_V_Liability_Services_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "V_Liability_Services_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name V_Liability_Services_Acct */
    public static final String COLUMNNAME_V_Liability_Services_Acct = "V_Liability_Services_Acct";

	/**
	 * Set Geleistete Anzahlungen.
	 * Konto für Geleistete Anzahlungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setV_Prepayment_Acct (int V_Prepayment_Acct);

	/**
	 * Get Geleistete Anzahlungen.
	 * Konto für Geleistete Anzahlungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getV_Prepayment_Acct();

	public org.compiere.model.I_C_ValidCombination getV_Prepayment_A();

	public void setV_Prepayment_A(org.compiere.model.I_C_ValidCombination V_Prepayment_A);

    /** Column definition for V_Prepayment_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_V_Prepayment_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "V_Prepayment_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name V_Prepayment_Acct */
    public static final String COLUMNNAME_V_Prepayment_Acct = "V_Prepayment_Acct";

	/**
	 * Set Lager Bestand Korrektur.
	 * Konto für Differenzen im Lagerbestand (erfasst durch Inventur)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setW_Differences_Acct (int W_Differences_Acct);

	/**
	 * Get Lager Bestand Korrektur.
	 * Konto für Differenzen im Lagerbestand (erfasst durch Inventur)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getW_Differences_Acct();

	public org.compiere.model.I_C_ValidCombination getW_Differences_A();

	public void setW_Differences_A(org.compiere.model.I_C_ValidCombination W_Differences_A);

    /** Column definition for W_Differences_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_W_Differences_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "W_Differences_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name W_Differences_Acct */
    public static final String COLUMNNAME_W_Differences_Acct = "W_Differences_Acct";

	/**
	 * Set Lager Wert Korrektur.
	 * Konto für Korrekturen am Lager Wert (i.d.R. mit Konto Warenbestand identisch)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setW_InvActualAdjust_Acct (int W_InvActualAdjust_Acct);

	/**
	 * Get Lager Wert Korrektur.
	 * Konto für Korrekturen am Lager Wert (i.d.R. mit Konto Warenbestand identisch)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getW_InvActualAdjust_Acct();

	public org.compiere.model.I_C_ValidCombination getW_InvActualAdjust_A();

	public void setW_InvActualAdjust_A(org.compiere.model.I_C_ValidCombination W_InvActualAdjust_A);

    /** Column definition for W_InvActualAdjust_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_W_InvActualAdjust_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "W_InvActualAdjust_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name W_InvActualAdjust_Acct */
    public static final String COLUMNNAME_W_InvActualAdjust_Acct = "W_InvActualAdjust_Acct";

	/**
	 * Set (Not Used).
	 * Warehouse Inventory Asset Account - Currently not used
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setW_Inventory_Acct (int W_Inventory_Acct);

	/**
	 * Get (Not Used).
	 * Warehouse Inventory Asset Account - Currently not used
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getW_Inventory_Acct();

	public org.compiere.model.I_C_ValidCombination getW_Inventory_A();

	public void setW_Inventory_A(org.compiere.model.I_C_ValidCombination W_Inventory_A);

    /** Column definition for W_Inventory_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_W_Inventory_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "W_Inventory_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name W_Inventory_Acct */
    public static final String COLUMNNAME_W_Inventory_Acct = "W_Inventory_Acct";

	/**
	 * Set Lager Wert Korrektur Währungsdifferenz.
	 * Konto für Lager Wert Korrektur Währungsdifferenz
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setW_Revaluation_Acct (int W_Revaluation_Acct);

	/**
	 * Get Lager Wert Korrektur Währungsdifferenz.
	 * Konto für Lager Wert Korrektur Währungsdifferenz
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getW_Revaluation_Acct();

	public org.compiere.model.I_C_ValidCombination getW_Revaluation_A();

	public void setW_Revaluation_A(org.compiere.model.I_C_ValidCombination W_Revaluation_A);

    /** Column definition for W_Revaluation_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_W_Revaluation_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "W_Revaluation_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name W_Revaluation_Acct */
    public static final String COLUMNNAME_W_Revaluation_Acct = "W_Revaluation_Acct";

	/**
	 * Set Einbehalt.
	 * Account for Withholdings
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWithholding_Acct (int Withholding_Acct);

	/**
	 * Get Einbehalt.
	 * Account for Withholdings
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWithholding_Acct();

	public org.compiere.model.I_C_ValidCombination getWithholding_A();

	public void setWithholding_A(org.compiere.model.I_C_ValidCombination Withholding_A);

    /** Column definition for Withholding_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_Withholding_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "Withholding_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name Withholding_Acct */
    public static final String COLUMNNAME_Withholding_Acct = "Withholding_Acct";

	/**
	 * Set Forderungsverluste.
	 * Konto für Forderungsverluste
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWriteOff_Acct (int WriteOff_Acct);

	/**
	 * Get Forderungsverluste.
	 * Konto für Forderungsverluste
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWriteOff_Acct();

	public org.compiere.model.I_C_ValidCombination getWriteOff_A();

	public void setWriteOff_A(org.compiere.model.I_C_ValidCombination WriteOff_A);

    /** Column definition for WriteOff_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_WriteOff_Acct = new org.adempiere.model.ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination>(I_C_AcctSchema_Default.class, "WriteOff_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name WriteOff_Acct */
    public static final String COLUMNNAME_WriteOff_Acct = "WriteOff_Acct";
}
