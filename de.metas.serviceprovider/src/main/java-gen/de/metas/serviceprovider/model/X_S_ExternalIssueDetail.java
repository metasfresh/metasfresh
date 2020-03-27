/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_ExternalIssueDetail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_ExternalIssueDetail extends org.compiere.model.PO implements I_S_ExternalIssueDetail, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -159184492L;

    /** Standard Constructor */
    public X_S_ExternalIssueDetail (Properties ctx, int S_ExternalIssueDetail_ID, String trxName)
    {
      super (ctx, S_ExternalIssueDetail_ID, trxName);
      /** if (S_ExternalIssueDetail_ID == 0)
        {
			setDetailValue (null);
			setType (null);
        } */
    }

    /** Load Constructor */
    public X_S_ExternalIssueDetail (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Detail value.
		@param DetailValue Detail value	  */
	@Override
	public void setDetailValue (java.lang.String DetailValue)
	{
		set_Value (COLUMNNAME_DetailValue, DetailValue);
	}

	/** Get Detail value.
		@return Detail value	  */
	@Override
	public java.lang.String getDetailValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DetailValue);
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

	@Override
	public de.metas.serviceprovider.model.I_S_Effort_Issue getS_Effort_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_S_Effort_Issue_ID, de.metas.serviceprovider.model.I_S_Effort_Issue.class);
	}

	@Override
	public void setS_Effort_Issue(de.metas.serviceprovider.model.I_S_Effort_Issue S_Effort_Issue)
	{
		set_ValueFromPO(COLUMNNAME_S_Effort_Issue_ID, de.metas.serviceprovider.model.I_S_Effort_Issue.class, S_Effort_Issue);
	}

	/** Set Aufwands-Issue.
		@param S_Effort_Issue_ID Aufwands-Issue	  */
	@Override
	public void setS_Effort_Issue_ID (int S_Effort_Issue_ID)
	{
		if (S_Effort_Issue_ID < 1) 
			set_Value (COLUMNNAME_S_Effort_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_S_Effort_Issue_ID, Integer.valueOf(S_Effort_Issue_ID));
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