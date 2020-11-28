/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_Issue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_Issue extends org.compiere.model.PO implements I_S_Issue, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -106251464L;

    /** Standard Constructor */
    public X_S_Issue (Properties ctx, int S_Issue_ID, String trxName)
    {
      super (ctx, S_Issue_ID, trxName);
    }

    /** Load Constructor */
    public X_S_Issue (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setAggregatedEffort (java.lang.String AggregatedEffort)
	{
		set_Value (COLUMNNAME_AggregatedEffort, AggregatedEffort);
	}

	@Override
	public java.lang.String getAggregatedEffort() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AggregatedEffort);
	}

	@Override
	public void setBudgetedEffort (java.math.BigDecimal BudgetedEffort)
	{
		set_Value (COLUMNNAME_BudgetedEffort, BudgetedEffort);
	}

	@Override
	public java.math.BigDecimal getBudgetedEffort() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_BudgetedEffort);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setDeliveredDate (java.sql.Timestamp DeliveredDate)
	{
		set_Value (COLUMNNAME_DeliveredDate, DeliveredDate);
	}

	@Override
	public java.sql.Timestamp getDeliveredDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveredDate);
	}

	@Override
	public void setDeliveryPlatform (java.lang.String DeliveryPlatform)
	{
		set_Value (COLUMNNAME_DeliveryPlatform, DeliveryPlatform);
	}

	@Override
	public java.lang.String getDeliveryPlatform() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryPlatform);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setEffort_UOM_ID (int Effort_UOM_ID)
	{
		if (Effort_UOM_ID < 1) 
			set_Value (COLUMNNAME_Effort_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Effort_UOM_ID, Integer.valueOf(Effort_UOM_ID));
	}

	@Override
	public int getEffort_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Effort_UOM_ID);
	}

	@Override
	public void setEffortDeliveryPlatform (java.lang.String EffortDeliveryPlatform)
	{
		throw new IllegalArgumentException ("EffortDeliveryPlatform is virtual column");	}

	@Override
	public java.lang.String getEffortDeliveryPlatform() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EffortDeliveryPlatform);
	}

	@Override
	public void setEstimatedEffort (java.math.BigDecimal EstimatedEffort)
	{
		set_ValueNoCheck (COLUMNNAME_EstimatedEffort, EstimatedEffort);
	}

	@Override
	public java.math.BigDecimal getEstimatedEffort() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_EstimatedEffort);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setExternalIssueNo (java.math.BigDecimal ExternalIssueNo)
	{
		set_Value (COLUMNNAME_ExternalIssueNo, ExternalIssueNo);
	}

	@Override
	public java.math.BigDecimal getExternalIssueNo() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ExternalIssueNo);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void sethasInternalEffortIssue (boolean hasInternalEffortIssue)
	{
		throw new IllegalArgumentException ("hasInternalEffortIssue is virtual column");	}

	@Override
	public boolean ishasInternalEffortIssue() 
	{
		return get_ValueAsBoolean(COLUMNNAME_hasInternalEffortIssue);
	}

	@Override
	public void setInternal_Assignee_ID (int Internal_Assignee_ID)
	{
		throw new IllegalArgumentException ("Internal_Assignee_ID is virtual column");	}

	@Override
	public int getInternal_Assignee_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Internal_Assignee_ID);
	}

	@Override
	public void setInternal_Budgeted (java.math.BigDecimal Internal_Budgeted)
	{
		throw new IllegalArgumentException ("Internal_Budgeted is virtual column");	}

	@Override
	public java.math.BigDecimal getInternal_Budgeted() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Internal_Budgeted);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInternal_DueDate (java.sql.Timestamp Internal_DueDate)
	{
		throw new IllegalArgumentException ("Internal_DueDate is virtual column");	}

	@Override
	public java.sql.Timestamp getInternal_DueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Internal_DueDate);
	}

	@Override
	public de.metas.serviceprovider.model.I_S_Issue getInternal_Effort_S_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_Internal_Effort_S_Issue_ID, de.metas.serviceprovider.model.I_S_Issue.class);
	}

	@Override
	public void setInternal_Effort_S_Issue(de.metas.serviceprovider.model.I_S_Issue Internal_Effort_S_Issue)
	{
		set_ValueFromPO(COLUMNNAME_Internal_Effort_S_Issue_ID, de.metas.serviceprovider.model.I_S_Issue.class, Internal_Effort_S_Issue);
	}

	@Override
	public void setInternal_Effort_S_Issue_ID (int Internal_Effort_S_Issue_ID)
	{
		throw new IllegalArgumentException ("Internal_Effort_S_Issue_ID is virtual column");	}

	@Override
	public int getInternal_Effort_S_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Internal_Effort_S_Issue_ID);
	}

	@Override
	public void setInternal_EstimatedEffort (java.math.BigDecimal Internal_EstimatedEffort)
	{
		throw new IllegalArgumentException ("Internal_EstimatedEffort is virtual column");	}

	@Override
	public java.math.BigDecimal getInternal_EstimatedEffort() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Internal_EstimatedEffort);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInternal_PlannedUATDate (java.sql.Timestamp Internal_PlannedUATDate)
	{
		throw new IllegalArgumentException ("Internal_PlannedUATDate is virtual column");	}

	@Override
	public java.sql.Timestamp getInternal_PlannedUATDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Internal_PlannedUATDate);
	}

	@Override
	public void setInternal_processed (boolean Internal_processed)
	{
		throw new IllegalArgumentException ("Internal_processed is virtual column");	}

	@Override
	public boolean isInternal_processed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Internal_processed);
	}

	@Override
	public void setInternal_RoughEstimation (java.math.BigDecimal Internal_RoughEstimation)
	{
		throw new IllegalArgumentException ("Internal_RoughEstimation is virtual column");	}

	@Override
	public java.math.BigDecimal getInternal_RoughEstimation() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Internal_RoughEstimation);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public de.metas.serviceprovider.model.I_S_Milestone getInternal_S_Milestone()
	{
		return get_ValueAsPO(COLUMNNAME_Internal_S_Milestone_ID, de.metas.serviceprovider.model.I_S_Milestone.class);
	}

	@Override
	public void setInternal_S_Milestone(de.metas.serviceprovider.model.I_S_Milestone Internal_S_Milestone)
	{
		set_ValueFromPO(COLUMNNAME_Internal_S_Milestone_ID, de.metas.serviceprovider.model.I_S_Milestone.class, Internal_S_Milestone);
	}

	@Override
	public void setInternal_S_Milestone_ID (int Internal_S_Milestone_ID)
	{
		throw new IllegalArgumentException ("Internal_S_Milestone_ID is virtual column");	}

	@Override
	public int getInternal_S_Milestone_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Internal_S_Milestone_ID);
	}

	/** 
	 * Internal_status AD_Reference_ID=541142
	 * Reference name: S_Issue_Status
	 */
	public static final int INTERNAL_STATUS_AD_Reference_ID=541142;
	/** In progress = InProgress */
	public static final String INTERNAL_STATUS_InProgress = "InProgress";
	/** Closed = Closed */
	public static final String INTERNAL_STATUS_Closed = "Closed";
	/** Pending = Pending */
	public static final String INTERNAL_STATUS_Pending = "Pending";
	/** Delivered = Delivered */
	public static final String INTERNAL_STATUS_Delivered = "Delivered";
	/** New = New */
	public static final String INTERNAL_STATUS_New = "New";
	/** Invoiced = Invoiced */
	public static final String INTERNAL_STATUS_Invoiced = "Invoiced";
	@Override
	public void setInternal_status (java.lang.String Internal_status)
	{

		throw new IllegalArgumentException ("Internal_status is virtual column");	}

	@Override
	public java.lang.String getInternal_status() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Internal_status);
	}

	@Override
	public void setInternalApproved (boolean InternalApproved)
	{
		throw new IllegalArgumentException ("InternalApproved is virtual column");	}

	@Override
	public boolean isInternalApproved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_InternalApproved);
	}

	@Override
	public void setInvoiceableEffort (java.math.BigDecimal InvoiceableEffort)
	{
		set_Value (COLUMNNAME_InvoiceableEffort, InvoiceableEffort);
	}

	@Override
	public java.math.BigDecimal getInvoiceableEffort() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InvoiceableEffort);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInvoicedDate (java.sql.Timestamp InvoicedDate)
	{
		set_Value (COLUMNNAME_InvoicedDate, InvoicedDate);
	}

	@Override
	public java.sql.Timestamp getInvoicedDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_InvoicedDate);
	}

	@Override
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	@Override
	public boolean isApproved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApproved);
	}

	@Override
	public void setIsEffortIssue (boolean IsEffortIssue)
	{
		set_Value (COLUMNNAME_IsEffortIssue, Boolean.valueOf(IsEffortIssue));
	}

	@Override
	public boolean isEffortIssue() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEffortIssue);
	}

	@Override
	public void setIssueEffort (java.lang.String IssueEffort)
	{
		set_Value (COLUMNNAME_IssueEffort, IssueEffort);
	}

	@Override
	public java.lang.String getIssueEffort() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IssueEffort);
	}

	/** 
	 * IssueType AD_Reference_ID=541105
	 * Reference name: Budget_Issue_Type
	 */
	public static final int ISSUETYPE_AD_Reference_ID=541105;
	/** Internal = Internal */
	public static final String ISSUETYPE_Internal = "Internal";
	/** External = External */
	public static final String ISSUETYPE_External = "External";
	@Override
	public void setIssueType (java.lang.String IssueType)
	{

		set_Value (COLUMNNAME_IssueType, IssueType);
	}

	@Override
	public java.lang.String getIssueType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IssueType);
	}

	@Override
	public void setIssueURL (java.lang.String IssueURL)
	{
		set_Value (COLUMNNAME_IssueURL, IssueURL);
	}

	@Override
	public java.lang.String getIssueURL() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IssueURL);
	}

	@Override
	public void setLatestActivity (java.sql.Timestamp LatestActivity)
	{
		set_ValueNoCheck (COLUMNNAME_LatestActivity, LatestActivity);
	}

	@Override
	public java.sql.Timestamp getLatestActivity() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_LatestActivity);
	}

	@Override
	public void setLatestActivityOnSubIssues (java.sql.Timestamp LatestActivityOnSubIssues)
	{
		set_Value (COLUMNNAME_LatestActivityOnSubIssues, LatestActivityOnSubIssues);
	}

	@Override
	public java.sql.Timestamp getLatestActivityOnSubIssues() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_LatestActivityOnSubIssues);
	}

	@Override
	public void setMilestone_DueDate (java.sql.Timestamp Milestone_DueDate)
	{
		throw new IllegalArgumentException ("Milestone_DueDate is virtual column");	}

	@Override
	public java.sql.Timestamp getMilestone_DueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Milestone_DueDate);
	}

	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public void setPlannedUATDate (java.sql.Timestamp PlannedUATDate)
	{
		set_Value (COLUMNNAME_PlannedUATDate, PlannedUATDate);
	}

	@Override
	public java.sql.Timestamp getPlannedUATDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PlannedUATDate);
	}

	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessedDate (java.sql.Timestamp ProcessedDate)
	{
		set_Value (COLUMNNAME_ProcessedDate, ProcessedDate);
	}

	@Override
	public java.sql.Timestamp getProcessedDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ProcessedDate);
	}

	@Override
	public void setRoughEstimation (java.math.BigDecimal RoughEstimation)
	{
		set_Value (COLUMNNAME_RoughEstimation, RoughEstimation);
	}

	@Override
	public java.math.BigDecimal getRoughEstimation() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_RoughEstimation);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public de.metas.serviceprovider.model.I_S_ExternalProjectReference getS_ExternalProjectReference()
	{
		return get_ValueAsPO(COLUMNNAME_S_ExternalProjectReference_ID, de.metas.serviceprovider.model.I_S_ExternalProjectReference.class);
	}

	@Override
	public void setS_ExternalProjectReference(de.metas.serviceprovider.model.I_S_ExternalProjectReference S_ExternalProjectReference)
	{
		set_ValueFromPO(COLUMNNAME_S_ExternalProjectReference_ID, de.metas.serviceprovider.model.I_S_ExternalProjectReference.class, S_ExternalProjectReference);
	}

	@Override
	public void setS_ExternalProjectReference_ID (int S_ExternalProjectReference_ID)
	{
		if (S_ExternalProjectReference_ID < 1) 
			set_Value (COLUMNNAME_S_ExternalProjectReference_ID, null);
		else 
			set_Value (COLUMNNAME_S_ExternalProjectReference_ID, Integer.valueOf(S_ExternalProjectReference_ID));
	}

	@Override
	public int getS_ExternalProjectReference_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_ExternalProjectReference_ID);
	}

	@Override
	public void setS_Issue_ID (int S_Issue_ID)
	{
		if (S_Issue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Issue_ID, Integer.valueOf(S_Issue_ID));
	}

	@Override
	public int getS_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Issue_ID);
	}

	@Override
	public de.metas.serviceprovider.model.I_S_Milestone getS_Milestone()
	{
		return get_ValueAsPO(COLUMNNAME_S_Milestone_ID, de.metas.serviceprovider.model.I_S_Milestone.class);
	}

	@Override
	public void setS_Milestone(de.metas.serviceprovider.model.I_S_Milestone S_Milestone)
	{
		set_ValueFromPO(COLUMNNAME_S_Milestone_ID, de.metas.serviceprovider.model.I_S_Milestone.class, S_Milestone);
	}

	@Override
	public void setS_Milestone_ID (int S_Milestone_ID)
	{
		if (S_Milestone_ID < 1) 
			set_Value (COLUMNNAME_S_Milestone_ID, null);
		else 
			set_Value (COLUMNNAME_S_Milestone_ID, Integer.valueOf(S_Milestone_ID));
	}

	@Override
	public int getS_Milestone_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Milestone_ID);
	}

	@Override
	public de.metas.serviceprovider.model.I_S_Issue getS_Parent_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_S_Parent_Issue_ID, de.metas.serviceprovider.model.I_S_Issue.class);
	}

	@Override
	public void setS_Parent_Issue(de.metas.serviceprovider.model.I_S_Issue S_Parent_Issue)
	{
		set_ValueFromPO(COLUMNNAME_S_Parent_Issue_ID, de.metas.serviceprovider.model.I_S_Issue.class, S_Parent_Issue);
	}

	@Override
	public void setS_Parent_Issue_ID (int S_Parent_Issue_ID)
	{
		if (S_Parent_Issue_ID < 1) 
			set_Value (COLUMNNAME_S_Parent_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_S_Parent_Issue_ID, Integer.valueOf(S_Parent_Issue_ID));
	}

	@Override
	public int getS_Parent_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Parent_Issue_ID);
	}

	/** 
	 * Status AD_Reference_ID=541142
	 * Reference name: S_Issue_Status
	 */
	public static final int STATUS_AD_Reference_ID=541142;
	/** In progress = InProgress */
	public static final String STATUS_InProgress = "InProgress";
	/** Closed = Closed */
	public static final String STATUS_Closed = "Closed";
	/** Pending = Pending */
	public static final String STATUS_Pending = "Pending";
	/** Delivered = Delivered */
	public static final String STATUS_Delivered = "Delivered";
	/** New = New */
	public static final String STATUS_New = "New";
	/** Invoiced = Invoiced */
	public static final String STATUS_Invoiced = "Invoiced";
	@Override
	public void setStatus (java.lang.String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}

	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}