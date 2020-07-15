package org.compiere.model;


/** Generated Interface for C_PaySelectionLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_PaySelectionLine 
{

    /** TableName=C_PaySelectionLine */
    public static final String Table_Name = "C_PaySelectionLine";

    /** AD_Table_ID=427 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bankauszug.
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bankauszug.
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatement_ID();

    /** Column name C_BankStatement_ID */
    public static final String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Auszugs-Position.
	 * Position auf einem Bankauszug zu dieser Bank
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/**
	 * Get Auszugs-Position.
	 * Position auf einem Bankauszug zu dieser Bank
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatementLine_ID();

    /** Column name C_BankStatementLine_ID */
    public static final String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/**
	 * Set Bankstatementline Reference.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatementLine_Ref_ID (int C_BankStatementLine_Ref_ID);

	/**
	 * Get Bankstatementline Reference.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatementLine_Ref_ID();

    /** Column definition for C_BankStatementLine_Ref_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_C_BankStatementLine_Ref_ID = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "C_BankStatementLine_Ref_ID", null);
    /** Column name C_BankStatementLine_Ref_ID */
    public static final String COLUMNNAME_C_BankStatementLine_Ref_ID = "C_BankStatementLine_Ref_ID";

	/**
	 * Set Bankverbindung.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_BP_BankAccount>(I_C_PaySelectionLine.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getC_Currency_ID();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_Invoice>(I_C_PaySelectionLine.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Zahlung.
	 * Zahlung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Zahlung.
	 * Zahlung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Payment_ID();

	public org.compiere.model.I_C_Payment getC_Payment();

	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment);

    /** Column definition for C_Payment_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_Payment> COLUMN_C_Payment_ID = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_Payment>(I_C_PaySelectionLine.class, "C_Payment_ID", org.compiere.model.I_C_Payment.class);
    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Zahlung Anweisen.
	 * Zahlung Anweisen
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PaySelection_ID (int C_PaySelection_ID);

	/**
	 * Get Zahlung Anweisen.
	 * Zahlung Anweisen
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PaySelection_ID();

	public org.compiere.model.I_C_PaySelection getC_PaySelection();

	public void setC_PaySelection(org.compiere.model.I_C_PaySelection C_PaySelection);

    /** Column definition for C_PaySelection_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_PaySelection> COLUMN_C_PaySelection_ID = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_PaySelection>(I_C_PaySelectionLine.class, "C_PaySelection_ID", org.compiere.model.I_C_PaySelection.class);
    /** Column name C_PaySelection_ID */
    public static final String COLUMNNAME_C_PaySelection_ID = "C_PaySelection_ID";

	/**
	 * Set Zahlungsauswahl- Position.
	 * Payment Selection Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PaySelectionLine_ID (int C_PaySelectionLine_ID);

	/**
	 * Get Zahlungsauswahl- Position.
	 * Payment Selection Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PaySelectionLine_ID();

    /** Column definition for C_PaySelectionLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_C_PaySelectionLine_ID = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "C_PaySelectionLine_ID", null);
    /** Column name C_PaySelectionLine_ID */
    public static final String COLUMNNAME_C_PaySelectionLine_ID = "C_PaySelectionLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "Created", null);
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

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Differenz.
	 * Difference Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDifferenceAmt (java.math.BigDecimal DifferenceAmt);

	/**
	 * Get Differenz.
	 * Difference Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDifferenceAmt();

    /** Column definition for DifferenceAmt */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_DifferenceAmt = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "DifferenceAmt", null);
    /** Column name DifferenceAmt */
    public static final String COLUMNNAME_DifferenceAmt = "DifferenceAmt";

	/**
	 * Set Skonto.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDiscountAmt (java.math.BigDecimal DiscountAmt);

	/**
	 * Get Skonto.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDiscountAmt();

    /** Column definition for DiscountAmt */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_DiscountAmt = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "DiscountAmt", null);
    /** Column name DiscountAmt */
    public static final String COLUMNNAME_DiscountAmt = "DiscountAmt";

	/**
	 * Set Has Open Credit Memo.
	 * Has Open Credit Memo Invoices
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setHasOpenCreditMemos (boolean HasOpenCreditMemos);

	/**
	 * Get Has Open Credit Memo.
	 * Has Open Credit Memo Invoices
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public boolean isHasOpenCreditMemos();

    /** Column definition for HasOpenCreditMemos */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_HasOpenCreditMemos = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "HasOpenCreditMemos", null);
    /** Column name HasOpenCreditMemos */
    public static final String COLUMNNAME_HasOpenCreditMemos = "HasOpenCreditMemos";

	/**
	 * Set Has Open Outgoing payments.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setHasOpenOutgoingPayments (boolean HasOpenOutgoingPayments);

	/**
	 * Get Has Open Outgoing payments.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public boolean isHasOpenOutgoingPayments();

    /** Column definition for HasOpenOutgoingPayments */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_HasOpenOutgoingPayments = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "HasOpenOutgoingPayments", null);
    /** Column name HasOpenOutgoingPayments */
    public static final String COLUMNNAME_HasOpenOutgoingPayments = "HasOpenOutgoingPayments";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Manuell.
	 * This is a manual process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManual (boolean IsManual);

	/**
	 * Get Manuell.
	 * This is a manual process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManual();

    /** Column definition for IsManual */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_IsManual = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "IsManual", null);
    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSOTrx();

    /** Column definition for IsSOTrx */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "IsSOTrx", null);
    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Offener Betrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOpenAmt (java.math.BigDecimal OpenAmt);

	/**
	 * Get Offener Betrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getOpenAmt();

    /** Column definition for OpenAmt */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_OpenAmt = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "OpenAmt", null);
    /** Column name OpenAmt */
    public static final String COLUMNNAME_OpenAmt = "OpenAmt";

	/**
	 * Set Offene Zahlungszuordnung.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOpenPaymentAllocationForm (java.lang.String OpenPaymentAllocationForm);

	/**
	 * Get Offene Zahlungszuordnung.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOpenPaymentAllocationForm();

    /** Column definition for OpenPaymentAllocationForm */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_OpenPaymentAllocationForm = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "OpenPaymentAllocationForm", null);
    /** Column name OpenPaymentAllocationForm */
    public static final String COLUMNNAME_OpenPaymentAllocationForm = "OpenPaymentAllocationForm";

	/**
	 * Set Zahlungsbetrag.
	 * Amount being paid
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayAmt (java.math.BigDecimal PayAmt);

	/**
	 * Get Zahlungsbetrag.
	 * Amount being paid
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPayAmt();

    /** Column definition for PayAmt */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_PayAmt = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "PayAmt", null);
    /** Column name PayAmt */
    public static final String COLUMNNAME_PayAmt = "PayAmt";

	/**
	 * Set Zahlungsweise.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Zahlungsweise.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentRule();

    /** Column definition for PaymentRule */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_PaymentRule = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "PaymentRule", null);
    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set Referenz.
	 * Bezug für diesen Eintrag
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReference (java.lang.String Reference);

	/**
	 * Get Referenz.
	 * Bezug für diesen Eintrag
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReference();

    /** Column definition for Reference */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Reference = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "Reference", null);
    /** Column name Reference */
    public static final String COLUMNNAME_Reference = "Reference";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
