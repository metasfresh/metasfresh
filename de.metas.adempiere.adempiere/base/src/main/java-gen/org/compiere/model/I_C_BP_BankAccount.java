package org.compiere.model;


/** Generated Interface for C_BP_BankAccount
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BP_BankAccount 
{

    /** TableName=C_BP_BankAccount */
    String Table_Name = "C_BP_BankAccount";

    /** AD_Table_ID=298 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Ort.
	 * City or the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_City (java.lang.String A_City);

	/**
	 * Get Ort.
	 * City or the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getA_City();

    /** Column definition for A_City */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_City = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "A_City", null);
    /** Column name A_City */
    String COLUMNNAME_A_City = "A_City";

	/**
	 * Set Land.
	 * Country
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Country (java.lang.String A_Country);

	/**
	 * Get Land.
	 * Country
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getA_Country();

    /** Column definition for A_Country */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_Country = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "A_Country", null);
    /** Column name A_Country */
    String COLUMNNAME_A_Country = "A_Country";

	/**
	 * Set EMail.
	 * Email Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_EMail (java.lang.String A_EMail);

	/**
	 * Get EMail.
	 * Email Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getA_EMail();

    /** Column definition for A_EMail */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_EMail = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "A_EMail", null);
    /** Column name A_EMail */
    String COLUMNNAME_A_EMail = "A_EMail";

	/**
	 * Set Führerschein-Nr..
	 * Payment Identification - Driver License
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Ident_DL (java.lang.String A_Ident_DL);

	/**
	 * Get Führerschein-Nr..
	 * Payment Identification - Driver License
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getA_Ident_DL();

    /** Column definition for A_Ident_DL */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_Ident_DL = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "A_Ident_DL", null);
    /** Column name A_Ident_DL */
    String COLUMNNAME_A_Ident_DL = "A_Ident_DL";

	/**
	 * Set Ausweis-Nr..
	 * Payment Identification - Social Security No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Ident_SSN (java.lang.String A_Ident_SSN);

	/**
	 * Get Ausweis-Nr..
	 * Payment Identification - Social Security No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getA_Ident_SSN();

    /** Column definition for A_Ident_SSN */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_Ident_SSN = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "A_Ident_SSN", null);
    /** Column name A_Ident_SSN */
    String COLUMNNAME_A_Ident_SSN = "A_Ident_SSN";

	/**
	 * Set Name.
	 * Name on Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setA_Name (java.lang.String A_Name);

	/**
	 * Get Name.
	 * Name on Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getA_Name();

    /** Column definition for A_Name */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_Name = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "A_Name", null);
    /** Column name A_Name */
    String COLUMNNAME_A_Name = "A_Name";

	/**
	 * Set Bundesstaat / -land.
	 * State of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_State (java.lang.String A_State);

	/**
	 * Get Bundesstaat / -land.
	 * State of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getA_State();

    /** Column definition for A_State */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_State = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "A_State", null);
    /** Column name A_State */
    String COLUMNNAME_A_State = "A_State";

	/**
	 * Set Straße.
	 * Street address of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Street (java.lang.String A_Street);

	/**
	 * Get Straße.
	 * Street address of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getA_Street();

    /** Column definition for A_Street */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_Street = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "A_Street", null);
    /** Column name A_Street */
    String COLUMNNAME_A_Street = "A_Street";

	/**
	 * Set Postleitzahl.
	 * Zip Code of the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Zip (java.lang.String A_Zip);

	/**
	 * Get Postleitzahl.
	 * Zip Code of the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getA_Zip();

    /** Column definition for A_Zip */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_Zip = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "A_Zip", null);
    /** Column name A_Zip */
    String COLUMNNAME_A_Zip = "A_Zip";

	/**
	 * Set Konto-Nr..
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountNo (java.lang.String AccountNo);

	/**
	 * Get Konto-Nr..
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getAccountNo();

    /** Column definition for AccountNo */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_AccountNo = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "AccountNo", null);
    /** Column name AccountNo */
    String COLUMNNAME_AccountNo = "AccountNo";

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	org.compiere.model.I_AD_Org getAD_Org();

	void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	org.compiere.model.I_AD_User getAD_User();

	void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Kontenart.
	 * Bank Account Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBankAccountType (java.lang.String BankAccountType);

	/**
	 * Get Kontenart.
	 * Bank Account Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getBankAccountType();

    /** Column definition for BankAccountType */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_BankAccountType = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "BankAccountType", null);
    /** Column name BankAccountType */
    String COLUMNNAME_BankAccountType = "BankAccountType";

	/**
	 * Set Kontonutzung.
	 * Business Partner Bank Account usage
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPBankAcctUse (java.lang.String BPBankAcctUse);

	/**
	 * Get Kontonutzung.
	 * Business Partner Bank Account usage
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getBPBankAcctUse();

    /** Column definition for BPBankAcctUse */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_BPBankAcctUse = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "BPBankAcctUse", null);
    /** Column name BPBankAcctUse */
    String COLUMNNAME_BPBankAcctUse = "BPBankAcctUse";

	/**
	 * Set Bank.
	 * Bank
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Bank_ID (int C_Bank_ID);

	/**
	 * Get Bank.
	 * Bank
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Bank_ID();

	org.compiere.model.I_C_Bank getC_Bank();

	void setC_Bank(org.compiere.model.I_C_Bank C_Bank);

    /** Column definition for C_Bank_ID */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, org.compiere.model.I_C_Bank> COLUMN_C_Bank_ID = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "C_Bank_ID", org.compiere.model.I_C_Bank.class);
    /** Column name C_Bank_ID */
    String COLUMNNAME_C_Bank_ID = "C_Bank_ID";

	/**
	 * Set Bankverbindung.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_ID();

    /** Column definition for C_BP_BankAccount_ID */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "C_BP_BankAccount_ID", null);
    /** Column name C_BP_BankAccount_ID */
    String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	org.compiere.model.I_C_BPartner getC_BPartner();

	void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

    /** Column definition for C_Currency_ID */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

    /** Column definition for Created */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "Created", null);
    /** Column name Created */
    String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

    /** Column definition for CreatedBy */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Exp. Monat.
	 * Expiry Month
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardExpMM (int CreditCardExpMM);

	/**
	 * Get Exp. Monat.
	 * Expiry Month
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreditCardExpMM();

    /** Column definition for CreditCardExpMM */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_CreditCardExpMM = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "CreditCardExpMM", null);
    /** Column name CreditCardExpMM */
    String COLUMNNAME_CreditCardExpMM = "CreditCardExpMM";

	/**
	 * Set Exp. Jahr.
	 * Expiry Year
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardExpYY (int CreditCardExpYY);

	/**
	 * Get Exp. Jahr.
	 * Expiry Year
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreditCardExpYY();

    /** Column definition for CreditCardExpYY */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_CreditCardExpYY = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "CreditCardExpYY", null);
    /** Column name CreditCardExpYY */
    String COLUMNNAME_CreditCardExpYY = "CreditCardExpYY";

	/**
	 * Set Kreditkarten-Nummer.
	 * Kreditkarten-Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardNumber (java.lang.String CreditCardNumber);

	/**
	 * Get Kreditkarten-Nummer.
	 * Kreditkarten-Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getCreditCardNumber();

    /** Column definition for CreditCardNumber */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_CreditCardNumber = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "CreditCardNumber", null);
    /** Column name CreditCardNumber */
    String COLUMNNAME_CreditCardNumber = "CreditCardNumber";

	/**
	 * Set Kreditkarte.
	 * Credit Card (Visa, MC, AmEx)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardType (java.lang.String CreditCardType);

	/**
	 * Get Kreditkarte.
	 * Credit Card (Visa, MC, AmEx)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getCreditCardType();

    /** Column definition for CreditCardType */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_CreditCardType = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "CreditCardType", null);
    /** Column name CreditCardType */
    String COLUMNNAME_CreditCardType = "CreditCardType";

	/**
	 * Set Verifizierungs-Code.
	 * Credit Card Verification code on credit card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardVV (java.lang.String CreditCardVV);

	/**
	 * Get Verifizierungs-Code.
	 * Credit Card Verification code on credit card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getCreditCardVV();

    /** Column definition for CreditCardVV */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_CreditCardVV = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "CreditCardVV", null);
    /** Column name CreditCardVV */
    String COLUMNNAME_CreditCardVV = "CreditCardVV";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getDescription();

    /** Column definition for Description */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "Description", null);
    /** Column name Description */
    String COLUMNNAME_Description = "Description";

	/**
	 * Set IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIBAN (java.lang.String IBAN);

	/**
	 * Get IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getIBAN();

    /** Column definition for IBAN */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_IBAN = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "IBAN", null);
    /** Column name IBAN */
    String COLUMNNAME_IBAN = "IBAN";

	/**
	 * Set ACH.
	 * Automatic Clearing House
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsACH (boolean IsACH);

	/**
	 * Get ACH.
	 * Automatic Clearing House
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isACH();

    /** Column definition for IsACH */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_IsACH = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "IsACH", null);
    /** Column name IsACH */
    String COLUMNNAME_IsACH = "IsACH";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

    /** Column definition for IsActive */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "IsActive", null);
    /** Column name IsActive */
    String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Addresse verifiziert.
	 * This address has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_AvsAddr (java.lang.String R_AvsAddr);

	/**
	 * Get Addresse verifiziert.
	 * This address has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getR_AvsAddr();

    /** Column definition for R_AvsAddr */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_R_AvsAddr = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "R_AvsAddr", null);
    /** Column name R_AvsAddr */
    String COLUMNNAME_R_AvsAddr = "R_AvsAddr";

	/**
	 * Set Postleitzahl verifiziert.
	 * The Zip Code has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_AvsZip (java.lang.String R_AvsZip);

	/**
	 * Get Postleitzahl verifiziert.
	 * The Zip Code has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getR_AvsZip();

    /** Column definition for R_AvsZip */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_R_AvsZip = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "R_AvsZip", null);
    /** Column name R_AvsZip */
    String COLUMNNAME_R_AvsZip = "R_AvsZip";

	/**
	 * Set BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRoutingNo (java.lang.String RoutingNo);

	/**
	 * Get BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getRoutingNo();

    /** Column definition for RoutingNo */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_RoutingNo = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "RoutingNo", null);
    /** Column name RoutingNo */
    String COLUMNNAME_RoutingNo = "RoutingNo";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "Updated", null);
    /** Column name Updated */
    String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

    /** Column definition for UpdatedBy */
    org.adempiere.model.ModelColumn<I_C_BP_BankAccount, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<>(I_C_BP_BankAccount.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
