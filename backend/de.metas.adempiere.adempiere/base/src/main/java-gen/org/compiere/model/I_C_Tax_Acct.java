package org.compiere.model;


/** Generated Interface for C_Tax_Acct
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Tax_Acct 
{

    /** TableName=C_Tax_Acct */
    public static final String Table_Name = "C_Tax_Acct";

    /** AD_Table_ID=399 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
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
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_AcctSchema>(I_C_Tax_Acct.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set C_Tax_Acct.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Tax_Acct_ID (int C_Tax_Acct_ID);

	/**
	 * Get C_Tax_Acct.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Tax_Acct_ID();

    /** Column definition for C_Tax_Acct_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, Object> COLUMN_C_Tax_Acct_ID = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, Object>(I_C_Tax_Acct.class, "C_Tax_Acct_ID", null);
    /** Column name C_Tax_Acct_ID */
    public static final String COLUMNNAME_C_Tax_Acct_ID = "C_Tax_Acct_ID";

	/**
	 * Set Steuer.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Steuer.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Tax_ID();

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, Object>(I_C_Tax_Acct.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, Object>(I_C_Tax_Acct.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_Credit_Acct = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination>(I_C_Tax_Acct.class, "T_Credit_Acct", org.compiere.model.I_C_ValidCombination.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_Due_Acct = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination>(I_C_Tax_Acct.class, "T_Due_Acct", org.compiere.model.I_C_ValidCombination.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_Expense_Acct = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination>(I_C_Tax_Acct.class, "T_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_Liability_Acct = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination>(I_C_Tax_Acct.class, "T_Liability_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name T_Liability_Acct */
    public static final String COLUMNNAME_T_Liability_Acct = "T_Liability_Acct";

	/**
	 * Set Steuerkorrektur gewährte Skonti.
	 * Steuerabhängiges Konto zur Verbuchung gewährter Skonti
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setT_PayDiscount_Exp_Acct (int T_PayDiscount_Exp_Acct);

	/**
	 * Get Steuerkorrektur gewährte Skonti.
	 * Steuerabhängiges Konto zur Verbuchung gewährter Skonti
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getT_PayDiscount_Exp_Acct();

	public org.compiere.model.I_C_ValidCombination getT_PayDiscount_Exp_A();

	public void setT_PayDiscount_Exp_A(org.compiere.model.I_C_ValidCombination T_PayDiscount_Exp_A);

    /** Column definition for T_PayDiscount_Exp_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_PayDiscount_Exp_Acct = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination>(I_C_Tax_Acct.class, "T_PayDiscount_Exp_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name T_PayDiscount_Exp_Acct */
    public static final String COLUMNNAME_T_PayDiscount_Exp_Acct = "T_PayDiscount_Exp_Acct";

	/**
	 * Set Steuerkorrektur erhaltene Skonti.
	 * Steuerabhängiges Konto zur Verbuchung erhaltener Skonti
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setT_PayDiscount_Rev_Acct (int T_PayDiscount_Rev_Acct);

	/**
	 * Get Steuerkorrektur erhaltene Skonti.
	 * Steuerabhängiges Konto zur Verbuchung erhaltener Skonti
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getT_PayDiscount_Rev_Acct();

	public org.compiere.model.I_C_ValidCombination getT_PayDiscount_Rev_A();

	public void setT_PayDiscount_Rev_A(org.compiere.model.I_C_ValidCombination T_PayDiscount_Rev_A);

    /** Column definition for T_PayDiscount_Rev_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_PayDiscount_Rev_Acct = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination>(I_C_Tax_Acct.class, "T_PayDiscount_Rev_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name T_PayDiscount_Rev_Acct */
    public static final String COLUMNNAME_T_PayDiscount_Rev_Acct = "T_PayDiscount_Rev_Acct";

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
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_Receivables_Acct = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination>(I_C_Tax_Acct.class, "T_Receivables_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name T_Receivables_Acct */
    public static final String COLUMNNAME_T_Receivables_Acct = "T_Receivables_Acct";

	/**
	 * Set Erlös Konto.
	 * Steuerabhängiges Konto zur Verbuchung Erlöse
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setT_Revenue_Acct (int T_Revenue_Acct);

	/**
	 * Get Erlös Konto.
	 * Steuerabhängiges Konto zur Verbuchung Erlöse
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getT_Revenue_Acct();

	public org.compiere.model.I_C_ValidCombination getT_Revenue_A();

	public void setT_Revenue_A(org.compiere.model.I_C_ValidCombination T_Revenue_A);

    /** Column definition for T_Revenue_Acct */
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_Revenue_Acct = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination>(I_C_Tax_Acct.class, "T_Revenue_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name T_Revenue_Acct */
    public static final String COLUMNNAME_T_Revenue_Acct = "T_Revenue_Acct";

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
    public static final org.adempiere.model.ModelColumn<I_C_Tax_Acct, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Tax_Acct, Object>(I_C_Tax_Acct.class, "Updated", null);
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
