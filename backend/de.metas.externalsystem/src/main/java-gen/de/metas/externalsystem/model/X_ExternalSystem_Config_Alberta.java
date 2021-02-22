// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_Config_Alberta
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_Alberta extends org.compiere.model.PO implements I_ExternalSystem_Config_Alberta, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1524631305L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_Alberta (final Properties ctx, final int ExternalSystem_Config_Alberta_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_Alberta_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_Alberta (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setApiKey (final java.lang.String ApiKey)
	{
		set_Value (COLUMNNAME_ApiKey, ApiKey);
	}

	@Override
	public java.lang.String getApiKey() 
	{
		return get_ValueAsString(COLUMNNAME_ApiKey);
	}

	@Override
	public void setBaseURL (final java.lang.String BaseURL)
	{
		set_Value (COLUMNNAME_BaseURL, BaseURL);
	}

	@Override
	public java.lang.String getBaseURL() 
	{
		return get_ValueAsString(COLUMNNAME_BaseURL);
	}

	@Override
	public void setExternalSystem_Config_Alberta_ID (final int ExternalSystem_Config_Alberta_ID)
	{
		if (ExternalSystem_Config_Alberta_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Alberta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Alberta_ID, ExternalSystem_Config_Alberta_ID);
	}

	@Override
	public int getExternalSystem_Config_Alberta_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_Alberta_ID);
	}

	@Override
	public de.metas.externalsystem.model.I_ExternalSystem_Config getExternalSystem_Config()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_ID, de.metas.externalsystem.model.I_ExternalSystem_Config.class);
	}

	@Override
	public void setExternalSystem_Config(final de.metas.externalsystem.model.I_ExternalSystem_Config ExternalSystem_Config)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_ID, de.metas.externalsystem.model.I_ExternalSystem_Config.class, ExternalSystem_Config);
	}

	@Override
	public void setExternalSystem_Config_ID (final int ExternalSystem_Config_ID)
	{
		if (ExternalSystem_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
	}

	@Override
	public int getExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
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
	public void setTenant (final java.lang.String Tenant)
	{
		set_Value (COLUMNNAME_Tenant, Tenant);
	}

	@Override
	public java.lang.String getTenant() 
	{
		return get_ValueAsString(COLUMNNAME_Tenant);
	}
}