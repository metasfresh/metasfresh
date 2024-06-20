// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MobileUI_MFG_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_MFG_Config extends org.compiere.model.PO implements I_MobileUI_MFG_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1386815163L;

    /** Standard Constructor */
    public X_MobileUI_MFG_Config (final Properties ctx, final int MobileUI_MFG_Config_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_MFG_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_MFG_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsScanResourceRequired (final boolean IsScanResourceRequired)
	{
		set_Value (COLUMNNAME_IsScanResourceRequired, IsScanResourceRequired);
	}

	@Override
	public boolean isScanResourceRequired() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsScanResourceRequired);
	}

	@Override
	public void setMobileUI_MFG_Config_ID (final int MobileUI_MFG_Config_ID)
	{
		if (MobileUI_MFG_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_MFG_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_MFG_Config_ID, MobileUI_MFG_Config_ID);
	}

	@Override
	public int getMobileUI_MFG_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_MFG_Config_ID);
	}
}