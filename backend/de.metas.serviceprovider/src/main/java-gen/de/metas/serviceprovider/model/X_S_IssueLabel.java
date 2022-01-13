// Generated Model - DO NOT CHANGE
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for S_IssueLabel
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_IssueLabel extends org.compiere.model.PO implements I_S_IssueLabel, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1351114609L;

    /** Standard Constructor */
    public X_S_IssueLabel (final Properties ctx, final int S_IssueLabel_ID, @Nullable final String trxName)
    {
      super (ctx, S_IssueLabel_ID, trxName);
    }

    /** Load Constructor */
    public X_S_IssueLabel (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * Label AD_Reference_ID=541140
	 * Reference name: S_IssueLabel
	 */
	public static final int LABEL_AD_Reference_ID=541140;
	/** TestLabel = TestLabel */
	public static final String LABEL_TestLabel = "TestLabel";
	@Override
	public void setLabel (final java.lang.String Label)
	{
		set_ValueNoCheck (COLUMNNAME_Label, Label);
	}

	@Override
	public java.lang.String getLabel() 
	{
		return get_ValueAsString(COLUMNNAME_Label);
	}

	@Override
	public de.metas.serviceprovider.model.I_S_Issue getS_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_S_Issue_ID, de.metas.serviceprovider.model.I_S_Issue.class);
	}

	@Override
	public void setS_Issue(final de.metas.serviceprovider.model.I_S_Issue S_Issue)
	{
		set_ValueFromPO(COLUMNNAME_S_Issue_ID, de.metas.serviceprovider.model.I_S_Issue.class, S_Issue);
	}

	@Override
	public void setS_Issue_ID (final int S_Issue_ID)
	{
		if (S_Issue_ID < 1) 
			set_Value (COLUMNNAME_S_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_S_Issue_ID, S_Issue_ID);
	}

	@Override
	public int getS_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Issue_ID);
	}

	@Override
	public void setS_IssueLabel_ID (final int S_IssueLabel_ID)
	{
		if (S_IssueLabel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_IssueLabel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_IssueLabel_ID, S_IssueLabel_ID);
	}

	@Override
	public int getS_IssueLabel_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_IssueLabel_ID);
	}
}