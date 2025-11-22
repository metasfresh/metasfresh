// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MobileUI_UserProfile_DD
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_UserProfile_DD extends org.compiere.model.PO implements I_MobileUI_UserProfile_DD, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 637196661L;

    /** Standard Constructor */
    public X_MobileUI_UserProfile_DD (final Properties ctx, final int MobileUI_UserProfile_DD_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_UserProfile_DD_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_UserProfile_DD (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsAllowPickingAnyHU (final boolean IsAllowPickingAnyHU)
	{
		set_Value (COLUMNNAME_IsAllowPickingAnyHU, IsAllowPickingAnyHU);
	}

	@Override
	public boolean isAllowPickingAnyHU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowPickingAnyHU);
	}

	@Override
	public void setIsAllowStartNextJobOnly (final boolean IsAllowStartNextJobOnly)
	{
		set_Value (COLUMNNAME_IsAllowStartNextJobOnly, IsAllowStartNextJobOnly);
	}

	@Override
	public boolean isAllowStartNextJobOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowStartNextJobOnly);
	}

	@Override
	public void setMaxLaunchers (final int MaxLaunchers)
	{
		set_Value (COLUMNNAME_MaxLaunchers, MaxLaunchers);
	}

	@Override
	public int getMaxLaunchers() 
	{
		return get_ValueAsInt(COLUMNNAME_MaxLaunchers);
	}

	@Override
	public void setMaxStartedLaunchers (final int MaxStartedLaunchers)
	{
		set_Value (COLUMNNAME_MaxStartedLaunchers, MaxStartedLaunchers);
	}

	@Override
	public int getMaxStartedLaunchers() 
	{
		return get_ValueAsInt(COLUMNNAME_MaxStartedLaunchers);
	}

	@Override
	public void setMobileUI_UserProfile_DD_ID (final int MobileUI_UserProfile_DD_ID)
	{
		if (MobileUI_UserProfile_DD_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_DD_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_DD_ID, MobileUI_UserProfile_DD_ID);
	}

	@Override
	public int getMobileUI_UserProfile_DD_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_DD_ID);
	}
}