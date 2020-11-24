package org.compiere.model;

/**
 * Generated Interface for C_Payment
 *
 * @author metasfresh (generated)
 */
@SuppressWarnings("javadoc")
public interface I_C_Payment
{

	/**
	 * TableName=C_Payment
	 */
	public static final String Table_Name = "C_Payment";

	/** AD_Table_ID=335 */
	//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

	/**
	 * Set Ort.
	 * City or the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_City(java.lang.String A_City);

	/**
	 * Get Ort.
	 * City or the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_City();

	/**
	 * Column definition for A_City
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_A_City = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "A_City", null);
	/**
	 * Column name A_City
	 */
	public static final String COLUMNNAME_A_City = "A_City";

	/**
	 * Set Land.
	 * Country
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Country(java.lang.String A_Country);

	/**
	 * Get Land.
	 * Country
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_Country();

	/**
	 * Column definition for A_Country
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_A_Country = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "A_Country", null);
	/**
	 * Column name A_Country
	 */
	public static final String COLUMNNAME_A_Country = "A_Country";

	/**
	 * Set EMail.
	 * Email Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_EMail(java.lang.String A_EMail);

	/**
	 * Get EMail.
	 * Email Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_EMail();

	/**
	 * Column definition for A_EMail
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_A_EMail = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "A_EMail", null);
	/**
	 * Column name A_EMail
	 */
	public static final String COLUMNNAME_A_EMail = "A_EMail";

	/**
	 * Set Führerschein-Nr..
	 * Payment Identification - Driver License
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Ident_DL(java.lang.String A_Ident_DL);

	/**
	 * Get Führerschein-Nr..
	 * Payment Identification - Driver License
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_Ident_DL();

	/**
	 * Column definition for A_Ident_DL
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_A_Ident_DL = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "A_Ident_DL", null);
	/**
	 * Column name A_Ident_DL
	 */
	public static final String COLUMNNAME_A_Ident_DL = "A_Ident_DL";

	/**
	 * Set Ausweis-Nr..
	 * Payment Identification - Social Security No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Ident_SSN(java.lang.String A_Ident_SSN);

	/**
	 * Get Ausweis-Nr..
	 * Payment Identification - Social Security No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_Ident_SSN();

	/**
	 * Column definition for A_Ident_SSN
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_A_Ident_SSN = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "A_Ident_SSN", null);
	/**
	 * Column name A_Ident_SSN
	 */
	public static final String COLUMNNAME_A_Ident_SSN = "A_Ident_SSN";

	/**
	 * Set Name.
	 * Name auf Kreditkarte oder des Kontoeigners
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Name(java.lang.String A_Name);

	/**
	 * Get Name.
	 * Name auf Kreditkarte oder des Kontoeigners
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_Name();

	/**
	 * Column definition for A_Name
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_A_Name = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "A_Name", null);
	/**
	 * Column name A_Name
	 */
	public static final String COLUMNNAME_A_Name = "A_Name";

	/**
	 * Set Bundesstaat / -land.
	 * State of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_State(java.lang.String A_State);

	/**
	 * Get Bundesstaat / -land.
	 * State of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_State();

	/**
	 * Column definition for A_State
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_A_State = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "A_State", null);
	/**
	 * Column name A_State
	 */
	public static final String COLUMNNAME_A_State = "A_State";

	/**
	 * Set Straße.
	 * Street address of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Street(java.lang.String A_Street);

	/**
	 * Get Straße.
	 * Street address of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_Street();

	/**
	 * Column definition for A_Street
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_A_Street = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "A_Street", null);
	/**
	 * Column name A_Street
	 */
	public static final String COLUMNNAME_A_Street = "A_Street";

	/**
	 * Set Postleitzahl.
	 * Zip Code of the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Zip(java.lang.String A_Zip);

	/**
	 * Get Postleitzahl.
	 * Zip Code of the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_Zip();

	/**
	 * Column definition for A_Zip
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_A_Zip = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "A_Zip", null);
	/**
	 * Column name A_Zip
	 */
	public static final String COLUMNNAME_A_Zip = "A_Zip";

