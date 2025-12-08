// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_ProCareManagement
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_ProCareManagement extends org.compiere.model.PO implements I_ExternalSystem_Config_ProCareManagement, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2091849040L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_ProCareManagement (final Properties ctx, final int ExternalSystem_Config_ProCareManagement_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_ProCareManagement_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_ProCareManagement (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public I_ExternalSystem_Config getExternalSystem_Config()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_ID, I_ExternalSystem_Config.class);
	}

	@Override
	public void setExternalSystem_Config(final I_ExternalSystem_Config ExternalSystem_Config)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_ID, I_ExternalSystem_Config.class, ExternalSystem_Config);
	}

	@Override
	public void setExternalSystem_Config_ID (final int ExternalSystem_Config_ID)
	{
		if (ExternalSystem_Config_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
	}

	@Override
	public int getExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
	}

	@Override
	public void setExternalSystem_Config_ProCareManagement_ID (final int ExternalSystem_Config_ProCareManagement_ID)
	{
		if (ExternalSystem_Config_ProCareManagement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID, ExternalSystem_Config_ProCareManagement_ID);
	}

	@Override
	public int getExternalSystem_Config_ProCareManagement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID);
	}

	@Override
	public void setExternalSystemValue (final String ExternalSystemValue)
	{
		set_Value (COLUMNNAME_ExternalSystemValue, ExternalSystemValue);
	}

	@Override
	public String getExternalSystemValue() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystemValue);
	}
}