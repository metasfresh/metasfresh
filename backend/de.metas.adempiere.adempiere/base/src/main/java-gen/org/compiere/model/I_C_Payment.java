package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Payment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Payment 
{

	String Table_Name = "C_Payment";

//	/** AD_Table_ID=335 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Ort.
	 * City or the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_City (@Nullable java.lang.String A_City);

	/**
	 * Get Ort.
	 * City or the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_City();

	ModelColumn<I_C_Payment, Object> COLUMN_A_City = new ModelColumn<>(I_C_Payment.class, "A_City", null);
	String COLUMNNAME_A_City = "A_City";

	/**
	 * Set Land.
	 * Country
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Country (@Nullable java.lang.String A_Country);

	/**
	 * Get Land.
	 * Country
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Country();

	ModelColumn<I_C_Payment, Object> COLUMN_A_Country = new ModelColumn<>(I_C_Payment.class, "A_Country", null);
	String COLUMNNAME_A_Country = "A_Country";

	/**
	 * Set EMail.
	 * Email Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_EMail (@Nullable java.lang.String A_EMail);

	/**
	 * Get EMail.
	 * Email Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_EMail();

	ModelColumn<I_C_Payment, Object> COLUMN_A_EMail = new ModelColumn<>(I_C_Payment.class, "A_EMail", null);
	String COLUMNNAME_A_EMail = "A_EMail";

	/**
	 * Set Führerschein-Nr..
	 * Payment Identification - Driver License
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Ident_DL (@Nullable java.lang.String A_Ident_DL);

	/**
	 * Get Führerschein-Nr..
	 * Payment Identification - Driver License
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Ident_DL();

	ModelColumn<I_C_Payment, Object> COLUMN_A_Ident_DL = new ModelColumn<>(I_C_Payment.class, "A_Ident_DL", null);
	String COLUMNNAME_A_Ident_DL = "A_Ident_DL";

	/**
	 * Set Ausweis-Nr..
	 * Payment Identification - Social Security No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Ident_SSN (@Nullable java.lang.String A_Ident_SSN);

	/**
	 * Get Ausweis-Nr..
	 * Payment Identification - Social Security No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Ident_SSN();

	ModelColumn<I_C_Payment, Object> COLUMN_A_Ident_SSN = new ModelColumn<>(I_C_Payment.class, "A_Ident_SSN", null);
	String COLUMNNAME_A_Ident_SSN = "A_Ident_SSN";

	/**
	 * Set Name.
	 * Name auf Kreditkarte oder des Kontoeigners
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Name (@Nullable java.lang.String A_Name);

	/**
	 * Get Name.
	 * Name auf Kreditkarte oder des Kontoeigners
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Name();

	ModelColumn<I_C_Payment, Object> COLUMN_A_Name = new ModelColumn<>(I_C_Payment.class, "A_Name", null);
	String COLUMNNAME_A_Name = "A_Name";

	/**
	 * Set Bundesstaat / -land.
	 * State of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_State (@Nullable java.lang.String A_State);

	/**
	 * Get Bundesstaat / -land.
	 * State of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_State();

	ModelColumn<I_C_Payment, Object> COLUMN_A_State = new ModelColumn<>(I_C_Payment.class, "A_State", null);
	String COLUMNNAME_A_State = "A_State";

	/**
	 * Set Straße.
	 * Street address of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Street (@Nullable java.lang.String A_Street);

	/**
	 * Get Straße.
	 * Street address of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Street();

	ModelColumn<I_C_Payment, Object> COLUMN_A_Street = new ModelColumn<>(I_C_Payment.class, "A_Street", null);
	String COLUMNNAME_A_Street = "A_Street";

	/**
	 * Set Postleitzahl.
	 * Zip Code of the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Zip (@Nullable java.lang.String A_Zip);

	/**
	 * Get Postleitzahl.
	 * Zip Code of the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Zip();

	ModelColumn<I_C_Payment, Object> COLUMN_A_Zip = new ModelColumn<>(I_C_Payment.class, "A_Zip", null);
	String COLUMNNAME_A_Zip = "A_Zip";

	/**
	 * Set Konto-Nr..
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountNo (@Nullable java.lang.String AccountNo);

	/**
	 * Get Konto-Nr..
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAccountNo();

	ModelColumn<I_C_Payment, Object> COLUMN_AccountNo = new ModelColumn<>(I_C_Payment.class, "AccountNo", null);
	String COLUMNNAME_AccountNo = "AccountNo";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgTrx_ID();

	String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BankStatement_ID();

	String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/**
	 * Get Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BankStatementLine_ID();

	String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/**
	 * Set Bank Statement Line Reference.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BankStatementLine_Ref_ID (int C_BankStatementLine_Ref_ID);

	/**
	 * Get Bank Statement Line Reference.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BankStatementLine_Ref_ID();

	ModelColumn<I_C_Payment, Object> COLUMN_C_BankStatementLine_Ref_ID = new ModelColumn<>(I_C_Payment.class, "C_BankStatementLine_Ref_ID", null);
	String COLUMNNAME_C_BankStatementLine_Ref_ID = "C_BankStatementLine_Ref_ID";

	/**
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_ID();

	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	@Nullable org.compiere.model.I_C_Campaign getC_Campaign();

	void setC_Campaign(@Nullable org.compiere.model.I_C_Campaign C_Campaign);

	ModelColumn<I_C_Payment, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_C_Payment.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Kassenbuch.
	 * Cash Book for recording petty cash transactions
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CashBook_ID (int C_CashBook_ID);

	/**
	 * Get Kassenbuch.
	 * Cash Book for recording petty cash transactions
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CashBook_ID();

	@Nullable org.compiere.model.I_C_CashBook getC_CashBook();

	void setC_CashBook(@Nullable org.compiere.model.I_C_CashBook C_CashBook);

	ModelColumn<I_C_Payment, org.compiere.model.I_C_CashBook> COLUMN_C_CashBook_ID = new ModelColumn<>(I_C_Payment.class, "C_CashBook_ID", org.compiere.model.I_C_CashBook.class);
	String COLUMNNAME_C_CashBook_ID = "C_CashBook_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Charge_ID();

	String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ConversionType_ID (int C_ConversionType_ID);

	/**
	 * Get Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ConversionType_ID();

	String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_C_Payment, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_Payment.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_C_Payment, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_Payment.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Payment.
	 * Payment identifier
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Payment.
	 * Payment identifier
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Payment_ID();

	ModelColumn<I_C_Payment, Object> COLUMN_C_Payment_ID = new ModelColumn<>(I_C_Payment.class, "C_Payment_ID", null);
	String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Zahlungslauf.
	 * Payment batch for EFT
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_PaymentBatch_ID (int C_PaymentBatch_ID);

	/**
	 * Get Zahlungslauf.
	 * Payment batch for EFT
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_PaymentBatch_ID();

	@Nullable org.compiere.model.I_C_PaymentBatch getC_PaymentBatch();

	void setC_PaymentBatch(@Nullable org.compiere.model.I_C_PaymentBatch C_PaymentBatch);

	ModelColumn<I_C_Payment, org.compiere.model.I_C_PaymentBatch> COLUMN_C_PaymentBatch_ID = new ModelColumn<>(I_C_Payment.class, "C_PaymentBatch_ID", org.compiere.model.I_C_PaymentBatch.class);
	String COLUMNNAME_C_PaymentBatch_ID = "C_PaymentBatch_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setChargeAmt (@Nullable BigDecimal ChargeAmt);

	/**
	 * Get Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getChargeAmt();

	ModelColumn<I_C_Payment, Object> COLUMN_ChargeAmt = new ModelColumn<>(I_C_Payment.class, "ChargeAmt", null);
	String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/**
	 * Set Check No.
	 * Check Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCheckNo (@Nullable java.lang.String CheckNo);

	/**
	 * Get Check No.
	 * Check Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCheckNo();

	ModelColumn<I_C_Payment, Object> COLUMN_CheckNo = new ModelColumn<>(I_C_Payment.class, "CheckNo", null);
	String COLUMNNAME_CheckNo = "CheckNo";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Payment, Object> COLUMN_Created = new ModelColumn<>(I_C_Payment.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

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

	ModelColumn<I_C_Payment, Object> COLUMN_CreditCardExpMM = new ModelColumn<>(I_C_Payment.class, "CreditCardExpMM", null);
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

	ModelColumn<I_C_Payment, Object> COLUMN_CreditCardExpYY = new ModelColumn<>(I_C_Payment.class, "CreditCardExpYY", null);
	String COLUMNNAME_CreditCardExpYY = "CreditCardExpYY";

	/**
	 * Set Kreditkarten-Nummer.
	 * Kreditkarten-Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardNumber (@Nullable java.lang.String CreditCardNumber);

	/**
	 * Get Kreditkarten-Nummer.
	 * Kreditkarten-Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreditCardNumber();

	ModelColumn<I_C_Payment, Object> COLUMN_CreditCardNumber = new ModelColumn<>(I_C_Payment.class, "CreditCardNumber", null);
	String COLUMNNAME_CreditCardNumber = "CreditCardNumber";

	/**
	 * Set Kreditkarte.
	 * Credit Card (Visa, MC, AmEx)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardType (@Nullable java.lang.String CreditCardType);

	/**
	 * Get Kreditkarte.
	 * Credit Card (Visa, MC, AmEx)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreditCardType();

	ModelColumn<I_C_Payment, Object> COLUMN_CreditCardType = new ModelColumn<>(I_C_Payment.class, "CreditCardType", null);
	String COLUMNNAME_CreditCardType = "CreditCardType";

	/**
	 * Set Verifizierungs-Code.
	 * Credit Card Verification code on credit card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardVV (@Nullable java.lang.String CreditCardVV);

	/**
	 * Get Verifizierungs-Code.
	 * Credit Card Verification code on credit card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreditCardVV();

	ModelColumn<I_C_Payment, Object> COLUMN_CreditCardVV = new ModelColumn<>(I_C_Payment.class, "CreditCardVV", null);
	String COLUMNNAME_CreditCardVV = "CreditCardVV";

	/**
	 * Set Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrencyRate (@Nullable BigDecimal CurrencyRate);

	/**
	 * Get Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrencyRate();

	ModelColumn<I_C_Payment, Object> COLUMN_CurrencyRate = new ModelColumn<>(I_C_Payment.class, "CurrencyRate", null);
	String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/**
	 * Set Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateAcct();

	ModelColumn<I_C_Payment, Object> COLUMN_DateAcct = new ModelColumn<>(I_C_Payment.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateTrx (java.sql.Timestamp DateTrx);

	/**
	 * Get Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateTrx();

	ModelColumn<I_C_Payment, Object> COLUMN_DateTrx = new ModelColumn<>(I_C_Payment.class, "DateTrx", null);
	String COLUMNNAME_DateTrx = "DateTrx";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_Payment, Object> COLUMN_Description = new ModelColumn<>(I_C_Payment.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Discount Amount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscountAmt (@Nullable BigDecimal DiscountAmt);

	/**
	 * Get Discount Amount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscountAmt();

	ModelColumn<I_C_Payment, Object> COLUMN_DiscountAmt = new ModelColumn<>(I_C_Payment.class, "DiscountAmt", null);
	String COLUMNNAME_DiscountAmt = "DiscountAmt";

	/**
	 * Set Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_C_Payment, Object> COLUMN_DocAction = new ModelColumn<>(I_C_Payment.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_C_Payment, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_Payment.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

	ModelColumn<I_C_Payment, Object> COLUMN_DocumentNo = new ModelColumn<>(I_C_Payment.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_C_Payment, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_Payment.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set External Order Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalOrderId (@Nullable java.lang.String ExternalOrderId);

	/**
	 * Get External Order Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalOrderId();

	ModelColumn<I_C_Payment, Object> COLUMN_ExternalOrderId = new ModelColumn<>(I_C_Payment.class, "ExternalOrderId", null);
	String COLUMNNAME_ExternalOrderId = "ExternalOrderId";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_Payment, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Payment.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Allocated.
	 * Zeigt an ob eine Zahlung bereits zugeordnet wurde
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllocated (boolean IsAllocated);

	/**
	 * Get Allocated.
	 * Zeigt an ob eine Zahlung bereits zugeordnet wurde
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllocated();

	ModelColumn<I_C_Payment, Object> COLUMN_IsAllocated = new ModelColumn<>(I_C_Payment.class, "IsAllocated", null);
	String COLUMNNAME_IsAllocated = "IsAllocated";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApproved (boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApproved();

	ModelColumn<I_C_Payment, Object> COLUMN_IsApproved = new ModelColumn<>(I_C_Payment.class, "IsApproved", null);
	String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set isAutoAllocateAvailableAmt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoAllocateAvailableAmt (boolean IsAutoAllocateAvailableAmt);

	/**
	 * Get isAutoAllocateAvailableAmt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoAllocateAvailableAmt();

	ModelColumn<I_C_Payment, Object> COLUMN_IsAutoAllocateAvailableAmt = new ModelColumn<>(I_C_Payment.class, "IsAutoAllocateAvailableAmt", null);
	String COLUMNNAME_IsAutoAllocateAvailableAmt = "IsAutoAllocateAvailableAmt";

	/**
	 * Set Delayed Capture.
	 * Charge after Shipment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDelayedCapture (boolean IsDelayedCapture);

	/**
	 * Get Delayed Capture.
	 * Charge after Shipment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDelayedCapture();

	ModelColumn<I_C_Payment, Object> COLUMN_IsDelayedCapture = new ModelColumn<>(I_C_Payment.class, "IsDelayedCapture", null);
	String COLUMNNAME_IsDelayedCapture = "IsDelayedCapture";

	/**
	 * Set Online Access.
	 * Can be accessed online
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOnline (boolean IsOnline);

	/**
	 * Get Online Access.
	 * Can be accessed online
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnline();

	ModelColumn<I_C_Payment, Object> COLUMN_IsOnline = new ModelColumn<>(I_C_Payment.class, "IsOnline", null);
	String COLUMNNAME_IsOnline = "IsOnline";

	/**
	 * Set Online Payment Approved.
	 * If set means that the payment was succesfully paid online
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOnlineApproved (boolean IsOnlineApproved);

	/**
	 * Get Online Payment Approved.
	 * If set means that the payment was succesfully paid online
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnlineApproved();

	ModelColumn<I_C_Payment, Object> COLUMN_IsOnlineApproved = new ModelColumn<>(I_C_Payment.class, "IsOnlineApproved", null);
	String COLUMNNAME_IsOnlineApproved = "IsOnlineApproved";

	/**
	 * Set Over/Under Payment.
	 * Überzahlung (nicht zugewiesen) oder Unterzahlung (Teilzahlung)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOverUnderPayment (boolean IsOverUnderPayment);

	/**
	 * Get Over/Under Payment.
	 * Überzahlung (nicht zugewiesen) oder Unterzahlung (Teilzahlung)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverUnderPayment();

	ModelColumn<I_C_Payment, Object> COLUMN_IsOverUnderPayment = new ModelColumn<>(I_C_Payment.class, "IsOverUnderPayment", null);
	String COLUMNNAME_IsOverUnderPayment = "IsOverUnderPayment";

	/**
	 * Set Prepayment.
	 * Die Zahlung ist eine Vorauszahlung
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrepayment (boolean IsPrepayment);

	/**
	 * Get Prepayment.
	 * Die Zahlung ist eine Vorauszahlung
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrepayment();

	ModelColumn<I_C_Payment, Object> COLUMN_IsPrepayment = new ModelColumn<>(I_C_Payment.class, "IsPrepayment", null);
	String COLUMNNAME_IsPrepayment = "IsPrepayment";

	/**
	 * Set Zahlungseingang.
	 * This is a sales transaction (receipt)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReceipt (boolean IsReceipt);

	/**
	 * Get Zahlungseingang.
	 * This is a sales transaction (receipt)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReceipt();

	ModelColumn<I_C_Payment, Object> COLUMN_IsReceipt = new ModelColumn<>(I_C_Payment.class, "IsReceipt", null);
	String COLUMNNAME_IsReceipt = "IsReceipt";

	/**
	 * Set Reconciled.
	 * Payment is reconciled with bank statement
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReconciled (boolean IsReconciled);

	/**
	 * Get Reconciled.
	 * Payment is reconciled with bank statement
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReconciled();

	ModelColumn<I_C_Payment, Object> COLUMN_IsReconciled = new ModelColumn<>(I_C_Payment.class, "IsReconciled", null);
	String COLUMNNAME_IsReconciled = "IsReconciled";

	/**
	 * Set Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSelfService();

	ModelColumn<I_C_Payment, Object> COLUMN_IsSelfService = new ModelColumn<>(I_C_Payment.class, "IsSelfService", null);
	String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Micr.
	 * Combination of routing no, account and check no
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMicr (@Nullable java.lang.String Micr);

	/**
	 * Get Micr.
	 * Combination of routing no, account and check no
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMicr();

	ModelColumn<I_C_Payment, Object> COLUMN_Micr = new ModelColumn<>(I_C_Payment.class, "Micr", null);
	String COLUMNNAME_Micr = "Micr";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_C_Payment, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_Payment.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Original Transaction ID.
	 * Original Transaction ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrig_TrxID (@Nullable java.lang.String Orig_TrxID);

	/**
	 * Get Original Transaction ID.
	 * Original Transaction ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrig_TrxID();

	ModelColumn<I_C_Payment, Object> COLUMN_Orig_TrxID = new ModelColumn<>(I_C_Payment.class, "Orig_TrxID", null);
	String COLUMNNAME_Orig_TrxID = "Orig_TrxID";

	/**
	 * Set Over/Under Payment.
	 * Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOverUnderAmt (@Nullable BigDecimal OverUnderAmt);

	/**
	 * Get Over/Under Payment.
	 * Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOverUnderAmt();

	ModelColumn<I_C_Payment, Object> COLUMN_OverUnderAmt = new ModelColumn<>(I_C_Payment.class, "OverUnderAmt", null);
	String COLUMNNAME_OverUnderAmt = "OverUnderAmt";

	/**
	 * Set Zahlungsbetrag.
	 * Amount being paid
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPayAmt (BigDecimal PayAmt);

	/**
	 * Get Zahlungsbetrag.
	 * Amount being paid
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPayAmt();

	ModelColumn<I_C_Payment, Object> COLUMN_PayAmt = new ModelColumn<>(I_C_Payment.class, "PayAmt", null);
	String COLUMNNAME_PayAmt = "PayAmt";

	/**
	 * Set PO Number.
	 * Purchase Order Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPONum (@Nullable java.lang.String PONum);

	/**
	 * Get PO Number.
	 * Purchase Order Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPONum();

	ModelColumn<I_C_Payment, Object> COLUMN_PONum = new ModelColumn<>(I_C_Payment.class, "PONum", null);
	String COLUMNNAME_PONum = "PONum";

	/**
	 * Set Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPosted (boolean Posted);

	/**
	 * Get Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPosted();

	ModelColumn<I_C_Payment, Object> COLUMN_Posted = new ModelColumn<>(I_C_Payment.class, "Posted", null);
	String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostingError_Issue_ID (int PostingError_Issue_ID);

	/**
	 * Get Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPostingError_Issue_ID();

	String COLUMNNAME_PostingError_Issue_ID = "PostingError_Issue_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_Payment, Object> COLUMN_Processed = new ModelColumn<>(I_C_Payment.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_C_Payment, Object> COLUMN_Processing = new ModelColumn<>(I_C_Payment.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Authorization Code.
	 * Authorization Code returned
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_AuthCode (@Nullable java.lang.String R_AuthCode);

	/**
	 * Get Authorization Code.
	 * Authorization Code returned
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getR_AuthCode();

	ModelColumn<I_C_Payment, Object> COLUMN_R_AuthCode = new ModelColumn<>(I_C_Payment.class, "R_AuthCode", null);
	String COLUMNNAME_R_AuthCode = "R_AuthCode";

	/**
	 * Set Authorization Code (DC).
	 * Authorization Code Delayed Capture returned
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_AuthCode_DC (@Nullable java.lang.String R_AuthCode_DC);

	/**
	 * Get Authorization Code (DC).
	 * Authorization Code Delayed Capture returned
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getR_AuthCode_DC();

	ModelColumn<I_C_Payment, Object> COLUMN_R_AuthCode_DC = new ModelColumn<>(I_C_Payment.class, "R_AuthCode_DC", null);
	String COLUMNNAME_R_AuthCode_DC = "R_AuthCode_DC";

	/**
	 * Set Addresse verifiziert.
	 * This address has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_AvsAddr (@Nullable java.lang.String R_AvsAddr);

	/**
	 * Get Addresse verifiziert.
	 * This address has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getR_AvsAddr();

	ModelColumn<I_C_Payment, Object> COLUMN_R_AvsAddr = new ModelColumn<>(I_C_Payment.class, "R_AvsAddr", null);
	String COLUMNNAME_R_AvsAddr = "R_AvsAddr";

	/**
	 * Set Postleitzahl verifiziert.
	 * The Zip Code has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_AvsZip (@Nullable java.lang.String R_AvsZip);

	/**
	 * Get Postleitzahl verifiziert.
	 * The Zip Code has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getR_AvsZip();

	ModelColumn<I_C_Payment, Object> COLUMN_R_AvsZip = new ModelColumn<>(I_C_Payment.class, "R_AvsZip", null);
	String COLUMNNAME_R_AvsZip = "R_AvsZip";

	/**
	 * Set CVV Match.
	 * Credit Card Verification Code Match
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_CVV2Match (boolean R_CVV2Match);

	/**
	 * Get CVV Match.
	 * Credit Card Verification Code Match
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isR_CVV2Match();

	ModelColumn<I_C_Payment, Object> COLUMN_R_CVV2Match = new ModelColumn<>(I_C_Payment.class, "R_CVV2Match", null);
	String COLUMNNAME_R_CVV2Match = "R_CVV2Match";

	/**
	 * Set Info.
	 * Response info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_Info (@Nullable java.lang.String R_Info);

	/**
	 * Get Info.
	 * Response info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getR_Info();

	ModelColumn<I_C_Payment, Object> COLUMN_R_Info = new ModelColumn<>(I_C_Payment.class, "R_Info", null);
	String COLUMNNAME_R_Info = "R_Info";

	/**
	 * Set Referenz.
	 * Payment reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_PnRef (@Nullable java.lang.String R_PnRef);

	/**
	 * Get Referenz.
	 * Payment reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getR_PnRef();

	ModelColumn<I_C_Payment, Object> COLUMN_R_PnRef = new ModelColumn<>(I_C_Payment.class, "R_PnRef", null);
	String COLUMNNAME_R_PnRef = "R_PnRef";

	/**
	 * Set Reference (DC).
	 * Payment Reference Delayed Capture
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_PnRef_DC (@Nullable java.lang.String R_PnRef_DC);

	/**
	 * Get Reference (DC).
	 * Payment Reference Delayed Capture
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getR_PnRef_DC();

	ModelColumn<I_C_Payment, Object> COLUMN_R_PnRef_DC = new ModelColumn<>(I_C_Payment.class, "R_PnRef_DC", null);
	String COLUMNNAME_R_PnRef_DC = "R_PnRef_DC";

	/**
	 * Set Response Message.
	 * Response message
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_RespMsg (@Nullable java.lang.String R_RespMsg);

	/**
	 * Get Response Message.
	 * Response message
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getR_RespMsg();

	ModelColumn<I_C_Payment, Object> COLUMN_R_RespMsg = new ModelColumn<>(I_C_Payment.class, "R_RespMsg", null);
	String COLUMNNAME_R_RespMsg = "R_RespMsg";

	/**
	 * Set Ergebnis.
	 * Result of transmission
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_Result (@Nullable java.lang.String R_Result);

	/**
	 * Get Ergebnis.
	 * Result of transmission
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getR_Result();

	ModelColumn<I_C_Payment, Object> COLUMN_R_Result = new ModelColumn<>(I_C_Payment.class, "R_Result", null);
	String COLUMNNAME_R_Result = "R_Result";

	/**
	 * Set Referenced Payment.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_Payment_ID (int Ref_Payment_ID);

	/**
	 * Get Referenced Payment.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_Payment_ID();

	String COLUMNNAME_Ref_Payment_ID = "Ref_Payment_ID";

	/**
	 * Set Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReversal_ID (int Reversal_ID);

	/**
	 * Get Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReversal_ID();

	String COLUMNNAME_Reversal_ID = "Reversal_ID";

	/**
	 * Set IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIBAN (@Nullable java.lang.String IBAN);

	/**
	 * Get IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIBAN();

	ModelColumn<I_C_Payment, Object> COLUMN_IBAN = new ModelColumn<>(I_C_Payment.class, "IBAN", null);
	String COLUMNNAME_IBAN = "IBAN";

	/**
	 * Set BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRoutingNo (@Nullable java.lang.String RoutingNo);

	/**
	 * Get BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRoutingNo();

	ModelColumn<I_C_Payment, Object> COLUMN_RoutingNo = new ModelColumn<>(I_C_Payment.class, "RoutingNo", null);
	String COLUMNNAME_RoutingNo = "RoutingNo";

	/**
	 * Set Source Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSource_Currency_ID (int Source_Currency_ID);

	/**
	 * Get Source Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSource_Currency_ID();

	String COLUMNNAME_Source_Currency_ID = "Source_Currency_ID";

	/**
	 * Set Swipe.
	 * Track 1 and 2 of the Credit Card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSwipe (@Nullable java.lang.String Swipe);

	/**
	 * Get Swipe.
	 * Track 1 and 2 of the Credit Card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSwipe();

	ModelColumn<I_C_Payment, Object> COLUMN_Swipe = new ModelColumn<>(I_C_Payment.class, "Swipe", null);
	String COLUMNNAME_Swipe = "Swipe";

	/**
	 * Set Steuerbetrag.
	 * Tax Amount for a document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxAmt (@Nullable BigDecimal TaxAmt);

	/**
	 * Get Steuerbetrag.
	 * Tax Amount for a document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTaxAmt();

	ModelColumn<I_C_Payment, Object> COLUMN_TaxAmt = new ModelColumn<>(I_C_Payment.class, "TaxAmt", null);
	String COLUMNNAME_TaxAmt = "TaxAmt";

	/**
	 * Set Zahlmittel.
	 * Method of Payment
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTenderType (java.lang.String TenderType);

	/**
	 * Get Zahlmittel.
	 * Method of Payment
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTenderType();

	ModelColumn<I_C_Payment, Object> COLUMN_TenderType = new ModelColumn<>(I_C_Payment.class, "TenderType", null);
	String COLUMNNAME_TenderType = "TenderType";

	/**
	 * Set Transaction Type.
	 * Type of credit card transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTrxType (java.lang.String TrxType);

	/**
	 * Get Transaction Type.
	 * Type of credit card transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTrxType();

	ModelColumn<I_C_Payment, Object> COLUMN_TrxType = new ModelColumn<>(I_C_Payment.class, "TrxType", null);
	String COLUMNNAME_TrxType = "TrxType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Payment, Object> COLUMN_Updated = new ModelColumn<>(I_C_Payment.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser1_ID (int User1_ID);

	/**
	 * Get User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser1_ID();

	@Nullable org.compiere.model.I_C_ElementValue getUser1();

	void setUser1(@Nullable org.compiere.model.I_C_ElementValue User1);

	ModelColumn<I_C_Payment, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new ModelColumn<>(I_C_Payment.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser2_ID (int User2_ID);

	/**
	 * Get User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser2_ID();

	@Nullable org.compiere.model.I_C_ElementValue getUser2();

	void setUser2(@Nullable org.compiere.model.I_C_ElementValue User2);

	ModelColumn<I_C_Payment, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new ModelColumn<>(I_C_Payment.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set Prüfziffer.
	 * Voice Authorization Code from credit card company
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVoiceAuthCode (@Nullable java.lang.String VoiceAuthCode);

	/**
	 * Get Prüfziffer.
	 * Voice Authorization Code from credit card company
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVoiceAuthCode();

	ModelColumn<I_C_Payment, Object> COLUMN_VoiceAuthCode = new ModelColumn<>(I_C_Payment.class, "VoiceAuthCode", null);
	String COLUMNNAME_VoiceAuthCode = "VoiceAuthCode";

	/**
	 * Set Write-off Amount.
	 * Amount to write-off
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWriteOffAmt (@Nullable BigDecimal WriteOffAmt);

	/**
	 * Get Write-off Amount.
	 * Amount to write-off
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getWriteOffAmt();

	ModelColumn<I_C_Payment, Object> COLUMN_WriteOffAmt = new ModelColumn<>(I_C_Payment.class, "WriteOffAmt", null);
	String COLUMNNAME_WriteOffAmt = "WriteOffAmt";
}
