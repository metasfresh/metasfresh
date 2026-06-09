package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Tax_Acct
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Tax_Acct 
{

	String Table_Name = "C_Tax_Acct";

//	/** AD_Table_ID=399 */
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

	ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_C_Tax_Acct.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Tax_Acct, Object> COLUMN_Created = new ModelColumn<>(I_C_Tax_Acct.class, "Created", null);
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
	 * Set C_Tax_Acct.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Tax_Acct_ID (int C_Tax_Acct_ID);

	/**
	 * Get C_Tax_Acct.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Tax_Acct_ID();

	ModelColumn<I_C_Tax_Acct, Object> COLUMN_C_Tax_Acct_ID = new ModelColumn<>(I_C_Tax_Acct.class, "C_Tax_Acct_ID", null);
	String COLUMNNAME_C_Tax_Acct_ID = "C_Tax_Acct_ID";

	/**
	 * Set Tax.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Tax.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Tax_ID();

	String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

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

	ModelColumn<I_C_Tax_Acct, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Tax_Acct.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_Credit_Acct = new ModelColumn<>(I_C_Tax_Acct.class, "T_Credit_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_Due_Acct = new ModelColumn<>(I_C_Tax_Acct.class, "T_Due_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_T_Due_Acct = "T_Due_Acct";

	/**
	 * Set Expense account.
	 * Tax-dependent account for booking expense
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setT_Expense_Acct (int T_Expense_Acct);

	/**
	 * Get Expense account.
	 * Tax-dependent account for booking expense
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getT_Expense_Acct();

	@Nullable org.compiere.model.I_C_ValidCombination getT_Expense_A();

	void setT_Expense_A(@Nullable org.compiere.model.I_C_ValidCombination T_Expense_A);

	ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_Expense_Acct = new ModelColumn<>(I_C_Tax_Acct.class, "T_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
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

	ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_Liability_Acct = new ModelColumn<>(I_C_Tax_Acct.class, "T_Liability_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_T_Liability_Acct = "T_Liability_Acct";

	/**
	 * Set Steuerkorrektur gewährte Skonti.
	 * Steuerabhängiges Konto zur Verbuchung gewährter Skonti
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setT_PayDiscount_Exp_Acct (int T_PayDiscount_Exp_Acct);

	/**
	 * Get Steuerkorrektur gewährte Skonti.
	 * Steuerabhängiges Konto zur Verbuchung gewährter Skonti
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getT_PayDiscount_Exp_Acct();

	@Nullable org.compiere.model.I_C_ValidCombination getT_PayDiscount_Exp_A();

	void setT_PayDiscount_Exp_A(@Nullable org.compiere.model.I_C_ValidCombination T_PayDiscount_Exp_A);

	ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_PayDiscount_Exp_Acct = new ModelColumn<>(I_C_Tax_Acct.class, "T_PayDiscount_Exp_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_T_PayDiscount_Exp_Acct = "T_PayDiscount_Exp_Acct";

	/**
	 * Set Steuerkorrektur erhaltene Skonti.
	 * Steuerabhängiges Konto zur Verbuchung erhaltener Skonti
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setT_PayDiscount_Rev_Acct (int T_PayDiscount_Rev_Acct);

	/**
	 * Get Steuerkorrektur erhaltene Skonti.
	 * Steuerabhängiges Konto zur Verbuchung erhaltener Skonti
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getT_PayDiscount_Rev_Acct();

	@Nullable org.compiere.model.I_C_ValidCombination getT_PayDiscount_Rev_A();

	void setT_PayDiscount_Rev_A(@Nullable org.compiere.model.I_C_ValidCombination T_PayDiscount_Rev_A);

	ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_PayDiscount_Rev_Acct = new ModelColumn<>(I_C_Tax_Acct.class, "T_PayDiscount_Rev_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_T_PayDiscount_Rev_Acct = "T_PayDiscount_Rev_Acct";

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

	ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_Receivables_Acct = new ModelColumn<>(I_C_Tax_Acct.class, "T_Receivables_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_T_Receivables_Acct = "T_Receivables_Acct";

	/**
	 * Set Revenue account.
	 * Steuerabhängiges Konto zur Verbuchung Erlöse
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setT_Revenue_Acct (int T_Revenue_Acct);

	/**
	 * Get Revenue account.
	 * Steuerabhängiges Konto zur Verbuchung Erlöse
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getT_Revenue_Acct();

	@Nullable org.compiere.model.I_C_ValidCombination getT_Revenue_A();

	void setT_Revenue_A(@Nullable org.compiere.model.I_C_ValidCombination T_Revenue_A);

	ModelColumn<I_C_Tax_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_T_Revenue_Acct = new ModelColumn<>(I_C_Tax_Acct.class, "T_Revenue_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_T_Revenue_Acct = "T_Revenue_Acct";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Tax_Acct, Object> COLUMN_Updated = new ModelColumn<>(I_C_Tax_Acct.class, "Updated", null);
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
