package org.compiere.model;

<<<<<<< HEAD

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
=======
import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_PaySelectionLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_PaySelectionLine 
{

	String Table_Name = "C_PaySelectionLine";

//	/** AD_Table_ID=427 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisatorische Einheit des Mandanten
=======
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisatorische Einheit des Mandanten
=======
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bankauszug.
=======
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bank Statement.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bankauszug.
=======
	void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bank Statement.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_BankStatement_ID();

    /** Column name C_BankStatement_ID */
    public static final String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Auszugs-Position.
	 * Position auf einem Bankauszug zu dieser Bank
=======
	int getC_BankStatement_ID();

	String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Statement Line.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/**
	 * Get Auszugs-Position.
	 * Position auf einem Bankauszug zu dieser Bank
=======
	void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/**
	 * Get Statement Line.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_BankStatementLine_ID();

    /** Column name C_BankStatementLine_ID */
    public static final String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/**
	 * Set Bankstatementline Reference.
=======
	int getC_BankStatementLine_ID();

	String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/**
	 * Set Bank Statement Line Reference.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_BankStatementLine_Ref_ID (int C_BankStatementLine_Ref_ID);

	/**
	 * Get Bankstatementline Reference.
=======
	void setC_BankStatementLine_Ref_ID (int C_BankStatementLine_Ref_ID);

	/**
	 * Get Bank Statement Line Reference.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_BankStatementLine_Ref_ID();

    /** Column definition for C_BankStatementLine_Ref_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_C_BankStatementLine_Ref_ID = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "C_BankStatementLine_Ref_ID", null);
    /** Column name C_BankStatementLine_Ref_ID */
    public static final String COLUMNNAME_C_BankStatementLine_Ref_ID = "C_BankStatementLine_Ref_ID";

	/**
	 * Set Bankverbindung.
=======
	int getC_BankStatementLine_Ref_ID();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_C_BankStatementLine_Ref_ID = new ModelColumn<>(I_C_PaySelectionLine.class, "C_BankStatementLine_Ref_ID", null);
	String COLUMNNAME_C_BankStatementLine_Ref_ID = "C_BankStatementLine_Ref_ID";

	/**
	 * Set Partner Bank Account.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
=======
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
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
=======
	int getC_BP_BankAccount_ID();

	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Business Partner.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
=======
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
=======
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
<<<<<<< HEAD
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
=======
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
<<<<<<< HEAD
	public int getC_Currency_ID();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Rechnung.
=======
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Invoice.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Rechnung.
=======
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
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
=======
	int getC_Invoice_ID();

	org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_PaySelectionLine.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Payment.
	 * Payment identifier
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Zahlung.
	 * Zahlung
=======
	void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Payment.
	 * Payment identifier
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_Payment_ID();

	public org.compiere.model.I_C_Payment getC_Payment();

	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment);

    /** Column definition for C_Payment_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_Payment> COLUMN_C_Payment_ID = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_Payment>(I_C_PaySelectionLine.class, "C_Payment_ID", org.compiere.model.I_C_Payment.class);
    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";
=======
	int getC_Payment_ID();

	String COLUMNNAME_C_Payment_ID = "C_Payment_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Zahlung Anweisen.
	 * Zahlung Anweisen
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_PaySelection_ID (int C_PaySelection_ID);
=======
	void setC_PaySelection_ID (int C_PaySelection_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Zahlung Anweisen.
	 * Zahlung Anweisen
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_PaySelection_ID();

	public org.compiere.model.I_C_PaySelection getC_PaySelection();

	public void setC_PaySelection(org.compiere.model.I_C_PaySelection C_PaySelection);

    /** Column definition for C_PaySelection_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_PaySelection> COLUMN_C_PaySelection_ID = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_PaySelection>(I_C_PaySelectionLine.class, "C_PaySelection_ID", org.compiere.model.I_C_PaySelection.class);
    /** Column name C_PaySelection_ID */
    public static final String COLUMNNAME_C_PaySelection_ID = "C_PaySelection_ID";
=======
	int getC_PaySelection_ID();

	org.compiere.model.I_C_PaySelection getC_PaySelection();

	void setC_PaySelection(org.compiere.model.I_C_PaySelection C_PaySelection);

	ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_PaySelection> COLUMN_C_PaySelection_ID = new ModelColumn<>(I_C_PaySelectionLine.class, "C_PaySelection_ID", org.compiere.model.I_C_PaySelection.class);
	String COLUMNNAME_C_PaySelection_ID = "C_PaySelection_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Zahlungsauswahl- Position.
	 * Payment Selection Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_PaySelectionLine_ID (int C_PaySelectionLine_ID);
=======
	void setC_PaySelectionLine_ID (int C_PaySelectionLine_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Zahlungsauswahl- Position.
	 * Payment Selection Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_PaySelectionLine_ID();

    /** Column definition for C_PaySelectionLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_C_PaySelectionLine_ID = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "C_PaySelectionLine_ID", null);
    /** Column name C_PaySelectionLine_ID */
    public static final String COLUMNNAME_C_PaySelectionLine_ID = "C_PaySelectionLine_ID";

	/**
	 * Get Erstellt.
=======
	int getC_PaySelectionLine_ID();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_C_PaySelectionLine_ID = new ModelColumn<>(I_C_PaySelectionLine.class, "C_PaySelectionLine_ID", null);
	String COLUMNNAME_C_PaySelectionLine_ID = "C_PaySelectionLine_ID";

	/**
	 * Get Created.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Created = new ModelColumn<>(I_C_PaySelectionLine.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
=======
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
=======
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";
=======
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Description = new ModelColumn<>(I_C_PaySelectionLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Differenz.
	 * Difference Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDifferenceAmt (java.math.BigDecimal DifferenceAmt);
=======
	void setDifferenceAmt (BigDecimal DifferenceAmt);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Differenz.
	 * Difference Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getDifferenceAmt();

    /** Column definition for DifferenceAmt */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_DifferenceAmt = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "DifferenceAmt", null);
    /** Column name DifferenceAmt */
    public static final String COLUMNNAME_DifferenceAmt = "DifferenceAmt";

	/**
	 * Set Skonto.
=======
	BigDecimal getDifferenceAmt();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_DifferenceAmt = new ModelColumn<>(I_C_PaySelectionLine.class, "DifferenceAmt", null);
	String COLUMNNAME_DifferenceAmt = "DifferenceAmt";

	/**
	 * Set Discount Amount.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDiscountAmt (java.math.BigDecimal DiscountAmt);

	/**
	 * Get Skonto.
=======
	void setDiscountAmt (BigDecimal DiscountAmt);

	/**
	 * Get Discount Amount.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getDiscountAmt();

    /** Column definition for DiscountAmt */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_DiscountAmt = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "DiscountAmt", null);
    /** Column name DiscountAmt */
    public static final String COLUMNNAME_DiscountAmt = "DiscountAmt";
