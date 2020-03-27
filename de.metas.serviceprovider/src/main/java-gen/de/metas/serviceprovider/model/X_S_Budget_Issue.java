/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_Budget_Issue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_Budget_Issue extends org.compiere.model.PO implements I_S_Budget_Issue, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 284204116L;

    /** Standard Constructor */
    public X_S_Budget_Issue (Properties ctx, int S_Budget_Issue_ID, String trxName)
    {
      super (ctx, S_Budget_Issue_ID, trxName);
      /** if (S_Budget_Issue_ID == 0)
        {
			setBudget_Issue_Type (null);
			setBudgetedEffort (BigDecimal.ZERO); // 0
			setCurrentEffort (0); // 0
			setEffort_UOM_ID (0);
			setEstimatedEffort (BigDecimal.ZERO); // 0
			setIsApproved (false); // N
			setName (null);
			setProcessed (false); // N
			setS_Budget_Issue_ID (0);
        } */
    }

    /** Load Constructor */
    public X_S_Budget_Issue (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * Budget_Issue_Type AD_Reference_ID=541105
	 * Reference name: Budget_Issue_Type
	 */
	public static final int BUDGET_ISSUE_TYPE_AD_Reference_ID=541105;
	/** Internal = Internal */
	public static final String BUDGET_ISSUE_TYPE_Internal = "Internal";
	/** External = External */
	public static final String BUDGET_ISSUE_TYPE_External = "External";
	/** Set Typ.
		@param Budget_Issue_Type Typ	  */
	@Override
	public void setBudget_Issue_Type (java.lang.String Budget_Issue_Type)
	{

		set_Value (COLUMNNAME_Budget_Issue_Type, Budget_Issue_Type);
	}

	/** Get Typ.
		@return Typ	  */
	@Override
	public java.lang.String getBudget_Issue_Type () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Budget_Issue_Type);
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

	/** Set Aktueller Aufwand.
		@param CurrentEffort Aktueller Aufwand	  */
	@Override
	public void setCurrentEffort (int CurrentEffort)
	{
		set_Value (COLUMNNAME_CurrentEffort, Integer.valueOf(CurrentEffort));
	}

	/** Get Aktueller Aufwand.
		@return Aktueller Aufwand	  */
	@Override
	public int getCurrentEffort () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CurrentEffort);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Geschätzter Aufwand.
		@param EstimatedEffort Geschätzter Aufwand	  */
	@Override
	public void setEstimatedEffort (java.math.BigDecimal EstimatedEffort)
	{
		set_Value (COLUMNNAME_EstimatedEffort, EstimatedEffort);
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

	/** Set External ID.
		@param ExternalId External ID	  */
	@Override
	public void setExternalId (java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	/** Get External ID.
		@return External ID	  */
	@Override
	public java.lang.String getExternalId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalId);
	}

	/** Set ExternalIssueNo.
		@param ExternalIssueNo 
		External issue number ( e.g. github issue number )
	  */
	@Override
	public void setExternalIssueNo (java.lang.String ExternalIssueNo)
	{
		set_Value (COLUMNNAME_ExternalIssueNo, ExternalIssueNo);
	}

	/** Get ExternalIssueNo.
		@return External issue number ( e.g. github issue number )
	  */
	@Override
	public java.lang.String getExternalIssueNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalIssueNo);
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

	/** Set Budget-Issue.
		@param S_Budget_Issue_ID Budget-Issue	  */
	@Override
	public void setS_Budget_Issue_ID (int S_Budget_Issue_ID)
	{
		if (S_Budget_Issue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Budget_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Budget_Issue_ID, Integer.valueOf(S_Budget_Issue_ID));
	}

	/** Get Budget-Issue.
		@return Budget-Issue	  */
	@Override
	public int getS_Budget_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Budget_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.serviceprovider.model.I_S_Budget_Issue getS_Budget_Issue_Parent()
	{
		return get_ValueAsPO(COLUMNNAME_S_Budget_Issue_Parent_ID, de.metas.serviceprovider.model.I_S_Budget_Issue.class);
	}

	@Override
	public void setS_Budget_Issue_Parent(de.metas.serviceprovider.model.I_S_Budget_Issue S_Budget_Issue_Parent)
	{
		set_ValueFromPO(COLUMNNAME_S_Budget_Issue_Parent_ID, de.metas.serviceprovider.model.I_S_Budget_Issue.class, S_Budget_Issue_Parent);
	}

	/** Set Übergeordnete Issue.
		@param S_Budget_Issue_Parent_ID 
		Hinweis: Nur Issues, die eine übergeordnete Issue haben, können "Intern" sein.
	  */
	@Override
	public void setS_Budget_Issue_Parent_ID (int S_Budget_Issue_Parent_ID)
	{
		if (S_Budget_Issue_Parent_ID < 1) 
			set_Value (COLUMNNAME_S_Budget_Issue_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_S_Budget_Issue_Parent_ID, Integer.valueOf(S_Budget_Issue_Parent_ID));
	}

	/** Get Übergeordnete Issue.
		@return Hinweis: Nur Issues, die eine übergeordnete Issue haben, können "Intern" sein.
	  */
	@Override
	public int getS_Budget_Issue_Parent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Budget_Issue_Parent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.serviceprovider.model.I_S_Milestone getS_Budgeted_Milestone()
	{
		return get_ValueAsPO(COLUMNNAME_S_Budgeted_Milestone_ID, de.metas.serviceprovider.model.I_S_Milestone.class);
	}

	@Override
	public void setS_Budgeted_Milestone(de.metas.serviceprovider.model.I_S_Milestone S_Budgeted_Milestone)
	{
		set_ValueFromPO(COLUMNNAME_S_Budgeted_Milestone_ID, de.metas.serviceprovider.model.I_S_Milestone.class, S_Budgeted_Milestone);
	}

	/** Set Budget Meilenstein.
		@param S_Budgeted_Milestone_ID Budget Meilenstein	  */
	@Override
	public void setS_Budgeted_Milestone_ID (int S_Budgeted_Milestone_ID)
	{
		if (S_Budgeted_Milestone_ID < 1) 
			set_Value (COLUMNNAME_S_Budgeted_Milestone_ID, null);
		else 
			set_Value (COLUMNNAME_S_Budgeted_Milestone_ID, Integer.valueOf(S_Budgeted_Milestone_ID));
	}

	/** Get Budget Meilenstein.
		@return Budget Meilenstein	  */
	@Override
	public int getS_Budgeted_Milestone_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Budgeted_Milestone_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.serviceprovider.model.I_S_Milestone getS_Current_Milestone()
	{
		return get_ValueAsPO(COLUMNNAME_S_Current_Milestone_ID, de.metas.serviceprovider.model.I_S_Milestone.class);
	}

	@Override
	public void setS_Current_Milestone(de.metas.serviceprovider.model.I_S_Milestone S_Current_Milestone)
	{
		set_ValueFromPO(COLUMNNAME_S_Current_Milestone_ID, de.metas.serviceprovider.model.I_S_Milestone.class, S_Current_Milestone);
	}

	/** Set Aktueller Meilenstein.
		@param S_Current_Milestone_ID Aktueller Meilenstein	  */
	@Override
	public void setS_Current_Milestone_ID (int S_Current_Milestone_ID)
	{
		if (S_Current_Milestone_ID < 1) 
			set_Value (COLUMNNAME_S_Current_Milestone_ID, null);
		else 
			set_Value (COLUMNNAME_S_Current_Milestone_ID, Integer.valueOf(S_Current_Milestone_ID));
	}

	/** Get Aktueller Meilenstein.
		@return Aktueller Meilenstein	  */
	@Override
	public int getS_Current_Milestone_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Current_Milestone_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.serviceprovider.model.I_S_Milestone getS_Planned_Milestone()
	{
		return get_ValueAsPO(COLUMNNAME_S_Planned_Milestone_ID, de.metas.serviceprovider.model.I_S_Milestone.class);
	}

	@Override
	public void setS_Planned_Milestone(de.metas.serviceprovider.model.I_S_Milestone S_Planned_Milestone)
	{
		set_ValueFromPO(COLUMNNAME_S_Planned_Milestone_ID, de.metas.serviceprovider.model.I_S_Milestone.class, S_Planned_Milestone);
	}

	/** Set Geplanter Meilenstein.
		@param S_Planned_Milestone_ID Geplanter Meilenstein	  */
	@Override
	public void setS_Planned_Milestone_ID (int S_Planned_Milestone_ID)
	{
		if (S_Planned_Milestone_ID < 1) 
			set_Value (COLUMNNAME_S_Planned_Milestone_ID, null);
		else 
			set_Value (COLUMNNAME_S_Planned_Milestone_ID, Integer.valueOf(S_Planned_Milestone_ID));
	}

	/** Get Geplanter Meilenstein.
		@return Geplanter Meilenstein	  */
	@Override
	public int getS_Planned_Milestone_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Planned_Milestone_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}