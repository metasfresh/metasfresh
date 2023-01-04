// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_Resource_Group_Assignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_Resource_Group_Assignment extends org.compiere.model.PO implements I_S_Resource_Group_Assignment, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1234324180L;

    /** Standard Constructor */
    public X_S_Resource_Group_Assignment (final Properties ctx, final int S_Resource_Group_Assignment_ID, @Nullable final String trxName)
    {
      super (ctx, S_Resource_Group_Assignment_ID, trxName);
    }

    /** Load Constructor */
    public X_S_Resource_Group_Assignment (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAssignDateFrom (final java.sql.Timestamp AssignDateFrom)
	{
		set_ValueNoCheck (COLUMNNAME_AssignDateFrom, AssignDateFrom);
	}

	@Override
	public java.sql.Timestamp getAssignDateFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AssignDateFrom);
	}

	@Override
	public void setAssignDateTo (final java.sql.Timestamp AssignDateTo)
	{
		set_ValueNoCheck (COLUMNNAME_AssignDateTo, AssignDateTo);
	}

	@Override
	public java.sql.Timestamp getAssignDateTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AssignDateTo);
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
	public void setIsAllDay (final boolean IsAllDay)
	{
		set_Value (COLUMNNAME_IsAllDay, IsAllDay);
	}

	@Override
	public boolean isAllDay() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllDay);
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
	public void setS_Resource_Group_Assignment_ID (final int S_Resource_Group_Assignment_ID)
	{
		if (S_Resource_Group_Assignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Resource_Group_Assignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Resource_Group_Assignment_ID, S_Resource_Group_Assignment_ID);
	}

	@Override
	public int getS_Resource_Group_Assignment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_Group_Assignment_ID);
	}

	@Override
	public void setS_Resource_Group_ID (final int S_Resource_Group_ID)
	{
		if (S_Resource_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Resource_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Resource_Group_ID, S_Resource_Group_ID);
	}

	@Override
	public int getS_Resource_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_Group_ID);
	}
}