/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_Effort_Issue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_Effort_Issue extends org.compiere.model.PO implements I_S_Effort_Issue, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -510900613L;

    /** Standard Constructor */
    public X_S_Effort_Issue (Properties ctx, int S_Effort_Issue_ID, String trxName)
    {
      super (ctx, S_Effort_Issue_ID, trxName);
      /** if (S_Effort_Issue_ID == 0)
        {
			setCurrentEffort (0); // 0
			setEffort_UOM_ID (0);
			setEstimatedEffort (BigDecimal.ZERO); // 0
			setName (null);
			setProcessed (false); // N
			setS_Effort_Issue_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_S_Effort_Issue (Properties ctx, ResultSet rs, String trxName)
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
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
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

	@Override
	public de.metas.serviceprovider.model.I_S_Budget_Issue getS_Budget_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_S_Budget_Issue_ID, de.metas.serviceprovider.model.I_S_Budget_Issue.class);
	}

	@Override
	public void setS_Budget_Issue(de.metas.serviceprovider.model.I_S_Budget_Issue S_Budget_Issue)
	{
		set_ValueFromPO(COLUMNNAME_S_Budget_Issue_ID, de.metas.serviceprovider.model.I_S_Budget_Issue.class, S_Budget_Issue);
	}

	/** Set Budget-Issue.
		@param S_Budget_Issue_ID Budget-Issue	  */
	@Override
	public void setS_Budget_Issue_ID (int S_Budget_Issue_ID)
	{
		if (S_Budget_Issue_ID < 1) 
			set_Value (COLUMNNAME_S_Budget_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_S_Budget_Issue_ID, Integer.valueOf(S_Budget_Issue_ID));
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

	/** Set Aufwands-Issue.
		@param S_Effort_Issue_ID Aufwands-Issue	  */
	@Override
	public void setS_Effort_Issue_ID (int S_Effort_Issue_ID)
	{
		if (S_Effort_Issue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Effort_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Effort_Issue_ID, Integer.valueOf(S_Effort_Issue_ID));
	}

	/** Get Aufwands-Issue.
		@return Aufwands-Issue	  */
	@Override
	public int getS_Effort_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Effort_Issue_ID);
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