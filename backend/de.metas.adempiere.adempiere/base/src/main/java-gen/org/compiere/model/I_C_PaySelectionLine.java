package org.compiere.model;

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

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_C_BankStatementLine_Ref_ID = new ModelColumn<>(I_C_PaySelectionLine.class, "C_BankStatementLine_Ref_ID", null);
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
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_PaySelectionLine.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

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
	 * Set Zahlung Anweisen.
	 * Zahlung Anweisen
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaySelection_ID (int C_PaySelection_ID);

	/**
	 * Get Zahlung Anweisen.
	 * Zahlung Anweisen
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaySelection_ID();

	org.compiere.model.I_C_PaySelection getC_PaySelection();

	void setC_PaySelection(org.compiere.model.I_C_PaySelection C_PaySelection);

	ModelColumn<I_C_PaySelectionLine, org.compiere.model.I_C_PaySelection> COLUMN_C_PaySelection_ID = new ModelColumn<>(I_C_PaySelectionLine.class, "C_PaySelection_ID", org.compiere.model.I_C_PaySelection.class);
	String COLUMNNAME_C_PaySelection_ID = "C_PaySelection_ID";

	/**
	 * Set Zahlungsauswahl- Position.
	 * Payment Selection Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaySelectionLine_ID (int C_PaySelectionLine_ID);

	/**
	 * Get Zahlungsauswahl- Position.
	 * Payment Selection Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaySelectionLine_ID();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_C_PaySelectionLine_ID = new ModelColumn<>(I_C_PaySelectionLine.class, "C_PaySelectionLine_ID", null);
	String COLUMNNAME_C_PaySelectionLine_ID = "C_PaySelectionLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Created = new ModelColumn<>(I_C_PaySelectionLine.class, "Created", null);
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

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Description = new ModelColumn<>(I_C_PaySelectionLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Differenz.
	 * Difference Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDifferenceAmt (BigDecimal DifferenceAmt);

	/**
	 * Get Differenz.
	 * Difference Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getDifferenceAmt();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_DifferenceAmt = new ModelColumn<>(I_C_PaySelectionLine.class, "DifferenceAmt", null);
	String COLUMNNAME_DifferenceAmt = "DifferenceAmt";

	/**
	 * Set Discount Amount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDiscountAmt (BigDecimal DiscountAmt);

	/**
	 * Get Discount Amount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscountAmt();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_DiscountAmt = new ModelColumn<>(I_C_PaySelectionLine.class, "DiscountAmt", null);
	String COLUMNNAME_DiscountAmt = "DiscountAmt";

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
	void setHasOpenCreditMemos (boolean HasOpenCreditMemos);

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

	/**
	 * Set Has Open Outgoing payments.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setHasOpenOutgoingPayments (boolean HasOpenOutgoingPayments);

	/**
	 * Get Has Open Outgoing payments.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isHasOpenOutgoingPayments();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_HasOpenOutgoingPayments = new ModelColumn<>(I_C_PaySelectionLine.class, "HasOpenOutgoingPayments", null);
	String COLUMNNAME_HasOpenOutgoingPayments = "HasOpenOutgoingPayments";

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

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_PaySelectionLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Manuell.
	 * This is a manual process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManual (boolean IsManual);

	/**
	 * Get Manuell.
	 * This is a manual process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManual();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_IsManual = new ModelColumn<>(I_C_PaySelectionLine.class, "IsManual", null);
	String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_C_PaySelectionLine.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

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

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Line = new ModelColumn<>(I_C_PaySelectionLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Open Amount.
	 * Open item amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOpenAmt (BigDecimal OpenAmt);

	/**
	 * Get Open Amount.
	 * Open item amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getOpenAmt();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_OpenAmt = new ModelColumn<>(I_C_PaySelectionLine.class, "OpenAmt", null);
	String COLUMNNAME_OpenAmt = "OpenAmt";

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

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_PayAmt = new ModelColumn<>(I_C_PaySelectionLine.class, "PayAmt", null);
	String COLUMNNAME_PayAmt = "PayAmt";

	/**
	 * Set Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPaymentRule();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_PaymentRule = new ModelColumn<>(I_C_PaySelectionLine.class, "PaymentRule", null);
	String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set Reference.
	 * Reference for this record
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReference (@Nullable java.lang.String Reference);

	/**
	 * Get Reference.
	 * Reference for this record
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReference();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Reference = new ModelColumn<>(I_C_PaySelectionLine.class, "Reference", null);
	String COLUMNNAME_Reference = "Reference";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_PaySelectionLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_PaySelectionLine.class, "Updated", null);
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
}
