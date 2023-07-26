package de.metas.serviceprovider.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for S_Issue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_Issue 
{

	String Table_Name = "S_Issue";

//	/** AD_Table_ID=541468 */
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
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Aggregated effort (H:mm).
	 * The time spent on the whole issue tree in H:mm format.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAggregatedEffort (@Nullable java.lang.String AggregatedEffort);

	/**
	 * Get Aggregated effort (H:mm).
	 * The time spent on the whole issue tree in H:mm format.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAggregatedEffort();

	ModelColumn<I_S_Issue, Object> COLUMN_AggregatedEffort = new ModelColumn<>(I_S_Issue.class, "AggregatedEffort", null);
	String COLUMNNAME_AggregatedEffort = "AggregatedEffort";

	/**
	 * Set Budgeted.
	 * Budgeted or originally expected effort
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBudgetedEffort (@Nullable BigDecimal BudgetedEffort);

	/**
	 * Get Budgeted.
	 * Budgeted or originally expected effort
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getBudgetedEffort();

	ModelColumn<I_S_Issue, Object> COLUMN_BudgetedEffort = new ModelColumn<>(I_S_Issue.class, "BudgetedEffort", null);
	String COLUMNNAME_BudgetedEffort = "BudgetedEffort";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
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

	ModelColumn<I_S_Issue, Object> COLUMN_Created = new ModelColumn<>(I_S_Issue.class, "Created", null);
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
	 * Set Delivered date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveredDate (@Nullable java.sql.Timestamp DeliveredDate);

	/**
	 * Get Delivered date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDeliveredDate();

	ModelColumn<I_S_Issue, Object> COLUMN_DeliveredDate = new ModelColumn<>(I_S_Issue.class, "DeliveredDate", null);
	String COLUMNNAME_DeliveredDate = "DeliveredDate";

	/**
	 * Set Delivery platform.
	 * Specifies the instances where the issue was deployed.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryPlatform (@Nullable java.lang.String DeliveryPlatform);

	/**
	 * Get Delivery platform.
	 * Specifies the instances where the issue was deployed.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryPlatform();

	ModelColumn<I_S_Issue, Object> COLUMN_DeliveryPlatform = new ModelColumn<>(I_S_Issue.class, "DeliveryPlatform", null);
	String COLUMNNAME_DeliveryPlatform = "DeliveryPlatform";

	/**
	 * Set Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_S_Issue, Object> COLUMN_Description = new ModelColumn<>(I_S_Issue.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Effort delivery platform.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setEffortDeliveryPlatform (@Nullable java.lang.String EffortDeliveryPlatform);

	/**
	 * Get Effort delivery platform.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getEffortDeliveryPlatform();

	ModelColumn<I_S_Issue, Object> COLUMN_EffortDeliveryPlatform = new ModelColumn<>(I_S_Issue.class, "EffortDeliveryPlatform", null);
	String COLUMNNAME_EffortDeliveryPlatform = "EffortDeliveryPlatform";

	/**
	 * Set Unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEffort_UOM_ID (int Effort_UOM_ID);

	/**
	 * Get Unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEffort_UOM_ID();

	String COLUMNNAME_Effort_UOM_ID = "Effort_UOM_ID";

	/**
	 * Set Estimated effort.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEstimatedEffort (BigDecimal EstimatedEffort);

	/**
	 * Get Estimated effort.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getEstimatedEffort();

	ModelColumn<I_S_Issue, Object> COLUMN_EstimatedEffort = new ModelColumn<>(I_S_Issue.class, "EstimatedEffort", null);
	String COLUMNNAME_EstimatedEffort = "EstimatedEffort";

	/**
	 * Set External issue no.
	 * External issue number ( e.g. github issue number )
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalIssueNo (@Nullable BigDecimal ExternalIssueNo);

	/**
	 * Get External issue no.
	 * External issue number ( e.g. github issue number )
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getExternalIssueNo();

	ModelColumn<I_S_Issue, Object> COLUMN_ExternalIssueNo = new ModelColumn<>(I_S_Issue.class, "ExternalIssueNo", null);
	String COLUMNNAME_ExternalIssueNo = "ExternalIssueNo";

	/**
	 * Set hasInternalEffortIssue.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void sethasInternalEffortIssue (boolean hasInternalEffortIssue);

	/**
	 * Get hasInternalEffortIssue.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean ishasInternalEffortIssue();

	ModelColumn<I_S_Issue, Object> COLUMN_hasInternalEffortIssue = new ModelColumn<>(I_S_Issue.class, "hasInternalEffortIssue", null);
	String COLUMNNAME_hasInternalEffortIssue = "hasInternalEffortIssue";

	/**
	 * Set Internal-Approved.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInternalApproved (boolean InternalApproved);

	/**
	 * Get Internal-Approved.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isInternalApproved();

	ModelColumn<I_S_Issue, Object> COLUMN_InternalApproved = new ModelColumn<>(I_S_Issue.class, "InternalApproved", null);
	String COLUMNNAME_InternalApproved = "InternalApproved";

	/**
	 * Set Internal assignee.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInternal_Assignee_ID (int Internal_Assignee_ID);

	/**
	 * Get Internal assignee.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getInternal_Assignee_ID();

	String COLUMNNAME_Internal_Assignee_ID = "Internal_Assignee_ID";

	/**
	 * Set Internal budget.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInternal_Budgeted (@Nullable BigDecimal Internal_Budgeted);

	/**
	 * Get Internal budget.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	BigDecimal getInternal_Budgeted();

	ModelColumn<I_S_Issue, Object> COLUMN_Internal_Budgeted = new ModelColumn<>(I_S_Issue.class, "Internal_Budgeted", null);
	String COLUMNNAME_Internal_Budgeted = "Internal_Budgeted";

	/**
	 * Set Internal delivered date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInternalDeliveredDate (@Nullable java.sql.Timestamp InternalDeliveredDate);

	/**
	 * Get Internal delivered date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.sql.Timestamp getInternalDeliveredDate();

	ModelColumn<I_S_Issue, Object> COLUMN_InternalDeliveredDate = new ModelColumn<>(I_S_Issue.class, "InternalDeliveredDate", null);
	String COLUMNNAME_InternalDeliveredDate = "InternalDeliveredDate";

	/**
	 * Set Internal due date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInternal_DueDate (@Nullable java.sql.Timestamp Internal_DueDate);

	/**
	 * Get Internal due date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.sql.Timestamp getInternal_DueDate();

	ModelColumn<I_S_Issue, Object> COLUMN_Internal_DueDate = new ModelColumn<>(I_S_Issue.class, "Internal_DueDate", null);
	String COLUMNNAME_Internal_DueDate = "Internal_DueDate";

	/**
	 * Set Internal effort issue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInternal_Effort_S_Issue_ID (int Internal_Effort_S_Issue_ID);

	/**
	 * Get Internal effort issue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getInternal_Effort_S_Issue_ID();

	@Nullable de.metas.serviceprovider.model.I_S_Issue getInternal_Effort_S_Issue();

	@Deprecated
	void setInternal_Effort_S_Issue(@Nullable de.metas.serviceprovider.model.I_S_Issue Internal_Effort_S_Issue);

	ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_Issue> COLUMN_Internal_Effort_S_Issue_ID = new ModelColumn<>(I_S_Issue.class, "Internal_Effort_S_Issue_ID", de.metas.serviceprovider.model.I_S_Issue.class);
	String COLUMNNAME_Internal_Effort_S_Issue_ID = "Internal_Effort_S_Issue_ID";

	/**
	 * Set Internal estimated effort.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInternal_EstimatedEffort (@Nullable BigDecimal Internal_EstimatedEffort);

	/**
	 * Get Internal estimated effort.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	BigDecimal getInternal_EstimatedEffort();

	ModelColumn<I_S_Issue, Object> COLUMN_Internal_EstimatedEffort = new ModelColumn<>(I_S_Issue.class, "Internal_EstimatedEffort", null);
	String COLUMNNAME_Internal_EstimatedEffort = "Internal_EstimatedEffort";

	/**
	 * Set Internal planned UAT date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInternal_PlannedUATDate (@Nullable java.sql.Timestamp Internal_PlannedUATDate);

	/**
	 * Get Internal planned UAT date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.sql.Timestamp getInternal_PlannedUATDate();

	ModelColumn<I_S_Issue, Object> COLUMN_Internal_PlannedUATDate = new ModelColumn<>(I_S_Issue.class, "Internal_PlannedUATDate", null);
	String COLUMNNAME_Internal_PlannedUATDate = "Internal_PlannedUATDate";

	/**
	 * Set Internal processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInternal_processed (boolean Internal_processed);

	/**
	 * Get Internal processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isInternal_processed();

	ModelColumn<I_S_Issue, Object> COLUMN_Internal_processed = new ModelColumn<>(I_S_Issue.class, "Internal_processed", null);
	String COLUMNNAME_Internal_processed = "Internal_processed";

	/**
	 * Set Internal rough estimation.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInternal_RoughEstimation (@Nullable BigDecimal Internal_RoughEstimation);

	/**
	 * Get Internal rough estimation.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	BigDecimal getInternal_RoughEstimation();

	ModelColumn<I_S_Issue, Object> COLUMN_Internal_RoughEstimation = new ModelColumn<>(I_S_Issue.class, "Internal_RoughEstimation", null);
	String COLUMNNAME_Internal_RoughEstimation = "Internal_RoughEstimation";

	/**
	 * Set Internal milestone.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInternal_S_Milestone_ID (int Internal_S_Milestone_ID);

	/**
	 * Get Internal milestone.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getInternal_S_Milestone_ID();

	@Nullable de.metas.serviceprovider.model.I_S_Milestone getInternal_S_Milestone();

	@Deprecated
	void setInternal_S_Milestone(@Nullable de.metas.serviceprovider.model.I_S_Milestone Internal_S_Milestone);

	ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_Milestone> COLUMN_Internal_S_Milestone_ID = new ModelColumn<>(I_S_Issue.class, "Internal_S_Milestone_ID", de.metas.serviceprovider.model.I_S_Milestone.class);
	String COLUMNNAME_Internal_S_Milestone_ID = "Internal_S_Milestone_ID";

	/**
	 * Set Internal status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInternal_status (@Nullable java.lang.String Internal_status);

	/**
	 * Get Internal status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getInternal_status();

	ModelColumn<I_S_Issue, Object> COLUMN_Internal_status = new ModelColumn<>(I_S_Issue.class, "Internal_status", null);
	String COLUMNNAME_Internal_status = "Internal_status";

	/**
	 * Set Invoiceable child efforts.
	 * Sum of invoicable efforts of all child issues
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceableChildEffort (@Nullable BigDecimal InvoiceableChildEffort);

	/**
	 * Get Invoiceable child efforts.
	 * Sum of invoicable efforts of all child issues
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoiceableChildEffort();

	ModelColumn<I_S_Issue, Object> COLUMN_InvoiceableChildEffort = new ModelColumn<>(I_S_Issue.class, "InvoiceableChildEffort", null);
	String COLUMNNAME_InvoiceableChildEffort = "InvoiceableChildEffort";

	/**
	 * Set Invoiceable effort.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceableEffort (@Nullable BigDecimal InvoiceableEffort);

	/**
	 * Get Invoiceable effort.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoiceableEffort();

	ModelColumn<I_S_Issue, Object> COLUMN_InvoiceableEffort = new ModelColumn<>(I_S_Issue.class, "InvoiceableEffort", null);
	String COLUMNNAME_InvoiceableEffort = "InvoiceableEffort";

	/**
	 * Set Invoice date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoicedDate (@Nullable java.sql.Timestamp InvoicedDate);

	/**
	 * Get Invoice date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getInvoicedDate();

	ModelColumn<I_S_Issue, Object> COLUMN_InvoicedDate = new ModelColumn<>(I_S_Issue.class, "InvoicedDate", null);
	String COLUMNNAME_InvoicedDate = "InvoicedDate";

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

	ModelColumn<I_S_Issue, Object> COLUMN_IsActive = new ModelColumn<>(I_S_Issue.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_S_Issue, Object> COLUMN_IsApproved = new ModelColumn<>(I_S_Issue.class, "IsApproved", null);
	String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Effort issue.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEffortIssue (boolean IsEffortIssue);

	/**
	 * Get Effort issue.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEffortIssue();

	ModelColumn<I_S_Issue, Object> COLUMN_IsEffortIssue = new ModelColumn<>(I_S_Issue.class, "IsEffortIssue", null);
	String COLUMNNAME_IsEffortIssue = "IsEffortIssue";

	/**
	 * Set Issue effort (H:mm).
	 * Time spent directly on this task in H:mm format.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssueEffort (@Nullable java.lang.String IssueEffort);

	/**
	 * Get Issue effort (H:mm).
	 * Time spent directly on this task in H:mm format.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIssueEffort();

	ModelColumn<I_S_Issue, Object> COLUMN_IssueEffort = new ModelColumn<>(I_S_Issue.class, "IssueEffort", null);
	String COLUMNNAME_IssueEffort = "IssueEffort";

	/**
	 * Set Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIssueType (java.lang.String IssueType);

	/**
	 * Get Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getIssueType();

	ModelColumn<I_S_Issue, Object> COLUMN_IssueType = new ModelColumn<>(I_S_Issue.class, "IssueType", null);
	String COLUMNNAME_IssueType = "IssueType";

	/**
	 * Set Issue-URL.
	 * URL of the issue, e.g. on github
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssueURL (@Nullable java.lang.String IssueURL);

	/**
	 * Get Issue-URL.
	 * URL of the issue, e.g. on github
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIssueURL();

	ModelColumn<I_S_Issue, Object> COLUMN_IssueURL = new ModelColumn<>(I_S_Issue.class, "IssueURL", null);
	String COLUMNNAME_IssueURL = "IssueURL";

	/**
	 * Set Latest activity.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLatestActivity (@Nullable java.sql.Timestamp LatestActivity);

	/**
	 * Get Latest activity.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getLatestActivity();

	ModelColumn<I_S_Issue, Object> COLUMN_LatestActivity = new ModelColumn<>(I_S_Issue.class, "LatestActivity", null);
	String COLUMNNAME_LatestActivity = "LatestActivity";

	/**
	 * Set Latest activity on sub issues.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLatestActivityOnSubIssues (@Nullable java.sql.Timestamp LatestActivityOnSubIssues);

	/**
	 * Get Latest activity on sub issues.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getLatestActivityOnSubIssues();

	ModelColumn<I_S_Issue, Object> COLUMN_LatestActivityOnSubIssues = new ModelColumn<>(I_S_Issue.class, "LatestActivityOnSubIssues", null);
	String COLUMNNAME_LatestActivityOnSubIssues = "LatestActivityOnSubIssues";

	/**
	 * Set Due date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setMilestone_DueDate (@Nullable java.sql.Timestamp Milestone_DueDate);

	/**
	 * Get Due date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.sql.Timestamp getMilestone_DueDate();

	ModelColumn<I_S_Issue, Object> COLUMN_Milestone_DueDate = new ModelColumn<>(I_S_Issue.class, "Milestone_DueDate", null);
	String COLUMNNAME_Milestone_DueDate = "Milestone_DueDate";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_S_Issue, Object> COLUMN_Name = new ModelColumn<>(I_S_Issue.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Planned UAT date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPlannedUATDate (@Nullable java.sql.Timestamp PlannedUATDate);

	/**
	 * Get Planned UAT date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPlannedUATDate();

	ModelColumn<I_S_Issue, Object> COLUMN_PlannedUATDate = new ModelColumn<>(I_S_Issue.class, "PlannedUATDate", null);
	String COLUMNNAME_PlannedUATDate = "PlannedUATDate";

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

	ModelColumn<I_S_Issue, Object> COLUMN_Processed = new ModelColumn<>(I_S_Issue.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Processed date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessedDate (@Nullable java.sql.Timestamp ProcessedDate);

	/**
	 * Get Processed date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getProcessedDate();

	ModelColumn<I_S_Issue, Object> COLUMN_ProcessedDate = new ModelColumn<>(I_S_Issue.class, "ProcessedDate", null);
	String COLUMNNAME_ProcessedDate = "ProcessedDate";

	/**
	 * Set Rough estimation.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRoughEstimation (@Nullable BigDecimal RoughEstimation);

	/**
	 * Get Rough estimation.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getRoughEstimation();

	ModelColumn<I_S_Issue, Object> COLUMN_RoughEstimation = new ModelColumn<>(I_S_Issue.class, "RoughEstimation", null);
	String COLUMNNAME_RoughEstimation = "RoughEstimation";

	/**
	 * Set External project reference ID.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_ExternalProjectReference_ID (int S_ExternalProjectReference_ID);

	/**
	 * Get External project reference ID.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_ExternalProjectReference_ID();

	@Nullable de.metas.serviceprovider.model.I_S_ExternalProjectReference getS_ExternalProjectReference();

	void setS_ExternalProjectReference(@Nullable de.metas.serviceprovider.model.I_S_ExternalProjectReference S_ExternalProjectReference);

	ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_ExternalProjectReference> COLUMN_S_ExternalProjectReference_ID = new ModelColumn<>(I_S_Issue.class, "S_ExternalProjectReference_ID", de.metas.serviceprovider.model.I_S_ExternalProjectReference.class);
	String COLUMNNAME_S_ExternalProjectReference_ID = "S_ExternalProjectReference_ID";

	/**
	 * Set Issue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_Issue_ID (int S_Issue_ID);

	/**
	 * Get Issue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_Issue_ID();

	ModelColumn<I_S_Issue, Object> COLUMN_S_Issue_ID = new ModelColumn<>(I_S_Issue.class, "S_Issue_ID", null);
	String COLUMNNAME_S_Issue_ID = "S_Issue_ID";

	/**
	 * Set Milestone.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_Milestone_ID (int S_Milestone_ID);

	/**
	 * Get Milestone.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_Milestone_ID();

	@Nullable de.metas.serviceprovider.model.I_S_Milestone getS_Milestone();

	void setS_Milestone(@Nullable de.metas.serviceprovider.model.I_S_Milestone S_Milestone);

	ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_Milestone> COLUMN_S_Milestone_ID = new ModelColumn<>(I_S_Issue.class, "S_Milestone_ID", de.metas.serviceprovider.model.I_S_Milestone.class);
	String COLUMNNAME_S_Milestone_ID = "S_Milestone_ID";

	/**
	 * Set Parent issue ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_Parent_Issue_ID (int S_Parent_Issue_ID);

	/**
	 * Get Parent issue ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_Parent_Issue_ID();

	@Nullable de.metas.serviceprovider.model.I_S_Issue getS_Parent_Issue();

	void setS_Parent_Issue(@Nullable de.metas.serviceprovider.model.I_S_Issue S_Parent_Issue);

	ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_Issue> COLUMN_S_Parent_Issue_ID = new ModelColumn<>(I_S_Issue.class, "S_Parent_Issue_ID", de.metas.serviceprovider.model.I_S_Issue.class);
	String COLUMNNAME_S_Parent_Issue_ID = "S_Parent_Issue_ID";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getStatus();

	ModelColumn<I_S_Issue, Object> COLUMN_Status = new ModelColumn<>(I_S_Issue.class, "Status", null);
	String COLUMNNAME_Status = "Status";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_S_Issue, Object> COLUMN_Updated = new ModelColumn<>(I_S_Issue.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_S_Issue, Object> COLUMN_Value = new ModelColumn<>(I_S_Issue.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
