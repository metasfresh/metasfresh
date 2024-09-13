package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_AcctSchema_GL
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_AcctSchema_GL 
{

	String Table_Name = "C_AcctSchema_GL";

//	/** AD_Table_ID=266 */
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
	 * Set C_AcctSchema_GL.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_GL_ID (int C_AcctSchema_GL_ID);

	/**
	 * Get C_AcctSchema_GL.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_GL_ID();

	ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_C_AcctSchema_GL_ID = new ModelColumn<>(I_C_AcctSchema_GL.class, "C_AcctSchema_GL_ID", null);
	String COLUMNNAME_C_AcctSchema_GL_ID = "C_AcctSchema_GL_ID";

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

	ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_C_AcctSchema_GL.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Cash Rounding Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCashRounding_Acct (int CashRounding_Acct);

	/**
	 * Get Cash Rounding Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCashRounding_Acct();

	@Nullable org.compiere.model.I_C_ValidCombination getCashRounding_A();

	void setCashRounding_A(@Nullable org.compiere.model.I_C_ValidCombination CashRounding_A);

	ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_CashRounding_Acct = new ModelColumn<>(I_C_AcctSchema_GL.class, "CashRounding_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_CashRounding_Acct = "CashRounding_Acct";

	/**
	 * Set Commitment Offset.
	 * Budgetary Commitment Offset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommitmentOffset_Acct (int CommitmentOffset_Acct);

	/**
	 * Get Commitment Offset.
	 * Budgetary Commitment Offset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCommitmentOffset_Acct();

	org.compiere.model.I_C_ValidCombination getCommitmentOffset_A();

	void setCommitmentOffset_A(org.compiere.model.I_C_ValidCombination CommitmentOffset_A);

	ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_CommitmentOffset_Acct = new ModelColumn<>(I_C_AcctSchema_GL.class, "CommitmentOffset_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_CommitmentOffset_Acct = "CommitmentOffset_Acct";

	/**
	 * Set Commitment Offset Sales.
	 * Budgetary Commitment Offset Account for Sales
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommitmentOffsetSales_Acct (int CommitmentOffsetSales_Acct);

	/**
	 * Get Commitment Offset Sales.
	 * Budgetary Commitment Offset Account for Sales
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCommitmentOffsetSales_Acct();

	org.compiere.model.I_C_ValidCombination getCommitmentOffsetSales_A();

	void setCommitmentOffsetSales_A(org.compiere.model.I_C_ValidCombination CommitmentOffsetSales_A);

	ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_CommitmentOffsetSales_Acct = new ModelColumn<>(I_C_AcctSchema_GL.class, "CommitmentOffsetSales_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_CommitmentOffsetSales_Acct = "CommitmentOffsetSales_Acct";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_Created = new ModelColumn<>(I_C_AcctSchema_GL.class, "Created", null);
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
	 * Set Currency Balancing Acct.
	 * Account used when a currency is out of balance
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrencyBalancing_Acct (int CurrencyBalancing_Acct);

	/**
	 * Get Currency Balancing Acct.
	 * Account used when a currency is out of balance
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCurrencyBalancing_Acct();

	@Nullable org.compiere.model.I_C_ValidCombination getCurrencyBalancing_A();

	void setCurrencyBalancing_A(@Nullable org.compiere.model.I_C_ValidCombination CurrencyBalancing_A);

	ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_CurrencyBalancing_Acct = new ModelColumn<>(I_C_AcctSchema_GL.class, "CurrencyBalancing_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_CurrencyBalancing_Acct = "CurrencyBalancing_Acct";

	/**
	 * Set Income Summary Acct.
	 * Income Summary Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIncomeSummary_Acct (int IncomeSummary_Acct);

	/**
	 * Get Income Summary Acct.
	 * Income Summary Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getIncomeSummary_Acct();

	org.compiere.model.I_C_ValidCombination getIncomeSummary_A();

	void setIncomeSummary_A(org.compiere.model.I_C_ValidCombination IncomeSummary_A);

	ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_IncomeSummary_Acct = new ModelColumn<>(I_C_AcctSchema_GL.class, "IncomeSummary_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_IncomeSummary_Acct = "IncomeSummary_Acct";

	/**
	 * Set Intercompany Due From Acct.
	 * Intercompany Due From / Receivables Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIntercompanyDueFrom_Acct (int IntercompanyDueFrom_Acct);

	/**
	 * Get Intercompany Due From Acct.
	 * Intercompany Due From / Receivables Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getIntercompanyDueFrom_Acct();

	org.compiere.model.I_C_ValidCombination getIntercompanyDueFrom_A();

	void setIntercompanyDueFrom_A(org.compiere.model.I_C_ValidCombination IntercompanyDueFrom_A);

	ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_IntercompanyDueFrom_Acct = new ModelColumn<>(I_C_AcctSchema_GL.class, "IntercompanyDueFrom_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_IntercompanyDueFrom_Acct = "IntercompanyDueFrom_Acct";

	/**
	 * Set Intercompany Due To Acct.
	 * Intercompany Due To / Payable Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIntercompanyDueTo_Acct (int IntercompanyDueTo_Acct);

	/**
	 * Get Intercompany Due To Acct.
	 * Intercompany Due To / Payable Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getIntercompanyDueTo_Acct();

	org.compiere.model.I_C_ValidCombination getIntercompanyDueTo_A();

	void setIntercompanyDueTo_A(org.compiere.model.I_C_ValidCombination IntercompanyDueTo_A);

	ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_IntercompanyDueTo_Acct = new ModelColumn<>(I_C_AcctSchema_GL.class, "IntercompanyDueTo_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_IntercompanyDueTo_Acct = "IntercompanyDueTo_Acct";

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

	ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_IsActive = new ModelColumn<>(I_C_AcctSchema_GL.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PPV Offset.
	 * Purchase Price Variance Offset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPPVOffset_Acct (int PPVOffset_Acct);

	/**
	 * Get PPV Offset.
	 * Purchase Price Variance Offset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPPVOffset_Acct();

	org.compiere.model.I_C_ValidCombination getPPVOffset_A();

	void setPPVOffset_A(org.compiere.model.I_C_ValidCombination PPVOffset_A);

	ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_PPVOffset_Acct = new ModelColumn<>(I_C_AcctSchema_GL.class, "PPVOffset_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_PPVOffset_Acct = "PPVOffset_Acct";

	/**
	 * Set Retained Earning Acct.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRetainedEarning_Acct (int RetainedEarning_Acct);

	/**
	 * Get Retained Earning Acct.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRetainedEarning_Acct();

	org.compiere.model.I_C_ValidCombination getRetainedEarning_A();

	void setRetainedEarning_A(org.compiere.model.I_C_ValidCombination RetainedEarning_A);

	ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_RetainedEarning_Acct = new ModelColumn<>(I_C_AcctSchema_GL.class, "RetainedEarning_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_RetainedEarning_Acct = "RetainedEarning_Acct";

	/**
	 * Set Suspense Balancing Acct.
	 * CpD-Konto (Conto-pro-Diverse) für doppelte Buchführung bei manuellen Buchungen.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSuspenseBalancing_Acct (int SuspenseBalancing_Acct);

	/**
	 * Get Suspense Balancing Acct.
	 * CpD-Konto (Conto-pro-Diverse) für doppelte Buchführung bei manuellen Buchungen.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSuspenseBalancing_Acct();

	@Nullable org.compiere.model.I_C_ValidCombination getSuspenseBalancing_A();

	void setSuspenseBalancing_A(@Nullable org.compiere.model.I_C_ValidCombination SuspenseBalancing_A);

	ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_SuspenseBalancing_Acct = new ModelColumn<>(I_C_AcctSchema_GL.class, "SuspenseBalancing_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_SuspenseBalancing_Acct = "SuspenseBalancing_Acct";

	/**
	 * Set Suspense Error Acct.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSuspenseError_Acct (int SuspenseError_Acct);

	/**
	 * Get Suspense Error Acct.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSuspenseError_Acct();

	@Nullable org.compiere.model.I_C_ValidCombination getSuspenseError_A();

	void setSuspenseError_A(@Nullable org.compiere.model.I_C_ValidCombination SuspenseError_A);

	ModelColumn<I_C_AcctSchema_GL, org.compiere.model.I_C_ValidCombination> COLUMN_SuspenseError_Acct = new ModelColumn<>(I_C_AcctSchema_GL.class, "SuspenseError_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_SuspenseError_Acct = "SuspenseError_Acct";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_Updated = new ModelColumn<>(I_C_AcctSchema_GL.class, "Updated", null);
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
	 * Set Use Currency Balancing.
	 * Sollen Währungsdifferenzen verbucht werden?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUseCurrencyBalancing (boolean UseCurrencyBalancing);

	/**
	 * Get Use Currency Balancing.
	 * Sollen Währungsdifferenzen verbucht werden?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseCurrencyBalancing();

	ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_UseCurrencyBalancing = new ModelColumn<>(I_C_AcctSchema_GL.class, "UseCurrencyBalancing", null);
	String COLUMNNAME_UseCurrencyBalancing = "UseCurrencyBalancing";

	/**
	 * Set Use Suspense Balancing.
	 * Verwendet doppelte Buchführung über Zwischenkonto bei manuellen Buchungen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUseSuspenseBalancing (boolean UseSuspenseBalancing);

	/**
	 * Get Use Suspense Balancing.
	 * Verwendet doppelte Buchführung über Zwischenkonto bei manuellen Buchungen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseSuspenseBalancing();

	ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_UseSuspenseBalancing = new ModelColumn<>(I_C_AcctSchema_GL.class, "UseSuspenseBalancing", null);
	String COLUMNNAME_UseSuspenseBalancing = "UseSuspenseBalancing";

	/**
	 * Set Use Suspense Error.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUseSuspenseError (boolean UseSuspenseError);

	/**
	 * Get Use Suspense Error.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseSuspenseError();

	ModelColumn<I_C_AcctSchema_GL, Object> COLUMN_UseSuspenseError = new ModelColumn<>(I_C_AcctSchema_GL.class, "UseSuspenseError", null);
	String COLUMNNAME_UseSuspenseError = "UseSuspenseError";
}
