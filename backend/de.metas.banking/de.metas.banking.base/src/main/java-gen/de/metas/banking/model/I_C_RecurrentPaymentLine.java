package de.metas.banking.model;


/** Generated Interface for C_RecurrentPaymentLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RecurrentPaymentLine 
{

    /** TableName=C_RecurrentPaymentLine */
    public static final String Table_Name = "C_RecurrentPaymentLine";

    /** AD_Table_ID=540094 */
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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_AD_Client>(I_C_RecurrentPaymentLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_AD_Org>(I_C_RecurrentPaymentLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_AD_User>(I_C_RecurrentPaymentLine.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Bankverbindung.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_C_BP_BankAccount>(I_C_RecurrentPaymentLine.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set eigenes Bankkonto.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_Own_ID (int C_BP_BankAccount_Own_ID);

	/**
	 * Get eigenes Bankkonto.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_Own_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount_Own();

	public void setC_BP_BankAccount_Own(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount_Own);

    /** Column definition for C_BP_BankAccount_Own_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_Own_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_C_BP_BankAccount>(I_C_RecurrentPaymentLine.class, "C_BP_BankAccount_Own_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_Own_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_Own_ID = "C_BP_BankAccount_Own_ID";

	/**
	 * Set Charge.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Charge.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge();

	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge);

    /** Column definition for C_Charge_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_C_Charge>(I_C_RecurrentPaymentLine.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_C_Currency>(I_C_RecurrentPaymentLine.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm();

	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm);

    /** Column definition for C_PaymentTerm_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_C_PaymentTerm>(I_C_RecurrentPaymentLine.class, "C_PaymentTerm_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Set Recurrent Payment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RecurrentPayment_ID (int C_RecurrentPayment_ID);

	/**
	 * Get Recurrent Payment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RecurrentPayment_ID();

	public de.metas.banking.model.I_C_RecurrentPayment getC_RecurrentPayment();

	public void setC_RecurrentPayment(de.metas.banking.model.I_C_RecurrentPayment C_RecurrentPayment);

    /** Column definition for C_RecurrentPayment_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, de.metas.banking.model.I_C_RecurrentPayment> COLUMN_C_RecurrentPayment_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, de.metas.banking.model.I_C_RecurrentPayment>(I_C_RecurrentPaymentLine.class, "C_RecurrentPayment_ID", de.metas.banking.model.I_C_RecurrentPayment.class);
    /** Column name C_RecurrentPayment_ID */
    public static final String COLUMNNAME_C_RecurrentPayment_ID = "C_RecurrentPayment_ID";

	/**
	 * Set Recurrent Payment Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RecurrentPaymentLine_ID (int C_RecurrentPaymentLine_ID);

	/**
	 * Get Recurrent Payment Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RecurrentPaymentLine_ID();

    /** Column definition for C_RecurrentPaymentLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object> COLUMN_C_RecurrentPaymentLine_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object>(I_C_RecurrentPaymentLine.class, "C_RecurrentPaymentLine_ID", null);
    /** Column name C_RecurrentPaymentLine_ID */
    public static final String COLUMNNAME_C_RecurrentPaymentLine_ID = "C_RecurrentPaymentLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object>(I_C_RecurrentPaymentLine.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_AD_User>(I_C_RecurrentPaymentLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Date From.
	 * Starting date for a range
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateFrom (java.sql.Timestamp DateFrom);

	/**
	 * Get Date From.
	 * Starting date for a range
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateFrom();

    /** Column definition for DateFrom */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object> COLUMN_DateFrom = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object>(I_C_RecurrentPaymentLine.class, "DateFrom", null);
    /** Column name DateFrom */
    public static final String COLUMNNAME_DateFrom = "DateFrom";

	/**
	 * Set Date To.
	 * End date of a date range
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateTo (java.sql.Timestamp DateTo);

	/**
	 * Get Date To.
	 * End date of a date range
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateTo();

    /** Column definition for DateTo */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object> COLUMN_DateTo = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object>(I_C_RecurrentPaymentLine.class, "DateTo", null);
    /** Column name DateTo */
    public static final String COLUMNNAME_DateTo = "DateTo";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object>(I_C_RecurrentPaymentLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Frequency.
	 * Frequency of events
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFrequency (int Frequency);

	/**
	 * Get Frequency.
	 * Frequency of events
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getFrequency();

    /** Column definition for Frequency */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object> COLUMN_Frequency = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object>(I_C_RecurrentPaymentLine.class, "Frequency", null);
    /** Column name Frequency */
    public static final String COLUMNNAME_Frequency = "Frequency";

	/**
	 * Set Frequency Type.
	 * Frequency of event
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFrequencyType (java.lang.String FrequencyType);

	/**
	 * Get Frequency Type.
	 * Frequency of event
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFrequencyType();

    /** Column definition for FrequencyType */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object> COLUMN_FrequencyType = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object>(I_C_RecurrentPaymentLine.class, "FrequencyType", null);
    /** Column name FrequencyType */
    public static final String COLUMNNAME_FrequencyType = "FrequencyType";

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
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object>(I_C_RecurrentPaymentLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Next Payment Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNextPaymentDate (java.sql.Timestamp NextPaymentDate);

	/**
	 * Get Next Payment Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getNextPaymentDate();

    /** Column definition for NextPaymentDate */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object> COLUMN_NextPaymentDate = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object>(I_C_RecurrentPaymentLine.class, "NextPaymentDate", null);
    /** Column name NextPaymentDate */
    public static final String COLUMNNAME_NextPaymentDate = "NextPaymentDate";

	/**
	 * Set Payment amount.
	 * Amount being paid
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayAmt (java.math.BigDecimal PayAmt);

	/**
	 * Get Payment amount.
	 * Amount being paid
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPayAmt();

    /** Column definition for PayAmt */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object> COLUMN_PayAmt = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object>(I_C_RecurrentPaymentLine.class, "PayAmt", null);
    /** Column name PayAmt */
    public static final String COLUMNNAME_PayAmt = "PayAmt";

	/**
	 * Set Aussendienst.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Aussendienst.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSalesRep_ID();

    /** Column definition for SalesRep_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_AD_User>(I_C_RecurrentPaymentLine.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, Object>(I_C_RecurrentPaymentLine.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RecurrentPaymentLine, org.compiere.model.I_AD_User>(I_C_RecurrentPaymentLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
