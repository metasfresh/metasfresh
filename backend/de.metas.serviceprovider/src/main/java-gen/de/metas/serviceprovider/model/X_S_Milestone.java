// Generated Model - DO NOT CHANGE
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for S_Milestone
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_Milestone extends org.compiere.model.PO implements I_S_Milestone, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 807505893L;

    /** Standard Constructor */
    public X_S_Milestone (final Properties ctx, final int S_Milestone_ID, @Nullable final String trxName)
    {
      super (ctx, S_Milestone_ID, trxName);
    }

    /** Load Constructor */
    public X_S_Milestone (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setExternalUrl (final @Nullable java.lang.String ExternalUrl)
	{
		set_Value (COLUMNNAME_ExternalUrl, ExternalUrl);
	}

	@Override
	public java.lang.String getExternalUrl() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalUrl);
	}

	@Override
	public void setMilestone_DueDate (final @Nullable java.sql.Timestamp Milestone_DueDate)
	{
		set_Value (COLUMNNAME_Milestone_DueDate, Milestone_DueDate);
	}

	@Override
	public java.sql.Timestamp getMilestone_DueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Milestone_DueDate);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setS_Milestone_ID (final int S_Milestone_ID)
	{
		if (S_Milestone_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Milestone_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Milestone_ID, S_Milestone_ID);
	}

	@Override
	public int getS_Milestone_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Milestone_ID);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}