=======
	BigDecimal getDiscountAmt();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_DiscountAmt = new ModelColumn<>(I_C_PaySelectionLine.class, "DiscountAmt", null);
	String COLUMNNAME_DiscountAmt = "DiscountAmt";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
	public void setHasOpenCreditMemos (boolean HasOpenCreditMemos);
=======
	void setHasOpenCreditMemos (boolean HasOpenCreditMemos);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
	public boolean isHasOpenCreditMemos();

    /** Column definition for HasOpenCreditMemos */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_HasOpenCreditMemos = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "HasOpenCreditMemos", null);
    /** Column name HasOpenCreditMemos */
    public static final String COLUMNNAME_HasOpenCreditMemos = "HasOpenCreditMemos";
=======
	boolean isHasOpenCreditMemos();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_HasOpenCreditMemos = new ModelColumn<>(I_C_PaySelectionLine.class, "HasOpenCreditMemos", null);
	String COLUMNNAME_HasOpenCreditMemos = "HasOpenCreditMemos";

	/**
	 * Set Has Open Credit Memo.
	 * Has Open Credit Memo Invoices
	 *
	 * <br>Type: Color
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setHasOpenCreditMemos_Color_ID (int HasOpenCreditMemos_Color_ID);

	/**
	 * Get Has Open Credit Memo.
	 * Has Open Credit Memo Invoices
	 *
	 * <br>Type: Color
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getHasOpenCreditMemos_Color_ID();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_HasOpenCreditMemos_Color_ID = new ModelColumn<>(I_C_PaySelectionLine.class, "HasOpenCreditMemos_Color_ID", null);
	String COLUMNNAME_HasOpenCreditMemos_Color_ID = "HasOpenCreditMemos_Color_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Has Open Outgoing payments.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
<<<<<<< HEAD
	public void setHasOpenOutgoingPayments (boolean HasOpenOutgoingPayments);
=======
	void setHasOpenOutgoingPayments (boolean HasOpenOutgoingPayments);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Has Open Outgoing payments.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
<<<<<<< HEAD
	public boolean isHasOpenOutgoingPayments();

    /** Column definition for HasOpenOutgoingPayments */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_HasOpenOutgoingPayments = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "HasOpenOutgoingPayments", null);
    /** Column name HasOpenOutgoingPayments */
    public static final String COLUMNNAME_HasOpenOutgoingPayments = "HasOpenOutgoingPayments";

	/**
	 * Set Aktiv.
=======
	boolean isHasOpenOutgoingPayments();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_HasOpenOutgoingPayments = new ModelColumn<>(I_C_PaySelectionLine.class, "HasOpenOutgoingPayments", null);
	String COLUMNNAME_HasOpenOutgoingPayments = "HasOpenOutgoingPayments";

	/**
	 * Set Active.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
=======
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";
=======
	boolean isActive();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_PaySelectionLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Manuell.
	 * This is a manual process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsManual (boolean IsManual);
=======
	void setIsManual (boolean IsManual);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Manuell.
	 * This is a manual process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isManual();

    /** Column definition for IsManual */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_IsManual = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "IsManual", null);
    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Verkaufs-Transaktion.
