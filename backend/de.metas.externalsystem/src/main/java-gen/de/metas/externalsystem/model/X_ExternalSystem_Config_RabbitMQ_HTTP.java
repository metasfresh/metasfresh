// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_RabbitMQ_HTTP
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_RabbitMQ_HTTP extends org.compiere.model.PO implements I_ExternalSystem_Config_RabbitMQ_HTTP, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -120952676L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_RabbitMQ_HTTP (final Properties ctx, final int ExternalSystem_Config_RabbitMQ_HTTP_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_RabbitMQ_HTTP_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_RabbitMQ_HTTP (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setExternalSystem_Config_RabbitMQ_HTTP_ID (final int ExternalSystem_Config_RabbitMQ_HTTP_ID)
	{
		if (ExternalSystem_Config_RabbitMQ_HTTP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_RabbitMQ_HTTP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_RabbitMQ_HTTP_ID, ExternalSystem_Config_RabbitMQ_HTTP_ID);
	}

	@Override
	public int getExternalSystem_Config_RabbitMQ_HTTP_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_RabbitMQ_HTTP_ID);
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

	@Override
	public void setIsSyncBPartnersToRabbitMQ (final boolean IsSyncBPartnersToRabbitMQ)
	{
		set_Value (COLUMNNAME_IsSyncBPartnersToRabbitMQ, IsSyncBPartnersToRabbitMQ);
	}

	@Override
	public boolean isSyncBPartnersToRabbitMQ() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSyncBPartnersToRabbitMQ);
	}

	@Override
	public void setRemoteURL (final String RemoteURL)
	{
		set_Value (COLUMNNAME_RemoteURL, RemoteURL);
	}

	@Override
	public String getRemoteURL()
	{
		return get_ValueAsString(COLUMNNAME_RemoteURL);
	}

	@Override
	public void setRouting_Key (final String Routing_Key)
	{
		set_Value (COLUMNNAME_Routing_Key, Routing_Key);
	}

	@Override
	public String getRouting_Key()
	{
		return get_ValueAsString(COLUMNNAME_Routing_Key);
	}

	@Override
	public void setAuthToken (final String AuthToken)
	{
		set_Value (COLUMNNAME_AuthToken, AuthToken);
	}

	@Override
	public String getAuthToken()
	{
		return get_ValueAsString(COLUMNNAME_AuthToken);
	}

}