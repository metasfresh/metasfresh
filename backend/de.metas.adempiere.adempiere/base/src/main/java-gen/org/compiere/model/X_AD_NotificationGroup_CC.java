// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_NotificationGroup_CC
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_NotificationGroup_CC extends org.compiere.model.PO implements I_AD_NotificationGroup_CC, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1342697129L;

    /** Standard Constructor */
    public X_AD_NotificationGroup_CC (final Properties ctx, final int AD_NotificationGroup_CC_ID, @Nullable final String trxName)
    {
      super (ctx, AD_NotificationGroup_CC_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_NotificationGroup_CC (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_NotificationGroup_CC_ID (final int AD_NotificationGroup_CC_ID)
	{
		if (AD_NotificationGroup_CC_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_NotificationGroup_CC_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_NotificationGroup_CC_ID, AD_NotificationGroup_CC_ID);
	}

	@Override
	public int getAD_NotificationGroup_CC_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_NotificationGroup_CC_ID);
	}

	@Override
	public org.compiere.model.I_AD_NotificationGroup getAD_NotificationGroup()
	{
		return get_ValueAsPO(COLUMNNAME_AD_NotificationGroup_ID, org.compiere.model.I_AD_NotificationGroup.class);
	}

	@Override
	public void setAD_NotificationGroup(final org.compiere.model.I_AD_NotificationGroup AD_NotificationGroup)
	{
		set_ValueFromPO(COLUMNNAME_AD_NotificationGroup_ID, org.compiere.model.I_AD_NotificationGroup.class, AD_NotificationGroup);
	}

	@Override
	public void setAD_NotificationGroup_ID (final int AD_NotificationGroup_ID)
	{
		if (AD_NotificationGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_NotificationGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_NotificationGroup_ID, AD_NotificationGroup_ID);
	}

	@Override
	public int getAD_NotificationGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_NotificationGroup_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}
}