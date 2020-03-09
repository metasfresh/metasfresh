package org.compiere.model;


/** Generated Interface for C_BankStatementLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BankStatementLine 
{

    /** TableName=C_BankStatementLine */
    public static final String Table_Name = "C_BankStatementLine";

    /** AD_Table_ID=393 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bankauszug.
	 * Bank Statement of account
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatement_ID();

	public org.compiere.model.I_C_BankStatement getC_BankStatement();

	public void setC_BankStatement(org.compiere.model.I_C_BankStatement C_BankStatement);

    /** Column definition for C_BankStatement_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, org.compiere.model.I_C_BankStatement> COLUMN_C_BankStatement_ID = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, org.compiere.model.I_C_BankStatement>(I_C_BankStatementLine.class, "C_BankStatement_ID", org.compiere.model.I_C_BankStatement.class);
    /** Column name C_BankStatement_ID */
    public static final String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Auszugs-Position.
	 * Line on a statement from this Bank
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/**
	 * Get Auszugs-Position.
	 * Line on a statement from this Bank
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatementLine_ID();

    /** Column definition for C_BankStatementLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_C_BankStatementLine_ID = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "C_BankStatementLine_ID", null);
    /** Column name C_BankStatementLine_ID */
    public static final String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/**
	 * Set Cashbook/Bank account To.
	 * Cashbook/Bank account To
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccountTo_ID (int C_BP_BankAccountTo_ID);

	/**
	 * Get Cashbook/Bank account To.
	 * Cashbook/Bank account To
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccountTo_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccountTo();

	public void setC_BP_BankAccountTo(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccountTo);

    /** Column definition for C_BP_BankAccountTo_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccountTo_ID = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, org.compiere.model.I_C_BP_BankAccount>(I_C_BankStatementLine.class, "C_BP_BankAccountTo_ID", org.compiere.model.I_C_BP_BankAccount.class);
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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Charge_ID();

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, org.compiere.model.I_C_Invoice>(I_C_BankStatementLine.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Auftrag.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, org.compiere.model.I_C_Order>(I_C_BankStatementLine.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

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

    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setChargeAmt (java.math.BigDecimal ChargeAmt);

	/**
	 * Get Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getChargeAmt();

    /** Column definition for ChargeAmt */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_ChargeAmt = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "ChargeAmt", null);
    /** Column name ChargeAmt */
    public static final String COLUMNNAME_ChargeAmt = "ChargeAmt";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_CreatePayment = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "CreatePayment", null);
    /** Column name CreatePayment */
    public static final String COLUMNNAME_CreatePayment = "CreatePayment";

	/**
	 * Set Wechselkurs.
	 * Wechselkurs für Währung
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCurrencyRate (java.math.BigDecimal CurrencyRate);

	/**
	 * Get Wechselkurs.
	 * Wechselkurs für Währung
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCurrencyRate();

    /** Column definition for CurrencyRate */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_CurrencyRate = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "CurrencyRate", null);
    /** Column name CurrencyRate */
    public static final String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "DateAcct", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Skonto.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscountAmt (java.math.BigDecimal DiscountAmt);

	/**
	 * Get Skonto.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDiscountAmt();

    /** Column definition for DiscountAmt */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_DiscountAmt = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "DiscountAmt", null);
    /** Column name DiscountAmt */
    public static final String COLUMNNAME_DiscountAmt = "DiscountAmt";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftAmt = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "EftAmt", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftCheckNo = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "EftCheckNo", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftCurrency = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "EftCurrency", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftMemo = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "EftMemo", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftPayee = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "EftPayee", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftPayeeAccount = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "EftPayeeAccount", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftReference = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "EftReference", null);
    /** Column name EftReference */
    public static final String COLUMNNAME_EftReference = "EftReference";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftStatementLineDate = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "EftStatementLineDate", null);
    /** Column name EftStatementLineDate */
    public static final String COLUMNNAME_EftStatementLineDate = "EftStatementLineDate";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftTrxID = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "EftTrxID", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftTrxType = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "EftTrxType", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftValutaDate = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "EftValutaDate", null);
    /** Column name EftValutaDate */
    public static final String COLUMNNAME_EftValutaDate = "EftValutaDate";

	/**
	 * Set Imported Bill Partner IBAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setImportedBillPartnerIBAN (java.lang.String ImportedBillPartnerIBAN);

	/**
	 * Get Imported Bill Partner IBAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getImportedBillPartnerIBAN();

    /** Column definition for ImportedBillPartnerIBAN */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_ImportedBillPartnerIBAN = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "ImportedBillPartnerIBAN", null);
    /** Column name ImportedBillPartnerIBAN */
    public static final String COLUMNNAME_ImportedBillPartnerIBAN = "ImportedBillPartnerIBAN";

	/**
	 * Set Importierter Rechnungspartner Name.
	 * Name of the Bill Partner as appears in the import file
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setImportedBillPartnerName (java.lang.String ImportedBillPartnerName);

	/**
	 * Get Importierter Rechnungspartner Name.
	 * Name of the Bill Partner as appears in the import file
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getImportedBillPartnerName();

    /** Column definition for ImportedBillPartnerName */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_ImportedBillPartnerName = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "ImportedBillPartnerName", null);
    /** Column name ImportedBillPartnerName */
    public static final String COLUMNNAME_ImportedBillPartnerName = "ImportedBillPartnerName";

	/**
	 * Set Interest Amount.
	 * Interest Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInterestAmt (java.math.BigDecimal InterestAmt);

	/**
	 * Get Interest Amount.
	 * Interest Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getInterestAmt();

    /** Column definition for InterestAmt */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_InterestAmt = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "InterestAmt", null);
    /** Column name InterestAmt */
    public static final String COLUMNNAME_InterestAmt = "InterestAmt";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsManual = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "IsManual", null);
    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set More than one payment.
	 * The bankstatement line references more than one payment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsMultiplePayment (boolean IsMultiplePayment);

	/**
	 * Get More than one payment.
	 * The bankstatement line references more than one payment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isMultiplePayment();

    /** Column definition for IsMultiplePayment */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsMultiplePayment = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "IsMultiplePayment", null);
    /** Column name IsMultiplePayment */
    public static final String COLUMNNAME_IsMultiplePayment = "IsMultiplePayment";

	/**
	 * Set Multiple Payments or Invoices.
	 * Bankstatement line with multiple payments/invoices
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsMultiplePaymentOrInvoice (boolean IsMultiplePaymentOrInvoice);

	/**
	 * Get Multiple Payments or Invoices.
	 * Bankstatement line with multiple payments/invoices
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isMultiplePaymentOrInvoice();

    /** Column definition for IsMultiplePaymentOrInvoice */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsMultiplePaymentOrInvoice = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "IsMultiplePaymentOrInvoice", null);
    /** Column name IsMultiplePaymentOrInvoice */
    public static final String COLUMNNAME_IsMultiplePaymentOrInvoice = "IsMultiplePaymentOrInvoice";

	/**
	 * Set Over/Under Payment.
	 * Over-Payment (unallocated) or Under-Payment (partial payment)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsOverUnderPayment (boolean IsOverUnderPayment);

	/**
	 * Get Over/Under Payment.
	 * Over-Payment (unallocated) or Under-Payment (partial payment)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverUnderPayment();

    /** Column definition for IsOverUnderPayment */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsOverUnderPayment = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "IsOverUnderPayment", null);
    /** Column name IsOverUnderPayment */
    public static final String COLUMNNAME_IsOverUnderPayment = "IsOverUnderPayment";

	/**
	 * Set Umkehrung.
	 * This is a reversing transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReversal (boolean IsReversal);

	/**
	 * Get Umkehrung.
	 * This is a reversing transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReversal();

    /** Column definition for IsReversal */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsReversal = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "IsReversal", null);
    /** Column name IsReversal */
    public static final String COLUMNNAME_IsReversal = "IsReversal";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Linked Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLink_BankStatementLine_ID (int Link_BankStatementLine_ID);

	/**
	 * Get Linked Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLink_BankStatementLine_ID();

	public org.compiere.model.I_C_BankStatementLine getLink_BankStatementLine();

	public void setLink_BankStatementLine(org.compiere.model.I_C_BankStatementLine Link_BankStatementLine);

    /** Column definition for Link_BankStatementLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, org.compiere.model.I_C_BankStatementLine> COLUMN_Link_BankStatementLine_ID = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, org.compiere.model.I_C_BankStatementLine>(I_C_BankStatementLine.class, "Link_BankStatementLine_ID", org.compiere.model.I_C_BankStatementLine.class);
    /** Column name Link_BankStatementLine_ID */
    public static final String COLUMNNAME_Link_BankStatementLine_ID = "Link_BankStatementLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_MatchStatement = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "MatchStatement", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_Memo = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "Memo", null);
    /** Column name Memo */
    public static final String COLUMNNAME_Memo = "Memo";

	/**
	 * Set Over/Under Payment.
	 * Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOverUnderAmt (java.math.BigDecimal OverUnderAmt);

	/**
	 * Get Over/Under Payment.
	 * Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getOverUnderAmt();

    /** Column definition for OverUnderAmt */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_OverUnderAmt = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "OverUnderAmt", null);
    /** Column name OverUnderAmt */
    public static final String COLUMNNAME_OverUnderAmt = "OverUnderAmt";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_ReferenceNo = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "ReferenceNo", null);
    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/**
	 * Set Valuta Datum.
	 * Valuta Datum
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStatementLineDate (java.sql.Timestamp StatementLineDate);

	/**
	 * Get Valuta Datum.
	 * Valuta Datum
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getStatementLineDate();

    /** Column definition for StatementLineDate */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_StatementLineDate = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "StatementLineDate", null);
    /** Column name StatementLineDate */
    public static final String COLUMNNAME_StatementLineDate = "StatementLineDate";

	/**
	 * Set Statement amount.
	 * Statement Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStmtAmt (java.math.BigDecimal StmtAmt);

	/**
	 * Get Statement amount.
	 * Statement Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getStmtAmt();

    /** Column definition for StmtAmt */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_StmtAmt = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "StmtAmt", null);
    /** Column name StmtAmt */
    public static final String COLUMNNAME_StmtAmt = "StmtAmt";

	/**
	 * Set Bewegungs-Betrag.
	 * Amount of a transaction
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTrxAmt (java.math.BigDecimal TrxAmt);

	/**
	 * Get Bewegungs-Betrag.
	 * Amount of a transaction
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTrxAmt();

    /** Column definition for TrxAmt */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_TrxAmt = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "TrxAmt", null);
    /** Column name TrxAmt */
    public static final String COLUMNNAME_TrxAmt = "TrxAmt";

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
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "Updated", null);
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

	/**
	 * Set Effective date.
	 * Date when money is available
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValutaDate (java.sql.Timestamp ValutaDate);

	/**
	 * Get Effective date.
	 * Date when money is available
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValutaDate();

    /** Column definition for ValutaDate */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_ValutaDate = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "ValutaDate", null);
    /** Column name ValutaDate */
    public static final String COLUMNNAME_ValutaDate = "ValutaDate";

	/**
	 * Set Write-off Amount.
	 * Amount to write-off
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWriteOffAmt (java.math.BigDecimal WriteOffAmt);

	/**
	 * Get Write-off Amount.
	 * Amount to write-off
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getWriteOffAmt();

    /** Column definition for WriteOffAmt */
    public static final org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object> COLUMN_WriteOffAmt = new org.adempiere.model.ModelColumn<I_C_BankStatementLine, Object>(I_C_BankStatementLine.class, "WriteOffAmt", null);
    /** Column name WriteOffAmt */
    public static final String COLUMNNAME_WriteOffAmt = "WriteOffAmt";
}