	/**
	 * Set Konto-Nr..
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccountNo(java.lang.String AccountNo);

	/**
	 * Get Konto-Nr..
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccountNo();

	/**
	 * Column definition for AccountNo
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_AccountNo = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "AccountNo", null);
	/**
	 * Column name AccountNo
	 */
	public static final String COLUMNNAME_AccountNo = "AccountNo";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	/**
	 * Column name AD_Client_ID
	 */
	public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID(int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	/**
	 * Column name AD_Org_ID
	 */
	public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgTrx_ID(int AD_OrgTrx_ID);

	/**
	 * Get Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgTrx_ID();

	/**
	 * Column name AD_OrgTrx_ID
	 */
	public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Activity_ID(int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Activity_ID();

	/**
	 * Column name C_Activity_ID
	 */
	public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatement_ID(int C_BankStatement_ID);

	/**
	 * Get Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatement_ID();

	/**
	 * Column name C_BankStatement_ID
	 */
	public static final String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatementLine_ID(int C_BankStatementLine_ID);

	/**
	 * Get Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatementLine_ID();

	/**
	 * Column name C_BankStatementLine_ID
	 */
	public static final String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/**
	 * Set Bank Statement Line Reference.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatementLine_Ref_ID(int C_BankStatementLine_Ref_ID);

	/**
	 * Get Bank Statement Line Reference.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatementLine_Ref_ID();

	/**
	 * Column definition for C_BankStatementLine_Ref_ID
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_C_BankStatementLine_Ref_ID = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "C_BankStatementLine_Ref_ID", null);
	/**
	 * Column name C_BankStatementLine_Ref_ID
	 */
	public static final String COLUMNNAME_C_BankStatementLine_Ref_ID = "C_BankStatementLine_Ref_ID";

	/**
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID(int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	/**
	 * Column name C_BP_BankAccount_ID
	 */
	public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID(int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	/**
	 * Column name C_BPartner_ID
	 */
	public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Campaign_ID(int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Campaign_ID();

	public org.compiere.model.I_C_Campaign getC_Campaign();

	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign);

	/**
	 * Column definition for C_Campaign_ID
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_Campaign>(I_C_Payment.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	/**
	 * Column name C_Campaign_ID
	 */
	public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Kassenbuch.
	 * Cash Book for recording petty cash transactions
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_CashBook_ID(int C_CashBook_ID);

	/**
	 * Get Kassenbuch.
	 * Cash Book for recording petty cash transactions
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_CashBook_ID();

	public org.compiere.model.I_C_CashBook getC_CashBook();

	public void setC_CashBook(org.compiere.model.I_C_CashBook C_CashBook);

	/**
	 * Column definition for C_CashBook_ID
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_CashBook> COLUMN_C_CashBook_ID = new org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_CashBook>(I_C_Payment.class, "C_CashBook_ID", org.compiere.model.I_C_CashBook.class);
	/**
	 * Column name C_CashBook_ID
	 */
	public static final String COLUMNNAME_C_CashBook_ID = "C_CashBook_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID(int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Charge_ID();

	/**
	 * Column name C_Charge_ID
	 */
	public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ConversionType_ID(int C_ConversionType_ID);

	/**
	 * Get Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ConversionType_ID();

	/**
	 * Column name C_ConversionType_ID
	 */
	public static final String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID(int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	/**
	 * Column name C_Currency_ID
	 */
	public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID(int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	/**
	 * Column name C_DocType_ID
	 */
	public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID(int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

	/**
	 * Column definition for C_Invoice_ID
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_Invoice>(I_C_Payment.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	/**
	 * Column name C_Invoice_ID
	 */
	public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID(int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

	/**
	 * Column definition for C_Order_ID
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_Order>(I_C_Payment.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	/**
	 * Column name C_Order_ID
	 */
	public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Payment.
	 * Payment identifier
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Payment_ID(int C_Payment_ID);

	/**
	 * Get Payment.
	 * Payment identifier
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Payment_ID();

	/**
	 * Column definition for C_Payment_ID
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_C_Payment_ID = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "C_Payment_ID", null);
	/**
	 * Column name C_Payment_ID
	 */
	public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Zahlungslauf.
	 * Payment batch for EFT
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentBatch_ID(int C_PaymentBatch_ID);

	/**
	 * Get Zahlungslauf.
	 * Payment batch for EFT
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentBatch_ID();

	public org.compiere.model.I_C_PaymentBatch getC_PaymentBatch();

	public void setC_PaymentBatch(org.compiere.model.I_C_PaymentBatch C_PaymentBatch);

	/**
	 * Column definition for C_PaymentBatch_ID
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_PaymentBatch> COLUMN_C_PaymentBatch_ID = new org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_PaymentBatch>(I_C_Payment.class, "C_PaymentBatch_ID", org.compiere.model.I_C_PaymentBatch.class);
	/**
	 * Column name C_PaymentBatch_ID
	 */
	public static final String COLUMNNAME_C_PaymentBatch_ID = "C_PaymentBatch_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Project_ID(int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Project_ID();

	/**
	 * Column name C_Project_ID
	 */
	public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setChargeAmt(java.math.BigDecimal ChargeAmt);

	/**
	 * Get Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getChargeAmt();

	/**
	 * Column definition for ChargeAmt
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_ChargeAmt = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "ChargeAmt", null);
	/**
	 * Column name ChargeAmt
	 */
	public static final String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/**
	 * Set Check No.
	 * Check Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCheckNo(java.lang.String CheckNo);

	/**
	 * Get Check No.
	 * Check Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCheckNo();

	/**
	 * Column definition for CheckNo
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_CheckNo = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "CheckNo", null);
	/**
	 * Column name CheckNo
	 */
	public static final String COLUMNNAME_CheckNo = "CheckNo";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

	/**
	 * Column definition for Created
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "Created", null);
	/**
	 * Column name Created
	 */
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

	/**
	 * Column name CreatedBy
	 */
	public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Exp. Monat.
	 * Expiry Month
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditCardExpMM(int CreditCardExpMM);

	/**
	 * Get Exp. Monat.
	 * Expiry Month
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreditCardExpMM();

	/**
	 * Column definition for CreditCardExpMM
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_CreditCardExpMM = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "CreditCardExpMM", null);
	/**
	 * Column name CreditCardExpMM
	 */
	public static final String COLUMNNAME_CreditCardExpMM = "CreditCardExpMM";

	/**
	 * Set Exp. Jahr.
	 * Expiry Year
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditCardExpYY(int CreditCardExpYY);

	/**
	 * Get Exp. Jahr.
	 * Expiry Year
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreditCardExpYY();

	/**
	 * Column definition for CreditCardExpYY
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_CreditCardExpYY = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "CreditCardExpYY", null);
	/**
	 * Column name CreditCardExpYY
	 */
	public static final String COLUMNNAME_CreditCardExpYY = "CreditCardExpYY";

	/**
	 * Set Kreditkarten-Nummer.
	 * Kreditkarten-Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditCardNumber(java.lang.String CreditCardNumber);

	/**
	 * Get Kreditkarten-Nummer.
	 * Kreditkarten-Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreditCardNumber();

	/**
	 * Column definition for CreditCardNumber
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_CreditCardNumber = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "CreditCardNumber", null);
	/**
	 * Column name CreditCardNumber
	 */
	public static final String COLUMNNAME_CreditCardNumber = "CreditCardNumber";

	/**
	 * Set Kreditkarte.
	 * Credit Card (Visa, MC, AmEx)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditCardType(java.lang.String CreditCardType);

	/**
	 * Get Kreditkarte.
	 * Credit Card (Visa, MC, AmEx)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreditCardType();

	/**
	 * Column definition for CreditCardType
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_CreditCardType = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "CreditCardType", null);
	/**
	 * Column name CreditCardType
	 */
	public static final String COLUMNNAME_CreditCardType = "CreditCardType";

	/**
	 * Set Verifizierungs-Code.
	 * Credit Card Verification code on credit card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditCardVV(java.lang.String CreditCardVV);

	/**
	 * Get Verifizierungs-Code.
	 * Credit Card Verification code on credit card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreditCardVV();

	/**
	 * Column definition for CreditCardVV
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_CreditCardVV = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "CreditCardVV", null);
	/**
	 * Column name CreditCardVV
	 */
	public static final String COLUMNNAME_CreditCardVV = "CreditCardVV";

	/**
	 * Set Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateAcct(java.sql.Timestamp DateAcct);

	/**
	 * Get Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcct();

	/**
	 * Column definition for DateAcct
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "DateAcct", null);
	/**
	 * Column name DateAcct
	 */
	public static final String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateTrx(java.sql.Timestamp DateTrx);

	/**
	 * Get Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateTrx();

	/**
	 * Column definition for DateTrx
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_DateTrx = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "DateTrx", null);
	/**
	 * Column name DateTrx
	 */
	public static final String COLUMNNAME_DateTrx = "DateTrx";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription(java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

	/**
	 * Column definition for Description
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "Description", null);
	/**
	 * Column name Description
	 */
	public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Discount Amount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscountAmt(java.math.BigDecimal DiscountAmt);

	/**
	 * Get Discount Amount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDiscountAmt();

	/**
	 * Column definition for DiscountAmt
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_DiscountAmt = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "DiscountAmt", null);
	/**
	 * Column name DiscountAmt
	 */
	public static final String COLUMNNAME_DiscountAmt = "DiscountAmt";

	/**
	 * Set Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocAction(java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocAction();

	/**
	 * Column definition for DocAction
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "DocAction", null);
	/**
	 * Column name DocAction
	 */
	public static final String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocStatus(java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

	/**
	 * Column definition for DocStatus
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "DocStatus", null);
	/**
	 * Column name DocStatus
	 */
	public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo(java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

	/**
	 * Column definition for DocumentNo
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "DocumentNo", null);
	/**
	 * Column name DocumentNo
	 */
	public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set External Order Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalOrderId(java.lang.String ExternalOrderId);

	/**
	 * Get External Order Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalOrderId();

	/**
	 * Column definition for ExternalOrderId
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_ExternalOrderId = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "ExternalOrderId", null);
	/**
	 * Column name ExternalOrderId
	 */
	public static final String COLUMNNAME_ExternalOrderId = "ExternalOrderId";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive(boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

	/**
	 * Column definition for IsActive
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "IsActive", null);
	/**
	 * Column name IsActive
	 */
	public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Allocated.
	 * Zeigt an ob eine Zahlung bereits zugeordnet wurde
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAllocated(boolean IsAllocated);

	/**
	 * Get Allocated.
	 * Zeigt an ob eine Zahlung bereits zugeordnet wurde
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllocated();

	/**
	 * Column definition for IsAllocated
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_IsAllocated = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "IsAllocated", null);
	/**
	 * Column name IsAllocated
	 */
	public static final String COLUMNNAME_IsAllocated = "IsAllocated";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApproved(boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApproved();

	/**
	 * Column definition for IsApproved
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "IsApproved", null);
	/**
	 * Column name IsApproved
	 */
	public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set isAutoAllocateAvailableAmt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAutoAllocateAvailableAmt(boolean IsAutoAllocateAvailableAmt);

	/**
	 * Get isAutoAllocateAvailableAmt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAutoAllocateAvailableAmt();

	/**
	 * Column definition for IsAutoAllocateAvailableAmt
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_IsAutoAllocateAvailableAmt = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "IsAutoAllocateAvailableAmt", null);
	/**
	 * Column name IsAutoAllocateAvailableAmt
	 */
	public static final String COLUMNNAME_IsAutoAllocateAvailableAmt = "IsAutoAllocateAvailableAmt";

	/**
	 * Set Delayed Capture.
	 * Charge after Shipment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDelayedCapture(boolean IsDelayedCapture);

	/**
	 * Get Delayed Capture.
	 * Charge after Shipment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDelayedCapture();

	/**
	 * Column definition for IsDelayedCapture
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_IsDelayedCapture = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "IsDelayedCapture", null);
	/**
	 * Column name IsDelayedCapture
	 */
	public static final String COLUMNNAME_IsDelayedCapture = "IsDelayedCapture";

	/**
	 * Set Online Access.
	 * Can be accessed online
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsOnline(boolean IsOnline);

	/**
	 * Get Online Access.
	 * Can be accessed online
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOnline();

	/**
	 * Column definition for IsOnline
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_IsOnline = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "IsOnline", null);
	/**
	 * Column name IsOnline
	 */
	public static final String COLUMNNAME_IsOnline = "IsOnline";

	/**
	 * Set Online Payment Approved.
	 * If set means that the payment was succesfully paid online
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsOnlineApproved(boolean IsOnlineApproved);

	/**
	 * Get Online Payment Approved.
	 * If set means that the payment was succesfully paid online
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOnlineApproved();

	/**
	 * Column definition for IsOnlineApproved
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_IsOnlineApproved = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "IsOnlineApproved", null);
	/**
	 * Column name IsOnlineApproved
	 */
	public static final String COLUMNNAME_IsOnlineApproved = "IsOnlineApproved";

	/**
	 * Set Over/Under Payment.
	 * Überzahlung (nicht zugewiesen) oder Unterzahlung (Teilzahlung)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsOverUnderPayment(boolean IsOverUnderPayment);

	/**
	 * Get Over/Under Payment.
	 * Überzahlung (nicht zugewiesen) oder Unterzahlung (Teilzahlung)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverUnderPayment();

	/**
	 * Column definition for IsOverUnderPayment
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_IsOverUnderPayment = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "IsOverUnderPayment", null);
	/**
	 * Column name IsOverUnderPayment
	 */
	public static final String COLUMNNAME_IsOverUnderPayment = "IsOverUnderPayment";

	/**
	 * Set Prepayment.
	 * Die Zahlung ist eine Vorauszahlung
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPrepayment(boolean IsPrepayment);

	/**
	 * Get Prepayment.
	 * Die Zahlung ist eine Vorauszahlung
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPrepayment();

	/**
	 * Column definition for IsPrepayment
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_IsPrepayment = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "IsPrepayment", null);
	/**
	 * Column name IsPrepayment
	 */
	public static final String COLUMNNAME_IsPrepayment = "IsPrepayment";

	/**
	 * Set Zahlungseingang.
	 * This is a sales transaction (receipt)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReceipt(boolean IsReceipt);

	/**
	 * Get Zahlungseingang.
	 * This is a sales transaction (receipt)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReceipt();

	/**
	 * Column definition for IsReceipt
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_IsReceipt = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "IsReceipt", null);
	/**
	 * Column name IsReceipt
	 */
	public static final String COLUMNNAME_IsReceipt = "IsReceipt";

	/**
	 * Set Reconciled.
	 * Payment is reconciled with bank statement
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReconciled(boolean IsReconciled);

	/**
	 * Get Reconciled.
	 * Payment is reconciled with bank statement
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReconciled();

	/**
	 * Column definition for IsReconciled
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_IsReconciled = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "IsReconciled", null);
	/**
	 * Column name IsReconciled
	 */
	public static final String COLUMNNAME_IsReconciled = "IsReconciled";

	/**
	 * Set Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSelfService(boolean IsSelfService);

	/**
	 * Get Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSelfService();

	/**
	 * Column definition for IsSelfService
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "IsSelfService", null);
	/**
	 * Column name IsSelfService
	 */
	public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Micr.
	 * Combination of routing no, account and check no
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMicr(java.lang.String Micr);

	/**
	 * Get Micr.
	 * Combination of routing no, account and check no
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMicr();

	/**
	 * Column definition for Micr
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_Micr = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "Micr", null);
	/**
	 * Column name Micr
	 */
	public static final String COLUMNNAME_Micr = "Micr";

	/**
	 * Set Original Transaction ID.
	 * Original Transaction ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrig_TrxID(java.lang.String Orig_TrxID);

	/**
	 * Get Original Transaction ID.
	 * Original Transaction ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrig_TrxID();

	/**
	 * Column definition for Orig_TrxID
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_Orig_TrxID = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "Orig_TrxID", null);
	/**
	 * Column name Orig_TrxID
	 */
	public static final String COLUMNNAME_Orig_TrxID = "Orig_TrxID";

	/**
	 * Set Over/Under Payment.
	 * Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOverUnderAmt(java.math.BigDecimal OverUnderAmt);

	/**
	 * Get Over/Under Payment.
	 * Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getOverUnderAmt();

	/**
	 * Column definition for OverUnderAmt
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_OverUnderAmt = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "OverUnderAmt", null);
	/**
	 * Column name OverUnderAmt
	 */
	public static final String COLUMNNAME_OverUnderAmt = "OverUnderAmt";

	/**
	 * Set Zahlungsbetrag.
	 * Amount being paid
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayAmt(java.math.BigDecimal PayAmt);

	/**
	 * Get Zahlungsbetrag.
	 * Amount being paid
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPayAmt();

	/**
	 * Column definition for PayAmt
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_PayAmt = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "PayAmt", null);
	/**
	 * Column name PayAmt
	 */
	public static final String COLUMNNAME_PayAmt = "PayAmt";

	/**
	 * Set Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPaymentRule(java.lang.String PaymentRule);

	/**
	 * Get Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentRule();

	/**
	 * Column definition for PaymentRule
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_PaymentRule = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "PaymentRule", null);
	/**
	 * Column name PaymentRule
	 */
	public static final String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set PO Number.
	 * Purchase Order Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPONum(java.lang.String PONum);

	/**
	 * Get PO Number.
	 * Purchase Order Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPONum();

	/**
	 * Column definition for PONum
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_PONum = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "PONum", null);
	/**
	 * Column name PONum
	 */
	public static final String COLUMNNAME_PONum = "PONum";

	/**
	 * Set Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPosted(boolean Posted);

	/**
	 * Get Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPosted();

	/**
	 * Column definition for Posted
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_Posted = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "Posted", null);
	/**
	 * Column name Posted
	 */
	public static final String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostingError_Issue_ID(int PostingError_Issue_ID);

	/**
	 * Get Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPostingError_Issue_ID();

	public org.compiere.model.I_AD_Issue getPostingError_Issue();

	public void setPostingError_Issue(org.compiere.model.I_AD_Issue PostingError_Issue);

	/**
	 * Column definition for PostingError_Issue_ID
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_AD_Issue> COLUMN_PostingError_Issue_ID = new org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_AD_Issue>(I_C_Payment.class, "PostingError_Issue_ID", org.compiere.model.I_AD_Issue.class);
	/**
	 * Column name PostingError_Issue_ID
	 */
	public static final String COLUMNNAME_PostingError_Issue_ID = "PostingError_Issue_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed(boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

	/**
	 * Column definition for Processed
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "Processed", null);
	/**
	 * Column name Processed
	 */
	public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing(boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

	/**
	 * Column definition for Processing
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "Processing", null);
	/**
	 * Column name Processing
	 */
	public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Authorization Code.
	 * Authorization Code returned
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_AuthCode(java.lang.String R_AuthCode);

	/**
	 * Get Authorization Code.
	 * Authorization Code returned
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_AuthCode();

	/**
	 * Column definition for R_AuthCode
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_R_AuthCode = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "R_AuthCode", null);
	/**
	 * Column name R_AuthCode
	 */
	public static final String COLUMNNAME_R_AuthCode = "R_AuthCode";

	/**
	 * Set Authorization Code (DC).
	 * Authorization Code Delayed Capture returned
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_AuthCode_DC(java.lang.String R_AuthCode_DC);

	/**
	 * Get Authorization Code (DC).
	 * Authorization Code Delayed Capture returned
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_AuthCode_DC();

	/**
	 * Column definition for R_AuthCode_DC
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_R_AuthCode_DC = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "R_AuthCode_DC", null);
	/**
	 * Column name R_AuthCode_DC
	 */
	public static final String COLUMNNAME_R_AuthCode_DC = "R_AuthCode_DC";

	/**
	 * Set Addresse verifiziert.
	 * This address has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_AvsAddr(java.lang.String R_AvsAddr);

	/**
	 * Get Addresse verifiziert.
	 * This address has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_AvsAddr();

	/**
	 * Column definition for R_AvsAddr
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_R_AvsAddr = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "R_AvsAddr", null);
	/**
	 * Column name R_AvsAddr
	 */
	public static final String COLUMNNAME_R_AvsAddr = "R_AvsAddr";

	/**
	 * Set Postleitzahl verifiziert.
	 * The Zip Code has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_AvsZip(java.lang.String R_AvsZip);

	/**
	 * Get Postleitzahl verifiziert.
	 * The Zip Code has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_AvsZip();

	/**
	 * Column definition for R_AvsZip
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_R_AvsZip = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "R_AvsZip", null);
	/**
	 * Column name R_AvsZip
	 */
	public static final String COLUMNNAME_R_AvsZip = "R_AvsZip";

	/**
	 * Set CVV Match.
	 * Credit Card Verification Code Match
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_CVV2Match(boolean R_CVV2Match);

	/**
	 * Get CVV Match.
	 * Credit Card Verification Code Match
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isR_CVV2Match();

	/**
	 * Column definition for R_CVV2Match
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_R_CVV2Match = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "R_CVV2Match", null);
	/**
	 * Column name R_CVV2Match
	 */
	public static final String COLUMNNAME_R_CVV2Match = "R_CVV2Match";

	/**
	 * Set Info.
	 * Response info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_Info(java.lang.String R_Info);

	/**
	 * Get Info.
	 * Response info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_Info();

	/**
	 * Column definition for R_Info
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_R_Info = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "R_Info", null);
	/**
	 * Column name R_Info
	 */
	public static final String COLUMNNAME_R_Info = "R_Info";

	/**
	 * Set Referenz.
	 * Payment reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_PnRef(java.lang.String R_PnRef);

	/**
	 * Get Referenz.
	 * Payment reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_PnRef();

	/**
	 * Column definition for R_PnRef
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_R_PnRef = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "R_PnRef", null);
	/**
	 * Column name R_PnRef
	 */
	public static final String COLUMNNAME_R_PnRef = "R_PnRef";

	/**
	 * Set Reference (DC).
	 * Payment Reference Delayed Capture
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_PnRef_DC(java.lang.String R_PnRef_DC);

	/**
	 * Get Reference (DC).
	 * Payment Reference Delayed Capture
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_PnRef_DC();

	/**
	 * Column definition for R_PnRef_DC
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_R_PnRef_DC = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "R_PnRef_DC", null);
	/**
	 * Column name R_PnRef_DC
	 */
	public static final String COLUMNNAME_R_PnRef_DC = "R_PnRef_DC";

	/**
	 * Set Response Message.
	 * Response message
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_RespMsg(java.lang.String R_RespMsg);

	/**
	 * Get Response Message.
	 * Response message
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_RespMsg();

	/**
	 * Column definition for R_RespMsg
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_R_RespMsg = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "R_RespMsg", null);
	/**
	 * Column name R_RespMsg
	 */
	public static final String COLUMNNAME_R_RespMsg = "R_RespMsg";

	/**
	 * Set Ergebnis.
	 * Result of transmission
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_Result(java.lang.String R_Result);

	/**
	 * Get Ergebnis.
	 * Result of transmission
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_Result();

	/**
	 * Column definition for R_Result
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_R_Result = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "R_Result", null);
	/**
	 * Column name R_Result
	 */
	public static final String COLUMNNAME_R_Result = "R_Result";

	/**
	 * Set Referenced Payment.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRef_Payment_ID(int Ref_Payment_ID);

	/**
	 * Get Referenced Payment.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRef_Payment_ID();

	/**
	 * Column name Ref_Payment_ID
	 */
	public static final String COLUMNNAME_Ref_Payment_ID = "Ref_Payment_ID";

	/**
	 * Set Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReversal_ID(int Reversal_ID);

	/**
	 * Get Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getReversal_ID();

	/**
	 * Column name Reversal_ID
	 */
	public static final String COLUMNNAME_Reversal_ID = "Reversal_ID";

	/**
	 * Set BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRoutingNo(java.lang.String RoutingNo);

	/**
	 * Get BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRoutingNo();

	/**
	 * Column definition for RoutingNo
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_RoutingNo = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "RoutingNo", null);
	/**
	 * Column name RoutingNo
	 */
	public static final String COLUMNNAME_RoutingNo = "RoutingNo";

	/**
	 * Set Swipe.
	 * Track 1 and 2 of the Credit Card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSwipe(java.lang.String Swipe);

	/**
	 * Get Swipe.
	 * Track 1 and 2 of the Credit Card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSwipe();

	/**
	 * Column definition for Swipe
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_Swipe = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "Swipe", null);
	/**
	 * Column name Swipe
	 */
	public static final String COLUMNNAME_Swipe = "Swipe";

	/**
	 * Set Steuerbetrag.
	 * Tax Amount for a document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTaxAmt(java.math.BigDecimal TaxAmt);

	/**
	 * Get Steuerbetrag.
	 * Tax Amount for a document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTaxAmt();

	/**
	 * Column definition for TaxAmt
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_TaxAmt = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "TaxAmt", null);
	/**
	 * Column name TaxAmt
	 */
	public static final String COLUMNNAME_TaxAmt = "TaxAmt";

	/**
	 * Set Zahlmittel.
	 * Method of Payment
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTenderType(java.lang.String TenderType);

	/**
	 * Get Zahlmittel.
	 * Method of Payment
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTenderType();

	/**
	 * Column definition for TenderType
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_TenderType = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "TenderType", null);
	/**
	 * Column name TenderType
	 */
	public static final String COLUMNNAME_TenderType = "TenderType";

	/**
	 * Set Transaction Type.
	 * Type of credit card transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTrxType(java.lang.String TrxType);

	/**
	 * Get Transaction Type.
	 * Type of credit card transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTrxType();

	/**
	 * Column definition for TrxType
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_TrxType = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "TrxType", null);
	/**
	 * Column name TrxType
	 */
	public static final String COLUMNNAME_TrxType = "TrxType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

	/**
	 * Column definition for Updated
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "Updated", null);
	/**
	 * Column name Updated
	 */
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

	/**
	 * Column name UpdatedBy
	 */
	public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser1_ID(int User1_ID);

	/**
	 * Get User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUser1_ID();

	public org.compiere.model.I_C_ElementValue getUser1();

	public void setUser1(org.compiere.model.I_C_ElementValue User1);

	/**
	 * Column definition for User1_ID
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_ElementValue>(I_C_Payment.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
	/**
	 * Column name User1_ID
	 */
	public static final String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser2_ID(int User2_ID);

	/**
	 * Get User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUser2_ID();

	public org.compiere.model.I_C_ElementValue getUser2();

	public void setUser2(org.compiere.model.I_C_ElementValue User2);

	/**
	 * Column definition for User2_ID
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<I_C_Payment, org.compiere.model.I_C_ElementValue>(I_C_Payment.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
	/**
	 * Column name User2_ID
	 */
	public static final String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set Prüfziffer.
	 * Voice Authorization Code from credit card company
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVoiceAuthCode(java.lang.String VoiceAuthCode);

	/**
	 * Get Prüfziffer.
	 * Voice Authorization Code from credit card company
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVoiceAuthCode();

	/**
	 * Column definition for VoiceAuthCode
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_VoiceAuthCode = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "VoiceAuthCode", null);
	/**
	 * Column name VoiceAuthCode
	 */
	public static final String COLUMNNAME_VoiceAuthCode = "VoiceAuthCode";

	/**
	 * Set Write-off Amount.
	 * Amount to write-off
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWriteOffAmt(java.math.BigDecimal WriteOffAmt);

	/**
	 * Get Write-off Amount.
	 * Amount to write-off
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getWriteOffAmt();

	/**
	 * Column definition for WriteOffAmt
	 */
	public static final org.adempiere.model.ModelColumn<I_C_Payment, Object> COLUMN_WriteOffAmt = new org.adempiere.model.ModelColumn<I_C_Payment, Object>(I_C_Payment.class, "WriteOffAmt", null);
	/**
	 * Column name WriteOffAmt
	 */
	public static final String COLUMNNAME_WriteOffAmt = "WriteOffAmt";
}
