/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_Issue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_Issue extends org.compiere.model.PO implements I_S_Issue, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1918761460L;

    /** Standard Constructor */
    public X_S_Issue (Properties ctx, int S_Issue_ID, String trxName)
    {
      super (ctx, S_Issue_ID, trxName);
      /** if (S_Issue_ID == 0)
        {
			setEffort_UOM_ID (0);
			setEstimatedEffort (BigDecimal.ZERO); // 0
			setIsApproved (false); // N
			setIsEffortIssue (false); // N
			setIssueType (null); // N
			setName (null);
			setProcessed (false); // N
			setS_Issue_ID (0);
			setStatus (null); // New
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_S_Issue (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Aggregated effort (H:mm).
		@param AggregatedEffort 
		The time spent on the whole issue tree in H:mm format.
	  */
	@Override
	public void setAggregatedEffort (java.lang.String AggregatedEffort)
	{
		set_Value (COLUMNNAME_AggregatedEffort, AggregatedEffort);
	}

	/** Get Aggregated effort (H:mm).
		@return The time spent on the whole issue tree in H:mm format.
	  */
	@Override
	public java.lang.String getAggregatedEffort () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AggregatedEffort);
	}

	/** Set Budgetiert.
		@param BudgetedEffort 
		Ursprünglich geplanter oder erwarteter Aufwand.
	  */
	@Override
	public void setBudgetedEffort (java.math.BigDecimal BudgetedEffort)
	{
		set_Value (COLUMNNAME_BudgetedEffort, BudgetedEffort);
	}

	/** Get Budgetiert.
		@return Ursprünglich geplanter oder erwarteter Aufwand.
	  */
	@Override
	public java.math.BigDecimal getBudgetedEffort () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BudgetedEffort);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	@Override
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	@Override
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Delivery platform.
		@param DeliveryPlatform 
		Specifies the instances where the issue was deployed.
	  */
	@Override
	public void setDeliveryPlatform (java.lang.String DeliveryPlatform)
	{
		set_Value (COLUMNNAME_DeliveryPlatform, DeliveryPlatform);
	}

	/** Get Delivery platform.
		@return Specifies the instances where the issue was deployed.
	  */
	@Override
	public java.lang.String getDeliveryPlatform () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryPlatform);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Einheit.
		@param Effort_UOM_ID Einheit	  */
	@Override
	public void setEffort_UOM_ID (int Effort_UOM_ID)
	{
		if (Effort_UOM_ID < 1) 
			set_Value (COLUMNNAME_Effort_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Effort_UOM_ID, Integer.valueOf(Effort_UOM_ID));
	}

	/** Get Einheit.
		@return Einheit	  */
	@Override
	public int getEffort_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Effort_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Effort delivery platform.
		@param EffortDeliveryPlatform Effort delivery platform	  */
	@Override
	public void setEffortDeliveryPlatform (java.lang.String EffortDeliveryPlatform)
	{
		throw new IllegalArgumentException ("EffortDeliveryPlatform is virtual column");	}

	/** Get Effort delivery platform.
		@return Effort delivery platform	  */
	@Override
	public java.lang.String getEffortDeliveryPlatform () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EffortDeliveryPlatform);
	}

	/** Set Geschätzter Aufwand.
		@param EstimatedEffort Geschätzter Aufwand	  */
	@Override
	public void setEstimatedEffort (java.math.BigDecimal EstimatedEffort)
	{
		set_ValueNoCheck (COLUMNNAME_EstimatedEffort, EstimatedEffort);
	}

	/** Get Geschätzter Aufwand.
		@return Geschätzter Aufwand	  */
	@Override
	public java.math.BigDecimal getEstimatedEffort () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EstimatedEffort);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set External issue no.
		@param ExternalIssueNo 
		External issue number ( e.g. github issue number )
	  */
	@Override
	public void setExternalIssueNo (java.math.BigDecimal ExternalIssueNo)
	{
		set_Value (COLUMNNAME_ExternalIssueNo, ExternalIssueNo);
	}

	/** Get External issue no.
		@return External issue number ( e.g. github issue number )
	  */
	@Override
	public java.math.BigDecimal getExternalIssueNo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExternalIssueNo);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set hasInternalEffortIssue.
		@param hasInternalEffortIssue hasInternalEffortIssue	  */
	@Override
	public void sethasInternalEffortIssue (boolean hasInternalEffortIssue)
	{
		throw new IllegalArgumentException ("hasInternalEffortIssue is virtual column");	}

	/** Get hasInternalEffortIssue.
		@return hasInternalEffortIssue	  */
	@Override
	public boolean ishasInternalEffortIssue () 
	{
		Object oo = get_Value(COLUMNNAME_hasInternalEffortIssue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Internal assignee.
		@param Internal_Assignee_ID Internal assignee	  */
	@Override
	public void setInternal_Assignee_ID (int Internal_Assignee_ID)
	{
		throw new IllegalArgumentException ("Internal_Assignee_ID is virtual column");	}

	/** Get Internal assignee.
		@return Internal assignee	  */
	@Override
	public int getInternal_Assignee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Internal_Assignee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Internal budget.
		@param Internal_Budgeted Internal budget	  */
	@Override
	public void setInternal_Budgeted (java.math.BigDecimal Internal_Budgeted)
	{
		throw new IllegalArgumentException ("Internal_Budgeted is virtual column");	}

	/** Get Internal budget.
		@return Internal budget	  */
	@Override
	public java.math.BigDecimal getInternal_Budgeted () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Internal_Budgeted);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Internal due date.
		@param Internal_DueDate Internal due date	  */
	@Override
	public void setInternal_DueDate (java.sql.Timestamp Internal_DueDate)
	{
		throw new IllegalArgumentException ("Internal_DueDate is virtual column");	}

	/** Get Internal due date.
		@return Internal due date	  */
	@Override
	public java.sql.Timestamp getInternal_DueDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_Internal_DueDate);
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

	/** Set Internal effort issue.
		@param Internal_Effort_S_Issue_ID Internal effort issue	  */
	@Override
	public void setInternal_Effort_S_Issue_ID (int Internal_Effort_S_Issue_ID)
	{
		throw new IllegalArgumentException ("Internal_Effort_S_Issue_ID is virtual column");	}

	/** Get Internal effort issue.
		@return Internal effort issue	  */
	@Override
	public int getInternal_Effort_S_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Internal_Effort_S_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Internal estimated effort.
		@param Internal_EstimatedEffort Internal estimated effort	  */
	@Override
	public void setInternal_EstimatedEffort (java.math.BigDecimal Internal_EstimatedEffort)
	{
		throw new IllegalArgumentException ("Internal_EstimatedEffort is virtual column");	}

	/** Get Internal estimated effort.
		@return Internal estimated effort	  */
	@Override
	public java.math.BigDecimal getInternal_EstimatedEffort () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Internal_EstimatedEffort);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Internal planned UAT date.
		@param Internal_PlannedUATDate Internal planned UAT date	  */
	@Override
	public void setInternal_PlannedUATDate (java.sql.Timestamp Internal_PlannedUATDate)
	{
		throw new IllegalArgumentException ("Internal_PlannedUATDate is virtual column");	}

	/** Get Internal planned UAT date.
		@return Internal planned UAT date	  */
	@Override
	public java.sql.Timestamp getInternal_PlannedUATDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_Internal_PlannedUATDate);
	}

	/** Set Internal rough estimation.
		@param Internal_RoughEstimation Internal rough estimation	  */
	@Override
	public void setInternal_RoughEstimation (java.math.BigDecimal Internal_RoughEstimation)
	{
		throw new IllegalArgumentException ("Internal_RoughEstimation is virtual column");	}

	/** Get Internal rough estimation.
		@return Internal rough estimation	  */
	@Override
	public java.math.BigDecimal getInternal_RoughEstimation () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Internal_RoughEstimation);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set Internal milestone.
		@param Internal_S_Milestone_ID Internal milestone	  */
	@Override
	public void setInternal_S_Milestone_ID (int Internal_S_Milestone_ID)
	{
		throw new IllegalArgumentException ("Internal_S_Milestone_ID is virtual column");	}

	/** Get Internal milestone.
		@return Internal milestone	  */
	@Override
	public int getInternal_S_Milestone_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Internal_S_Milestone_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Internal-Approved.
		@param InternalApproved Internal-Approved	  */
	@Override
	public void setInternalApproved (boolean InternalApproved)
	{
		throw new IllegalArgumentException ("InternalApproved is virtual column");	}

	/** Get Internal-Approved.
		@return Internal-Approved	  */
	@Override
	public boolean isInternalApproved () 
	{
		Object oo = get_Value(COLUMNNAME_InternalApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Invoiceable effort.
		@param InvoiceableEffort Invoiceable effort	  */
	@Override
	public void setInvoiceableEffort (java.math.BigDecimal InvoiceableEffort)
	{
		set_Value (COLUMNNAME_InvoiceableEffort, InvoiceableEffort);
	}

	/** Get Invoiceable effort.
		@return Invoiceable effort	  */
	@Override
	public java.math.BigDecimal getInvoiceableEffort () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InvoiceableEffort);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Invoiced date.
		@param InvoicedDate Invoiced date	  */
	@Override
	public void setInvoicedDate (java.sql.Timestamp InvoicedDate)
	{
		set_Value (COLUMNNAME_InvoicedDate, InvoicedDate);
	}

	/** Get Invoiced date.
		@return Invoiced date	  */
	@Override
	public java.sql.Timestamp getInvoicedDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_InvoicedDate);
	}

	/** Set Freigegeben.
		@param IsApproved 
		Zeigt an, ob dieser Beleg eine Freigabe braucht
	  */
	@Override
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Freigegeben.
		@return Zeigt an, ob dieser Beleg eine Freigabe braucht
	  */
	@Override
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Effort issue.
		@param IsEffortIssue Effort issue	  */
	@Override
	public void setIsEffortIssue (boolean IsEffortIssue)
	{
		set_Value (COLUMNNAME_IsEffortIssue, Boolean.valueOf(IsEffortIssue));
	}

	/** Get Effort issue.
		@return Effort issue	  */
	@Override
	public boolean isEffortIssue () 
	{
		Object oo = get_Value(COLUMNNAME_IsEffortIssue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Issue effort (H:mm).
		@param IssueEffort 
		Time spent directly on this task in H:mm format.
	  */
	@Override
	public void setIssueEffort (java.lang.String IssueEffort)
	{
		set_Value (COLUMNNAME_IssueEffort, IssueEffort);
	}

	/** Get Issue effort (H:mm).
		@return Time spent directly on this task in H:mm format.
	  */
	@Override
	public java.lang.String getIssueEffort () 
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
	/** Set Type.
		@param IssueType Type	  */
	@Override
	public void setIssueType (java.lang.String IssueType)
	{

		set_Value (COLUMNNAME_IssueType, IssueType);
	}

	/** Get Type.
		@return Type	  */
	@Override
	public java.lang.String getIssueType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IssueType);
	}

	/** Set Issue-URL.
		@param IssueURL 
		URL der Issue, z.B. auf github
	  */
	@Override
	public void setIssueURL (java.lang.String IssueURL)
	{
		set_Value (COLUMNNAME_IssueURL, IssueURL);
	}

	/** Get Issue-URL.
		@return URL der Issue, z.B. auf github
	  */
	@Override
	public java.lang.String getIssueURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IssueURL);
	}

	/** Set Latest activity.
		@param LatestActivity Latest activity	  */
	@Override
	public void setLatestActivity (java.sql.Timestamp LatestActivity)
	{
		set_ValueNoCheck (COLUMNNAME_LatestActivity, LatestActivity);
	}

	/** Get Latest activity.
		@return Latest activity	  */
	@Override
	public java.sql.Timestamp getLatestActivity () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LatestActivity);
	}

	/** Set Latest activity on sub issues.
		@param LatestActivityOnSubIssues Latest activity on sub issues	  */
	@Override
	public void setLatestActivityOnSubIssues (java.sql.Timestamp LatestActivityOnSubIssues)
	{
		set_Value (COLUMNNAME_LatestActivityOnSubIssues, LatestActivityOnSubIssues);
	}

	/** Get Latest activity on sub issues.
		@return Latest activity on sub issues	  */
	@Override
	public java.sql.Timestamp getLatestActivityOnSubIssues () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LatestActivityOnSubIssues);
	}

	/** Set Fälligkeitsdatum.
		@param Milestone_DueDate Fälligkeitsdatum	  */
	@Override
	public void setMilestone_DueDate (java.sql.Timestamp Milestone_DueDate)
	{
		throw new IllegalArgumentException ("Milestone_DueDate is virtual column");	}

	/** Get Fälligkeitsdatum.
		@return Fälligkeitsdatum	  */
	@Override
	public java.sql.Timestamp getMilestone_DueDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_Milestone_DueDate);
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Planned UAT date.
		@param PlannedUATDate Planned UAT date	  */
	@Override
	public void setPlannedUATDate (java.sql.Timestamp PlannedUATDate)
	{
		set_Value (COLUMNNAME_PlannedUATDate, PlannedUATDate);
	}

	/** Get Planned UAT date.
		@return Planned UAT date	  */
	@Override
	public java.sql.Timestamp getPlannedUATDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PlannedUATDate);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rough estimation.
		@param RoughEstimation Rough estimation	  */
	@Override
	public void setRoughEstimation (java.math.BigDecimal RoughEstimation)
	{
		set_Value (COLUMNNAME_RoughEstimation, RoughEstimation);
	}

	/** Get Rough estimation.
		@return Rough estimation	  */
	@Override
	public java.math.BigDecimal getRoughEstimation () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RoughEstimation);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set External project reference ID.
		@param S_ExternalProjectReference_ID External project reference ID	  */
	@Override
	public void setS_ExternalProjectReference_ID (int S_ExternalProjectReference_ID)
	{
		if (S_ExternalProjectReference_ID < 1) 
			set_Value (COLUMNNAME_S_ExternalProjectReference_ID, null);
		else 
			set_Value (COLUMNNAME_S_ExternalProjectReference_ID, Integer.valueOf(S_ExternalProjectReference_ID));
	}

	/** Get External project reference ID.
		@return External project reference ID	  */
	@Override
	public int getS_ExternalProjectReference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_ExternalProjectReference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Issue.
		@param S_Issue_ID Issue	  */
	@Override
	public void setS_Issue_ID (int S_Issue_ID)
	{
		if (S_Issue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Issue_ID, Integer.valueOf(S_Issue_ID));
	}

	/** Get Issue.
		@return Issue	  */
	@Override
	public int getS_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Meilenstein.
		@param S_Milestone_ID Meilenstein	  */
	@Override
	public void setS_Milestone_ID (int S_Milestone_ID)
	{
		if (S_Milestone_ID < 1) 
			set_Value (COLUMNNAME_S_Milestone_ID, null);
		else 
			set_Value (COLUMNNAME_S_Milestone_ID, Integer.valueOf(S_Milestone_ID));
	}

	/** Get Meilenstein.
		@return Meilenstein	  */
	@Override
	public int getS_Milestone_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Milestone_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Parent issue ID.
		@param S_Parent_Issue_ID Parent issue ID	  */
	@Override
	public void setS_Parent_Issue_ID (int S_Parent_Issue_ID)
	{
		if (S_Parent_Issue_ID < 1) 
			set_Value (COLUMNNAME_S_Parent_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_S_Parent_Issue_ID, Integer.valueOf(S_Parent_Issue_ID));
	}

	/** Get Parent issue ID.
		@return Parent issue ID	  */
	@Override
	public int getS_Parent_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Parent_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	/** Set Status.
		@param Status Status	  */
	@Override
	public void setStatus (java.lang.String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status	  */
	@Override
	public java.lang.String getStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}

	/** Set Suchschlüssel.
		@param Value 
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}