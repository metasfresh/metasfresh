// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for S_HumanResourceTestGroup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_HumanResourceTestGroup extends org.compiere.model.PO implements I_S_HumanResourceTestGroup, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 869487017L;

    /** Standard Constructor */
    public X_S_HumanResourceTestGroup (final Properties ctx, final int S_HumanResourceTestGroup_ID, @Nullable final String trxName)
    {
      super (ctx, S_HumanResourceTestGroup_ID, trxName);
    }

    /** Load Constructor */
    public X_S_HumanResourceTestGroup (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCapacityInHours (final int CapacityInHours)
	{
		set_Value (COLUMNNAME_CapacityInHours, CapacityInHours);
	}

	@Override
	public int getCapacityInHours() 
	{
		return get_ValueAsInt(COLUMNNAME_CapacityInHours);
	}

	@Override
	public void setDepartment (final java.lang.String Department)
	{
		set_Value (COLUMNNAME_Department, Department);
	}

	@Override
	public java.lang.String getDepartment() 
	{
		return get_ValueAsString(COLUMNNAME_Department);
	}

	@Override
	public void setGroupIdentifier (final java.lang.String GroupIdentifier)
	{
		set_Value (COLUMNNAME_GroupIdentifier, GroupIdentifier);
	}

	@Override
	public java.lang.String getGroupIdentifier() 
	{
		return get_ValueAsString(COLUMNNAME_GroupIdentifier);
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
	public void setS_HumanResourceTestGroup_ID (final int S_HumanResourceTestGroup_ID)
	{
		if (S_HumanResourceTestGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_HumanResourceTestGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_HumanResourceTestGroup_ID, S_HumanResourceTestGroup_ID);
	}

	@Override
	public int getS_HumanResourceTestGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_HumanResourceTestGroup_ID);
	}
}