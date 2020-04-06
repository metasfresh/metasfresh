/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_Milestone
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_Milestone extends org.compiere.model.PO implements I_S_Milestone, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -899828235L;

    /** Standard Constructor */
    public X_S_Milestone (Properties ctx, int S_Milestone_ID, String trxName)
    {
      super (ctx, S_Milestone_ID, trxName);
      /** if (S_Milestone_ID == 0)
        {
			setName (null);
			setProcessed (false); // N
			setS_Milestone_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_S_Milestone (Properties ctx, ResultSet rs, String trxName)
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

	/** Set External URL.
		@param ExternalUrl 
		Link to the external resource. 
	  */
	@Override
	public void setExternalUrl (java.lang.String ExternalUrl)
	{
		set_Value (COLUMNNAME_ExternalUrl, ExternalUrl);
	}

	/** Get External URL.
		@return Link to the external resource. 
	  */
	@Override
	public java.lang.String getExternalUrl () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalUrl);
	}

	/** Set Fälligkeitsdatum.
		@param Milestone_DueDate Fälligkeitsdatum	  */
	@Override
	public void setMilestone_DueDate (java.sql.Timestamp Milestone_DueDate)
	{
		set_Value (COLUMNNAME_Milestone_DueDate, Milestone_DueDate);
	}

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

	/** Set Meilenstein.
		@param S_Milestone_ID Meilenstein	  */
	@Override
	public void setS_Milestone_ID (int S_Milestone_ID)
	{
		if (S_Milestone_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Milestone_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Milestone_ID, Integer.valueOf(S_Milestone_ID));
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