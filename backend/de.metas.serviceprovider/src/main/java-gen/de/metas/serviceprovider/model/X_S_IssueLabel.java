/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_IssueLabel
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_IssueLabel extends org.compiere.model.PO implements I_S_IssueLabel, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1700391006L;

    /** Standard Constructor */
    public X_S_IssueLabel (Properties ctx, int S_IssueLabel_ID, String trxName)
    {
      super (ctx, S_IssueLabel_ID, trxName);
      /** if (S_IssueLabel_ID == 0)
        {
			setLabel (null);
			setS_Issue_ID (0);
        } */
    }

    /** Load Constructor */
    public X_S_IssueLabel (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * Label AD_Reference_ID=541140
	 * Reference name: S_IssueLabel
	 */
	public static final int LABEL_AD_Reference_ID=541140;
	/** Set Label.
		@param Label Label	  */
	@Override
	public void setLabel (java.lang.String Label)
	{

		set_ValueNoCheck (COLUMNNAME_Label, Label);
	}

	/** Get Label.
		@return Label	  */
	@Override
	public java.lang.String getLabel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Label);
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

	/** Set Issue label ID.
		@param S_IssueLabel_ID Issue label ID	  */
	@Override
	public void setS_IssueLabel_ID (int S_IssueLabel_ID)
	{
		if (S_IssueLabel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_IssueLabel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_IssueLabel_ID, Integer.valueOf(S_IssueLabel_ID));
	}

	/** Get Issue label ID.
		@return Issue label ID	  */
	@Override
	public int getS_IssueLabel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_IssueLabel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
