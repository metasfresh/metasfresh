package de.metas.banking.model;


/** Generated Interface for C_BankStatementLine_Ref
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BankStatementLine_Ref 
{

    /** TableName=C_BankStatementLine_Ref */
    public static final String Table_Name = "C_BankStatementLine_Ref";

    /** AD_Table_ID=53065 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Client.
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bankauszug.
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatement_ID();

    /** Column name C_BankStatement_ID */
    public static final String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Bank statement line.
	 * Line on a statement from this Bank
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/**
	 * Get Bank statement line.
	 * Line on a statement from this Bank
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatementLine_ID();

    /** Column name C_BankStatementLine_ID */
    public static final String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/**
	 * Set Bankstatementline Reference.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatementLine_Ref_ID (int C_BankStatementLine_Ref_ID);

	/**
	 * Get Bankstatementline Reference.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatementLine_Ref_ID();

    /** Column definition for C_BankStatementLine_Ref_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object> COLUMN_C_BankStatementLine_Ref_ID = new org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object>(I_C_BankStatementLine_Ref.class, "C_BankStatementLine_Ref_ID", null);
    /** Column name C_BankStatementLine_Ref_ID */
    public static final String COLUMNNAME_C_BankStatementLine_Ref_ID = "C_BankStatementLine_Ref_ID";

	/**
	 * Set Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, org.compiere.model.I_C_Invoice>(I_C_BankStatementLine_Ref.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Zahlung.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Zahlung.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Payment_ID();

    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object>(I_C_BankStatementLine_Ref.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object>(I_C_BankStatementLine_Ref.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Line No.
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Line No.
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object>(I_C_BankStatementLine_Ref.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object>(I_C_BankStatementLine_Ref.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Referenznummer.
	 * Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReferenceNo (java.lang.String ReferenceNo);

	/**
	 * Get Referenznummer.
	 * Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReferenceNo();

    /** Column definition for ReferenceNo */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object> COLUMN_ReferenceNo = new org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object>(I_C_BankStatementLine_Ref.class, "ReferenceNo", null);
    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/**
	 * Set Bewegungs-Betrag.
	 * Betrag einer Transaktion
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTrxAmt (java.math.BigDecimal TrxAmt);

	/**
	 * Get Bewegungs-Betrag.
	 * Betrag einer Transaktion
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTrxAmt();

    /** Column definition for TrxAmt */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object> COLUMN_TrxAmt = new org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object>(I_C_BankStatementLine_Ref.class, "TrxAmt", null);
    /** Column name TrxAmt */
    public static final String COLUMNNAME_TrxAmt = "TrxAmt";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BankStatementLine_Ref, Object>(I_C_BankStatementLine_Ref.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