=======
	boolean isManual();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_IsManual = new ModelColumn<>(I_C_PaySelectionLine.class, "IsManual", null);
	String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Sales Transaction.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Verkaufs-Transaktion.
=======
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isSOTrx();

    /** Column definition for IsSOTrx */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "IsSOTrx", null);
    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
=======
	boolean isSOTrx();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_C_PaySelectionLine.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set SeqNo..
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
=======
	void setLine (int Line);

	/**
	 * Get SeqNo..
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Offener Betrag.
=======
	int getLine();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Line = new ModelColumn<>(I_C_PaySelectionLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Open Amount.
	 * Open item amount
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setOpenAmt (java.math.BigDecimal OpenAmt);

	/**
	 * Get Offener Betrag.
=======
	void setOpenAmt (BigDecimal OpenAmt);

	/**
	 * Get Open Amount.
	 * Open item amount
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
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
=======
	BigDecimal getOpenAmt();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_OpenAmt = new ModelColumn<>(I_C_PaySelectionLine.class, "OpenAmt", null);
	String COLUMNNAME_OpenAmt = "OpenAmt";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Zahlungsbetrag.
	 * Amount being paid
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setPayAmt (java.math.BigDecimal PayAmt);
=======
	void setPayAmt (BigDecimal PayAmt);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Zahlungsbetrag.
	 * Amount being paid
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getPayAmt();

    /** Column definition for PayAmt */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_PayAmt = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "PayAmt", null);
    /** Column name PayAmt */
    public static final String COLUMNNAME_PayAmt = "PayAmt";

	/**
	 * Set Zahlungsweise.
=======
	BigDecimal getPayAmt();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_PayAmt = new ModelColumn<>(I_C_PaySelectionLine.class, "PayAmt", null);
	String COLUMNNAME_PayAmt = "PayAmt";

	/**
	 * Set Payment Rule.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Zahlungsweise.
=======
	void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Payment Rule.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getPaymentRule();

    /** Column definition for PaymentRule */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_PaymentRule = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "PaymentRule", null);
    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set Referenz.
	 * Bezug für diesen Eintrag
=======
	java.lang.String getPaymentRule();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_PaymentRule = new ModelColumn<>(I_C_PaySelectionLine.class, "PaymentRule", null);
	String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set Reference.
	 * Reference for this record
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setReference (java.lang.String Reference);

	/**
	 * Get Referenz.
	 * Bezug für diesen Eintrag
=======
	void setReference (@Nullable java.lang.String Reference);

	/**
	 * Get Reference.
	 * Reference for this record
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getReference();

    /** Column definition for Reference */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Reference = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "Reference", null);
    /** Column name Reference */
    public static final String COLUMNNAME_Reference = "Reference";

	/**
	 * Get Aktualisiert.
=======
	@Nullable java.lang.String getReference();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Reference = new ModelColumn<>(I_C_PaySelectionLine.class, "Reference", null);
	String COLUMNNAME_Reference = "Reference";

	/**
	 * Get Updated.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_PaySelectionLine, Object>(I_C_PaySelectionLine.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_PaySelectionLine.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
=======
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
