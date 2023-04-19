package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_BankStatementLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BankStatementLine 
{

	String Table_Name = "C_BankStatementLine";

//	/** AD_Table_ID=393 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


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
	 * Set Bank Fee.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBankFeeAmt (BigDecimal BankFeeAmt);

	/**
	 * Get Bank Fee.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getBankFeeAmt();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_BankFeeAmt = new ModelColumn<>(I_C_BankStatementLine.class, "BankFeeAmt", null);
	String COLUMNNAME_BankFeeAmt = "BankFeeAmt";

	/**
	 * Set Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BankStatement_ID();

	String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Statement Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/**
	 * Get Statement Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BankStatementLine_ID();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_C_BankStatementLine_ID = new ModelColumn<>(I_C_BankStatementLine.class, "C_BankStatementLine_ID", null);
	String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Cashbook/Bank account To.
	 * Cashbook/Bank account To
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccountTo_ID (int C_BP_BankAccountTo_ID);

	/**
	 * Get Cashbook/Bank account To.
	 * Cashbook/Bank account To
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccountTo_ID();

	String COLUMNNAME_C_BP_BankAccountTo_ID = "C_BP_BankAccountTo_ID";

	/**
	 * Set Costs.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Costs.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Charge_ID();

	String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

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
	 * Set Charge amount.
	 * Charge Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setChargeAmt (BigDecimal ChargeAmt);

	/**
	 * Get Charge amount.
	 * Charge Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getChargeAmt();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_ChargeAmt = new ModelColumn<>(I_C_BankStatementLine.class, "ChargeAmt", null);
	String COLUMNNAME_ChargeAmt = "ChargeAmt";

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

	ModelColumn<I_C_BankStatementLine, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_BankStatementLine.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_C_BankStatementLine, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_BankStatementLine.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Payment.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Payment.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Payment_ID();

	String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_Created = new ModelColumn<>(I_C_BankStatementLine.class, "Created", null);
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

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_CurrencyRate = new ModelColumn<>(I_C_BankStatementLine.class, "CurrencyRate", null);
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

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_DateAcct = new ModelColumn<>(I_C_BankStatementLine.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Debitor No./Kreditor No..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDebitorOrCreditorId (int DebitorOrCreditorId);

	/**
	 * Get Debitor No./Kreditor No..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDebitorOrCreditorId();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_DebitorOrCreditorId = new ModelColumn<>(I_C_BankStatementLine.class, "DebitorOrCreditorId", null);
	String COLUMNNAME_DebitorOrCreditorId = "DebitorOrCreditorId";

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

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_Description = new ModelColumn<>(I_C_BankStatementLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set EFT Amount.
	 * Electronic Funds Transfer Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftAmt (@Nullable BigDecimal EftAmt);

	/**
	 * Get EFT Amount.
	 * Electronic Funds Transfer Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getEftAmt();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftAmt = new ModelColumn<>(I_C_BankStatementLine.class, "EftAmt", null);
	String COLUMNNAME_EftAmt = "EftAmt";

	/**
	 * Set EFT Check No.
	 * Electronic Funds Transfer Check No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftCheckNo (@Nullable java.lang.String EftCheckNo);

	/**
	 * Get EFT Check No.
	 * Electronic Funds Transfer Check No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftCheckNo();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftCheckNo = new ModelColumn<>(I_C_BankStatementLine.class, "EftCheckNo", null);
	String COLUMNNAME_EftCheckNo = "EftCheckNo";

	/**
	 * Set EFT Currency.
	 * Electronic Funds Transfer Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftCurrency (@Nullable java.lang.String EftCurrency);

	/**
	 * Get EFT Currency.
	 * Electronic Funds Transfer Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftCurrency();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftCurrency = new ModelColumn<>(I_C_BankStatementLine.class, "EftCurrency", null);
	String COLUMNNAME_EftCurrency = "EftCurrency";

	/**
	 * Set EFT Memo.
	 * Electronic Funds Transfer Memo
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftMemo (@Nullable java.lang.String EftMemo);

	/**
	 * Get EFT Memo.
	 * Electronic Funds Transfer Memo
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftMemo();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftMemo = new ModelColumn<>(I_C_BankStatementLine.class, "EftMemo", null);
	String COLUMNNAME_EftMemo = "EftMemo";

	/**
	 * Set EFT Payee.
	 * Electronic Funds Transfer Payee information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftPayee (@Nullable java.lang.String EftPayee);

	/**
	 * Get EFT Payee.
	 * Electronic Funds Transfer Payee information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftPayee();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftPayee = new ModelColumn<>(I_C_BankStatementLine.class, "EftPayee", null);
	String COLUMNNAME_EftPayee = "EftPayee";

	/**
	 * Set EFT Payee Account.
	 * Electronic Funds Transfer Payee Account Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftPayeeAccount (@Nullable java.lang.String EftPayeeAccount);

	/**
	 * Get EFT Payee Account.
	 * Electronic Funds Transfer Payee Account Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftPayeeAccount();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftPayeeAccount = new ModelColumn<>(I_C_BankStatementLine.class, "EftPayeeAccount", null);
	String COLUMNNAME_EftPayeeAccount = "EftPayeeAccount";

	/**
	 * Set EFT Reference.
	 * Electronic Funds Transfer Reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftReference (@Nullable java.lang.String EftReference);

	/**
	 * Get EFT Reference.
	 * Electronic Funds Transfer Reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftReference();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftReference = new ModelColumn<>(I_C_BankStatementLine.class, "EftReference", null);
	String COLUMNNAME_EftReference = "EftReference";

	/**
	 * Set EFT Statement Line Date.
	 * Electronic Funds Transfer Statement Line Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftStatementLineDate (@Nullable java.sql.Timestamp EftStatementLineDate);

	/**
	 * Get EFT Statement Line Date.
	 * Electronic Funds Transfer Statement Line Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getEftStatementLineDate();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftStatementLineDate = new ModelColumn<>(I_C_BankStatementLine.class, "EftStatementLineDate", null);
	String COLUMNNAME_EftStatementLineDate = "EftStatementLineDate";

	/**
	 * Set EFT Trx ID.
	 * Electronic Funds Transfer Transaction ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftTrxID (@Nullable java.lang.String EftTrxID);

	/**
	 * Get EFT Trx ID.
	 * Electronic Funds Transfer Transaction ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftTrxID();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftTrxID = new ModelColumn<>(I_C_BankStatementLine.class, "EftTrxID", null);
	String COLUMNNAME_EftTrxID = "EftTrxID";

	/**
	 * Set EFT Trx Type.
	 * Electronic Funds Transfer Transaction Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftTrxType (@Nullable java.lang.String EftTrxType);

	/**
	 * Get EFT Trx Type.
	 * Electronic Funds Transfer Transaction Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftTrxType();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftTrxType = new ModelColumn<>(I_C_BankStatementLine.class, "EftTrxType", null);
	String COLUMNNAME_EftTrxType = "EftTrxType";

	/**
	 * Set EFT Effective Date.
	 * Electronic Funds Transfer Valuta (effective) Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftValutaDate (@Nullable java.sql.Timestamp EftValutaDate);

	/**
	 * Get EFT Effective Date.
	 * Electronic Funds Transfer Valuta (effective) Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getEftValutaDate();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_EftValutaDate = new ModelColumn<>(I_C_BankStatementLine.class, "EftValutaDate", null);
	String COLUMNNAME_EftValutaDate = "EftValutaDate";

	/**
	 * Set Imported Bill Partner IBAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImportedBillPartnerIBAN (@Nullable java.lang.String ImportedBillPartnerIBAN);

	/**
	 * Get Imported Bill Partner IBAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getImportedBillPartnerIBAN();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_ImportedBillPartnerIBAN = new ModelColumn<>(I_C_BankStatementLine.class, "ImportedBillPartnerIBAN", null);
	String COLUMNNAME_ImportedBillPartnerIBAN = "ImportedBillPartnerIBAN";

	/**
	 * Set Imported Bill Partner Name.
	 * Name of the Bill Partner as appears in the import file
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImportedBillPartnerName (@Nullable java.lang.String ImportedBillPartnerName);

	/**
	 * Get Imported Bill Partner Name.
	 * Name of the Bill Partner as appears in the import file
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getImportedBillPartnerName();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_ImportedBillPartnerName = new ModelColumn<>(I_C_BankStatementLine.class, "ImportedBillPartnerName", null);
	String COLUMNNAME_ImportedBillPartnerName = "ImportedBillPartnerName";

	/**
	 * Set Interest Amount.
	 * Interest Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInterestAmt (BigDecimal InterestAmt);

	/**
	 * Get Interest Amount.
	 * Interest Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getInterestAmt();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_InterestAmt = new ModelColumn<>(I_C_BankStatementLine.class, "InterestAmt", null);
	String COLUMNNAME_InterestAmt = "InterestAmt";

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

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BankStatementLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Invoice manually allocated.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManual (boolean IsManual);

	/**
	 * Get Invoice manually allocated.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManual();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsManual = new ModelColumn<>(I_C_BankStatementLine.class, "IsManual", null);
	String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set More than one payment.
	 * The bankstatement line references more than one payment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsMultiplePayment (boolean IsMultiplePayment);

	/**
	 * Get More than one payment.
	 * The bankstatement line references more than one payment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isMultiplePayment();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsMultiplePayment = new ModelColumn<>(I_C_BankStatementLine.class, "IsMultiplePayment", null);
	String COLUMNNAME_IsMultiplePayment = "IsMultiplePayment";

	/**
	 * Set Multiple Payments or Invoices.
	 * Bankstatement line with multiple payments/invoices
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsMultiplePaymentOrInvoice (boolean IsMultiplePaymentOrInvoice);

	/**
	 * Get Multiple Payments or Invoices.
	 * Bankstatement line with multiple payments/invoices
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isMultiplePaymentOrInvoice();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsMultiplePaymentOrInvoice = new ModelColumn<>(I_C_BankStatementLine.class, "IsMultiplePaymentOrInvoice", null);
	String COLUMNNAME_IsMultiplePaymentOrInvoice = "IsMultiplePaymentOrInvoice";

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

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsReconciled = new ModelColumn<>(I_C_BankStatementLine.class, "IsReconciled", null);
	String COLUMNNAME_IsReconciled = "IsReconciled";

	/**
	 * Set Reversal.
	 * This is a reversing transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReversal (boolean IsReversal);

	/**
	 * Get Reversal.
	 * This is a reversing transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReversal();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsReversal = new ModelColumn<>(I_C_BankStatementLine.class, "IsReversal", null);
	String COLUMNNAME_IsReversal = "IsReversal";

	/**
	 * Set Update Amounts from invoice.
	 * If set and an invoice is assigned, then the bank statement and transaction amount as well as the currency will be taken from the open amount of the invoice.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUpdateAmountsFromInvoice (boolean IsUpdateAmountsFromInvoice);

	/**
	 * Get Update Amounts from invoice.
	 * If set and an invoice is assigned, then the bank statement and transaction amount as well as the currency will be taken from the open amount of the invoice.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUpdateAmountsFromInvoice();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_IsUpdateAmountsFromInvoice = new ModelColumn<>(I_C_BankStatementLine.class, "IsUpdateAmountsFromInvoice", null);
	String COLUMNNAME_IsUpdateAmountsFromInvoice = "IsUpdateAmountsFromInvoice";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_Line = new ModelColumn<>(I_C_BankStatementLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Linked Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLink_BankStatementLine_ID (int Link_BankStatementLine_ID);

	/**
	 * Get Linked Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLink_BankStatementLine_ID();

	String COLUMNNAME_Link_BankStatementLine_ID = "Link_BankStatementLine_ID";

	/**
	 * Set Memo.
	 * Memo Text
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMemo (@Nullable java.lang.String Memo);

	/**
	 * Get Memo.
	 * Memo Text
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMemo();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_Memo = new ModelColumn<>(I_C_BankStatementLine.class, "Memo", null);
	String COLUMNNAME_Memo = "Memo";

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

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_Processed = new ModelColumn<>(I_C_BankStatementLine.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Reference No.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReferenceNo (@Nullable java.lang.String ReferenceNo);

	/**
	 * Get Reference No.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReferenceNo();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_ReferenceNo = new ModelColumn<>(I_C_BankStatementLine.class, "ReferenceNo", null);
	String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/**
	 * Set Effective date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStatementLineDate (java.sql.Timestamp StatementLineDate);

	/**
	 * Get Effective date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getStatementLineDate();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_StatementLineDate = new ModelColumn<>(I_C_BankStatementLine.class, "StatementLineDate", null);
	String COLUMNNAME_StatementLineDate = "StatementLineDate";

	/**
	 * Set Statement amount.
	 * Statement Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStmtAmt (BigDecimal StmtAmt);

	/**
	 * Get Statement amount.
	 * Statement Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getStmtAmt();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_StmtAmt = new ModelColumn<>(I_C_BankStatementLine.class, "StmtAmt", null);
	String COLUMNNAME_StmtAmt = "StmtAmt";

	/**
	 * Set Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTrxAmt (BigDecimal TrxAmt);

	/**
	 * Get Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTrxAmt();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_TrxAmt = new ModelColumn<>(I_C_BankStatementLine.class, "TrxAmt", null);
	String COLUMNNAME_TrxAmt = "TrxAmt";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_BankStatementLine.class, "Updated", null);
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
	 * Set Valuta Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValutaDate (java.sql.Timestamp ValutaDate);

	/**
	 * Get Valuta Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getValutaDate();

	ModelColumn<I_C_BankStatementLine, Object> COLUMN_ValutaDate = new ModelColumn<>(I_C_BankStatementLine.class, "ValutaDate", null);
	String COLUMNNAME_ValutaDate = "ValutaDate";
}
