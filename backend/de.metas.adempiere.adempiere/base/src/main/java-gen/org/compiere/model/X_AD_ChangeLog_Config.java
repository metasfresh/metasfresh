// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_ChangeLog_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_ChangeLog_Config extends org.compiere.model.PO implements I_AD_ChangeLog_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 850578821L;

    /** Standard Constructor */
    public X_AD_ChangeLog_Config (final Properties ctx, final int AD_ChangeLog_Config_ID, @Nullable final String trxName)
    {
      super (ctx, AD_ChangeLog_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_ChangeLog_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_ChangeLog_Config_ID (final int AD_ChangeLog_Config_ID)
	{
		if (AD_ChangeLog_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ChangeLog_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ChangeLog_Config_ID, AD_ChangeLog_Config_ID);
	}

	@Override
	public int getAD_ChangeLog_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_ChangeLog_Config_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setKeepChangeLogsDays (final int KeepChangeLogsDays)
	{
		set_Value (COLUMNNAME_KeepChangeLogsDays, KeepChangeLogsDays);
	}

	@Override
	public int getKeepChangeLogsDays() 
	{
		return get_ValueAsInt(COLUMNNAME_KeepChangeLogsDays);
	}
}