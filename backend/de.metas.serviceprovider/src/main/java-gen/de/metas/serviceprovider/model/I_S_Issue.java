package de.metas.serviceprovider.model;


/** Generated Interface for S_Issue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_S_Issue 
{

    /** TableName=S_Issue */
    public static final String Table_Name = "S_Issue";

    /** AD_Table_ID=541468 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Aggregated effort (H:mm).
	 * The time spent on the whole issue tree in H:mm format.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAggregatedEffort (java.lang.String AggregatedEffort);

	/**
	 * Get Aggregated effort (H:mm).
	 * The time spent on the whole issue tree in H:mm format.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAggregatedEffort();

    /** Column definition for AggregatedEffort */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_AggregatedEffort = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "AggregatedEffort", null);
    /** Column name AggregatedEffort */
    public static final String COLUMNNAME_AggregatedEffort = "AggregatedEffort";

	/**
	 * Set Budgeted.
	 * Budgeted or originally expected effort
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBudgetedEffort (java.math.BigDecimal BudgetedEffort);

	/**
	 * Get Budgeted.
	 * Budgeted or originally expected effort
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getBudgetedEffort();

    /** Column definition for BudgetedEffort */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_BudgetedEffort = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "BudgetedEffort", null);
    /** Column name BudgetedEffort */
    public static final String COLUMNNAME_BudgetedEffort = "BudgetedEffort";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Project_ID();

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Delivered date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveredDate (java.sql.Timestamp DeliveredDate);

	/**
	 * Get Delivered date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDeliveredDate();

    /** Column definition for DeliveredDate */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_DeliveredDate = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "DeliveredDate", null);
    /** Column name DeliveredDate */
    public static final String COLUMNNAME_DeliveredDate = "DeliveredDate";

	/**
	 * Set Delivery platform.
	 * Specifies the instances where the issue was deployed.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryPlatform (java.lang.String DeliveryPlatform);

	/**
	 * Get Delivery platform.
	 * Specifies the instances where the issue was deployed.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryPlatform();

    /** Column definition for DeliveryPlatform */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_DeliveryPlatform = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "DeliveryPlatform", null);
    /** Column name DeliveryPlatform */
    public static final String COLUMNNAME_DeliveryPlatform = "DeliveryPlatform";

	/**
	 * Set Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEffort_UOM_ID (int Effort_UOM_ID);

	/**
	 * Get Unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEffort_UOM_ID();

    /** Column name Effort_UOM_ID */
    public static final String COLUMNNAME_Effort_UOM_ID = "Effort_UOM_ID";

	/**
	 * Set Effort delivery platform.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setEffortDeliveryPlatform (java.lang.String EffortDeliveryPlatform);

	/**
	 * Get Effort delivery platform.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getEffortDeliveryPlatform();

    /** Column definition for EffortDeliveryPlatform */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_EffortDeliveryPlatform = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "EffortDeliveryPlatform", null);
    /** Column name EffortDeliveryPlatform */
    public static final String COLUMNNAME_EffortDeliveryPlatform = "EffortDeliveryPlatform";

	/**
	 * Set Estimated effort.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEstimatedEffort (java.math.BigDecimal EstimatedEffort);

	/**
	 * Get Estimated effort.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getEstimatedEffort();

    /** Column definition for EstimatedEffort */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_EstimatedEffort = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "EstimatedEffort", null);
    /** Column name EstimatedEffort */
    public static final String COLUMNNAME_EstimatedEffort = "EstimatedEffort";

	/**
	 * Set External issue no.
	 * External issue number ( e.g. github issue number )
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalIssueNo (java.math.BigDecimal ExternalIssueNo);

	/**
	 * Get External issue no.
	 * External issue number ( e.g. github issue number )
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getExternalIssueNo();

    /** Column definition for ExternalIssueNo */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_ExternalIssueNo = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "ExternalIssueNo", null);
    /** Column name ExternalIssueNo */
    public static final String COLUMNNAME_ExternalIssueNo = "ExternalIssueNo";

	/**
	 * Set hasInternalEffortIssue.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void sethasInternalEffortIssue (boolean hasInternalEffortIssue);

	/**
	 * Get hasInternalEffortIssue.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean ishasInternalEffortIssue();

    /** Column definition for hasInternalEffortIssue */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_hasInternalEffortIssue = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "hasInternalEffortIssue", null);
    /** Column name hasInternalEffortIssue */
    public static final String COLUMNNAME_hasInternalEffortIssue = "hasInternalEffortIssue";

	/**
	 * Set Internal assignee.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setInternal_Assignee_ID (int Internal_Assignee_ID);

	/**
	 * Get Internal assignee.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getInternal_Assignee_ID();

    /** Column name Internal_Assignee_ID */
    public static final String COLUMNNAME_Internal_Assignee_ID = "Internal_Assignee_ID";

	/**
	 * Set Internal budget.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setInternal_Budgeted (java.math.BigDecimal Internal_Budgeted);

	/**
	 * Get Internal budget.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getInternal_Budgeted();

    /** Column definition for Internal_Budgeted */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Internal_Budgeted = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Internal_Budgeted", null);
    /** Column name Internal_Budgeted */
    public static final String COLUMNNAME_Internal_Budgeted = "Internal_Budgeted";

	/**
	 * Set Internal due date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setInternal_DueDate (java.sql.Timestamp Internal_DueDate);

	/**
	 * Get Internal due date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.sql.Timestamp getInternal_DueDate();

    /** Column definition for Internal_DueDate */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Internal_DueDate = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Internal_DueDate", null);
    /** Column name Internal_DueDate */
    public static final String COLUMNNAME_Internal_DueDate = "Internal_DueDate";

	/**
	 * Set Internal effort issue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setInternal_Effort_S_Issue_ID (int Internal_Effort_S_Issue_ID);

	/**
	 * Get Internal effort issue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getInternal_Effort_S_Issue_ID();

	public de.metas.serviceprovider.model.I_S_Issue getInternal_Effort_S_Issue();

	@Deprecated
	public void setInternal_Effort_S_Issue(de.metas.serviceprovider.model.I_S_Issue Internal_Effort_S_Issue);

    /** Column definition for Internal_Effort_S_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_Issue> COLUMN_Internal_Effort_S_Issue_ID = new org.adempiere.model.ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_Issue>(I_S_Issue.class, "Internal_Effort_S_Issue_ID", de.metas.serviceprovider.model.I_S_Issue.class);
    /** Column name Internal_Effort_S_Issue_ID */
    public static final String COLUMNNAME_Internal_Effort_S_Issue_ID = "Internal_Effort_S_Issue_ID";

	/**
	 * Set Internal estimated effort.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setInternal_EstimatedEffort (java.math.BigDecimal Internal_EstimatedEffort);

	/**
	 * Get Internal estimated effort.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getInternal_EstimatedEffort();

    /** Column definition for Internal_EstimatedEffort */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Internal_EstimatedEffort = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Internal_EstimatedEffort", null);
    /** Column name Internal_EstimatedEffort */
    public static final String COLUMNNAME_Internal_EstimatedEffort = "Internal_EstimatedEffort";

	/**
	 * Set Internal planned UAT date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setInternal_PlannedUATDate (java.sql.Timestamp Internal_PlannedUATDate);

	/**
	 * Get Internal planned UAT date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.sql.Timestamp getInternal_PlannedUATDate();

    /** Column definition for Internal_PlannedUATDate */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Internal_PlannedUATDate = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Internal_PlannedUATDate", null);
    /** Column name Internal_PlannedUATDate */
    public static final String COLUMNNAME_Internal_PlannedUATDate = "Internal_PlannedUATDate";

	/**
	 * Set Internal processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setInternal_processed (boolean Internal_processed);

	/**
	 * Get Internal processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isInternal_processed();

    /** Column definition for Internal_processed */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Internal_processed = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Internal_processed", null);
    /** Column name Internal_processed */
    public static final String COLUMNNAME_Internal_processed = "Internal_processed";

	/**
	 * Set Internal rough estimation.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setInternal_RoughEstimation (java.math.BigDecimal Internal_RoughEstimation);

	/**
	 * Get Internal rough estimation.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getInternal_RoughEstimation();

    /** Column definition for Internal_RoughEstimation */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Internal_RoughEstimation = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Internal_RoughEstimation", null);
    /** Column name Internal_RoughEstimation */
    public static final String COLUMNNAME_Internal_RoughEstimation = "Internal_RoughEstimation";

	/**
	 * Set Internal milestone.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setInternal_S_Milestone_ID (int Internal_S_Milestone_ID);

	/**
	 * Get Internal milestone.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getInternal_S_Milestone_ID();

	public de.metas.serviceprovider.model.I_S_Milestone getInternal_S_Milestone();

	@Deprecated
	public void setInternal_S_Milestone(de.metas.serviceprovider.model.I_S_Milestone Internal_S_Milestone);

    /** Column definition for Internal_S_Milestone_ID */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_Milestone> COLUMN_Internal_S_Milestone_ID = new org.adempiere.model.ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_Milestone>(I_S_Issue.class, "Internal_S_Milestone_ID", de.metas.serviceprovider.model.I_S_Milestone.class);
    /** Column name Internal_S_Milestone_ID */
    public static final String COLUMNNAME_Internal_S_Milestone_ID = "Internal_S_Milestone_ID";

	/**
	 * Set Internal status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setInternal_status (java.lang.String Internal_status);

	/**
	 * Get Internal status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getInternal_status();

    /** Column definition for Internal_status */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Internal_status = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Internal_status", null);
    /** Column name Internal_status */
    public static final String COLUMNNAME_Internal_status = "Internal_status";

	/**
	 * Set Internal-Approved.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setInternalApproved (boolean InternalApproved);

	/**
	 * Get Internal-Approved.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isInternalApproved();

    /** Column definition for InternalApproved */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_InternalApproved = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "InternalApproved", null);
    /** Column name InternalApproved */
    public static final String COLUMNNAME_InternalApproved = "InternalApproved";

	/**
	 * Set Invoiceable effort.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoiceableEffort (java.math.BigDecimal InvoiceableEffort);

	/**
	 * Get Invoiceable effort.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getInvoiceableEffort();

    /** Column definition for InvoiceableEffort */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_InvoiceableEffort = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "InvoiceableEffort", null);
    /** Column name InvoiceableEffort */
    public static final String COLUMNNAME_InvoiceableEffort = "InvoiceableEffort";

	/**
	 * Set Invoice date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoicedDate (java.sql.Timestamp InvoicedDate);

	/**
	 * Get Invoice date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getInvoicedDate();

    /** Column definition for InvoicedDate */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_InvoicedDate = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "InvoicedDate", null);
    /** Column name InvoicedDate */
    public static final String COLUMNNAME_InvoicedDate = "InvoicedDate";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApproved (boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApproved();

    /** Column definition for IsApproved */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Effort issue.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsEffortIssue (boolean IsEffortIssue);

	/**
	 * Get Effort issue.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEffortIssue();

    /** Column definition for IsEffortIssue */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_IsEffortIssue = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "IsEffortIssue", null);
    /** Column name IsEffortIssue */
    public static final String COLUMNNAME_IsEffortIssue = "IsEffortIssue";

	/**
	 * Set Issue effort (H:mm).
	 * Time spent directly on this task in H:mm format.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIssueEffort (java.lang.String IssueEffort);

	/**
	 * Get Issue effort (H:mm).
	 * Time spent directly on this task in H:mm format.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIssueEffort();

    /** Column definition for IssueEffort */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_IssueEffort = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "IssueEffort", null);
    /** Column name IssueEffort */
    public static final String COLUMNNAME_IssueEffort = "IssueEffort";

	/**
	 * Set Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIssueType (java.lang.String IssueType);

	/**
	 * Get Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIssueType();

    /** Column definition for IssueType */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_IssueType = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "IssueType", null);
    /** Column name IssueType */
    public static final String COLUMNNAME_IssueType = "IssueType";

	/**
	 * Set Issue-URL.
	 * URL of the issue, e.g. on github
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIssueURL (java.lang.String IssueURL);

	/**
	 * Get Issue-URL.
	 * URL of the issue, e.g. on github
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIssueURL();

    /** Column definition for IssueURL */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_IssueURL = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "IssueURL", null);
    /** Column name IssueURL */
    public static final String COLUMNNAME_IssueURL = "IssueURL";

	/**
	 * Set Latest activity.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLatestActivity (java.sql.Timestamp LatestActivity);

	/**
	 * Get Latest activity.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLatestActivity();

    /** Column definition for LatestActivity */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_LatestActivity = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "LatestActivity", null);
    /** Column name LatestActivity */
    public static final String COLUMNNAME_LatestActivity = "LatestActivity";

	/**
	 * Set Latest activity on sub issues.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLatestActivityOnSubIssues (java.sql.Timestamp LatestActivityOnSubIssues);

	/**
	 * Get Latest activity on sub issues.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLatestActivityOnSubIssues();

    /** Column definition for LatestActivityOnSubIssues */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_LatestActivityOnSubIssues = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "LatestActivityOnSubIssues", null);
    /** Column name LatestActivityOnSubIssues */
    public static final String COLUMNNAME_LatestActivityOnSubIssues = "LatestActivityOnSubIssues";

	/**
	 * Set Due date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setMilestone_DueDate (java.sql.Timestamp Milestone_DueDate);

	/**
	 * Get Due date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.sql.Timestamp getMilestone_DueDate();

    /** Column definition for Milestone_DueDate */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Milestone_DueDate = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Milestone_DueDate", null);
    /** Column name Milestone_DueDate */
    public static final String COLUMNNAME_Milestone_DueDate = "Milestone_DueDate";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Planned UAT date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPlannedUATDate (java.sql.Timestamp PlannedUATDate);

	/**
	 * Get Planned UAT date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPlannedUATDate();

    /** Column definition for PlannedUATDate */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_PlannedUATDate = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "PlannedUATDate", null);
    /** Column name PlannedUATDate */
    public static final String COLUMNNAME_PlannedUATDate = "PlannedUATDate";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Processed date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessedDate (java.sql.Timestamp ProcessedDate);

	/**
	 * Get Processed date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getProcessedDate();

    /** Column definition for ProcessedDate */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_ProcessedDate = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "ProcessedDate", null);
    /** Column name ProcessedDate */
    public static final String COLUMNNAME_ProcessedDate = "ProcessedDate";

	/**
	 * Set Rough estimation.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRoughEstimation (java.math.BigDecimal RoughEstimation);

	/**
	 * Get Rough estimation.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getRoughEstimation();

    /** Column definition for RoughEstimation */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_RoughEstimation = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "RoughEstimation", null);
    /** Column name RoughEstimation */
    public static final String COLUMNNAME_RoughEstimation = "RoughEstimation";

	/**
	 * Set External project reference ID.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_ExternalProjectReference_ID (int S_ExternalProjectReference_ID);

	/**
	 * Get External project reference ID.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_ExternalProjectReference_ID();

	public de.metas.serviceprovider.model.I_S_ExternalProjectReference getS_ExternalProjectReference();

	public void setS_ExternalProjectReference(de.metas.serviceprovider.model.I_S_ExternalProjectReference S_ExternalProjectReference);

    /** Column definition for S_ExternalProjectReference_ID */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_ExternalProjectReference> COLUMN_S_ExternalProjectReference_ID = new org.adempiere.model.ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_ExternalProjectReference>(I_S_Issue.class, "S_ExternalProjectReference_ID", de.metas.serviceprovider.model.I_S_ExternalProjectReference.class);
    /** Column name S_ExternalProjectReference_ID */
    public static final String COLUMNNAME_S_ExternalProjectReference_ID = "S_ExternalProjectReference_ID";

	/**
	 * Set Issue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setS_Issue_ID (int S_Issue_ID);

	/**
	 * Get Issue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getS_Issue_ID();

    /** Column definition for S_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_S_Issue_ID = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "S_Issue_ID", null);
    /** Column name S_Issue_ID */
    public static final String COLUMNNAME_S_Issue_ID = "S_Issue_ID";

	/**
	 * Set Milestone.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_Milestone_ID (int S_Milestone_ID);

	/**
	 * Get Milestone.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_Milestone_ID();

	public de.metas.serviceprovider.model.I_S_Milestone getS_Milestone();

	public void setS_Milestone(de.metas.serviceprovider.model.I_S_Milestone S_Milestone);

    /** Column definition for S_Milestone_ID */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_Milestone> COLUMN_S_Milestone_ID = new org.adempiere.model.ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_Milestone>(I_S_Issue.class, "S_Milestone_ID", de.metas.serviceprovider.model.I_S_Milestone.class);
    /** Column name S_Milestone_ID */
    public static final String COLUMNNAME_S_Milestone_ID = "S_Milestone_ID";

	/**
	 * Set Parent issue ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_Parent_Issue_ID (int S_Parent_Issue_ID);

	/**
	 * Get Parent issue ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_Parent_Issue_ID();

	public de.metas.serviceprovider.model.I_S_Issue getS_Parent_Issue();

	public void setS_Parent_Issue(de.metas.serviceprovider.model.I_S_Issue S_Parent_Issue);

    /** Column definition for S_Parent_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_Issue> COLUMN_S_Parent_Issue_ID = new org.adempiere.model.ModelColumn<I_S_Issue, de.metas.serviceprovider.model.I_S_Issue>(I_S_Issue.class, "S_Parent_Issue_ID", de.metas.serviceprovider.model.I_S_Issue.class);
    /** Column name S_Parent_Issue_ID */
    public static final String COLUMNNAME_S_Parent_Issue_ID = "S_Parent_Issue_ID";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStatus();

    /** Column definition for Status */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Status = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Status", null);
    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_S_Issue, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_S_Issue, Object>(I_S_Issue.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
