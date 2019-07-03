package org.compiere.model;


/** Generated Interface for I_BankStatement
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_BankStatement 
{

    /** TableName=I_BankStatement */
    public static final String Table_Name = "I_BankStatement";

    /** AD_Table_ID=600 */
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_AD_Client>(I_I_BankStatement.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_AD_Org>(I_I_BankStatement.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bank Account No.
	 * Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBankAccountNo (java.lang.String BankAccountNo);

	/**
	 * Get Bank Account No.
	 * Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBankAccountNo();

    /** Column definition for BankAccountNo */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_BankAccountNo = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "BankAccountNo", null);
    /** Column name BankAccountNo */
    public static final String COLUMNNAME_BankAccountNo = "BankAccountNo";

	/**
	 * Set Name Rechnungspartner.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_BPartner_Name (java.lang.String Bill_BPartner_Name);

	/**
	 * Get Name Rechnungspartner.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBill_BPartner_Name();

    /** Column definition for Bill_BPartner_Name */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_Bill_BPartner_Name = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "Bill_BPartner_Name", null);
    /** Column name Bill_BPartner_Name */
    public static final String COLUMNNAME_Bill_BPartner_Name = "Bill_BPartner_Name";

	/**
	 * Set Geschäftspartner-Schlüssel.
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerValue (java.lang.String BPartnerValue);

	/**
	 * Get Geschäftspartner-Schlüssel.
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerValue();

    /** Column definition for BPartnerValue */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_BPartnerValue = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "BPartnerValue", null);
    /** Column name BPartnerValue */
    public static final String COLUMNNAME_BPartnerValue = "BPartnerValue";

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

	public org.compiere.model.I_C_BankStatement getC_BankStatement();

	public void setC_BankStatement(org.compiere.model.I_C_BankStatement C_BankStatement);

    /** Column definition for C_BankStatement_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_BankStatement> COLUMN_C_BankStatement_ID = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_BankStatement>(I_I_BankStatement.class, "C_BankStatement_ID", org.compiere.model.I_C_BankStatement.class);
    /** Column name C_BankStatement_ID */
    public static final String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Auszugs-Position.
	 * Line on a statement from this Bank
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/**
	 * Get Auszugs-Position.
	 * Line on a statement from this Bank
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatementLine_ID();

	public org.compiere.model.I_C_BankStatementLine getC_BankStatementLine();

	public void setC_BankStatementLine(org.compiere.model.I_C_BankStatementLine C_BankStatementLine);

    /** Column definition for C_BankStatementLine_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_BankStatementLine> COLUMN_C_BankStatementLine_ID = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_BankStatementLine>(I_I_BankStatement.class, "C_BankStatementLine_ID", org.compiere.model.I_C_BankStatementLine.class);
    /** Column name C_BankStatementLine_ID */
    public static final String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/**
	 * Set Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_BP_BankAccount>(I_I_BankStatement.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Cashbook/Bank account To.
	 * Cashbook/Bank account To
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccountTo_ID (int C_BP_BankAccountTo_ID);

	/**
	 * Get Cashbook/Bank account To.
	 * Cashbook/Bank account To
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccountTo_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccountTo();

	public void setC_BP_BankAccountTo(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccountTo);

    /** Column definition for C_BP_BankAccountTo_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccountTo_ID = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_BP_BankAccount>(I_I_BankStatement.class, "C_BP_BankAccountTo_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccountTo_ID */
    public static final String COLUMNNAME_C_BP_BankAccountTo_ID = "C_BP_BankAccountTo_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_BPartner>(I_I_BankStatement.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
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
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_Charge>(I_I_BankStatement.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_Currency>(I_I_BankStatement.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
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
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_Invoice>(I_I_BankStatement.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Zahlung.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Zahlung.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Payment_ID();

	public org.compiere.model.I_C_Payment getC_Payment();

	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment);

    /** Column definition for C_Payment_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_Payment> COLUMN_C_Payment_ID = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_C_Payment>(I_I_BankStatement.class, "C_Payment_ID", org.compiere.model.I_C_Payment.class);
    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setChargeAmt (java.math.BigDecimal ChargeAmt);

	/**
	 * Get Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getChargeAmt();

    /** Column definition for ChargeAmt */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_ChargeAmt = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "ChargeAmt", null);
    /** Column name ChargeAmt */
    public static final String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/**
	 * Set Gebühren-Bezeichnung.
	 * Name of the Charge
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setChargeName (java.lang.String ChargeName);

	/**
	 * Get Gebühren-Bezeichnung.
	 * Name of the Charge
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getChargeName();

    /** Column definition for ChargeName */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_ChargeName = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "ChargeName", null);
    /** Column name ChargeName */
    public static final String COLUMNNAME_ChargeName = "ChargeName";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_AD_User>(I_I_BankStatement.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Zahlung erstellen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreatePayment (java.lang.String CreatePayment);

	/**
	 * Get Zahlung erstellen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreatePayment();

    /** Column definition for CreatePayment */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_CreatePayment = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "CreatePayment", null);
    /** Column name CreatePayment */
    public static final String COLUMNNAME_CreatePayment = "CreatePayment";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "DateAcct", null);
    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

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
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set ELV-Betrag.
	 * Electronic Funds Transfer Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftAmt (java.math.BigDecimal EftAmt);

	/**
	 * Get ELV-Betrag.
	 * Electronic Funds Transfer Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getEftAmt();

    /** Column definition for EftAmt */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftAmt = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftAmt", null);
    /** Column name EftAmt */
    public static final String COLUMNNAME_EftAmt = "EftAmt";

	/**
	 * Set ELV Scheck-Nr..
	 * Electronic Funds Transfer Check No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftCheckNo (java.lang.String EftCheckNo);

	/**
	 * Get ELV Scheck-Nr..
	 * Electronic Funds Transfer Check No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEftCheckNo();

    /** Column definition for EftCheckNo */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftCheckNo = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftCheckNo", null);
    /** Column name EftCheckNo */
    public static final String COLUMNNAME_EftCheckNo = "EftCheckNo";

	/**
	 * Set ELV-Währung.
	 * Electronic Funds Transfer Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftCurrency (java.lang.String EftCurrency);

	/**
	 * Get ELV-Währung.
	 * Electronic Funds Transfer Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEftCurrency();

    /** Column definition for EftCurrency */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftCurrency = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftCurrency", null);
    /** Column name EftCurrency */
    public static final String COLUMNNAME_EftCurrency = "EftCurrency";

	/**
	 * Set ELV Memo.
	 * Electronic Funds Transfer Memo
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftMemo (java.lang.String EftMemo);

	/**
	 * Get ELV Memo.
	 * Electronic Funds Transfer Memo
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEftMemo();

    /** Column definition for EftMemo */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftMemo = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftMemo", null);
    /** Column name EftMemo */
    public static final String COLUMNNAME_EftMemo = "EftMemo";

	/**
	 * Set ELV-Zahlungsempfänger.
	 * Electronic Funds Transfer Payee information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftPayee (java.lang.String EftPayee);

	/**
	 * Get ELV-Zahlungsempfänger.
	 * Electronic Funds Transfer Payee information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEftPayee();

    /** Column definition for EftPayee */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftPayee = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftPayee", null);
    /** Column name EftPayee */
    public static final String COLUMNNAME_EftPayee = "EftPayee";

	/**
	 * Set Konto ELV-Zahlungsempfänger.
	 * Electronic Funds Transfer Payyee Account Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftPayeeAccount (java.lang.String EftPayeeAccount);

	/**
	 * Get Konto ELV-Zahlungsempfänger.
	 * Electronic Funds Transfer Payyee Account Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEftPayeeAccount();

    /** Column definition for EftPayeeAccount */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftPayeeAccount = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftPayeeAccount", null);
    /** Column name EftPayeeAccount */
    public static final String COLUMNNAME_EftPayeeAccount = "EftPayeeAccount";

	/**
	 * Set EFT Reference.
	 * Electronic Funds Transfer Reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftReference (java.lang.String EftReference);

	/**
	 * Get EFT Reference.
	 * Electronic Funds Transfer Reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEftReference();

    /** Column definition for EftReference */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftReference = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftReference", null);
    /** Column name EftReference */
    public static final String COLUMNNAME_EftReference = "EftReference";

	/**
	 * Set Auszugsdatum ELV.
	 * Electronic Funds Transfer Statement Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftStatementDate (java.sql.Timestamp EftStatementDate);

	/**
	 * Get Auszugsdatum ELV.
	 * Electronic Funds Transfer Statement Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEftStatementDate();

    /** Column definition for EftStatementDate */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftStatementDate = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftStatementDate", null);
    /** Column name EftStatementDate */
    public static final String COLUMNNAME_EftStatementDate = "EftStatementDate";

	/**
	 * Set Datum ELV-Position.
	 * Electronic Funds Transfer Statement Line Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftStatementLineDate (java.sql.Timestamp EftStatementLineDate);

	/**
	 * Get Datum ELV-Position.
	 * Electronic Funds Transfer Statement Line Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEftStatementLineDate();

    /** Column definition for EftStatementLineDate */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftStatementLineDate = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftStatementLineDate", null);
    /** Column name EftStatementLineDate */
    public static final String COLUMNNAME_EftStatementLineDate = "EftStatementLineDate";

	/**
	 * Set ELV Auszugs-Referenz.
	 * Electronic Funds Transfer Statement Reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftStatementReference (java.lang.String EftStatementReference);

	/**
	 * Get ELV Auszugs-Referenz.
	 * Electronic Funds Transfer Statement Reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEftStatementReference();

    /** Column definition for EftStatementReference */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftStatementReference = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftStatementReference", null);
    /** Column name EftStatementReference */
    public static final String COLUMNNAME_EftStatementReference = "EftStatementReference";

	/**
	 * Set ELV-TransaktionsID.
	 * Electronic Funds Transfer Transaction ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftTrxID (java.lang.String EftTrxID);

	/**
	 * Get ELV-TransaktionsID.
	 * Electronic Funds Transfer Transaction ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEftTrxID();

    /** Column definition for EftTrxID */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftTrxID = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftTrxID", null);
    /** Column name EftTrxID */
    public static final String COLUMNNAME_EftTrxID = "EftTrxID";

	/**
	 * Set ELV-Transaktionsart.
	 * Electronic Funds Transfer Transaction Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftTrxType (java.lang.String EftTrxType);

	/**
	 * Get ELV-Transaktionsart.
	 * Electronic Funds Transfer Transaction Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEftTrxType();

    /** Column definition for EftTrxType */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftTrxType = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftTrxType", null);
    /** Column name EftTrxType */
    public static final String COLUMNNAME_EftTrxType = "EftTrxType";

	/**
	 * Set ELV Wertstellungs-Datum.
	 * Electronic Funds Transfer Valuta (effective) Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEftValutaDate (java.sql.Timestamp EftValutaDate);

	/**
	 * Get ELV Wertstellungs-Datum.
	 * Electronic Funds Transfer Valuta (effective) Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEftValutaDate();

    /** Column definition for EftValutaDate */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_EftValutaDate = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "EftValutaDate", null);
    /** Column name EftValutaDate */
    public static final String COLUMNNAME_EftValutaDate = "EftValutaDate";

	/**
	 * Set Import - Bankauszug.
	 * Import of the Bank Statement
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_BankStatement_ID (int I_BankStatement_ID);

	/**
	 * Get Import - Bankauszug.
	 * Import of the Bank Statement
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_BankStatement_ID();

    /** Column definition for I_BankStatement_ID */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_I_BankStatement_ID = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "I_BankStatement_ID", null);
    /** Column name I_BankStatement_ID */
    public static final String COLUMNNAME_I_BankStatement_ID = "I_BankStatement_ID";

	/**
	 * Set Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Importiert.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Importiert.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIBAN (java.lang.String IBAN);

	/**
	 * Get IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIBAN();

    /** Column definition for IBAN */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_IBAN = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "IBAN", null);
    /** Column name IBAN */
    public static final String COLUMNNAME_IBAN = "IBAN";

	/**
	 * Set IBAN_To.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIBAN_To (java.lang.String IBAN_To);

	/**
	 * Get IBAN_To.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIBAN_To();

    /** Column definition for IBAN_To */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_IBAN_To = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "IBAN_To", null);
    /** Column name IBAN_To */
    public static final String COLUMNNAME_IBAN_To = "IBAN_To";

	/**
	 * Set Interest Amount.
	 * Interest Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInterestAmt (java.math.BigDecimal InterestAmt);

	/**
	 * Get Interest Amount.
	 * Interest Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getInterestAmt();

    /** Column definition for InterestAmt */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_InterestAmt = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "InterestAmt", null);
    /** Column name InterestAmt */
    public static final String COLUMNNAME_InterestAmt = "InterestAmt";

	/**
	 * Set Invoice Document No.
	 * Document Number of the Invoice
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoiceDocumentNo (java.lang.String InvoiceDocumentNo);

	/**
	 * Get Invoice Document No.
	 * Document Number of the Invoice
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceDocumentNo();

    /** Column definition for InvoiceDocumentNo */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_InvoiceDocumentNo = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "InvoiceDocumentNo", null);
    /** Column name InvoiceDocumentNo */
    public static final String COLUMNNAME_InvoiceDocumentNo = "InvoiceDocumentNo";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setISO_Code (java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getISO_Code();

    /** Column definition for ISO_Code */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_ISO_Code = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "ISO_Code", null);
    /** Column name ISO_Code */
    public static final String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Umkehrung.
	 * This is a reversing transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsReversal (boolean IsReversal);

	/**
	 * Get Umkehrung.
	 * This is a reversing transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isReversal();

    /** Column definition for IsReversal */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_IsReversal = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "IsReversal", null);
    /** Column name IsReversal */
    public static final String COLUMNNAME_IsReversal = "IsReversal";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Positions-Beschreibung.
	 * Description of the Line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineDescription (java.lang.String LineDescription);

	/**
	 * Get Positions-Beschreibung.
	 * Description of the Line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLineDescription();

    /** Column definition for LineDescription */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_LineDescription = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "LineDescription", null);
    /** Column name LineDescription */
    public static final String COLUMNNAME_LineDescription = "LineDescription";

	/**
	 * Set Match Statement.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMatchStatement (java.lang.String MatchStatement);

	/**
	 * Get Match Statement.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMatchStatement();

    /** Column definition for MatchStatement */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_MatchStatement = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "MatchStatement", null);
    /** Column name MatchStatement */
    public static final String COLUMNNAME_MatchStatement = "MatchStatement";

	/**
	 * Set Memo.
	 * Memo Text
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMemo (java.lang.String Memo);

	/**
	 * Get Memo.
	 * Memo Text
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMemo();

    /** Column definition for Memo */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_Memo = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "Memo", null);
    /** Column name Memo */
    public static final String COLUMNNAME_Memo = "Memo";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Payment Document No.
	 * Document number of the Payment
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPaymentDocumentNo (java.lang.String PaymentDocumentNo);

	/**
	 * Get Payment Document No.
	 * Document number of the Payment
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentDocumentNo();

    /** Column definition for PaymentDocumentNo */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_PaymentDocumentNo = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "PaymentDocumentNo", null);
    /** Column name PaymentDocumentNo */
    public static final String COLUMNNAME_PaymentDocumentNo = "PaymentDocumentNo";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

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
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Referenznummer.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReferenceNo (java.lang.String ReferenceNo);

	/**
	 * Get Referenznummer.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReferenceNo();

    /** Column definition for ReferenceNo */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_ReferenceNo = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "ReferenceNo", null);
    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/**
	 * Set BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRoutingNo (java.lang.String RoutingNo);

	/**
	 * Get BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRoutingNo();

    /** Column definition for RoutingNo */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_RoutingNo = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "RoutingNo", null);
    /** Column name RoutingNo */
    public static final String COLUMNNAME_RoutingNo = "RoutingNo";

	/**
	 * Set Auszugsdatum.
	 * Date of the statement
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStatementDate (java.sql.Timestamp StatementDate);

	/**
	 * Get Auszugsdatum.
	 * Date of the statement
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getStatementDate();

    /** Column definition for StatementDate */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_StatementDate = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "StatementDate", null);
    /** Column name StatementDate */
    public static final String COLUMNNAME_StatementDate = "StatementDate";

	/**
	 * Set Valuta Datum.
	 * Valuta Datum
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStatementLineDate (java.sql.Timestamp StatementLineDate);

	/**
	 * Get Valuta Datum.
	 * Valuta Datum
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getStatementLineDate();

    /** Column definition for StatementLineDate */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_StatementLineDate = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "StatementLineDate", null);
    /** Column name StatementLineDate */
    public static final String COLUMNNAME_StatementLineDate = "StatementLineDate";

	/**
	 * Set Statement amount.
	 * Statement Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStmtAmt (java.math.BigDecimal StmtAmt);

	/**
	 * Get Statement amount.
	 * Statement Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getStmtAmt();

    /** Column definition for StmtAmt */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_StmtAmt = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "StmtAmt", null);
    /** Column name StmtAmt */
    public static final String COLUMNNAME_StmtAmt = "StmtAmt";

	/**
	 * Set Bewegungs-Betrag.
	 * Amount of a transaction
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTrxAmt (java.math.BigDecimal TrxAmt);

	/**
	 * Get Bewegungs-Betrag.
	 * Amount of a transaction
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTrxAmt();

    /** Column definition for TrxAmt */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_TrxAmt = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "TrxAmt", null);
    /** Column name TrxAmt */
    public static final String COLUMNNAME_TrxAmt = "TrxAmt";

	/**
	 * Set Transaction Type.
	 * Type of credit card transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTrxType (java.lang.String TrxType);

	/**
	 * Get Transaction Type.
	 * Type of credit card transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTrxType();

    /** Column definition for TrxType */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_TrxType = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "TrxType", null);
    /** Column name TrxType */
    public static final String COLUMNNAME_TrxType = "TrxType";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_BankStatement, org.compiere.model.I_AD_User>(I_I_BankStatement.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Effective date.
	 * Date when money is available
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValutaDate (java.sql.Timestamp ValutaDate);

	/**
	 * Get Effective date.
	 * Date when money is available
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValutaDate();

    /** Column definition for ValutaDate */
    public static final org.adempiere.model.ModelColumn<I_I_BankStatement, Object> COLUMN_ValutaDate = new org.adempiere.model.ModelColumn<I_I_BankStatement, Object>(I_I_BankStatement.class, "ValutaDate", null);
    /** Column name ValutaDate */
    public static final String COLUMNNAME_ValutaDate = "ValutaDate";
}
