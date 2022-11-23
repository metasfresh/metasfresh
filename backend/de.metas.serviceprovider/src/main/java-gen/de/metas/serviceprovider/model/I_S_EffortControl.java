package de.metas.serviceprovider.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for S_EffortControl
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_EffortControl 
{

	String Table_Name = "S_EffortControl";

//	/** AD_Table_ID=542215 */
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
	 * Set Budget.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBudget (@Nullable BigDecimal Budget);

	/**
	 * Get Budget.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getBudget();

	ModelColumn<I_S_EffortControl, Object> COLUMN_Budget = new ModelColumn<>(I_S_EffortControl.class, "Budget", null);
	String COLUMNNAME_Budget = "Budget";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_S_EffortControl, Object> COLUMN_Created = new ModelColumn<>(I_S_EffortControl.class, "Created", null);
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
	 * Set Effort aggregation key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEffortAggregationKey (java.lang.String EffortAggregationKey);

	/**
	 * Get Effort aggregation key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEffortAggregationKey();

	ModelColumn<I_S_EffortControl, Object> COLUMN_EffortAggregationKey = new ModelColumn<>(I_S_EffortControl.class, "EffortAggregationKey", null);
	String COLUMNNAME_EffortAggregationKey = "EffortAggregationKey";

	/**
	 * Set Effort sum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEffortSum (@Nullable java.lang.String EffortSum);

	/**
	 * Get Effort sum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEffortSum();

	ModelColumn<I_S_EffortControl, Object> COLUMN_EffortSum = new ModelColumn<>(I_S_EffortControl.class, "EffortSum", null);
	String COLUMNNAME_EffortSum = "EffortSum";

	/**
	 * Set EffortSumSeconds.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEffortSumSeconds (@Nullable BigDecimal EffortSumSeconds);

	/**
	 * Get EffortSumSeconds.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getEffortSumSeconds();

	ModelColumn<I_S_EffortControl, Object> COLUMN_EffortSumSeconds = new ModelColumn<>(I_S_EffortControl.class, "EffortSumSeconds", null);
	String COLUMNNAME_EffortSumSeconds = "EffortSumSeconds";

	/**
	 * Set Invoiceable hours.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceableHours (@Nullable BigDecimal InvoiceableHours);

	/**
	 * Get Invoiceable hours.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoiceableHours();

	ModelColumn<I_S_EffortControl, Object> COLUMN_InvoiceableHours = new ModelColumn<>(I_S_EffortControl.class, "InvoiceableHours", null);
	String COLUMNNAME_InvoiceableHours = "InvoiceableHours";

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

	ModelColumn<I_S_EffortControl, Object> COLUMN_IsActive = new ModelColumn<>(I_S_EffortControl.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Issue closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsIssueClosed (boolean IsIssueClosed);

	/**
	 * Get Issue closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isIssueClosed();

	ModelColumn<I_S_EffortControl, Object> COLUMN_IsIssueClosed = new ModelColumn<>(I_S_EffortControl.class, "IsIssueClosed", null);
	String COLUMNNAME_IsIssueClosed = "IsIssueClosed";

	/**
	 * Set Over budget.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOverBudget (boolean IsOverBudget);

	/**
	 * Get Over budget.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverBudget();

	ModelColumn<I_S_EffortControl, Object> COLUMN_IsOverBudget = new ModelColumn<>(I_S_EffortControl.class, "IsOverBudget", null);
	String COLUMNNAME_IsOverBudget = "IsOverBudget";

	/**
	 * Set Pending effort sum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPendingEffortSum (@Nullable java.lang.String PendingEffortSum);

	/**
	 * Get Pending effort sum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPendingEffortSum();

	ModelColumn<I_S_EffortControl, Object> COLUMN_PendingEffortSum = new ModelColumn<>(I_S_EffortControl.class, "PendingEffortSum", null);
	String COLUMNNAME_PendingEffortSum = "PendingEffortSum";

	/**
	 * Set PendingEffortSumSeconds.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPendingEffortSumSeconds (@Nullable BigDecimal PendingEffortSumSeconds);

	/**
	 * Get PendingEffortSumSeconds.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPendingEffortSumSeconds();

	ModelColumn<I_S_EffortControl, Object> COLUMN_PendingEffortSumSeconds = new ModelColumn<>(I_S_EffortControl.class, "PendingEffortSumSeconds", null);
	String COLUMNNAME_PendingEffortSumSeconds = "PendingEffortSumSeconds";

	/**
	 * Set S_EffortControl.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_EffortControl_ID (int S_EffortControl_ID);

	/**
	 * Get S_EffortControl.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_EffortControl_ID();

	ModelColumn<I_S_EffortControl, Object> COLUMN_S_EffortControl_ID = new ModelColumn<>(I_S_EffortControl.class, "S_EffortControl_ID", null);
	String COLUMNNAME_S_EffortControl_ID = "S_EffortControl_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_S_EffortControl, Object> COLUMN_Updated = new ModelColumn<>(I_S_EffortControl.class, "Updated", null);
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
