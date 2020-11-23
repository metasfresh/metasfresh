package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for I_BankStatement
 *  @author metasfresh (generated) 
 */
public interface I_I_BankStatement 
{

	String Table_Name = "I_BankStatement";

//	/** AD_Table_ID=600 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set AmtFormat.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmtFormat (@Nullable java.lang.String AmtFormat);

	/**
	 * Get AmtFormat.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAmtFormat();

	ModelColumn<I_I_BankStatement, Object> COLUMN_AmtFormat = new ModelColumn<>(I_I_BankStatement.class, "AmtFormat", null);
	String COLUMNNAME_AmtFormat = "AmtFormat";

	/**
	 * Set Bank Account No.
	 * Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBankAccountNo (@Nullable java.lang.String BankAccountNo);

	/**
	 * Get Bank Account No.
	 * Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBankAccountNo();

	ModelColumn<I_I_BankStatement, Object> COLUMN_BankAccountNo = new ModelColumn<>(I_I_BankStatement.class, "BankAccountNo", null);
	String COLUMNNAME_BankAccountNo = "BankAccountNo";

	/**
	 * Set Name Bill Partner.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_Name (@Nullable java.lang.String Bill_BPartner_Name);

	/**
	 * Get Name Bill Partner.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBill_BPartner_Name();

	ModelColumn<I_I_BankStatement, Object> COLUMN_Bill_BPartner_Name = new ModelColumn<>(I_I_BankStatement.class, "Bill_BPartner_Name", null);
	String COLUMNNAME_Bill_BPartner_Name = "Bill_BPartner_Name";

	/**
	 * Set Geschäftspartner-Schlüssel.
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerValue (@Nullable java.lang.String BPartnerValue);

	/**
	 * Get Geschäftspartner-Schlüssel.
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartnerValue();

	ModelColumn<I_I_BankStatement, Object> COLUMN_BPartnerValue = new ModelColumn<>(I_I_BankStatement.class, "BPartnerValue", null);
	String COLUMNNAME_BPartnerValue = "BPartnerValue";

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
	 * Set Cashbook/Bank account To.
	 * Cashbook/Bank account To
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccountTo_ID (int C_BP_BankAccountTo_ID);

	/**
	 * Get Cashbook/Bank account To.
	 * Cashbook/Bank account To
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccountTo_ID();

	String COLUMNNAME_C_BP_BankAccountTo_ID = "C_BP_BankAccountTo_ID";

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
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_ID();

	@Nullable org.compiere.model.I_C_DataImport getC_DataImport();

	void setC_DataImport(@Nullable org.compiere.model.I_C_DataImport C_DataImport);

	ModelColumn<I_I_BankStatement, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new ModelColumn<>(I_I_BankStatement.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
	String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_Run_ID (int C_DataImport_Run_ID);

	/**
	 * Get Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_Run_ID();

	@Nullable org.compiere.model.I_C_DataImport_Run getC_DataImport_Run();

	void setC_DataImport_Run(@Nullable org.compiere.model.I_C_DataImport_Run C_DataImport_Run);

	ModelColumn<I_I_BankStatement, org.compiere.model.I_C_DataImport_Run> COLUMN_C_DataImport_Run_ID = new ModelColumn<>(I_I_BankStatement.class, "C_DataImport_Run_ID", org.compiere.model.I_C_DataImport_Run.class);
	String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

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

	ModelColumn<I_I_BankStatement, Object> COLUMN_ChargeAmt = new ModelColumn<>(I_I_BankStatement.class, "ChargeAmt", null);
	String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/**
	 * Set Gebühren-Bezeichnung.
	 * Name of the Charge
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setChargeName (@Nullable java.lang.String ChargeName);

	/**
	 * Get Gebühren-Bezeichnung.
	 * Name of the Charge
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getChargeName();

	ModelColumn<I_I_BankStatement, Object> COLUMN_ChargeName = new ModelColumn<>(I_I_BankStatement.class, "ChargeName", null);
	String COLUMNNAME_ChargeName = "ChargeName";

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

	ModelColumn<I_I_BankStatement, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_I_BankStatement.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_I_BankStatement, Object> COLUMN_Created = new ModelColumn<>(I_I_BankStatement.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Zahlung erstellen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreatePayment (@Nullable java.lang.String CreatePayment);

	/**
	 * Get Zahlung erstellen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreatePayment();

	ModelColumn<I_I_BankStatement, Object> COLUMN_CreatePayment = new ModelColumn<>(I_I_BankStatement.class, "CreatePayment", null);
	String COLUMNNAME_CreatePayment = "CreatePayment";

	/**
	 * Set Credit Statement Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditStmtAmt (@Nullable BigDecimal CreditStmtAmt);

	/**
	 * Get Credit Statement Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCreditStmtAmt();

	ModelColumn<I_I_BankStatement, Object> COLUMN_CreditStmtAmt = new ModelColumn<>(I_I_BankStatement.class, "CreditStmtAmt", null);
	String COLUMNNAME_CreditStmtAmt = "CreditStmtAmt";

	/**
	 * Set Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateAcct (@Nullable java.sql.Timestamp DateAcct);

	/**
	 * Get Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateAcct();

	ModelColumn<I_I_BankStatement, Object> COLUMN_DateAcct = new ModelColumn<>(I_I_BankStatement.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set DebitOrCreditAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDebitOrCreditAmt (@Nullable BigDecimal DebitOrCreditAmt);

	/**
	 * Get DebitOrCreditAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDebitOrCreditAmt();

	ModelColumn<I_I_BankStatement, Object> COLUMN_DebitOrCreditAmt = new ModelColumn<>(I_I_BankStatement.class, "DebitOrCreditAmt", null);
	String COLUMNNAME_DebitOrCreditAmt = "DebitOrCreditAmt";

	/**
	 * Set DebitOrCreditIndicator.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDebitOrCreditIndicator (@Nullable java.lang.String DebitOrCreditIndicator);

	/**
	 * Get DebitOrCreditIndicator.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDebitOrCreditIndicator();

	ModelColumn<I_I_BankStatement, Object> COLUMN_DebitOrCreditIndicator = new ModelColumn<>(I_I_BankStatement.class, "DebitOrCreditIndicator", null);
	String COLUMNNAME_DebitOrCreditIndicator = "DebitOrCreditIndicator";

	/**
	 * Set Debit Statement Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDebitStmtAmt (@Nullable BigDecimal DebitStmtAmt);

	/**
	 * Get Debit Statement Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDebitStmtAmt();

	ModelColumn<I_I_BankStatement, Object> COLUMN_DebitStmtAmt = new ModelColumn<>(I_I_BankStatement.class, "DebitStmtAmt", null);
	String COLUMNNAME_DebitStmtAmt = "DebitStmtAmt";

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

	ModelColumn<I_I_BankStatement, Object> COLUMN_Description = new ModelColumn<>(I_I_BankStatement.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set ELV-Betrag.
	 * Electronic Funds Transfer Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftAmt (@Nullable BigDecimal EftAmt);

	/**
	 * Get ELV-Betrag.
	 * Electronic Funds Transfer Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getEftAmt();

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftAmt = new ModelColumn<>(I_I_BankStatement.class, "EftAmt", null);
	String COLUMNNAME_EftAmt = "EftAmt";

	/**
	 * Set ELV Scheck-Nr..
	 * Electronic Funds Transfer Check No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftCheckNo (@Nullable java.lang.String EftCheckNo);

	/**
	 * Get ELV Scheck-Nr..
	 * Electronic Funds Transfer Check No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftCheckNo();

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftCheckNo = new ModelColumn<>(I_I_BankStatement.class, "EftCheckNo", null);
	String COLUMNNAME_EftCheckNo = "EftCheckNo";

	/**
	 * Set ELV-Währung.
	 * Electronic Funds Transfer Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftCurrency (@Nullable java.lang.String EftCurrency);

	/**
	 * Get ELV-Währung.
	 * Electronic Funds Transfer Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftCurrency();

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftCurrency = new ModelColumn<>(I_I_BankStatement.class, "EftCurrency", null);
	String COLUMNNAME_EftCurrency = "EftCurrency";

	/**
	 * Set ELV Memo.
	 * Electronic Funds Transfer Memo
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftMemo (@Nullable java.lang.String EftMemo);

	/**
	 * Get ELV Memo.
	 * Electronic Funds Transfer Memo
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftMemo();

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftMemo = new ModelColumn<>(I_I_BankStatement.class, "EftMemo", null);
	String COLUMNNAME_EftMemo = "EftMemo";

	/**
	 * Set ELV-Zahlungsempfänger.
	 * Electronic Funds Transfer Payee information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftPayee (@Nullable java.lang.String EftPayee);

	/**
	 * Get ELV-Zahlungsempfänger.
	 * Electronic Funds Transfer Payee information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftPayee();

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftPayee = new ModelColumn<>(I_I_BankStatement.class, "EftPayee", null);
	String COLUMNNAME_EftPayee = "EftPayee";

	/**
	 * Set Konto ELV-Zahlungsempfänger.
	 * Electronic Funds Transfer Payyee Account Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftPayeeAccount (@Nullable java.lang.String EftPayeeAccount);

	/**
	 * Get Konto ELV-Zahlungsempfänger.
	 * Electronic Funds Transfer Payyee Account Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftPayeeAccount();

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftPayeeAccount = new ModelColumn<>(I_I_BankStatement.class, "EftPayeeAccount", null);
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

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftReference = new ModelColumn<>(I_I_BankStatement.class, "EftReference", null);
	String COLUMNNAME_EftReference = "EftReference";

	/**
	 * Set Auszugsdatum ELV.
	 * Electronic Funds Transfer Statement Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftStatementDate (@Nullable java.sql.Timestamp EftStatementDate);

	/**
	 * Get Auszugsdatum ELV.
	 * Electronic Funds Transfer Statement Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getEftStatementDate();

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftStatementDate = new ModelColumn<>(I_I_BankStatement.class, "EftStatementDate", null);
	String COLUMNNAME_EftStatementDate = "EftStatementDate";

	/**
	 * Set Datum ELV-Position.
	 * Electronic Funds Transfer Statement Line Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftStatementLineDate (@Nullable java.sql.Timestamp EftStatementLineDate);

	/**
	 * Get Datum ELV-Position.
	 * Electronic Funds Transfer Statement Line Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getEftStatementLineDate();

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftStatementLineDate = new ModelColumn<>(I_I_BankStatement.class, "EftStatementLineDate", null);
	String COLUMNNAME_EftStatementLineDate = "EftStatementLineDate";

	/**
	 * Set ELV Auszugs-Referenz.
	 * Electronic Funds Transfer Statement Reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftStatementReference (@Nullable java.lang.String EftStatementReference);

	/**
	 * Get ELV Auszugs-Referenz.
	 * Electronic Funds Transfer Statement Reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftStatementReference();

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftStatementReference = new ModelColumn<>(I_I_BankStatement.class, "EftStatementReference", null);
	String COLUMNNAME_EftStatementReference = "EftStatementReference";

	/**
	 * Set ELV-TransaktionsID.
	 * Electronic Funds Transfer Transaction ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftTrxID (@Nullable java.lang.String EftTrxID);

	/**
	 * Get ELV-TransaktionsID.
	 * Electronic Funds Transfer Transaction ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftTrxID();

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftTrxID = new ModelColumn<>(I_I_BankStatement.class, "EftTrxID", null);
	String COLUMNNAME_EftTrxID = "EftTrxID";

	/**
	 * Set ELV-Transaktionsart.
	 * Electronic Funds Transfer Transaction Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftTrxType (@Nullable java.lang.String EftTrxType);

	/**
	 * Get ELV-Transaktionsart.
	 * Electronic Funds Transfer Transaction Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEftTrxType();

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftTrxType = new ModelColumn<>(I_I_BankStatement.class, "EftTrxType", null);
	String COLUMNNAME_EftTrxType = "EftTrxType";

	/**
	 * Set ELV Wertstellungs-Datum.
	 * Electronic Funds Transfer Valuta (effective) Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEftValutaDate (@Nullable java.sql.Timestamp EftValutaDate);

	/**
	 * Get ELV Wertstellungs-Datum.
	 * Electronic Funds Transfer Valuta (effective) Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getEftValutaDate();

	ModelColumn<I_I_BankStatement, Object> COLUMN_EftValutaDate = new ModelColumn<>(I_I_BankStatement.class, "EftValutaDate", null);
	String COLUMNNAME_EftValutaDate = "EftValutaDate";

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

	ModelColumn<I_I_BankStatement, Object> COLUMN_IBAN = new ModelColumn<>(I_I_BankStatement.class, "IBAN", null);
	String COLUMNNAME_IBAN = "IBAN";

	/**
	 * Set Import - Bankauszug.
	 * Import of the Bank Statement
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_BankStatement_ID (int I_BankStatement_ID);

	/**
	 * Get Import - Bankauszug.
	 * Import of the Bank Statement
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getI_BankStatement_ID();

	ModelColumn<I_I_BankStatement, Object> COLUMN_I_BankStatement_ID = new ModelColumn<>(I_I_BankStatement.class, "I_BankStatement_ID", null);
	String COLUMNNAME_I_BankStatement_ID = "I_BankStatement_ID";

	/**
	 * Set IBAN_To.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIBAN_To (@Nullable java.lang.String IBAN_To);

	/**
	 * Get IBAN_To.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIBAN_To();

	ModelColumn<I_I_BankStatement, Object> COLUMN_IBAN_To = new ModelColumn<>(I_I_BankStatement.class, "IBAN_To", null);
	String COLUMNNAME_IBAN_To = "IBAN_To";

	/**
	 * Set Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_ErrorMsg (@Nullable java.lang.String I_ErrorMsg);

	/**
	 * Get Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_ErrorMsg();

	ModelColumn<I_I_BankStatement, Object> COLUMN_I_ErrorMsg = new ModelColumn<>(I_I_BankStatement.class, "I_ErrorMsg", null);
	String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isI_IsImported();

	ModelColumn<I_I_BankStatement, Object> COLUMN_I_IsImported = new ModelColumn<>(I_I_BankStatement.class, "I_IsImported", null);
	String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineContent (@Nullable java.lang.String I_LineContent);

	/**
	 * Get Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_LineContent();

	ModelColumn<I_I_BankStatement, Object> COLUMN_I_LineContent = new ModelColumn<>(I_I_BankStatement.class, "I_LineContent", null);
	String COLUMNNAME_I_LineContent = "I_LineContent";

	/**
	 * Set Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineNo (int I_LineNo);

	/**
	 * Get Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getI_LineNo();

	ModelColumn<I_I_BankStatement, Object> COLUMN_I_LineNo = new ModelColumn<>(I_I_BankStatement.class, "I_LineNo", null);
	String COLUMNNAME_I_LineNo = "I_LineNo";

	/**
	 * Set Interest Amount.
	 * Interest Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInterestAmt (@Nullable BigDecimal InterestAmt);

	/**
	 * Get Interest Amount.
	 * Interest Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getInterestAmt();

	ModelColumn<I_I_BankStatement, Object> COLUMN_InterestAmt = new ModelColumn<>(I_I_BankStatement.class, "InterestAmt", null);
	String COLUMNNAME_InterestAmt = "InterestAmt";

	/**
	 * Set Invoice Document No.
	 * Document Number of the Invoice
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceDocumentNo (@Nullable java.lang.String InvoiceDocumentNo);

	/**
	 * Get Invoice Document No.
	 * Document Number of the Invoice
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoiceDocumentNo();

	ModelColumn<I_I_BankStatement, Object> COLUMN_InvoiceDocumentNo = new ModelColumn<>(I_I_BankStatement.class, "InvoiceDocumentNo", null);
	String COLUMNNAME_InvoiceDocumentNo = "InvoiceDocumentNo";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_I_BankStatement, Object> COLUMN_IsActive = new ModelColumn<>(I_I_BankStatement.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setISO_Code (@Nullable java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getISO_Code();

	ModelColumn<I_I_BankStatement, Object> COLUMN_ISO_Code = new ModelColumn<>(I_I_BankStatement.class, "ISO_Code", null);
	String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Umkehrung.
	 * This is a reversing transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsReversal (boolean IsReversal);

	/**
	 * Get Umkehrung.
	 * This is a reversing transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isReversal();

	ModelColumn<I_I_BankStatement, Object> COLUMN_IsReversal = new ModelColumn<>(I_I_BankStatement.class, "IsReversal", null);
	String COLUMNNAME_IsReversal = "IsReversal";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_I_BankStatement, Object> COLUMN_Line = new ModelColumn<>(I_I_BankStatement.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Positions-Beschreibung.
	 * Description of the Line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineDescription (@Nullable java.lang.String LineDescription);

	/**
	 * Get Positions-Beschreibung.
	 * Description of the Line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLineDescription();

	ModelColumn<I_I_BankStatement, Object> COLUMN_LineDescription = new ModelColumn<>(I_I_BankStatement.class, "LineDescription", null);
	String COLUMNNAME_LineDescription = "LineDescription";

	/**
	 * Set LineDescriptionExtra_1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineDescriptionExtra_1 (@Nullable java.lang.String LineDescriptionExtra_1);

	/**
	 * Get LineDescriptionExtra_1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLineDescriptionExtra_1();

	ModelColumn<I_I_BankStatement, Object> COLUMN_LineDescriptionExtra_1 = new ModelColumn<>(I_I_BankStatement.class, "LineDescriptionExtra_1", null);
	String COLUMNNAME_LineDescriptionExtra_1 = "LineDescriptionExtra_1";

	/**
	 * Set LineDescriptionExtra_2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineDescriptionExtra_2 (@Nullable java.lang.String LineDescriptionExtra_2);

	/**
	 * Get LineDescriptionExtra_2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLineDescriptionExtra_2();

	ModelColumn<I_I_BankStatement, Object> COLUMN_LineDescriptionExtra_2 = new ModelColumn<>(I_I_BankStatement.class, "LineDescriptionExtra_2", null);
	String COLUMNNAME_LineDescriptionExtra_2 = "LineDescriptionExtra_2";

	/**
	 * Set LineDescriptionExtra_3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineDescriptionExtra_3 (@Nullable java.lang.String LineDescriptionExtra_3);

	/**
	 * Get LineDescriptionExtra_3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLineDescriptionExtra_3();

	ModelColumn<I_I_BankStatement, Object> COLUMN_LineDescriptionExtra_3 = new ModelColumn<>(I_I_BankStatement.class, "LineDescriptionExtra_3", null);
	String COLUMNNAME_LineDescriptionExtra_3 = "LineDescriptionExtra_3";

	/**
	 * Set LineDescriptionExtra_4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineDescriptionExtra_4 (@Nullable java.lang.String LineDescriptionExtra_4);

	/**
	 * Get LineDescriptionExtra_4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLineDescriptionExtra_4();

	ModelColumn<I_I_BankStatement, Object> COLUMN_LineDescriptionExtra_4 = new ModelColumn<>(I_I_BankStatement.class, "LineDescriptionExtra_4", null);
	String COLUMNNAME_LineDescriptionExtra_4 = "LineDescriptionExtra_4";

	/**
	 * Set Match Statement.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMatchStatement (@Nullable java.lang.String MatchStatement);

	/**
	 * Get Match Statement.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMatchStatement();

	ModelColumn<I_I_BankStatement, Object> COLUMN_MatchStatement = new ModelColumn<>(I_I_BankStatement.class, "MatchStatement", null);
	String COLUMNNAME_MatchStatement = "MatchStatement";

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

	ModelColumn<I_I_BankStatement, Object> COLUMN_Memo = new ModelColumn<>(I_I_BankStatement.class, "Memo", null);
	String COLUMNNAME_Memo = "Memo";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_I_BankStatement, Object> COLUMN_Name = new ModelColumn<>(I_I_BankStatement.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Payment Document No.
	 * Document number of the Payment
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentDocumentNo (@Nullable java.lang.String PaymentDocumentNo);

	/**
	 * Get Payment Document No.
	 * Document number of the Payment
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPaymentDocumentNo();

	ModelColumn<I_I_BankStatement, Object> COLUMN_PaymentDocumentNo = new ModelColumn<>(I_I_BankStatement.class, "PaymentDocumentNo", null);
	String COLUMNNAME_PaymentDocumentNo = "PaymentDocumentNo";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_I_BankStatement, Object> COLUMN_Processed = new ModelColumn<>(I_I_BankStatement.class, "Processed", null);
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

	ModelColumn<I_I_BankStatement, Object> COLUMN_Processing = new ModelColumn<>(I_I_BankStatement.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Referenznummer.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReferenceNo (@Nullable java.lang.String ReferenceNo);

	/**
	 * Get Referenznummer.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReferenceNo();

	ModelColumn<I_I_BankStatement, Object> COLUMN_ReferenceNo = new ModelColumn<>(I_I_BankStatement.class, "ReferenceNo", null);
	String COLUMNNAME_ReferenceNo = "ReferenceNo";

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

	ModelColumn<I_I_BankStatement, Object> COLUMN_RoutingNo = new ModelColumn<>(I_I_BankStatement.class, "RoutingNo", null);
	String COLUMNNAME_RoutingNo = "RoutingNo";

	/**
	 * Set Auszugsdatum.
	 * Date of the statement
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStatementDate (@Nullable java.sql.Timestamp StatementDate);

	/**
	 * Get Auszugsdatum.
	 * Date of the statement
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getStatementDate();

	ModelColumn<I_I_BankStatement, Object> COLUMN_StatementDate = new ModelColumn<>(I_I_BankStatement.class, "StatementDate", null);
	String COLUMNNAME_StatementDate = "StatementDate";

	/**
	 * Set Valuta Datum.
	 * Valuta Datum
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStatementLineDate (@Nullable java.sql.Timestamp StatementLineDate);

	/**
	 * Get Valuta Datum.
	 * Valuta Datum
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getStatementLineDate();

	ModelColumn<I_I_BankStatement, Object> COLUMN_StatementLineDate = new ModelColumn<>(I_I_BankStatement.class, "StatementLineDate", null);
	String COLUMNNAME_StatementLineDate = "StatementLineDate";

	/**
	 * Set Statement amount.
	 * Kontoauszug Betrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStmtAmt (@Nullable BigDecimal StmtAmt);

	/**
	 * Get Statement amount.
	 * Kontoauszug Betrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getStmtAmt();

	ModelColumn<I_I_BankStatement, Object> COLUMN_StmtAmt = new ModelColumn<>(I_I_BankStatement.class, "StmtAmt", null);
	String COLUMNNAME_StmtAmt = "StmtAmt";

	/**
	 * Set Bewegungs-Betrag.
	 * Amount of a transaction
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTrxAmt (@Nullable BigDecimal TrxAmt);

	/**
	 * Get Bewegungs-Betrag.
	 * Amount of a transaction
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTrxAmt();

	ModelColumn<I_I_BankStatement, Object> COLUMN_TrxAmt = new ModelColumn<>(I_I_BankStatement.class, "TrxAmt", null);
	String COLUMNNAME_TrxAmt = "TrxAmt";

	/**
	 * Set Transaction Type.
	 * Type of credit card transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTrxType (@Nullable java.lang.String TrxType);

	/**
	 * Get Transaction Type.
	 * Type of credit card transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTrxType();

	ModelColumn<I_I_BankStatement, Object> COLUMN_TrxType = new ModelColumn<>(I_I_BankStatement.class, "TrxType", null);
	String COLUMNNAME_TrxType = "TrxType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_I_BankStatement, Object> COLUMN_Updated = new ModelColumn<>(I_I_BankStatement.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Effective date.
	 * Date when money is available
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValutaDate (@Nullable java.sql.Timestamp ValutaDate);

	/**
	 * Get Effective date.
	 * Date when money is available
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValutaDate();

	ModelColumn<I_I_BankStatement, Object> COLUMN_ValutaDate = new ModelColumn<>(I_I_BankStatement.class, "ValutaDate", null);
	String COLUMNNAME_ValutaDate = "ValutaDate";
}
