// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_ResourceAssignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_ResourceAssignment extends org.compiere.model.PO implements I_S_ResourceAssignment, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -749641729L;

    /** Standard Constructor */
    public X_S_ResourceAssignment (final Properties ctx, final int S_ResourceAssignment_ID, @Nullable final String trxName)
    {
      super (ctx, S_ResourceAssignment_ID, trxName);
    }

    /** Load Constructor */
    public X_S_ResourceAssignment (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAssignDateTo (final @Nullable java.sql.Timestamp AssignDateTo)
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
	public void setIsConfirmed (final boolean IsConfirmed)
	{
		set_ValueNoCheck (COLUMNNAME_IsConfirmed, IsConfirmed);
	}

	@Override
	public boolean isConfirmed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsConfirmed);
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
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setS_Resource_ID (final int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, S_Resource_ID);
	}

	@Override
	public int getS_Resource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
	}

	@Override
	public void setS_ResourceAssignment_ID (final int S_ResourceAssignment_ID)
	{
		if (S_ResourceAssignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_ResourceAssignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_ResourceAssignment_ID, S_ResourceAssignment_ID);
	}

	@Override
	public int getS_ResourceAssignment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_ResourceAssignment_ID);
	}
}