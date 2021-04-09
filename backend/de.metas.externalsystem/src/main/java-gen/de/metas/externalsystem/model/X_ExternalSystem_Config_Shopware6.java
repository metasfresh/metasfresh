// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_Config_Shopware6
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_Shopware6 extends org.compiere.model.PO implements I_ExternalSystem_Config_Shopware6, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -96827426L;

	/** Standard Constructor */
	public X_ExternalSystem_Config_Shopware6 (final Properties ctx, final int ExternalSystem_Config_Shopware6_ID, @Nullable final String trxName)
	{
		super (ctx, ExternalSystem_Config_Shopware6_ID, trxName);
	}

	/** Load Constructor */
	public X_ExternalSystem_Config_Shopware6 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setClient_Id (final java.lang.String Client_Id)
	{
		set_Value (COLUMNNAME_Client_Id, Client_Id);
	}

	@Override
	public java.lang.String getClient_Id()
	{
		return get_ValueAsString(COLUMNNAME_Client_Id);
	}

	@Override
	public void setClient_Secret (final java.lang.String Client_Secret)
	{
		set_Value (COLUMNNAME_Client_Secret, Client_Secret);
	}

	@Override
	public java.lang.String getClient_Secret()
	{
		return get_ValueAsString(COLUMNNAME_Client_Secret);
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
	public void setExternalSystem_Config_Shopware6_ID (final int ExternalSystem_Config_Shopware6_ID)
	{
		if (ExternalSystem_Config_Shopware6_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Shopware6_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Shopware6_ID, ExternalSystem_Config_Shopware6_ID);
	}

	@Override
	public int getExternalSystem_Config_Shopware6_ID()
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_Shopware6_ID);
	}

	@Override
	public void setExternalSystemValue (final java.lang.String ExternalSystemValue)
	{
		set_Value (COLUMNNAME_ExternalSystemValue, ExternalSystemValue);
	}

	@Override
	public java.lang.String getExternalSystemValue()
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystemValue);
	}

	@Override
	public void setJSONPathConstantBPartnerID (final @Nullable java.lang.String JSONPathConstantBPartnerID)
	{
		set_Value (COLUMNNAME_JSONPathConstantBPartnerID, JSONPathConstantBPartnerID);
	}

	@Override
	public java.lang.String getJSONPathConstantBPartnerID()
	{
		return get_ValueAsString(COLUMNNAME_JSONPathConstantBPartnerID);
	}

	@Override
	public void setJSONPathConstantBPartnerLocationID (final @Nullable java.lang.String JSONPathConstantBPartnerLocationID)
	{
		set_Value (COLUMNNAME_JSONPathConstantBPartnerLocationID, JSONPathConstantBPartnerLocationID);
	}

	@Override
	public java.lang.String getJSONPathConstantBPartnerLocationID()
	{
		return get_ValueAsString(COLUMNNAME_JSONPathConstantBPartnerLocationID);
	}
}