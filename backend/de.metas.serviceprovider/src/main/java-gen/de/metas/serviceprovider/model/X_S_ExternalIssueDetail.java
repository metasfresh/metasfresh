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
	private static final long serialVersionUID = -683123281L;

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

	/** Set Value.
		@param DetailValue Value	  */
	@Override
	public void setDetailValue (java.lang.String DetailValue)
	{
		set_Value (COLUMNNAME_DetailValue, DetailValue);
	}

	/** Get Value.
		@return Value	  */
	@Override
	public java.lang.String getDetailValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DetailValue);
	}

	/** Set External issue details.
		@param S_ExternalIssueDetail_ID External issue details	  */
	@Override
	public void setS_ExternalIssueDetail_ID (int S_ExternalIssueDetail_ID)
	{
		if (S_ExternalIssueDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_ExternalIssueDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_ExternalIssueDetail_ID, Integer.valueOf(S_ExternalIssueDetail_ID));
	}

	/** Get External issue details.
		@return External issue details	  */
	@Override
	public int getS_ExternalIssueDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_ExternalIssueDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.serviceprovider.model.I_S_Issue getS_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_S_Issue_ID, de.metas.serviceprovider.model.I_S_Issue.class);
	}

	@Override
	public void setS_Issue(de.metas.serviceprovider.model.I_S_Issue S_Issue)
	{
		set_ValueFromPO(COLUMNNAME_S_Issue_ID, de.metas.serviceprovider.model.I_S_Issue.class, S_Issue);
	}

	/** Set Issue.
		@param S_Issue_ID Issue	  */
	@Override
	public void setS_Issue_ID (int S_Issue_ID)
	{
		if (S_Issue_ID < 1) 
			set_Value (COLUMNNAME_S_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_S_Issue_ID, Integer.valueOf(S_Issue_ID));
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