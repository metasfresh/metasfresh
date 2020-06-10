/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_ExternalReference
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_ExternalReference extends org.compiere.model.PO implements I_S_ExternalReference, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 388616412L;

    /** Standard Constructor */
    public X_S_ExternalReference (Properties ctx, int S_ExternalReference_ID, String trxName)
    {
      super (ctx, S_ExternalReference_ID, trxName);
      /** if (S_ExternalReference_ID == 0)
        {
			setExternalReference (null);
			setExternalSystem (null);
			setRecord_ID (0);
			setReferenced_AD_Table_ID (0);
			setReferenced_Record_ID (0);
			setType (null);
        } */
    }

    /** Load Constructor */
    public X_S_ExternalReference (Properties ctx, ResultSet rs, String trxName)
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

	/** Set External reference.
		@param ExternalReference External reference	  */
	@Override
	public void setExternalReference (java.lang.String ExternalReference)
	{
		set_Value (COLUMNNAME_ExternalReference, ExternalReference);
	}

	/** Get External reference.
		@return External reference	  */
	@Override
	public java.lang.String getExternalReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalReference);
	}

	/** 
	 * ExternalSystem AD_Reference_ID=541117
	 * Reference name: ExternalSystem
	 */
	public static final int EXTERNALSYSTEM_AD_Reference_ID=541117;
	/** Github = Github */
	public static final String EXTERNALSYSTEM_Github = "Github";
	/** Everhour = Everhour */
	public static final String EXTERNALSYSTEM_Everhour = "Everhour";
	/** Set External system.
		@param ExternalSystem 
		Name of an external system (e.g. Github )
	  */
	@Override
	public void setExternalSystem (java.lang.String ExternalSystem)
	{

		set_Value (COLUMNNAME_ExternalSystem, ExternalSystem);
	}

	/** Get External system.
		@return Name of an external system (e.g. Github )
	  */
	@Override
	public java.lang.String getExternalSystem () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalSystem);
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Referenced table ID.
		@param Referenced_AD_Table_ID Referenced table ID	  */
	@Override
	public void setReferenced_AD_Table_ID (int Referenced_AD_Table_ID)
	{
		if (Referenced_AD_Table_ID < 1) 
			set_Value (COLUMNNAME_Referenced_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_Referenced_AD_Table_ID, Integer.valueOf(Referenced_AD_Table_ID));
	}

	/** Get Referenced table ID.
		@return Referenced table ID	  */
	@Override
	public int getReferenced_AD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Referenced_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Referenced record ID.
		@param Referenced_Record_ID Referenced record ID	  */
	@Override
	public void setReferenced_Record_ID (int Referenced_Record_ID)
	{
		if (Referenced_Record_ID < 1) 
			set_Value (COLUMNNAME_Referenced_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Referenced_Record_ID, Integer.valueOf(Referenced_Record_ID));
	}

	/** Get Referenced record ID.
		@return Referenced record ID	  */
	@Override
	public int getReferenced_Record_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Referenced_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set External reference.
		@param S_ExternalReference_ID External reference	  */
	@Override
	public void setS_ExternalReference_ID (int S_ExternalReference_ID)
	{
		if (S_ExternalReference_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_ExternalReference_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_ExternalReference_ID, Integer.valueOf(S_ExternalReference_ID));
	}

	/** Get External reference.
		@return External reference	  */
	@Override
	public int getS_ExternalReference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_ExternalReference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * Type AD_Reference_ID=541127
	 * Reference name: ExternalReferenceType
	 */
	public static final int TYPE_AD_Reference_ID=541127;
	/** UserID = UserID */
	public static final String TYPE_UserID = "UserID";
	/** IssueID = IssueID */
	public static final String TYPE_IssueID = "IssueID";
	/** Time booking ID = TimeBookingID */
	public static final String TYPE_TimeBookingID = "TimeBookingID";
	/** MilestoneId = MilestonId */
	public static final String TYPE_MilestoneId = "MilestonId";
	/** Set Art.
		@param Type Art	  */
	@Override
	public void setType (java.lang.String Type)
	{

		set_ValueNoCheck (COLUMNNAME_Type, Type);
	}

	/** Get Art.
		@return Art	  */
	@Override
	public java.lang.String getType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type);
	}
}