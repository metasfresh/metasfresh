// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_ResourceUnAvailable
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_ResourceUnAvailable extends org.compiere.model.PO implements I_S_ResourceUnAvailable, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -799511794L;

    /** Standard Constructor */
    public X_S_ResourceUnAvailable (final Properties ctx, final int S_ResourceUnAvailable_ID, @Nullable final String trxName)
    {
      super (ctx, S_ResourceUnAvailable_ID, trxName);
    }

    /** Load Constructor */
    public X_S_ResourceUnAvailable (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDateFrom (final java.sql.Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	@Override
	public java.sql.Timestamp getDateFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateFrom);
	}

	@Override
	public void setDateTo (final @Nullable java.sql.Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	@Override
	public java.sql.Timestamp getDateTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateTo);
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
	public void setS_ResourceUnAvailable_ID (final int S_ResourceUnAvailable_ID)
	{
		if (S_ResourceUnAvailable_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_ResourceUnAvailable_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_ResourceUnAvailable_ID, S_ResourceUnAvailable_ID);
	}

	@Override
	public int getS_ResourceUnAvailable_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_ResourceUnAvailable_ID);
	}
}