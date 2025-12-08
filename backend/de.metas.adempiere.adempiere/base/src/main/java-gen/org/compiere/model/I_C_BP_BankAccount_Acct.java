package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_BP_BankAccount_Acct
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BP_BankAccount_Acct 
{

	String Table_Name = "C_BP_BankAccount_Acct";

//	/** AD_Table_ID=540643 */
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_B_Asset_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "B_Asset_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_B_Expense_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "B_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_B_InterestExp_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "B_InterestExp_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_B_InterestRev_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "B_InterestRev_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_B_InTransit_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "B_InTransit_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_B_PaymentSelect_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "B_PaymentSelect_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_B_RevaluationGain_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "B_RevaluationGain_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_B_RevaluationLoss_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "B_RevaluationLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_B_SettlementGain_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "B_SettlementGain_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_B_SettlementLoss_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "B_SettlementLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_B_UnallocatedCash_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "B_UnallocatedCash_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_B_Unidentified_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "B_Unidentified_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_B_Unidentified_Acct = "B_Unidentified_Acct";

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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set C_BP_BankAccount_Acct.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_Acct_ID (int C_BP_BankAccount_Acct_ID);

	/**
	 * Get C_BP_BankAccount_Acct.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_Acct_ID();

	ModelColumn<I_C_BP_BankAccount_Acct, Object> COLUMN_C_BP_BankAccount_Acct_ID = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "C_BP_BankAccount_Acct_ID", null);
	String COLUMNNAME_C_BP_BankAccount_Acct_ID = "C_BP_BankAccount_Acct_ID";

	/**
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_ID();

	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BP_BankAccount_Acct, Object> COLUMN_Created = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "Created", null);
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

	ModelColumn<I_C_BP_BankAccount_Acct, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_PayBankFee_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "PayBankFee_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_PayBankFee_Acct = "PayBankFee_Acct";

	/**
	 * Set Payment WriteOff Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayment_WriteOff_Acct (int Payment_WriteOff_Acct);

	/**
	 * Get Payment WriteOff Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPayment_WriteOff_Acct();

	@Nullable org.compiere.model.I_C_ValidCombination getPayment_WriteOff_A();

	void setPayment_WriteOff_A(@Nullable org.compiere.model.I_C_ValidCombination Payment_WriteOff_A);

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_Payment_WriteOff_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "Payment_WriteOff_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_Payment_WriteOff_Acct = "Payment_WriteOff_Acct";

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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_RealizedGain_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "RealizedGain_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_BP_BankAccount_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_RealizedLoss_Acct = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "RealizedLoss_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_RealizedLoss_Acct = "RealizedLoss_Acct";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BP_BankAccount_Acct, Object> COLUMN_Updated = new ModelColumn<>(I_C_BP_BankAccount_Acct.class, "Updated", null);
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
