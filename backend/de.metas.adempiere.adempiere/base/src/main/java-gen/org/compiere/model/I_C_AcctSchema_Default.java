package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_AcctSchema_Default
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_AcctSchema_Default 
{

	String Table_Name = "C_AcctSchema_Default";

//	/** AD_Table_ID=315 */
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
	 * Set Bank Asset.
	 * Bank Asset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setB_Asset_Acct (int B_Asset_Acct);

	/**
	 * Get Bank Asset.
	 * Bank Asset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getB_Asset_Acct();

	org.compiere.model.I_C_ValidCombination getB_Asset_A();

	void setB_Asset_A(org.compiere.model.I_C_ValidCombination B_Asset_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_Asset_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "B_Asset_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_Asset_Acct = "B_Asset_Acct";

	/**
	 * Set Bank Expense.
	 * Bank Expense Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setB_Expense_Acct (int B_Expense_Acct);

	/**
	 * Get Bank Expense.
	 * Bank Expense Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getB_Expense_Acct();

	org.compiere.model.I_C_ValidCombination getB_Expense_A();

	void setB_Expense_A(org.compiere.model.I_C_ValidCombination B_Expense_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_Expense_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "B_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_Expense_Acct = "B_Expense_Acct";

	/**
	 * Set Bank Interest Expense.
	 * Bank Interest Expense Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setB_InterestExp_Acct (int B_InterestExp_Acct);

	/**
	 * Get Bank Interest Expense.
	 * Bank Interest Expense Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getB_InterestExp_Acct();

	org.compiere.model.I_C_ValidCombination getB_InterestExp_A();

	void setB_InterestExp_A(org.compiere.model.I_C_ValidCombination B_InterestExp_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_InterestExp_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "B_InterestExp_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_InterestExp_Acct = "B_InterestExp_Acct";

	/**
	 * Set Bank Interest Revenue.
	 * Bank Interest Revenue Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setB_InterestRev_Acct (int B_InterestRev_Acct);

	/**
	 * Get Bank Interest Revenue.
	 * Bank Interest Revenue Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getB_InterestRev_Acct();

	org.compiere.model.I_C_ValidCombination getB_InterestRev_A();

	void setB_InterestRev_A(org.compiere.model.I_C_ValidCombination B_InterestRev_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_InterestRev_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "B_InterestRev_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_InterestRev_Acct = "B_InterestRev_Acct";

	/**
	 * Set Bank In Transit.
	 * Bank In Transit Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setB_InTransit_Acct (int B_InTransit_Acct);

	/**
	 * Get Bank In Transit.
	 * Bank In Transit Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getB_InTransit_Acct();

	org.compiere.model.I_C_ValidCombination getB_InTransit_A();

	void setB_InTransit_A(org.compiere.model.I_C_ValidCombination B_InTransit_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_InTransit_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "B_InTransit_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_InTransit_Acct = "B_InTransit_Acct";

	/**
	 * Set Payment Selection.
	 * AP Payment Selection Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setB_PaymentSelect_Acct (int B_PaymentSelect_Acct);

	/**
	 * Get Payment Selection.
	 * AP Payment Selection Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getB_PaymentSelect_Acct();

	org.compiere.model.I_C_ValidCombination getB_PaymentSelect_A();

	void setB_PaymentSelect_A(org.compiere.model.I_C_ValidCombination B_PaymentSelect_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_PaymentSelect_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "B_PaymentSelect_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_PaymentSelect_Acct = "B_PaymentSelect_Acct";

	/**
	 * Set Bank Revaluation Gain.
	 * Bank Revaluation Gain Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setB_RevaluationGain_Acct (int B_RevaluationGain_Acct);

	/**
	 * Get Bank Revaluation Gain.
	 * Bank Revaluation Gain Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getB_RevaluationGain_Acct();

	org.compiere.model.I_C_ValidCombination getB_RevaluationGain_A();

	void setB_RevaluationGain_A(org.compiere.model.I_C_ValidCombination B_RevaluationGain_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_RevaluationGain_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "B_RevaluationGain_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_RevaluationGain_Acct = "B_RevaluationGain_Acct";

	/**
	 * Set Bank Revaluation Loss.
	 * Bank Revaluation Loss Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setB_RevaluationLoss_Acct (int B_RevaluationLoss_Acct);

	/**
	 * Get Bank Revaluation Loss.
	 * Bank Revaluation Loss Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getB_RevaluationLoss_Acct();

	org.compiere.model.I_C_ValidCombination getB_RevaluationLoss_A();

	void setB_RevaluationLoss_A(org.compiere.model.I_C_ValidCombination B_RevaluationLoss_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_RevaluationLoss_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "B_RevaluationLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_RevaluationLoss_Acct = "B_RevaluationLoss_Acct";

	/**
	 * Set Bank Settlement Gain.
	 * Bank Settlement Gain Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setB_SettlementGain_Acct (int B_SettlementGain_Acct);

	/**
	 * Get Bank Settlement Gain.
	 * Bank Settlement Gain Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getB_SettlementGain_Acct();

	org.compiere.model.I_C_ValidCombination getB_SettlementGain_A();

	void setB_SettlementGain_A(org.compiere.model.I_C_ValidCombination B_SettlementGain_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_SettlementGain_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "B_SettlementGain_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_SettlementGain_Acct = "B_SettlementGain_Acct";

	/**
	 * Set Bank Settlement Loss.
	 * Bank Settlement Loss Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setB_SettlementLoss_Acct (int B_SettlementLoss_Acct);

	/**
	 * Get Bank Settlement Loss.
	 * Bank Settlement Loss Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getB_SettlementLoss_Acct();

	org.compiere.model.I_C_ValidCombination getB_SettlementLoss_A();

	void setB_SettlementLoss_A(org.compiere.model.I_C_ValidCombination B_SettlementLoss_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_SettlementLoss_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "B_SettlementLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_SettlementLoss_Acct = "B_SettlementLoss_Acct";

	/**
	 * Set Unallocated Cash.
	 * Unallocated Cash Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setB_UnallocatedCash_Acct (int B_UnallocatedCash_Acct);

	/**
	 * Get Unallocated Cash.
	 * Unallocated Cash Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getB_UnallocatedCash_Acct();

	org.compiere.model.I_C_ValidCombination getB_UnallocatedCash_A();

	void setB_UnallocatedCash_A(org.compiere.model.I_C_ValidCombination B_UnallocatedCash_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_UnallocatedCash_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "B_UnallocatedCash_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_UnallocatedCash_Acct = "B_UnallocatedCash_Acct";

	/**
	 * Set Bank Unidentified Receipts.
	 * Bank Unidentified Receipts Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setB_Unidentified_Acct (int B_Unidentified_Acct);

	/**
	 * Get Bank Unidentified Receipts.
	 * Bank Unidentified Receipts Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getB_Unidentified_Acct();

	org.compiere.model.I_C_ValidCombination getB_Unidentified_A();

	void setB_Unidentified_A(org.compiere.model.I_C_ValidCombination B_Unidentified_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_B_Unidentified_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "B_Unidentified_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_Unidentified_Acct = "B_Unidentified_Acct";

	/**
	 * Set C_AcctSchema_Default.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_Default_ID (int C_AcctSchema_Default_ID);

	/**
	 * Get C_AcctSchema_Default.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_Default_ID();

	ModelColumn<I_C_AcctSchema_Default, Object> COLUMN_C_AcctSchema_Default_ID = new ModelColumn<>(I_C_AcctSchema_Default.class, "C_AcctSchema_Default_ID", null);
	String COLUMNNAME_C_AcctSchema_Default_ID = "C_AcctSchema_Default_ID";

	/**
	 * Set Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_C_AcctSchema_Default.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Cash Book Asset.
	 * Cash Book Asset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCB_Asset_Acct (int CB_Asset_Acct);

	/**
	 * Get Cash Book Asset.
	 * Cash Book Asset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCB_Asset_Acct();

	org.compiere.model.I_C_ValidCombination getCB_Asset_A();

	void setCB_Asset_A(org.compiere.model.I_C_ValidCombination CB_Asset_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_CB_Asset_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "CB_Asset_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_CB_Asset_Acct = "CB_Asset_Acct";

	/**
	 * Set Cash Transfer.
	 * Cash Transfer Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCB_CashTransfer_Acct (int CB_CashTransfer_Acct);

	/**
	 * Get Cash Transfer.
	 * Cash Transfer Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCB_CashTransfer_Acct();

	org.compiere.model.I_C_ValidCombination getCB_CashTransfer_A();

	void setCB_CashTransfer_A(org.compiere.model.I_C_ValidCombination CB_CashTransfer_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_CB_CashTransfer_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "CB_CashTransfer_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_CB_CashTransfer_Acct = "CB_CashTransfer_Acct";

	/**
	 * Set Cash Book Differences.
	 * Cash Book Differences Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCB_Differences_Acct (int CB_Differences_Acct);

	/**
	 * Get Cash Book Differences.
	 * Cash Book Differences Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCB_Differences_Acct();

	org.compiere.model.I_C_ValidCombination getCB_Differences_A();

	void setCB_Differences_A(org.compiere.model.I_C_ValidCombination CB_Differences_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_CB_Differences_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "CB_Differences_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_CB_Differences_Acct = "CB_Differences_Acct";

	/**
	 * Set Cash Book Expense.
	 * Cash Book Expense Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCB_Expense_Acct (int CB_Expense_Acct);

	/**
	 * Get Cash Book Expense.
	 * Cash Book Expense Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCB_Expense_Acct();

	org.compiere.model.I_C_ValidCombination getCB_Expense_A();

	void setCB_Expense_A(org.compiere.model.I_C_ValidCombination CB_Expense_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_CB_Expense_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "CB_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_CB_Expense_Acct = "CB_Expense_Acct";

	/**
	 * Set Cash Book Receipt.
	 * Cash Book Receipts Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCB_Receipt_Acct (int CB_Receipt_Acct);

	/**
	 * Get Cash Book Receipt.
	 * Cash Book Receipts Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCB_Receipt_Acct();

	org.compiere.model.I_C_ValidCombination getCB_Receipt_A();

	void setCB_Receipt_A(org.compiere.model.I_C_ValidCombination CB_Receipt_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_CB_Receipt_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "CB_Receipt_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_CB_Receipt_Acct = "CB_Receipt_Acct";

	/**
	 * Set Charge Expense.
	 * Charge Expense Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCh_Expense_Acct (int Ch_Expense_Acct);

	/**
	 * Get Charge Expense.
	 * Charge Expense Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCh_Expense_Acct();

	org.compiere.model.I_C_ValidCombination getCh_Expense_A();

	void setCh_Expense_A(org.compiere.model.I_C_ValidCombination Ch_Expense_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_Ch_Expense_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "Ch_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_Ch_Expense_Acct = "Ch_Expense_Acct";

	/**
	 * Set Charge Revenue.
	 * Charge Revenue Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCh_Revenue_Acct (int Ch_Revenue_Acct);

	/**
	 * Get Charge Revenue.
	 * Charge Revenue Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCh_Revenue_Acct();

	org.compiere.model.I_C_ValidCombination getCh_Revenue_A();

	void setCh_Revenue_A(org.compiere.model.I_C_ValidCombination Ch_Revenue_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_Ch_Revenue_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "Ch_Revenue_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_Ch_Revenue_Acct = "Ch_Revenue_Acct";

	/**
	 * Set Prepayment Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Prepayment_Acct (int C_Prepayment_Acct);

	/**
	 * Get Prepayment Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Prepayment_Acct();

	org.compiere.model.I_C_ValidCombination getC_Prepayment_A();

	void setC_Prepayment_A(org.compiere.model.I_C_ValidCombination C_Prepayment_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_C_Prepayment_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "C_Prepayment_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_C_Prepayment_Acct = "C_Prepayment_Acct";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_AcctSchema_Default, Object> COLUMN_Created = new ModelColumn<>(I_C_AcctSchema_Default.class, "Created", null);
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
	 * Set Receivable Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Receivable_Acct (int C_Receivable_Acct);

	/**
	 * Get Receivable Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Receivable_Acct();

	org.compiere.model.I_C_ValidCombination getC_Receivable_A();

	void setC_Receivable_A(org.compiere.model.I_C_ValidCombination C_Receivable_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_C_Receivable_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "C_Receivable_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_C_Receivable_Acct = "C_Receivable_Acct";

	/**
	 * Set Receivable Services Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Receivable_Services_Acct (int C_Receivable_Services_Acct);

	/**
	 * Get Receivable Services Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Receivable_Services_Acct();

	org.compiere.model.I_C_ValidCombination getC_Receivable_Services_A();

	void setC_Receivable_Services_A(org.compiere.model.I_C_ValidCombination C_Receivable_Services_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_C_Receivable_Services_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "C_Receivable_Services_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_C_Receivable_Services_Acct = "C_Receivable_Services_Acct";

	/**
	 * Set Employee Expense.
	 * Account for Employee Expenses
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setE_Expense_Acct (int E_Expense_Acct);

	/**
	 * Get Employee Expense.
	 * Account for Employee Expenses
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getE_Expense_Acct();

	org.compiere.model.I_C_ValidCombination getE_Expense_A();

	void setE_Expense_A(org.compiere.model.I_C_ValidCombination E_Expense_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_E_Expense_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "E_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_E_Expense_Acct = "E_Expense_Acct";

	/**
	 * Set Employee Prepayment.
	 * Account for Employee Expense Prepayments
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setE_Prepayment_Acct (int E_Prepayment_Acct);

	/**
	 * Get Employee Prepayment.
	 * Account for Employee Expense Prepayments
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getE_Prepayment_Acct();

	org.compiere.model.I_C_ValidCombination getE_Prepayment_A();

	void setE_Prepayment_A(org.compiere.model.I_C_ValidCombination E_Prepayment_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_E_Prepayment_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "E_Prepayment_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_E_Prepayment_Acct = "E_Prepayment_Acct";

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

	ModelColumn<I_C_AcctSchema_Default, Object> COLUMN_IsActive = new ModelColumn<>(I_C_AcctSchema_Default.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Not Invoiced Receipts.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setNotInvoicedReceipts_Acct (int NotInvoicedReceipts_Acct);

	/**
	 * Get Not Invoiced Receipts.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getNotInvoicedReceipts_Acct();

	org.compiere.model.I_C_ValidCombination getNotInvoicedReceipts_A();

	void setNotInvoicedReceipts_A(org.compiere.model.I_C_ValidCombination NotInvoicedReceipts_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_NotInvoicedReceipts_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "NotInvoicedReceipts_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_NotInvoicedReceipts_Acct = "NotInvoicedReceipts_Acct";

	/**
	 * Set Not Invoices Receivables Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setNotInvoicedReceivables_Acct (int NotInvoicedReceivables_Acct);

	/**
	 * Get Not Invoices Receivables Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getNotInvoicedReceivables_Acct();

	org.compiere.model.I_C_ValidCombination getNotInvoicedReceivables_A();

	void setNotInvoicedReceivables_A(org.compiere.model.I_C_ValidCombination NotInvoicedReceivables_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_NotInvoicedReceivables_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "NotInvoicedReceivables_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_NotInvoicedReceivables_Acct = "NotInvoicedReceivables_Acct";

	/**
	 * Set Not Invoiced Revenue Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setNotInvoicedRevenue_Acct (int NotInvoicedRevenue_Acct);

	/**
	 * Get Not Invoiced Revenue Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getNotInvoicedRevenue_Acct();

	org.compiere.model.I_C_ValidCombination getNotInvoicedRevenue_A();

	void setNotInvoicedRevenue_A(org.compiere.model.I_C_ValidCombination NotInvoicedRevenue_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_NotInvoicedRevenue_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "NotInvoicedRevenue_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_NotInvoicedRevenue_Acct = "NotInvoicedRevenue_Acct";

	/**
	 * Set Product Asset.
	 * Account for Product Asset (Inventory)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Asset_Acct (int P_Asset_Acct);

	/**
	 * Get Product Asset.
	 * Account for Product Asset (Inventory)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Asset_Acct();

	org.compiere.model.I_C_ValidCombination getP_Asset_A();

	void setP_Asset_A(org.compiere.model.I_C_ValidCombination P_Asset_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Asset_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_Asset_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Asset_Acct = "P_Asset_Acct";

	/**
	 * Set Bank Fee Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPayBankFee_Acct (int PayBankFee_Acct);

	/**
	 * Get Bank Fee Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPayBankFee_Acct();

	org.compiere.model.I_C_ValidCombination getPayBankFee_A();

	void setPayBankFee_A(org.compiere.model.I_C_ValidCombination PayBankFee_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_PayBankFee_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "PayBankFee_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_PayBankFee_Acct = "PayBankFee_Acct";

	/**
	 * Set Payment Discount Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPayDiscount_Exp_Acct (int PayDiscount_Exp_Acct);

	/**
	 * Get Payment Discount Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPayDiscount_Exp_Acct();

	org.compiere.model.I_C_ValidCombination getPayDiscount_Exp_A();

	void setPayDiscount_Exp_A(org.compiere.model.I_C_ValidCombination PayDiscount_Exp_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_PayDiscount_Exp_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "PayDiscount_Exp_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_PayDiscount_Exp_Acct = "PayDiscount_Exp_Acct";

	/**
	 * Set Received Discount Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPayDiscount_Rev_Acct (int PayDiscount_Rev_Acct);

	/**
	 * Get Received Discount Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPayDiscount_Rev_Acct();

	org.compiere.model.I_C_ValidCombination getPayDiscount_Rev_A();

	void setPayDiscount_Rev_A(org.compiere.model.I_C_ValidCombination PayDiscount_Rev_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_PayDiscount_Rev_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "PayDiscount_Rev_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_PayDiscount_Rev_Acct = "PayDiscount_Rev_Acct";

	/**
	 * Set Burden.
	 * The Burden account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Burden_Acct (int P_Burden_Acct);

	/**
	 * Get Burden.
	 * The Burden account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Burden_Acct();

	org.compiere.model.I_C_ValidCombination getP_Burden_A();

	void setP_Burden_A(org.compiere.model.I_C_ValidCombination P_Burden_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Burden_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_Burden_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Burden_Acct = "P_Burden_Acct";

	/**
	 * Set Product COGS.
	 * Account for Cost of Goods Sold
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_COGS_Acct (int P_COGS_Acct);

	/**
	 * Get Product COGS.
	 * Account for Cost of Goods Sold
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_COGS_Acct();

	org.compiere.model.I_C_ValidCombination getP_COGS_A();

	void setP_COGS_A(org.compiere.model.I_C_ValidCombination P_COGS_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_COGS_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_COGS_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_COGS_Acct = "P_COGS_Acct";

	/**
	 * Set Cost Adjustment.
	 * Product Cost Adjustment Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_CostAdjustment_Acct (int P_CostAdjustment_Acct);

	/**
	 * Get Cost Adjustment.
	 * Product Cost Adjustment Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_CostAdjustment_Acct();

	org.compiere.model.I_C_ValidCombination getP_CostAdjustment_A();

	void setP_CostAdjustment_A(org.compiere.model.I_C_ValidCombination P_CostAdjustment_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_CostAdjustment_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_CostAdjustment_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_CostAdjustment_Acct = "P_CostAdjustment_Acct";

	/**
	 * Set Cost Clearing Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_CostClearing_Acct (int P_CostClearing_Acct);

	/**
	 * Get Cost Clearing Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_CostClearing_Acct();

	org.compiere.model.I_C_ValidCombination getP_CostClearing_A();

	void setP_CostClearing_A(org.compiere.model.I_C_ValidCombination P_CostClearing_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_CostClearing_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_CostClearing_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_CostClearing_Acct = "P_CostClearing_Acct";

	/**
	 * Set Cost Of Production.
	 * The Cost Of Production account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_CostOfProduction_Acct (int P_CostOfProduction_Acct);

	/**
	 * Get Cost Of Production.
	 * The Cost Of Production account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_CostOfProduction_Acct();

	org.compiere.model.I_C_ValidCombination getP_CostOfProduction_A();

	void setP_CostOfProduction_A(org.compiere.model.I_C_ValidCombination P_CostOfProduction_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_CostOfProduction_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_CostOfProduction_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_CostOfProduction_Acct = "P_CostOfProduction_Acct";

	/**
	 * Set Product Expense.
	 * Account for Product Expense
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Expense_Acct (int P_Expense_Acct);

	/**
	 * Get Product Expense.
	 * Account for Product Expense
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Expense_Acct();

	org.compiere.model.I_C_ValidCombination getP_Expense_A();

	void setP_Expense_A(org.compiere.model.I_C_ValidCombination P_Expense_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Expense_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Expense_Acct = "P_Expense_Acct";

	/**
	 * Set Externally Owned Stock.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_ExternallyOwnedStock_Acct (int P_ExternallyOwnedStock_Acct);

	/**
	 * Get Externally Owned Stock.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_ExternallyOwnedStock_Acct();

	org.compiere.model.I_C_ValidCombination getP_ExternallyOwnedStock_A();

	void setP_ExternallyOwnedStock_A(org.compiere.model.I_C_ValidCombination P_ExternallyOwnedStock_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_ExternallyOwnedStock_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_ExternallyOwnedStock_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_ExternallyOwnedStock_Acct = "P_ExternallyOwnedStock_Acct";

	/**
	 * Set Floor Stock.
	 * The Floor Stock account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_FloorStock_Acct (int P_FloorStock_Acct);

	/**
	 * Get Floor Stock.
	 * The Floor Stock account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_FloorStock_Acct();

	org.compiere.model.I_C_ValidCombination getP_FloorStock_A();

	void setP_FloorStock_A(org.compiere.model.I_C_ValidCombination P_FloorStock_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_FloorStock_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_FloorStock_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_FloorStock_Acct = "P_FloorStock_Acct";

	/**
	 * Set Inventory Clearing.
	 * Product Inventory Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_InventoryClearing_Acct (int P_InventoryClearing_Acct);

	/**
	 * Get Inventory Clearing.
	 * Product Inventory Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_InventoryClearing_Acct();

	org.compiere.model.I_C_ValidCombination getP_InventoryClearing_A();

	void setP_InventoryClearing_A(org.compiere.model.I_C_ValidCombination P_InventoryClearing_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_InventoryClearing_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_InventoryClearing_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_InventoryClearing_Acct = "P_InventoryClearing_Acct";

	/**
	 * Set Invoice Price Variance.
	 * Difference between Costs and Invoice Price (IPV)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_InvoicePriceVariance_Acct (int P_InvoicePriceVariance_Acct);

	/**
	 * Get Invoice Price Variance.
	 * Difference between Costs and Invoice Price (IPV)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_InvoicePriceVariance_Acct();

	org.compiere.model.I_C_ValidCombination getP_InvoicePriceVariance_A();

	void setP_InvoicePriceVariance_A(org.compiere.model.I_C_ValidCombination P_InvoicePriceVariance_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_InvoicePriceVariance_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_InvoicePriceVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_InvoicePriceVariance_Acct = "P_InvoicePriceVariance_Acct";

	/**
	 * Set Project Asset.
	 * Project Asset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPJ_Asset_Acct (int PJ_Asset_Acct);

	/**
	 * Get Project Asset.
	 * Project Asset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPJ_Asset_Acct();

	org.compiere.model.I_C_ValidCombination getPJ_Asset_A();

	void setPJ_Asset_A(org.compiere.model.I_C_ValidCombination PJ_Asset_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_PJ_Asset_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "PJ_Asset_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_PJ_Asset_Acct = "PJ_Asset_Acct";

	/**
	 * Set Work In Progress.
	 * Account for Work in Progress
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPJ_WIP_Acct (int PJ_WIP_Acct);

	/**
	 * Get Work In Progress.
	 * Account for Work in Progress
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPJ_WIP_Acct();

	org.compiere.model.I_C_ValidCombination getPJ_WIP_A();

	void setPJ_WIP_A(org.compiere.model.I_C_ValidCombination PJ_WIP_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_PJ_WIP_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "PJ_WIP_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_PJ_WIP_Acct = "PJ_WIP_Acct";

	/**
	 * Set Labor.
	 * The Labor account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Labor_Acct (int P_Labor_Acct);

	/**
	 * Get Labor.
	 * The Labor account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Labor_Acct();

	org.compiere.model.I_C_ValidCombination getP_Labor_A();

	void setP_Labor_A(org.compiere.model.I_C_ValidCombination P_Labor_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Labor_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_Labor_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Labor_Acct = "P_Labor_Acct";

	/**
	 * Set Method Change Variance.
	 * The Method Change Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_MethodChangeVariance_Acct (int P_MethodChangeVariance_Acct);

	/**
	 * Get Method Change Variance.
	 * The Method Change Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_MethodChangeVariance_Acct();

	org.compiere.model.I_C_ValidCombination getP_MethodChangeVariance_A();

	void setP_MethodChangeVariance_A(org.compiere.model.I_C_ValidCombination P_MethodChangeVariance_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_MethodChangeVariance_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_MethodChangeVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_MethodChangeVariance_Acct = "P_MethodChangeVariance_Acct";

	/**
	 * Set Mix Variance.
	 * The Mix Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_MixVariance_Acct (int P_MixVariance_Acct);

	/**
	 * Get Mix Variance.
	 * The Mix Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_MixVariance_Acct();

	org.compiere.model.I_C_ValidCombination getP_MixVariance_A();

	void setP_MixVariance_A(org.compiere.model.I_C_ValidCombination P_MixVariance_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_MixVariance_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_MixVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_MixVariance_Acct = "P_MixVariance_Acct";

	/**
	 * Set Outside Processing.
	 * The Outside Processing Account is the account used in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_OutsideProcessing_Acct (int P_OutsideProcessing_Acct);

	/**
	 * Get Outside Processing.
	 * The Outside Processing Account is the account used in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_OutsideProcessing_Acct();

	org.compiere.model.I_C_ValidCombination getP_OutsideProcessing_A();

	void setP_OutsideProcessing_A(org.compiere.model.I_C_ValidCombination P_OutsideProcessing_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_OutsideProcessing_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_OutsideProcessing_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_OutsideProcessing_Acct = "P_OutsideProcessing_Acct";

	/**
	 * Set Overhead.
	 * The Overhead account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Overhead_Acct (int P_Overhead_Acct);

	/**
	 * Get Overhead.
	 * The Overhead account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Overhead_Acct();

	org.compiere.model.I_C_ValidCombination getP_Overhead_A();

	void setP_Overhead_A(org.compiere.model.I_C_ValidCombination P_Overhead_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Overhead_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_Overhead_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Overhead_Acct = "P_Overhead_Acct";

	/**
	 * Set Purchase Price Variance.
	 * Difference between Standard Cost and Purchase Price (PPV)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_PurchasePriceVariance_Acct (int P_PurchasePriceVariance_Acct);

	/**
	 * Get Purchase Price Variance.
	 * Difference between Standard Cost and Purchase Price (PPV)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_PurchasePriceVariance_Acct();

	org.compiere.model.I_C_ValidCombination getP_PurchasePriceVariance_A();

	void setP_PurchasePriceVariance_A(org.compiere.model.I_C_ValidCombination P_PurchasePriceVariance_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_PurchasePriceVariance_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_PurchasePriceVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_PurchasePriceVariance_Acct = "P_PurchasePriceVariance_Acct";

	/**
	 * Set Rate Variance.
	 * The Rate Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_RateVariance_Acct (int P_RateVariance_Acct);

	/**
	 * Get Rate Variance.
	 * The Rate Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_RateVariance_Acct();

	org.compiere.model.I_C_ValidCombination getP_RateVariance_A();

	void setP_RateVariance_A(org.compiere.model.I_C_ValidCombination P_RateVariance_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_RateVariance_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_RateVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_RateVariance_Acct = "P_RateVariance_Acct";

	/**
	 * Set Product Revenue.
	 * Account for Product Revenue (Sales Account)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Revenue_Acct (int P_Revenue_Acct);

	/**
	 * Get Product Revenue.
	 * Account for Product Revenue (Sales Account)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Revenue_Acct();

	org.compiere.model.I_C_ValidCombination getP_Revenue_A();

	void setP_Revenue_A(org.compiere.model.I_C_ValidCombination P_Revenue_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Revenue_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_Revenue_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Revenue_Acct = "P_Revenue_Acct";

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

	ModelColumn<I_C_AcctSchema_Default, Object> COLUMN_Processing = new ModelColumn<>(I_C_AcctSchema_Default.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Scrap.
	 * The Scrap account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Scrap_Acct (int P_Scrap_Acct);

	/**
	 * Get Scrap.
	 * The Scrap account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Scrap_Acct();

	org.compiere.model.I_C_ValidCombination getP_Scrap_A();

	void setP_Scrap_A(org.compiere.model.I_C_ValidCombination P_Scrap_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_Scrap_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_Scrap_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Scrap_Acct = "P_Scrap_Acct";

	/**
	 * Set Trade Discount Granted.
	 * Trade Discount Granted Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_TradeDiscountGrant_Acct (int P_TradeDiscountGrant_Acct);

	/**
	 * Get Trade Discount Granted.
	 * Trade Discount Granted Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_TradeDiscountGrant_Acct();

	org.compiere.model.I_C_ValidCombination getP_TradeDiscountGrant_A();

	void setP_TradeDiscountGrant_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountGrant_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_TradeDiscountGrant_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_TradeDiscountGrant_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_TradeDiscountGrant_Acct = "P_TradeDiscountGrant_Acct";

	/**
	 * Set Trade Discount Received.
	 * Trade Discount Receivable Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_TradeDiscountRec_Acct (int P_TradeDiscountRec_Acct);

	/**
	 * Get Trade Discount Received.
	 * Trade Discount Receivable Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_TradeDiscountRec_Acct();

	org.compiere.model.I_C_ValidCombination getP_TradeDiscountRec_A();

	void setP_TradeDiscountRec_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountRec_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_TradeDiscountRec_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_TradeDiscountRec_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_TradeDiscountRec_Acct = "P_TradeDiscountRec_Acct";

	/**
	 * Set Usage Variance.
	 * The Usage Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_UsageVariance_Acct (int P_UsageVariance_Acct);

	/**
	 * Get Usage Variance.
	 * The Usage Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_UsageVariance_Acct();

	org.compiere.model.I_C_ValidCombination getP_UsageVariance_A();

	void setP_UsageVariance_A(org.compiere.model.I_C_ValidCombination P_UsageVariance_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_UsageVariance_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_UsageVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_UsageVariance_Acct = "P_UsageVariance_Acct";

	/**
	 * Set Work In Process.
	 * The Work in Process account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_WIP_Acct (int P_WIP_Acct);

	/**
	 * Get Work In Process.
	 * The Work in Process account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_WIP_Acct();

	org.compiere.model.I_C_ValidCombination getP_WIP_A();

	void setP_WIP_A(org.compiere.model.I_C_ValidCombination P_WIP_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_P_WIP_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "P_WIP_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_WIP_Acct = "P_WIP_Acct";

	/**
	 * Set Realized Gain Acct.
	 * Realized Gain Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRealizedGain_Acct (int RealizedGain_Acct);

	/**
	 * Get Realized Gain Acct.
	 * Realized Gain Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRealizedGain_Acct();

	org.compiere.model.I_C_ValidCombination getRealizedGain_A();

	void setRealizedGain_A(org.compiere.model.I_C_ValidCombination RealizedGain_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_RealizedGain_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "RealizedGain_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_RealizedGain_Acct = "RealizedGain_Acct";

	/**
	 * Set Realized Loss Acct.
	 * Realized Loss Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRealizedLoss_Acct (int RealizedLoss_Acct);

	/**
	 * Get Realized Loss Acct.
	 * Realized Loss Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRealizedLoss_Acct();

	org.compiere.model.I_C_ValidCombination getRealizedLoss_A();

	void setRealizedLoss_A(org.compiere.model.I_C_ValidCombination RealizedLoss_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_RealizedLoss_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "RealizedLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_RealizedLoss_Acct = "RealizedLoss_Acct";

	/**
	 * Set Tax Credit.
	 * Account for Tax you can reclaim
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setT_Credit_Acct (int T_Credit_Acct);

	/**
	 * Get Tax Credit.
	 * Account for Tax you can reclaim
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getT_Credit_Acct();

	org.compiere.model.I_C_ValidCombination getT_Credit_A();

	void setT_Credit_A(org.compiere.model.I_C_ValidCombination T_Credit_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_T_Credit_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "T_Credit_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_T_Credit_Acct = "T_Credit_Acct";

	/**
	 * Set Tax Due.
	 * Account for Tax you have to pay
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setT_Due_Acct (int T_Due_Acct);

	/**
	 * Get Tax Due.
	 * Account for Tax you have to pay
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getT_Due_Acct();

	org.compiere.model.I_C_ValidCombination getT_Due_A();

	void setT_Due_A(org.compiere.model.I_C_ValidCombination T_Due_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_T_Due_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "T_Due_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_T_Due_Acct = "T_Due_Acct";

	/**
	 * Set Tax Expense.
	 * Account for paid tax you cannot reclaim
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setT_Expense_Acct (int T_Expense_Acct);

	/**
	 * Get Tax Expense.
	 * Account for paid tax you cannot reclaim
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getT_Expense_Acct();

	org.compiere.model.I_C_ValidCombination getT_Expense_A();

	void setT_Expense_A(org.compiere.model.I_C_ValidCombination T_Expense_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_T_Expense_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "T_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_T_Expense_Acct = "T_Expense_Acct";

	/**
	 * Set Tax Liability.
	 * Account for Tax declaration liability
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setT_Liability_Acct (int T_Liability_Acct);

	/**
	 * Get Tax Liability.
	 * Account for Tax declaration liability
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getT_Liability_Acct();

	org.compiere.model.I_C_ValidCombination getT_Liability_A();

	void setT_Liability_A(org.compiere.model.I_C_ValidCombination T_Liability_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_T_Liability_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "T_Liability_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_T_Liability_Acct = "T_Liability_Acct";

	/**
	 * Set Tax Receivables.
	 * Account for Tax credit after tax declaration
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setT_Receivables_Acct (int T_Receivables_Acct);

	/**
	 * Get Tax Receivables.
	 * Account for Tax credit after tax declaration
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getT_Receivables_Acct();

	org.compiere.model.I_C_ValidCombination getT_Receivables_A();

	void setT_Receivables_A(org.compiere.model.I_C_ValidCombination T_Receivables_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_T_Receivables_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "T_Receivables_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_T_Receivables_Acct = "T_Receivables_Acct";

	/**
	 * Set Unearned Revenue Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUnEarnedRevenue_Acct (int UnEarnedRevenue_Acct);

	/**
	 * Get Unearned Revenue Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUnEarnedRevenue_Acct();

	org.compiere.model.I_C_ValidCombination getUnEarnedRevenue_A();

	void setUnEarnedRevenue_A(org.compiere.model.I_C_ValidCombination UnEarnedRevenue_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_UnEarnedRevenue_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "UnEarnedRevenue_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_UnEarnedRevenue_Acct = "UnEarnedRevenue_Acct";

	/**
	 * Set Unrealized Gain Acct.
	 * Unrealized Gain Account for currency revaluation
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUnrealizedGain_Acct (int UnrealizedGain_Acct);

	/**
	 * Get Unrealized Gain Acct.
	 * Unrealized Gain Account for currency revaluation
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUnrealizedGain_Acct();

	org.compiere.model.I_C_ValidCombination getUnrealizedGain_A();

	void setUnrealizedGain_A(org.compiere.model.I_C_ValidCombination UnrealizedGain_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_UnrealizedGain_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "UnrealizedGain_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_UnrealizedGain_Acct = "UnrealizedGain_Acct";

	/**
	 * Set Unrealized Loss Acct.
	 * Unrealized Loss Account for currency revaluation
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUnrealizedLoss_Acct (int UnrealizedLoss_Acct);

	/**
	 * Get Unrealized Loss Acct.
	 * Unrealized Loss Account for currency revaluation
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUnrealizedLoss_Acct();

	org.compiere.model.I_C_ValidCombination getUnrealizedLoss_A();

	void setUnrealizedLoss_A(org.compiere.model.I_C_ValidCombination UnrealizedLoss_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_UnrealizedLoss_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "UnrealizedLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_UnrealizedLoss_Acct = "UnrealizedLoss_Acct";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_AcctSchema_Default, Object> COLUMN_Updated = new ModelColumn<>(I_C_AcctSchema_Default.class, "Updated", null);
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
	 * Set Liability Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setV_Liability_Acct (int V_Liability_Acct);

	/**
	 * Get Liability Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getV_Liability_Acct();

	org.compiere.model.I_C_ValidCombination getV_Liability_A();

	void setV_Liability_A(org.compiere.model.I_C_ValidCombination V_Liability_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_V_Liability_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "V_Liability_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_V_Liability_Acct = "V_Liability_Acct";

	/**
	 * Set Services Liability Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setV_Liability_Services_Acct (int V_Liability_Services_Acct);

	/**
	 * Get Services Liability Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getV_Liability_Services_Acct();

	org.compiere.model.I_C_ValidCombination getV_Liability_Services_A();

	void setV_Liability_Services_A(org.compiere.model.I_C_ValidCombination V_Liability_Services_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_V_Liability_Services_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "V_Liability_Services_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_V_Liability_Services_Acct = "V_Liability_Services_Acct";

	/**
	 * Set Vendor Prepayment Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setV_Prepayment_Acct (int V_Prepayment_Acct);

	/**
	 * Get Vendor Prepayment Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getV_Prepayment_Acct();

	org.compiere.model.I_C_ValidCombination getV_Prepayment_A();

	void setV_Prepayment_A(org.compiere.model.I_C_ValidCombination V_Prepayment_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_V_Prepayment_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "V_Prepayment_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_V_Prepayment_Acct = "V_Prepayment_Acct";

	/**
	 * Set Differences Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setW_Differences_Acct (int W_Differences_Acct);

	/**
	 * Get Differences Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getW_Differences_Acct();

	org.compiere.model.I_C_ValidCombination getW_Differences_A();

	void setW_Differences_A(org.compiere.model.I_C_ValidCombination W_Differences_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_W_Differences_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "W_Differences_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_W_Differences_Acct = "W_Differences_Acct";

	/**
	 * Set Adjust Value Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setW_InvActualAdjust_Acct (int W_InvActualAdjust_Acct);

	/**
	 * Get Adjust Value Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getW_InvActualAdjust_Acct();

	org.compiere.model.I_C_ValidCombination getW_InvActualAdjust_A();

	void setW_InvActualAdjust_A(org.compiere.model.I_C_ValidCombination W_InvActualAdjust_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_W_InvActualAdjust_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "W_InvActualAdjust_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_W_InvActualAdjust_Acct = "W_InvActualAdjust_Acct";

	/**
	 * Set (Not Used).
	 * Warehouse Inventory Asset Account - Currently not used
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setW_Inventory_Acct (int W_Inventory_Acct);

	/**
	 * Get (Not Used).
	 * Warehouse Inventory Asset Account - Currently not used
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getW_Inventory_Acct();

	org.compiere.model.I_C_ValidCombination getW_Inventory_A();

	void setW_Inventory_A(org.compiere.model.I_C_ValidCombination W_Inventory_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_W_Inventory_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "W_Inventory_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_W_Inventory_Acct = "W_Inventory_Acct";

	/**
	 * Set Withholding.
	 * Account for Withholdings
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWithholding_Acct (int Withholding_Acct);

	/**
	 * Get Withholding.
	 * Account for Withholdings
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWithholding_Acct();

	org.compiere.model.I_C_ValidCombination getWithholding_A();

	void setWithholding_A(org.compiere.model.I_C_ValidCombination Withholding_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_Withholding_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "Withholding_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_Withholding_Acct = "Withholding_Acct";

	/**
	 * Set Revaluation Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setW_Revaluation_Acct (int W_Revaluation_Acct);

	/**
	 * Get Revaluation Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getW_Revaluation_Acct();

	org.compiere.model.I_C_ValidCombination getW_Revaluation_A();

	void setW_Revaluation_A(org.compiere.model.I_C_ValidCombination W_Revaluation_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_W_Revaluation_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "W_Revaluation_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_W_Revaluation_Acct = "W_Revaluation_Acct";

	/**
	 * Set Receiveables Writeoff Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWriteOff_Acct (int WriteOff_Acct);

	/**
	 * Get Receiveables Writeoff Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWriteOff_Acct();

	org.compiere.model.I_C_ValidCombination getWriteOff_A();

	void setWriteOff_A(org.compiere.model.I_C_ValidCombination WriteOff_A);

	ModelColumn<I_C_AcctSchema_Default, org.compiere.model.I_C_ValidCombination> COLUMN_WriteOff_Acct = new ModelColumn<>(I_C_AcctSchema_Default.class, "WriteOff_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_WriteOff_Acct = "WriteOff_Acct";
}
