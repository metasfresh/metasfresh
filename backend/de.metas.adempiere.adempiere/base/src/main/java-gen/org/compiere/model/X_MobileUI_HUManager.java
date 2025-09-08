// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MobileUI_HUManager
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_HUManager extends org.compiere.model.PO implements I_MobileUI_HUManager, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1751514139L;

    /** Standard Constructor */
    public X_MobileUI_HUManager (final Properties ctx, final int MobileUI_HUManager_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_HUManager_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_HUManager (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setMobileUI_HUManager_ID (final int MobileUI_HUManager_ID)
	{
		if (MobileUI_HUManager_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_HUManager_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_HUManager_ID, MobileUI_HUManager_ID);
	}

	@Override
	public int getMobileUI_HUManager_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_HUManager_ID);
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
}